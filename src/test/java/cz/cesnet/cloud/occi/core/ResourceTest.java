package cz.cesnet.cloud.occi.core;

import cz.cesnet.cloud.occi.DataGenerator;
import cz.cesnet.cloud.occi.TestHelper;
import org.junit.Test;
import static org.junit.Assert.*;

public class ResourceTest {

    private static final String RESOURCE_PATH = "src/test/resources/rendering/text/";

    @Test
    public void testToText() throws Exception {
        String expected = TestHelper.readFile(RESOURCE_PATH + "resource.txt");
        Resource resource = DataGenerator.getResource();

        System.out.println(resource.toText());
        assertEquals(expected, resource.toText());
    }
}
