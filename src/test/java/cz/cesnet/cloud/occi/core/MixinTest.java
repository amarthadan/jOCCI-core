package cz.cesnet.cloud.occi.core;

import com.sun.net.httpserver.Headers;
import cz.cesnet.cloud.occi.TestHelper;
import cz.cesnet.cloud.occi.infrastructure.IPNetwork;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;

public class MixinTest {

    private static final String RESOURCE_PATH = "src/test/resources/rendering/text/";

    @Test
    public void testFullConstructor() throws URISyntaxException {
        Set<Attribute> attributes = new HashSet<>();
        attributes.add(new Attribute("aaa"));
        attributes.add(new Attribute("bbb"));
        attributes.add(new Attribute("ccc"));
        Mixin mixin = new Mixin(Category.SCHEME_CORE_DEFAULT, Entity.TERM_DEFAULT, "title", new URI("/location/"), attributes);

        assertEquals(mixin.getAttributes(), attributes);
        assertEquals(mixin.getLocation(), new URI("/location/"));
        assertEquals(mixin.getScheme(), Category.SCHEME_CORE_DEFAULT);
        assertEquals(mixin.getTerm(), Entity.TERM_DEFAULT);
        assertEquals(mixin.getTitle(), "title");
    }

    @Test
    public void testMinimalConstructor() throws URISyntaxException {
        Mixin mixin = new Mixin(Category.SCHEME_CORE_DEFAULT, Entity.TERM_DEFAULT);

        assertEquals(mixin.getScheme(), Category.SCHEME_CORE_DEFAULT);
        assertEquals(mixin.getTerm(), Entity.TERM_DEFAULT);
    }

    @Test
    public void testInvalidConstructor() throws URISyntaxException {
        try {
            Mixin mixin = new Mixin(null, Entity.TERM_DEFAULT);
            fail();
        } catch (NullPointerException ex) {
            //cool
        }

        try {
            Mixin mixin = new Mixin(Category.SCHEME_CORE_DEFAULT, null);
            fail();
        } catch (NullPointerException ex) {
            //cool
        }

        try {
            Mixin mixin = new Mixin(Category.SCHEME_CORE_DEFAULT, "");
            fail();
        } catch (IllegalArgumentException ex) {
            //cool
        }
    }

    @Test
    public void testToText() throws Exception {
        String[] lines = TestHelper.readFile(RESOURCE_PATH + "mixin_plain.txt").split("\n");
        Attribute at1 = new Attribute(IPNetwork.ADDRESS_ATTRIBUTE_NAME);
        Attribute at2 = new Attribute(IPNetwork.GATEWAY_ATTRIBUTE_NAME);
        Attribute at3 = new Attribute(IPNetwork.ALLOCATION_ATTRIBUTE_NAME);
        Attribute at4 = new Attribute(IPNetwork.STATE_ATTRIBUTE_NAME);
        Action a1 = new Action(new URI("http://schemas.ogf.org/occi/infrastructure/compute/action#"), "start");
        Action a2 = new Action(new URI("http://schemas.ogf.org/occi/infrastructure/compute/action#"), "stop");

        Mixin mixin = new Mixin(IPNetwork.SCHEME_DEFAULT, IPNetwork.TERM_DEFAULT);
        assertEquals(mixin.toText(), lines[0]);

        mixin.setTitle("IP Network Mixin");
        assertEquals(mixin.toText(), lines[1]);

        mixin.setTitle(null);
        mixin.setLocation(new URI("/mixins/ipnetwork/"));
        assertEquals(mixin.toText(), lines[2]);

        mixin.setLocation(null);
        mixin.addAttribute(at1);
        mixin.addAttribute(at2);
        mixin.addAttribute(at3);
        mixin.addAttribute(at4);
        assertEquals(mixin.toText(), lines[3]);

        mixin = new Mixin(IPNetwork.SCHEME_DEFAULT, IPNetwork.TERM_DEFAULT);
        mixin.addAction(a1);
        mixin.addAction(a2);
        assertEquals(mixin.toText(), lines[4]);

        mixin.addAttribute(at1);
        mixin.addAttribute(at2);
        mixin.addAttribute(at3);
        mixin.addAttribute(at4);
        mixin.getAttribute(IPNetwork.ADDRESS_ATTRIBUTE_NAME).setRequired(true);
        mixin.getAttribute(IPNetwork.GATEWAY_ATTRIBUTE_NAME).setImmutable(true);
        mixin.getAttribute(IPNetwork.ALLOCATION_ATTRIBUTE_NAME).setRequired(true);
        mixin.getAttribute(IPNetwork.ALLOCATION_ATTRIBUTE_NAME).setImmutable(true);
        mixin.setTitle("IP Network Mixin");
        mixin.setLocation(new URI("/mixins/ipnetwork/"));
        assertEquals(mixin.toText(), lines[5]);
    }

    @Test
    public void testToHeaders() throws Exception {
        String[] lines = TestHelper.readFile(RESOURCE_PATH + "mixin_headers.txt").split("\n");
        Attribute at1 = new Attribute(IPNetwork.ADDRESS_ATTRIBUTE_NAME);
        Attribute at2 = new Attribute(IPNetwork.GATEWAY_ATTRIBUTE_NAME);
        Attribute at3 = new Attribute(IPNetwork.ALLOCATION_ATTRIBUTE_NAME);
        Attribute at4 = new Attribute(IPNetwork.STATE_ATTRIBUTE_NAME);
        Action a1 = new Action(new URI("http://schemas.ogf.org/occi/infrastructure/compute/action#"), "start");
        Action a2 = new Action(new URI("http://schemas.ogf.org/occi/infrastructure/compute/action#"), "stop");

        Headers headers = new Headers();

        Mixin mixin = new Mixin(IPNetwork.SCHEME_DEFAULT, IPNetwork.TERM_DEFAULT);
        headers.add("Category", lines[0]);
        assertEquals(headers, mixin.toHeaders());

        mixin.setTitle("IP Network Mixin");
        headers.clear();
        headers.add("Category", lines[1]);
        assertEquals(headers, mixin.toHeaders());

        mixin.setTitle(null);
        mixin.setLocation(new URI("/mixins/ipnetwork/"));
        headers.clear();
        headers.add("Category", lines[2]);
        assertEquals(headers, mixin.toHeaders());

        mixin.setLocation(null);
        mixin.addAttribute(at1);
        mixin.addAttribute(at2);
        mixin.addAttribute(at3);
        mixin.addAttribute(at4);
        headers.clear();
        headers.add("Category", lines[3]);
        assertEquals(headers, mixin.toHeaders());

        mixin = new Mixin(IPNetwork.SCHEME_DEFAULT, IPNetwork.TERM_DEFAULT);
        mixin.addAction(a1);
        mixin.addAction(a2);
        headers.clear();
        headers.add("Category", lines[4]);
        assertEquals(headers, mixin.toHeaders());

        mixin.addAttribute(at1);
        mixin.addAttribute(at2);
        mixin.addAttribute(at3);
        mixin.addAttribute(at4);
        mixin.getAttribute(IPNetwork.ADDRESS_ATTRIBUTE_NAME).setRequired(true);
        mixin.getAttribute(IPNetwork.GATEWAY_ATTRIBUTE_NAME).setImmutable(true);
        mixin.getAttribute(IPNetwork.ALLOCATION_ATTRIBUTE_NAME).setRequired(true);
        mixin.getAttribute(IPNetwork.ALLOCATION_ATTRIBUTE_NAME).setImmutable(true);
        mixin.setTitle("IP Network Mixin");
        mixin.setLocation(new URI("/mixins/ipnetwork/"));
        headers.clear();
        headers.add("Category", lines[5]);
        assertEquals(headers, mixin.toHeaders());
    }
}
