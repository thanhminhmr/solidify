package mrmathami.solidify;

import mrmathami.util.Triple;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

public final class Liquifier {
	@Nonnull
	private final ObjectWriter objectWriter;

	public Liquifier(@Nonnull List<ObjectProcessor<?>> objectProcessors, @Nonnull OutputStream stream) {
		this.objectWriter = new ObjectWriterImpl(objectProcessors, stream);
	}

	public <E> void liquify(@Nonnull Class<E> objectClass, @Nullable E object) throws IOException {
		objectWriter.writeObject(objectClass, object);
	}

	private static final class ObjectWriterImpl implements ObjectWriter {
		@Nonnull private final Map<Class<?>, Triple<ObjectProcessor<?>, Map<Object, Integer>, List<Object>>> processorMap = new HashMap<>();
		@Nonnull private final OutputStream stream;

		public ObjectWriterImpl(@Nonnull List<ObjectProcessor<?>> objectProcessors, @Nonnull OutputStream stream) {
			this.stream = stream instanceof BufferedOutputStream ? stream : new BufferedOutputStream(stream);

			for (final ObjectProcessor<?> objectProcessor : objectProcessors) {
				final Class<?> objectClass = objectProcessor.getObjectClass();
				final int cacheMode = objectProcessor.getCacheMode();
				processorMap.put(objectClass, Triple.immutableOf(objectProcessor,
						cacheMode < 0 ? null : cacheMode == 0 ? new IdentityHashMap<>() : new HashMap<>(),
						cacheMode < 0 ? null : new ArrayList<>()));
			}
		}

		@Override
		public void writeByte(byte value) throws IOException {
			stream.write(value);
		}

		@Override
		public void writeBytes(@Nonnull byte... values) throws IOException {
			if (values.length == 0) throw new IllegalArgumentException("Values should not be empty");
			stream.write(values);
		}

		@Override
		public void writeShort(short value) throws IOException {
			stream.write(value);
			stream.write(value >> 8);
		}

		@Override
		public void writeShorts(@Nonnull short... values) throws IOException {
			if (values.length == 0) throw new IllegalArgumentException("Values should not be empty");
			for (short value : values) writeShort(value);
		}

		@Override
		public void writePackedShort(short value) throws IOException {
			if (value < (short) -0x80) throw new IllegalArgumentException("Value out of range.");
			if (value < (short) 0x80) {
				stream.write(value);
				if (value < (short) 0) stream.write(0);
			} else {
				stream.write(value | 0x80);
				stream.write(value >> 7);
			}
		}

		@Override
		public void writePackedShorts(@Nonnull short... values) throws IOException {
			if (values.length == 0) throw new IllegalArgumentException("Values should not be empty");
			for (short value : values) writePackedShort(value);
		}

		@Override
		public void writeInt(int value) throws IOException {
			stream.write(value);
			stream.write(value >> 8);
			stream.write(value >> 16);
			stream.write(value >> 24);
		}

		@Override
		public void writeInts(@Nonnull int... values) throws IOException {
			if (values.length == 0) throw new IllegalArgumentException("Values should not be empty");
			for (int value : values) writeInt(value);
		}

		@Override
		public void writePackedInt(int value) throws IOException {
			if (value < -0x8000) throw new IllegalArgumentException("Value out of range.");
			stream.write(value);
			if (value < 0x8000) {
				stream.write(value >> 8);
				if (value < 0) {
					stream.write(0);
					stream.write(0);
				}
			} else {
				stream.write(value >> 8 | 0x80);
				stream.write(value >> 15);
				stream.write(value >> 23);
			}
		}

		@Override
		public void writePackedInts(@Nonnull int... values) throws IOException {
			if (values.length == 0) throw new IllegalArgumentException("Values should not be empty");
			for (int value : values) writePackedInt(value);
		}

		@Override
		public void writeLong(long value) throws IOException {
			writeInt((int) value);
			writeInt((int) (value >> 32));
		}

		@Override
		public void writeLongs(@Nonnull long... values) throws IOException {
			if (values.length == 0) throw new IllegalArgumentException("Values should not be empty");
			for (long value : values) writeLong(value);
		}

		@Override
		public void writePackedLong(long value) throws IOException {
			if (value < -0x80000000L) throw new IllegalArgumentException("Value out of range.");
			if (value < 0x80000000L) {
				writeInt((int) value);
				if (value < 0L) {
					// writeInt(0);
					stream.write(0);
					stream.write(0);
					stream.write(0);
					stream.write(0);
				}
			} else {
				writeInt((int) (value | 0x80000000L));
				writeInt((int) (value >> 31));
			}
		}

		@Override
		public void writePackedLongs(@Nonnull long... values) throws IOException {
			if (values.length == 0) throw new IllegalArgumentException("Values should not be empty");
			for (long value : values) writePackedLong(value);
		}

		@Override
		public void writeFloat(float value) throws IOException {
			writeInt(Float.floatToIntBits(value));
		}

		@Override
		public void writeFloats(@Nonnull float... values) throws IOException {
			if (values.length == 0) throw new IllegalArgumentException("Values should not be empty");
			for (float value : values) writeFloat(value);
		}

		@Override
		public void writeDouble(double value) throws IOException {
			writeLong(Double.doubleToLongBits(value));
		}

		@Override
		public void writeDoubles(@Nonnull double... values) throws IOException {
			if (values.length == 0) throw new IllegalArgumentException("Values should not be empty");
			for (double value : values) writeDouble(value);
		}

		@Override
		public void writeBoolean(boolean value) throws IOException {
			stream.write(value ? 0x80 : 0);
		}

		@Override
		public void writeBooleans(@Nonnull boolean... values) throws IOException {
			final int size = values.length;
			if (size == 0) throw new IllegalArgumentException("Values should not be empty");

			final int length = size + 7 >> 3;
			final byte[] buffer = new byte[length];
			int vi = 7, bi = 0;
			while (vi < size) {
				buffer[bi++] = (byte) ((values[vi - 7] ? 0x80 : 0)
						| (values[vi - 6] ? 0x40 : 0)
						| (values[vi - 5] ? 0x20 : 0)
						| (values[vi - 4] ? 0x10 : 0)
						| (values[vi - 3] ? 0x08 : 0)
						| (values[vi - 2] ? 0x04 : 0)
						| (values[vi - 1] ? 0x02 : 0)
						| (values[vi] ? 0x01 : 0));
				vi += 8;
			}
			if (bi < length) {
				vi -= 7;
				int value = 0, mask = (byte) 0x80;
				do {
					value |= values[vi++] ? mask : 0;
					mask >>= 1;
				} while (vi < size);
				buffer[bi] = (byte) value;
			}
			stream.write(buffer);
		}

		@Override
		public void writeChar(char value) throws IOException {
			stream.write(value);
			stream.write(value >> 8);
		}

		@Override
		public void writeChars(@Nonnull char... values) throws IOException {
			if (values.length == 0) throw new IllegalArgumentException("Values should not be empty");
			for (char value : values) writeChar(value);
		}

		@Override
		public void writeUtf8(@Nonnull String value) throws IOException {
			stream.write(value.getBytes(StandardCharsets.UTF_8));
		}

		@Override
		public <E> void writeObject(@Nonnull Class<E> objectClass, @Nullable E object) throws IOException {
			final Triple<ObjectProcessor<?>, Map<Object, Integer>, List<Object>> triple = processorMap.get(objectClass);
			if (triple == null) throw new IllegalArgumentException("Unregistered class: " + objectClass.getName());
			@SuppressWarnings("unchecked") final ObjectProcessor<E> objectProcessor = (ObjectProcessor<E>) triple.getA();
			if (objectProcessor.getCacheMode() < 0) {
				if (object != null) {
					writeBoolean(true);
					objectProcessor.liquify(this, object);
				} else {
					writeBoolean(false);
				}
			} else {
				if (object != null) {
					final Map<Object, Integer> objectMap = triple.getB();
					final Integer identity = objectMap.get(object);
					if (identity == null) {
						final List<Object> objectList = triple.getC();
						final int newId = objectList.size();
						writePackedInt(newId);
						objectMap.put(object, newId);
						objectList.add(object);
						objectProcessor.liquify(this, object);
					} else {
						writePackedInt(identity);
					}
				} else {
					writePackedInt(-0x8000);
				}
			}
		}

		@Override
		public <E> void writeObjects(@Nonnull Class<E> objectClass, @Nonnull E[] objects) throws IOException {
			final Triple<ObjectProcessor<?>, Map<Object, Integer>, List<Object>> triple = processorMap.get(objectClass);
			if (triple == null) throw new IllegalArgumentException("Unregistered class: " + objectClass.getName());
			@SuppressWarnings("unchecked") final ObjectProcessor<E> objectProcessor = (ObjectProcessor<E>) triple.getA();
			if (objectProcessor.getCacheMode() < 0) {
				for (final E object : objects) {
					if (object != null) {
						writeBoolean(true);
						objectProcessor.liquify(this, object);
					} else {
						writeBoolean(false);
					}
				}
			} else {
				final Map<Object, Integer> objectMap = triple.getB();
				final List<Object> objectList = triple.getC();
				for (final E object : objects) {
					if (object != null) {
						final Integer identity = objectMap.get(object);
						if (identity == null) {
							final int newId = objectList.size();
							writePackedInt(newId);
							objectMap.put(object, newId);
							objectList.add(object);
							objectProcessor.liquify(this, object);
						} else {
							writePackedInt(identity);
						}
					} else {
						writePackedInt(-0x8000);
					}
				}
			}
		}
	}
}

