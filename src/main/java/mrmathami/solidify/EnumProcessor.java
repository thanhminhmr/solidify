package mrmathami.solidify;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;

abstract class EnumProcessor<E extends Enum<E>> implements ObjectProcessor<E> {
	@Nonnull protected final Class<E> enumClass;
	@Nonnull protected final E[] enumValues;

	private EnumProcessor(@Nonnull Class<E> enumClass, @Nonnull E[] enumValues) {
		this.enumClass = enumClass;
		this.enumValues = enumValues;
	}

	@Nonnull
	static <E extends Enum<E>> EnumProcessor<E> of(@Nonnull Class<E> enumClass) {
		final E[] enumValues = enumClass.getEnumConstants();
		return enumValues.length <= 255
				? new ByteEnumProcessor<>(enumClass, enumValues)
				: new ShortEnumProcessor<>(enumClass, enumValues);
	}

	@Override
	public boolean usingCache() {
		return false;
	}

	@Nonnull
	@Override
	public Class<E> getObjectClass() {
		return enumClass;
	}

	private static final class ByteEnumProcessor<E extends Enum<E>> extends EnumProcessor<E> {
		private ByteEnumProcessor(@Nonnull Class<E> enumClass, @Nonnull E[] enumValues) {
			super(enumClass, enumValues);
			assert enumValues.length <= 0xFF;
		}

		@Override
		public void liquify(@Nonnull ObjectWriter objectWriter, @Nullable ObjectWriter.Cache<E> writerCache, @Nullable E object) throws IOException {
			objectWriter.writeByte((byte) (object == null ? 0xFF : object.ordinal()));
		}

		@Nullable
		@Override
		public E solidify(@Nonnull ObjectReader objectReader, @Nullable ObjectReader.Cache<E> readerCache) throws IOException {
			final int value = objectReader.readUnsignedByte();
			if (value == 0xFF) return null;
			if (value >= enumValues.length) throw new IOException("Invalid input data.");
			return enumValues[value];
		}
	}

	private static final class ShortEnumProcessor<E extends Enum<E>> extends EnumProcessor<E> {
		private ShortEnumProcessor(@Nonnull Class<E> enumClass, @Nonnull E[] enumValues) {
			super(enumClass, enumValues);
			assert enumValues.length <= 0xFFFF;
		}

		@Override
		public void liquify(@Nonnull ObjectWriter objectWriter, @Nullable ObjectWriter.Cache<E> writerCache, @Nullable E object) throws IOException {
			objectWriter.writeShort((short) (object == null ? 0xFFFF : object.ordinal()));
		}

		@Nullable
		@Override
		public E solidify(@Nonnull ObjectReader objectReader, @Nullable ObjectReader.Cache<E> readerCache) throws IOException {
			final int value = objectReader.readUnsignedShort();
			if (value == 0xFFFF) return null;
			if (value >= enumValues.length) throw new IOException("Invalid input data.");
			return enumValues[value];
		}
	}
}
