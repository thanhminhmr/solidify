package mrmathami.solidify;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

final class StringProcessor implements ObjectProcessor<String> {
	private static final String EMPTY = "";

	@Override
	public boolean usingEqualityCache() {
		return true;
	}

	@Nonnull
	@Override
	public List<String> preloadCache() {
		return Arrays.asList(null, EMPTY);
	}

	@Nonnull
	@Override
	public Class<String> getObjectClass() {
		return String.class;
	}

	@Override
	public void liquify(@Nonnull ObjectWriter objectWriter, @Nullable ObjectWriter.Cache<String> writerCache, @Nullable String object) throws IOException {
		if (writerCache == null) throw new IllegalStateException("Object cache not available.");

		final int index = writerCache.putIfAbsent(object == null || object.length() > 0 ? object : EMPTY);
		if (index >= 0) {
			objectWriter.writePackedInt(index);
		} else {
			assert object != null;
			final byte[] bytes = object.getBytes(StandardCharsets.UTF_8);
			final int length = bytes.length;
			if (length < 0x8000) {
				objectWriter.writePackedInt(-length);
			} else {
				objectWriter.writePackedInt(-0x8000);
				objectWriter.writePackedInt(length - 0x8000);
			}
			objectWriter.writeBytes(bytes);
		}
	}

	@Nullable
	@Override
	public String solidify(@Nonnull ObjectReader objectReader, @Nullable ObjectReader.Cache<String> readerCache) throws IOException {
		if (readerCache == null) throw new IllegalStateException("Object cache not available.");

		try {
			final int index = objectReader.readPackedInt();
			if (index < 0) {
				final ObjectReader.Cache.Slot<String> slot = readerCache.alloc();
				final int length = index > -0x8000 ? -index : objectReader.readPackedInt() + 0x8000;
				final byte[] bytes = objectReader.readBytes(length);
				final String object = new String(bytes, StandardCharsets.UTF_8);
				slot.put(object);
				return object;
			} else {
				return readerCache.get(index);
			}
		} catch (IllegalArgumentException | IllegalStateException e) {
			throw new IOException("Invalid input data.", e);
		}
	}
}
