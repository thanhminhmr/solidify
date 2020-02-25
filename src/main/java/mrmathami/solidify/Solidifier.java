package mrmathami.solidify;

import mrmathami.util.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		@Nonnull private final Map<Class<?>, Pair<ObjectProcessor<?>, List<Object>>> processorMap = new HashMap<>();
		@Nonnull private final InputStream stream;

		public ObjectReaderImpl(@Nonnull List<ObjectProcessor<?>> objectProcessors, @Nonnull InputStream stream) {
			this.stream = stream instanceof BufferedInputStream ? stream : new BufferedInputStream(stream);

			for (final ObjectProcessor<?> objectProcessor : objectProcessors) {
				processorMap.put(objectProcessor.getObjectClass(),
						Pair.immutableOf(objectProcessor, objectProcessor.getCacheMode() >= 0 ? new ArrayList<>() : null));
			}
		}

		@Override
		public byte readByte() throws IOException {
			return (byte) readUnsignedByte();
		}

		@Override
		public byte[] readBytes(int size) throws IOException {
			if (size <= 0) throw new IllegalArgumentException("Size must be positive.");
			return readFromStream(size);
		}

		@Override
		public int readUnsignedByte() throws IOException {
			final int value = stream.read();
			if (value < 0) throw new EOFException();
			return value;
		}

		@Override
		public int[] readUnsignedBytes(int size) throws IOException {
			if (size <= 0) throw new IllegalArgumentException("Size must be positive.");
			final int[] values = new int[size];
			for (int i = 0; i < size; i++) values[i] = readUnsignedByte();
			return values;
		}

		@Override
		public short readShort() throws IOException {
			return (short) readUnsignedShort();
		}

		@Override
		public short[] readShorts(int size) throws IOException {
			if (size <= 0) throw new IllegalArgumentException("Size must be positive.");
			final short[] values = new short[size];
			for (int i = 0; i < size; i++) values[i] = readShort();
			return values;
		}

		@Override
		public int readUnsignedShort() throws IOException {
			return readUnsignedByte() | readUnsignedByte() << 8;
		}

		@Override
		public int[] readUnsignedShorts(int size) throws IOException {
			if (size <= 0) throw new IllegalArgumentException("Size must be positive.");
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

		@Override
		public short[] readPackedShorts(int size) throws IOException {
			if (size <= 0) throw new IllegalArgumentException("Size must be positive.");
			final short[] values = new short[size];
			for (int i = 0; i < size; i++) values[i] = readPackedShort();
			return values;
		}

		@Override
		public int readInt() throws IOException {
			return readUnsignedByte() | readUnsignedByte() << 8
					| readUnsignedByte() << 16 | readUnsignedByte() << 24;
		}

		@Override
		public int[] readInts(int size) throws IOException {
			if (size <= 0) throw new IllegalArgumentException("Size must be positive.");
			final int[] values = new int[size];
			for (int i = 0; i < size; i++) values[i] = readInt();
			return values;
		}

		@Override
		public long readUnsignedInt() throws IOException {
			return readUnsignedByte() | readUnsignedByte() << 8
					| readUnsignedByte() << 16 | (long) readUnsignedByte() << 24;
		}

		@Override
		public long[] readUnsignedInts(int size) throws IOException {
			if (size <= 0) throw new IllegalArgumentException("Size must be positive.");
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

		@Override
		public int[] readPackedInts(int size) throws IOException {
			if (size <= 0) throw new IllegalArgumentException("Size must be positive.");
			final int[] values = new int[size];
			for (int i = 0; i < size; i++) values[i] = readPackedInt();
			return values;
		}

		@Override
		public long readLong() throws IOException {
			return readUnsignedInt() | (long) readInt() << 32;
		}

		@Override
		public long[] readLongs(int size) throws IOException {
			if (size <= 0) throw new IllegalArgumentException("Size must be positive.");
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

		@Override
		public long[] readPackedLongs(int size) throws IOException {
			if (size <= 0) throw new IllegalArgumentException("Size must be positive.");
			final long[] values = new long[size];
			for (int i = 0; i < size; i++) values[i] = readPackedLong();
			return values;
		}

		@Override
		public float readFloat() throws IOException {
			return Float.intBitsToFloat(readInt());
		}

		@Override
		public float[] readFloats(int size) throws IOException {
			if (size <= 0) throw new IllegalArgumentException("Size must be positive.");
			final float[] values = new float[size];
			for (int i = 0; i < size; i++) values[i] = readFloat();
			return values;
		}

		@Override
		public double readDouble() throws IOException {
			return Double.longBitsToDouble(readLong());
		}

		@Override
		public double[] readDoubles(int size) throws IOException {
			if (size <= 0) throw new IllegalArgumentException("Size must be positive.");
			final double[] values = new double[size];
			for (int i = 0; i < size; i++) values[i] = readDouble();
			return values;
		}

		@Override
		public boolean readBoolean() throws IOException {
			final int value = readUnsignedByte();
			if (value == 0x00) return false;
			if (value == 0x80) return true;
			throw new IOException("Invalid input data.");
		}

		@Nonnull
		@Override
		public boolean[] readBooleans(int size) throws IOException {
			if (size <= 0) throw new IllegalArgumentException("Size must be positive.");

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

		@Override
		public char[] readChars(int size) throws IOException {
			if (size <= 0) throw new IllegalArgumentException("Size must be positive.");
			final char[] values = new char[size];
			for (int i = 0; i < size; i++) values[i] = readChar();
			return values;
		}

		@Nonnull
		@Override
		public String readUtf8() throws IOException {
			final int length = readPackedInt();
			if (length < 0) throw new IOException("Invalid input data.");
			if (length == 0) return "";
			final byte[] bytes = readFromStream(length);
			try {
				return new String(bytes, StandardCharsets.UTF_8);
			} catch (IllegalArgumentException e) {
				throw new IOException("Invalid input data.", e);
			}
		}

		@Nullable
		@Override
		public <E> E readObject(@Nonnull Class<E> objectClass) throws IOException {
			final Pair<ObjectProcessor<?>, List<Object>> pair = processorMap.get(objectClass);
			if (pair == null) throw new IllegalArgumentException("Unregistered class: " + objectClass.getName());
			@SuppressWarnings("unchecked") final ObjectProcessor<E> objectProcessor = (ObjectProcessor<E>) pair.getA();
			if (objectProcessor.getCacheMode() < 0) return readBoolean() ? objectProcessor.solidify(this) : null;
			final int identity = readPackedInt();
			if (identity < 0) return null;
			@SuppressWarnings("unchecked") final List<E> objectList = (List<E>) pair.getB();
			if (identity < objectList.size()) return objectList.get(identity);
			final E object = objectProcessor.solidify(this);
			if (identity != objectList.size()) throw new IOException("Invalid input data.");
			objectList.add(object);
			return object;
		}

		@Nonnull
		@Override
		public <E> E[] readObjects(@Nonnull Class<E> objectClass, int size) throws IOException {
			if (size <= 0) throw new IllegalArgumentException("Size must be positive.");
			@SuppressWarnings("unchecked") final E[] objects = (E[]) new Object[size];

			final Pair<ObjectProcessor<?>, List<Object>> pair = processorMap.get(objectClass);
			if (pair == null) throw new IllegalArgumentException("Unregistered class: " + objectClass.getName());
			@SuppressWarnings("unchecked") final ObjectProcessor<E> objectProcessor = (ObjectProcessor<E>) pair.getA();
			if (objectProcessor.getCacheMode() < 0) {
				for (int i = 0; i < objects.length; i++) {
					objects[i] = readBoolean() ? objectProcessor.solidify(this) : null;
				}
			} else {
				@SuppressWarnings("unchecked") final List<E> objectList = (List<E>) pair.getB();
				for (int i = 0; i < objects.length; i++) {
					final int identity = readPackedInt();
					if (identity >= 0) {
						if (identity >= objectList.size()) {
							final E object = objectProcessor.solidify(this);
							if (identity != objectList.size()) throw new IOException("Invalid input data.");
							objectList.add(object);
							objects[i] = object;
						} else {
							objects[i] = objectList.get(identity);
						}
					} else {
						objects[i] = null;
					}
				}
			}
			return objects;
		}

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
	}
}

