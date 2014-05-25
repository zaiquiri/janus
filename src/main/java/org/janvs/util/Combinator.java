package org.janvs.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

import static org.janvs.util.Booleans.getBooleanRepresentationFor;
import static org.janvs.util.Instantiation.mockFor;
import static org.janvs.util.Instantiation.nullFor;


public class Combinator {

    public static Collection getAllParameterCombinations(final Method factory) {
        final Class<?>[] parameterTypes = factory.getParameterTypes();
        return getAllCombos(parameterTypes);
    }

    static private ArrayList getAllCombos(final Class<?>[] parameters) {
        final ArrayList<Object> allCombos = new ArrayList<>();
        final int numberOfParams = parameters.length;
        final double numberOfCombos = Math.pow(2, numberOfParams);

        for (int comboNumber = 0; comboNumber < numberOfCombos; comboNumber++) {
            boolean[] booleans = getBooleanRepresentationFor(comboNumber, numberOfParams);
            allCombos.add(makeCombo(parameters, booleans));
        }
        return allCombos;
    }

    static private ArrayList<Object> makeCombo(final Class<?>[] parameters, final boolean[] booleanSchema) {
        final ArrayList<Object> combo = new ArrayList<>();
        final int numberOfParametersNeeded = parameters.length;

        while (combo.size() < numberOfParametersNeeded) {
            final Class<?> parameter = parameters[combo.size()];
            if (booleanSchema[combo.size()]) {
                combo.add(mockFor(parameter));
                mockFor(parameter);
            } else {
                combo.add(nullFor(parameter));
                nullFor(parameter);
            }
        }
        return combo;
    }



}
