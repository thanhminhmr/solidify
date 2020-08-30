package mrmathami.solidify;

import mrmathami.annotation.Nonnull;
import mrmathami.annotation.Nullable;

import java.io.IOException;

public interface ObjectProcessor<E> {

	@Nonnull
	CacheType getCacheType();

	@Nullable
	default E[] preloadCache() {
		return null;
	}

	@Nonnull
	Class<E> getObjectClass();

	void liquify(@Nonnull ObjectWriter objectWriter, @Nullable ObjectWriter.Cache<E> writerCache, @Nullable E object) throws IOException, LiquifierException;

	@Nullable
	E solidify(@Nonnull ObjectReader objectReader, @Nullable ObjectReader.Cache<E> readerCache) throws IOException, SolidifierException;

	enum CacheType {
		NO_CACHE,
		IDENTITY_CACHE,
		EQUALITY_CACHE
	}

}
