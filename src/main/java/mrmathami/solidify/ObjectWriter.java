package mrmathami.solidify;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;

public interface ObjectWriter {
	void writeByte(byte value) throws IOException;

	void writeShort(short value) throws IOException;

	void writeInt(int value) throws IOException;

	void writeLong(long value) throws IOException;

	void writeFloat(float value) throws IOException;

	void writeDouble(double value) throws IOException;

	void writeBoolean(boolean value) throws IOException;

	void writeChar(char value) throws IOException;

	void writeLiterals(@Nonnull String value) throws IOException;

	void writeByteArray(@Nullable byte[] array) throws IOException;

	void writeShortArray(@Nullable short[] array) throws IOException;

	void writeIntArray(@Nullable int[] array) throws IOException;

	void writeLongArray(@Nullable long[] array) throws IOException;

	void writeFloatArray(@Nullable float[] array) throws IOException;

	void writeDoubleArray(@Nullable double[] array) throws IOException;

	void writeBooleanArray(@Nullable boolean[] array) throws IOException;

	void writeCharArray(@Nullable char[] array) throws IOException;

	<E> void writeArray(@Nullable E[] objectArray) throws IOException;

	<E> void writeObject(@Nullable E object) throws IOException;
}
