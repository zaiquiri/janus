package org.janvs.util;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.janvs.util.Combinator.getAllCombosOf;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;

public class CombinatorTest {

    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOURTH = 3;

    @Test
    public void shouldReturnOneEmptyComboForNoArgs() {

        final Class[] parameters = new Class[0];
        final List<List> combos = (List) getAllCombosOf(parameters);

        assertThat(combos.size(), is(1));
        assertThat(combos.get(FIRST).size(), is(0));

    }

    @Test
    public void shouldReturnNullAndMockForOneObjectArg() {
        final Class[] parameters = {Object.class};
        final List<List> combos = (List) getAllCombosOf(parameters);

        final List firstCombo = combos.get(FIRST);
        final List secondCombo = combos.get(SECOND);

        assertThat(combos.size(), is(2));

        assertThat(firstCombo.size(), is(1));
        assertThat(secondCombo.size(), is(1));

        assertNull(firstCombo.get(FIRST));
        assertNotNull(secondCombo.get(FIRST));
    }

    @Test
    public void shouldReturnMaxAndMinValuesForOnePrimitiveArg() {
        final Class[] parameters = {int.class};
        final List<List> combos = (List) getAllCombosOf(parameters);

        final List firstCombo = combos.get(FIRST);
        final List secondCombo = combos.get(SECOND);

        assertThat(combos.size(), is(2));

        assertThat(firstCombo.size(), is(1));
        assertThat(secondCombo.size(), is(1));

        assertThat(firstCombo.get(FIRST), CoreMatchers.<Object>is(Integer.MIN_VALUE));
        assertThat(secondCombo.get(FIRST), CoreMatchers.<Object>is(Integer.MAX_VALUE));
    }

    @Test
    public void shouldReturnProperCombosForTwoArgs() {
        final Class[] parameters = {String.class, Class.class};
        final List<List> combos = (List) getAllCombosOf(parameters);

        final List firstCombo = combos.get(FIRST);
        final List secondCombo = combos.get(SECOND);
        final List thirdCombo = combos.get(THIRD);
        final List fourthCombo = combos.get(FOURTH);

        assertThat(combos.size(), is(4));

        assertThat(firstCombo.size(), is(2));
        assertThat(secondCombo.size(), is(2));
        assertThat(thirdCombo.size(), is(2));
        assertThat(fourthCombo.size(), is(2));

        assertNull(firstCombo.get(FIRST));
        assertNull(firstCombo.get(SECOND));

        assertNull(secondCombo.get(FIRST));
        assertThat(secondCombo.get(SECOND), CoreMatchers.<Object>is(Class.class));

        assertThat(thirdCombo.get(FIRST), CoreMatchers.<Object>is(any(String.class)));
        assertNull(thirdCombo.get(SECOND));

        assertThat(fourthCombo.get(FIRST), CoreMatchers.<Object>is(any(String.class)));
        assertThat(fourthCombo.get(SECOND), CoreMatchers.<Object>is(Class.class));

    }

}
