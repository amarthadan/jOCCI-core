package cz.cesnet.cloud.occi.core;

import com.sun.net.httpserver.Headers;
import cz.cesnet.cloud.occi.DataGenerator;
import cz.cesnet.cloud.occi.TestHelper;
import java.io.BufferedReader;
import java.io.FileReader;
import org.junit.Test;
import static org.junit.Assert.*;

public class ResourceTest {

    private static final String RESOURCE_PATH = "src/test/resources/rendering/text/";

    @Test
    public void testToText() throws Exception {
        String expected = TestHelper.readFile(RESOURCE_PATH + "resource_plain.txt");
        Resource resource = DataGenerator.getResource();

        assertEquals(expected, resource.toText());
    }

    @Test
    public void testToHeaders() throws Exception {
        Headers headers = new Headers();
        Resource resource = DataGenerator.getResource();

        try (BufferedReader br = new BufferedReader(new FileReader(RESOURCE_PATH + "resource_headers_categories.txt"))) {
            String line = br.readLine();
            while (line != null) {
                headers.add("Category", line);
                line = br.readLine();
            }
        }
        try (BufferedReader br = new BufferedReader(new FileReader(RESOURCE_PATH + "resource_headers_attributes.txt"))) {
            String line = br.readLine();
            while (line != null) {
                headers.add("X-OCCI-Attribute", line);
                line = br.readLine();
            }
        }
        try (BufferedReader br = new BufferedReader(new FileReader(RESOURCE_PATH + "resource_headers_links.txt"))) {
            String line = br.readLine();
            while (line != null) {
                headers.add("Link", line);
                line = br.readLine();
            }
        }

        for (String name : headers.keySet()) {
            System.out.println(name);
            System.out.println(headers.get(name));
        }
        for (String name : resource.toHeaders().keySet()) {
            System.out.println(name);
            System.out.println(resource.toHeaders().get(name));
        }

        assertEquals(headers, resource.toHeaders());
    }
}
