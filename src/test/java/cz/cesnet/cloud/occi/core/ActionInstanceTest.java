package cz.cesnet.cloud.occi.core;

import java.net.URI;
import org.junit.Test;
import static org.junit.Assert.*;

public class ActionInstanceTest {

    @Test
    public void testConstructor() throws Exception {
        Action action = new Action(new URI("http://dummy.action/"), "term");
        ActionInstance ai = new ActionInstance(action);
        assertEquals(action, ai.getAction());
    }

    @Test
    public void testInvalidConstructor() {
        try {
            ActionInstance ai = new ActionInstance(null);
            fail();
        } catch (NullPointerException ex) {
            //cool
        }
    }

    @Test
    public void testInvalidSetAction() throws Exception {
        try {
            Action action = new Action(new URI("http://dummy.action/"), "term");
            ActionInstance ai = new ActionInstance(action);
            ai.setAction(null);
            fail();
        } catch (NullPointerException ex) {
            //cool
        }
    }
}
