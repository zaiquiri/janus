package com.zaiquiri.janus;

import java.lang.annotation.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

public class Janus extends Runner {
	private final Class<?> clazz;
	private final List<Method> tests;
	private final Object interfaceTest;
	private final Field interfaceUnderTest;

	public Janus(final Class<?> clazz) throws InstantiationException, IllegalAccessException{
		this.clazz = clazz;
		tests = getTestMethods();
		interfaceTest = clazz.newInstance();
		interfaceUnderTest = findInterfaceUnderTest();
	}

    private Collection<Class<?>> getImplementors() {
        ClassFinder classFinder = getClassFinderForClass(clazz);
        return classFinder.findImplementationsOf(interfaceUnderTest.getType());
    }

    @Override
	public void run(final RunNotifier notifier) {
        runForAllImplementors(notifier);
    }

    private void runForAllImplementors(final RunNotifier notifier) {
        for (final Class<?> implementor : getImplementors()){
            runForImplementor(notifier, implementor);
        }
    }

    private void runForImplementor(final RunNotifier notifier, final Class<?> implementor) {
        runForConstructors(notifier, implementor);
        runForFactories(notifier, implementor);
    }

    private void runForFactories(RunNotifier notifier, Class<?> implementation) {

    }

    private void runForConstructors(RunNotifier notifier, Class<?> implementation) {
        for (final Constructor constructor : implementation.getConstructors()) {
            runForAllInstancesOfConstructor(notifier, constructor);
        }
    }

    private void runForAllInstancesOfConstructor(RunNotifier notifier, Constructor constructor) {
        for (final Object instance : createAllInstancesFromConstructor(constructor)){
            runAllTests(notifier, instance);
        }
    }

    private Iterable<? extends Object> createAllInstancesFromConstructor(Constructor constructor) {
        List instances = new ArrayList();
        try {
            instances.add(constructor.newInstance());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return instances;
    }

    private void runAllTests(final RunNotifier notifier, final Object instance) {
        for (final Method test : tests){
          runTest(notifier, instance, test);
        }
    }

    private void runTest(final RunNotifier notifier, final Object instance, final Method test) {
        final Description testDescription = Description.createTestDescription(instance.getClass(), test.getName());
        injectInstance(instance);
        evaluateTest(notifier, test, testDescription);
    }

    private ClassFinder getClassFinderForClass(final Class<?> clazz) {
        for (final Annotation annotation : clazz.getAnnotations()) {
            if (annotation.annotationType().equals(Options.class)) {
                final Options options = (Options) annotation;
                return new ClassFinder(options.basePackage());
            }
        }
        throw new RuntimeException("Base package not defined in @Janus.Options annotation");
    }

	private Field findInterfaceUnderTest() throws IllegalArgumentException, IllegalAccessException {
		final Field[] fields = clazz.getDeclaredFields();
		for(final Field field : fields){
			if (field.getAnnotation(UnderTest.class) != null){
				return field;
			}
		}
        throw new RuntimeException("@UnderTest interface not found in test class");
	}
	
	private void injectInstance(Object instance) {
		final Exposer exposer = new Exposer();
		exposer.expose(interfaceUnderTest);
        try {
            interfaceUnderTest.set(interfaceTest, instance);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

	private List<Method> getTestMethods() {
		final ArrayList<Method> methods = new ArrayList<Method>();
		for (final Method method : clazz.getDeclaredMethods()) {
            if (isATestMethod(method)) {
                methods.add(method);
            }
        }
		return methods;
	}
	
	@Override
	public Description getDescription() {
		final Description description = Description.createSuiteDescription(clazz.getName(), clazz.getAnnotations());
		return description;
	}
	
	private boolean isATestMethod(final Method method) {
		return method.getAnnotation(Test.class) != null && method.getAnnotation(Ignore.class) == null;
	}

	private void evaluateTest(final RunNotifier notifier, final Method test,final Description description) {
		testStarted(notifier, description);
		final Class<? extends Throwable> expectedException = test.getAnnotation(Test.class).expected();
		try {
			runTest(test);
			if (isNotExpected(expectedException)) {
				testSucceeded(notifier, description);
			} else {
				throw new Exception();
			}
		} catch (final InvocationTargetException e) {
			if (theExpectedHappened(expectedException, e)){
				testSucceeded(notifier, description);
			} else {
				testFailed(notifier, description, e);
			}
		} catch (final Exception e) {
			notifier.fireTestFailure(new Failure(description, e));
		}
	}

	private void testStarted(final RunNotifier notifier, final Description description) {
		notifier.fireTestStarted(description);
	}

	private void testFailed(final RunNotifier notifier, final Description description,
			InvocationTargetException e) {
		notifier.fireTestFailure(new Failure(description, e));
	}

	private boolean theExpectedHappened(
			Class<? extends Throwable> expectedException,
			InvocationTargetException e) {
		return expectedException == e.getTargetException().getClass();
	}

	private void testSucceeded(final RunNotifier notifier, final Description description) {
		notifier.fireTestFinished(description);
	}

	private Object runTest(final Method test) throws IllegalAccessException,
			InvocationTargetException {
		return test.invoke(interfaceTest, (Object[]) null);
	}

	private boolean isNotExpected(final Class<? extends Throwable> expected) {
		return expected == org.junit.Test.None.class;
	}

    @Retention(RetentionPolicy.RUNTIME)
    public @interface Options {
        String basePackage();
    }
}
