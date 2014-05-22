package org.janvs.factories;

public interface Factory<T, E> {
    T createFor(E type);
}
