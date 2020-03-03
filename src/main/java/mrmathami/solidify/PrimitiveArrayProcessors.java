package mrmathami.solidify;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

final class PrimitiveBooleanArrayProcessor implements ObjectProcessor<boolean[]> {
	private static final boolean[] EMPTY = new boolean[0];

	@Override
	public boolean usingEqualityCache() {
		return false;
	}

	@Nonnull
	@Override
	public List<boolean[]> preloadCache() {
		return Arrays.asList(null, EMPTY);
	}

	@Nonnull
	@Override
	public Class<boolean[]> getObjectClass() {
		return boolean[].class;
	}

	@Override
	public void liquify(@Nonnull ObjectWriter objectWriter, @Nullable ObjectWriter.Cache<boolean[]> cache, @Nullable boolean[] object) throws IOException {
		final int length = object.length;
		objectWriter.writePackedInt(length);
		if (length > 0) objectWriter.writeBooleans(object);
	}

	@Nullable
	@Override
	public boolean[] solidify(@Nonnull ObjectReader objectReader, @Nullable ObjectReader.Cache<boolean[]> cache) throws IOException {
		final int length = objectReader.readPackedInt();
		if (length < 0) throw new IOException("Invalid input data.");
		if (length == 0) return EMPTY;
		return objectReader.readBooleans(length);
	}
}

final class PrimitiveByteArrayProcessor implements ObjectProcessor<byte[]> {
	private static final byte[] EMPTY = new byte[0];

	@Override
	public boolean usingEqualityCache() {
		return false;
	}

	@Nonnull
	@Override
	public Class<byte[]> getObjectClass() {
		return byte[].class;
	}

	@Override
	public void liquify(@Nonnull ObjectWriter objectWriter, @Nullable ObjectWriter.Cache<byte[]> cache, @Nullable byte[] object) throws IOException {

		final int length = object.length;
		objectWriter.writePackedInt(length);
		if (length > 0) objectWriter.writeBytes(object);
	}

	@Nullable
	@Override
	public byte[] solidify(@Nonnull ObjectReader objectReader, @Nullable ObjectReader.Cache<byte[]> cache) throws IOException {
		final int length = objectReader.readPackedInt();
		if (length < 0) throw new IOException("Invalid input data.");
		if (length == 0) return EMPTY;
		return objectReader.readBytes(length);
	}
}

final class PrimitiveCharArrayProcessor implements ObjectProcessor<char[]> {
	private static final char[] EMPTY = new char[0];

	@Nonnull
	@Override
	public Class<char[]> getObjectClass() {
		return char[].class;
	}

	@Override
	public void liquify(@Nonnull ObjectWriter objectWriter, @Nullable ObjectWriter.Cache<char[]> cache, @Nullable char[] object) throws IOException {
		final int length = object.length;
		objectWriter.writePackedInt(length);
		if (length > 0) objectWriter.writeChars(object);
	}

	@Nullable
	@Override
	public char[] solidify(@Nonnull ObjectReader objectReader, @Nullable ObjectReader.Cache<char[]> cache) throws IOException {
		final int length = objectReader.readPackedInt();
		if (length < 0) throw new IOException("Invalid input data.");
		if (length == 0) return EMPTY;
		return objectReader.readChars(length);
	}
}

final class PrimitiveDoubleArrayProcessor implements ObjectProcessor<double[]> {
	private static final double[] EMPTY = new double[0];

	@Nonnull
	@Override
	public Class<double[]> getObjectClass() {
		return double[].class;
	}

	@Override
	public void liquify(@Nonnull ObjectWriter objectWriter, @Nullable ObjectWriter.Cache<double[]> cache, @Nullable double[] object) throws IOException {
		final int length = object.length;
		objectWriter.writePackedInt(length);
		if (length > 0) objectWriter.writeDoubles(object);
	}

	@Nullable
	@Override
	public double[] solidify(@Nonnull ObjectReader objectReader, @Nullable ObjectReader.Cache<double[]> cache) throws IOException {
		final int length = objectReader.readPackedInt();
		if (length < 0) throw new IOException("Invalid input data.");
		if (length == 0) return EMPTY;
		return objectReader.readDoubles(length);
	}
}

final class PrimitiveFloatArrayProcessor implements ObjectProcessor<float[]> {
	private static final float[] EMPTY = new float[0];

	@Nonnull
	@Override
	public Class<float[]> getObjectClass() {
		return float[].class;
	}

	@Override
	public void liquify(@Nonnull ObjectWriter objectWriter, @Nullable ObjectWriter.Cache<float[]> cache, @Nullable float[] object) throws IOException {
		final int length = object.length;
		objectWriter.writePackedInt(length);
		if (length > 0) objectWriter.writeFloats(object);
	}

	@Nullable
	@Override
	public float[] solidify(@Nonnull ObjectReader objectReader, @Nullable ObjectReader.Cache<float[]> cache) throws IOException {
		final int length = objectReader.readPackedInt();
		if (length < 0) throw new IOException("Invalid input data.");
		if (length == 0) return EMPTY;
		return objectReader.readFloats(length);
	}
}

final class PrimitiveIntArrayProcessor implements ObjectProcessor<int[]> {
	private static final int[] EMPTY = new int[0];

	@Nonnull
	@Override
	public Class<int[]> getObjectClass() {
		return int[].class;
	}

	@Override
	public void liquify(@Nonnull ObjectWriter objectWriter, @Nullable ObjectWriter.Cache<int[]> cache, @Nullable int[] object) throws IOException {
		final int length = object.length;
		objectWriter.writePackedInt(length);
		if (length > 0) objectWriter.writeInts(object);
	}

	@Nullable
	@Override
	public int[] solidify(@Nonnull ObjectReader objectReader, @Nullable ObjectReader.Cache<int[]> cache) throws IOException {
		final int length = objectReader.readPackedInt();
		if (length < 0) throw new IOException("Invalid input data.");
		if (length == 0) return EMPTY;
		return objectReader.readInts(length);
	}
}

final class PrimitiveLongArrayProcessor implements ObjectProcessor<long[]> {
	private static final long[] EMPTY = new long[0];

	@Nonnull
	@Override
	public Class<long[]> getObjectClass() {
		return long[].class;
	}

	@Override
	public void liquify(@Nonnull ObjectWriter objectWriter, @Nullable ObjectWriter.Cache<long[]> cache, @Nullable long[] object) throws IOException {
		final int length = object.length;
		objectWriter.writePackedInt(length);
		if (length > 0) objectWriter.writeLongs(object);
	}

	@Nullable
	@Override
	public long[] solidify(@Nonnull ObjectReader objectReader, @Nullable ObjectReader.Cache<long[]> cache) throws IOException {
		final int length = objectReader.readPackedInt();
		if (length < 0) throw new IOException("Invalid input data.");
		if (length == 0) return EMPTY;
		return objectReader.readLongs(length);
	}
}

final class PrimitiveShortArrayProcessor implements ObjectProcessor<short[]> {
	private static final short[] EMPTY = new short[0];

	@Nonnull
	@Override
	public Class<short[]> getObjectClass() {
		return short[].class;
	}

	@Override
	public void liquify(@Nonnull ObjectWriter objectWriter, @Nullable ObjectWriter.Cache<short[]> cache, @Nullable short[] object) throws IOException {
		final int length = object.length;
		objectWriter.writePackedInt(length);
		if (length > 0) objectWriter.writeShorts(object);
	}

	@Nullable
	@Override
	public short[] solidify(@Nonnull ObjectReader objectReader, @Nullable ObjectReader.Cache<short[]> cache) throws IOException {
		final int length = objectReader.readPackedInt();
		if (length < 0) throw new IOException("Invalid input data.");
		if (length == 0) return EMPTY;
		return objectReader.readShorts(length);
	}
}

