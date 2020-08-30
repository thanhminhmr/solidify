package mrmathami.solidify;

import mrmathami.annotation.Nonnull;
import mrmathami.annotation.Nullable;
import java.io.IOException;

final class PrimitiveByteArrayProcessor implements ObjectProcessor<byte[]> {
	@Nonnull private static final byte[] EMPTY = new byte[0];
	@Nonnull private static final byte[][] PRELOAD_OBJECTS = {null, EMPTY};

	@Nonnull
	@Override
	public byte[][] preloadCache() {
		return PRELOAD_OBJECTS;
	}

	@Nonnull
	@Override
	public Class<byte[]> getObjectClass() {
		return byte[].class;
	}

	@Override
	public void liquify(@Nonnull ObjectWriter objectWriter, @Nullable ObjectWriter.Cache<byte[]> writerCache, @Nullable byte[] array) throws IOException {
		if (writerCache == null) throw new IllegalStateException("Object cache not available.");

		final int length = array != null ? array.length : -1;
		final int index = writerCache.putIfAbsent(length > 0 ? array : EMPTY);
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
			objectWriter.writeBytes(array);
		}
	}

	@Nullable
	@Override
	public byte[] solidify(@Nonnull ObjectReader objectReader, @Nullable ObjectReader.Cache<byte[]> readerCache) throws IOException {
		if (readerCache == null) throw new IllegalStateException("Object cache not available.");

		try {
			final int index = objectReader.readPackedInt();
			if (index < 0) {
				final ObjectReader.CacheSlot<byte[]> slot = readerCache.alloc();
				final int length;
				if (index > -0x8000) {
					length = -index;
				} else {
					final int lengthExt = objectReader.readPackedInt();
					if (lengthExt < 0) throw new IOException("Invalid input data.");
					length = lengthExt + 0x8000;
					if (length < 0) throw new IOException("Invalid input data.");
				}
				final byte[] array = objectReader.readBytes(length);
				slot.put(array);
				return array;
			} else {
				return readerCache.get(index);
			}
		} catch (ObjectReader.CacheException e) {
			throw new IOException("Invalid input data.", e);
		}
	}
}

final class PrimitiveShortArrayProcessor implements ObjectProcessor<short[]> {
	@Nonnull private static final short[] EMPTY = new short[0];
	@Nonnull private static final short[][] PRELOAD_OBJECTS = {null, EMPTY};

	@Nonnull
	@Override
	public short[][] preloadCache() {
		return PRELOAD_OBJECTS;
	}

	@Nonnull
	@Override
	public Class<short[]> getObjectClass() {
		return short[].class;
	}

	@Override
	public void liquify(@Nonnull ObjectWriter objectWriter, @Nullable ObjectWriter.Cache<short[]> writerCache, @Nullable short[] array) throws IOException {
		if (writerCache == null) throw new IllegalStateException("Object cache not available.");

		final int length = array != null ? array.length : -1;
		final int index = writerCache.putIfAbsent(length > 0 ? array : EMPTY);
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
			objectWriter.writeShorts(array);
		}
	}

	@Nullable
	@Override
	public short[] solidify(@Nonnull ObjectReader objectReader, @Nullable ObjectReader.Cache<short[]> readerCache) throws IOException {
		if (readerCache == null) throw new IllegalStateException("Object cache not available.");

		try {
			final int index = objectReader.readPackedInt();
			if (index < 0) {
				final ObjectReader.CacheSlot<short[]> slot = readerCache.alloc();
				final int length;
				if (index > -0x8000) {
					length = -index;
				} else {
					final int lengthExt = objectReader.readPackedInt();
					if (lengthExt < 0) throw new IOException("Invalid input data.");
					length = lengthExt + 0x8000;
					if (length < 0) throw new IOException("Invalid input data.");
				}
				final short[] array = objectReader.readShorts(length);
				slot.put(array);
				return array;
			} else {
				return readerCache.get(index);
			}
		} catch (ObjectReader.CacheException e) {
			throw new IOException("Invalid input data.", e);
		}
	}
}

final class PrimitiveIntArrayProcessor implements ObjectProcessor<int[]> {
	@Nonnull private static final int[] EMPTY = new int[0];
	@Nonnull private static final int[][] PRELOAD_OBJECTS = {null, EMPTY};

	@Nonnull
	@Override
	public int[][] preloadCache() {
		return PRELOAD_OBJECTS;
	}

	@Nonnull
	@Override
	public Class<int[]> getObjectClass() {
		return int[].class;
	}

	@Override
	public void liquify(@Nonnull ObjectWriter objectWriter, @Nullable ObjectWriter.Cache<int[]> writerCache, @Nullable int[] array) throws IOException {
		if (writerCache == null) throw new IllegalStateException("Object cache not available.");

		final int length = array != null ? array.length : -1;
		final int index = writerCache.putIfAbsent(length > 0 ? array : EMPTY);
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
			objectWriter.writeInts(array);
		}
	}

	@Nullable
	@Override
	public int[] solidify(@Nonnull ObjectReader objectReader, @Nullable ObjectReader.Cache<int[]> readerCache) throws IOException {
		if (readerCache == null) throw new IllegalStateException("Object cache not available.");

		try {
			final int index = objectReader.readPackedInt();
			if (index < 0) {
				final ObjectReader.CacheSlot<int[]> slot = readerCache.alloc();
				final int length;
				if (index > -0x8000) {
					length = -index;
				} else {
					final int lengthExt = objectReader.readPackedInt();
					if (lengthExt < 0) throw new IOException("Invalid input data.");
					length = lengthExt + 0x8000;
					if (length < 0) throw new IOException("Invalid input data.");
				}
				final int[] array = objectReader.readInts(length);
				slot.put(array);
				return array;
			} else {
				return readerCache.get(index);
			}
		} catch (ObjectReader.CacheException e) {
			throw new IOException("Invalid input data.", e);
		}
	}
}

final class PrimitiveLongArrayProcessor implements ObjectProcessor<long[]> {
	@Nonnull private static final long[] EMPTY = new long[0];
	@Nonnull private static final long[][] PRELOAD_OBJECTS = {null, EMPTY};

	@Nonnull
	@Override
	public long[][] preloadCache() {
		return PRELOAD_OBJECTS;
	}

	@Nonnull
	@Override
	public Class<long[]> getObjectClass() {
		return long[].class;
	}

	@Override
	public void liquify(@Nonnull ObjectWriter objectWriter, @Nullable ObjectWriter.Cache<long[]> writerCache, @Nullable long[] array) throws IOException {
		if (writerCache == null) throw new IllegalStateException("Object cache not available.");

		final int length = array != null ? array.length : -1;
		final int index = writerCache.putIfAbsent(length > 0 ? array : EMPTY);
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
			objectWriter.writeLongs(array);
		}
	}

	@Nullable
	@Override
	public long[] solidify(@Nonnull ObjectReader objectReader, @Nullable ObjectReader.Cache<long[]> readerCache) throws IOException {
		if (readerCache == null) throw new IllegalStateException("Object cache not available.");

		try {
			final int index = objectReader.readPackedInt();
			if (index < 0) {
				final ObjectReader.CacheSlot<long[]> slot = readerCache.alloc();
				final int length;
				if (index > -0x8000) {
					length = -index;
				} else {
					final int lengthExt = objectReader.readPackedInt();
					if (lengthExt < 0) throw new IOException("Invalid input data.");
					length = lengthExt + 0x8000;
					if (length < 0) throw new IOException("Invalid input data.");
				}
				final long[] array = objectReader.readLongs(length);
				slot.put(array);
				return array;
			} else {
				return readerCache.get(index);
			}
		} catch (ObjectReader.CacheException e) {
			throw new IOException("Invalid input data.", e);
		}
	}
}

final class PrimitiveFloatArrayProcessor implements ObjectProcessor<float[]> {
	@Nonnull private static final float[] EMPTY = new float[0];
	@Nonnull private static final float[][] PRELOAD_OBJECTS = {null, EMPTY};

	@Nonnull
	@Override
	public float[][] preloadCache() {
		return PRELOAD_OBJECTS;
	}

	@Nonnull
	@Override
	public Class<float[]> getObjectClass() {
		return float[].class;
	}

	@Override
	public void liquify(@Nonnull ObjectWriter objectWriter, @Nullable ObjectWriter.Cache<float[]> writerCache, @Nullable float[] array) throws IOException {
		if (writerCache == null) throw new IllegalStateException("Object cache not available.");

		final int length = array != null ? array.length : -1;
		final int index = writerCache.putIfAbsent(length > 0 ? array : EMPTY);
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
			objectWriter.writeFloats(array);
		}
	}

	@Nullable
	@Override
	public float[] solidify(@Nonnull ObjectReader objectReader, @Nullable ObjectReader.Cache<float[]> readerCache) throws IOException {
		if (readerCache == null) throw new IllegalStateException("Object cache not available.");

		try {
			final int index = objectReader.readPackedInt();
			if (index < 0) {
				final ObjectReader.CacheSlot<float[]> slot = readerCache.alloc();
				final int length;
				if (index > -0x8000) {
					length = -index;
				} else {
					final int lengthExt = objectReader.readPackedInt();
					if (lengthExt < 0) throw new IOException("Invalid input data.");
					length = lengthExt + 0x8000;
					if (length < 0) throw new IOException("Invalid input data.");
				}
				final float[] array = objectReader.readFloats(length);
				slot.put(array);
				return array;
			} else {
				return readerCache.get(index);
			}
		} catch (ObjectReader.CacheException e) {
			throw new IOException("Invalid input data.", e);
		}
	}
}

final class PrimitiveDoubleArrayProcessor implements ObjectProcessor<double[]> {
	@Nonnull private static final double[] EMPTY = new double[0];
	@Nonnull private static final double[][] PRELOAD_OBJECTS = {null, EMPTY};

	@Nonnull
	@Override
	public double[][] preloadCache() {
		return PRELOAD_OBJECTS;
	}

	@Nonnull
	@Override
	public Class<double[]> getObjectClass() {
		return double[].class;
	}

	@Override
	public void liquify(@Nonnull ObjectWriter objectWriter, @Nullable ObjectWriter.Cache<double[]> writerCache, @Nullable double[] array) throws IOException {
		if (writerCache == null) throw new IllegalStateException("Object cache not available.");

		final int length = array != null ? array.length : -1;
		final int index = writerCache.putIfAbsent(length > 0 ? array : EMPTY);
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
			objectWriter.writeDoubles(array);
		}
	}

	@Nullable
	@Override
	public double[] solidify(@Nonnull ObjectReader objectReader, @Nullable ObjectReader.Cache<double[]> readerCache) throws IOException {
		if (readerCache == null) throw new IllegalStateException("Object cache not available.");

		try {
			final int index = objectReader.readPackedInt();
			if (index < 0) {
				final ObjectReader.CacheSlot<double[]> slot = readerCache.alloc();
				final int length;
				if (index > -0x8000) {
					length = -index;
				} else {
					final int lengthExt = objectReader.readPackedInt();
					if (lengthExt < 0) throw new IOException("Invalid input data.");
					length = lengthExt + 0x8000;
					if (length < 0) throw new IOException("Invalid input data.");
				}
				final double[] array = objectReader.readDoubles(length);
				slot.put(array);
				return array;
			} else {
				return readerCache.get(index);
			}
		} catch (ObjectReader.CacheException e) {
			throw new IOException("Invalid input data.", e);
		}
	}
}

final class PrimitiveBooleanArrayProcessor implements ObjectProcessor<boolean[]> {
	@Nonnull private static final boolean[] EMPTY = new boolean[0];
	@Nonnull private static final boolean[][] PRELOAD_OBJECTS = {null, EMPTY};

	@Nonnull
	@Override
	public boolean[][] preloadCache() {
		return PRELOAD_OBJECTS;
	}

	@Nonnull
	@Override
	public Class<boolean[]> getObjectClass() {
		return boolean[].class;
	}

	@Override
	public void liquify(@Nonnull ObjectWriter objectWriter, @Nullable ObjectWriter.Cache<boolean[]> writerCache, @Nullable boolean[] array) throws IOException {
		if (writerCache == null) throw new IllegalStateException("Object cache not available.");

		final int length = array != null ? array.length : -1;
		final int index = writerCache.putIfAbsent(length > 0 ? array : EMPTY);
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
			objectWriter.writePackedBooleans(array);
		}
	}

	@Nullable
	@Override
	public boolean[] solidify(@Nonnull ObjectReader objectReader, @Nullable ObjectReader.Cache<boolean[]> readerCache) throws IOException {
		if (readerCache == null) throw new IllegalStateException("Object cache not available.");

		try {
			final int index = objectReader.readPackedInt();
			if (index < 0) {
				final ObjectReader.CacheSlot<boolean[]> slot = readerCache.alloc();
				final int length;
				if (index > -0x8000) {
					length = -index;
				} else {
					final int lengthExt = objectReader.readPackedInt();
					if (lengthExt < 0) throw new IOException("Invalid input data.");
					length = lengthExt + 0x8000;
					if (length < 0) throw new IOException("Invalid input data.");
				}
				final boolean[] array = objectReader.readPackedBooleans(length);
				slot.put(array);
				return array;
			} else {
				return readerCache.get(index);
			}
		} catch (ObjectReader.CacheException e) {
			throw new IOException("Invalid input data.", e);
		}
	}
}

final class PrimitiveCharArrayProcessor implements ObjectProcessor<char[]> {
	@Nonnull private static final char[] EMPTY = new char[0];
	@Nonnull private static final char[][] PRELOAD_OBJECTS = {null, EMPTY};

	@Nonnull
	@Override
	public char[][] preloadCache() {
		return PRELOAD_OBJECTS;
	}

	@Nonnull
	@Override
	public Class<char[]> getObjectClass() {
		return char[].class;
	}

	@Override
	public void liquify(@Nonnull ObjectWriter objectWriter, @Nullable ObjectWriter.Cache<char[]> writerCache, @Nullable char[] array) throws IOException {
		if (writerCache == null) throw new IllegalStateException("Object cache not available.");

		final int length = array != null ? array.length : -1;
		final int index = writerCache.putIfAbsent(length > 0 ? array : EMPTY);
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
			objectWriter.writeChars(array);
		}
	}

	@Nullable
	@Override
	public char[] solidify(@Nonnull ObjectReader objectReader, @Nullable ObjectReader.Cache<char[]> readerCache) throws IOException {
		if (readerCache == null) throw new IllegalStateException("Object cache not available.");

		try {
			final int index = objectReader.readPackedInt();
			if (index < 0) {
				final ObjectReader.CacheSlot<char[]> slot = readerCache.alloc();
				final int length;
				if (index > -0x8000) {
					length = -index;
				} else {
					final int lengthExt = objectReader.readPackedInt();
					if (lengthExt < 0) throw new IOException("Invalid input data.");
					length = lengthExt + 0x8000;
					if (length < 0) throw new IOException("Invalid input data.");
				}
				final char[] array = objectReader.readChars(length);
				slot.put(array);
				return array;
			} else {
				return readerCache.get(index);
			}
		} catch (ObjectReader.CacheException e) {
			throw new IOException("Invalid input data.", e);
		}
	}
}
