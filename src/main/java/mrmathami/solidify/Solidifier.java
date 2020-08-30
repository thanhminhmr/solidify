package mrmathami.solidify;

import mrmathami.annotation.Nonnull;
import mrmathami.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

public final class Solidifier {
	@Nonnull
	private final ObjectReader objectReader;

	public Solidifier(@Nonnull Set<ObjectProcessor<?>> objectProcessors, @Nonnull InputStream stream) {
		this.objectReader = new ObjectReaderImpl(objectProcessors, stream);
	}

	@Nullable
	public <E> E solidify(@Nonnull Class<E> objectClass) throws IOException, SolidifierException {
		return objectReader.readObject(objectClass);
	}
}

