package cz.cesnet.cloud.occi.parser;

import cz.cesnet.cloud.occi.Collection;
import cz.cesnet.cloud.occi.Model;
import cz.cesnet.cloud.occi.core.Action;
import cz.cesnet.cloud.occi.core.Attribute;
import cz.cesnet.cloud.occi.core.Kind;
import cz.cesnet.cloud.occi.core.Link;
import cz.cesnet.cloud.occi.core.Mixin;
import cz.cesnet.cloud.occi.core.Resource;
import cz.cesnet.cloud.occi.exception.InvalidAttributeValueException;
import cz.cesnet.cloud.occi.infrastructure.Compute;
import cz.cesnet.cloud.occi.infrastructure.NetworkInterface;
import cz.cesnet.cloud.occi.infrastructure.StorageLink;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

    private List<Kind> getMinimalKind() throws URISyntaxException {
        List<Kind> kinds = new ArrayList<>();
        Kind kind = new Kind(new URI("http://schemas.ogf.org/occi/core#"), "entity");
        kinds.add(kind);

        return kinds;
    }

    private List<Kind> getFiveKinds() throws URISyntaxException {
        Set<Attribute> attributes = new HashSet<>();
        List<Kind> kinds = new ArrayList<>();

        Attribute a = new Attribute("occi.core.id");
        attributes.add(a);
        a = new Attribute("occi.core.title");
        attributes.add(a);
        Kind entity = new Kind(new URI("http://schemas.ogf.org/occi/core#"), "entity", "Entity", new URI("/entity/"), attributes);
        kinds.add(entity);

        attributes.clear();
        a = new Attribute("occi.core.summary");
        attributes.add(a);
        Kind resource = new Kind(new URI("http://schemas.ogf.org/occi/core#"), "resource", "Resource", new URI("/resource/"), attributes);
        resource.addRelation(entity);
        kinds.add(resource);

        attributes.clear();
        a = new Attribute("occi.core.target");
        attributes.add(a);
        a = new Attribute("occi.core.source");
        attributes.add(a);
        Kind link = new Kind(new URI("http://schemas.ogf.org/occi/core#"), "link", "Link", new URI("/link/"), attributes);
        link.addRelation(entity);
        kinds.add(link);

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
        Kind k = new Kind(new URI("http://schemas.ogf.org/occi/infrastructure#"), "compute", "Compute Resource", new URI("/compute/"), attributes);
        Action ac = new Action(new URI("http://schemas.ogf.org/occi/infrastructure/compute/action#"), "start");
        k.addAction(ac);
        ac = new Action(new URI("http://schemas.ogf.org/occi/infrastructure/compute/action#"), "stop");
        k.addAction(ac);
        ac = new Action(new URI("http://schemas.ogf.org/occi/infrastructure/compute/action#"), "restart");
        k.addAction(ac);
        ac = new Action(new URI("http://schemas.ogf.org/occi/infrastructure/compute/action#"), "suspend");
        k.addAction(ac);
        k.addRelation(resource);
        kinds.add(k);

        attributes.clear();
        a = new Attribute("occi.storagelink.deviceid", true, false);
        attributes.add(a);
        a = new Attribute("occi.storagelink.mountpoint");
        attributes.add(a);
        a = new Attribute("occi.storagelink.state", true, true);
        attributes.add(a);
        k = new Kind(new URI("http://schemas.ogf.org/occi/infrastructure#"), "storagelink", "Storage Link", new URI("/storagelink/"), attributes);
        k.addRelation(link);
        kinds.add(k);

        return kinds;
    }

    private List<Mixin> getMinimalMixin() throws URISyntaxException {
        List<Mixin> mixins = new ArrayList<>();
        Mixin ostpl = new Mixin(new URI("http://schemas.ogf.org/occi/infrastructure#"), "os_tpl");
        mixins.add(ostpl);

        return mixins;
    }

    private List<Mixin> getFiveMixins() throws URISyntaxException {
        Set<Attribute> attributes = new HashSet<>();
        List<Mixin> mixins = new ArrayList<>();

        Mixin ostpl = new Mixin(new URI("http://schemas.ogf.org/occi/infrastructure#"), "os_tpl", "Operating System Template", new URI("/mixins/os_tpl/"), attributes);
        mixins.add(ostpl);

        attributes.clear();
        Attribute a = new Attribute("occi.network.address", true, false);
        attributes.add(a);
        a = new Attribute("occi.network.gateway");
        attributes.add(a);
        a = new Attribute("occi.network.allocation");
        attributes.add(a);
        a = new Attribute("occi.network.state");
        attributes.add(a);
        Mixin m = new Mixin(new URI("http://schemas.ogf.org/occi/infrastructure/network#"), "ipnetwork", "IP Network Mixin", new URI("/mixins/ipnetwork/"), attributes);
        mixins.add(m);

        attributes.clear();
        Mixin resourcetpl = new Mixin(new URI("http://schemas.ogf.org/occi/infrastructure#"), "resource_tpl", "Resource Template", new URI("/mixins/resource_tpl/"), attributes);
        mixins.add(resourcetpl);

        attributes.clear();
        a = new Attribute("occi.compute.architecture");
        attributes.add(a);
        a = new Attribute("occi.compute.cores", true, true);
        attributes.add(a);
        a = new Attribute("occi.compute.speed");
        attributes.add(a);
        a = new Attribute("occi.compute.memory", false, true);
        attributes.add(a);
        m = new Mixin(new URI("https://occi.localhost/occi/infrastructure/resource_tpl#"), "larger", "Larger Instance - 4 cores and 10 GB of RAM", new URI("/mixins/larger/"), attributes);
        m.addRelation(resourcetpl);
        mixins.add(m);

        attributes.clear();
        m = new Mixin(new URI("https://occi.localhost/occi/infrastructure/os_tpl#"), "debianvm", "debianvm", new URI("/mixins/debianvm/"), attributes);
        m.addRelation(ostpl);
        mixins.add(m);

        return mixins;
    }

    private List<Action> getMinimalAction() throws URISyntaxException {
        List<Action> actions = new ArrayList<>();
        Action ac = new Action(new URI("http://schemas.ogf.org/occi/infrastructure/network/action#"), "up");
        actions.add(ac);

        return actions;
    }

    private List<Action> getFiveActions() throws URISyntaxException {
        List<Action> actions = new ArrayList<>();
        Set<Attribute> attributes = new HashSet<>();

        attributes.clear();
        Attribute a = new Attribute("method");
        attributes.add(a);
        Action ac = new Action(new URI("http://schemas.ogf.org/occi/infrastructure/compute/action#"), "restart", "Restart Compute instance", null, attributes);
        actions.add(ac);

        attributes.clear();
        a = new Attribute("method");
        attributes.add(a);
        ac = new Action(new URI("http://schemas.ogf.org/occi/infrastructure/compute/action#"), "suspend", "Suspend Compute instance", null, attributes);
        actions.add(ac);

        ac = new Action(new URI("http://schemas.ogf.org/occi/infrastructure/network/action#"), "up", "Activate network", null, null);
        actions.add(ac);

        ac = new Action(new URI("http://schemas.ogf.org/occi/infrastructure/network/action#"), "down", "Deactivate network", null, null);
        actions.add(ac);

        ac = new Action(new URI("http://schemas.ogf.org/occi/infrastructure/storage/action#"), "backup", "Backup Storage", null, null);
        actions.add(ac);

        return actions;
    }

    private Model populateModelWithKinds(List<Kind> kinds, Model initialModel) {
        Model model;
        if (initialModel == null) {
            model = new Model();
        } else {
            model = initialModel;
        }

        for (Kind k : kinds) {
            model.addKind(k);
        }

        return model;
    }

    private Model populateModelWithMixins(List<Mixin> mixins, Model initialModel) {
        Model model;
        if (initialModel == null) {
            model = new Model();
        } else {
            model = initialModel;
        }

        for (Mixin k : mixins) {
            model.addMixin(k);
        }

        return model;
    }

    private Model populateModelWithActions(List<Action> actions, Model initialModel) {
        Model model;
        if (initialModel == null) {
            model = new Model();
        } else {
            model = initialModel;
        }

        for (Action k : actions) {
            model.addAction(k);
        }

        return model;
    }

    @Test
    public void testParseModelPlainKindsMinimal() throws Exception {
        String body = readFile(RESOURCE_PATH + "model_plain_kinds_minimal.txt");
        Map<String, String> headers = null;
        TextParser instance = new TextParser();
        Model expResult = populateModelWithKinds(getMinimalKind(), null);
        Model result = instance.parseModel(MediaType.TEXT_PLAIN, body, headers);
        assertEquals(expResult, result);
        assertKindsEqual(expResult.getKinds(), result.getKinds());
    }

    @Test
    public void testParseModelPlainKindsFull() throws Exception {
        String body = readFile(RESOURCE_PATH + "model_plain_kinds_full.txt");
        Map<String, String> headers = null;
        TextParser instance = new TextParser();
        Model expResult = populateModelWithKinds(getFiveKinds(), null);
        Model result = instance.parseModel(MediaType.TEXT_PLAIN, body, headers);
        assertEquals(expResult, result);
        assertKindsEqual(expResult.getKinds(), result.getKinds());
    }

    @Test
    public void testParseModelPlainMixinsMinimal() throws Exception {
        String body = readFile(RESOURCE_PATH + "model_plain_mixins_minimal.txt");
        Map<String, String> headers = null;
        TextParser instance = new TextParser();
        Model expResult = populateModelWithMixins(getMinimalMixin(), null);
        Model result = instance.parseModel(MediaType.TEXT_PLAIN, body, headers);
        assertEquals(expResult, result);
        assertMixinsEqual(expResult.getMixins(), result.getMixins());
    }

    @Test
    public void testParseModelPlainMixinsFull() throws Exception {
        String body = readFile(RESOURCE_PATH + "model_plain_mixins_full.txt");
        Map<String, String> headers = null;
        TextParser instance = new TextParser();
        Model expResult = populateModelWithMixins(getFiveMixins(), null);
        Model result = instance.parseModel(MediaType.TEXT_PLAIN, body, headers);
        assertEquals(expResult, result);
        assertMixinsEqual(expResult.getMixins(), result.getMixins());
    }

    @Test
    public void testParseModelPlainActionsMinimal() throws Exception {
        String body = readFile(RESOURCE_PATH + "model_plain_actions_minimal.txt");
        Map<String, String> headers = null;
        TextParser instance = new TextParser();
        Model expResult = populateModelWithActions(getMinimalAction(), null);
        Model result = instance.parseModel(MediaType.TEXT_PLAIN, body, headers);
        assertEquals(expResult, result);
        assertActionsEqual(expResult.getActions(), result.getActions());
    }

    @Test
    public void testParseModelPlainActionsFull() throws Exception {
        String body = readFile(RESOURCE_PATH + "model_plain_actions_full.txt");
        Map<String, String> headers = null;
        TextParser instance = new TextParser();
        Model expResult = populateModelWithActions(getFiveActions(), null);
        Model result = instance.parseModel(MediaType.TEXT_PLAIN, body, headers);
        assertEquals(expResult, result);
        assertActionsEqual(expResult.getActions(), result.getActions());
    }

    @Test
    public void testParseModelPlainAll() throws Exception {
        String body = readFile(RESOURCE_PATH + "model_plain_all.txt");
        Map<String, String> headers = null;
        TextParser instance = new TextParser();
        Model expResult = populateModelWithKinds(getFiveKinds(), null);
        expResult = populateModelWithMixins(getFiveMixins(), expResult);
        expResult = populateModelWithActions(getFiveActions(), expResult);
        Model result = instance.parseModel(MediaType.TEXT_PLAIN, body, headers);
        assertEquals(expResult, result);
        assertKindsEqual(expResult.getKinds(), result.getKinds());
        assertMixinsEqual(expResult.getMixins(), result.getMixins());
        assertActionsEqual(expResult.getActions(), result.getActions());
    }

    private Map<String, String> createDefaultHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Status Code", "200 OK");
        headers.put("Cache-Control", "no-cache");
        headers.put("Connection", "keep-alive");
        headers.put("Content-Length", "0");
        headers.put("Content-Type", "text/occi; charset=utf-8");
        headers.put("Date", "Thu, 06 Nov 2014 19:11:38 GMT");
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
    public void testParseModelOcciKindsMinimal() throws Exception {
        String categoryHeader = readFile(RESOURCE_PATH + "model_occi_kinds_minimal.txt");
        String body = null;
        Map<String, String> headers = createDefaultHeaders();
        headers.put("Category", categoryHeader);
        TextParser instance = new TextParser();
        Model expResult = populateModelWithKinds(getMinimalKind(), null);
        Model result = instance.parseModel(MediaType.TEXT_OCCI, body, headers);
        assertEquals(expResult, result);
        assertKindsEqual(expResult.getKinds(), result.getKinds());
    }

    @Test
    public void testParseModelOcciKindsFull() throws Exception {
        String categoryHeader = readFile(RESOURCE_PATH + "model_occi_kinds_full.txt");
        String body = null;
        Map<String, String> headers = createDefaultHeaders();
        headers.put("Category", categoryHeader);
        TextParser instance = new TextParser();
        Model expResult = populateModelWithKinds(getFiveKinds(), null);
        Model result = instance.parseModel(MediaType.TEXT_OCCI, body, headers);
        assertEquals(expResult, result);
        assertKindsEqual(expResult.getKinds(), result.getKinds());
    }

    @Test
    public void testParseModelOcciMixinsMinimal() throws Exception {
        String categoryHeader = readFile(RESOURCE_PATH + "model_occi_mixins_minimal.txt");
        String body = null;
        Map<String, String> headers = createDefaultHeaders();
        headers.put("Category", categoryHeader);
        TextParser instance = new TextParser();
        Model expResult = populateModelWithMixins(getMinimalMixin(), null);
        Model result = instance.parseModel(MediaType.TEXT_OCCI, body, headers);
        assertEquals(expResult, result);
        assertMixinsEqual(expResult.getMixins(), result.getMixins());
    }

    @Test
    public void testParseModelOcciMixinsFull() throws Exception {
        String categoryHeader = readFile(RESOURCE_PATH + "model_occi_mixins_full.txt");
        String body = null;
        Map<String, String> headers = createDefaultHeaders();
        headers.put("Category", categoryHeader);
        TextParser instance = new TextParser();
        Model expResult = populateModelWithMixins(getFiveMixins(), null);
        Model result = instance.parseModel(MediaType.TEXT_OCCI, body, headers);
        assertEquals(expResult, result);
        assertMixinsEqual(expResult.getMixins(), result.getMixins());
    }

    @Test
    public void testParseModelOcciActionsMinimal() throws Exception {
        String categoryHeader = readFile(RESOURCE_PATH + "model_occi_actions_minimal.txt");
        String body = null;
        Map<String, String> headers = createDefaultHeaders();
        headers.put("Category", categoryHeader);
        TextParser instance = new TextParser();
        Model expResult = populateModelWithActions(getMinimalAction(), null);
        Model result = instance.parseModel(MediaType.TEXT_OCCI, body, headers);
        assertEquals(expResult, result);
        assertActionsEqual(expResult.getActions(), result.getActions());
    }

    @Test
    public void testParseModelOcciActionsFull() throws Exception {
        String categoryHeader = readFile(RESOURCE_PATH + "model_occi_actions_full.txt");
        String body = null;
        Map<String, String> headers = createDefaultHeaders();
        headers.put("Category", categoryHeader);
        TextParser instance = new TextParser();
        Model expResult = populateModelWithActions(getFiveActions(), null);
        Model result = instance.parseModel(MediaType.TEXT_OCCI, body, headers);
        assertEquals(expResult, result);
        assertActionsEqual(expResult.getActions(), result.getActions());
    }

    @Test
    public void testParseModelOcciAll() throws Exception {
        String categoryHeader = readFile(RESOURCE_PATH + "model_occi_all.txt");
        String body = null;
        Map<String, String> headers = createDefaultHeaders();
        headers.put("Category", categoryHeader);
        TextParser instance = new TextParser();
        Model expResult = populateModelWithKinds(getFiveKinds(), null);
        expResult = populateModelWithMixins(getFiveMixins(), expResult);
        expResult = populateModelWithActions(getFiveActions(), expResult);
        Model result = instance.parseModel(MediaType.TEXT_OCCI, body, headers);
        assertEquals(expResult, result);
        assertKindsEqual(expResult.getKinds(), result.getKinds());
        assertMixinsEqual(expResult.getMixins(), result.getMixins());
        assertActionsEqual(expResult.getActions(), result.getActions());
    }

    private List<URI> getLocations() throws URISyntaxException {
        List<URI> locations = new ArrayList<>();
        locations.add(new URI("http://rocci-server-1-1-x.herokuapp.com:80/compute/87f3bfc3-42d4-4474-b45c-757e55e093e9"));
        locations.add(new URI("http://rocci-server-1-1-x.herokuapp.com:80/compute/17679ebd-975f-4ea0-b42b-47405178c360"));
        locations.add(new URI("http://rocci-server-1-1-x.herokuapp.com:80/compute/509afbd3-abff-427c-9b25-7913d17e5102"));

        return locations;
    }

    @Test
    public void testParseLocationsPlain() throws Exception {
        String body = readFile(RESOURCE_PATH + "locations_plain.txt");
        Map<String, String> headers = null;
        TextParser instance = new TextParser();
        List<URI> expResult = getLocations();
        List<URI> result = instance.parseLocations(MediaType.TEXT_PLAIN, body, headers);
        assertEquals(expResult, result);
    }

    @Test
    public void testParseLocationsUriList() throws Exception {
        String body = readFile(RESOURCE_PATH + "locations_uri-list.txt");
        Map<String, String> headers = null;
        TextParser instance = new TextParser();
        List<URI> expResult = getLocations();
        List<URI> result = instance.parseLocations(MediaType.TEXT_URI_LIST, body, headers);
        assertEquals(expResult, result);
    }

    @Test
    public void testParseLocationsOcci() throws Exception {
        String body = null;
        Map<String, String> headers = createDefaultHeaders();
        headers.put("Location", "http://rocci-server-1-1-x.herokuapp.com:80/compute/87f3bfc3-42d4-4474-b45c-757e55e093e9,http://rocci-server-1-1-x.herokuapp.com:80/compute/17679ebd-975f-4ea0-b42b-47405178c360,http://rocci-server-1-1-x.herokuapp.com:80/compute/509afbd3-abff-427c-9b25-7913d17e5102");
        TextParser instance = new TextParser();
        List<URI> expResult = getLocations();
        List<URI> result = instance.parseLocations(MediaType.TEXT_OCCI, body, headers);
        assertEquals(expResult, result);
    }

    //    private Resource createResource() {
//        Kind k = new Kind("http://schemas.ogf.org/occi/infrastructure#", "compute", "compute resource", "/compute/", null);
//        Mixin m = new Mixin("https://occi.localhost/occi/infrastructure/os_tpl#", "debian6", "debian", "/mixin/os_tpl/debian6/", null);
//        Resource r = null;
//        try {
//            r = new Resource("87f3bfc3-42d4-4474-b45c-757e55e093e9", k);
//            r.addMixin(m);
//            r.setTitle("compute1");
//            r.addAttribute(Compute.ARCHITECTURE_ATTRIBUTE_NAME, "x86");
//            r.addAttribute(Compute.HOSTNAME_ATTRIBUTE_NAME, "compute1.example.org");
//            r.addAttribute(Compute.MEMORY_ATTRIBUTE_NAME, "1.7");
//            r.addAttribute(Compute.SPEED_ATTRIBUTE_NAME, "1.0");
//            r.addAttribute(Compute.STATE_ATTRIBUTE_NAME, "active");
//        } catch (InvalidAttributeValueException ex) {
//            Logger.getLogger(TextParserTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        return r;
//    }
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
    private Resource getResource() throws InvalidAttributeValueException, URISyntaxException {
        Kind k = new Kind(new URI("http://schemas.ogf.org/occi/infrastructure#"), "compute", "compute resource", new URI("/compute/"), null);
        Resource r = new Resource("87f3bfc3-42d4-4474-b45c-757e55e093e9", k);
        r.setTitle("compute1");
        r.addAttribute(Compute.ARCHITECTURE_ATTRIBUTE_NAME, "x86");
        r.addAttribute(Compute.HOSTNAME_ATTRIBUTE_NAME, "compute1.example.org");
        r.addAttribute(Compute.MEMORY_ATTRIBUTE_NAME, "1.7");
        r.addAttribute(Compute.SPEED_ATTRIBUTE_NAME, "1.0");
        r.addAttribute(Compute.STATE_ATTRIBUTE_NAME, "active");

        List<Mixin> mixins = getFiveMixins();
        for (Mixin mixin : mixins) {
            r.addMixin(mixin);
        }

        List<Link> links = getLinks();
        for (Link link : links) {
            link.setSource(r);
            r.addLink(link);
        }

        return r;
    }

    private List<Link> getLinks() throws URISyntaxException, InvalidAttributeValueException {
        List<Link> links = new ArrayList<>();

        Kind k = new Kind(new URI("http://schemas.ogf.org/occi/infrastructure#"), "networkinterface", null, new URI("/link/networkinterface/"), null);
        Link l = new Link("456", k);
        l.addAttribute(NetworkInterface.INTERFACE_ATTRIBUTE_NAME, "eth0");
        l.addAttribute(NetworkInterface.MAC_ATTRIBUTE_NAME, "00:11:22:33:44:55");
        l.addAttribute(NetworkInterface.STATE_ATTRIBUTE_NAME, "active");
        l.setTarget("/network/123");
        links.add(l);

        k = new Kind(new URI("http://schemas.ogf.org/occi/infrastructure#"), "storagelink", null, new URI("/link/storagelink/"), null);
        l = new Link("789", k);
        l.addAttribute(StorageLink.DEVICE_ID_ATTRIBUTE_NAME, "1234qwerty");
        l.addAttribute(StorageLink.MOUNTPOINT_ATTRIBUTE_NAME, "/mnt/somewhere/");
        l.addAttribute(StorageLink.STATE_ATTRIBUTE_NAME, "active");
        l.setTarget("/storage/852");
        links.add(l);

        return links;
    }

    @Test
    public void testParseCollectionPlainResource() throws Exception {
        String body = readFile(RESOURCE_PATH + "collection_plain_resource.txt");
        Map<String, String> headers = null;
        TextParser instance = new TextParser();

        Collection expResult = new Collection();
        expResult.addResource(getResource());
        Collection result = instance.parseCollection(MediaType.TEXT_PLAIN, body, headers);
        assertEquals(expResult, result);
        assertResourcesEqual(expResult.getResources(), result.getResources());
    }

    @Test
    public void testParseCollectionPlainLink() throws Exception {
        fail();
    }

    @Test
    public void testParseCollectionOcciResource() throws Exception {
        String categoryHeader = readFile(RESOURCE_PATH + "collection_occi_resource_category.txt");
        String attributeHeader = readFile(RESOURCE_PATH + "collection_occi_resource_attribute.txt");
        String linkHeader = readFile(RESOURCE_PATH + "collection_occi_resource_link.txt");
        String body = null;
        Map<String, String> headers = createDefaultHeaders();
        headers.put("Category", categoryHeader);
        headers.put("X-Occi-Attribute", attributeHeader);
        headers.put("Link", linkHeader);
        TextParser instance = new TextParser();
        Collection expResult = new Collection();
        expResult.addResource(getResource());
        Collection result = instance.parseCollection(MediaType.TEXT_OCCI, body, headers);
        assertEquals(expResult, result);
        assertResourcesEqual(expResult.getResources(), result.getResources());
    }

    @Test
    public void testParseCollectionOcciLink() throws Exception {
        fail();
    }

    private void assertKindsEqual(Set<Kind> expected, Set<Kind> result) {
        assertEquals(expected.size(), result.size());

        List<Kind> expectedList = new ArrayList<>();
        expectedList.addAll(expected);
        List<Kind> resultList = new ArrayList<>();
        resultList.addAll(result);
        for (int i = 0; i < expectedList.size(); i++) {
            assertKindDeepEquals(expectedList.get(i), resultList.get(i));
        }
    }

    private void assertKindDeepEquals(Kind expected, Kind result) {
        System.out.println("comparing " + expected + " with " + result);
        assertEquals(expected, result);

        assertEquals(expected.getTitle(), result.getTitle());
        assertEquals(expected.getLocation(), result.getLocation());
        assertEquals(expected.getRelations(), result.getRelations());
        assertAttributesEqual(expected.getAttributes(), result.getAttributes());
    }

    private void assertMixinsEqual(Set<Mixin> expected, Set<Mixin> result) {
        assertEquals(expected.size(), result.size());

        List<Mixin> expectedList = new ArrayList<>();
        expectedList.addAll(expected);
        List<Mixin> resultList = new ArrayList<>();
        resultList.addAll(result);
        for (int i = 0; i < expectedList.size(); i++) {
            assertMixinDeepEquals(expectedList.get(i), resultList.get(i));
        }
    }

    private void assertMixinDeepEquals(Mixin expected, Mixin result) {
        System.out.println("comparing " + expected + " with " + result);
        assertEquals(expected, result);

        assertEquals(expected.getTitle(), result.getTitle());
        assertEquals(expected.getLocation(), result.getLocation());
        assertEquals(expected.getRelations(), result.getRelations());
        assertAttributesEqual(expected.getAttributes(), result.getAttributes());
    }

    private void assertActionsEqual(Set<Action> expected, Set<Action> result) {
        assertEquals(expected.size(), result.size());

        List<Action> expectedList = new ArrayList<>();
        expectedList.addAll(expected);
        List<Action> resultList = new ArrayList<>();
        resultList.addAll(result);
        for (int i = 0; i < expectedList.size(); i++) {
            assertActionDeepEquals(expectedList.get(i), resultList.get(i));
        }
    }

    private void assertActionDeepEquals(Action expected, Action result) {
        System.out.println("comparing " + expected + " with " + result);
        assertEquals(expected, result);

        assertEquals(expected.getTitle(), result.getTitle());
        assertEquals(expected.getLocation(), result.getLocation());
        assertAttributesEqual(expected.getAttributes(), result.getAttributes());
    }

    private void assertLinksEqual(Set<Link> expected, Set<Link> result) {
        assertEquals(expected.size(), result.size());

        List<Link> expectedList = new ArrayList<>();
        expectedList.addAll(expected);
        System.out.println("expected links: " + expectedList);
        List<Link> resultList = new ArrayList<>();
        resultList.addAll(result);
        System.out.println("result links: " + resultList);
        for (int i = 0; i < expectedList.size(); i++) {
            assertLinkDeepEquals(expectedList.get(i), resultList.get(i));
        }
    }

    private void assertLinkDeepEquals(Link expected, Link result) {
        System.out.println("comparing " + expected + " with " + result);
        assertEquals(expected, result);

        assertEquals(expected.getTitle(), result.getTitle());
        assertEquals(expected.getMixins(), result.getMixins());
        assertEquals(expected.getAttributes(), result.getAttributes());
    }

    private void assertResourcesEqual(Set<Resource> expected, Set<Resource> result) {
        assertEquals(expected.size(), result.size());

        List<Resource> expectedList = new ArrayList<>();
        expectedList.addAll(expected);
        List<Resource> resultList = new ArrayList<>();
        resultList.addAll(result);
        for (int i = 0; i < expectedList.size(); i++) {
            assertResourceDeepEquals(expectedList.get(i), resultList.get(i));
        }
    }

    private void assertResourceDeepEquals(Resource expected, Resource result) {
        System.out.println("comparing " + expected + " with " + result);
        assertEquals(expected, result);

        assertEquals(expected.getTitle(), result.getTitle());
        assertEquals(expected.getMixins(), result.getMixins());
        assertEquals(expected.getAttributes(), result.getAttributes());
        assertLinksEqual(expected.getLinks(), result.getLinks());
    }

    private void assertAttributesEqual(Set<Attribute> expected, Set<Attribute> result) {
        assertEquals(expected.size(), result.size());

        List<Attribute> expectedList = new ArrayList<>();
        expectedList.addAll(expected);
        List<Attribute> resultList = new ArrayList<>();
        resultList.addAll(result);
        for (int i = 0; i < expectedList.size(); i++) {
            assertAttributeDeepEquals(expectedList.get(i), resultList.get(i));
        }
    }

    private void assertAttributeDeepEquals(Attribute expected, Attribute result) {
        assertEquals(expected, result);

        assertEquals(expected.isRequired(), result.isRequired());
        assertEquals(expected.isImmutable(), result.isImmutable());
        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getPattern(), result.getPattern());
        assertEquals(expected.getDefaultValue(), result.getDefaultValue());
        assertEquals(expected.getDescription(), result.getDescription());
    }
}
