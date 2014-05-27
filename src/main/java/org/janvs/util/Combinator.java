package org.janvs.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static org.janvs.util.BooleanMaker.getBooleanRepresentationFor;
import static org.janvs.util.Instantiatior.mockFor;
import static org.janvs.util.Instantiatior.nullFor;


public class Combinator {

    public static Collection getAllCombosOf(final Class[] parameters) {
        final ArrayList<Object> allCombos = new ArrayList<>();
        final int numberOfParams = parameters.length;
        final int numberOfCombos = (int) Math.pow(2, numberOfParams);

        for (int comboNumber = 0; comboNumber < numberOfCombos; comboNumber++) {
            boolean[] booleans = getBooleanRepresentationFor(comboNumber, numberOfParams);
            allCombos.add(makeCombo(parameters, booleans));
        }
        return allCombos;
    }

    static private List<Object> makeCombo(final Class<?>[] parameters, final boolean[] booleanSchema) {
        final LinkedList<Object> combo = new LinkedList<>();
        final int numberOfParametersNeeded = parameters.length;

        while (combo.size() < numberOfParametersNeeded) {
            final Class<?> parameter = parameters[combo.size()];
            if (booleanSchema[combo.size()]) {
                combo.add(mockFor(parameter));
            } else {
                combo.add(nullFor(parameter));
            }
        }
        return combo;
    }



}
