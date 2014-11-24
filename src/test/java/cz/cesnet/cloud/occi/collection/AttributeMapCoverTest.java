package cz.cesnet.cloud.occi.collection;

import cz.cesnet.cloud.occi.core.Attribute;
import java.util.ArrayList;
import java.util.List;
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
    public void testToPrefixText() {
        String line = "X-OCCI-Attribute: occi.compute.hostname=\"compute1.example.org\"\nX-OCCI-Attribute: occi.core.title=\"compute1\"\nX-OCCI-Attribute: occi.compute.memory=1.7\nX-OCCI-Attribute: occi.compute.speed=1.0\nX-OCCI-Attribute: occi.core.id=\"87f3bfc3-42d4-4474-b45c-757e55e093e9\"\nX-OCCI-Attribute: occi.compute.architecture=\"x86\"";

        System.out.println(attrMap.toPrefixText());
        assertEquals(line, attrMap.toPrefixText());
    }

    @Test
    public void testToOneLineText() {
        String line = "occi.compute.hostname=\"compute1.example.org\";occi.core.title=\"compute1\";occi.compute.memory=1.7;occi.compute.speed=1.0;occi.core.id=\"87f3bfc3-42d4-4474-b45c-757e55e093e9\";occi.compute.architecture=\"x86\";";

        assertEquals(line, attrMap.toOneLineText());
    }

}
