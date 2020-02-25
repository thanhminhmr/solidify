package mrmathami.solidify;

import mrmathami.util.Triple;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

public final class Liquifier {
	@Nonnull
	private final ObjectWriter objectWriter;

	public Liquifier(@Nonnull List<ObjectProcessor<?>> objectProcessors, @Nonnull OutputStream stream) {
		this.objectWriter = new ObjectWriterImpl(objectProcessors, stream);
	}

	public <E> void liquify(@Nullable E object) throws IOException {
		objectWriter.writeObject(object);
	}

	private static final class ObjectWriterImpl implements ObjectWriter {
		@Nonnull private final Map<Class<?>, Triple<Map<Object, Integer>, List<Object>, ObjectProcessor<?>>> processorMap = new HashMap<>();
		@Nonnull private final DataOutputStream stream;

		public ObjectWriterImpl(@Nonnull List<ObjectProcessor<?>> objectProcessors, @Nonnull OutputStream stream) {
			this.stream = new DataOutputStream(stream);

			for (final ObjectProcessor<?> objectProcessor : objectProcessors) {
				final Class<?> objectClass = objectProcessor.getObjectClass();
				processorMap.put(objectClass, Triple.immutableOf(
						objectProcessor.isInstanceIndependence() ? new IdentityHashMap<>() : new HashMap<>(),
						new ArrayList<>(), objectProcessor));
			}
		}

		@Override
		public void writeByte(byte value) throws IOException {
			stream.write(value);
		}

		@Override
		public void writeShort(short value) throws IOException {
			stream.writeShort(value);
		}

		@Override
		public void writeInt(int value) throws IOException {
			stream.writeInt(value);
		}

		@Override
		public void writeLong(long value) throws IOException {
			stream.writeLong(value);
		}

		@Override
		public void writeFloat(float value) throws IOException {
			stream.writeFloat(value);
		}

		@Override
		public void writeDouble(double value) throws IOException {
			stream.writeDouble(value);
		}

		@Override
		public void writeBoolean(boolean value) throws IOException {
			stream.writeBoolean(value);
		}

		@Override
		public void writeChar(char value) throws IOException {
			stream.writeChar(value);
		}

		@Override
		public void writeLiterals(@Nonnull String value) throws IOException {
			stream.writeUTF(value);
		}

		@Override
		public void writeByteArray(@Nullable byte[] array) throws IOException {
			if (array == null) {
				writeEncodedInt(-1);
			} else {
				final int length = array.length;
				writeEncodedInt(length);
				for (final byte value : array) {
					stream.writeByte(value);
				}
			}
		}

		@Override
		public void writeShortArray(@Nullable short[] array) throws IOException {

		}

		@Override
		public void writeIntArray(@Nullable int[] array) throws IOException {

		}

		@Override
		public void writeLongArray(@Nullable long[] array) throws IOException {

		}

		@Override
		public void writeFloatArray(@Nullable float[] array) throws IOException {

		}

		@Override
		public void writeDoubleArray(@Nullable double[] array) throws IOException {

		}

		@Override
		public void writeBooleanArray(@Nullable boolean[] array) throws IOException {

		}

		@Override
		public void writeCharArray(@Nullable char[] array) throws IOException {

		}

		@Override
		public <E> void writeArray(@Nullable E[] objectArray) throws IOException {
			if (objectArray == null) {
				writeEncodedInt(-1);
			} else {
				final int length = objectArray.length;
				writeEncodedInt(length);
				for (final E object : objectArray) {
					writeObject(object);
				}
			}
		}

		@Override
		public <E> void writeObject(@Nullable E object) throws IOException {
			if (object == null) {
				writeEncodedInt(-1);
			} else {
				final Class<?> objectClass = object.getClass();
				final Triple<Map<Object, Integer>, List<Object>, ObjectProcessor<?>> triple = processorMap.get(objectClass);
				if (triple == null) throw new IllegalArgumentException("Unregistered class: " + objectClass.getName());
				final Map<Object, Integer> objectMap = triple.getA();
				final Integer identity = objectMap.get(object);
				if (identity != null) {
					writeEncodedInt(identity);
				} else {
					final List<Object> objectList = triple.getB();
					final int newId = objectList.size();
					writeEncodedInt(newId);
					objectMap.put(object, newId);
					objectList.add(object);

					@SuppressWarnings("unchecked") final ObjectProcessor<E> objectProcessor = (ObjectProcessor<E>) triple.getC();
					objectProcessor.liquify(this, object);
				}
			}
		}

		private void writeEncodedInt(int id) throws IOException {
			if (id < 0) {
				stream.writeShort(0x8000);
				stream.writeShort(0x0000);
			} else if (id < 0x8000) {
				stream.writeShort(id);
			} else {
				stream.writeShort(0x8000 | id & 0x7FFF);
				stream.writeShort(id >> 15);
			}
		}
	}
}

