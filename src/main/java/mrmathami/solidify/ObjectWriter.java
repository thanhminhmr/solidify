package mrmathami.solidify;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;

public interface ObjectWriter {
	void writeByte(byte value) throws IOException;

	void writeBytes(@Nonnull byte... values) throws IOException;

	void writeShort(short value) throws IOException;

	void writeShorts(@Nonnull short... values) throws IOException;

	void writePackedShort(short value) throws IOException;

	void writePackedShorts(@Nonnull short... values) throws IOException;

	void writeInt(int value) throws IOException;

	void writeInts(@Nonnull int... values) throws IOException;

	void writePackedInt(int value) throws IOException;

	void writePackedInts(@Nonnull int... values) throws IOException;

	void writeLong(long value) throws IOException;

	void writeLongs(@Nonnull long... values) throws IOException;

	void writePackedLong(long value) throws IOException;

	void writePackedLongs(@Nonnull long... values) throws IOException;

	void writeFloat(float value) throws IOException;

	void writeFloats(@Nonnull float... values) throws IOException;

	void writeDouble(double value) throws IOException;

	void writeDoubles(@Nonnull double... values) throws IOException;

	void writeBoolean(boolean value) throws IOException;

	void writeBooleans(@Nonnull boolean... values) throws IOException;

	void writeChar(char value) throws IOException;

	void writeChars(@Nonnull char... values) throws IOException;

	void writeUtf8(@Nonnull String value) throws IOException;

	<E> void writeObject(@Nonnull Class<E> objectClass, @Nullable E object) throws IOException;

	<E> void writeObjects(@Nonnull Class<E> objectClass, @Nonnull E[] objects) throws IOException;
}
