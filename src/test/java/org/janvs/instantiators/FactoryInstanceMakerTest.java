package org.janvs.instantiators;

import org.hamcrest.Matcher;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

public class FactoryInstanceMakerTest {

    class NoFactories {
    }

    static class OneFactoryNoArgs {
        public static OneFactoryNoArgs makeMe() {
            return new OneFactoryNoArgs();
        }
    }

    static class OneFactoryOneArg {

        private ArrayList arg;

        public static OneFactoryOneArg makMe(ArrayList arg) {
            return new OneFactoryOneArg(arg);
        }

        private OneFactoryOneArg(ArrayList arg){
            this.arg = arg;
        }

        public OneFactoryOneArg(){}

    }

    static class OneFactoryTwoArgs {

    }

    static class TwoFactoriesTwoArgs {

    }

    static class PrimitiveArgs {

    }

    static class OnePrimitiveOneObjectArg {

    }


    @Test
    public void shouldReturnEmptyListWhenImplementorHasNoFactoryConstructors() {

        final Class<? extends NoFactories> ihaveNoFactories = new NoFactories().getClass();

        final Collection<Object> instances = new FactoryInstanceMaker().createInstancesOf(ihaveNoFactories);

        assertTrue(instances.isEmpty());

    }


    @Test
    public void shouldReturnOneInstanceWhenFactoryConstructorTakesNoArguments() {

        final Class<? extends OneFactoryNoArgs> oneFactoryNoArgs = new OneFactoryNoArgs().getClass();

        final List<Object> instances = new FactoryInstanceMaker().createInstancesOf(oneFactoryNoArgs);

        assertThat(instances.size(), is(1));
        assertTrue(oneFactoryNoArgs.isInstance(instances.get(0)));
    }

    @Test
    public void shouldReturnTwoInstancesWhenOneFactoryConstructorTakesOneArgument() {
        final Class<? extends OneFactoryOneArg> oneFactoryOneArg = new OneFactoryOneArg().getClass();

        final List<Object> instances = new FactoryInstanceMaker().createInstancesOf(oneFactoryOneArg);

        assertThat(instances.size(), is(2));
        for (Object instance : instances){
            assertTrue(oneFactoryOneArg.isInstance(instance));
        }
    }

}
