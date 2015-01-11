package cz.cesnet.cloud.occi.collection;

import cz.cesnet.cloud.occi.core.Attribute;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class AttributeMapCoverTest {

    private final AttributeMapCover attrMap = new AttributeMapCover();

    @Before
    public void setUp() {
        attrMap.add(new Attribute("occi.core.id"), "87f3bfc3-42d4-4474-b45c-757e55e093e9");
        attrMap.add(new Attribute("occi.core.title"), "compute1");
        attrMap.add(new Attribute("occi.compute.architecture"), "x86");
        attrMap.add(new Attribute("occi.compute.hostname"), "compute1.example.org");
        attrMap.add(new Attribute("occi.compute.memory"), "1.7");
        attrMap.add(new Attribute("occi.compute.speed"), "1.0");
    }

    @Test
    public void testAdd() {
        AttributeMapCover instance = new AttributeMapCover();
        assertEquals(0, instance.size());
        instance.add(new Attribute("name"), "value");
        assertEquals(1, instance.size());
        assertTrue(instance.containsAttribute("name"));
    }

    @Test
    public void testInvalidAdd() {
        AttributeMapCover instance = new AttributeMapCover();
        try {
            instance.add(null, "aaa");
            fail();
        } catch (NullPointerException ex) {
            //cool
        }

        try {
            instance.add(new Attribute("aaa"), null);
            fail();
        } catch (NullPointerException ex) {
            //cool
        }
    }

    @Test
    public void testRemoveWithAttribute() {
        assertEquals(6, attrMap.size());
        attrMap.remove(new Attribute("occi.core.id"));
        assertEquals(5, attrMap.size());
        assertFalse(attrMap.containsAttribute("occi.core.id"));
    }

    @Test
    public void testRemoveWithString() {
        assertEquals(6, attrMap.size());
        attrMap.remove("occi.core.id");
        assertEquals(5, attrMap.size());
        assertFalse(attrMap.containsAttribute("occi.core.id"));
    }

    @Test
    public void testContainsAttributeWithAttribute() {
        assertTrue(attrMap.containsAttribute(new Attribute("occi.core.id")));
        assertTrue(attrMap.containsAttribute(new Attribute("occi.compute.architecture")));
        assertFalse(attrMap.containsAttribute(new Attribute("nonexisting_attribute")));
    }

    @Test
    public void testContainsAttributeWithString() {
        assertTrue(attrMap.containsAttribute("occi.core.id"));
        assertTrue(attrMap.containsAttribute("occi.compute.architecture"));
        assertFalse(attrMap.containsAttribute("nonexisting_attribute"));
    }

    @Test
    public void testGetValueWithAttribute() {
        assertEquals("compute1", attrMap.getValue(new Attribute("occi.core.title")));
        assertEquals("x86", attrMap.getValue(new Attribute("occi.compute.architecture")));
        assertNull(attrMap.getValue(new Attribute("nonexisting_attribute")));
    }

    @Test
    public void testGetValueWithString() {
        assertEquals("compute1", attrMap.getValue("occi.core.title"));
        assertEquals("x86", attrMap.getValue("occi.compute.architecture"));
        assertNull(attrMap.getValue("nonexisting_attribute"));
    }

    @Test
    public void testGetAttributes() {
        Map<Attribute, String> map = new HashMap<>();
        map.put(new Attribute("occi.core.id"), "87f3bfc3-42d4-4474-b45c-757e55e093e9");
        map.put(new Attribute("occi.core.title"), "compute1");
        map.put(new Attribute("occi.compute.architecture"), "x86");
        map.put(new Attribute("occi.compute.hostname"), "compute1.example.org");
        map.put(new Attribute("occi.compute.memory"), "1.7");
        map.put(new Attribute("occi.compute.speed"), "1.0");

        assertEquals(map, attrMap.getAttributes());
    }

    @Test
    public void testClear() {
        assertEquals(6, attrMap.size());
        attrMap.clear();
        assertEquals(0, attrMap.size());
    }

    @Test
    public void testToOneLineText() {
        String line = "occi.compute.architecture=\"x86\";occi.compute.hostname=\"compute1.example.org\";occi.compute.memory=1.7;occi.compute.speed=1.0;occi.core.id=\"87f3bfc3-42d4-4474-b45c-757e55e093e9\";occi.core.title=\"compute1\";";

        assertEquals(line, attrMap.toOneLineText());
    }

    @Test
    public void testToPrefixText() {
        String line = "X-OCCI-Attribute: occi.compute.architecture=\"x86\"\nX-OCCI-Attribute: occi.compute.hostname=\"compute1.example.org\"\nX-OCCI-Attribute: occi.compute.memory=1.7\nX-OCCI-Attribute: occi.compute.speed=1.0\nX-OCCI-Attribute: occi.core.id=\"87f3bfc3-42d4-4474-b45c-757e55e093e9\"\nX-OCCI-Attribute: occi.core.title=\"compute1\"";

        System.out.println(attrMap.toPrefixText());
        assertEquals(line, attrMap.toPrefixText());
    }
}
