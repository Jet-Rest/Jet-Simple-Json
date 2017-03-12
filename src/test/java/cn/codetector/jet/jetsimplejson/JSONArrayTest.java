package cn.codetector.jet.jetsimplejson;/*
 * Copyright (c) 2011-2014 The original author or authors
 * ------------------------------------------------------
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution.
 *
 *     The Eclipse Public License is available at
 *     http://www.eclipse.org/legal/epl-v10.html
 *
 *     The Apache License v2.0 is available at
 *     http://www.opensource.org/licenses/apache2.0.php
 *
 * You may elect to redistribute this code under either of these licenses.
 */


import cn.codetector.jet.jetsimplejson.exception.DecodeException;
import org.junit.*;
import static org.junit.Assert.*;



import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.time.format.DateTimeFormatter.ISO_INSTANT;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class JSONArrayTest {

    private JSONArray jsonArray;

    @Before
    public void setUp() {
        jsonArray = new JSONArray();
    }

    @Test
    public void testGetInteger() {
        jsonArray.add(123);
        assertEquals(Integer.valueOf(123), jsonArray.getInteger(0));
        try {
            jsonArray.getInteger(-1);
            fail("");
        } catch (IndexOutOfBoundsException e) {
            // OK
        }
        try {
            jsonArray.getInteger(1);
            fail("");
        } catch (IndexOutOfBoundsException e) {
            // OK
        }
        // Different number types
        jsonArray.add(123l);
        assertEquals(Integer.valueOf(123), jsonArray.getInteger(1));
        jsonArray.add(123f);
        assertEquals(Integer.valueOf(123), jsonArray.getInteger(2));
        jsonArray.add(123d);
        assertEquals(Integer.valueOf(123), jsonArray.getInteger(3));
        jsonArray.add("foo");
        try {
            jsonArray.getInteger(4);
            fail("");
        } catch (ClassCastException e) {
            // OK
        }
        jsonArray.addNull();
        assertNull(jsonArray.getInteger(5));
    }

    @Test
    public void testGetLong() {
        jsonArray.add(123l);
        assertEquals(Long.valueOf(123l), jsonArray.getLong(0));
        try {
            jsonArray.getLong(-1);
            fail("");
        } catch (IndexOutOfBoundsException e) {
            // OK
        }
        try {
            jsonArray.getLong(1);
            fail("");
        } catch (IndexOutOfBoundsException e) {
            // OK
        }
        // Different number types
        jsonArray.add(123);
        assertEquals(Long.valueOf(123l), jsonArray.getLong(1));
        jsonArray.add(123f);
        assertEquals(Long.valueOf(123l), jsonArray.getLong(2));
        jsonArray.add(123d);
        assertEquals(Long.valueOf(123l), jsonArray.getLong(3));
        jsonArray.add("foo");
        try {
            jsonArray.getLong(4);
            fail("");
        } catch (ClassCastException e) {
            // OK
        }
        jsonArray.addNull();
        assertNull(jsonArray.getLong(5));
    }

    @Test
    public void testGetFloat() {
        jsonArray.add(123f);
        assertEquals(Float.valueOf(123f), jsonArray.getFloat(0));
        try {
            jsonArray.getFloat(-1);
            fail("");
        } catch (IndexOutOfBoundsException e) {
            // OK
        }
        try {
            jsonArray.getFloat(1);
            fail("");
        } catch (IndexOutOfBoundsException e) {
            // OK
        }
        // Different number types
        jsonArray.add(123);
        assertEquals(Float.valueOf(123f), jsonArray.getFloat(1));
        jsonArray.add(123);
        assertEquals(Float.valueOf(123f), jsonArray.getFloat(2));
        jsonArray.add(123d);
        assertEquals(Float.valueOf(123f), jsonArray.getFloat(3));
        jsonArray.add("foo");
        try {
            jsonArray.getFloat(4);
            fail("");
        } catch (ClassCastException e) {
            // OK
        }
        jsonArray.addNull();
        assertNull(jsonArray.getFloat(5));
    }

    @Test
    public void testGetDouble() {
        jsonArray.add(123d);
        assertEquals(Double.valueOf(123d), jsonArray.getDouble(0));
        try {
            jsonArray.getDouble(-1);
            fail("");
        } catch (IndexOutOfBoundsException e) {
            // OK
        }
        try {
            jsonArray.getDouble(1);
            fail("");
        } catch (IndexOutOfBoundsException e) {
            // OK
        }
        // Different number types
        jsonArray.add(123);
        assertEquals(Double.valueOf(123d), jsonArray.getDouble(1));
        jsonArray.add(123);
        assertEquals(Double.valueOf(123d), jsonArray.getDouble(2));
        jsonArray.add(123d);
        assertEquals(Double.valueOf(123d), jsonArray.getDouble(3));
        jsonArray.add("foo");
        try {
            jsonArray.getDouble(4);
            fail("");
        } catch (ClassCastException e) {
            // OK
        }
        jsonArray.addNull();
        assertNull(jsonArray.getDouble(5));
    }

    @Test
    public void testGetString() {
        jsonArray.add("foo");
        assertEquals("foo", jsonArray.getString(0));
        try {
            jsonArray.getString(-1);
            fail("");
        } catch (IndexOutOfBoundsException e) {
            // OK
        }
        try {
            jsonArray.getString(1);
            fail("");
        } catch (IndexOutOfBoundsException e) {
            // OK
        }
        jsonArray.add(123);
        try {
            jsonArray.getString(1);
            fail("");
        } catch (ClassCastException e) {
            // OK
        }
        jsonArray.addNull();
        assertNull(jsonArray.getString(2));
    }

    @Test
    public void testGetBoolean() {
        jsonArray.add(true);
        assertEquals(true, jsonArray.getBoolean(0));
        jsonArray.add(false);
        assertEquals(false, jsonArray.getBoolean(1));
        try {
            jsonArray.getBoolean(-1);
            fail("");
        } catch (IndexOutOfBoundsException e) {
            // OK
        }
        try {
            jsonArray.getBoolean(2);
            fail("");
        } catch (IndexOutOfBoundsException e) {
            // OK
        }
        jsonArray.add(123);
        try {
            jsonArray.getBoolean(2);
            fail("");
        } catch (ClassCastException e) {
            // OK
        }
        jsonArray.addNull();
        assertNull(jsonArray.getBoolean(3));
    }

    @Test
    public void testGetBinary() {
        byte[] bytes = TestUtils.randomByteArray(10);
        jsonArray.add(bytes);
        assertTrue(TestUtils.byteArraysEqual(bytes, jsonArray.getBinary(0)));
        assertTrue(TestUtils.byteArraysEqual(bytes, Base64.getDecoder().decode(jsonArray.getString(0))));
        try {
            jsonArray.getBinary(-1);
            fail("");
        } catch (IndexOutOfBoundsException e) {
            // OK
        }
        try {
            jsonArray.getBinary(1);
            fail("");
        } catch (IndexOutOfBoundsException e) {
            // OK
        }
        jsonArray.add(123);
        try {
            jsonArray.getBinary(1);
            fail("");
        } catch (ClassCastException e) {
            // OK
        }
        jsonArray.addNull();
        assertNull(jsonArray.getBinary(2));
    }

    @Test
    public void testGetInstant() {
        Instant now = Instant.now();
        jsonArray.add(now);
        assertEquals(now, jsonArray.getInstant(0));
        assertEquals(now, Instant.from(ISO_INSTANT.parse(jsonArray.getString(0))));
        try {
            jsonArray.getInstant(-1);
            fail("");
        } catch (IndexOutOfBoundsException e) {
            // OK
        }
        try {
            jsonArray.getInstant(1);
            fail("");
        } catch (IndexOutOfBoundsException e) {
            // OK
        }
        jsonArray.add(123);
        try {
            jsonArray.getInstant(1);
            fail("");
        } catch (ClassCastException e) {
            // OK
        }
        jsonArray.addNull();
        assertNull(jsonArray.getInstant(2));
    }

    @Test
    public void testGetJSONObject() {
        JSONObject obj = new JSONObject().put("foo", "bar");
        jsonArray.add(obj);
        assertEquals(obj, jsonArray.getJSONObject(0));
        try {
            jsonArray.getJSONObject(-1);
            fail("");
        } catch (IndexOutOfBoundsException e) {
            // OK
        }
        try {
            jsonArray.getJSONObject(1);
            fail("");
        } catch (IndexOutOfBoundsException e) {
            // OK
        }
        jsonArray.add(123);
        try {
            jsonArray.getJSONObject(1);
            fail("");
        } catch (ClassCastException e) {
            // OK
        }
        jsonArray.addNull();
        assertNull(jsonArray.getJSONObject(2));
    }

    @Test
    public void testGetJSONArray() {
        JSONArray arr = new JSONArray().add("foo");
        jsonArray.add(arr);
        assertEquals(arr, jsonArray.getJSONArray(0));
        try {
            jsonArray.getJSONArray(-1);
            fail("");
        } catch (IndexOutOfBoundsException e) {
            // OK
        }
        try {
            jsonArray.getJSONArray(1);
            fail("");
        } catch (IndexOutOfBoundsException e) {
            // OK
        }
        jsonArray.add(123);
        try {
            jsonArray.getJSONArray(1);
            fail("");
        } catch (ClassCastException e) {
            // OK
        }
        jsonArray.addNull();
        assertNull(jsonArray.getJSONArray(2));
    }

    @Test
    public void testGetValue() {
        jsonArray.add(123);
        assertEquals(123, jsonArray.getValue(0));
        jsonArray.add(123l);
        assertEquals(123l, jsonArray.getValue(1));
        jsonArray.add(123f);
        assertEquals(123f, jsonArray.getValue(2));
        jsonArray.add(123d);
        assertEquals(123d, jsonArray.getValue(3));
        jsonArray.add(false);
        assertEquals(false, jsonArray.getValue(4));
        jsonArray.add(true);
        assertEquals(true, jsonArray.getValue(5));
        jsonArray.add("bar");
        assertEquals("bar", jsonArray.getValue(6));
        JSONObject obj = new JSONObject().put("blah", "wibble");
        jsonArray.add(obj);
        assertEquals(obj, jsonArray.getValue(7));
        JSONArray arr = new JSONArray().add("blah").add("wibble");
        jsonArray.add(arr);
        assertEquals(arr, jsonArray.getValue(8));
        byte[] bytes = TestUtils.randomByteArray(100);
        jsonArray.add(bytes);
        assertTrue(TestUtils.byteArraysEqual(bytes, Base64.getDecoder().decode((String) jsonArray.getValue(9))));
        Instant now = Instant.now();
        jsonArray.add(now);
        assertEquals(now, jsonArray.getInstant(10));
        jsonArray.addNull();
        assertNull(jsonArray.getValue(11));
        try {
            jsonArray.getValue(-1);
            fail("");
        } catch (IndexOutOfBoundsException e) {
            // OK
        }
        try {
            jsonArray.getValue(12);
            fail("");
        } catch (IndexOutOfBoundsException e) {
            // OK
        }
        // JSONObject with inner Map
        List<Object> list = new ArrayList<>();
        Map<String, Object> innerMap = new HashMap<>();
        innerMap.put("blah", "wibble");
        list.add(innerMap);
        jsonArray = new JSONArray(list);
        obj = (JSONObject)jsonArray.getValue(0);
        assertEquals("wibble", obj.getString("blah"));
        // JSONObject with inner List
        list = new ArrayList<>();
        List<Object> innerList = new ArrayList<>();
        innerList.add("blah");
        list.add(innerList);
        jsonArray = new JSONArray(list);
        arr = (JSONArray)jsonArray.getValue(0);
        assertEquals("blah", arr.getString(0));
    }

    enum SomeEnum {
        FOO, BAR
    }

    @Test
    public void testAddEnum() {
        assertSame(jsonArray, jsonArray.add(JSONObjectTest.SomeEnum.FOO));
        assertEquals(JSONObjectTest.SomeEnum.FOO.toString(), jsonArray.getString(0));
        try {
            jsonArray.add((JSONObjectTest.SomeEnum)null);
            fail("");
        } catch (NullPointerException e) {
            // OK
        }
    }

    @Test
    public void testAddString() {
        assertSame(jsonArray, jsonArray.add("foo"));
        assertEquals("foo", jsonArray.getString(0));
        try {
            jsonArray.add((String)null);
            fail("");
        } catch (NullPointerException e) {
            // OK
        }
    }

    @Test
    public void testAddCharSequence() {
        assertSame(jsonArray, jsonArray.add(new StringBuilder("bar")));
        assertEquals("bar", jsonArray.getString(0));
        try {
            jsonArray.add((CharSequence) null);
            fail("");
        } catch (NullPointerException e) {
            // OK
        }
    }

    @Test
    public void testAddInteger() {
        assertSame(jsonArray, jsonArray.add(123));
        assertEquals(Integer.valueOf(123), jsonArray.getInteger(0));
        try {
            jsonArray.add((Integer)null);
            fail("");
        } catch (NullPointerException e) {
            // OK
        }
    }

    @Test
    public void testAddLong() {
        assertSame(jsonArray, jsonArray.add(123l));
        assertEquals(Long.valueOf(123l), jsonArray.getLong(0));
        try {
            jsonArray.add((Long)null);
            fail("");
        } catch (NullPointerException e) {
            // OK
        }
    }

    @Test
    public void testAddFloat() {
        assertSame(jsonArray, jsonArray.add(123f));
        assertEquals(Float.valueOf(123f), jsonArray.getFloat(0));
        try {
            jsonArray.add((Float)null);
            fail("");
        } catch (NullPointerException e) {
            // OK
        }
    }

    @Test
    public void testAddDouble() {
        assertSame(jsonArray, jsonArray.add(123d));
        assertEquals(Double.valueOf(123d), jsonArray.getDouble(0));
        try {
            jsonArray.add((Double)null);
            fail("");
        } catch (NullPointerException e) {
            // OK
        }
    }

    @Test
    public void testAddBoolean() {
        assertSame(jsonArray, jsonArray.add(true));
        assertEquals(true, jsonArray.getBoolean(0));
        jsonArray.add(false);
        assertEquals(false, jsonArray.getBoolean(1));
        try {
            jsonArray.add((Boolean)null);
            fail("");
        } catch (NullPointerException e) {
            // OK
        }
    }

    @Test
    public void testAddJSONObject() {
        JSONObject obj = new JSONObject().put("foo", "bar");
        assertSame(jsonArray, jsonArray.add(obj));
        assertEquals(obj, jsonArray.getJSONObject(0));
        try {
            jsonArray.add((JSONObject)null);
            fail("");
        } catch (NullPointerException e) {
            // OK
        }
    }

    @Test
    public void testAddJSONArray() {
        JSONArray arr = new JSONArray().add("foo");
        assertSame(jsonArray, jsonArray.add(arr));
        assertEquals(arr, jsonArray.getJSONArray(0));
        try {
            jsonArray.add((JSONArray)null);
            fail("");
        } catch (NullPointerException e) {
            // OK
        }
    }

    @Test
    public void testAddBinary() {
        byte[] bytes = TestUtils.randomByteArray(10);
        assertSame(jsonArray, jsonArray.add(bytes));
        assertTrue(TestUtils.byteArraysEqual(bytes, jsonArray.getBinary(0)));
        try {
            jsonArray.add((byte[])null);
            fail("");
        } catch (NullPointerException e) {
            // OK
        }
    }

    @Test
    public void testAddInstant() {
        Instant now = Instant.now();
        assertSame(jsonArray, jsonArray.add(now));
        assertEquals(now, jsonArray.getInstant(0));
        try {
            jsonArray.add((Instant)null);
            fail("");
        } catch (NullPointerException e) {
            // OK
        }
    }

    @Test
    public void testAddObject() {
        jsonArray.add((Object)"bar");
        jsonArray.add((Object)(Integer.valueOf(123)));
        jsonArray.add((Object)(Long.valueOf(123l)));
        jsonArray.add((Object)(Float.valueOf(1.23f)));
        jsonArray.add((Object)(Double.valueOf(1.23d)));
        jsonArray.add((Object) true);
        byte[] bytes = TestUtils.randomByteArray(10);
        jsonArray.add((Object)(bytes));
        Instant now = Instant.now();
        jsonArray.add(now);
        JSONObject obj = new JSONObject().put("foo", "blah");
        JSONArray arr = new JSONArray().add("quux");
        jsonArray.add((Object)obj);
        jsonArray.add((Object)arr);
        assertEquals("bar", jsonArray.getString(0));
        assertEquals(Integer.valueOf(123), jsonArray.getInteger(1));
        assertEquals(Long.valueOf(123l), jsonArray.getLong(2));
        assertEquals(Float.valueOf(1.23f), jsonArray.getFloat(3));
        assertEquals(Double.valueOf(1.23d), jsonArray.getDouble(4));
        assertEquals(true, jsonArray.getBoolean(5));
        assertTrue(TestUtils.byteArraysEqual(bytes, jsonArray.getBinary(6)));
        assertEquals(now, jsonArray.getInstant(7));
        assertEquals(obj, jsonArray.getJSONObject(8));
        assertEquals(arr, jsonArray.getJSONArray(9));
        try {
            jsonArray.add(new SomeClass());
            fail("");
        } catch (IllegalStateException e) {
            // OK
        }
        try {
            jsonArray.add(new BigDecimal(123));
            fail("");
        } catch (IllegalStateException e) {
            // OK
        }
        try {
            jsonArray.add(new Date());
            fail("");
        } catch (IllegalStateException e) {
            // OK
        }

    }

    @Test
    public void testAddAllJSONArray() {
        jsonArray.add("bar");
        JSONArray arr = new JSONArray().add("foo").add(48);
        assertSame(jsonArray, jsonArray.addAll(arr));
        assertEquals(arr.getString(0), jsonArray.getString(1));
        assertEquals(arr.getInteger(1), jsonArray.getInteger(2));
        try {
            jsonArray.add((JSONArray)null);
            fail("");
        } catch (NullPointerException e) {
            // OK
        }
    }

    @Test
    public void testAddNull() {
        assertSame(jsonArray, jsonArray.addNull());
        assertEquals(null, jsonArray.getString(0));
        assertTrue(jsonArray.hasNull(0));
    }

    @Test
    public void testHasNull() {
        jsonArray.addNull();
        jsonArray.add("foo");
        assertEquals(null, jsonArray.getString(0));
        assertTrue(jsonArray.hasNull(0));
        assertFalse(jsonArray.hasNull(1));
    }

    @Test
    public void testContains() {
        jsonArray.add("wibble");
        jsonArray.add(true);
        jsonArray.add(123);
        JSONObject obj = new JSONObject();
        JSONArray arr = new JSONArray();
        jsonArray.add(obj);
        jsonArray.add(arr);
        assertFalse(jsonArray.contains("eek"));
        assertFalse(jsonArray.contains(false));
        assertFalse(jsonArray.contains(321));
        assertFalse(jsonArray.contains(new JSONObject().put("blah", "flib")));
        assertFalse(jsonArray.contains(new JSONArray().add("oob")));
        assertTrue(jsonArray.contains("wibble"));
        assertTrue(jsonArray.contains(true));
        assertTrue(jsonArray.contains(123));
        assertTrue(jsonArray.contains(obj));
        assertTrue(jsonArray.contains(arr));
    }

    @Test
    public void testRemoveByObject() {
        jsonArray.add("wibble");
        jsonArray.add(true);
        jsonArray.add(123);
        assertEquals(3, jsonArray.size());
        assertTrue(jsonArray.remove("wibble"));
        assertEquals(2, jsonArray.size());
        assertFalse(jsonArray.remove("notthere"));
        assertTrue(jsonArray.remove(true));
        assertTrue(jsonArray.remove(Integer.valueOf(123)));
        assertTrue(jsonArray.isEmpty());
    }

    @Test
    public void testRemoveByPos() {
        jsonArray.add("wibble");
        jsonArray.add(true);
        jsonArray.add(123);
        assertEquals(3, jsonArray.size());
        assertEquals("wibble", jsonArray.remove(0));
        assertEquals(2, jsonArray.size());
        assertEquals(123, jsonArray.remove(1));
        assertEquals(1, jsonArray.size());
        assertEquals(true, jsonArray.remove(0));
        assertTrue(jsonArray.isEmpty());
    }

    @Test
    public void testSize() {
        jsonArray.add("wibble");
        jsonArray.add(true);
        jsonArray.add(123);
        assertEquals(3, jsonArray.size());
    }

    @Test
    public void testClear() {
        jsonArray.add("wibble");
        jsonArray.add(true);
        jsonArray.add(123);
        assertEquals(3, jsonArray.size());
        assertEquals(jsonArray, jsonArray.clear());
        assertEquals(0, jsonArray.size());
        assertTrue(jsonArray.isEmpty());
    }

    @Test
    public void testIterator() {
        jsonArray.add("foo");
        jsonArray.add(123);
        JSONObject obj = new JSONObject().put("foo", "bar");
        jsonArray.add(obj);
        Iterator<Object> iter = jsonArray.iterator();
        assertTrue(iter.hasNext());
        Object entry = iter.next();
        assertEquals("foo", entry);
        assertTrue(iter.hasNext());
        entry = iter.next();
        assertEquals(123, entry);
        assertTrue(iter.hasNext());
        entry = iter.next();
        assertEquals(obj, entry);
        assertFalse(iter.hasNext());
        iter.remove();
        assertFalse(jsonArray.contains(obj));
        assertEquals(2, jsonArray.size());
    }

    @Test
    public void testStream() {
        jsonArray.add("foo");
        jsonArray.add(123);
        JSONObject obj = new JSONObject().put("foo", "bar");
        jsonArray.add(obj);
        List<Object> list = jsonArray.stream().collect(Collectors.toList());
        Iterator<Object> iter = list.iterator();
        assertTrue(iter.hasNext());
        Object entry = iter.next();
        assertEquals("foo", entry);
        assertTrue(iter.hasNext());
        entry = iter.next();
        assertEquals(123, entry);
        assertTrue(iter.hasNext());
        entry = iter.next();
        assertEquals(obj, entry);
        assertFalse(iter.hasNext());
    }

    @Test
    public void testCopy() {
        jsonArray.add("foo");
        jsonArray.add(123);
        JSONObject obj = new JSONObject().put("foo", "bar");
        jsonArray.add(obj);
        jsonArray.add(new StringBuilder("eeek"));
        JSONArray copy = jsonArray.copy();
        assertEquals("eeek", copy.getString(3));
        assertNotSame(jsonArray, copy);
        assertEquals(jsonArray, copy);
        assertEquals(4, copy.size());
        assertEquals("foo", copy.getString(0));
        assertEquals(Integer.valueOf(123), copy.getInteger(1));
        assertEquals(obj, copy.getJSONObject(2));
        assertNotSame(obj, copy.getJSONObject(2));
        copy.add("foo");
        assertEquals(4, jsonArray.size());
        jsonArray.add("bar");
        assertEquals(5, copy.size());
    }

    @Test
    public void testInvalidValsOnCopy() {
        List<Object> invalid = new ArrayList<>();
        invalid.add(new SomeClass());
        JSONArray arr = new JSONArray(invalid);
        try {
            arr.copy();
            fail("");
        } catch (IllegalStateException e) {
            // OK
        }
    }

    @Test
    public void testInvalidValsOnCopy2() {
        List<Object> invalid = new ArrayList<>();
        List<Object> invalid2 = new ArrayList<>();
        invalid2.add(new SomeClass());
        invalid.add(invalid2);
        JSONArray arr = new JSONArray(invalid);
        try {
            arr.copy();
            fail("");
        } catch (IllegalStateException e) {
            // OK
        }
    }

    @Test
    public void testInvalidValsOnCopy3() {
        List<Object> invalid = new ArrayList<>();
        Map<String, Object> invalid2 = new HashMap<>();
        invalid2.put("foo", new SomeClass());
        invalid.add(invalid2);
        JSONArray arr = new JSONArray(invalid);
        try {
            arr.copy();
            fail("");
        } catch (IllegalStateException e) {
            // OK
        }
    }

    class SomeClass {
    }

    @Test
    public void testEncode() throws Exception {
        jsonArray.add("foo");
        jsonArray.add(123);
        jsonArray.add(1234l);
        jsonArray.add(1.23f);
        jsonArray.add(2.34d);
        jsonArray.add(true);
        byte[] bytes = TestUtils.randomByteArray(10);
        jsonArray.add(bytes);
        jsonArray.addNull();
        jsonArray.add(new JSONObject().put("foo", "bar"));
        jsonArray.add(new JSONArray().add("foo").add(123));
        String strBytes = Base64.getEncoder().encodeToString(bytes);
        String expected = "[\"foo\",123,1234,1.23,2.34,true,\"" + strBytes + "\",null,{\"foo\":\"bar\"},[\"foo\",123]]";
        String json = jsonArray.encode();
        assertEquals(expected, json);
    }

    @Test
    public void testDecode() {
        byte[] bytes = TestUtils.randomByteArray(10);
        String strBytes = Base64.getEncoder().encodeToString(bytes);
        Instant now = Instant.now();
        String strInstant = ISO_INSTANT.format(now);
        String json = "[\"foo\",123,1234,1.23,2.34,true,\"" + strBytes + "\",\"" + strInstant + "\",null,{\"foo\":\"bar\"},[\"foo\",123]]";
        JSONArray arr = new JSONArray(json);
        assertEquals("foo", arr.getString(0));
        assertEquals(Integer.valueOf(123), arr.getInteger(1));
        assertEquals(Long.valueOf(1234l), arr.getLong(2));
        assertEquals(Float.valueOf(1.23f), arr.getFloat(3));
        assertEquals(Double.valueOf(2.34d), arr.getDouble(4));
        assertEquals(true, arr.getBoolean(5));
        assertTrue(TestUtils.byteArraysEqual(bytes, arr.getBinary(6)));
        assertEquals(now, arr.getInstant(7));
        assertTrue(arr.hasNull(8));
        JSONObject obj = arr.getJSONObject(9);
        assertEquals("bar", obj.getString("foo"));
        JSONArray arr2 = arr.getJSONArray(10);
        assertEquals("foo", arr2.getString(0));
        assertEquals(Integer.valueOf(123), arr2.getInteger(1));
    }

    @Test
    public void testEncodePrettily() throws Exception {
        jsonArray.add("foo");
        jsonArray.add(123);
        jsonArray.add(1234l);
        jsonArray.add(1.23f);
        jsonArray.add(2.34d);
        jsonArray.add(true);
        byte[] bytes = TestUtils.randomByteArray(10);
        jsonArray.add(bytes);
        jsonArray.addNull();
        jsonArray.add(new JSONObject().put("foo", "bar"));
        jsonArray.add(new JSONArray().add("foo").add(123));
        String strBytes = Base64.getEncoder().encodeToString(bytes);
        String expected = "[ \"foo\", 123, 1234, 1.23, 2.34, true, \"" + strBytes + "\", null, {" + Utils.LINE_SEPARATOR +
                "  \"foo\" : \"bar\"" + Utils.LINE_SEPARATOR +
                "}, [ \"foo\", 123 ] ]";
        String json = jsonArray.encodePrettily();
        assertEquals(expected, json);
    }

    @Test
    public void testToString() {
        jsonArray.add("foo").add(123);
        assertEquals(jsonArray.encode(), jsonArray.toString());
    }

    // Strict JSON doesn't allow comments but we do so users can add comments to config files etc
    @Test
    public void testCommentsInJSON() {
        String jsonWithComments =
                "// single line comment\n" +
                        "/*\n" +
                        "  This is a multi \n" +
                        "  line comment\n" +
                        "*/\n" +
                        "[\n" +
                        "// another single line comment this time inside the JSON array itself\n" +
                        "  \"foo\", \"bar\" // and a single line comment at end of line \n" +
                        "/*\n" +
                        "  This is a another multi \n" +
                        "  line comment this time inside the JSON array itself\n" +
                        "*/\n" +
                        "]";
        JSONArray json = new JSONArray(jsonWithComments);
        assertEquals("[\"foo\",\"bar\"]", json.encode());
    }

    @Test
    public void testInvalidJSON() {
        String invalid = "qiwjdoiqwjdiqwjd";
        try {
            new JSONArray(invalid);
            fail("");
        } catch (DecodeException e) {
            // OK
        }
    }

    @Test
    public void testGetList() {
        JSONObject obj = new JSONObject().put("quux", "wibble");
        jsonArray.add("foo").add(123).add(obj);
        List<Object> list = jsonArray.getList();
        list.remove("foo");
        assertFalse(jsonArray.contains("foo"));
        list.add("floob");
        assertTrue(jsonArray.contains("floob"));
        assertSame(obj, list.get(1));
        obj.remove("quux");
    }

    @Test
    public void testCreateFromList() {
        List<Object> list = new ArrayList<>();
        list.add("foo");
        list.add(123);
        JSONArray arr = new JSONArray(list);
        assertEquals("foo", arr.getString(0));
        assertEquals(Integer.valueOf(123), arr.getInteger(1));
        assertSame(list, arr.getList());
    }

    @Test
    public void testCreateFromListCharSequence() {
        List<Object> list = new ArrayList<>();
        list.add("foo");
        list.add(123);
        list.add(new StringBuilder("eek"));
        JSONArray arr = new JSONArray(list);
        assertEquals("foo", arr.getString(0));
        assertEquals(Integer.valueOf(123), arr.getInteger(1));
        assertEquals("eek", arr.getString(2));
        assertSame(list, arr.getList());
    }

    @Test
    public void testCreateFromListNestedJSONObject() {
        List<Object> list = new ArrayList<>();
        list.add("foo");
        list.add(123);
        JSONObject obj = new JSONObject().put("blah", "wibble");
        list.add(obj);
        JSONArray arr = new JSONArray(list);
        assertEquals("foo", arr.getString(0));
        assertEquals(Integer.valueOf(123), arr.getInteger(1));
        assertSame(list, arr.getList());
        assertSame(obj, arr.getJSONObject(2));
    }

    @Test
    public void testCreateFromListNestedMap() {
        List<Object> list = new ArrayList<>();
        list.add("foo");
        list.add(123);
        Map<String, Object> map = new HashMap<>();
        map.put("blah", "wibble");
        list.add(map);
        JSONArray arr = new JSONArray(list);
        assertEquals("foo", arr.getString(0));
        assertEquals(Integer.valueOf(123), arr.getInteger(1));
        assertSame(list, arr.getList());
        JSONObject obj = arr.getJSONObject(2);
        assertSame(map, obj.getMap());
    }

    @Test
    public void testCreateFromListNestedJSONArray() {
        List<Object> list = new ArrayList<>();
        list.add("foo");
        list.add(123);
        JSONArray arr2 = new JSONArray().add("blah").add("wibble");
        list.add(arr2);
        JSONArray arr = new JSONArray(list);
        assertEquals("foo", arr.getString(0));
        assertEquals(Integer.valueOf(123), arr.getInteger(1));
        assertSame(list, arr.getList());
        assertSame(arr2, arr.getJSONArray(2));
    }

    @Test
    public void testCreateFromListNestedList() {
        List<Object> list = new ArrayList<>();
        list.add("foo");
        list.add(123);
        List<Object> list2 = new ArrayList<>();
        list2.add("blah");
        list2.add("wibble");
        list.add(list2);
        JSONArray arr = new JSONArray(list);
        assertEquals("foo", arr.getString(0));
        assertEquals(Integer.valueOf(123), arr.getInteger(1));
        assertSame(list, arr.getList());
        JSONArray arr2 = arr.getJSONArray(2);
        assertSame(list2, arr2.getList());
    }

    @Test
    public void testJSONArrayEquality() {
        JSONObject obj = new JSONObject(Collections.singletonMap("abc", Collections.singletonList(3)));
        assertEquals(obj, new JSONObject(Collections.singletonMap("abc", Collections.singletonList(3))));
        assertEquals(obj, new JSONObject(Collections.singletonMap("abc", Collections.singletonList(3L))));
        assertEquals(obj, new JSONObject(Collections.singletonMap("abc", new JSONArray().add(3))));
        assertEquals(obj, new JSONObject(Collections.singletonMap("abc", new JSONArray().add(3L))));
        assertNotEquals(obj, new JSONObject(Collections.singletonMap("abc", Collections.singletonList(4))));
        assertNotEquals(obj, new JSONObject(Collections.singletonMap("abc", new JSONArray().add(4))));
        JSONArray array = new JSONArray(Collections.singletonList(Collections.singletonList(3)));
        assertEquals(array, new JSONArray(Collections.singletonList(Collections.singletonList(3))));
        assertEquals(array, new JSONArray(Collections.singletonList(Collections.singletonList(3L))));
        assertEquals(array, new JSONArray(Collections.singletonList(new JSONArray().add(3))));
        assertEquals(array, new JSONArray(Collections.singletonList(new JSONArray().add(3L))));
        assertNotEquals(array, new JSONArray(Collections.singletonList(Collections.singletonList(4))));
        assertNotEquals(array, new JSONArray(Collections.singletonList(new JSONArray().add(4))));
    }

    @Test
    public void testStreamCorrectTypes() throws Exception {
        String json = "{\"object1\": [{\"object2\": 12}]}";
        JSONObject object = new JSONObject(json);
        testStreamCorrectTypes(object.copy());
        testStreamCorrectTypes(object);
    }

    @Test
    public void testRemoveMethodReturnedObject() {
        JSONArray obj = new JSONArray();
        obj.add("bar")
                .add(new JSONObject().put("name", "vert.x").put("count", 2))
                .add(new JSONArray().add(1.0).add(2.0));

        Object removed = obj.remove(0);
        assertTrue(removed instanceof String);

        removed = obj.remove(0);
        assertTrue(removed instanceof JSONObject);
        assertEquals(((JSONObject) removed).getString("name"), "vert.x");

        removed = obj.remove(0);
        assertTrue(removed instanceof JSONArray);
        assertEquals(((JSONArray) removed).getDouble(0), 1.0, 0.0);
    }

    private void testStreamCorrectTypes(JSONObject object) {
        object.getJSONArray("object1").stream().forEach(innerMap -> {
            assertTrue("Expecting JSONObject, found: " + innerMap.getClass().getCanonicalName(), innerMap instanceof JSONObject);
        });
    }

}