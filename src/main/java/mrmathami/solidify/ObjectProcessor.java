package mrmathami.solidify;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.util.List;

public interface ObjectProcessor<E> {
	default boolean usingCache() {
		return true;
	}

	default boolean usingEqualityCache() {
		return false;
	}

	@Nullable
	default List<E> preloadCache() {
		return null;
	}

	@Nonnull
	Class<E> getObjectClass();

	void liquify(@Nonnull ObjectWriter objectWriter, @Nullable ObjectWriter.Cache<E> writerCache, @Nullable E object) throws IOException;

	@Nullable
	E solidify(@Nonnull ObjectReader objectReader, @Nullable ObjectReader.Cache<E> readerCache) throws IOException;
}
