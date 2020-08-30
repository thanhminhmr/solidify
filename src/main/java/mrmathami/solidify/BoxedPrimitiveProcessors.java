package mrmathami.solidify;

import mrmathami.annotation.Nonnull;
import mrmathami.annotation.Nullable;
import java.io.IOException;

final class BoxedByteProcessor implements ObjectProcessor<Byte> {
	@Override
	public boolean usingCache() {
		return false;
	}

	@Nonnull
	@Override
	public Class<Byte> getObjectClass() {
		return Byte.class;
	}

	@Override
	public void liquify(@Nonnull ObjectWriter objectWriter, @Nullable ObjectWriter.Cache<Byte> writerCache, @Nullable Byte object) throws IOException {
		objectWriter.writeBoolean(object != null);
		if (object != null) objectWriter.writeByte(object);
	}

	@Nullable
	@Override
	public Byte solidify(@Nonnull ObjectReader objectReader, @Nullable ObjectReader.Cache<Byte> readerCache) throws IOException {
		return objectReader.readBoolean() ? objectReader.readByte() : null;
	}
}

final class BoxedShortProcessor implements ObjectProcessor<Short> {
	@Override
	public boolean usingCache() {
		return false;
	}

	@Nonnull
	@Override
	public Class<Short> getObjectClass() {
		return Short.class;
	}

	@Override
	public void liquify(@Nonnull ObjectWriter objectWriter, @Nullable ObjectWriter.Cache<Short> writerCache, @Nullable Short object) throws IOException {
		objectWriter.writeBoolean(object != null);
		if (object != null) objectWriter.writeShort(object);
	}

	@Nullable
	@Override
	public Short solidify(@Nonnull ObjectReader objectReader, @Nullable ObjectReader.Cache<Short> readerCache) throws IOException {
		return objectReader.readBoolean() ? objectReader.readShort() : null;
	}
}

final class BoxedIntegerProcessor implements ObjectProcessor<Integer> {
	@Override
	public boolean usingCache() {
		return false;
	}

	@Nonnull
	@Override
	public Class<Integer> getObjectClass() {
		return Integer.class;
	}

	@Override
	public void liquify(@Nonnull ObjectWriter objectWriter, @Nullable ObjectWriter.Cache<Integer> writerCache, @Nullable Integer object) throws IOException {
		objectWriter.writeBoolean(object != null);
		if (object != null) objectWriter.writeInt(object);
	}

	@Nullable
	@Override
	public Integer solidify(@Nonnull ObjectReader objectReader, @Nullable ObjectReader.Cache<Integer> readerCache) throws IOException {
		return objectReader.readBoolean() ? objectReader.readInt() : null;
	}
}

final class BoxedLongProcessor implements ObjectProcessor<Long> {
	@Override
	public boolean usingCache() {
		return false;
	}

	@Nonnull
	@Override
	public Class<Long> getObjectClass() {
		return Long.class;
	}

	@Override
	public void liquify(@Nonnull ObjectWriter objectWriter, @Nullable ObjectWriter.Cache<Long> writerCache, @Nullable Long object) throws IOException {
		objectWriter.writeBoolean(object != null);
		if (object != null) objectWriter.writeLong(object);
	}

	@Nullable
	@Override
	public Long solidify(@Nonnull ObjectReader objectReader, @Nullable ObjectReader.Cache<Long> readerCache) throws IOException {
		return objectReader.readBoolean() ? objectReader.readLong() : null;
	}
}

final class BoxedFloatProcessor implements ObjectProcessor<Float> {
	@Override
	public boolean usingCache() {
		return false;
	}

	@Nonnull
	@Override
	public Class<Float> getObjectClass() {
		return Float.class;
	}

	@Override
	public void liquify(@Nonnull ObjectWriter objectWriter, @Nullable ObjectWriter.Cache<Float> writerCache, @Nullable Float object) throws IOException {
		// 1111 1111 1100 0000 null
		// 0111 1111 1000 0000 positive infinity
		// 1111 1111 1000 0000 negative infinity
		// 0111 1111 1100 0000 NaN
		// ?000 0000 0??? ???? \
		// ?... .... .??? ????  | normal double
		// ?111 1111 0??? ???? /
		if (object == null) {
			objectWriter.writeShort((short) 0xFFC0);
		} else {
			final float value = object;
			if (value == Float.POSITIVE_INFINITY) {
				objectWriter.writeShort((short) 0x7F80);
			} else if (value == Float.NEGATIVE_INFINITY) {
				objectWriter.writeShort((short) 0xFF80);
			} else if (Float.isNaN(value)) {
				objectWriter.writeShort((short) 0x7FC0);
			} else {
				final int bits = Float.floatToRawIntBits(value);
				assert (bits & 0x7F800000) != 0x7F800000;
				objectWriter.writeShort((short) (bits >> 16));
				objectWriter.writeShort((short) bits);
			}
		}
	}

	@Nullable
	@Override
	public Float solidify(@Nonnull ObjectReader objectReader, @Nullable ObjectReader.Cache<Float> readerCache) throws IOException {
		// 1111 1111 1100 0000 null
		// 0111 1111 1000 0000 positive infinity
		// 1111 1111 1000 0000 negative infinity
		// 0111 1111 1100 0000 NaN
		// ?000 0000 0??? ???? \
		// ?... .... .??? ????  | normal double
		// ?111 1111 0??? ???? /
		final int leadingBits = objectReader.readUnsignedShort();
		if (leadingBits == 0xFFC0) return null;
		if (leadingBits == 0x7F80) return Float.POSITIVE_INFINITY;
		if (leadingBits == 0xFF80) return Float.NEGATIVE_INFINITY;
		if (leadingBits == 0x7FC0) return Float.NaN;
		if ((leadingBits & 0x7F80) == 0x7F80) throw new IOException("Invalid input data.");
		return Float.intBitsToFloat(leadingBits << 16 | objectReader.readUnsignedShort());
	}
}

final class BoxedDoubleProcessor implements ObjectProcessor<Double> {
	@Override
	public boolean usingCache() {
		return false;
	}

	@Nonnull
	@Override
	public Class<Double> getObjectClass() {
		return Double.class;
	}

	@Override
	public void liquify(@Nonnull ObjectWriter objectWriter, @Nullable ObjectWriter.Cache<Double> writerCache, @Nullable Double object) throws IOException {
		// 1111 1111 1111 1000 null
		// 0111 1111 1111 0000 positive infinity
		// 1111 1111 1111 0000 negative infinity
		// 0111 1111 1111 1000 NaN
		// ?000 0000 0000 ???? \
		// ?... .... .... ????  | normal double
		// ?111 1111 1110 ???? /
		if (object == null) {
			objectWriter.writeShort((short) 0xFFF8);
		} else {
			final double value = object;
			if (value == Double.POSITIVE_INFINITY) {
				objectWriter.writeShort((short) 0x7FF0);
			} else if (value == Double.NEGATIVE_INFINITY) {
				objectWriter.writeShort((short) 0xFFF0);
			} else if (Double.isNaN(value)) {
				objectWriter.writeShort((short) 0x7FF8);
			} else {
				final long bits = Double.doubleToRawLongBits(value);
				assert (bits & 0x7FF0000000000000L) != 0x7FF0000000000000L;
				objectWriter.writeShort((short) (bits >> 48));
				objectWriter.writeShort((short) (bits >> 32));
				objectWriter.writeInt((int) bits);
			}
		}
	}

	@Nullable
	@Override
	public Double solidify(@Nonnull ObjectReader objectReader, @Nullable ObjectReader.Cache<Double> readerCache) throws IOException {
		// 1111 1111 1111 1000 null
		// 0111 1111 1111 0000 positive infinity
		// 1111 1111 1111 0000 negative infinity
		// 0111 1111 1111 1000 NaN
		// ?000 0000 0000 ???? \
		// ?... .... .... ????  | normal double
		// ?111 1111 1110 ???? /
		final int leadingBits = objectReader.readUnsignedShort();
		if (leadingBits == 0xFFF8) return null;
		if (leadingBits == 0x7FF0) return Double.POSITIVE_INFINITY;
		if (leadingBits == 0xFFF0) return Double.NEGATIVE_INFINITY;
		if (leadingBits == 0x7FF8) return Double.NaN;
		if ((leadingBits & 0x7FF0) == 0x7FF0) throw new IOException("Invalid input data.");
		return Double.longBitsToDouble(((long) (leadingBits << 16 | objectReader.readUnsignedShort())) << 32 | objectReader.readUnsignedInt());
	}
}

final class BoxedBooleanProcessor implements ObjectProcessor<Boolean> {
	@Override
	public boolean usingCache() {
		return false;
	}

	@Nonnull
	@Override
	public Class<Boolean> getObjectClass() {
		return Boolean.class;
	}

	@Override
	public void liquify(@Nonnull ObjectWriter objectWriter, @Nullable ObjectWriter.Cache<Boolean> writerCache, @Nullable Boolean object) throws IOException {
		objectWriter.writeByte((byte) (object == null ? 0xFF : object ? 0x80 : 0x00));
	}

	@Nullable
	@Override
	public Boolean solidify(@Nonnull ObjectReader objectReader, @Nullable ObjectReader.Cache<Boolean> readerCache) throws IOException {
		final int value = objectReader.readUnsignedByte();
		if (value == 0x00) return Boolean.FALSE;
		if (value == 0x80) return Boolean.TRUE;
		if (value == 0xFF) return null;
		throw new IOException("Invalid input data.");
	}
}

final class BoxedCharacterProcessor implements ObjectProcessor<Character> {
	@Override
	public boolean usingCache() {
		return false;
	}

	@Nonnull
	@Override
	public Class<Character> getObjectClass() {
		return Character.class;
	}

	@Override
	public void liquify(@Nonnull ObjectWriter objectWriter, @Nullable ObjectWriter.Cache<Character> writerCache, @Nullable Character object) throws IOException {
		objectWriter.writeBoolean(object != null);
		if (object != null) objectWriter.writeChar(object);
	}

	@Nullable
	@Override
	public Character solidify(@Nonnull ObjectReader objectReader, @Nullable ObjectReader.Cache<Character> readerCache) throws IOException {
		return objectReader.readBoolean() ? objectReader.readChar() : null;
	}
}
