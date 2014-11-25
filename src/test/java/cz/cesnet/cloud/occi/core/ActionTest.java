package cz.cesnet.cloud.occi.core;

import cz.cesnet.cloud.occi.infrastructure.Compute;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;

public class ActionTest {

    @Test
    public void testFullConstructor() throws URISyntaxException {
        Set<Attribute> attributes = new HashSet<>();
        attributes.add(new Attribute("aaa"));
        attributes.add(new Attribute("bbb"));
        attributes.add(new Attribute("ccc"));
        Action action = new Action(Compute.getSchemeDefault(), "start", "Start", new URI("/location/"), attributes);

        assertEquals(action.getAttributes(), attributes);
        assertEquals(action.getLocation(), new URI("/location/"));
        assertEquals(action.getScheme(), Compute.getSchemeDefault());
        assertEquals(action.getTerm(), "start");
        assertEquals(action.getTitle(), "Start");
    }

    @Test
    public void testMinimalConstructor() throws URISyntaxException {
        Action action = new Action(Compute.getSchemeDefault(), "start");

        assertEquals(action.getScheme(), Compute.getSchemeDefault());
        assertEquals(action.getTerm(), "start");
    }

    @Test
    public void testInvalidConstructor() throws URISyntaxException {
        try {
            Action action = new Action(null, Entity.getTermDefult());
            fail();
        } catch (NullPointerException ex) {
            //cool
        }

        try {
            Action action = new Action(Category.SCHEME_CORE_DEFAULT, null);
            fail();
        } catch (NullPointerException ex) {
            //cool
        }

        try {
            Action action = new Action(Category.SCHEME_CORE_DEFAULT, "");
            fail();
        } catch (IllegalArgumentException ex) {
            //cool
        }
    }

    @Test
    public void testToText() throws Exception {
        String expected = "Link: </compute/123?action=start>;rel=\"http://schemas.ogf.org/occi/infrastructure/compute/action#start\";";
        Action action = new Action(new URI("http://schemas.ogf.org/occi/infrastructure/compute/action#"), "start");

        assertEquals(expected, action.toText("/compute/123"));
    }
}
