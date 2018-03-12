package ch.epfl.imhof;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

import org.junit.Test;


public class AttributesTest {

    @Test
    public void isEmptyTest() {

        Attributes attributes1 = new Attributes(new HashMap<>());
        boolean b = attributes1.isEmpty();
        assertTrue(b);

        String s = "keys";
        String d = "value";
        Map<String, String> Mappy = new HashMap<>();
        Mappy.put(s, d);
        Attributes attributes = new Attributes(Mappy);

        boolean c = attributes.isEmpty();
        assertFalse(c);
    }

    @Test
    public void containsTest() {

        Map<String, String> Mappy = new HashMap<>();
        Mappy.put("keys1", "value1");
        Mappy.put("keys2", "value2");
        Mappy.put("keys3", "value3");
        Mappy.put("keys4", "value4");
        Mappy.put("keys5", "value5");
        Attributes attributes = new Attributes(Mappy);

        assertTrue(attributes.contains("keys5"));
        assertFalse(attributes.contains("keys6"));

    }

    @Test
    public void getTest() {

        Map<String, String> Mappy = new HashMap<>();
        Mappy.put("keys1", "value1");
        Mappy.put("keys2", "value2");
        Mappy.put("keys3", "value3");
        Attributes attributes = new Attributes(Mappy);

        assertEquals("value1", attributes.get("keys1"));
        assertEquals(null, attributes.get("nokeys"));

    }

    @Test
    public void get2Test() {

        Map<String, String> Mappy = new HashMap<>();
        Mappy.put("keys1", "value1");
        Mappy.put("keys2", "value2");
        Mappy.put("keys3", "value3");
        Mappy.put("keys4", null);
        Attributes attributes = new Attributes(Mappy);

        assertEquals("value1", attributes.get("keys1", "defaut1"));
        assertEquals("nodefaut", attributes.get("nokeys", "nodefaut"));
        assertEquals(null, attributes.get("keys4", "defaut4"));

    }

    @Test
    public void get3Test() {

        int a = (int) Math.random() * 100;
        int b = (int) Math.random() * 100;
        int c = (int) Math.random() * 100;
        int d = (int) Math.random() * 100;
        String c1 = "" + c + "";

        Map<String, String> Mappy = new HashMap<>();
        Mappy.put("keys1", "value1");
        Mappy.put("keys2", c1);
        Attributes attributes = new Attributes(Mappy);

        assertEquals(a, attributes.get("keys1", a));
        assertEquals(c, attributes.get("keys2", b));
        assertEquals(d, attributes.get("nokeys", d));

    }

    @Test
    public void keepOnlyKeysTest() {
        Map<String, String> Mappy = new HashMap<>();
        Mappy.put("keys1", "value1");
        Mappy.put("keys2", "value2");
        Mappy.put("keys3", "value3");
        Mappy.put("keys4", "value4");
        Mappy.put("keys5", "value5");
        Attributes attribute1 = new Attributes(Mappy);

        Set<String> Setty = new HashSet<>();
        Setty.add("keys1");
        Setty.add("keys2");
        Setty.add("keys5");
        Setty.add("nokeys");

        Attributes attributes = attribute1.keepOnlyKeys(Setty);

        assertEquals("value1", attributes.get("keys1", "defaut1"));
        assertEquals("value2", attributes.get("keys2", "defaut2"));
        assertEquals("defaut3", attributes.get("keys3", "defaut3"));
        assertEquals("defaut4", attributes.get("keys4", "defaut4"));
        assertEquals("value5", attributes.get("keys5", "defaut5"));

    }


}
