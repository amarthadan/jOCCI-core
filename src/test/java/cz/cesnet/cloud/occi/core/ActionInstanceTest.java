package cz.cesnet.cloud.occi.core;

import cz.cesnet.cloud.occi.DataGenerator;
import cz.cesnet.cloud.occi.TestHelper;
import java.net.URI;
import org.junit.Test;
import static org.junit.Assert.*;

public class ActionInstanceTest {

    private static final String RESOURCE_PATH = "src/test/resources/rendering/text/";

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

    @Test
    public void testToText() throws Exception {
        String expected = TestHelper.readFile(RESOURCE_PATH + "action.txt");
        ActionInstance ai = DataGenerator.getAction();

        System.out.println(ai.toText());
        assertEquals(expected, ai.toText());
    }
}
