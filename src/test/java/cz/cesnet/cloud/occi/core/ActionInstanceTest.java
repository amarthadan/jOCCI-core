package cz.cesnet.cloud.occi.core;

import com.sun.net.httpserver.Headers;
import cz.cesnet.cloud.occi.DataGenerator;
import cz.cesnet.cloud.occi.TestHelper;
import java.io.BufferedReader;
import java.io.FileReader;
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
        String expected = TestHelper.readFile(RESOURCE_PATH + "action_plain.txt");
        ActionInstance ai = DataGenerator.getAction();

        assertEquals(expected, ai.toText());
    }

    @Test
    public void testToHeaders() throws Exception {
        Headers headers = new Headers();
        ActionInstance ai = DataGenerator.getAction();

        headers.add("Category", TestHelper.readFile(RESOURCE_PATH + "action_headers_category.txt"));

        try (BufferedReader br = new BufferedReader(new FileReader(RESOURCE_PATH + "action_headers_attributes.txt"))) {
            String line = br.readLine();
            while (line != null) {
                headers.add("X-OCCI-Attribute", line);
                line = br.readLine();
            }
        }

        assertEquals(headers, ai.toHeaders());
    }
}
