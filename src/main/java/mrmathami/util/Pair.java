package mrmathami.util;

import mrmathami.annotation.Nonnull;
import mrmathami.annotation.Nullable;

import java.io.Serializable;
import java.util.Objects;

public interface Pair<A, B> extends Serializable, Cloneable {

	@Nonnull
	static <A, B> Pair<A, B> mutableOf(A a, B b) {
		return new MutablePair<>(a, b);
	}

	@Nonnull
	static <A, B> Pair<A, B> mutableOf(@Nonnull Pair<A, B> pair) {
		return new MutablePair<>(pair.getA(), pair.getB());
	}

	@Nonnull
	static <A, B> Pair<A, B> immutableOf(A a, B b) {
		return new ImmutablePair<>(a, b);
	}

	@Nonnull
	static <A, B> Pair<A, B> immutableOf(@Nonnull Pair<A, B> pair) {
		return new ImmutablePair<>(pair.getA(), pair.getB());
	}

	A getA();

	A setA(A a) throws UnsupportedOperationException;

	B getB();

	B setB(B b) throws UnsupportedOperationException;

	@Nonnull
	Pair<A, B> clone();
}

final class MutablePair<A, B> implements Pair<A, B> {
	private static final long serialVersionUID = 3329068140030524856L;
	private A a;
	private B b;

	MutablePair(A a, B b) {
		this.a = a;
		this.b = b;
	}

	@Override
	public final A getA() {
		return a;
	}

	@Override
	public final A setA(A a) throws UnsupportedOperationException {
		final A oldA = this.a;
		this.a = a;
		return oldA;
	}

	@Override
	public final B getB() {
		return b;
	}

	@Override
	public final B setB(B b) throws UnsupportedOperationException {
		final B oldB = this.b;
		this.b = b;
		return oldB;
	}

	@SuppressWarnings("unchecked")
	@Nonnull
	@Override
	public final MutablePair<A, B> clone() {
		try {
			return (MutablePair<A, B>) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public final boolean equals(@Nullable Object object) {
		if (this == object) return true;
		if (!(object instanceof Pair)) return false;
		final Pair<?, ?> pair = (Pair<?, ?>) object;
		return Objects.equals(a, pair.getA()) && Objects.equals(b, pair.getB());
	}

	@Override
	public final int hashCode() {
		return Objects.hash(a, b);
	}

	@Nonnull
	@Override
	public final String toString() {
		return "{ " + a + ", " + b + " }";
	}
}

final class ImmutablePair<A, B> implements Pair<A, B> {
	private static final long serialVersionUID = 6120253641955548765L;
	private final A a;
	private final B b;

	ImmutablePair(A a, B b) {
		this.a = a;
		this.b = b;
	}

	@Override
	public final A getA() {
		return a;
	}

	@Override
	public final A setA(A a) throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Immutable pair can't be modified.");
	}

	@Override
	public final B getB() {
		return b;
	}

	@Override
	public final B setB(B b) throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Immutable pair can't be modified.");
	}

	@SuppressWarnings("unchecked")
	@Nonnull
	@Override
	public ImmutablePair<A, B> clone() {
		try {
			return (ImmutablePair<A, B>) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public final boolean equals(@Nullable Object object) {
		if (this == object) return true;
		if (!(object instanceof Pair)) return false;
		final Pair<?, ?> pair = (Pair<?, ?>) object;
		return Objects.equals(a, pair.getA()) && Objects.equals(b, pair.getB());
	}

	@Override
	public final int hashCode() {
		return Objects.hash(a, b);
	}

	@Nonnull
	@Override
	public final String toString() {
		return "{ " + a + ", " + b + " }";
	}
}