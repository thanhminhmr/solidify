package mrmathami.solidify;

import javax.annotation.Nonnull;

public final class ObjectProcessors {
	@Nonnull public static final ObjectProcessor<String> STRING_PROCESSOR = new StringProcessor();

	@Nonnull public static final ObjectProcessor<Byte> BOXED_BYTE_PROCESSOR = new BoxedByteProcessor();
	@Nonnull public static final ObjectProcessor<Short> BOXED_SHORT_PROCESSOR = new BoxedShortProcessor();
	@Nonnull public static final ObjectProcessor<Integer> BOXED_INTEGER_PROCESSOR = new BoxedIntegerProcessor();
	@Nonnull public static final ObjectProcessor<Long> BOXED_LONG_PROCESSOR = new BoxedLongProcessor();
	@Nonnull public static final ObjectProcessor<Float> BOXED_FLOAT_PROCESSOR = new BoxedFloatProcessor();
	@Nonnull public static final ObjectProcessor<Double> BOXED_DOUBLE_PROCESSOR = new BoxedDoubleProcessor();
	@Nonnull public static final ObjectProcessor<Boolean> BOXED_BOOLEAN_PROCESSOR = new BoxedBooleanProcessor();
	@Nonnull public static final ObjectProcessor<Character> BOXED_CHARACTER_PROCESSOR = new BoxedCharacterProcessor();

	@Nonnull public static final ObjectProcessor<byte[]> BYTE_ARRAY_PROCESSOR = new PrimitiveByteArrayProcessor();
	@Nonnull public static final ObjectProcessor<short[]> SHORT_ARRAY_PROCESSOR = new PrimitiveShortArrayProcessor();
	@Nonnull public static final ObjectProcessor<int[]> INT_ARRAY_PROCESSOR = new PrimitiveIntArrayProcessor();
	@Nonnull public static final ObjectProcessor<long[]> LONG_ARRAY_PROCESSOR = new PrimitiveLongArrayProcessor();
	@Nonnull public static final ObjectProcessor<float[]> FLOAT_ARRAY_PROCESSOR = new PrimitiveFloatArrayProcessor();
	@Nonnull public static final ObjectProcessor<double[]> DOUBLE_ARRAY_PROCESSOR = new PrimitiveDoubleArrayProcessor();
	@Nonnull public static final ObjectProcessor<boolean[]> BOOLEAN_ARRAY_PROCESSOR = new PrimitiveBooleanArrayProcessor();
	@Nonnull public static final ObjectProcessor<char[]> CHAR_ARRAY_PROCESSOR = new PrimitiveCharArrayProcessor();

//	private static final List<Class<?>> PRIMITIVE_CLASSES = Arrays.asList(
//			Byte.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE, Boolean.TYPE, Character.TYPE);

	private ObjectProcessors() {
	}

	@Nonnull
	public static <E extends Enum<E>> ObjectProcessor<E> forEnum(@Nonnull Class<E> enumClass) {
		return EnumProcessor.of(enumClass);
	}

	@Nonnull
	public static <E> ObjectProcessor<E[]> forArray(@Nonnull Class<E[]> arrayClass) {
		return new ObjectArrayProcessor<>(arrayClass);
	}
}
