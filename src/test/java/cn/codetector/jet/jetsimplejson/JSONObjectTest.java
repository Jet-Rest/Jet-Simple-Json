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


import cn.codetector.jet.jetsimplejson.JSONObject;
import cn.codetector.jet.jetsimplejson.exception.DecodeException;
import org.junit.*;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.format.DateTimeFormatter.ISO_INSTANT;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class JSONObjectTest {

    protected JSONObject jsonObject;

    @Before
    public void setUp() throws Exception {
        jsonObject = new JSONObject();
    }

    @Test
    public void testGetInteger() {
        jsonObject.put("foo", 123);
        assertEquals(Integer.valueOf(123), jsonObject.getInteger("foo"));
        jsonObject.put("bar", "hello");
        try {
            jsonObject.getInteger("bar");
            fail("");
        } catch (ClassCastException e) {
            // Ok
        }
        // Put as different Number types
        jsonObject.put("foo", 123L);
        assertEquals(Integer.valueOf(123), jsonObject.getInteger("foo"));
        jsonObject.put("foo", 123d);
        assertEquals(Integer.valueOf(123), jsonObject.getInteger("foo"));
        jsonObject.put("foo", 123f);
        assertEquals(Integer.valueOf(123), jsonObject.getInteger("foo"));
        jsonObject.put("foo", Long.MAX_VALUE);
        assertEquals(Integer.valueOf(-1), jsonObject.getInteger("foo"));

        // Null and absent values
        jsonObject.putNull("foo");
        assertNull(jsonObject.getInteger("foo"));
        assertNull(jsonObject.getInteger("absent"));

        try {
            jsonObject.getInteger(null);
            fail("");
        } catch (NullPointerException e) {
            // OK
        }

    }

    @Test
    public void testGetIntegerDefault() {
        jsonObject.put("foo", 123);
        assertEquals(Integer.valueOf(123), jsonObject.getInteger("foo", 321));
        assertEquals(Integer.valueOf(123), jsonObject.getInteger("foo", null));
        jsonObject.put("bar", "hello");
        try {
            jsonObject.getInteger("bar", 123);
            fail("");
        } catch (ClassCastException e) {
            // Ok
        }
        // Put as different Number types
        jsonObject.put("foo", 123l);
        assertEquals(Integer.valueOf(123), jsonObject.getInteger("foo", 321));
        jsonObject.put("foo", 123d);
        assertEquals(Integer.valueOf(123), jsonObject.getInteger("foo", 321));
        jsonObject.put("foo", 123f);
        assertEquals(Integer.valueOf(123), jsonObject.getInteger("foo", 321));
        jsonObject.put("foo", Long.MAX_VALUE);
        assertEquals(Integer.valueOf(-1), jsonObject.getInteger("foo", 321));

        // Null and absent values
        jsonObject.putNull("foo");
        assertNull(jsonObject.getInteger("foo", 321));
        assertEquals(Integer.valueOf(321), jsonObject.getInteger("absent", 321));
        assertNull(jsonObject.getInteger("foo", null));
        assertNull(jsonObject.getInteger("absent", null));

        try {
            jsonObject.getInteger(null, null);
            fail("");
        } catch (NullPointerException e) {
            // OK
        }

    }

    @Test
    public void testGetLong() {
        jsonObject.put("foo", 123l);
        assertEquals(Long.valueOf(123l), jsonObject.getLong("foo"));
        jsonObject.put("bar", "hello");
        try {
            jsonObject.getLong("bar");
            fail("");
        } catch (ClassCastException e) {
            // Ok
        }
        // Put as different Number types
        jsonObject.put("foo", 123);
        assertEquals(Long.valueOf(123l), jsonObject.getLong("foo"));
        jsonObject.put("foo", 123d);
        assertEquals(Long.valueOf(123l), jsonObject.getLong("foo"));
        jsonObject.put("foo", 123f);
        assertEquals(Long.valueOf(123l), jsonObject.getLong("foo"));
        jsonObject.put("foo", Long.MAX_VALUE);
        assertEquals(Long.valueOf(Long.MAX_VALUE), jsonObject.getLong("foo"));

        // Null and absent values
        jsonObject.putNull("foo");
        assertNull(jsonObject.getLong("foo"));
        assertNull(jsonObject.getLong("absent"));

        try {
            jsonObject.getLong(null);
            fail("");
        } catch (NullPointerException e) {
            // OK
        }

    }

    @Test
    public void testGetLongDefault() {
        jsonObject.put("foo", 123l);
        assertEquals(Long.valueOf(123l), jsonObject.getLong("foo", 321l));
        assertEquals(Long.valueOf(123), jsonObject.getLong("foo", null));
        jsonObject.put("bar", "hello");
        try {
            jsonObject.getLong("bar", 123l);
            fail("");
        } catch (ClassCastException e) {
            // Ok
        }
        // Put as different Number types
        jsonObject.put("foo", 123);
        assertEquals(Long.valueOf(123l), jsonObject.getLong("foo", 321l));
        jsonObject.put("foo", 123d);
        assertEquals(Long.valueOf(123l), jsonObject.getLong("foo", 321l));
        jsonObject.put("foo", 123f);
        assertEquals(Long.valueOf(123l), jsonObject.getLong("foo", 321l));
        jsonObject.put("foo", Long.MAX_VALUE);
        assertEquals(Long.valueOf(Long.MAX_VALUE), jsonObject.getLong("foo", 321l));

        // Null and absent values
        jsonObject.putNull("foo");
        assertNull(jsonObject.getLong("foo", 321l));
        assertEquals(Long.valueOf(321l), jsonObject.getLong("absent", 321l));
        assertNull(jsonObject.getLong("foo", null));
        assertNull(jsonObject.getLong("absent", null));

        try {
            jsonObject.getLong(null, null);
            fail("");
        } catch (NullPointerException e) {
            // OK
        }

    }

    @Test
    public void testGetFloat() {
        jsonObject.put("foo", 123f);
        assertEquals(Float.valueOf(123f), jsonObject.getFloat("foo"));
        jsonObject.put("bar", "hello");
        try {
            jsonObject.getFloat("bar");
            fail("");
        } catch (ClassCastException e) {
            // Ok
        }
        // Put as different Number types
        jsonObject.put("foo", 123);
        assertEquals(Float.valueOf(123f), jsonObject.getFloat("foo"));
        jsonObject.put("foo", 123d);
        assertEquals(Float.valueOf(123f), jsonObject.getFloat("foo"));
        jsonObject.put("foo", 123f);
        assertEquals(Float.valueOf(123l), jsonObject.getFloat("foo"));

        // Null and absent values
        jsonObject.putNull("foo");
        assertNull(jsonObject.getFloat("foo"));
        assertNull(jsonObject.getFloat("absent"));

        try {
            jsonObject.getFloat(null);
            fail("");
        } catch (NullPointerException e) {
            // OK
        }

    }

    @Test
    public void testGetFloatDefault() {
        jsonObject.put("foo", 123f);
        assertEquals(Float.valueOf(123f), jsonObject.getFloat("foo", 321f));
        assertEquals(Float.valueOf(123), jsonObject.getFloat("foo", null));
        jsonObject.put("bar", "hello");
        try {
            jsonObject.getFloat("bar", 123f);
            fail("");
        } catch (ClassCastException e) {
            // Ok
        }
        // Put as different Number types
        jsonObject.put("foo", 123);
        assertEquals(Float.valueOf(123f), jsonObject.getFloat("foo", 321f));
        jsonObject.put("foo", 123d);
        assertEquals(Float.valueOf(123f), jsonObject.getFloat("foo", 321f));
        jsonObject.put("foo", 123l);
        assertEquals(Float.valueOf(123f), jsonObject.getFloat("foo", 321f));

        // Null and absent values
        jsonObject.putNull("foo");
        assertNull(jsonObject.getFloat("foo", 321f));
        assertEquals(Float.valueOf(321f), jsonObject.getFloat("absent", 321f));
        assertNull(jsonObject.getFloat("foo", null));
        assertNull(jsonObject.getFloat("absent", null));

        try {
            jsonObject.getFloat(null, null);
            fail("");
        } catch (NullPointerException e) {
            // OK
        }

    }

    @Test
    public void testGetDouble() {
        jsonObject.put("foo", 123d);
        assertEquals(Double.valueOf(123d), jsonObject.getDouble("foo"));
        jsonObject.put("bar", "hello");
        try {
            jsonObject.getDouble("bar");
            fail("");
        } catch (ClassCastException e) {
            // Ok
        }
        // Put as different Number types
        jsonObject.put("foo", 123);
        assertEquals(Double.valueOf(123d), jsonObject.getDouble("foo"));
        jsonObject.put("foo", 123l);
        assertEquals(Double.valueOf(123d), jsonObject.getDouble("foo"));
        jsonObject.put("foo", 123f);
        assertEquals(Double.valueOf(123d), jsonObject.getDouble("foo"));

        // Null and absent values
        jsonObject.putNull("foo");
        assertNull(jsonObject.getDouble("foo"));
        assertNull(jsonObject.getDouble("absent"));

        try {
            jsonObject.getDouble(null);
            fail("");
        } catch (NullPointerException e) {
            // OK
        }

    }

    @Test
    public void testGetDoubleDefault() {
        jsonObject.put("foo", 123d);
        assertEquals(Double.valueOf(123d), jsonObject.getDouble("foo", 321d));
        assertEquals(Double.valueOf(123), jsonObject.getDouble("foo", null));
        jsonObject.put("bar", "hello");
        try {
            jsonObject.getDouble("bar", 123d);
            fail("");
        } catch (ClassCastException e) {
            // Ok
        }
        // Put as different Number types
        jsonObject.put("foo", 123);
        assertEquals(Double.valueOf(123d), jsonObject.getDouble("foo", 321d));
        jsonObject.put("foo", 123f);
        assertEquals(Double.valueOf(123d), jsonObject.getDouble("foo", 321d));
        jsonObject.put("foo", 123l);
        assertEquals(Double.valueOf(123d), jsonObject.getDouble("foo", 321d));

        // Null and absent values
        jsonObject.putNull("foo");
        assertNull(jsonObject.getDouble("foo", 321d));
        assertEquals(Double.valueOf(321d), jsonObject.getDouble("absent", 321d));
        assertNull(jsonObject.getDouble("foo", null));
        assertNull(jsonObject.getDouble("absent", null));

        try {
            jsonObject.getDouble(null, null);
            fail("");
        } catch (NullPointerException e) {
            // OK
        }

    }

    @Test
    public void testGetString() {
        jsonObject.put("foo", "bar");
        assertEquals("bar", jsonObject.getString("foo"));
        jsonObject.put("bar", 123);
        try {
            jsonObject.getString("bar");
            fail("");
        } catch (ClassCastException e) {
            // Ok
        }

        // Null and absent values
        jsonObject.putNull("foo");
        assertNull(jsonObject.getString("foo"));
        assertNull(jsonObject.getString("absent"));

        try {
            jsonObject.getString(null);
            fail("");
        } catch (NullPointerException e) {
            // OK
        }

    }

    @Test
    public void testGetStringDefault() {
        jsonObject.put("foo", "bar");
        assertEquals("bar", jsonObject.getString("foo", "wibble"));
        assertEquals("bar", jsonObject.getString("foo", null));
        jsonObject.put("bar", 123);
        try {
            jsonObject.getString("bar", "wibble");
            fail("");
        } catch (ClassCastException e) {
            // Ok
        }

        // Null and absent values
        jsonObject.putNull("foo");
        assertNull(jsonObject.getString("foo", "wibble"));
        assertEquals("wibble", jsonObject.getString("absent", "wibble"));
        assertNull(jsonObject.getString("foo", null));
        assertNull(jsonObject.getString("absent", null));

        try {
            jsonObject.getString(null, null);
            fail("");
        } catch (NullPointerException e) {
            // OK
        }

    }

    @Test
    public void testGetBoolean() {
        jsonObject.put("foo", true);
        assertEquals(true, jsonObject.getBoolean("foo"));
        jsonObject.put("foo", false);
        assertEquals(false, jsonObject.getBoolean("foo"));
        jsonObject.put("bar", 123);
        try {
            jsonObject.getBoolean("bar");
            fail("");
        } catch (ClassCastException e) {
            // Ok
        }

        // Null and absent values
        jsonObject.putNull("foo");
        assertNull(jsonObject.getBoolean("foo"));
        assertNull(jsonObject.getBoolean("absent"));

        try {
            jsonObject.getBoolean(null);
            fail("");
        } catch (NullPointerException e) {
            // OK
        }

    }

    @Test
    public void testGetBooleanDefault() {
        jsonObject.put("foo", true);
        assertEquals(true, jsonObject.getBoolean("foo", false));
        assertEquals(true, jsonObject.getBoolean("foo", null));
        jsonObject.put("foo", false);
        assertEquals(false, jsonObject.getBoolean("foo", true));
        assertEquals(false, jsonObject.getBoolean("foo", null));
        jsonObject.put("bar", 123);
        try {
            jsonObject.getBoolean("bar", true);
            fail("");
        } catch (ClassCastException e) {
            // Ok
        }

        // Null and absent values
        jsonObject.putNull("foo");
        assertNull(jsonObject.getBoolean("foo", true));
        assertNull(jsonObject.getBoolean("foo", false));
        assertEquals(true, jsonObject.getBoolean("absent", true));
        assertEquals(false, jsonObject.getBoolean("absent", false));

        try {
            jsonObject.getBoolean(null, null);
            fail("");
        } catch (NullPointerException e) {
            // OK
        }

    }


    @Test
    public void testGetBinary() {
        byte[] bytes = TestUtils.randomByteArray(100);
        jsonObject.put("foo", bytes);
        assertTrue(TestUtils.byteArraysEqual(bytes, jsonObject.getBinary("foo")));

        // Can also get as string:
        String val = jsonObject.getString("foo");
        assertNotNull(val);
        byte[] retrieved = Base64.getDecoder().decode(val);
        assertTrue(TestUtils.byteArraysEqual(bytes, retrieved));

        jsonObject.put("foo", 123);
        try {
            jsonObject.getBinary("foo");
            fail("");
        } catch (ClassCastException e) {
            // Ok
        }

        jsonObject.putNull("foo");
        assertNull(jsonObject.getBinary("foo"));
        assertNull(jsonObject.getBinary("absent"));
        try {
            jsonObject.getBinary(null);
            fail("");
        } catch (NullPointerException e) {
            // OK
        }
        try {
            jsonObject.getBinary(null, null);
            fail("");
        } catch (NullPointerException e) {
            // OK
        }
    }

    @Test
    public void testGetInstant() {
        Instant now = Instant.now();
        jsonObject.put("foo", now);
        assertEquals(now, jsonObject.getInstant("foo"));

        // Can also get as string:
        String val = jsonObject.getString("foo");
        assertNotNull(val);
        Instant retrieved = Instant.from(ISO_INSTANT.parse(val));
        assertEquals(now, retrieved);

        jsonObject.put("foo", 123);
        try {
            jsonObject.getInstant("foo");
            fail("");
        } catch (ClassCastException e) {
            // Ok
        }

        jsonObject.putNull("foo");
        assertNull(jsonObject.getInstant("foo"));
        assertNull(jsonObject.getInstant("absent"));
        try {
            jsonObject.getInstant(null);
            fail("");
        } catch (NullPointerException e) {
            // OK
        }
        try {
            jsonObject.getInstant(null, null);
            fail("");
        } catch (NullPointerException e) {
            // OK
        }
    }

    @Test
    public void testGetBinaryDefault() {
        byte[] bytes = TestUtils.randomByteArray(100);
        byte[] defBytes = TestUtils.randomByteArray(100);
        jsonObject.put("foo", bytes);
        assertTrue(TestUtils.byteArraysEqual(bytes, jsonObject.getBinary("foo", defBytes)));
        assertTrue(TestUtils.byteArraysEqual(bytes, jsonObject.getBinary("foo", null)));

        jsonObject.put("foo", 123);
        try {
            jsonObject.getBinary("foo", defBytes);
            fail("");
        } catch (ClassCastException e) {
            // Ok
        }

        jsonObject.putNull("foo");
        assertNull(jsonObject.getBinary("foo", defBytes));
        assertTrue(TestUtils.byteArraysEqual(defBytes, jsonObject.getBinary("absent", defBytes)));
        assertNull(jsonObject.getBinary("foo", null));
        assertNull(jsonObject.getBinary("absent", null));
        try {
            jsonObject.getBinary(null, null);
            fail("");
        } catch (NullPointerException e) {
            // OK
        }
    }

    @Test
    public void testGetInstantDefault() {
        Instant now = Instant.now();
        Instant later = now.plus(1, ChronoUnit.DAYS);
        jsonObject.put("foo", now);
        assertEquals(now, jsonObject.getInstant("foo", later));
        assertEquals(now, jsonObject.getInstant("foo", null));

        jsonObject.put("foo", 123);
        try {
            jsonObject.getInstant("foo", later);
            fail("");
        } catch (ClassCastException e) {
            // Ok
        }

        jsonObject.putNull("foo");
        assertNull(jsonObject.getInstant("foo", later));
        assertEquals(later, jsonObject.getInstant("absent", later));
        assertNull(jsonObject.getInstant("foo", null));
        assertNull(jsonObject.getInstant("absent", null));
        try {
            jsonObject.getInstant(null, null);
            fail("");
        } catch (NullPointerException e) {
            // OK
        }
    }

    @Test
    public void testGetJSONObject() {
        JSONObject obj = new JSONObject().put("blah", "wibble");
        jsonObject.put("foo", obj);
        assertEquals(obj, jsonObject.getJSONObject("foo"));

        jsonObject.put("foo", "hello");
        try {
            jsonObject.getJSONObject("foo");
            fail("");
        } catch (ClassCastException e) {
            // Ok
        }

        jsonObject.putNull("foo");
        assertNull(jsonObject.getJSONObject("foo"));
        assertNull(jsonObject.getJSONObject("absent"));
        try {
            jsonObject.getJSONObject(null);
            fail("");
        } catch (NullPointerException e) {
            // OK
        }
    }

    @Test
    public void testGetJSONObjectDefault() {
        JSONObject obj = new JSONObject().put("blah", "wibble");
        JSONObject def = new JSONObject().put("eek", "quuz");
        jsonObject.put("foo", obj);
        assertEquals(obj, jsonObject.getJSONObject("foo", def));
        assertEquals(obj, jsonObject.getJSONObject("foo", null));

        jsonObject.put("foo", "hello");
        try {
            jsonObject.getJSONObject("foo", def);
            fail("");
        } catch (ClassCastException e) {
            // Ok
        }

        jsonObject.putNull("foo");
        assertNull(jsonObject.getJSONObject("foo", def));
        assertEquals(def, jsonObject.getJSONObject("absent", def));
        assertNull(jsonObject.getJSONObject("foo", null));
        assertNull(jsonObject.getJSONObject("absent", null));
        try {
            jsonObject.getJSONObject(null, null);
            fail("");
        } catch (NullPointerException e) {
            // OK
        }
    }

    @Test
    public void testGetJSONArray() {
        JSONArray arr = new JSONArray().add("blah").add("wibble");
        jsonObject.put("foo", arr);
        assertEquals(arr, jsonObject.getJSONArray("foo"));

        jsonObject.put("foo", "hello");
        try {
            jsonObject.getJSONArray("foo");
            fail("");
        } catch (ClassCastException e) {
            // Ok
        }

        jsonObject.putNull("foo");
        assertNull(jsonObject.getJSONArray("foo"));
        assertNull(jsonObject.getJSONArray("absent"));
        try {
            jsonObject.getJSONArray(null);
            fail("");
        } catch (NullPointerException e) {
            // OK
        }
    }

    @Test
    public void testGetJSONArrayDefault() {
        JSONArray arr = new JSONArray().add("blah").add("wibble");
        JSONArray def = new JSONArray().add("quux").add("eek");
        jsonObject.put("foo", arr);
        assertEquals(arr, jsonObject.getJSONArray("foo", def));
        assertEquals(arr, jsonObject.getJSONArray("foo", null));

        jsonObject.put("foo", "hello");
        try {
            jsonObject.getJSONArray("foo", def);
            fail("");
        } catch (ClassCastException e) {
            // Ok
        }

        jsonObject.putNull("foo");
        assertNull(jsonObject.getJSONArray("foo", def));
        assertEquals(def, jsonObject.getJSONArray("absent", def));
        assertNull(jsonObject.getJSONArray("foo", null));
        assertNull(jsonObject.getJSONArray("absent", null));
        try {
            jsonObject.getJSONArray(null, null);
            fail("");
        } catch (NullPointerException e) {
            // OK
        }
    }

    @Test
    public void testGetValue() {
        jsonObject.put("foo", 123);
        assertEquals(123, jsonObject.getValue("foo"));
        jsonObject.put("foo", 123l);
        assertEquals(123l, jsonObject.getValue("foo"));
        jsonObject.put("foo", 123f);
        assertEquals(123f, jsonObject.getValue("foo"));
        jsonObject.put("foo", 123d);
        assertEquals(123d, jsonObject.getValue("foo"));
        jsonObject.put("foo", false);
        assertEquals(false, jsonObject.getValue("foo"));
        jsonObject.put("foo", true);
        assertEquals(true, jsonObject.getValue("foo"));
        jsonObject.put("foo", "bar");
        assertEquals("bar", jsonObject.getValue("foo"));
        JSONObject obj = new JSONObject().put("blah", "wibble");
        jsonObject.put("foo", obj);
        assertEquals(obj, jsonObject.getValue("foo"));
        JSONArray arr = new JSONArray().add("blah").add("wibble");
        jsonObject.put("foo", arr);
        assertEquals(arr, jsonObject.getValue("foo"));
        byte[] bytes = TestUtils.randomByteArray(100);
        jsonObject.put("foo", bytes);
        assertTrue(TestUtils.byteArraysEqual(bytes, Base64.getDecoder().decode((String) jsonObject.getValue("foo"))));
        jsonObject.putNull("foo");
        assertNull(jsonObject.getValue("foo"));
        assertNull(jsonObject.getValue("absent"));
        // JSONObject with inner Map
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> innerMap = new HashMap<>();
        innerMap.put("blah", "wibble");
        map.put("foo", innerMap);
        jsonObject = new JSONObject(map);
        obj = (JSONObject)jsonObject.getValue("foo");
        assertEquals("wibble", obj.getString("blah"));
        // JSONObject with inner List
        map = new HashMap<>();
        List<Object> innerList = new ArrayList<>();
        innerList.add("blah");
        map.put("foo", innerList);
        jsonObject = new JSONObject(map);
        arr = (JSONArray)jsonObject.getValue("foo");
        assertEquals("blah", arr.getString(0));
    }

    @Test
    public void testGetValueDefault() {
        jsonObject.put("foo", 123);
        assertEquals(123, jsonObject.getValue("foo", "blah"));
        assertEquals(123, jsonObject.getValue("foo", null));
        jsonObject.put("foo", 123l);
        assertEquals(123l, jsonObject.getValue("foo", "blah"));
        assertEquals(123l, jsonObject.getValue("foo", null));
        jsonObject.put("foo", 123f);
        assertEquals(123f, jsonObject.getValue("foo", "blah"));
        assertEquals(123f, jsonObject.getValue("foo", null));
        jsonObject.put("foo", 123d);
        assertEquals(123d, jsonObject.getValue("foo", "blah"));
        assertEquals(123d, jsonObject.getValue("foo", null));
        jsonObject.put("foo", false);
        assertEquals(false, jsonObject.getValue("foo", "blah"));
        assertEquals(false, jsonObject.getValue("foo", null));
        jsonObject.put("foo", true);
        assertEquals(true, jsonObject.getValue("foo", "blah"));
        assertEquals(true, jsonObject.getValue("foo", null));
        jsonObject.put("foo", "bar");
        assertEquals("bar", jsonObject.getValue("foo", "blah"));
        assertEquals("bar", jsonObject.getValue("foo", null));
        JSONObject obj = new JSONObject().put("blah", "wibble");
        jsonObject.put("foo", obj);
        assertEquals(obj, jsonObject.getValue("foo", "blah"));
        assertEquals(obj, jsonObject.getValue("foo", null));
        JSONArray arr = new JSONArray().add("blah").add("wibble");
        jsonObject.put("foo", arr);
        assertEquals(arr, jsonObject.getValue("foo", "blah"));
        assertEquals(arr, jsonObject.getValue("foo", null));
        byte[] bytes = TestUtils.randomByteArray(100);
        jsonObject.put("foo", bytes);
        assertTrue(TestUtils.byteArraysEqual(bytes, Base64.getDecoder().decode((String) jsonObject.getValue("foo", "blah"))));
        assertTrue(TestUtils.byteArraysEqual(bytes, Base64.getDecoder().decode((String)jsonObject.getValue("foo", null))));
        jsonObject.putNull("foo");
        assertNull(jsonObject.getValue("foo", "blah"));
        assertNull(jsonObject.getValue("foo", null));
        assertEquals("blah", jsonObject.getValue("absent", "blah"));
        assertNull(jsonObject.getValue("absent", null));
    }

    @Test
    public void testContainsKey() {
        jsonObject.put("foo", "bar");
        assertTrue(jsonObject.containsKey("foo"));
        jsonObject.putNull("foo");
        assertTrue(jsonObject.containsKey("foo"));
        assertFalse(jsonObject.containsKey("absent"));
    }

    @Test
    public void testFieldNames() {
        jsonObject.put("foo", "bar");
        jsonObject.put("eek", 123);
        jsonObject.put("flib", new JSONObject());
        Set<String> fieldNames = jsonObject.fieldNames();
        assertEquals(3, fieldNames.size());
        assertTrue(fieldNames.contains("foo"));
        assertTrue(fieldNames.contains("eek"));
        assertTrue(fieldNames.contains("flib"));
        jsonObject.remove("foo");
        assertEquals(2, fieldNames.size());
        assertFalse(fieldNames.contains("foo"));
    }

    @Test
    public void testSize() {
        assertEquals(0, jsonObject.size());
        jsonObject.put("foo", "bar");
        assertEquals(1, jsonObject.size());
        jsonObject.put("bar", 123);
        assertEquals(2, jsonObject.size());
        jsonObject.putNull("wibble");
        assertEquals(3, jsonObject.size());
        jsonObject.remove("wibble");
        assertEquals(2, jsonObject.size());
        jsonObject.clear();
        assertEquals(0, jsonObject.size());
    }

    enum SomeEnum {
        FOO, BAR
    }

    @Test
    public void testPutEnum() {
        assertSame(jsonObject, jsonObject.put("foo", SomeEnum.FOO));
        assertEquals(SomeEnum.FOO.toString(), jsonObject.getString("foo"));
        assertTrue(jsonObject.containsKey("foo"));
        try {
            jsonObject.put(null, SomeEnum.FOO);
            fail("");
        } catch (NullPointerException e) {
            // OK
        }
    }

    @Test
    public void testPutString() {
        assertSame(jsonObject, jsonObject.put("foo", "bar"));
        assertEquals("bar", jsonObject.getString("foo"));
        jsonObject.put("quux", "wibble");
        assertEquals("wibble", jsonObject.getString("quux"));
        assertEquals("bar", jsonObject.getString("foo"));
        jsonObject.put("foo", "blah");
        assertEquals("blah", jsonObject.getString("foo"));
        jsonObject.put("foo", (String) null);
        assertTrue(jsonObject.containsKey("foo"));
        try {
            jsonObject.put(null, "blah");
            fail("");
        } catch (NullPointerException e) {
            // OK
        }
    }

    @Test
    public void testPutCharSequence() {
        assertSame(jsonObject, jsonObject.put("foo", new StringBuilder("bar")));
        assertEquals("bar", jsonObject.getString("foo"));
        assertEquals("bar", jsonObject.getString("foo", "def"));
        jsonObject.put("quux",new StringBuilder("wibble"));
        assertEquals("wibble", jsonObject.getString("quux"));
        assertEquals("bar", jsonObject.getString("foo"));
        jsonObject.put("foo", new StringBuilder("blah"));
        assertEquals("blah", jsonObject.getString("foo"));
        jsonObject.put("foo", (CharSequence) null);
        assertTrue(jsonObject.containsKey("foo"));
        try {
            jsonObject.put(null, (CharSequence)"blah");
            fail("");
        } catch (NullPointerException e) {
            // OK
        }
    }

    @Test
    public void testPutInteger() {
        assertSame(jsonObject, jsonObject.put("foo", 123));
        assertEquals(Integer.valueOf(123), jsonObject.getInteger("foo"));
        jsonObject.put("quux", 321);
        assertEquals(Integer.valueOf(321), jsonObject.getInteger("quux"));
        assertEquals(Integer.valueOf(123), jsonObject.getInteger("foo"));
        jsonObject.put("foo", 456);
        assertEquals(Integer.valueOf(456), jsonObject.getInteger("foo"));
        jsonObject.put("foo", (Integer) null);
        assertTrue(jsonObject.containsKey("foo"));
        try {
            jsonObject.put(null, 123);
            fail("");
        } catch (NullPointerException e) {
            // OK
        }
    }

    @Test
    public void testPutLong() {
        assertSame(jsonObject, jsonObject.put("foo", 123l));
        assertEquals(Long.valueOf(123l), jsonObject.getLong("foo"));
        jsonObject.put("quux", 321l);
        assertEquals(Long.valueOf(321l), jsonObject.getLong("quux"));
        assertEquals(Long.valueOf(123l), jsonObject.getLong("foo"));
        jsonObject.put("foo", 456l);
        assertEquals(Long.valueOf(456l), jsonObject.getLong("foo"));
        jsonObject.put("foo", (Long) null);
        assertTrue(jsonObject.containsKey("foo"));

        try {
            jsonObject.put(null, 123l);
            fail("");
        } catch (NullPointerException e) {
            // OK
        }
    }

    @Test
    public void testPutFloat() {
        assertSame(jsonObject, jsonObject.put("foo", 123f));
        assertEquals(Float.valueOf(123f), jsonObject.getFloat("foo"));
        jsonObject.put("quux", 321f);
        assertEquals(Float.valueOf(321f), jsonObject.getFloat("quux"));
        assertEquals(Float.valueOf(123f), jsonObject.getFloat("foo"));
        jsonObject.put("foo", 456f);
        assertEquals(Float.valueOf(456f), jsonObject.getFloat("foo"));
        jsonObject.put("foo", (Float) null);
        assertTrue(jsonObject.containsKey("foo"));

        try {
            jsonObject.put(null, 1.2f);
            fail("");
        } catch (NullPointerException e) {
            // OK
        }
    }

    @Test
    public void testPutDouble() {
        assertSame(jsonObject, jsonObject.put("foo", 123d));
        assertEquals(Double.valueOf(123d), jsonObject.getDouble("foo"));
        jsonObject.put("quux", 321d);
        assertEquals(Double.valueOf(321d), jsonObject.getDouble("quux"));
        assertEquals(Double.valueOf(123d), jsonObject.getDouble("foo"));
        jsonObject.put("foo", 456d);
        assertEquals(Double.valueOf(456d), jsonObject.getDouble("foo"));
        jsonObject.put("foo", (Double) null);
        assertTrue(jsonObject.containsKey("foo"));
        try {
            jsonObject.put(null, 1.23d);
            fail("");
        } catch (NullPointerException e) {
            // OK
        }
    }

    @Test
    public void testPutBoolean() {
        assertSame(jsonObject, jsonObject.put("foo", true));
        assertEquals(true, jsonObject.getBoolean("foo"));
        jsonObject.put("quux", true);
        assertEquals(true, jsonObject.getBoolean("quux"));
        assertEquals(true, jsonObject.getBoolean("foo"));
        jsonObject.put("foo", true);
        assertEquals(true, jsonObject.getBoolean("foo"));
        jsonObject.put("foo", (Boolean) null);
        assertTrue(jsonObject.containsKey("foo"));
        try {
            jsonObject.put(null, false);
            fail("");
        } catch (NullPointerException e) {
            // OK
        }
    }

    @Test
    public void testPutJSONObject() {
        JSONObject obj1 = new JSONObject().put("blah", "wibble");
        JSONObject obj2 = new JSONObject().put("eeek", "flibb");
        JSONObject obj3 = new JSONObject().put("floob", "plarp");
        assertSame(jsonObject, jsonObject.put("foo", obj1));
        assertEquals(obj1, jsonObject.getJSONObject("foo"));
        jsonObject.put("quux", obj2);
        assertEquals(obj2, jsonObject.getJSONObject("quux"));
        assertEquals(obj1, jsonObject.getJSONObject("foo"));
        jsonObject.put("foo", obj3);
        assertEquals(obj3, jsonObject.getJSONObject("foo"));
        jsonObject.put("foo", (JSONObject) null);
        assertTrue(jsonObject.containsKey("foo"));
        try {
            jsonObject.put(null, new JSONObject());
            fail("");
        } catch (NullPointerException e) {
            // OK
        }
    }

    @Test
    public void testPutJSONArray() {
        JSONArray obj1 = new JSONArray().add("parp");
        JSONArray obj2 = new JSONArray().add("fleep");
        JSONArray obj3 = new JSONArray().add("woob");

        assertSame(jsonObject, jsonObject.put("foo", obj1));
        assertEquals(obj1, jsonObject.getJSONArray("foo"));
        jsonObject.put("quux", obj2);
        assertEquals(obj2, jsonObject.getJSONArray("quux"));
        assertEquals(obj1, jsonObject.getJSONArray("foo"));
        jsonObject.put("foo", obj3);
        assertEquals(obj3, jsonObject.getJSONArray("foo"));

        jsonObject.put("foo", (JSONArray) null);
        assertTrue(jsonObject.containsKey("foo"));


        try {
            jsonObject.put(null, new JSONArray());
            fail("");
        } catch (NullPointerException e) {
            // OK
        }
    }

    @Test
    public void testPutBinary() {
        byte[] bin1 = TestUtils.randomByteArray(100);
        byte[] bin2 = TestUtils.randomByteArray(100);
        byte[] bin3 = TestUtils.randomByteArray(100);

        assertSame(jsonObject, jsonObject.put("foo", bin1));
        assertTrue(TestUtils.byteArraysEqual(bin1, jsonObject.getBinary("foo")));
        jsonObject.put("quux", bin2);
        assertTrue(TestUtils.byteArraysEqual(bin2, jsonObject.getBinary("quux")));
        assertTrue(TestUtils.byteArraysEqual(bin1, jsonObject.getBinary("foo")));
        jsonObject.put("foo", bin3);
        assertTrue(TestUtils.byteArraysEqual(bin3, jsonObject.getBinary("foo")));

        jsonObject.put("foo", (byte[]) null);
        assertTrue(jsonObject.containsKey("foo"));

        try {
            jsonObject.put(null, bin1);
            fail("");
        } catch (NullPointerException e) {
            // OK
        }
    }

    @Test
    public void testPutInstant() {
        Instant bin1 = Instant.now();
        Instant bin2 = bin1.plus(1, ChronoUnit.DAYS);
        Instant bin3 = bin1.plus(1, ChronoUnit.MINUTES);

        assertSame(jsonObject, jsonObject.put("foo", bin1));
        assertEquals(bin1, jsonObject.getInstant("foo"));
        jsonObject.put("quux", bin2);
        assertEquals(bin2, jsonObject.getInstant("quux"));
        assertEquals(bin1, jsonObject.getInstant("foo"));
        jsonObject.put("foo", bin3);
        assertEquals(bin3, jsonObject.getInstant("foo"));

        jsonObject.put("foo", (Instant) null);
        assertTrue(jsonObject.containsKey("foo"));

        try {
            jsonObject.put(null, bin1);
            fail("");
        } catch (NullPointerException e) {
            // OK
        }
    }

    @Test
    public void testPutNull() {
        assertSame(jsonObject, jsonObject.putNull("foo"));
        assertTrue(jsonObject.containsKey("foo"));
        assertSame(jsonObject, jsonObject.putNull("bar"));
        assertTrue(jsonObject.containsKey("bar"));
        try {
            jsonObject.putNull(null);
            fail("");
        } catch (NullPointerException e) {
            // OK
        }
    }

    @Test
    public void testPutValue() {
        jsonObject.put("str", (Object)"bar");
        jsonObject.put("int", (Object)(Integer.valueOf(123)));
        jsonObject.put("long", (Object)(Long.valueOf(123l)));
        jsonObject.put("float", (Object)(Float.valueOf(1.23f)));
        jsonObject.put("double", (Object)(Double.valueOf(1.23d)));
        jsonObject.put("boolean", (Object) true);
        byte[] bytes = TestUtils.randomByteArray(10);
        jsonObject.put("binary", (Object)(bytes));
        Instant now = Instant.now();
        jsonObject.put("instant", now);
        JSONObject obj = new JSONObject().put("foo", "blah");
        JSONArray arr = new JSONArray().add("quux");
        jsonObject.put("obj", (Object)obj);
        jsonObject.put("arr", (Object)arr);
        assertEquals("bar", jsonObject.getString("str"));
        assertEquals(Integer.valueOf(123), jsonObject.getInteger("int"));
        assertEquals(Long.valueOf(123l), jsonObject.getLong("long"));
        assertEquals(Float.valueOf(1.23f), jsonObject.getFloat("float"));
        assertEquals(Double.valueOf(1.23d), jsonObject.getDouble("double"));
        assertTrue(TestUtils.byteArraysEqual(bytes, jsonObject.getBinary("binary")));
        assertEquals(now, jsonObject.getInstant("instant"));
        assertEquals(obj, jsonObject.getJSONObject("obj"));
        assertEquals(arr, jsonObject.getJSONArray("arr"));
        try {
            jsonObject.put("inv", new SomeClass());
            fail("");
        } catch (IllegalStateException e) {
            // OK
        }
        try {
            jsonObject.put("inv", new BigDecimal(123));
            fail("");
        } catch (IllegalStateException e) {
            // OK
        }
        try {
            jsonObject.put("inv", new Date());
            fail("");
        } catch (IllegalStateException e) {
            // OK
        }

    }

    @Test
    public void testMergeIn1() {
        JSONObject obj1 = new JSONObject().put("foo", "bar");
        JSONObject obj2 = new JSONObject().put("eek", "flurb");
        obj1.mergeIn(obj2);
        assertEquals(2, obj1.size());
        assertEquals("bar", obj1.getString("foo"));
        assertEquals("flurb", obj1.getString("eek"));
        assertEquals(1, obj2.size());
        assertEquals("flurb", obj2.getString("eek"));
    }

    @Test
    public void testMergeIn2() {
        JSONObject obj1 = new JSONObject().put("foo", "bar");
        JSONObject obj2 = new JSONObject().put("foo", "flurb");
        obj1.mergeIn(obj2);
        assertEquals(1, obj1.size());
        assertEquals("flurb", obj1.getString("foo"));
        assertEquals(1, obj2.size());
        assertEquals("flurb", obj2.getString("foo"));
    }

    @Test
    public void testMergeInDepth0() {
        JSONObject obj1 = new JSONObject("{ \"foo\": { \"bar\": \"flurb\" }}");
        JSONObject obj2 = new JSONObject("{ \"foo\": { \"bar\": \"eek\" }}");
        obj1.mergeIn(obj2, 0);
        assertEquals(1, obj1.size());
        assertEquals(1, obj1.getJSONObject("foo").size());
        assertEquals("flurb", obj1.getJSONObject("foo").getString("bar"));
    }

    @Test
    public void testMergeInFlat() {
        JSONObject obj1 = new JSONObject("{ \"foo\": { \"bar\": \"flurb\", \"eek\": 32 }}");
        JSONObject obj2 = new JSONObject("{ \"foo\": { \"bar\": \"eek\" }}");
        obj1.mergeIn(obj2, false);
        assertEquals(1, obj1.size());
        assertEquals(1, obj1.getJSONObject("foo").size());
        assertEquals("eek", obj1.getJSONObject("foo").getString("bar"));
    }

    @Test
    public void testMergeInDepth1() {
        JSONObject obj1 = new JSONObject("{ \"foo\": \"bar\", \"flurb\": { \"eek\": \"foo\", \"bar\": \"flurb\"}}");
        JSONObject obj2 = new JSONObject("{ \"flurb\": { \"bar\": \"flurb1\" }}");
        obj1.mergeIn(obj2, 1);
        assertEquals(2, obj1.size());
        assertEquals(1, obj1.getJSONObject("flurb").size());
        assertEquals("flurb1", obj1.getJSONObject("flurb").getString("bar"));
    }

    @Test
    public void testMergeInDepth2() {
        JSONObject obj1 = new JSONObject("{ \"foo\": \"bar\", \"flurb\": { \"eek\": \"foo\", \"bar\": \"flurb\"}}");
        JSONObject obj2 = new JSONObject("{ \"flurb\": { \"bar\": \"flurb1\" }}");
        obj1.mergeIn(obj2, 2);
        assertEquals(2, obj1.size());
        assertEquals(2, obj1.getJSONObject("flurb").size());
        assertEquals("foo", obj1.getJSONObject("flurb").getString("eek"));
        assertEquals("flurb1", obj1.getJSONObject("flurb").getString("bar"));
    }

    @Test
    public void testEncode() throws Exception {
        jsonObject.put("mystr", "foo");
        jsonObject.put("mycharsequence", new StringBuilder("oob"));
        jsonObject.put("myint", 123);
        jsonObject.put("mylong", 1234l);
        jsonObject.put("myfloat", 1.23f);
        jsonObject.put("mydouble", 2.34d);
        jsonObject.put("myboolean", true);
        byte[] bytes = TestUtils.randomByteArray(10);
        jsonObject.put("mybinary", bytes);
        Instant now = Instant.now();
        jsonObject.put("myinstant", now);
        jsonObject.putNull("mynull");
        jsonObject.put("myobj", new JSONObject().put("foo", "bar"));
        jsonObject.put("myarr", new JSONArray().add("foo").add(123));
        String strBytes = Base64.getEncoder().encodeToString(bytes);
        String expected = "{\"mystr\":\"foo\",\"mycharsequence\":\"oob\",\"myint\":123,\"mylong\":1234,\"myfloat\":1.23,\"mydouble\":2.34,\"" +
                "myboolean\":true,\"mybinary\":\"" + strBytes + "\",\"myinstant\":\"" + ISO_INSTANT.format(now) + "\",\"mynull\":null,\"myobj\":{\"foo\":\"bar\"},\"myarr\":[\"foo\",123]}";
        String json = jsonObject.encode();
        assertEquals(expected, json);
    }

    @Test
    public void testDecode() throws Exception {
        byte[] bytes = TestUtils.randomByteArray(10);
        String strBytes = Base64.getEncoder().encodeToString(bytes);
        Instant now = Instant.now();
        String strInstant = ISO_INSTANT.format(now);
        String json = "{\"mystr\":\"foo\",\"myint\":123,\"mylong\":1234,\"myfloat\":1.23,\"mydouble\":2.34,\"" +
                "myboolean\":true,\"mybinary\":\"" + strBytes + "\",\"myinstant\":\"" + strInstant + "\",\"mynull\":null,\"myobj\":{\"foo\":\"bar\"},\"myarr\":[\"foo\",123]}";
        JSONObject obj = new JSONObject(json);
        assertEquals(json, obj.encode());
        assertEquals("foo", obj.getString("mystr"));
        assertEquals(Integer.valueOf(123), obj.getInteger("myint"));
        assertEquals(Long.valueOf(1234), obj.getLong("mylong"));
        assertEquals(Float.valueOf(1.23f), obj.getFloat("myfloat"));
        assertEquals(Double.valueOf(2.34d), obj.getDouble("mydouble"));
        assertTrue(obj.getBoolean("myboolean"));
        assertTrue(TestUtils.byteArraysEqual(bytes, obj.getBinary("mybinary")));
        assertEquals(now, obj.getInstant("myinstant"));
        assertTrue(obj.containsKey("mynull"));
        JSONObject nestedObj = obj.getJSONObject("myobj");
        assertEquals("bar", nestedObj.getString("foo"));
        JSONArray nestedArr = obj.getJSONArray("myarr");
        assertEquals("foo", nestedArr.getString(0));
        assertEquals(Integer.valueOf(123), Integer.valueOf(nestedArr.getInteger(1)));
    }

    @Test
    public void testToString() {
        jsonObject.put("foo", "bar");
        assertEquals(jsonObject.encode(), jsonObject.toString());
    }

    @Test
    public void testEncodePrettily() throws Exception {
        jsonObject.put("mystr", "foo");
        jsonObject.put("myint", 123);
        jsonObject.put("mylong", 1234l);
        jsonObject.put("myfloat", 1.23f);
        jsonObject.put("mydouble", 2.34d);
        jsonObject.put("myboolean", true);
        byte[] bytes = TestUtils.randomByteArray(10);
        jsonObject.put("mybinary", bytes);
        Instant now = Instant.now();
        jsonObject.put("myinstant", now);
        jsonObject.put("myobj", new JSONObject().put("foo", "bar"));
        jsonObject.put("myarr", new JSONArray().add("foo").add(123));
        String strBytes = Base64.getEncoder().encodeToString(bytes);
        String strInstant = ISO_INSTANT.format(now);
        String expected = "{" + Utils.LINE_SEPARATOR +
                "  \"mystr\" : \"foo\"," + Utils.LINE_SEPARATOR +
                "  \"myint\" : 123," + Utils.LINE_SEPARATOR +
                "  \"mylong\" : 1234," + Utils.LINE_SEPARATOR +
                "  \"myfloat\" : 1.23," + Utils.LINE_SEPARATOR +
                "  \"mydouble\" : 2.34," + Utils.LINE_SEPARATOR +
                "  \"myboolean\" : true," + Utils.LINE_SEPARATOR +
                "  \"mybinary\" : \"" + strBytes + "\"," + Utils.LINE_SEPARATOR +
                "  \"myinstant\" : \"" + strInstant + "\"," + Utils.LINE_SEPARATOR +
                "  \"myobj\" : {" + Utils.LINE_SEPARATOR +
                "    \"foo\" : \"bar\"" + Utils.LINE_SEPARATOR +
                "  }," + Utils.LINE_SEPARATOR +
                "  \"myarr\" : [ \"foo\", 123 ]" + Utils.LINE_SEPARATOR +
                "}";
        String json = jsonObject.encodePrettily();
        assertEquals(expected, json);
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
                        "{\n" +
                        "// another single line comment this time inside the JSON object itself\n" +
                        "  \"foo\": \"bar\" // and a single line comment at end of line \n" +
                        "/*\n" +
                        "  This is a another multi \n" +
                        "  line comment this time inside the JSON object itself\n" +
                        "*/\n" +
                        "}";
        JSONObject json = new JSONObject(jsonWithComments);
        assertEquals("{\"foo\":\"bar\"}", json.encode());
    }

    @Test
    public void testInvalidJSON() {
        String invalid = "qiwjdoiqwjdiqwjd";
        try {
            new JSONObject(invalid);
            fail("");
        } catch (DecodeException e) {
            // OK
        }
    }

    @Test
    public void testClear() {
        jsonObject.put("foo", "bar");
        jsonObject.put("quux", 123);
        assertEquals(2, jsonObject.size());
        jsonObject.clear();
        assertEquals(0, jsonObject.size());
        assertNull(jsonObject.getValue("foo"));
        assertNull(jsonObject.getValue("quux"));
    }

    @Test
    public void testIsEmpty() {
        assertTrue(jsonObject.isEmpty());
        jsonObject.put("foo", "bar");
        jsonObject.put("quux", 123);
        assertFalse(jsonObject.isEmpty());
        jsonObject.clear();
        assertTrue(jsonObject.isEmpty());
    }

    @Test
    public void testRemove() {
        jsonObject.put("mystr", "bar");
        jsonObject.put("myint", 123);
        assertEquals("bar", jsonObject.remove("mystr"));
        assertNull(jsonObject.getValue("mystr"));
        assertEquals(123, jsonObject.remove("myint"));
        assertNull(jsonObject.getValue("myint"));
        assertTrue(jsonObject.isEmpty());
    }

    @Test
    public void testIterator() {
        jsonObject.put("foo", "bar");
        jsonObject.put("quux", 123);
        JSONObject obj = createJSONObject();
        jsonObject.put("wibble", obj);
        Iterator<Map.Entry<String, Object>> iter = jsonObject.iterator();
        assertTrue(iter.hasNext());
        Map.Entry<String, Object> entry = iter.next();
        assertEquals("foo", entry.getKey());
        assertEquals("bar", entry.getValue());
        assertTrue(iter.hasNext());
        entry = iter.next();
        assertEquals("quux", entry.getKey());
        assertEquals(123, entry.getValue());
        assertTrue(iter.hasNext());
        entry = iter.next();
        assertEquals("wibble", entry.getKey());
        assertEquals(obj, entry.getValue());
        assertFalse(iter.hasNext());
        iter.remove();
        assertFalse(obj.containsKey("wibble"));
        assertEquals(2, jsonObject.size());
    }

    @Test
    public void testIteratorDoesntChangeObject() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("nestedMap", new HashMap<>());
        map.put("nestedList", new ArrayList<>());
        JSONObject obj = new JSONObject(map);
        Iterator<Map.Entry<String, Object>> iter = obj.iterator();
        Map.Entry<String, Object> entry1 = iter.next();
        assertEquals("nestedMap", entry1.getKey());
        Object val1 = entry1.getValue();
        assertTrue(val1 instanceof JSONObject);
        Map.Entry<String, Object> entry2 = iter.next();
        assertEquals("nestedList", entry2.getKey());
        Object val2 = entry2.getValue();
        assertTrue(val2 instanceof JSONArray);
        assertTrue(map.get("nestedMap") instanceof HashMap);
        assertTrue(map.get("nestedList") instanceof ArrayList);
    }

    @Test
    public void testStream() {
        jsonObject.put("foo", "bar");
        jsonObject.put("quux", 123);
        JSONObject obj = createJSONObject();
        jsonObject.put("wibble", obj);
        List<Map.Entry<String, Object>> list = jsonObject.stream().collect(Collectors.toList());
        Iterator<Map.Entry<String, Object>> iter = list.iterator();
        assertTrue(iter.hasNext());
        Map.Entry<String, Object> entry = iter.next();
        assertEquals("foo", entry.getKey());
        assertEquals("bar", entry.getValue());
        assertTrue(iter.hasNext());
        entry = iter.next();
        assertEquals("quux", entry.getKey());
        assertEquals(123, entry.getValue());
        assertTrue(iter.hasNext());
        entry = iter.next();
        assertEquals("wibble", entry.getKey());
        assertEquals(obj, entry.getValue());
        assertFalse(iter.hasNext());
    }

    @Test
    public void testCopy() {
        jsonObject.put("foo", "bar");
        jsonObject.put("quux", 123);
        JSONObject obj = createJSONObject();
        jsonObject.put("wibble", obj);
        jsonObject.put("eek", new StringBuilder("blah")); // CharSequence
        JSONObject copy = jsonObject.copy();
        assertNotSame(jsonObject, copy);
        assertEquals(jsonObject, copy);
        copy.put("blah", "flib");
        assertFalse(jsonObject.containsKey("blah"));
        copy.remove("foo");
        assertFalse(copy.containsKey("foo"));
        assertTrue(jsonObject.containsKey("foo"));
        jsonObject.put("oob", "flarb");
        assertFalse(copy.containsKey("oob"));
        jsonObject.remove("quux");
        assertFalse(jsonObject.containsKey("quux"));
        assertTrue(copy.containsKey("quux"));
        JSONObject nested = jsonObject.getJSONObject("wibble");
        JSONObject nestedCopied = copy.getJSONObject("wibble");
        assertNotSame(nested, nestedCopied);
        assertEquals(nested, nestedCopied);
        assertEquals("blah", copy.getString("eek"));
    }

    @Test
    public void testInvalidValsOnCopy1() {
        Map<String, Object> invalid = new HashMap<>();
        invalid.put("foo", new SomeClass());
        JSONObject object = new JSONObject(invalid);
        try {
            object.copy();
            fail("");
        } catch (IllegalStateException e) {
            // OK
        }
    }

    @Test
    public void testInvalidValsOnCopy2() {
        Map<String, Object> invalid = new HashMap<>();
        Map<String, Object> invalid2 = new HashMap<>();
        invalid2.put("foo", new SomeClass());
        invalid.put("bar",invalid2);
        JSONObject object = new JSONObject(invalid);
        try {
            object.copy();
            fail("");
        } catch (IllegalStateException e) {
            // OK
        }
    }

    @Test
    public void testInvalidValsOnCopy3() {
        Map<String, Object> invalid = new HashMap<>();
        List<Object> invalid2 = new ArrayList<>();
        invalid2.add(new SomeClass());
        invalid.put("bar",invalid2);
        JSONObject object = new JSONObject(invalid);
        try {
            object.copy();
            fail("");
        } catch (IllegalStateException e) {
            // OK
        }
    }

    class SomeClass {
    }

    @Test
    public void testGetMap() {
        jsonObject.put("foo", "bar");
        jsonObject.put("quux", 123);
        JSONObject obj = createJSONObject();
        jsonObject.put("wibble", obj);
        Map<String, Object> map = jsonObject.getMap();
        map.remove("foo");
        assertFalse(jsonObject.containsKey("foo"));
        map.put("bleep", "flarp");
        assertTrue(jsonObject.containsKey("bleep"));
        jsonObject.remove("quux");
        assertFalse(map.containsKey("quux"));
        jsonObject.put("wooble", "plink");
        assertTrue(map.containsKey("wooble"));
        assertSame(obj, map.get("wibble"));
    }

    @Test
    public void testCreateFromMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("foo", "bar");
        map.put("quux", 123);
        JSONObject obj = new JSONObject(map);
        assertEquals("bar", obj.getString("foo"));
        assertEquals(Integer.valueOf(123), obj.getInteger("quux"));
        assertSame(map, obj.getMap());
    }

    @Test
    public void testCreateFromMapCharSequence() {
        Map<String, Object> map = new HashMap<>();
        map.put("foo", "bar");
        map.put("quux", 123);
        map.put("eeek", new StringBuilder("blah"));
        JSONObject obj = new JSONObject(map);
        assertEquals("bar", obj.getString("foo"));
        assertEquals(Integer.valueOf(123), obj.getInteger("quux"));
        assertEquals("blah", obj.getString("eeek"));
        assertSame(map, obj.getMap());
    }

    @Test
    public void testCreateFromMapNestedJSONObject() {
        Map<String, Object> map = new HashMap<>();
        JSONObject nestedObj = new JSONObject().put("foo", "bar");
        map.put("nested", nestedObj);
        JSONObject obj = new JSONObject(map);
        JSONObject nestedRetrieved = obj.getJSONObject("nested");
        assertEquals("bar", nestedRetrieved.getString("foo"));
    }

    @Test
    public void testCreateFromMapNestedMap() {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> nestedMap = new HashMap<>();
        nestedMap.put("foo", "bar");
        map.put("nested", nestedMap);
        JSONObject obj = new JSONObject(map);
        JSONObject nestedRetrieved = obj.getJSONObject("nested");
        assertEquals("bar", nestedRetrieved.getString("foo"));
    }

    @Test
    public void testCreateFromMapNestedJSONArray() {
        Map<String, Object> map = new HashMap<>();
        JSONArray nestedArr = new JSONArray().add("foo");
        map.put("nested", nestedArr);
        JSONObject obj = new JSONObject(map);
        JSONArray nestedRetrieved = obj.getJSONArray("nested");
        assertEquals("foo", nestedRetrieved.getString(0));
    }

    @Test
    public void testCreateFromMapNestedList() {
        Map<String, Object> map = new HashMap<>();
        List<String> nestedArr = Arrays.asList("foo");
        map.put("nested", nestedArr);
        JSONObject obj = new JSONObject(map);
        JSONArray nestedRetrieved = obj.getJSONArray("nested");
        assertEquals("foo", nestedRetrieved.getString(0));
    }

    @Test
    public void testNumberEquality() {
        assertNumberEquals(4, 4);
        assertNumberEquals(4, (long)4);
        assertNumberEquals(4, 4f);
        assertNumberEquals(4, 4D);
        assertNumberEquals((long)4, (long)4);
        assertNumberEquals((long)4, 4f);
        assertNumberEquals((long)4, 4D);
        assertNumberEquals(4f, 4f);
        assertNumberEquals(4f, 4D);
        assertNumberEquals(4D, 4D);
        assertNumberEquals(4.1D, 4.1D);
        assertNumberEquals(4.1f, 4.1f);
        assertNumberNotEquals(4.1f, 4.1D);
        assertNumberEquals(4.5D, 4.5D);
        assertNumberEquals(4.5f, 4.5f);
        assertNumberEquals(4.5f, 4.5D);
        assertNumberNotEquals(4, 5);
        assertNumberNotEquals(4, (long)5);
        assertNumberNotEquals(4, 5D);
        assertNumberNotEquals(4, 5f);
        assertNumberNotEquals((long)4, (long)5);
        assertNumberNotEquals((long)4, 5D);
        assertNumberNotEquals((long)4, 5f);
        assertNumberNotEquals(4f, 5f);
        assertNumberNotEquals(4f, 5D);
        assertNumberNotEquals(4D, 5D);
    }

    private void assertNumberEquals(Number value1, Number value2) {
        JSONObject o1 = new JSONObject().put("key", value1);
        JSONObject o2 = new JSONObject().put("key", value2);
        if (!o1.equals(o2)) {
            fail("Was expecting " + value1.getClass().getSimpleName() + ":" + value1 + " == " +
                    value2.getClass().getSimpleName() + ":" + value2);
        }
        JSONArray a1 = new JSONArray().add(value1);
        JSONArray a2 = new JSONArray().add(value2);
        if (!a1.equals(a2)) {
            fail("Was expecting " + value1.getClass().getSimpleName() + ":" + value1 + " == " +
                    value2.getClass().getSimpleName() + ":" + value2);
        }
    }

    private void assertNumberNotEquals(Number value1, Number value2) {
        JSONObject o1 = new JSONObject().put("key", value1);
        JSONObject o2 = new JSONObject().put("key", value2);
        if (o1.equals(o2)) {
            fail("Was expecting " + value1.getClass().getSimpleName() + ":" + value1 + " != " +
                    value2.getClass().getSimpleName() + ":" + value2);
        }
    }

    @Test
    public void testJSONObjectEquality() {
        JSONObject obj = new JSONObject(Collections.singletonMap("abc", Collections.singletonMap("def", 3)));
        assertEquals(obj, new JSONObject(Collections.singletonMap("abc", Collections.singletonMap("def", 3))));
        assertEquals(obj, new JSONObject(Collections.singletonMap("abc", Collections.singletonMap("def", 3L))));
        assertEquals(obj, new JSONObject(Collections.singletonMap("abc", new JSONObject().put("def", 3))));
        assertEquals(obj, new JSONObject(Collections.singletonMap("abc", new JSONObject().put("def", 3L))));
        assertNotEquals(obj, new JSONObject(Collections.singletonMap("abc", Collections.singletonMap("def", 4))));
        assertNotEquals(obj, new JSONObject(Collections.singletonMap("abc", new JSONObject().put("def", 4))));
        JSONArray array = new JSONArray(Collections.singletonList(Collections.singletonMap("def", 3)));
        assertEquals(array, new JSONArray(Collections.singletonList(Collections.singletonMap("def", 3))));
        assertEquals(array, new JSONArray(Collections.singletonList(Collections.singletonMap("def", 3L))));
        assertEquals(array, new JSONArray(Collections.singletonList(new JSONObject().put("def", 3))));
        assertEquals(array, new JSONArray(Collections.singletonList(new JSONObject().put("def", 3L))));
        assertNotEquals(array, new JSONArray(Collections.singletonList(Collections.singletonMap("def", 4))));
        assertNotEquals(array, new JSONArray(Collections.singletonList(new JSONObject().put("def", 4))));
    }

    @Test
    public void testJSONObjectEquality2() {
        JSONObject obj1 = new JSONObject().put("arr", new JSONArray().add("x"));
        List < Object > list = new ArrayList<>();
        list.add("x");
        Map<String, Object> map = new HashMap<>();
        map.put("arr", list);
        JSONObject obj2 = new JSONObject(map);
        Iterator<Map.Entry<String, Object>> iter = obj2.iterator();
        // There was a bug where iteration of entries caused the underlying object to change resulting in a
        // subsequent equals changing
        while (iter.hasNext()) {
            Map.Entry<String, Object> entry = iter.next();
        }
        assertEquals(obj2, obj1);
    }

    @Test
    public void testPutInstantAsObject() {
        Object instant = Instant.now();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("instant", instant);
        // assert data is stored as String
        assertTrue(jsonObject.getValue("instant") instanceof String);
    }

    @Test
    public void testStreamCorrectTypes() throws Exception {
        String json = "{\"object1\": {\"object2\": 12}}";
        JSONObject object = new JSONObject(json);
        testStreamCorrectTypes(object.copy());
        testStreamCorrectTypes(object);
    }

    @Test
    public void testRemoveMethodReturnedObject() {
        JSONObject obj = new JSONObject();
        obj.put("simple", "bar")
                .put("object", new JSONObject().put("name", "vert.x").put("count", 2))
                .put("array", new JSONArray().add(1.0).add(2.0));

        Object removed = obj.remove("missing");
        assertNull(removed);

        removed = obj.remove("simple");
        assertTrue(removed instanceof String);

        removed = obj.remove("object");
        assertTrue(removed instanceof JSONObject);
        assertEquals(((JSONObject) removed).getString("name"), "vert.x");

        removed = obj.remove("array");
        assertTrue(removed instanceof JSONArray);
        assertEquals(((JSONArray) removed).getDouble(0), 1.0, 0.0);
    }

    @Test
    public void testOrder() {
        List<String> expectedKeys = new ArrayList<>();
        int size = 100;
        StringBuilder sb = new StringBuilder("{");
        for (int i = 0;i < size;i++) {
            sb.append("\"key-").append(i).append("\":").append(i).append(",");
            expectedKeys.add("key-" + i);
        }
        sb.setCharAt(sb.length() - 1, '}');
        JSONObject obj = new JSONObject(sb.toString());
        List<String> keys = new ArrayList<>();

        // ordered because of Jackson uses a LinkedHashMap
        obj.forEach(e -> keys.add(e.getKey()));
        assertEquals(expectedKeys, keys);
        keys.clear();

        // Ordered because we preserve the LinkedHashMap
        obj.copy().forEach(e -> keys.add(e.getKey()));
        assertEquals(expectedKeys, keys);
    }

    private void testStreamCorrectTypes(JSONObject object) {
        object.stream().forEach(entry -> {
            String key = entry.getKey();
            Object val = entry.getValue();
            assertEquals("object1", key);
            assertTrue("Expecting JSONObject, found: " + val.getClass().getCanonicalName(), val instanceof JSONObject);
        });
    }

    private JSONObject createJSONObject() {
        JSONObject obj = new JSONObject();
        obj.put("mystr", "bar");
        obj.put("myint", Integer.MAX_VALUE);
        obj.put("mylong", Long.MAX_VALUE);
        obj.put("myfloat", Float.MAX_VALUE);
        obj.put("mydouble", Double.MAX_VALUE);
        obj.put("myboolean", true);
        obj.put("mybinary", TestUtils.randomByteArray(100));
        obj.put("myinstant", Instant.now());
        return obj;
    }

}

