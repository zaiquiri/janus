package org.janvs.util;

public class BooleanMaker {

    public static boolean[] getBooleanRepresentationFor(final int value, final int numberOfPlaces) {
        String binary = getBinaryStringOfProperSize(value, numberOfPlaces);
        final boolean[] booleans = convertToBooleans(binary);
        return booleans;
    }

    static private String getBinaryStringOfProperSize(final int value, final int numberOfPlaces) {
        final String binary = Integer.toBinaryString(value);
        if (binary.length() < numberOfPlaces) {
            return padWithZeros(binary, numberOfPlaces);
        }
        return binary;
    }

    static private String padWithZeros(String binary, final double numberOfPlaces) {
        final double numberOfZerosToAdd = numberOfPlaces - binary.length();
        for (int i = 0; i < numberOfZerosToAdd; i++) {
            binary = "0" + binary;
        }
        return binary;
    }

    static private boolean[] convertToBooleans(final String binary) {
        final char[] binaryArray = binary.toCharArray();
        final boolean[] booleans = new boolean[binaryArray.length];
        for (int i = 0; i < binary.length(); i++)
            booleans[i] = binaryArray[i] == '1' ? true : false;
        return booleans;
    }
}
