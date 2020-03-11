package mrmathami.solidify;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Arrays;

final class BoxedByteArrayProcessor extends AbstractArrayProcessor<Byte> {
	BoxedByteArrayProcessor() {
		super(Byte[].class);
	}

	@Override
	protected void liquify(@Nonnull ObjectWriter objectWriter, @Nonnull Byte[] array) throws IOException {
		final int length = array.length;
		assert length > 0;

		final boolean[] mask = new boolean[length];
		for (int i = 0; i < length; i++) mask[i] = array[i] != null;
		objectWriter.writePackedBooleans(mask);
		for (final Byte object : array) if (object != null) objectWriter.writeByte(object);
	}

	@Nonnull
	@Override
	protected Byte[] solidify(@Nonnull ObjectReader objectReader, int length) throws IOException {
		assert length > 0;

		final boolean[] mask = objectReader.readPackedBooleans(length);
		final Byte[] array = new Byte[length];
		for (int i = 0; i < length; i++) if (mask[i]) array[i] = objectReader.readByte();
		return array;
	}
}

final class BoxedShortArrayProcessor extends AbstractArrayProcessor<Short> {
	BoxedShortArrayProcessor() {
		super(Short[].class);
	}

	@Override
	protected void liquify(@Nonnull ObjectWriter objectWriter, @Nonnull Short[] array) throws IOException {
		final int length = array.length;
		assert length > 0;

		final boolean[] mask = new boolean[length];
		for (int i = 0; i < length; i++) mask[i] = array[i] != null;
		objectWriter.writePackedBooleans(mask);
		for (final Short object : array) if (object != null) objectWriter.writeShort(object);
	}

	@Nonnull
	@Override
	protected Short[] solidify(@Nonnull ObjectReader objectReader, int length) throws IOException {
		assert length > 0;

		final boolean[] mask = objectReader.readPackedBooleans(length);
		final Short[] array = new Short[length];
		for (int i = 0; i < length; i++) if (mask[i]) array[i] = objectReader.readShort();
		return array;
	}
}

final class BoxedIntegerArrayProcessor extends AbstractArrayProcessor<Integer> {
	BoxedIntegerArrayProcessor() {
		super(Integer[].class);
	}

	@Override
	protected void liquify(@Nonnull ObjectWriter objectWriter, @Nonnull Integer[] array) throws IOException {
		final int length = array.length;
		assert length > 0;

		final boolean[] mask = new boolean[length];
		for (int i = 0; i < length; i++) mask[i] = array[i] != null;
		objectWriter.writePackedBooleans(mask);
		for (final Integer object : array) if (object != null) objectWriter.writeInt(object);
	}

	@Nonnull
	@Override
	protected Integer[] solidify(@Nonnull ObjectReader objectReader, int length) throws IOException {
		assert length > 0;

		final boolean[] mask = objectReader.readPackedBooleans(length);
		final Integer[] array = new Integer[length];
		for (int i = 0; i < length; i++) if (mask[i]) array[i] = objectReader.readInt();
		return array;
	}
}

final class BoxedLongArrayProcessor extends AbstractArrayProcessor<Long> {
	BoxedLongArrayProcessor() {
		super(Long[].class);
	}

	@Override
	protected void liquify(@Nonnull ObjectWriter objectWriter, @Nonnull Long[] array) throws IOException {
		final int length = array.length;
		assert length > 0;

		final boolean[] mask = new boolean[length];
		for (int i = 0; i < length; i++) mask[i] = array[i] != null;
		objectWriter.writePackedBooleans(mask);
		for (final Long object : array) if (object != null) objectWriter.writeLong(object);
	}

	@Nonnull
	@Override
	protected Long[] solidify(@Nonnull ObjectReader objectReader, int length) throws IOException {
		assert length > 0;

		final boolean[] mask = objectReader.readPackedBooleans(length);
		final Long[] array = new Long[length];
		for (int i = 0; i < length; i++) if (mask[i]) array[i] = objectReader.readLong();
		return array;
	}
}

final class BoxedFloatArrayProcessor extends AbstractArrayProcessor<Float> {
	BoxedFloatArrayProcessor() {
		super(Float[].class);
	}

	@Override
	protected void liquify(@Nonnull ObjectWriter objectWriter, @Nonnull Float[] array) throws IOException {
		final int length = array.length;
		assert length > 0;

		final boolean[] mask = new boolean[length];
		for (int i = 0; i < length; i++) mask[i] = array[i] != null;
		objectWriter.writePackedBooleans(mask);
		for (final Float object : array) if (object != null) objectWriter.writeFloat(object);
	}

	@Nonnull
	@Override
	protected Float[] solidify(@Nonnull ObjectReader objectReader, int length) throws IOException {
		assert length > 0;

		final boolean[] mask = objectReader.readPackedBooleans(length);
		final Float[] array = new Float[length];
		for (int i = 0; i < length; i++) if (mask[i]) array[i] = objectReader.readFloat();
		return array;
	}
}

final class BoxedDoubleArrayProcessor extends AbstractArrayProcessor<Double> {
	BoxedDoubleArrayProcessor() {
		super(Double[].class);
	}

	@Override
	protected void liquify(@Nonnull ObjectWriter objectWriter, @Nonnull Double[] array) throws IOException {
		final int length = array.length;
		assert length > 0;

		final boolean[] mask = new boolean[length];
		for (int i = 0; i < length; i++) mask[i] = array[i] != null;
		objectWriter.writePackedBooleans(mask);
		for (final Double object : array) if (object != null) objectWriter.writeDouble(object);
	}

	@Nonnull
	@Override
	protected Double[] solidify(@Nonnull ObjectReader objectReader, int length) throws IOException {
		assert length > 0;

		final boolean[] mask = objectReader.readPackedBooleans(length);
		final Double[] array = new Double[length];
		for (int i = 0; i < length; i++) if (mask[i]) array[i] = objectReader.readDouble();
		return array;
	}
}

final class BoxedBooleanArrayProcessor extends AbstractArrayProcessor<Boolean> {
	BoxedBooleanArrayProcessor() {
		super(Boolean[].class);
	}

	@Override
	protected void liquify(@Nonnull ObjectWriter objectWriter, @Nonnull Boolean[] array) throws IOException {
		final int length = array.length;
		assert length > 0;

		final boolean[] mask = new boolean[length];
		final boolean[] packed = new boolean[length];

		int count = 0;
		for (int i = 0; i < length; i++) if (mask[i] = array[i] != null) packed[count++] = array[i];

		objectWriter.writePackedBooleans(mask);
		objectWriter.writePackedBooleans(count == length ? packed : Arrays.copyOf(packed, count));
	}

	@Nonnull
	@Override
	protected Boolean[] solidify(@Nonnull ObjectReader objectReader, int length) throws IOException {
		assert length > 0;

		final boolean[] mask = objectReader.readPackedBooleans(length);

		int count = 0;
		for (int i = 0; i < length; i++) if (mask[i]) count++;

		final boolean[] packed = objectReader.readPackedBooleans(count);

		final Boolean[] array = new Boolean[length];
		for (int i = 0, j = 0; i < length; i++) if (mask[i]) array[i] = packed[j++];
		return array;
	}
}

final class BoxedCharacterArrayProcessor extends AbstractArrayProcessor<Character> {
	BoxedCharacterArrayProcessor() {
		super(Character[].class);
	}

	@Override
	protected void liquify(@Nonnull ObjectWriter objectWriter, @Nonnull Character[] array) throws IOException {
		final int length = array.length;
		assert length > 0;

		final boolean[] mask = new boolean[length];
		for (int i = 0; i < length; i++) mask[i] = array[i] != null;
		objectWriter.writePackedBooleans(mask);
		for (final Character object : array) if (object != null) objectWriter.writeChar(object);
	}

	@Nonnull
	@Override
	protected Character[] solidify(@Nonnull ObjectReader objectReader, int length) throws IOException {
		assert length > 0;

		final boolean[] mask = objectReader.readPackedBooleans(length);
		final Character[] array = new Character[length];
		for (int i = 0; i < length; i++) if (mask[i]) array[i] = objectReader.readChar();
		return array;
	}
}
