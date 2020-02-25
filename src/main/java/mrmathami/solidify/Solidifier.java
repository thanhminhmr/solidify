package mrmathami.solidify;

import mrmathami.util.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
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
		@Nonnull private final Map<Class<?>, Pair<List<Object>, ObjectProcessor<?>>> processorMap = new HashMap<>();
		@Nonnull private final InputStream stream;

		public ObjectReaderImpl(@Nonnull List<ObjectProcessor<?>> objectProcessors, @Nonnull InputStream stream) {
			for (final ObjectProcessor<?> objectProcessor : objectProcessors) {
				processorMap.put(objectProcessor.getObjectClass(), Pair.immutableOf(new ArrayList<>(), objectProcessor));
			}
			this.stream = stream;
		}

		@Override
		public byte readByte() throws IOException {
			return (byte) readUnsignedByte();
		}

		@Override
		public int readUnsignedByte() throws IOException {
			final int value = stream.read();
			if (value < 0) throw new EOFException();
			return value;
		}

		@Override
		public short readShort() throws IOException {
			return (short) readUnsignedShort();
		}

		@Override
		public int readUnsignedShort() throws IOException {
			return readUnsignedByte() | readUnsignedByte() << 8;
		}

		@Override
		public int readInt() throws IOException {
			return readUnsignedByte() | readUnsignedByte() << 8
					| readUnsignedByte() << 16 | readUnsignedByte() << 24;
		}

		@Override
		public long readUnsignedInt() throws IOException {
			return readUnsignedByte() | readUnsignedByte() << 8
					| readUnsignedByte() << 16 | (long) readUnsignedByte() << 24;
		}

		@Override
		public int readPackedInt() throws IOException {
			final int low = readUnsignedShort();
			if (low < 0x8000) return low;
			final int high = readUnsignedShort();
			return high > 0 ? high << 15 | low & 0x7FFF : (short) low;
		}

		@Override
		public long readLong() throws IOException {
			return readUnsignedInt() | (long) readInt() << 32;
		}

		@Override
		public long readPackedLong() throws IOException {
			final int low = readInt();
			if (low >= 0) return low;
			final long high = readUnsignedInt();
			return high > 0 ? high << 31 | low & 0x7FFFFFFF : low;
		}

		@Override
		public float readFloat() throws IOException {
			return Float.intBitsToFloat(readInt());
		}

		@Override
		public double readDouble() throws IOException {
			return Double.longBitsToDouble(readLong());
		}

		@Override
		public boolean readBoolean() throws IOException {
			final int value = readUnsignedByte();
			if (value == 0x00) return false;
			if (value == 0x80) return true;
			throw new IOException("Invalid input data.");
		}

		@Override
		public char readChar() throws IOException {
			return (char) readUnsignedShort();
		}

		@Nonnull
		@Override
		public String readUtf8() throws IOException {
			final int length = readPackedInt();
			if (length < 0) throw new IOException("Invalid input data.");
			if (length == 0) return "";

			final byte[] bytes = new byte[length];
			int index = 0;
			do {
				final int count = stream.read(bytes, index, length - index);
				if (count < 0) throw new EOFException();
				index += count;
			} while (index < length);

			try {
				return new String(bytes, StandardCharsets.UTF_8);
			} catch (IllegalArgumentException e) {
				throw new IOException("Invalid input data.", e);
			}
		}


		@Nullable
		@Override
		public <E> E readObject(@Nonnull Class<E> objectClass) throws IOException {
			final int identity = readPackedInt();
			if (identity < 0) return null;
			final Pair<List<Object>, ObjectProcessor<?>> pair = processorMap.get(objectClass);
			if (pair == null) throw new IllegalArgumentException("Unregistered class: " + objectClass.getName());
			final List<Object> objectList = pair.getA();
			if (objectList.size() > identity) return objectClass.cast(objectList.get(identity));
			@SuppressWarnings("unchecked") final ObjectProcessor<E> objectSolidifier = (ObjectProcessor<E>) pair.getB();
			final E object = objectSolidifier.solidify(this);
			if (objectList.size() != identity) throw new IOException("Invalid input data.");
			objectList.add(object);
			return object;
		}

	}
}

