package cz.cesnet.cloud.occi.parser;

import cz.cesnet.cloud.occi.Model;
import cz.cesnet.cloud.occi.core.Action;
import cz.cesnet.cloud.occi.core.Attribute;
import cz.cesnet.cloud.occi.core.Kind;
import cz.cesnet.cloud.occi.core.Mixin;
import cz.cesnet.cloud.occi.core.Resource;
import cz.cesnet.cloud.occi.exception.InvalidAttributeValueException;
import cz.cesnet.cloud.occi.infrastructure.Compute;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;

public class TextParserTest {

    private static final String RESOURCE_PATH = "src/test/resources/parser/text/";

    private String readFile(String filename) throws IOException {
        File f = new File(filename);
        String fileContent = new String(Files.readAllBytes(f.toPath()));
        return fileContent;
    }

    private Model createMinimalModel() {
        Model model = new Model();

        Kind kind = new Kind("http://schemas.ogf.org/occi/core#", "entity");
        model.addKind(kind);

        return model;
    }

    private Model createKindsModel(Model startModel) {
        Model model;
        if (startModel != null) {
            model = startModel;
        } else {
            model = new Model();
        }

        Set<Attribute> attributes = new HashSet<>();

        Attribute a = new Attribute("occi.core.id");
        attributes.add(a);
        a = new Attribute("occi.core.title");
        attributes.add(a);
        Kind entity = new Kind("http://schemas.ogf.org/occi/core#", "entity", "Entity", "/entity/", attributes);
        model.addKind(entity);

        attributes.clear();
        a = new Attribute("occi.core.summary");
        attributes.add(a);
        Kind resource = new Kind("http://schemas.ogf.org/occi/core#", "resource", "Resource", "/resource/", attributes);
        resource.addRelation(entity);
        model.addKind(resource);

        attributes.clear();
        a = new Attribute("occi.core.target");
        attributes.add(a);
        a = new Attribute("occi.core.source");
        attributes.add(a);
        Kind link = new Kind("http://schemas.ogf.org/occi/core#", "link", "Link", "/link/", attributes);
        link.addRelation(entity);
        model.addKind(link);

        attributes.clear();
        a = new Attribute("occi.compute.architecture", false, true);
        attributes.add(a);
        a = new Attribute("occi.compute.cores");
        attributes.add(a);
        a = new Attribute("occi.compute.hostname");
        attributes.add(a);
        a = new Attribute("occi.compute.speed");
        attributes.add(a);
        a = new Attribute("occi.compute.memory");
        attributes.add(a);
        a = new Attribute("occi.compute.state");
        attributes.add(a);
        Kind k = new Kind("http://schemas.ogf.org/occi/infrastructure#", "compute", "Compute Resource", "/compute/", attributes);
        Action ac = new Action("http://schemas.ogf.org/occi/infrastructure/compute/action#", "start");
        k.addAction(ac);
        ac = new Action("http://schemas.ogf.org/occi/infrastructure/compute/action#", "stop");
        k.addAction(ac);
        ac = new Action("http://schemas.ogf.org/occi/infrastructure/compute/action#", "restart");
        k.addAction(ac);
        ac = new Action("http://schemas.ogf.org/occi/infrastructure/compute/action#", "suspend");
        k.addAction(ac);
        k.addRelation(resource);
        model.addKind(k);

        attributes.clear();
        a = new Attribute("occi.storagelink.deviceid", true, false);
        attributes.add(a);
        a = new Attribute("occi.storagelink.mountpoint");
        attributes.add(a);
        a = new Attribute("occi.storagelink.state", true, true);
        attributes.add(a);
        k = new Kind("http://schemas.ogf.org/occi/infrastructure#", "storagelink", "Storage Link", "/storage/", attributes);
        k.addRelation(link);
        model.addKind(k);

        attributes.clear();
        a = new Attribute("occi.networkinterface.interface");
        attributes.add(a);
        a = new Attribute("occi.networkinterface.mac");
        attributes.add(a);
        a = new Attribute("occi.networkinterface.state");
        attributes.add(a);
        k = new Kind("http://schemas.ogf.org/occi/infrastructure#", "networkinterface", "Networkinterface", "/networkinterface/", attributes);
        k.addRelation(link);
        model.addKind(k);

        attributes.clear();
        k = new Kind("http://schemas.ogf.org/occi/infrastructure/compute#", "console", "Link to the VM's console", "/console/", attributes);
        k.addRelation(link);
        model.addKind(k);

        return model;
    }

    private Model createMixinsModel(Model startModel) {
        Model model;
        if (startModel != null) {
            model = startModel;
        } else {
            model = new Model();
        }

        Set<Attribute> attributes = new HashSet<>();

        Mixin ostpl = new Mixin("http://schemas.ogf.org/occi/infrastructure#", "os_tpl", "Operating System Template", "/mixins/os_tpl/", attributes);
        model.addMixin(ostpl);

        attributes.clear();
        Attribute a = new Attribute("occi.network.address", false, true);
        attributes.add(a);
        a = new Attribute("occi.network.gateway");
        attributes.add(a);
        a = new Attribute("occi.network.allocation");
        attributes.add(a);
        a = new Attribute("occi.network.state");
        attributes.add(a);
        Mixin m = new Mixin("http://schemas.ogf.org/occi/infrastructure/network#", "ipnetwork", "IP Network Mixin", "/mixins/ipnetwork/", attributes);
        model.addMixin(m);

        attributes.clear();
        Mixin resourcetpl = new Mixin("http://schemas.ogf.org/occi/infrastructure#", "resource_tpl", "Resource Template", "/mixins/resource_tpl/", attributes);
        model.addMixin(resourcetpl);

        attributes.clear();
        a = new Attribute("occi.compute.architecture");
        attributes.add(a);
        a = new Attribute("occi.compute.cores", true, true);
        attributes.add(a);
        a = new Attribute("occi.compute.speed");
        attributes.add(a);
        a = new Attribute("occi.compute.memory");
        attributes.add(a);
        m = new Mixin("https://occi.localhost/occi/infrastructure/resource_tpl#", "larger", "Larger Instance - 4 cores and 10 GB of RAM", "/mixins/larger/", attributes);
        m.addRelation(resourcetpl);
        model.addMixin(m);

        attributes.clear();
        m = new Mixin("https://occi.localhost/occi/infrastructure/os_tpl#", "debianvm", "debianvm", "/mixins/debianvm/", attributes);
        m.addRelation(ostpl);
        model.addMixin(m);

        return model;
    }

    private Model createActionsModel(Model startModel) {
        Model model;
        if (startModel != null) {
            model = startModel;
        } else {
            model = new Model();
        }
        Set<Attribute> attributes = new HashSet<>();

        attributes.clear();
        Attribute a = new Attribute("method");
        attributes.add(a);
        Action ac = new Action("http://schemas.ogf.org/occi/infrastructure/compute/action#", "restart", "Restart Compute instance", null, attributes);
        model.addAction(ac);

        attributes.clear();
        a = new Attribute("method");
        attributes.add(a);
        ac = new Action("http://schemas.ogf.org/occi/infrastructure/compute/action#", "suspend", "Suspend Compute instance", null, attributes);
        model.addAction(ac);

        ac = new Action("http://schemas.ogf.org/occi/infrastructure/network/action#", "up", "Activate network", null, null);
        model.addAction(ac);

        ac = new Action("http://schemas.ogf.org/occi/infrastructure/network/action#", "down", "Deactivate network", null, null);
        model.addAction(ac);

        return model;
    }

    private Model createAllModel() {
        Model model = createKindsModel(null);
        model = createMixinsModel(model);
        model = createActionsModel(model);

        return model;
    }

    @Test
    public void testParseModelPlainMinimal() throws Exception {
        String body = readFile(RESOURCE_PATH + "model_plain_minimal.txt");
        Map<String, String> headers = null;
        TextParser instance = new TextParser();
        Model expResult = createMinimalModel();
        Model result = instance.parseModel(MediaType.TEXT_PLAIN, body, headers);
        assertEquals(expResult, result);
    }

    @Test
    public void testParseModelPlainKinds() throws Exception {
        String body = readFile(RESOURCE_PATH + "model_plain_kinds.txt");
        Map<String, String> headers = null;
        TextParser instance = new TextParser();
        Model expResult = createKindsModel(null);
        Model result = instance.parseModel(MediaType.TEXT_PLAIN, body, headers);
        assertEquals(expResult, result);
    }

    @Test
    public void testParseModelPlainMixins() throws Exception {
        String body = readFile(RESOURCE_PATH + "model_plain_mixins.txt");
        Map<String, String> headers = null;
        TextParser instance = new TextParser();
        Model expResult = createMixinsModel(null);
        Model result = instance.parseModel(MediaType.TEXT_PLAIN, body, headers);
        assertEquals(expResult, result);
    }

    @Test
    public void testParseModelPlainActions() throws Exception {
        String body = readFile(RESOURCE_PATH + "model_plain_actions.txt");
        Map<String, String> headers = null;
        TextParser instance = new TextParser();
        Model expResult = createActionsModel(null);
        Model result = instance.parseModel(MediaType.TEXT_PLAIN, body, headers);
        assertEquals(expResult, result);
    }

    @Test
    public void testParseModelPlainAll() throws Exception {
        String body = readFile(RESOURCE_PATH + "model_plain_all.txt");
        Map<String, String> headers = null;
        TextParser instance = new TextParser();
        Model expResult = createAllModel();
        Model result = instance.parseModel(MediaType.TEXT_PLAIN, body, headers);
        assertEquals(expResult, result);
    }

    private List<String> createLocations() {
        List<String> locations = new ArrayList<>();
        locations.add("http://rocci-server-1-1-x.herokuapp.com:80/compute/87f3bfc3-42d4-4474-b45c-757e55e093e9");
        locations.add("http://rocci-server-1-1-x.herokuapp.com:80/compute/17679ebd-975f-4ea0-b42b-47405178c360");
        locations.add("http://rocci-server-1-1-x.herokuapp.com:80/compute/509afbd3-abff-427c-9b25-7913d17e5102");

        return locations;
    }

    @Test
    public void testParseLocationsPlain() throws Exception {
        String body = readFile(RESOURCE_PATH + "locations_plain.txt");
        Map<String, String> headers = null;
        TextParser instance = new TextParser();
        List<String> expResult = createLocations();
        List<String> result = instance.parseLocations(MediaType.TEXT_PLAIN, body, headers);
        assertEquals(expResult, result);
    }

    @Test
    public void testParseLocationsUriList() throws Exception {
        String body = readFile(RESOURCE_PATH + "locations_uri-list.txt");
        Map<String, String> headers = null;
        TextParser instance = new TextParser();
        List<String> expResult = createLocations();
        List<String> result = instance.parseLocations(MediaType.TEXT_URI_LIST, body, headers);
        assertEquals(expResult, result);
    }

    private Map<String, String> createHeadersWithLocations() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Status Code", "200 OK");
        headers.put("Cache-Control", "no-cache");
        headers.put("Connection", "keep-alive");
        headers.put("Content-Length", "0");
        headers.put("Content-Type", "text/occi; charset=utf-8");
        headers.put("Date", "Thu, 06 Nov 2014 19:11:38 GMT");
        headers.put("Location", "http://rocci-server-1-1-x.herokuapp.com:80/compute/87f3bfc3-42d4-4474-b45c-757e55e093e9,http://rocci-server-1-1-x.herokuapp.com:80/compute/17679ebd-975f-4ea0-b42b-47405178c360,http://rocci-server-1-1-x.herokuapp.com:80/compute/509afbd3-abff-427c-9b25-7913d17e5102");
        headers.put("Server", "WEBrick/1.3.1 (Ruby/2.0.0/2014-09-19)");
        headers.put("Via", "1.1 vegur");
        headers.put("X-Frame-Options", "SAMEORIGIN");
        headers.put("X-Request-Id", "3191d404-a8f5-4bda-97d6-1069e71fc418");
        headers.put("X-Runtime", "0.025947");
        headers.put("X-XSS-Protection", "1; mode=block");
        headers.put("x-content-type-options", "nosniff");

        return headers;
    }

    @Test
    public void testParseLocationsOcci() throws Exception {
        String body = null;
        Map<String, String> headers = createHeadersWithLocations();
        TextParser instance = new TextParser();
        List<String> expResult = createLocations();
        List<String> result = instance.parseLocations(MediaType.TEXT_OCCI, body, headers);
        assertEquals(expResult, result);
    }

    private Resource createResource() {
        Kind k = new Kind("http://schemas.ogf.org/occi/infrastructure#", "compute", "compute resource", "/compute/", null);
        Mixin m = new Mixin("https://occi.localhost/occi/infrastructure/os_tpl#", "debian6", "debian", "/mixin/os_tpl/debian6/", null);
        Resource r = null;
        try {
            r = new Resource("87f3bfc3-42d4-4474-b45c-757e55e093e9", k);
            r.addMixin(m);
            r.setTitle("compute1");
            r.addAttribute(Compute.ARCHITECTURE_ATTRIBUTE_NAME, "x86");
            r.addAttribute(Compute.HOSTNAME_ATTRIBUTE_NAME, "compute1.example.org");
            r.addAttribute(Compute.MEMORY_ATTRIBUTE_NAME, "1.7");
            r.addAttribute(Compute.SPEED_ATTRIBUTE_NAME, "1.0");
            r.addAttribute(Compute.STATE_ATTRIBUTE_NAME, "active");
        } catch (InvalidAttributeValueException ex) {
            Logger.getLogger(TextParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        return r;
    }

//    @Test
//    public void testParseResourcePlain() throws Exception {
//        String body = readFile(RESOURCE_PATH + "resource_plain.txt");
//        Map<String, String> headers = null;
//        TextParser instance = new TextParser();
//        Resource expResult = createResource();
//        Resource result = instance.parseResource(MediaType.TEXT_PLAIN, body, headers);
//        assertEquals(expResult, result);
//    }
    private Map<String, String> createHeadersWithResource() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Status Code", "200 OK");
        headers.put("Cache-Control", "no-cache");
        headers.put("Category", "compute;scheme=\"http://schemas.ogf.org/occi/infrastructure#\";class=\"kind\";location=\"/compute/\";title=\"compute resource\",debian6;scheme=\"http://occi.example.org/occi/infrastructure/os_tpl#\";class=\"mixin\";location=\"/mixin/os_tpl/debian6/\";title=\"debian\"");
        headers.put("Connection", "keep-alive");
        headers.put("Content-Length", "0");
        headers.put("Content-Type", "text/occi; charset=utf-8");
        headers.put("Date", "Thu, 06 Nov 2014 19:11:38 GMT");
        headers.put("Server", "WEBrick/1.3.1 (Ruby/2.0.0/2014-09-19)");
        headers.put("Via", "1.1 vegur");
        headers.put("X-Frame-Options", "SAMEORIGIN");
        headers.put("X-Occi-Attribute", "occi.core.id=\"87f3bfc3-42d4-4474-b45c-757e55e093e9\",occi.core.title=\"compute1\",occi.compute.architecture=\"x86\",occi.compute.hostname=\"compute1.example.org\",occi.compute.memory=1.7,occi.compute.speed=1.0,occi.compute.state=\"active\"");
        headers.put("X-Request-Id", "3191d404-a8f5-4bda-97d6-1069e71fc418");
        headers.put("X-Runtime", "0.025947");
        headers.put("X-XSS-Protection", "1; mode=block");
        headers.put("x-content-type-options", "nosniff");

        return headers;
    }

//    @Test
//    public void testParseResourceOcci() throws Exception {
//        String body = null;
//        Map<String, String> headers = createHeadersWithResource();
//        TextParser instance = new TextParser();
//        Resource expResult = createResource();
//        Resource result = instance.parseResource(MediaType.TEXT_OCCI, body, headers);
//        assertEquals(expResult, result);
//    }
    @Test
    public void testParseModelPlainKindsMinimal() throws Exception {
        fail();
    }

    @Test
    public void testParseModelPlainKindsFull() throws Exception {
        fail();
    }

    @Test
    public void testParseModelPlainKindsRelations() throws Exception {
        fail();
    }

    @Test
    public void testParseModelPlainKindsAttributesZero() throws Exception {
        fail();
    }

    @Test
    public void testParseModelPlainKindsAttributesOne() throws Exception {
        fail();
    }

    @Test
    public void testParseModelPlainKindsAttributesBoth() throws Exception {
        fail();
    }

    @Test
    public void testParseModelPlainMixinsMinimal() throws Exception {
        fail();
    }

    @Test
    public void testParseModelPlainMixinsFull() throws Exception {
        fail();
    }

    @Test
    public void testParseModelPlainMixinsRelations() throws Exception {
        fail();
    }

    @Test
    public void testParseModelPlainMixinsAttributesZero() throws Exception {
        fail();
    }

    @Test
    public void testParseModelPlainMixinsAttributesOne() throws Exception {
        fail();
    }

    @Test
    public void testParseModelPlainMixinsAttributesBoth() throws Exception {
        fail();
    }

    @Test
    public void testParseModelPlainActionsMinimal() throws Exception {
        fail();
    }

    @Test
    public void testParseModelPlainActionsFull() throws Exception {
        fail();
    }

    @Test
    public void testParseModelPlainActionsAttributesZero() throws Exception {
        fail();
    }

    @Test
    public void testParseModelPlainActionsAttributesOne() throws Exception {
        fail();
    }

    @Test
    public void testParseModelPlainActionsAttributesBoth() throws Exception {
        fail();
    }

//    @Test
//    public void testParseModelPlainAll() throws Exception {
//        fail();
//    }
    @Test
    public void testParseModelOcciKindsMinimal() throws Exception {
        fail();
    }

    @Test
    public void testParseModelOcciKindsFull() throws Exception {
        fail();
    }

    @Test
    public void testParseModelOcciKindsRelations() throws Exception {
        fail();
    }

    @Test
    public void testParseModelOcciKindsAttributesZero() throws Exception {
        fail();
    }

    @Test
    public void testParseModelOcciKindsAttributesOne() throws Exception {
        fail();
    }

    @Test
    public void testParseModelOcciKindsAttributesBoth() throws Exception {
        fail();
    }

    @Test
    public void testParseModelOcciMixinsMinimal() throws Exception {
        fail();
    }

    @Test
    public void testParseModelOcciMixinsFull() throws Exception {
        fail();
    }

    @Test
    public void testParseModelOcciMixinsRelations() throws Exception {
        fail();
    }

    @Test
    public void testParseModelOcciMixinsAttributesZero() throws Exception {
        fail();
    }

    @Test
    public void testParseModelOcciMixinsAttributesOne() throws Exception {
        fail();
    }

    @Test
    public void testParseModelOcciMixinsAttributesBoth() throws Exception {
        fail();
    }

    @Test
    public void testParseModelOcciActionsMinimal() throws Exception {
        fail();
    }

    @Test
    public void testParseModelOcciActionsFull() throws Exception {
        fail();
    }

    @Test
    public void testParseModelOcciActionsAttributesZero() throws Exception {
        fail();
    }

    @Test
    public void testParseModelOcciActionsAttributesOne() throws Exception {
        fail();
    }

    @Test
    public void testParseModelOcciActionsAttributesBoth() throws Exception {
        fail();
    }

    @Test
    public void testParseModelOcciAll() throws Exception {
        fail();
    }

//    @Test
//    public void testParseLocationsPlain() throws Exception {
//        fail();
//    }
//
//    @Test
//    public void testParseLocationsOcci() throws Exception {
//        fail();
//    }
//
//    @Test
//    public void testParseLocationsUriList() throws Exception {
//        fail();
//    }
    @Test
    public void testParseCollectionPlainAttributes() throws Exception {
        fail();
    }

    @Test
    public void testParseCollectionPlainKinds() throws Exception {
        fail();
    }

    @Test
    public void testParseCollectionPlainMixins() throws Exception {
        fail();
    }

    @Test
    public void testParseCollectionPlainLinks() throws Exception {
        fail();
    }

    @Test
    public void testParseCollectionPlainAll() throws Exception {
        fail();
    }

    @Test
    public void testParseCollectionOcciAttributes() throws Exception {
        fail();
    }

    @Test
    public void testParseCollectionOcciKinds() throws Exception {
        fail();
    }

    @Test
    public void testParseCollectionOcciMixins() throws Exception {
        fail();
    }

    @Test
    public void testParseCollectionOcciLinks() throws Exception {
        fail();
    }

    @Test
    public void testParseCollectionOcciAll() throws Exception {
        fail();
    }
}
