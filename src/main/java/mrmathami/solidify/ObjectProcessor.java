package mrmathami.solidify;

import mrmathami.annotation.Nonnull;
import mrmathami.annotation.Nullable;
import java.io.IOException;

public interface ObjectProcessor<E> {
	default boolean usingCache() {
		return true;
	}

	default boolean usingEqualityCache() {
		return false;
	}

	@Nullable
	default E[] preloadCache() {
		return null;
	}

	@Nonnull
	Class<E> getObjectClass();

	void liquify(@Nonnull ObjectWriter objectWriter, @Nullable ObjectWriter.Cache<E> writerCache, @Nullable E object) throws IOException, LiquifierException;

	@Nullable
	E solidify(@Nonnull ObjectReader objectReader, @Nullable ObjectReader.Cache<E> readerCache) throws IOException, SolidifierException;
}
