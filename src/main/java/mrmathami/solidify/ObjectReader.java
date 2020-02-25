package mrmathami.solidify;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;

public interface ObjectReader {
	byte readByte() throws IOException;

	int readUnsignedByte() throws IOException;

	short readShort() throws IOException;

	int readUnsignedShort() throws IOException;

	short readPackedShort() throws IOException;

	int readInt() throws IOException;

	long readUnsignedInt() throws IOException;

	int readPackedInt() throws IOException;

	long readLong() throws IOException;

	long readPackedLong() throws IOException;

	float readFloat() throws IOException;

	double readDouble() throws IOException;

	boolean readBoolean() throws IOException;

	boolean[] readPackedBoolean(int size) throws IOException;

	char readChar() throws IOException;

	@Nonnull
	String readUtf8() throws IOException;

	@Nullable
	<E> E readObject(@Nonnull Class<E> objectClass) throws IOException;
}
