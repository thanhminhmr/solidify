package mrmathami;

public final class Test {
	public static void main(String[] args) {
		String[] a = (String[]) new Object[0];
	}

	public enum EnumA {
		ENUM_A("string"),
		ENUM_B("string");

		private final String string;

		EnumA(String string) {
			this.string = string;
		}
	}

	public static final class TestClass {
		private String string;

		private Byte boxedByte;
		private Short boxedShort;
		private Integer boxedInteger;
		private Long boxedLong;
		private Float boxedFloat;
		private Double boxedDouble;
		private Boolean boxedBoolean;
		private Character boxedCharacter;

		private byte primitiveByte;
		private short primitiveShort;
		private int primitiveInt;
		private long primitiveLong;
		private float primitiveFloat;
		private double primitiveDouble;
		private boolean primitiveBoolean;
		private char primitiveChar;

		private Byte[] boxedByteArray;
		private Short[] boxedShortArray;
		private Integer[] boxedIntegerArray;
		private Long[] boxedLongArray;
		private Float[] boxedFloatArray;
		private Double[] boxedDoubleArray;
		private Boolean[] boxedBooleanArray;
		private Character[] boxedCharacterArray;

		private byte[] primitiveByteArray;
		private short[] primitiveShortArray;
		private int[] primitiveIntArray;
		private long[] primitiveLongArray;
		private float[] primitiveFloatArray;
		private double[] primitiveDoubleArray;
		private boolean[] primitiveBooleanArray;
		private char[] primitiveCharArray;


	}
}
