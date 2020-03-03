package mrmathami.solidify;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

final class PrimitiveByteArrayProcessor implements ObjectProcessor<byte[]> {
	private static final byte[] EMPTY = new byte[0];

	@Nonnull
	@Override
	public List<byte[]> preloadCache() {
		return Arrays.asList(null, EMPTY);
	}

	@Nonnull
	@Override
	public Class<byte[]> getObjectClass() {
		return byte[].class;
	}

	@Override
	public void liquify(@Nonnull ObjectWriter objectWriter, @Nullable ObjectWriter.Cache<byte[]> writerCache, @Nullable byte[] object) throws IOException {
		if (writerCache == null) throw new IllegalStateException("Object cache not available.");

		final int length = object != null ? object.length : -1;
		final int index = writerCache.putIfAbsent(length > 0 ? object : EMPTY);
		if (index >= 0) {
			objectWriter.writePackedInt(index);
		} else {
			assert length > 0;
			if (length < 0x8000) {
				objectWriter.writePackedInt(-length);
			} else {
				objectWriter.writePackedInt(-0x8000);
				objectWriter.writePackedInt(length - 0x8000);
			}
			objectWriter.writeBytes(object);
		}
	}

	@Nullable
	@Override
	public byte[] solidify(@Nonnull ObjectReader objectReader, @Nullable ObjectReader.Cache<byte[]> readerCache) throws IOException {
		if (readerCache == null) throw new IllegalStateException("Object cache not available.");

		try {
			final int index = objectReader.readPackedInt();
			if (index < 0) {
				final ObjectReader.Cache.Slot<byte[]> slot = readerCache.alloc();
				final int length = index > -0x8000 ? -index : objectReader.readPackedInt() + 0x8000;
				final byte[] object = objectReader.readBytes(length);
				slot.put(object);
				return object;
			} else {
				return readerCache.get(index);
			}
		} catch (IllegalStateException e) {
			throw new IOException("Invalid input data.", e);
		}
	}
}

final class PrimitiveShortArrayProcessor implements ObjectProcessor<short[]> {
	private static final short[] EMPTY = new short[0];

	@Nonnull
	@Override
	public List<short[]> preloadCache() {
		return Arrays.asList(null, EMPTY);
	}

	@Nonnull
	@Override
	public Class<short[]> getObjectClass() {
		return short[].class;
	}

	@Override
	public void liquify(@Nonnull ObjectWriter objectWriter, @Nullable ObjectWriter.Cache<short[]> writerCache, @Nullable short[] object) throws IOException {
		if (writerCache == null) throw new IllegalStateException("Object cache not available.");

		final int length = object != null ? object.length : -1;
		final int index = writerCache.putIfAbsent(length > 0 ? object : EMPTY);
		if (index >= 0) {
			objectWriter.writePackedInt(index);
		} else {
			assert length > 0;
			if (length < 0x8000) {
				objectWriter.writePackedInt(-length);
			} else {
				objectWriter.writePackedInt(-0x8000);
				objectWriter.writePackedInt(length - 0x8000);
			}
			objectWriter.writeShorts(object);
		}
	}

	@Nullable
	@Override
	public short[] solidify(@Nonnull ObjectReader objectReader, @Nullable ObjectReader.Cache<short[]> readerCache) throws IOException {
		if (readerCache == null) throw new IllegalStateException("Object cache not available.");

		try {
			final int index = objectReader.readPackedInt();
			if (index < 0) {
				final ObjectReader.Cache.Slot<short[]> slot = readerCache.alloc();
				final int length = index > -0x8000 ? -index : objectReader.readPackedInt() + 0x8000;
				final short[] object = objectReader.readShorts(length);
				slot.put(object);
				return object;
			} else {
				return readerCache.get(index);
			}
		} catch (IllegalStateException e) {
			throw new IOException("Invalid input data.", e);
		}
	}
}

final class PrimitiveIntArrayProcessor implements ObjectProcessor<int[]> {
	private static final int[] EMPTY = new int[0];

	@Nonnull
	@Override
	public List<int[]> preloadCache() {
		return Arrays.asList(null, EMPTY);
	}

	@Nonnull
	@Override
	public Class<int[]> getObjectClass() {
		return int[].class;
	}

	@Override
	public void liquify(@Nonnull ObjectWriter objectWriter, @Nullable ObjectWriter.Cache<int[]> writerCache, @Nullable int[] object) throws IOException {
		if (writerCache == null) throw new IllegalStateException("Object cache not available.");

		final int length = object != null ? object.length : -1;
		final int index = writerCache.putIfAbsent(length > 0 ? object : EMPTY);
		if (index >= 0) {
			objectWriter.writePackedInt(index);
		} else {
			assert length > 0;
			if (length < 0x8000) {
				objectWriter.writePackedInt(-length);
			} else {
				objectWriter.writePackedInt(-0x8000);
				objectWriter.writePackedInt(length - 0x8000);
			}
			objectWriter.writeInts(object);
		}
	}

	@Nullable
	@Override
	public int[] solidify(@Nonnull ObjectReader objectReader, @Nullable ObjectReader.Cache<int[]> readerCache) throws IOException {
		if (readerCache == null) throw new IllegalStateException("Object cache not available.");

		try {
			final int index = objectReader.readPackedInt();
			if (index < 0) {
				final ObjectReader.Cache.Slot<int[]> slot = readerCache.alloc();
				final int length = index > -0x8000 ? -index : objectReader.readPackedInt() + 0x8000;
				final int[] object = objectReader.readInts(length);
				slot.put(object);
				return object;
			} else {
				return readerCache.get(index);
			}
		} catch (IllegalStateException e) {
			throw new IOException("Invalid input data.", e);
		}
	}
}

final class PrimitiveLongArrayProcessor implements ObjectProcessor<long[]> {
	private static final long[] EMPTY = new long[0];

	@Nonnull
	@Override
	public List<long[]> preloadCache() {
		return Arrays.asList(null, EMPTY);
	}

	@Nonnull
	@Override
	public Class<long[]> getObjectClass() {
		return long[].class;
	}

	@Override
	public void liquify(@Nonnull ObjectWriter objectWriter, @Nullable ObjectWriter.Cache<long[]> writerCache, @Nullable long[] object) throws IOException {
		if (writerCache == null) throw new IllegalStateException("Object cache not available.");

		final int length = object != null ? object.length : -1;
		final int index = writerCache.putIfAbsent(length > 0 ? object : EMPTY);
		if (index >= 0) {
			objectWriter.writePackedInt(index);
		} else {
			assert length > 0;
			if (length < 0x8000) {
				objectWriter.writePackedInt(-length);
			} else {
				objectWriter.writePackedInt(-0x8000);
				objectWriter.writePackedInt(length - 0x8000);
			}
			objectWriter.writeLongs(object);
		}
	}

	@Nullable
	@Override
	public long[] solidify(@Nonnull ObjectReader objectReader, @Nullable ObjectReader.Cache<long[]> readerCache) throws IOException {
		if (readerCache == null) throw new IllegalStateException("Object cache not available.");

		try {
			final int index = objectReader.readPackedInt();
			if (index < 0) {
				final ObjectReader.Cache.Slot<long[]> slot = readerCache.alloc();
				final int length = index > -0x8000 ? -index : objectReader.readPackedInt() + 0x8000;
				final long[] object = objectReader.readLongs(length);
				slot.put(object);
				return object;
			} else {
				return readerCache.get(index);
			}
		} catch (IllegalStateException e) {
			throw new IOException("Invalid input data.", e);
		}
	}
}

final class PrimitiveFloatArrayProcessor implements ObjectProcessor<float[]> {
	private static final float[] EMPTY = new float[0];

	@Nonnull
	@Override
	public List<float[]> preloadCache() {
		return Arrays.asList(null, EMPTY);
	}

	@Nonnull
	@Override
	public Class<float[]> getObjectClass() {
		return float[].class;
	}

	@Override
	public void liquify(@Nonnull ObjectWriter objectWriter, @Nullable ObjectWriter.Cache<float[]> writerCache, @Nullable float[] object) throws IOException {
		if (writerCache == null) throw new IllegalStateException("Object cache not available.");

		final int length = object != null ? object.length : -1;
		final int index = writerCache.putIfAbsent(length > 0 ? object : EMPTY);
		if (index >= 0) {
			objectWriter.writePackedInt(index);
		} else {
			assert length > 0;
			if (length < 0x8000) {
				objectWriter.writePackedInt(-length);
			} else {
				objectWriter.writePackedInt(-0x8000);
				objectWriter.writePackedInt(length - 0x8000);
			}
			objectWriter.writeFloats(object);
		}
	}

	@Nullable
	@Override
	public float[] solidify(@Nonnull ObjectReader objectReader, @Nullable ObjectReader.Cache<float[]> readerCache) throws IOException {
		if (readerCache == null) throw new IllegalStateException("Object cache not available.");

		try {
			final int index = objectReader.readPackedInt();
			if (index < 0) {
				final ObjectReader.Cache.Slot<float[]> slot = readerCache.alloc();
				final int length = index > -0x8000 ? -index : objectReader.readPackedInt() + 0x8000;
				final float[] object = objectReader.readFloats(length);
				slot.put(object);
				return object;
			} else {
				return readerCache.get(index);
			}
		} catch (IllegalStateException e) {
			throw new IOException("Invalid input data.", e);
		}
	}
}

final class PrimitiveDoubleArrayProcessor implements ObjectProcessor<double[]> {
	private static final double[] EMPTY = new double[0];

	@Nonnull
	@Override
	public List<double[]> preloadCache() {
		return Arrays.asList(null, EMPTY);
	}

	@Nonnull
	@Override
	public Class<double[]> getObjectClass() {
		return double[].class;
	}

	@Override
	public void liquify(@Nonnull ObjectWriter objectWriter, @Nullable ObjectWriter.Cache<double[]> writerCache, @Nullable double[] object) throws IOException {
		if (writerCache == null) throw new IllegalStateException("Object cache not available.");

		final int length = object != null ? object.length : -1;
		final int index = writerCache.putIfAbsent(length > 0 ? object : EMPTY);
		if (index >= 0) {
			objectWriter.writePackedInt(index);
		} else {
			assert length > 0;
			if (length < 0x8000) {
				objectWriter.writePackedInt(-length);
			} else {
				objectWriter.writePackedInt(-0x8000);
				objectWriter.writePackedInt(length - 0x8000);
			}
			objectWriter.writeDoubles(object);
		}
	}

	@Nullable
	@Override
	public double[] solidify(@Nonnull ObjectReader objectReader, @Nullable ObjectReader.Cache<double[]> readerCache) throws IOException {
		if (readerCache == null) throw new IllegalStateException("Object cache not available.");

		try {
			final int index = objectReader.readPackedInt();
			if (index < 0) {
				final ObjectReader.Cache.Slot<double[]> slot = readerCache.alloc();
				final int length = index > -0x8000 ? -index : objectReader.readPackedInt() + 0x8000;
				final double[] object = objectReader.readDoubles(length);
				slot.put(object);
				return object;
			} else {
				return readerCache.get(index);
			}
		} catch (IllegalStateException e) {
			throw new IOException("Invalid input data.", e);
		}
	}
}

final class PrimitiveBooleanArrayProcessor implements ObjectProcessor<boolean[]> {
	private static final boolean[] EMPTY = new boolean[0];

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
	public void liquify(@Nonnull ObjectWriter objectWriter, @Nullable ObjectWriter.Cache<boolean[]> writerCache, @Nullable boolean[] object) throws IOException {
		if (writerCache == null) throw new IllegalStateException("Object cache not available.");

		final int length = object != null ? object.length : -1;
		final int index = writerCache.putIfAbsent(length > 0 ? object : EMPTY);
		if (index >= 0) {
			objectWriter.writePackedInt(index);
		} else {
			assert length > 0;
			if (length < 0x8000) {
				objectWriter.writePackedInt(-length);
			} else {
				objectWriter.writePackedInt(-0x8000);
				objectWriter.writePackedInt(length - 0x8000);
			}
			objectWriter.writeBooleans(object);
		}
	}

	@Nullable
	@Override
	public boolean[] solidify(@Nonnull ObjectReader objectReader, @Nullable ObjectReader.Cache<boolean[]> readerCache) throws IOException {
		if (readerCache == null) throw new IllegalStateException("Object cache not available.");

		try {
			final int index = objectReader.readPackedInt();
			if (index < 0) {
				final ObjectReader.Cache.Slot<boolean[]> slot = readerCache.alloc();
				final int length = index > -0x8000 ? -index : objectReader.readPackedInt() + 0x8000;
				final boolean[] object = objectReader.readBooleans(length);
				slot.put(object);
				return object;
			} else {
				return readerCache.get(index);
			}
		} catch (IllegalStateException e) {
			throw new IOException("Invalid input data.", e);
		}
	}
}

final class PrimitiveCharArrayProcessor implements ObjectProcessor<char[]> {
	private static final char[] EMPTY = new char[0];

	@Nonnull
	@Override
	public List<char[]> preloadCache() {
		return Arrays.asList(null, EMPTY);
	}

	@Nonnull
	@Override
	public Class<char[]> getObjectClass() {
		return char[].class;
	}

	@Override
	public void liquify(@Nonnull ObjectWriter objectWriter, @Nullable ObjectWriter.Cache<char[]> writerCache, @Nullable char[] object) throws IOException {
		if (writerCache == null) throw new IllegalStateException("Object cache not available.");

		final int length = object != null ? object.length : -1;
		final int index = writerCache.putIfAbsent(length > 0 ? object : EMPTY);
		if (index >= 0) {
			objectWriter.writePackedInt(index);
		} else {
			assert length > 0;
			if (length < 0x8000) {
				objectWriter.writePackedInt(-length);
			} else {
				objectWriter.writePackedInt(-0x8000);
				objectWriter.writePackedInt(length - 0x8000);
			}
			objectWriter.writeChars(object);
		}
	}

	@Nullable
	@Override
	public char[] solidify(@Nonnull ObjectReader objectReader, @Nullable ObjectReader.Cache<char[]> readerCache) throws IOException {
		if (readerCache == null) throw new IllegalStateException("Object cache not available.");

		try {
			final int index = objectReader.readPackedInt();
			if (index < 0) {
				final ObjectReader.Cache.Slot<char[]> slot = readerCache.alloc();
				final int length = index > -0x8000 ? -index : objectReader.readPackedInt() + 0x8000;
				final char[] object = objectReader.readChars(length);
				slot.put(object);
				return object;
			} else {
				return readerCache.get(index);
			}
		} catch (IllegalStateException e) {
			throw new IOException("Invalid input data.", e);
		}
	}
}
