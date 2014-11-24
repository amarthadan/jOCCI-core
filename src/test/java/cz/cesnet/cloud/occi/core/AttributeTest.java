package cz.cesnet.cloud.occi.core;

import org.junit.Test;
import static org.junit.Assert.*;

public class AttributeTest {

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
