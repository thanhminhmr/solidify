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
}
