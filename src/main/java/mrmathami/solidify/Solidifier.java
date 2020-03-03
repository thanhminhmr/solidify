package mrmathami.solidify;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import mrmathami.util.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class Solidifier {
	@Nonnull
	private final ObjectReader objectReader;

	public Solidifier(@Nonnull List<ObjectProcessor<?>> objectProcessors, @Nonnull InputStream stream) {
		this.objectReader = new ObjectReaderImpl(objectProcessors, stream);
	}

	@Nullable
	public <E> E solidify(@Nonnull Class<E> objectClass) throws IOException {
		return objectReader.readObject(objectClass);
	}

	private static final class ObjectReaderImpl implements ObjectReader {
		@Nonnull private final Map<Class<?>, Pair<ObjectProcessor<?>, Cache<?>>> classMap = new HashMap<>();
		@Nonnull private final InputStream stream;

		public ObjectReaderImpl(@Nonnull List<ObjectProcessor<?>> objectProcessors, @Nonnull InputStream stream) {
			this.stream = stream instanceof BufferedInputStream ? stream : new BufferedInputStream(stream);

			for (final ObjectProcessor<?> objectProcessor : objectProcessors) {
				final Class<?> objectClass = objectProcessor.getObjectClass();
				if (classMap.put(objectClass, Pair.immutableOf(objectProcessor, createCache(objectProcessor))) != null) {
					throwAlreadyRegisteredClass(objectClass);
				}
			}
		}

		private static void throwInvalidInput() throws IOException {
			throw new IOException("Invalid input data.");
		}

		private static void throwInvalidSize() {
			throw new IllegalArgumentException("Size must be positive.");
		}

		private static void throwUnregisteredClass(@Nonnull Class<?> objectClass) {
			throw new IllegalArgumentException("Unregistered class: " + objectClass.getName());
		}

		private static void throwAlreadyRegisteredClass(@Nonnull Class<?> objectClass) {
			throw new IllegalArgumentException("Already registered class: " + objectClass.getName());
		}

		@Nullable
		private static <E> Cache<E> createCache(@Nonnull ObjectProcessor<E> objectProcessor) {
			return objectProcessor.usingCache()
					? new CacheImpl<>(objectProcessor.usingEqualityCache(), objectProcessor.preloadCache())
					: null;
		}

		// region //====== Basic read ======

		@Override
		public byte readByte() throws IOException {
			return (byte) readUnsignedByte();
		}

		@Nonnull
		@Override
		public byte[] readBytes(int size) throws IOException {
			if (size <= 0) throwInvalidSize();
			return readFromStream(size);
		}

		@Override
		public int readUnsignedByte() throws IOException {
			final int value = stream.read();
			if (value < 0) throw new EOFException();
			return value;
		}

		@Nonnull
		@Override
		public int[] readUnsignedBytes(int size) throws IOException {
			if (size <= 0) throwInvalidSize();
			final int[] values = new int[size];
			for (int i = 0; i < size; i++) values[i] = readUnsignedByte();
			return values;
		}

		@Override
		public short readShort() throws IOException {
			return (short) readUnsignedShort();
		}

		@Nonnull
		@Override
		public short[] readShorts(int size) throws IOException {
			if (size <= 0) throwInvalidSize();
			final short[] values = new short[size];
			for (int i = 0; i < size; i++) values[i] = readShort();
			return values;
		}

		@Override
		public int readUnsignedShort() throws IOException {
			return readUnsignedByte() | readUnsignedByte() << 8;
		}

		@Nonnull
		@Override
		public int[] readUnsignedShorts(int size) throws IOException {
			if (size <= 0) throwInvalidSize();
			final int[] values = new int[size];
			for (int i = 0; i < size; i++) values[i] = readUnsignedShort();
			return values;
		}

		@Override
		public short readPackedShort() throws IOException {
			final int low = readUnsignedByte();
			if (low < 0x80) return (short) low;
			final int high = readUnsignedByte();
			return (short) (high > 0 ? high << 7 | low & 0x7F : low);
		}

		@Nonnull
		@Override
		public short[] readPackedShorts(int size) throws IOException {
			if (size <= 0) throwInvalidSize();
			final short[] values = new short[size];
			for (int i = 0; i < size; i++) values[i] = readPackedShort();
			return values;
		}

		@Override
		public int readInt() throws IOException {
			return readUnsignedByte() | readUnsignedByte() << 8
					| readUnsignedByte() << 16 | readUnsignedByte() << 24;
		}

		@Nonnull
		@Override
		public int[] readInts(int size) throws IOException {
			if (size <= 0) throwInvalidSize();
			final int[] values = new int[size];
			for (int i = 0; i < size; i++) values[i] = readInt();
			return values;
		}

		@Override
		public long readUnsignedInt() throws IOException {
			return readUnsignedByte() | readUnsignedByte() << 8
					| readUnsignedByte() << 16 | (long) readUnsignedByte() << 24;
		}

		@Nonnull
		@Override
		public long[] readUnsignedInts(int size) throws IOException {
			if (size <= 0) throwInvalidSize();
			final long[] values = new long[size];
			for (int i = 0; i < size; i++) values[i] = readUnsignedInt();
			return values;
		}

		@Override
		public int readPackedInt() throws IOException {
			final int low = readUnsignedShort();
			if (low < 0x8000) return low;
			final int high = readUnsignedShort();
			return high > 0 ? high << 15 | low & 0x7FFF : (short) low;
		}

		@Nonnull
		@Override
		public int[] readPackedInts(int size) throws IOException {
			if (size <= 0) throwInvalidSize();
			final int[] values = new int[size];
			for (int i = 0; i < size; i++) values[i] = readPackedInt();
			return values;
		}

		@Override
		public long readLong() throws IOException {
			return readUnsignedInt() | (long) readInt() << 32;
		}

		@Nonnull
		@Override
		public long[] readLongs(int size) throws IOException {
			if (size <= 0) throwInvalidSize();
			final long[] values = new long[size];
			for (int i = 0; i < size; i++) values[i] = readLong();
			return values;
		}

		@Override
		public long readPackedLong() throws IOException {
			final int low = readInt();
			if (low >= 0) return low;
			final long high = readUnsignedInt();
			return high > 0 ? high << 31 | low & 0x7FFFFFFF : low;
		}

		@Nonnull
		@Override
		public long[] readPackedLongs(int size) throws IOException {
			if (size <= 0) throwInvalidSize();
			final long[] values = new long[size];
			for (int i = 0; i < size; i++) values[i] = readPackedLong();
			return values;
		}

		@Override
		public float readFloat() throws IOException {
			return Float.intBitsToFloat(readInt());
		}

		@Nonnull
		@Override
		public float[] readFloats(int size) throws IOException {
			if (size <= 0) throwInvalidSize();
			final float[] values = new float[size];
			for (int i = 0; i < size; i++) values[i] = readFloat();
			return values;
		}

		@Override
		public float readPackedFloat() throws IOException {
			// 0111 1111 1000 0000 positive infinity
			// 1111 1111 1000 0000 negative infinity
			// 0111 1111 1100 0000 NaN
			// ?000 0000 0??? ???? \
			// ?... .... .??? ????  | normal double
			// ?111 1111 0??? ???? /
			final int leadingBits = readUnsignedShort();
			if (leadingBits == 0x7F80) return Float.POSITIVE_INFINITY;
			if (leadingBits == 0xFF80) return Float.NEGATIVE_INFINITY;
			if (leadingBits == 0x7FC0) return Float.NaN;
			if ((leadingBits & 0x7F80) == 0x7F80) throwInvalidInput();
			return Float.intBitsToFloat(leadingBits << 16 | readUnsignedShort());
		}

		@Nonnull
		@Override
		public float[] readPackedFloats(int size) throws IOException {
			if (size <= 0) throwInvalidSize();
			final float[] values = new float[size];
			for (int i = 0; i < size; i++) values[i] = readPackedFloat();
			return values;
		}

		@Override
		public double readDouble() throws IOException {
			return Double.longBitsToDouble(readLong());
		}

		@Nonnull
		@Override
		public double[] readDoubles(int size) throws IOException {
			if (size <= 0) throwInvalidSize();
			final double[] values = new double[size];
			for (int i = 0; i < size; i++) values[i] = readDouble();
			return values;
		}

		@Override
		public double readPackedDouble() throws IOException {
			// 0111 1111 1111 0000 positive infinity
			// 1111 1111 1111 0000 negative infinity
			// 0111 1111 1111 1000 NaN
			// ?000 0000 0000 ???? \
			// ?... .... .... ????  | normal double
			// ?111 1111 1110 ???? /
			final int leadingBits = readUnsignedShort();
			if (leadingBits == 0x7FF0) return Double.POSITIVE_INFINITY;
			if (leadingBits == 0xFFF0) return Double.NEGATIVE_INFINITY;
			if (leadingBits == 0x7FF8) return Double.NaN;
			if ((leadingBits & 0x7FF0) == 0x7FF0) throwInvalidInput();
			return Double.longBitsToDouble(((long) (leadingBits << 16 | readUnsignedShort())) << 32 | readUnsignedInt());
		}

		@Nonnull
		@Override
		public double[] readPackedDoubles(int size) throws IOException {
			if (size <= 0) throwInvalidSize();
			final double[] values = new double[size];
			for (int i = 0; i < size; i++) values[i] = readPackedDouble();
			return values;
		}

		@Override
		public boolean readBoolean() throws IOException {
			final int value = readUnsignedByte();
			if (value != 0x00 && value != 0x80) throwInvalidInput();
			return value != 0x00;
		}

		@Nonnull
		@Override
		public boolean[] readBooleans(int size) throws IOException {
			if (size <= 0) throwInvalidSize();

			final int length = size + 7 >> 3;
			final byte[] buffer = readFromStream(length);
			final boolean[] values = new boolean[size];
			int vi = 7, bi = 0;
			while (vi < size) {
				final byte packed = buffer[bi++];
				values[vi - 7] = (packed & 0x80) != 0;
				values[vi - 6] = (packed & 0x40) != 0;
				values[vi - 5] = (packed & 0x20) != 0;
				values[vi - 4] = (packed & 0x10) != 0;
				values[vi - 3] = (packed & 0x08) != 0;
				values[vi - 2] = (packed & 0x04) != 0;
				values[vi - 1] = (packed & 0x02) != 0;
				values[vi] = (packed & 0x01) != 0;
				vi += 8;
			}
			if (bi < length) {
				vi -= 7;
				byte value = buffer[bi];
				do {
					values[vi++] = (value & 0x80) != 0;
					value += value;
				} while (vi < size);
			}
			return values;
		}

		@Override
		public char readChar() throws IOException {
			return (char) readUnsignedShort();
		}

		@Nonnull
		@Override
		public char[] readChars(int size) throws IOException {
			if (size <= 0) throwInvalidSize();
			final char[] values = new char[size];
			for (int i = 0; i < size; i++) values[i] = readChar();
			return values;
		}

		// endregion

		@Nullable
		@Override
		public <E> E readObject(@Nonnull Class<E> objectClass) throws IOException {
			final Pair<ObjectProcessor<?>, Cache<?>> pair = classMap.get(objectClass);
			if (pair == null) throwUnregisteredClass(objectClass);
			@SuppressWarnings("unchecked") final ObjectProcessor<E> objectProcessor = (ObjectProcessor<E>) pair.getA();
			@SuppressWarnings("unchecked") final Cache<E> readerCache = (Cache<E>) pair.getB();
			return objectProcessor.solidify(this, readerCache);
		}

		@Nonnull
		@Override
		public <E> E[] readObjects(@Nonnull Class<E> objectClass, int size) throws IOException {
			if (size <= 0) throwInvalidSize();
			final Pair<ObjectProcessor<?>, Cache<?>> pair = classMap.get(objectClass);
			if (pair == null) throwUnregisteredClass(objectClass);
			@SuppressWarnings("unchecked") final ObjectProcessor<E> objectProcessor = (ObjectProcessor<E>) pair.getA();
			@SuppressWarnings("unchecked") final Cache<E> readerCache = (Cache<E>) pair.getB();
			@SuppressWarnings("unchecked") final E[] objects = (E[]) Array.newInstance(objectClass, size);
			for (int i = 0; i < size; i++) {
				objects[i] = objectProcessor.solidify(this, readerCache);
			}
			return objects;
		}

		@Nonnull
		private byte[] readFromStream(int length) throws IOException {
			assert length > 0;
			final byte[] bytes = new byte[length];
			int index = 0;
			do {
				final int count = stream.read(bytes, index, length - index);
				if (count < 0) throw new EOFException();
				index += count;
			} while (index < length);
			return bytes;
		}

		private static final class CacheImpl<E> extends ArrayList<E> implements Cache<E> {
			private final Set<E> set;

			private CacheImpl(boolean isEqualityCache, @Nullable List<E> preloadObjects) {
				this.set = isEqualityCache ? new ObjectOpenHashSet<>() : new ReferenceOpenHashSet<>();

				if (preloadObjects != null) {
					for (final E object : preloadObjects) {
						if (!set.add(object)) throw new IllegalStateException("Object already existed in cache.");
						add(object);
					}
				}
			}

			@Nullable
			@Override
			public E get(int index) throws IllegalStateException {
				try {
					return super.get(index);
				} catch (IndexOutOfBoundsException e) {
					throw new IllegalStateException("Invalid cache index.", e);
				}
			}

			@Nonnull
			@Override
			public Slot<E> alloc() {
				final int index = size();
				add(index, null);
				return object -> {
					if (!set.add(object)) throw new IllegalStateException("Object already existed in cache.");
					if (set(index, object) != null) throw new IllegalStateException("Cache slot already used.");
				};
			}
		}
	}
}

