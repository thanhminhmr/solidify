package mrmathami.solidify;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;

public interface ObjectReader {
	byte readByte() throws IOException;

	byte[] readBytes(int size) throws IOException;

	int readUnsignedByte() throws IOException;

	int[] readUnsignedBytes(int size) throws IOException;

	short readShort() throws IOException;

	short[] readShorts(int size) throws IOException;

	int readUnsignedShort() throws IOException;

	int[] readUnsignedShorts(int size) throws IOException;

	short readPackedShort() throws IOException;

	short[] readPackedShorts(int size) throws IOException;

	int readInt() throws IOException;

	int[] readInts(int size) throws IOException;

	long readUnsignedInt() throws IOException;

	long[] readUnsignedInts(int size) throws IOException;

	int readPackedInt() throws IOException;

	int[] readPackedInts(int size) throws IOException;

	long readLong() throws IOException;

	long[] readLongs(int size) throws IOException;

	long readPackedLong() throws IOException;

	long[] readPackedLongs(int size) throws IOException;

	float readFloat() throws IOException;

	float[] readFloats(int size) throws IOException;

	double readDouble() throws IOException;

	double[] readDoubles(int size) throws IOException;

	boolean readBoolean() throws IOException;

	boolean[] readBooleans(int size) throws IOException;

	char readChar() throws IOException;

	char[] readChars(int size) throws IOException;

	@Nonnull
	String readUtf8() throws IOException;

	@Nullable
	<E> E readObject(@Nonnull Class<E> objectClass) throws IOException;

	@Nonnull
	<E> E[] readObjects(@Nonnull Class<E> objectClass, int size) throws IOException;
}
