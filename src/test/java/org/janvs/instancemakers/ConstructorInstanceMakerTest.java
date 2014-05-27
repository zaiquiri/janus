package org.janvs.instancemakers;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ConstructorInstanceMakerTest {

    @Test
    public void shouldReturnOneInstanceWhenClassHasOneNoArgConstructor() {
        final Class<? extends DefaultConstructorOnly> defaultConstructorOnly = new DefaultConstructorOnly().getClass();

        final List<Object> instances = new ConstructorInstanceMaker().createInstancesOf(defaultConstructorOnly);

        assertThat(instances.size(), is(1));
        assertTrue(defaultConstructorOnly.isInstance(instances.get(0)));
    }

    @Test
    public void shouldReturnTwoInstancesWhenClassHasOneConstructorWithOneArgument() {
        final Class<? extends OneConstructorOneArg> oneConstructorOneArg = new OneConstructorOneArg(new ArrayList()).getClass();

        final List<Object> instances = new ConstructorInstanceMaker().createInstancesOf(oneConstructorOneArg);
        assertThat(instances.size(), is(2));
        for (Object instance : instances){
            assertTrue(oneConstructorOneArg.isInstance(instance));
        }
    }

    @Test
    public void shouldReturnFourInstancesWhenClassHasOneConstructorWithOnePrimitiveAndOneObjectArg() {
        final Class<? extends OnePrimitiveAndOneObjectConstructorOneArg> primitiveAndObjectArgs = new OnePrimitiveAndOneObjectConstructorOneArg(new ArrayList(), 0).getClass();

        final List<Object> instances = new ConstructorInstanceMaker().createInstancesOf(primitiveAndObjectArgs);

        assertThat(instances.size(), is(4));
        for (Object instance : instances){
            assertTrue(primitiveAndObjectArgs.isInstance(instance));
        }
    }

    @Test
    public void shouldReturnThreeInstancesWhenClassHasOneDefaultConstructorAndOneConstructorTakingOneArg() {
        final Class<? extends DefaultAndOneArg> defaultAndOneArg = new DefaultAndOneArg().getClass();

        final List<Object> instances = new ConstructorInstanceMaker().createInstancesOf(defaultAndOneArg);

        assertThat(instances.size(), is(3));
        for (Object instance : instances) {
            assertTrue(defaultAndOneArg.isInstance(instance));
        }
    }

    @Test
    public void shouldReturnTwoInstancesWhenConstructorTakesAFinalClassAsArg() {
        final Class<? extends FinalArgConstructor> finalArgConstructor = new FinalArgConstructor("").getClass();

        final List<Object> instances = new ConstructorInstanceMaker().createInstancesOf(finalArgConstructor);

        assertThat(instances.size(), is(2));
        for (Object instance : instances){
            assertTrue(finalArgConstructor.isInstance(instance));
        }
    }

    static class DefaultConstructorOnly {
        public DefaultConstructorOnly(){}
    }

    static class OneConstructorOneArg {
        public OneConstructorOneArg(ArrayList a){}
    }

    static class OnePrimitiveAndOneObjectConstructorOneArg {
        public OnePrimitiveAndOneObjectConstructorOneArg(ArrayList b, double a){}
    }

    static class DefaultAndOneArg {
        public DefaultAndOneArg(){}
        public DefaultAndOneArg(int a){}
    }

    static class FinalArgConstructor {
        public FinalArgConstructor(String a){}
    }
}
