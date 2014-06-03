package org.janvs.util;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BooleanMakerTest {

    @Test
    public void shouldReturnOneFalseWhenZeroToOnePlace() {
        int value = 0;
        int numberOfPlaces = 1;
        final boolean[] booleans = BooleanMaker.getBooleanRepresentationFor(value, numberOfPlaces);

        assertThat(booleans.length, is(1));
        assertThat(booleans[0], is(false));
    }

    @Test
    public void shouldReturnThreeFalsesWhenZeroToThreePlaces() {
        int value = 0;
        int numberOfPlaces = 3;
        final boolean[] booleans = BooleanMaker.getBooleanRepresentationFor(value, numberOfPlaces);

        assertThat(booleans.length, is(3));
        assertThat(booleans[0], is(false));
        assertThat(booleans[1], is(false));
        assertThat(booleans[2], is(false));
    }

    @Test
    public void shouldReturnOneTrueWhenOneToOnePlace() {
        int value = 1;
        int numberOfPlaces = 1;
        final boolean[] booleans = BooleanMaker.getBooleanRepresentationFor(value, numberOfPlaces);

        assertThat(booleans.length, is(1));
        assertThat(booleans[0], is(true));
    }

    @Test
    public void shouldReturnTwoFalsesAndOneTrueWhenOneToThreePlaces() {
        int value = 1;
        int numberOfPlaces = 3;
        final boolean[] booleans = BooleanMaker.getBooleanRepresentationFor(value, numberOfPlaces);

        assertThat(booleans.length, is(3));
        assertThat(booleans[0], is(false));
        assertThat(booleans[1], is(false));
        assertThat(booleans[2], is(true));
    }

    @Test
    public void shouldReturnFalseTrueFalseTrueFalseForTenToFivePlaces() {
        int value = 10;
        int numberOfPlaces = 5;
        final boolean[] booleans = BooleanMaker.getBooleanRepresentationFor(value, numberOfPlaces);

        assertThat(booleans.length, is(5));
        assertThat(booleans[0], is(false));
        assertThat(booleans[1], is(true));
        assertThat(booleans[2], is(false));
        assertThat(booleans[3], is(true));
        assertThat(booleans[4], is(false));
    }

    @Test
    public void shouldReturnTrueFalseTrueFalseForSevenToFourPlaces() {
        int value = 10;
        int numberOfPlaces = 4;
        final boolean[] booleans = BooleanMaker.getBooleanRepresentationFor(value, numberOfPlaces);

        assertThat(booleans.length, is(4));
        assertThat(booleans[0], is(true));
        assertThat(booleans[1], is(false));
        assertThat(booleans[2], is(true));
        assertThat(booleans[3], is(false));
    }

}
