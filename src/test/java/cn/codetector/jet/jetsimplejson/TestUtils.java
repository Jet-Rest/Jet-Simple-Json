package cn.codetector.jet.jetsimplejson;
/*
 *
 *  * Copyright 2014 Red Hat, Inc.
 *  *
 *  * All rights reserved. This program and the accompanying materials
 *  * are made available under the terms of the Eclipse Public License v1.0
 *  * and Apache License v2.0 which accompanies this distribution.
 *  *
 *  *     The Eclipse Public License is available at
 *  *     http://www.eclipse.org/legal/epl-v10.html
 *  *
 *  *     The Apache License v2.0 is available at
 *  *     http://www.opensource.org/licenses/apache2.0.php
 *  *
 *  * You may elect to redistribute this code under either of these licenses.
 *  *
 *
 */


import java.io.ByteArrayOutputStream;
import java.util.EnumSet;
import java.util.Random;
import java.util.Set;
import java.util.zip.GZIPOutputStream;

import static org.junit.Assert.fail;


/**
 * Copied from Red Hat Repo. Adapted for own use
 */
public class TestUtils {

    private static Random random = new Random();


    /**
     * Create an array of random bytes
     * @param length The length of the created array
     * @return the byte array
     */
    public static byte[] randomByteArray(int length) {
        return randomByteArray(length, false, (byte) 0);
    }

    /**
     * Create an array of random bytes
     * @param length The length of the created array
     * @param avoid If true, the resulting array will not contain avoidByte
     * @param avoidByte A byte that is not to be included in the resulting array
     * @return an array of random bytes
     */
    public static byte[] randomByteArray(int length, boolean avoid, byte avoidByte) {
        byte[] line = new byte[length];
        for (int i = 0; i < length; i++) {
            byte rand;
            do {
                rand = randomByte();
            } while (avoid && rand == avoidByte);

            line[i] = rand;
        }
        return line;
    }

    /**
     * @return a random byte
     */
    public static byte randomByte() {
        return (byte) ((int) (Math.random() * 255) - 128);
    }

    /**
     * @return a random int
     */
    public static int randomInt() {
        return random.nextInt();
    }

    /**
     * @return a random port
     */
    public static int randomPortInt() {
        return random.nextInt(65536);
    }

    /**
     * @return a random positive int
     */
    public static int randomPositiveInt() {
        while (true) {
            int rand = random.nextInt();
            if (rand > 0) {
                return rand;
            }
        }
    }

    /**
     * @return a random positive long
     */
    public static long randomPositiveLong() {
        while (true) {
            long rand = random.nextLong();
            if (rand > 0) {
                return rand;
            }
        }
    }

    /**
     * @return a random long
     */
    public static long randomLong() {
        return random.nextLong();
    }

    /**
     * @return a random boolean
     */
    public static boolean randomBoolean() {
        return random.nextBoolean();
    }

    /**
     * @return a random char
     */
    public static char randomChar() {
        return (char)(random.nextInt(16));
    }

    /**
     * @return a random short
     */
    public static short randomShort() {
        return (short)(random.nextInt(1 << 15));
    }

    /**
     * @return a random random float
     */
    public static float randomFloat() {
        return random.nextFloat();
    }

    /**
     * @return a random random double
     */
    public static double randomDouble() {
        return random.nextDouble();
    }

    /**
     * Creates a String containing random unicode characters
     * @param length The length of the string to create
     * @return a String of random unicode characters
     */
    public static String randomUnicodeString(int length) {
        StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char c;
            do {
                c = (char) (0xFFFF * Math.random());
            } while ((c >= 0xFFFE && c <= 0xFFFF) || (c >= 0xD800 && c <= 0xDFFF)); //Illegal chars
            builder.append(c);
        }
        return builder.toString();
    }

    /**
     * Creates a random string of ascii alpha characters
     * @param length the length of the string to create
     * @return a String of random ascii alpha characters
     */
    public static String randomAlphaString(int length) {
        StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char c = (char) (65 + 25 * Math.random());
            builder.append(c);
        }
        return builder.toString();
    }

    public static <E extends Enum<E>> Set<E> randomEnumSet(Class<E> enumType) {
        EnumSet<E> set = EnumSet.noneOf(enumType);
        for (E e : EnumSet.allOf(enumType)) {
            if (randomPositiveInt() % 2 == 1) {
                set.add(e);
            }
        }
        return set;
    }

    public static <E> E randomElement(E[] array) {
        return array[randomPositiveInt() % array.length];
    }

    /**
     * Determine if two byte arrays are equal
     * @param b1 The first byte array to compare
     * @param b2 The second byte array to compare
     * @return true if the byte arrays are equal
     */
    public static boolean byteArraysEqual(byte[] b1, byte[] b2) {
        if (b1.length != b2.length) return false;
        for (int i = 0; i < b1.length; i++) {
            if (b1[i] != b2[i]) return false;
        }
        return true;
    }

    /**
     * Asserts that an IllegalArgumentException is thrown by the code block.
     * @param runnable code block to execute
     */
    public static void assertIllegalArgumentException(Runnable runnable) {
        try {
            runnable.run();
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Asserts that a NullPointerException is thrown by the code block.
     * @param runnable code block to execute
     */
    public static void assertNullPointerException(Runnable runnable) {
        try {
            runnable.run();
            fail("Should throw NullPointerException");
        } catch (NullPointerException e) {
            // OK
        }
    }

    /**
     * Asserts that an IllegalStateException is thrown by the code block.
     * @param runnable code block to execute
     */
    public static void assertIllegalStateException(Runnable runnable) {
        try {
            runnable.run();
            fail("Should throw IllegalStateException");
        } catch (IllegalStateException e) {
            // OK
        }
    }

    /**
     * Asserts that an IndexOutOfBoundsException is thrown by the code block.
     * @param runnable code block to execute
     */
    public static void assertIndexOutOfBoundsException(Runnable runnable) {
        try {
            runnable.run();
            fail("Should throw IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            // OK
        }
    }

    /**
     * @param source
     * @return gzipped data
     * @throws Exception
     */
    public static byte[] compressGzip(String source) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        GZIPOutputStream gos = new GZIPOutputStream(baos);
        gos.write(source.getBytes());
        gos.close();
        return baos.toByteArray();
    }
}