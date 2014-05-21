package com.zaiquiri.janus;

public class TestSuiteForInstacesFactory {

    private final TesterFactory testerFactory;

    public TestSuiteForInstacesFactory(final TesterFactory testerFactory) {
        this.testerFactory = testerFactory;
    }

    public TestSuiteForInstaces createSuiteFor(final Iterable<Object> intsances){
        return new TestSuiteForInstaces(intsances, testerFactory);
    }


}
