package mrmathami.solidify;

import mrmathami.annotation.Nonnull;
import mrmathami.annotation.Nullable;
import java.io.IOException;
import java.lang.reflect.Array;

public abstract class AbstractArrayProcessor<E> implements ObjectProcessor<E[]> {
	@Nonnull protected final Class<E[]> arrayClass;
	@Nonnull protected final Class<E> innerClass;
	@Nonnull private final E[] empty;
	@Nonnull private final E[][] preloadObjects;

	protected AbstractArrayProcessor(@Nonnull Class<E[]> arrayClass) {
		@SuppressWarnings("unchecked") final Class<E> innerClass = (Class<E>) arrayClass.getComponentType();
		@SuppressWarnings("unchecked") final E[] empty = (E[]) Array.newInstance(innerClass, 0);
		@SuppressWarnings("unchecked") final E[][] preloadObjects = (E[][]) Array.newInstance(arrayClass, 2);

		preloadObjects[0] = null;
		preloadObjects[1] = empty;

		this.arrayClass = arrayClass;
		this.innerClass = innerClass;
		this.empty = empty;
		this.preloadObjects = preloadObjects;
	}

	@Override
	public final boolean usingCache() {
		return false;
	}

	@Nonnull
	@Override
	public final E[][] preloadCache() {
		return preloadObjects;
	}

	@Nonnull
	@Override
	public final Class<E[]> getObjectClass() {
		return arrayClass;
	}

	@Override
	public final void liquify(@Nonnull ObjectWriter objectWriter, @Nullable ObjectWriter.Cache<E[]> writerCache, @Nullable E[] array) throws IOException, LiquifierException {
		if (writerCache == null) throw new IllegalStateException("Object cache not available.");

		final int length = array != null ? array.length : -1;
		final int index = writerCache.putIfAbsent(length > 0 ? array : empty);
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
			liquify(objectWriter, array);
		}
	}

	protected abstract void liquify(@Nonnull ObjectWriter objectWriter, @Nonnull E[] array) throws IOException, LiquifierException;

	@Nullable
	@Override
	public final E[] solidify(@Nonnull ObjectReader objectReader, @Nullable ObjectReader.Cache<E[]> readerCache) throws IOException, SolidifierException {
		if (readerCache == null) throw new IllegalStateException("Object cache not available.");

		final int index = objectReader.readPackedInt();
		try {
			if (index < 0) {
				final ObjectReader.CacheSlot<E[]> slot = readerCache.alloc();
				final int length;
				if (index > -0x8000) {
					length = -index;
				} else {
					final int lengthExt = objectReader.readPackedInt();
					if (lengthExt < 0) throw new IOException("Invalid input data.");
					length = lengthExt + 0x8000;
					if (length < 0) throw new IOException("Invalid input data.");
				}
				final E[] array = solidify(objectReader, length);
				slot.put(array);
				return array;
			} else {
				return readerCache.get(index);
			}
		} catch (ObjectReader.CacheException e) {
			throw new IOException("Invalid input data.", e);
		}
	}

	@Nonnull
	protected abstract E[] solidify(@Nonnull ObjectReader objectReader, int length) throws IOException, SolidifierException;
}
