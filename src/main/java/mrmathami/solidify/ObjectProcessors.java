package mrmathami.solidify;

import javax.annotation.Nonnull;
import java.io.IOException;

final class ByteProcessor implements ObjectProcessor<Byte> {
	@Override
	public int getCacheMode() {
		return 1;
	}

	@Nonnull
	@Override
	public Class<Byte> getObjectClass() {
		return Byte.class;
	}

	@Override
	public void liquify(@Nonnull ObjectWriter objectWriter, @Nonnull Byte object) throws IOException {
		objectWriter.writeByte(object);
	}

	@Nonnull
	@Override
	public Byte solidify(@Nonnull ObjectReader objectReader) throws IOException {
		return objectReader.readByte();
	}
}

final class ShortProcessor implements ObjectProcessor<Short> {
	@Override
	public int getCacheMode() {
		return 1;
	}

	@Nonnull
	@Override
	public Class<Short> getObjectClass() {
		return Short.class;
	}

	@Override
	public void liquify(@Nonnull ObjectWriter objectWriter, @Nonnull Short object) throws IOException {
		objectWriter.writeShort(object);
	}

	@Nonnull
	@Override
	public Short solidify(@Nonnull ObjectReader objectReader) throws IOException {
		return objectReader.readShort();
	}
}

final class IntegerProcessor implements ObjectProcessor<Integer> {
	@Override
	public int getCacheMode() {
		return 1;
	}

	@Nonnull
	@Override
	public Class<Integer> getObjectClass() {
		return Integer.class;
	}

	@Override
	public void liquify(@Nonnull ObjectWriter objectWriter, @Nonnull Integer object) throws IOException {
		objectWriter.writeInt(object);
	}

	@Nonnull
	@Override
	public Integer solidify(@Nonnull ObjectReader objectReader) throws IOException {
		return objectReader.readInt();
	}
}

final class LongProcessor implements ObjectProcessor<Long> {
	@Override
	public int getCacheMode() {
		return 1;
	}

	@Nonnull
	@Override
	public Class<Long> getObjectClass() {
		return Long.class;
	}

	@Override
	public void liquify(@Nonnull ObjectWriter objectWriter, @Nonnull Long object) throws IOException {
		objectWriter.writeLong(object);
	}

	@Nonnull
	@Override
	public Long solidify(@Nonnull ObjectReader objectReader) throws IOException {
		return objectReader.readLong();
	}
}

final class FloatProcessor implements ObjectProcessor<Float> {
	@Override
	public int getCacheMode() {
		return 1;
	}

	@Nonnull
	@Override
	public Class<Float> getObjectClass() {
		return Float.class;
	}

	@Override
	public void liquify(@Nonnull ObjectWriter objectWriter, @Nonnull Float object) throws IOException {
		objectWriter.writeFloat(object);
	}

	@Nonnull
	@Override
	public Float solidify(@Nonnull ObjectReader objectReader) throws IOException {
		return objectReader.readFloat();
	}
}

final class DoubleProcessor implements ObjectProcessor<Double> {
	@Override
	public int getCacheMode() {
		return 1;
	}

	@Nonnull
	@Override
	public Class<Double> getObjectClass() {
		return Double.class;
	}

	@Override
	public void liquify(@Nonnull ObjectWriter objectWriter, @Nonnull Double object) throws IOException {
		objectWriter.writeDouble(object);
	}

	@Nonnull
	@Override
	public Double solidify(@Nonnull ObjectReader objectReader) throws IOException {
		return objectReader.readDouble();
	}
}

final class BooleanProcessor implements ObjectProcessor<Boolean> {
	@Override
	public int getCacheMode() {
		return 1;
	}

	@Nonnull
	@Override
	public Class<Boolean> getObjectClass() {
		return Boolean.class;
	}

	@Override
	public void liquify(@Nonnull ObjectWriter objectWriter, @Nonnull Boolean object) throws IOException {
		objectWriter.writeBoolean(object);
	}

	@Nonnull
	@Override
	public Boolean solidify(@Nonnull ObjectReader objectReader) throws IOException {
		return objectReader.readBoolean();
	}
}

final class CharacterProcessor implements ObjectProcessor<Character> {
	@Override
	public int getCacheMode() {
		return 1;
	}

	@Nonnull
	@Override
	public Class<Character> getObjectClass() {
		return Character.class;
	}

	@Override
	public void liquify(@Nonnull ObjectWriter objectWriter, @Nonnull Character object) throws IOException {
		objectWriter.writeChar(object);
	}

	@Nonnull
	@Override
	public Character solidify(@Nonnull ObjectReader objectReader) throws IOException {
		return objectReader.readChar();
	}
}

final class StringProcessor implements ObjectProcessor<String> {
	@Override
	public int getCacheMode() {
		return 1;
	}

	@Nonnull
	@Override
	public Class<String> getObjectClass() {
		return String.class;
	}

	@Override
	public void liquify(@Nonnull ObjectWriter objectWriter, @Nonnull String object) throws IOException {
		objectWriter.writeUtf8(object);
	}

	@Nonnull
	@Override
	public String solidify(@Nonnull ObjectReader objectReader) throws IOException {
		return objectReader.readUtf8();
	}
}
