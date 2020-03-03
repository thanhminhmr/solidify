package mrmathami.solidify;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

final class ObjectArrayProcessor<E> implements ObjectProcessor<E[]> {
	@Nonnull private final Class<E[]> arrayObjectClass;
	@Nonnull private final Class<E> innerObjectClass;
	@Nonnull private final E[] empty;

	public ObjectArrayProcessor(@Nonnull Class<E[]> arrayObjectClass, @Nonnull Class<E> innerObjectClass) {
		this.arrayObjectClass = arrayObjectClass;
		this.innerObjectClass = innerObjectClass;
		this.empty = arrayObjectClass.cast(Array.newInstance(innerObjectClass, 0));
	}

	@Nonnull
	@Override
	public List<E[]> preloadCache() {
		return Arrays.asList(null, empty);
	}

	@Nonnull
	@Override
	public Class<E[]> getObjectClass() {
		return arrayObjectClass;
	}

	@Override
	public void liquify(@Nonnull ObjectWriter objectWriter, @Nullable ObjectWriter.Cache<E[]> writerCache, @Nullable E[] object) throws IOException {
		if (writerCache == null) throw new IllegalStateException("Object cache not available.");

		final int length = object != null ? object.length : -1;
		final int index = writerCache.putIfAbsent(length > 0 ? object : empty);
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
			objectWriter.writeObjects(innerObjectClass, object);
		}
	}

	@Nullable
	@Override
	public E[] solidify(@Nonnull ObjectReader objectReader, @Nullable ObjectReader.Cache<E[]> readerCache) throws IOException {
		if (readerCache == null) throw new IllegalStateException("Object cache not available.");

		try {
			final int index = objectReader.readPackedInt();
			if (index < 0) {
				final ObjectReader.Cache.Slot<E[]> slot = readerCache.alloc();
				final int length = index > -0x8000 ? -index : objectReader.readPackedInt() + 0x8000;
				final E[] object = objectReader.readObjects(innerObjectClass, length);
				slot.put(object);
				return object;
			} else {
				return readerCache.get(index);
			}
		} catch (IllegalStateException e) {
			throw new IOException("Invalid input data.", e);
		}
	}
}
