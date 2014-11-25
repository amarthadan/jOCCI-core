package cz.cesnet.cloud.occi.core;

import cz.cesnet.cloud.occi.TestHelper;
import cz.cesnet.cloud.occi.infrastructure.NetworkInterface;
import java.net.URI;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class LinkTest {

    private static final String RESOURCE_PATH = "src/test/resources/rendering/text/";

    @Test
    public void testToText() throws Exception {
        String[] lines = TestHelper.readFile(RESOURCE_PATH + "link.txt").split("\n");

        Kind kind = new Kind(NetworkInterface.getSchemeDefault(), NetworkInterface.getTermDefult());
        Link link = new Link("456", kind);
        link.setRelation("http://schemas.ogf.org/occi/infrastructure#network");
        link.setTarget("/network/123");
        assertEquals(lines[0], link.toText());

        link.getKind().setLocation(new URI("/link/networkinterface/"));
        assertEquals(lines[1], link.toText());

        link.getKind().setLocation(null);
        link.addAttribute("occi.networkinterface.interface", "eth0");
        link.addAttribute("occi.networkinterface.mac", "00:11:22:33:44:55");
        link.addAttribute("occi.networkinterface.state", "active");
        assertEquals(lines[2], link.toText());

        link.getKind().setLocation(new URI("/link/networkinterface/"));
        assertEquals(lines[3], link.toText());
    }
}
