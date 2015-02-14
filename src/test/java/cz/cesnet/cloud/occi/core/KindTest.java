package cz.cesnet.cloud.occi.core;

import com.sun.net.httpserver.Headers;
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
        Kind kind = new Kind(Category.SCHEME_CORE_DEFAULT, Entity.getTermDefault(), "title", new URI("/location/"), attributes);

        assertEquals(kind.getAttributes(), attributes);
        assertEquals(kind.getLocation(), new URI("/location/"));
        assertEquals(kind.getScheme(), Category.SCHEME_CORE_DEFAULT);
        assertEquals(kind.getTerm(), Entity.getTermDefault());
        assertEquals(kind.getTitle(), "title");
    }

    @Test
    public void testMinimalConstructor() throws URISyntaxException {
        Kind kind = new Kind(Category.SCHEME_CORE_DEFAULT, Entity.getTermDefault());

        assertEquals(kind.getScheme(), Category.SCHEME_CORE_DEFAULT);
        assertEquals(kind.getTerm(), Entity.getTermDefault());
    }

    @Test
    public void testInvalidConstructor() throws URISyntaxException {
        try {
            Kind kind = new Kind(null, Entity.getTermDefault());
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
        String[] lines = TestHelper.readFile(RESOURCE_PATH + "kind_plain.txt").split("\n");
        Attribute at1 = new Attribute(Entity.ID_ATTRIBUTE_NAME);
        Attribute at2 = new Attribute(Entity.TITLE_ATTRIBUTE_NAME);

        Action a1 = new Action(new URI("http://schemas.ogf.org/occi/infrastructure/compute/action#"), "start");
        Action a2 = new Action(new URI("http://schemas.ogf.org/occi/infrastructure/compute/action#"), "stop");

        Kind kind = new Kind(Category.SCHEME_CORE_DEFAULT, Entity.getTermDefault());
        assertEquals(lines[0], kind.toText());

        kind.setTitle("Entity");
        assertEquals(lines[1], kind.toText());

        kind.setTitle(null);
        kind.setLocation(new URI("/entity/"));
        assertEquals(lines[2], kind.toText());

        kind.setLocation(null);
        kind.addAttribute(at1);
        kind.addAttribute(at2);
        assertEquals(lines[3], kind.toText());

        kind = new Kind(Category.SCHEME_CORE_DEFAULT, Entity.getTermDefault());
        kind.addAction(a1);
        kind.addAction(a2);
        assertEquals(lines[4], kind.toText());

        kind.addAttribute(at1);
        kind.addAttribute(at2);
        kind.setTitle("Entity");
        kind.setLocation(new URI("/entity/"));
        assertEquals(lines[5], kind.toText());

        kind.getAttribute(Entity.ID_ATTRIBUTE_NAME).setRequired(true);
        assertEquals(lines[6], kind.toText());

        kind.getAttribute(Entity.ID_ATTRIBUTE_NAME).setImmutable(true);
        assertEquals(lines[7], kind.toText());

        kind.getAttribute(Entity.TITLE_ATTRIBUTE_NAME).setImmutable(true);
        assertEquals(lines[8], kind.toText());
    }

    @Test
    public void testToHeaders() throws Exception {
        String[] lines = TestHelper.readFile(RESOURCE_PATH + "kind_headers.txt").split("\n");
        Attribute at1 = new Attribute(Entity.ID_ATTRIBUTE_NAME);
        Attribute at2 = new Attribute(Entity.TITLE_ATTRIBUTE_NAME);

        Action a1 = new Action(new URI("http://schemas.ogf.org/occi/infrastructure/compute/action#"), "start");
        Action a2 = new Action(new URI("http://schemas.ogf.org/occi/infrastructure/compute/action#"), "stop");

        Headers headers = new Headers();

        Kind kind = new Kind(Category.SCHEME_CORE_DEFAULT, Entity.getTermDefault());
        headers.add("Category", lines[0]);
        assertEquals(headers, kind.toHeaders());

        kind.setTitle("Entity");
        headers.clear();
        headers.add("Category", lines[1]);
        assertEquals(headers, kind.toHeaders());

        kind.setTitle(null);
        kind.setLocation(new URI("/entity/"));
        headers.clear();
        headers.add("Category", lines[2]);
        assertEquals(headers, kind.toHeaders());

        kind.setLocation(null);
        kind.addAttribute(at1);
        kind.addAttribute(at2);
        headers.clear();
        headers.add("Category", lines[3]);
        assertEquals(headers, kind.toHeaders());

        kind = new Kind(Category.SCHEME_CORE_DEFAULT, Entity.getTermDefault());
        kind.addAction(a1);
        kind.addAction(a2);
        headers.clear();
        headers.add("Category", lines[4]);
        assertEquals(headers, kind.toHeaders());

        kind.addAttribute(at1);
        kind.addAttribute(at2);
        kind.setTitle("Entity");
        kind.setLocation(new URI("/entity/"));
        headers.clear();
        headers.add("Category", lines[5]);
        assertEquals(headers, kind.toHeaders());

        kind.getAttribute(Entity.ID_ATTRIBUTE_NAME).setRequired(true);
        headers.clear();
        headers.add("Category", lines[6]);
        assertEquals(headers, kind.toHeaders());

        kind.getAttribute(Entity.ID_ATTRIBUTE_NAME).setImmutable(true);
        headers.clear();
        headers.add("Category", lines[7]);
        assertEquals(headers, kind.toHeaders());

        kind.getAttribute(Entity.TITLE_ATTRIBUTE_NAME).setImmutable(true);
        headers.clear();
        headers.add("Category", lines[8]);
        assertEquals(headers, kind.toHeaders());
    }
}
