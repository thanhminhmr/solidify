package mrmathami.solidify;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.util.List;

public interface ObjectProcessor<E> {
	@Nonnull ObjectProcessor<String> STRING_PROCESSOR = new StringProcessor();

	@Nonnull ObjectProcessor<Byte> BOXED_BYTE_PROCESSOR = new BoxedByteProcessor();
	@Nonnull ObjectProcessor<Short> BOXED_SHORT_PROCESSOR = new BoxedShortProcessor();
	@Nonnull ObjectProcessor<Integer> BOXED_INTEGER_PROCESSOR = new BoxedIntegerProcessor();
	@Nonnull ObjectProcessor<Long> BOXED_LONG_PROCESSOR = new BoxedLongProcessor();
	@Nonnull ObjectProcessor<Float> BOXED_FLOAT_PROCESSOR = new BoxedFloatProcessor();
	@Nonnull ObjectProcessor<Double> BOXED_DOUBLE_PROCESSOR = new BoxedDoubleProcessor();
	@Nonnull ObjectProcessor<Boolean> BOXED_BOOLEAN_PROCESSOR = new BoxedBooleanProcessor();
	@Nonnull ObjectProcessor<Character> BOXED_CHAR_PROCESSOR = new BoxedCharacterProcessor();

	@Nonnull ObjectProcessor<byte[]> BYTE_ARRAY_PROCESSOR = new PrimitiveByteArrayProcessor();
	@Nonnull ObjectProcessor<short[]> SHORT_ARRAY_PROCESSOR = new PrimitiveShortArrayProcessor();
	@Nonnull ObjectProcessor<int[]> INT_ARRAY_PROCESSOR = new PrimitiveIntArrayProcessor();
	@Nonnull ObjectProcessor<long[]> LONG_ARRAY_PROCESSOR = new PrimitiveLongArrayProcessor();
	@Nonnull ObjectProcessor<float[]> FLOAT_ARRAY_PROCESSOR = new PrimitiveFloatArrayProcessor();
	@Nonnull ObjectProcessor<double[]> DOUBLE_ARRAY_PROCESSOR = new PrimitiveDoubleArrayProcessor();
	@Nonnull ObjectProcessor<boolean[]> BOOLEAN_ARRAY_PROCESSOR = new PrimitiveBooleanArrayProcessor();
	@Nonnull ObjectProcessor<char[]> CHAR_ARRAY_PROCESSOR = new PrimitiveCharArrayProcessor();

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
