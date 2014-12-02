package cz.cesnet.cloud.occi.core;

import org.junit.Test;
import static org.junit.Assert.*;

public class AttributeTest {

    @Test
    public void testConstructor() {
        Attribute a = new Attribute("name", true, false, "type", "pattern", "defaultValue", "description");

        assertEquals("name", a.getName());
        assertEquals("type", a.getType());
        assertEquals("pattern", a.getPattern());
        assertEquals("defaultValue", a.getDefaultValue());
        assertEquals("description", a.getDescription());
        assertTrue(a.isRequired());
        assertFalse(a.isImmutable());

        a = new Attribute("name", true, false, "type", null, "defaultValue", "description");
        assertEquals(".*", a.getPattern());
    }

    @Test
    public void testInvalidConstructor() {
        try {
            Attribute a = new Attribute(null);
            fail();
        } catch (NullPointerException ex) {
            //cool
        }

        try {
            Attribute a = new Attribute("");
            fail();
        } catch (IllegalArgumentException ex) {
            //cool
        }
    }

    @Test
    public void testInvalidSetName() {
        try {
            Attribute a = new Attribute("name");
            a.setName(null);
            fail();
        } catch (NullPointerException ex) {
            //cool
        }

        try {
            Attribute a = new Attribute("name");
            a.setName("");
            fail();
        } catch (IllegalArgumentException ex) {
            //cool
        }
    }

    @Test
    public void testToText() {
        Attribute a = new Attribute("attribute_name");
        assertEquals(a.toText(), "attribute_name");

        a.setRequired(true);
        assertEquals(a.toText(), "attribute_name{required}");

        a.setImmutable(true);
        assertEquals(a.toText(), "attribute_name{required immutable}");

        a.setRequired(false);
        assertEquals(a.toText(), "attribute_name{immutable}");
    }
}
