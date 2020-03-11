package mrmathami.solidify;

import javax.annotation.Nonnull;
import java.io.IOException;

final class ObjectArrayProcessor<E> extends AbstractArrayProcessor<E> implements ObjectProcessor<E[]> {
	ObjectArrayProcessor(@Nonnull Class<E[]> arrayClass) {
		super(arrayClass);
	}

	@Override
	protected void liquify(@Nonnull ObjectWriter objectWriter, @Nonnull E[] array) throws IOException, LiquifierException {
		objectWriter.writeObjects(innerClass, array);
	}

	@Nonnull
	@Override
	protected E[] solidify(@Nonnull ObjectReader objectReader, int length) throws IOException, SolidifierException {
		return objectReader.readObjects(innerClass, length);
	}
}
