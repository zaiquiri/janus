package org.janvs.instantiators;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

public class FactoryInstanceMakerTest {

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
        for (Object instance : instances) {
            assertTrue(oneFactoryOneArg.isInstance(instance));
        }
    }

    @Test
    public void shouldReturnFourInstancesWhenOneFactoryConstructorTakesTwoArguments() {
        final Class<? extends OneFactoryTwoArgs> oneFactoryTwoArgs = new OneFactoryTwoArgs().getClass();

        final List<Object> instances = new FactoryInstanceMaker().createInstancesOf(oneFactoryTwoArgs);

        assertThat(instances.size(), is(4));
        for (Object instance : instances) {
            assertTrue(oneFactoryTwoArgs.isInstance(instance));
        }
    }

    @Test
    public void shouldReturnEightInstancesWhenTwoFactoryConstructorsTakeTwoArguments() {
        final Class<? extends TwoFactoriesTwoArgs> twoFactoriesTwoArgs = new TwoFactoriesTwoArgs().getClass();

        final List<Object> instances = new FactoryInstanceMaker().createInstancesOf(twoFactoriesTwoArgs);

        assertThat(instances.size(), is(8));
        for (Object instance : instances) {
            assertTrue(twoFactoriesTwoArgs.isInstance(instance));
        }
    }

    @Test
    public void shouldCreateTwoInstancesWhenOnFactoryMethodTakesOnePrimitiveTypeAsArgument() {
        final Class<? extends PrimitiveArgs> primitiveArg = new PrimitiveArgs().getClass();

        final List<Object> instances = new FactoryInstanceMaker().createInstancesOf(primitiveArg);

        assertThat(instances.size(), is(2));
        for (Object instance : instances){
            assertTrue(primitiveArg.isInstance(instance));
        }
    }

//    @Test
//    public void shouldMakeInstanceWithAnInstanceOfItParameterClassEvenWhenThatClassIsFinal(){
//        final Class<? extends ClassWithFinalParam> oneFactoryOneArg = new ClassWithFinalParam().getClass();
//
//        final List<Object> instances = new FactoryInstanceMaker().createInstancesOf(oneFactoryOneArg);
//
//        final ClassWithFinalParam myClass = (ClassWithFinalParam) instances.get(0);
//        assertTrue(String.class.isInstance(myClass.param));
//    }
//


    class NoFactories {
    }

    static class OneFactoryNoArgs {
        public static OneFactoryNoArgs makeMe() {
            return new OneFactoryNoArgs();
        }
    }

    static class OneFactoryOneArg {

        private ArrayList arg;

        public static OneFactoryOneArg makeMe(ArrayList arg) {
            return new OneFactoryOneArg(arg);
        }

        private OneFactoryOneArg(ArrayList arg) {
            this.arg = arg;
        }

        public OneFactoryOneArg() {
        }

    }

    static class OneFactoryTwoArgs {

        private ArrayList arg1;
        private ArrayList arg2;

        public static OneFactoryTwoArgs makeMe(ArrayList arg1, ArrayList arg2) {
            return new OneFactoryTwoArgs(arg1, arg2);
        }

        private OneFactoryTwoArgs(ArrayList arg1, ArrayList arg2) {
            this.arg1 = arg1;
            this.arg2 = arg2;
        }

        public OneFactoryTwoArgs() {
        }
    }

    static class TwoFactoriesTwoArgs {
        private List arg1;
        private List arg2;

        public static TwoFactoriesTwoArgs makeMe(ArrayList arg1, LinkedList arg2) {
            return new TwoFactoriesTwoArgs(arg1, arg2);
        }

        public static TwoFactoriesTwoArgs makeMeAgain(LinkedList arg1, ArrayList arg2) {
            return new TwoFactoriesTwoArgs(arg1, arg2);
        }

        private TwoFactoriesTwoArgs(List arg1, List arg2) {
            this.arg1 = arg1;
            this.arg2 = arg2;
        }

        public TwoFactoriesTwoArgs() {
        }
    }

    static class PrimitiveArgs {
        public static PrimitiveArgs makeMe(int i){
            return new PrimitiveArgs();
        }

        public PrimitiveArgs() {

        }
    }

    static class OnePrimitiveOneObjectArg {

    }


    private static class ClassWithFinalParam {

        String param;

        public static ClassWithFinalParam makeMe(String finalParam) {
            return new ClassWithFinalParam(finalParam);
        }

        private ClassWithFinalParam(String finalParam) {
            this.param = finalParam;
        }

        public ClassWithFinalParam() {
        }
    }
}





