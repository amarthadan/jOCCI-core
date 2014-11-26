package cz.cesnet.cloud.occi.core;

import cz.cesnet.cloud.occi.TestHelper;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;

public class KindTest {

    private static final String RESOURCE_PATH = "src/test/resources/rendering/text/";

    @Test
    public void testFullConstructor() throws URISyntaxException {
        Set<Attribute> attributes = new HashSet<>();
        attributes.add(new Attribute("aaa"));
        attributes.add(new Attribute("bbb"));
        attributes.add(new Attribute("ccc"));
        Kind kind = new Kind(Category.SCHEME_CORE_DEFAULT, Entity.getTermDefult(), "title", new URI("/location/"), attributes);

        assertEquals(kind.getAttributes(), attributes);
        assertEquals(kind.getLocation(), new URI("/location/"));
        assertEquals(kind.getScheme(), Category.SCHEME_CORE_DEFAULT);
        assertEquals(kind.getTerm(), Entity.getTermDefult());
        assertEquals(kind.getTitle(), "title");
    }

    @Test
    public void testMinimalConstructor() throws URISyntaxException {
        Kind kind = new Kind(Category.SCHEME_CORE_DEFAULT, Entity.getTermDefult());

        assertEquals(kind.getScheme(), Category.SCHEME_CORE_DEFAULT);
        assertEquals(kind.getTerm(), Entity.getTermDefult());
    }

    @Test
    public void testInvalidConstructor() throws URISyntaxException {
        try {
            Kind kind = new Kind(null, Entity.getTermDefult());
            fail();
        } catch (NullPointerException ex) {
            //cool
        }

        try {
            Kind kind = new Kind(Category.SCHEME_CORE_DEFAULT, null);
            fail();
        } catch (NullPointerException ex) {
            //cool
        }

        try {
            Kind kind = new Kind(Category.SCHEME_CORE_DEFAULT, "");
            fail();
        } catch (IllegalArgumentException ex) {
            //cool
        }
    }

    @Test
    public void testToText() throws Exception {
        String[] lines = TestHelper.readFile(RESOURCE_PATH + "kind.txt").split("\n");
        Attribute at1 = new Attribute(Entity.ID_ATTRIBUTE_NAME);
        Attribute at2 = new Attribute(Entity.TITLE_ATTRIBUTE_NAME);

        Action a1 = new Action(new URI("http://schemas.ogf.org/occi/infrastructure/compute/action#"), "start");
        Action a2 = new Action(new URI("http://schemas.ogf.org/occi/infrastructure/compute/action#"), "stop");

        Kind kind = new Kind(Category.SCHEME_CORE_DEFAULT, Entity.getTermDefult());
        assertEquals(kind.toText(), lines[0]);

        kind.setTitle("Entity");
        assertEquals(kind.toText(), lines[1]);

        kind.setTitle(null);
        kind.setLocation(new URI("/entity/"));
        assertEquals(kind.toText(), lines[2]);

        kind.setLocation(null);
        kind.addAttribute(at1);
        kind.addAttribute(at2);
        assertEquals(kind.toText(), lines[3]);

        kind = new Kind(Category.SCHEME_CORE_DEFAULT, Entity.getTermDefult());
        kind.addAction(a1);
        kind.addAction(a2);
        assertEquals(kind.toText(), lines[4]);

        kind.addAttribute(at1);
        kind.addAttribute(at2);
        kind.setTitle("Entity");
        kind.setLocation(new URI("/entity/"));
        assertEquals(kind.toText(), lines[5]);

        kind.getAttribute(Entity.ID_ATTRIBUTE_NAME).setRequired(true);
        assertEquals(kind.toText(), lines[6]);

        kind.getAttribute(Entity.ID_ATTRIBUTE_NAME).setImmutable(true);
        assertEquals(kind.toText(), lines[7]);

        kind.getAttribute(Entity.TITLE_ATTRIBUTE_NAME).setImmutable(true);
        assertEquals(kind.toText(), lines[8]);
    }
}
