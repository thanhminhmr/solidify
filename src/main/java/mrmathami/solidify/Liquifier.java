package mrmathami.solidify;

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap;
import mrmathami.solidify.ObjectWriter.Cache;
import mrmathami.util.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Liquifier {
	@Nonnull private final ObjectWriter objectWriter;

	public Liquifier(@Nonnull List<ObjectProcessor<?>> objectProcessors, @Nonnull OutputStream stream) {
		this.objectWriter = new ObjectWriterImpl(objectProcessors, stream);
	}

	public <E> void liquify(@Nonnull Class<E> objectClass, @Nullable E object) throws IOException {
		objectWriter.writeObject(objectClass, object);
	}

	private static final class ObjectWriterImpl implements ObjectWriter {
		@Nonnull private final Map<Class<?>, Pair<ObjectProcessor<?>, Cache<?>>> classMap = new HashMap<>();
		@Nonnull private final OutputStream stream;

		public ObjectWriterImpl(@Nonnull List<ObjectProcessor<?>> objectProcessors, @Nonnull OutputStream stream) {
			this.stream = stream instanceof BufferedOutputStream ? stream : new BufferedOutputStream(stream);

			for (final ObjectProcessor<?> objectProcessor : objectProcessors) {
				final Class<?> objectClass = objectProcessor.getObjectClass();
				final boolean usingCache = objectProcessor.usingCache();
				final List<Object> preloadObjects = usingCache ? objectProcessor.preloadCache() : null;
				final Cache<?> cache = usingCache ? objectProcessor.usingEqualityCache()
						? new EqualityCacheImpl<>(preloadObjects) : new IdentityCacheImpl<>(preloadObjects) : null;
				if (classMap.put(objectClass, Pair.immutableOf(objectProcessor, cache)) != null) {
					throwAlreadyRegisteredClass(objectClass);
				}
			}
		}

		private static void throwEmptyValues() {
			throw new IllegalArgumentException("Values should not be empty.");
		}

		private static void throwOutOfRangeValue() {
			throw new IllegalArgumentException("Value out of range.");
		}

		private static void throwUnregisteredClass(@Nonnull Class<?> objectClass) {
			throw new IllegalArgumentException("Unregistered class: " + objectClass.getName());
		}

		private static void throwAlreadyRegisteredClass(@Nonnull Class<?> objectClass) {
			throw new IllegalArgumentException("Already registered class: " + objectClass.getName());
		}

		// region //====== Basic write ======

		@Override
		public void writeByte(byte value) throws IOException {
			stream.write(value);
		}

		@Override
		public void writeBytes(@Nonnull byte... values) throws IOException {
			if (values.length == 0) throwEmptyValues();
			stream.write(values);
		}

		@Override
		public void writeShort(short value) throws IOException {
			stream.write(value);
			stream.write(value >> 8);
		}

		@Override
		public void writeShorts(@Nonnull short... values) throws IOException {
			if (values.length == 0) throwEmptyValues();
			for (short value : values) writeShort(value);
		}

		@Override
		public void writePackedShort(short value) throws IOException {
			if (value < (short) -0x80) throwOutOfRangeValue();
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
			if (values.length == 0) throwEmptyValues();
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
			if (values.length == 0) throwEmptyValues();
			for (int value : values) writeInt(value);
		}

		@Override
		public void writePackedInt(int value) throws IOException {
			if (value < -0x8000) throwOutOfRangeValue();
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
			if (values.length == 0) throwEmptyValues();
			for (int value : values) writePackedInt(value);
		}

		@Override
		public void writeLong(long value) throws IOException {
			writeInt((int) value);
			writeInt((int) (value >> 32));
		}

		@Override
		public void writeLongs(@Nonnull long... values) throws IOException {
			if (values.length == 0) throwEmptyValues();
			for (long value : values) writeLong(value);
		}

		@Override
		public void writePackedLong(long value) throws IOException {
			if (value < -0x80000000L) throwOutOfRangeValue();
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
			if (values.length == 0) throwEmptyValues();
			for (long value : values) writePackedLong(value);
		}

		@Override
		public void writeFloat(float value) throws IOException {
			writeInt(Float.floatToIntBits(value));
		}

		@Override
		public void writeFloats(@Nonnull float... values) throws IOException {
			if (values.length == 0) throwEmptyValues();
			for (float value : values) writeFloat(value);
		}

		@Override
		public void writePackedFloat(float value) throws IOException {
			// 0111 1111 1000 0000 positive infinity
			// 1111 1111 1000 0000 negative infinity
			// 0111 1111 1100 0000 NaN
			// ?000 0000 0??? ???? \
			// ?... .... .??? ????  | normal double
			// ?111 1111 0??? ???? /
			if (value == Float.POSITIVE_INFINITY) {
				writeShort((short) 0x7F80);
			} else if (value == Float.NEGATIVE_INFINITY) {
				writeShort((short) 0xFF80);
			} else if (Float.isNaN(value)) {
				writeShort((short) 0x7FC0);
			} else {
				final int bits = Float.floatToRawIntBits(value);
				assert (bits & 0x7F800000) != 0x7F800000;
				writeShort((short) (bits >> 16));
				writeShort((short) bits);
			}
		}

		@Override
		public void writePackedFloats(@Nonnull float... values) throws IOException {
			if (values.length == 0) throwEmptyValues();
			for (float value : values) writePackedFloat(value);
		}

		@Override
		public void writeDouble(double value) throws IOException {
			writeLong(Double.doubleToLongBits(value));
		}

		@Override
		public void writeDoubles(@Nonnull double... values) throws IOException {
			if (values.length == 0) throwEmptyValues();
			for (double value : values) writeDouble(value);
		}

		@Override
		public void writePackedDouble(double value) throws IOException {
			// 0111 1111 1111 0000 positive infinity
			// 1111 1111 1111 0000 negative infinity
			// 0111 1111 1111 1000 NaN
			// ?000 0000 0000 ???? \
			// ?... .... .... ????  | normal double
			// ?111 1111 1110 ???? /
			if (value == Double.POSITIVE_INFINITY) {
				writeShort((short) 0x7FF0);
			} else if (value == Double.NEGATIVE_INFINITY) {
				writeShort((short) 0xFFF0);
			} else if (Double.isNaN(value)) {
				writeShort((short) 0x7FF8);
			} else {
				final long bits = Double.doubleToRawLongBits(value);
				assert (bits & 0x7FF0000000000000L) != 0x7FF0000000000000L;
				writeShort((short) (bits >> 48));
				writeShort((short) (bits >> 32));
				writeInt((int) bits);
			}
		}

		@Override
		public void writePackedDoubles(@Nonnull double... values) throws IOException {
			if (values.length == 0) throwEmptyValues();
			for (double value : values) writePackedDouble(value);
		}

		@Override
		public void writeBoolean(boolean value) throws IOException {
			stream.write(value ? 0x80 : 0);
		}

		@Override
		public void writeBooleans(@Nonnull boolean... values) throws IOException {
			final int size = values.length;
			if (size == 0) throwEmptyValues();

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
			if (values.length == 0) throwEmptyValues();
			for (char value : values) writeChar(value);
		}

		// endregion

		@Override
		public <E> void writeObject(@Nonnull Class<E> objectClass, @Nullable E object) throws IOException {
			final Pair<ObjectProcessor<?>, Cache<?>> pair = classMap.get(objectClass);
			if (pair == null) throwUnregisteredClass(objectClass);
			@SuppressWarnings("unchecked") final ObjectProcessor<E> objectProcessor = (ObjectProcessor<E>) pair.getA();
			@SuppressWarnings("unchecked") final Cache<E> writerCache = (Cache<E>) pair.getB();
			objectProcessor.liquify(this, writerCache, object);
		}

		@Override
		public <E> void writeObjects(@Nonnull Class<E> objectClass, @Nonnull E[] objects) throws IOException {
			if (objects.length <= 0) throwEmptyValues();
			final Pair<ObjectProcessor<?>, Cache<?>> pair = classMap.get(objectClass);
			if (pair == null) throwUnregisteredClass(objectClass);
			@SuppressWarnings("unchecked") final ObjectProcessor<E> objectProcessor = (ObjectProcessor<E>) pair.getA();
			@SuppressWarnings("unchecked") final Cache<E> writerCache = (Cache<E>) pair.getB();
			for (final E object : objects) objectProcessor.liquify(this, writerCache, object);
		}
	}

	private static final class IdentityCacheImpl<E> extends Reference2IntOpenHashMap<E> implements ObjectWriter.Cache<E> {
		public IdentityCacheImpl(@Nullable E[] preloadObjects) {
			this.defRetValue = -1;

			if (preloadObjects != null) for (final E object : preloadObjects) putIfAbsent(object);
		}

		@Override
		public int putIfAbsent(@Nullable E object) {
			return putIfAbsent(object, size);
		}
	}

	private static final class EqualityCacheImpl<E> extends Object2IntOpenHashMap<E> implements ObjectWriter.Cache<E> {
		public EqualityCacheImpl(@Nullable E[] preloadObjects) {
			this.defRetValue = -1;

			if (preloadObjects != null) for (final E object : preloadObjects) putIfAbsent(object);
		}

		@Override
		public int putIfAbsent(@Nullable E object) {
			return putIfAbsent(object, size);
		}
	}
}

