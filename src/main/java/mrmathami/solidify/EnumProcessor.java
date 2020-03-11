package mrmathami.solidify;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;

final class EnumProcessor<E extends Enum<E>> implements ObjectProcessor<E> {
	@Nonnull protected final Class<E> enumClass;
	@Nonnull protected final E[] enumValues;

	EnumProcessor(@Nonnull Class<E> enumClass) {
		this.enumClass = enumClass;
		this.enumValues = enumClass.getEnumConstants();

		assert enumValues.length < 65535;
	}

	@Override
	public final boolean usingCache() {
		return false;
	}

	@Nonnull
	@Override
	public final Class<E> getObjectClass() {
		return enumClass;
	}

	@Override
	public void liquify(@Nonnull ObjectWriter objectWriter, @Nullable ObjectWriter.Cache<E> writerCache, @Nullable E object) throws IOException {
		final int value = object == null ? -1 : object.ordinal();
		if (enumValues.length < 0xFF) {
			objectWriter.writeByte((byte) value);
		} else {
			objectWriter.writeShort((short) value);
		}
	}

	@Nullable
	@Override
	public E solidify(@Nonnull ObjectReader objectReader, @Nullable ObjectReader.Cache<E> readerCache) throws IOException {
		final int length = enumValues.length;
		final int value = length < 0xFF ? objectReader.readUnsignedByte() : objectReader.readUnsignedShort();
		if (length < 0xFF && value == 0xFF || value == 0xFFFF) return null;
		if (value >= length) throw new IOException("Invalid input data.");
		return enumValues[value];
	}
}
