package org.janvs.testers;

import org.janvs.factories.Factory;

import java.util.Collection;

public class TypeCollectionTester<T> implements Tester {

    private final Collection<T> types;
    private final Factory<Tester, T> testerFactory;

    public static <T> TypeCollectionTester<T> typeCollectionTester(final Collection<T> types, final Factory<Tester, T> testerFactory) {
        return new TypeCollectionTester<>(types, testerFactory);
    }

    private TypeCollectionTester(final Collection<T> types, final Factory<Tester, T> testerFactory) {
        if (types == null || testerFactory == null) {
            throw new NullPointerException();
        }
        this.types = types;
        this.testerFactory = testerFactory;
    }

    @Override
    public void run() {
        for (final T type : types) {
            this.testerFactory.createFor(type).run();
        }
    }
}
