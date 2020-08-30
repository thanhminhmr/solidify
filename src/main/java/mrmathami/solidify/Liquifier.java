package mrmathami.solidify;

import mrmathami.annotation.Nonnull;
import mrmathami.annotation.Nullable;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;

public final class Liquifier {
	@Nonnull private final ObjectWriter objectWriter;

	public Liquifier(@Nonnull Set<ObjectProcessor<?>> objectProcessors, @Nonnull OutputStream stream) {
		this.objectWriter = new ObjectWriterImpl(objectProcessors, stream);
	}

	public <E> void liquify(@Nonnull Class<E> objectClass, @Nullable E object) throws IOException, LiquifierException {
		objectWriter.writeObject(objectClass, object);
	}
}

