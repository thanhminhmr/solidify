package mrmathami.solidify;

import javax.annotation.Nonnull;
import java.io.IOException;

final class BooleanArrayProcessor implements ObjectProcessor<boolean[]> {
	private static final boolean[] EMPTY = new boolean[0];

	@Nonnull
	@Override
	public Class<boolean[]> getObjectClass() {
		return boolean[].class;
	}

	@Override
	public void liquify(@Nonnull ObjectWriter objectWriter, @Nonnull boolean[] object) throws IOException {
		final int length = object.length;
		objectWriter.writePackedInt(length);
		if (length > 0) objectWriter.writeBooleans(object);
	}

	@Nonnull
	@Override
	public boolean[] solidify(@Nonnull ObjectReader objectReader) throws IOException {
		final int length = objectReader.readPackedInt();
		if (length < 0) throw new IOException("Invalid input data.");
		if (length == 0) return EMPTY;
		return objectReader.readBooleans(length);
	}
}


final class ByteArrayProcessor implements ObjectProcessor<byte[]> {
	private static final byte[] EMPTY = new byte[0];

	@Nonnull
	@Override
	public Class<byte[]> getObjectClass() {
		return byte[].class;
	}

	@Override
	public void liquify(@Nonnull ObjectWriter objectWriter, @Nonnull byte[] object) throws IOException {
		final int length = object.length;
		objectWriter.writePackedInt(length);
		if (length > 0) objectWriter.writeBytes(object);
	}

	@Nonnull
	@Override
	public byte[] solidify(@Nonnull ObjectReader objectReader) throws IOException {
		final int length = objectReader.readPackedInt();
		if (length < 0) throw new IOException("Invalid input data.");
		if (length == 0) return EMPTY;
		return objectReader.readBytes(length);
	}
}


final class CharArrayProcessor implements ObjectProcessor<char[]> {
	private static final char[] EMPTY = new char[0];

	@Nonnull
	@Override
	public Class<char[]> getObjectClass() {
		return char[].class;
	}

	@Override
	public void liquify(@Nonnull ObjectWriter objectWriter, @Nonnull char[] object) throws IOException {
		final int length = object.length;
		objectWriter.writePackedInt(length);
		if (length > 0) objectWriter.writeChars(object);
	}

	@Nonnull
	@Override
	public char[] solidify(@Nonnull ObjectReader objectReader) throws IOException {
		final int length = objectReader.readPackedInt();
		if (length < 0) throw new IOException("Invalid input data.");
		if (length == 0) return EMPTY;
		return objectReader.readChars(length);
	}
}


final class DoubleArrayProcessor implements ObjectProcessor<double[]> {
	private static final double[] EMPTY = new double[0];

	@Nonnull
	@Override
	public Class<double[]> getObjectClass() {
		return double[].class;
	}

	@Override
	public void liquify(@Nonnull ObjectWriter objectWriter, @Nonnull double[] object) throws IOException {
		final int length = object.length;
		objectWriter.writePackedInt(length);
		if (length > 0) objectWriter.writeDoubles(object);
	}

	@Nonnull
	@Override
	public double[] solidify(@Nonnull ObjectReader objectReader) throws IOException {
		final int length = objectReader.readPackedInt();
		if (length < 0) throw new IOException("Invalid input data.");
		if (length == 0) return EMPTY;
		return objectReader.readDoubles(length);
	}
}


final class FloatArrayProcessor implements ObjectProcessor<float[]> {
	private static final float[] EMPTY = new float[0];

	@Nonnull
	@Override
	public Class<float[]> getObjectClass() {
		return float[].class;
	}

	@Override
	public void liquify(@Nonnull ObjectWriter objectWriter, @Nonnull float[] object) throws IOException {
		final int length = object.length;
		objectWriter.writePackedInt(length);
		if (length > 0) objectWriter.writeFloats(object);
	}

	@Nonnull
	@Override
	public float[] solidify(@Nonnull ObjectReader objectReader) throws IOException {
		final int length = objectReader.readPackedInt();
		if (length < 0) throw new IOException("Invalid input data.");
		if (length == 0) return EMPTY;
		return objectReader.readFloats(length);
	}
}


final class IntArrayProcessor implements ObjectProcessor<int[]> {
	private static final int[] EMPTY = new int[0];

	@Nonnull
	@Override
	public Class<int[]> getObjectClass() {
		return int[].class;
	}

	@Override
	public void liquify(@Nonnull ObjectWriter objectWriter, @Nonnull int[] object) throws IOException {
		final int length = object.length;
		objectWriter.writePackedInt(length);
		if (length > 0) objectWriter.writeInts(object);
	}

	@Nonnull
	@Override
	public int[] solidify(@Nonnull ObjectReader objectReader) throws IOException {
		final int length = objectReader.readPackedInt();
		if (length < 0) throw new IOException("Invalid input data.");
		if (length == 0) return EMPTY;
		return objectReader.readInts(length);
	}
}


final class LongArrayProcessor implements ObjectProcessor<long[]> {
	private static final long[] EMPTY = new long[0];

	@Nonnull
	@Override
	public Class<long[]> getObjectClass() {
		return long[].class;
	}

	@Override
	public void liquify(@Nonnull ObjectWriter objectWriter, @Nonnull long[] object) throws IOException {
		final int length = object.length;
		objectWriter.writePackedInt(length);
		if (length > 0) objectWriter.writeLongs(object);
	}

	@Nonnull
	@Override
	public long[] solidify(@Nonnull ObjectReader objectReader) throws IOException {
		final int length = objectReader.readPackedInt();
		if (length < 0) throw new IOException("Invalid input data.");
		if (length == 0) return EMPTY;
		return objectReader.readLongs(length);
	}
}
