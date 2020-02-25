package mrmathami.solidify;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;

public interface ObjectWriter {
	void writeByte(byte value) throws IOException;

	void writeShort(short value) throws IOException;

	void writePackedShort(short value) throws IOException;

	void writeInt(int value) throws IOException;

	void writePackedInt(int value) throws IOException;

	void writeLong(long value) throws IOException;

	void writePackedLong(long value) throws IOException;

	void writeFloat(float value) throws IOException;

	void writeDouble(double value) throws IOException;

	void writeBoolean(boolean value) throws IOException;

	void writePackedBoolean(boolean... value) throws IOException;

	void writeChar(char value) throws IOException;

	<E> void writeObject(@Nullable E object) throws IOException;
}
