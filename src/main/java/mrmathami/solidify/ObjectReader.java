package mrmathami.solidify;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;

public interface ObjectReader {
	byte readByte() throws IOException;

	@Nonnull
	byte[] readBytes(int size) throws IOException;

	int readUnsignedByte() throws IOException;

	@Nonnull
	int[] readUnsignedBytes(int size) throws IOException;

	short readShort() throws IOException;

	@Nonnull
	short[] readShorts(int size) throws IOException;

	int readUnsignedShort() throws IOException;

	@Nonnull
	int[] readUnsignedShorts(int size) throws IOException;

	short readPackedShort() throws IOException;

	@Nonnull
	short[] readPackedShorts(int size) throws IOException;

	int readInt() throws IOException;

	@Nonnull
	int[] readInts(int size) throws IOException;

	long readUnsignedInt() throws IOException;

	@Nonnull
	long[] readUnsignedInts(int size) throws IOException;

	int readPackedInt() throws IOException;

	@Nonnull
	int[] readPackedInts(int size) throws IOException;

	long readLong() throws IOException;

	@Nonnull
	long[] readLongs(int size) throws IOException;

	long readPackedLong() throws IOException;

	@Nonnull
	long[] readPackedLongs(int size) throws IOException;

	float readFloat() throws IOException;

	@Nonnull
	float[] readFloats(int size) throws IOException;

	double readDouble() throws IOException;

	@Nonnull
	double[] readDoubles(int size) throws IOException;

	boolean readBoolean() throws IOException;

	@Nonnull
	boolean[] readPackedBooleans(int size) throws IOException;

	char readChar() throws IOException;

	@Nonnull
	char[] readChars(int size) throws IOException;

	@Nullable
	<E> E readObject(@Nonnull Class<E> objectClass) throws IOException, SolidifierException;

	@Nonnull
	<E> E[] readObjects(@Nonnull Class<E> objectClass, int size) throws IOException, SolidifierException;

	interface Cache<E> {
		@Nullable
		E get(int index) throws CacheException;

		@Nonnull
		CacheSlot<E> alloc();

	}

	interface CacheSlot<E> {
		void put(@Nullable E object) throws CacheException;
	}

	final class CacheException extends Exception {
		private static final long serialVersionUID = 2713790600985545449L;

		CacheException(String message) {
			super(message);
		}
	}
}
