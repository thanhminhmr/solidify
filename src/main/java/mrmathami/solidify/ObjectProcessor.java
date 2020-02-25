package mrmathami.solidify;

import javax.annotation.Nonnull;
import java.io.IOException;

public interface ObjectProcessor<E> {
	@Nonnull ObjectProcessor<Byte> BYTE_PROCESSOR = new ByteProcessor();
	@Nonnull ObjectProcessor<Short> SHORT_PROCESSOR = new ShortProcessor();
	@Nonnull ObjectProcessor<Integer> INTEGER_PROCESSOR = new IntegerProcessor();
	@Nonnull ObjectProcessor<Long> LONG_PROCESSOR = new LongProcessor();
	@Nonnull ObjectProcessor<Float> FLOAT_PROCESSOR = new FloatProcessor();
	@Nonnull ObjectProcessor<Double> DOUBLE_PROCESSOR = new DoubleProcessor();
	@Nonnull ObjectProcessor<Boolean> BOOLEAN_PROCESSOR = new BooleanProcessor();
	@Nonnull ObjectProcessor<Character> CHAR_PROCESSOR = new CharacterProcessor();
	@Nonnull ObjectProcessor<String> STRING_PROCESSOR = new StringProcessor();

	/**
	 * Get cache mode.
	 * ret == 0 for Identity Cache. (default)
	 * ret > 0 for Equality Cache.
	 * ret < 0 for No Cache.
	 *
	 * @return cache mode
	 */
	default int getCacheMode() {
		return 0;
	}

	@Nonnull
	Class<E> getObjectClass();

	void liquify(@Nonnull ObjectWriter objectWriter, @Nonnull E object) throws IOException;

	@Nonnull
	E solidify(@Nonnull ObjectReader objectReader) throws IOException;
}
