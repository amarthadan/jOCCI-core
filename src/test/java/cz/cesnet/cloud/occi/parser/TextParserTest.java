package cz.cesnet.cloud.occi.parser;

import cz.cesnet.cloud.occi.Collection;
import cz.cesnet.cloud.occi.DataGenerator;
import cz.cesnet.cloud.occi.Model;
import cz.cesnet.cloud.occi.TestHelper;
import cz.cesnet.cloud.occi.core.Action;
import cz.cesnet.cloud.occi.core.ActionInstance;
import cz.cesnet.cloud.occi.core.Attribute;
import cz.cesnet.cloud.occi.core.Kind;
import cz.cesnet.cloud.occi.core.Link;
import cz.cesnet.cloud.occi.core.Mixin;
import cz.cesnet.cloud.occi.core.Resource;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;

public class TextParserTest {

    private static final String RESOURCE_PATH = "src/test/resources/parser/text/";

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
        String body = TestHelper.readFile(RESOURCE_PATH + "model_plain_kinds_minimal.txt");
        Map<String, String> headers = null;
        TextParser instance = new TextParser();
        Model expResult = populateModelWithKinds(DataGenerator.getMinimalKind(), null);
        Model result = instance.parseModel(MediaType.TEXT_PLAIN, body, headers);
        assertEquals(expResult, result);
        assertKindsEqual(expResult.getKinds(), result.getKinds());
    }

    @Test
    public void testParseModelPlainKindsFull() throws Exception {
        String body = TestHelper.readFile(RESOURCE_PATH + "model_plain_kinds_full.txt");
        Map<String, String> headers = null;
        TextParser instance = new TextParser();
        Model expResult = populateModelWithKinds(DataGenerator.getFiveKinds(), null);
        Model result = instance.parseModel(MediaType.TEXT_PLAIN, body, headers);
        assertEquals(expResult, result);
        assertKindsEqual(expResult.getKinds(), result.getKinds());
    }

    @Test
    public void testParseModelPlainMixinsMinimal() throws Exception {
        String body = TestHelper.readFile(RESOURCE_PATH + "model_plain_mixins_minimal.txt");
        Map<String, String> headers = null;
        TextParser instance = new TextParser();
        Model expResult = populateModelWithMixins(DataGenerator.getMinimalMixin(), null);
        Model result = instance.parseModel(MediaType.TEXT_PLAIN, body, headers);
        assertEquals(expResult, result);
        assertMixinsEqual(expResult.getMixins(), result.getMixins());
    }

    @Test
    public void testParseModelPlainMixinsFull() throws Exception {
        String body = TestHelper.readFile(RESOURCE_PATH + "model_plain_mixins_full.txt");
        Map<String, String> headers = null;
        TextParser instance = new TextParser();
        Model expResult = populateModelWithMixins(DataGenerator.getFiveMixins(), null);
        Model result = instance.parseModel(MediaType.TEXT_PLAIN, body, headers);
        assertEquals(expResult, result);
        assertMixinsEqual(expResult.getMixins(), result.getMixins());
    }

    @Test
    public void testParseModelPlainActionsMinimal() throws Exception {
        String body = TestHelper.readFile(RESOURCE_PATH + "model_plain_actions_minimal.txt");
        Map<String, String> headers = null;
        TextParser instance = new TextParser();
        Model expResult = populateModelWithActions(DataGenerator.getMinimalAction(), null);
        Model result = instance.parseModel(MediaType.TEXT_PLAIN, body, headers);
        assertEquals(expResult, result);
        assertActionsEqual(expResult.getActions(), result.getActions());
    }

    @Test
    public void testParseModelPlainActionsFull() throws Exception {
        String body = TestHelper.readFile(RESOURCE_PATH + "model_plain_actions_full.txt");
        Map<String, String> headers = null;
        TextParser instance = new TextParser();
        Model expResult = populateModelWithActions(DataGenerator.getFiveActions(), null);
        Model result = instance.parseModel(MediaType.TEXT_PLAIN, body, headers);
        assertEquals(expResult, result);
        assertActionsEqual(expResult.getActions(), result.getActions());
    }

    @Test
    public void testParseModelPlainAll() throws Exception {
        String body = TestHelper.readFile(RESOURCE_PATH + "model_plain_all.txt");
        Map<String, String> headers = null;
        TextParser instance = new TextParser();
        Model expResult = populateModelWithKinds(DataGenerator.getFiveKinds(), null);
        expResult = populateModelWithMixins(DataGenerator.getFiveMixins(), expResult);
        expResult = populateModelWithActions(DataGenerator.getFiveActions(), expResult);
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
        String categoryHeader = TestHelper.readFile(RESOURCE_PATH + "model_occi_kinds_minimal.txt");
        String body = null;
        Map<String, String> headers = createDefaultHeaders();
        headers.put("Category", categoryHeader);
        TextParser instance = new TextParser();
        Model expResult = populateModelWithKinds(DataGenerator.getMinimalKind(), null);
        Model result = instance.parseModel(MediaType.TEXT_OCCI, body, headers);
        assertEquals(expResult, result);
        assertKindsEqual(expResult.getKinds(), result.getKinds());
    }

    @Test
    public void testParseModelOcciKindsFull() throws Exception {
        String categoryHeader = TestHelper.readFile(RESOURCE_PATH + "model_occi_kinds_full.txt");
        String body = null;
        Map<String, String> headers = createDefaultHeaders();
        headers.put("Category", categoryHeader);
        TextParser instance = new TextParser();
        Model expResult = populateModelWithKinds(DataGenerator.getFiveKinds(), null);
        Model result = instance.parseModel(MediaType.TEXT_OCCI, body, headers);
        assertEquals(expResult, result);
        assertKindsEqual(expResult.getKinds(), result.getKinds());
    }

    @Test
    public void testParseModelOcciMixinsMinimal() throws Exception {
        String categoryHeader = TestHelper.readFile(RESOURCE_PATH + "model_occi_mixins_minimal.txt");
        String body = null;
        Map<String, String> headers = createDefaultHeaders();
        headers.put("Category", categoryHeader);
        TextParser instance = new TextParser();
        Model expResult = populateModelWithMixins(DataGenerator.getMinimalMixin(), null);
        Model result = instance.parseModel(MediaType.TEXT_OCCI, body, headers);
        assertEquals(expResult, result);
        assertMixinsEqual(expResult.getMixins(), result.getMixins());
    }

    @Test
    public void testParseModelOcciMixinsFull() throws Exception {
        String categoryHeader = TestHelper.readFile(RESOURCE_PATH + "model_occi_mixins_full.txt");
        String body = null;
        Map<String, String> headers = createDefaultHeaders();
        headers.put("Category", categoryHeader);
        TextParser instance = new TextParser();
        Model expResult = populateModelWithMixins(DataGenerator.getFiveMixins(), null);
        Model result = instance.parseModel(MediaType.TEXT_OCCI, body, headers);
        assertEquals(expResult, result);
        assertMixinsEqual(expResult.getMixins(), result.getMixins());
    }

    @Test
    public void testParseModelOcciActionsMinimal() throws Exception {
        String categoryHeader = TestHelper.readFile(RESOURCE_PATH + "model_occi_actions_minimal.txt");
        String body = null;
        Map<String, String> headers = createDefaultHeaders();
        headers.put("Category", categoryHeader);
        TextParser instance = new TextParser();
        Model expResult = populateModelWithActions(DataGenerator.getMinimalAction(), null);
        Model result = instance.parseModel(MediaType.TEXT_OCCI, body, headers);
        assertEquals(expResult, result);
        assertActionsEqual(expResult.getActions(), result.getActions());
    }

    @Test
    public void testParseModelOcciActionsFull() throws Exception {
        String categoryHeader = TestHelper.readFile(RESOURCE_PATH + "model_occi_actions_full.txt");
        String body = null;
        Map<String, String> headers = createDefaultHeaders();
        headers.put("Category", categoryHeader);
        TextParser instance = new TextParser();
        Model expResult = populateModelWithActions(DataGenerator.getFiveActions(), null);
        Model result = instance.parseModel(MediaType.TEXT_OCCI, body, headers);
        assertEquals(expResult, result);
        assertActionsEqual(expResult.getActions(), result.getActions());
    }

    @Test
    public void testParseModelOcciAll() throws Exception {
        String categoryHeader = TestHelper.readFile(RESOURCE_PATH + "model_occi_all.txt");
        String body = null;
        Map<String, String> headers = createDefaultHeaders();
        headers.put("Category", categoryHeader);
        TextParser instance = new TextParser();
        Model expResult = populateModelWithKinds(DataGenerator.getFiveKinds(), null);
        expResult = populateModelWithMixins(DataGenerator.getFiveMixins(), expResult);
        expResult = populateModelWithActions(DataGenerator.getFiveActions(), expResult);
        Model result = instance.parseModel(MediaType.TEXT_OCCI, body, headers);
        assertEquals(expResult, result);
        assertKindsEqual(expResult.getKinds(), result.getKinds());
        assertMixinsEqual(expResult.getMixins(), result.getMixins());
        assertActionsEqual(expResult.getActions(), result.getActions());
    }

    @Test
    public void testParseLocationsPlain() throws Exception {
        String body = TestHelper.readFile(RESOURCE_PATH + "locations_plain.txt");
        Map<String, String> headers = null;
        TextParser instance = new TextParser();
        List<URI> expResult = DataGenerator.getLocations();
        List<URI> result = instance.parseLocations(MediaType.TEXT_PLAIN, body, headers);
        assertEquals(expResult, result);
    }

    @Test
    public void testParseLocationsUriList() throws Exception {
        String body = TestHelper.readFile(RESOURCE_PATH + "locations_uri-list.txt");
        Map<String, String> headers = null;
        TextParser instance = new TextParser();
        List<URI> expResult = DataGenerator.getLocations();
        List<URI> result = instance.parseLocations(MediaType.TEXT_URI_LIST, body, headers);
        assertEquals(expResult, result);
    }

    @Test
    public void testParseLocationsOcci() throws Exception {
        String body = null;
        Map<String, String> headers = createDefaultHeaders();
        headers.put("Location", "http://rocci-server-1-1-x.herokuapp.com:80/compute/87f3bfc3-42d4-4474-b45c-757e55e093e9,http://rocci-server-1-1-x.herokuapp.com:80/compute/17679ebd-975f-4ea0-b42b-47405178c360,http://rocci-server-1-1-x.herokuapp.com:80/compute/509afbd3-abff-427c-9b25-7913d17e5102");
        TextParser instance = new TextParser();
        List<URI> expResult = DataGenerator.getLocations();
        List<URI> result = instance.parseLocations(MediaType.TEXT_OCCI, body, headers);
        assertEquals(expResult, result);
    }

    @Test
    public void testParseCollectionPlainResource() throws Exception {
        String body = TestHelper.readFile(RESOURCE_PATH + "collection_plain_resource.txt");
        Map<String, String> headers = null;
        TextParser instance = new TextParser();

        Collection expResult = new Collection();
        expResult.addResource(DataGenerator.getResource());
        Collection result = instance.parseCollection(MediaType.TEXT_PLAIN, body, headers, CollectionType.RESOURCE);
        assertEquals(expResult, result);
        assertResourcesEqual(expResult.getResources(), result.getResources());
    }

    @Test
    public void testParseCollectionPlainLink() throws Exception {
        String body = TestHelper.readFile(RESOURCE_PATH + "collection_plain_link.txt");
        Map<String, String> headers = null;
        TextParser instance = new TextParser();

        Collection expResult = new Collection();
        expResult.addLink(DataGenerator.getLink());
        Collection result = instance.parseCollection(MediaType.TEXT_PLAIN, body, headers, CollectionType.LINK);
        assertEquals(expResult, result);
        assertLinksEqual(expResult.getLinks(), result.getLinks());
    }

    @Test
    public void testParseCollectionPlainAction() throws Exception {
        String body = TestHelper.readFile(RESOURCE_PATH + "collection_plain_action.txt");
        Map<String, String> headers = null;
        TextParser instance = new TextParser();

        Collection expResult = new Collection();
        expResult.addAction(DataGenerator.getAction());
        Collection result = instance.parseCollection(MediaType.TEXT_PLAIN, body, headers, CollectionType.ACTION);
        assertEquals(expResult, result);
        assertActionInstancesEqual(expResult.getActions(), result.getActions());
    }

    @Test
    public void testParseCollectionOcciResource() throws Exception {
        String categoryHeader = TestHelper.readFile(RESOURCE_PATH + "collection_occi_resource_category.txt");
        String attributeHeader = TestHelper.readFile(RESOURCE_PATH + "collection_occi_resource_attribute.txt");
        String linkHeader = TestHelper.readFile(RESOURCE_PATH + "collection_occi_resource_link.txt");
        String body = null;
        Map<String, String> headers = createDefaultHeaders();
        headers.put("Category", categoryHeader);
        headers.put("X-Occi-Attribute", attributeHeader);
        headers.put("Link", linkHeader);
        TextParser instance = new TextParser();
        Collection expResult = new Collection();
        expResult.addResource(DataGenerator.getResource());
        Collection result = instance.parseCollection(MediaType.TEXT_OCCI, body, headers, CollectionType.RESOURCE);
        assertEquals(expResult, result);
        assertResourcesEqual(expResult.getResources(), result.getResources());
    }

    @Test
    public void testParseCollectionOcciLink() throws Exception {
        String categoryHeader = TestHelper.readFile(RESOURCE_PATH + "collection_occi_link_category.txt");
        String attributeHeader = TestHelper.readFile(RESOURCE_PATH + "collection_occi_link_attribute.txt");
        String body = null;
        Map<String, String> headers = createDefaultHeaders();
        headers.put("Category", categoryHeader);
        headers.put("X-Occi-Attribute", attributeHeader);
        TextParser instance = new TextParser();
        Collection expResult = new Collection();
        expResult.addLink(DataGenerator.getLink());
        Collection result = instance.parseCollection(MediaType.TEXT_OCCI, body, headers, CollectionType.LINK);
        assertEquals(expResult, result);
        assertLinksEqual(expResult.getLinks(), result.getLinks());
    }

    @Test
    public void testParseCollectionOcciAction() throws Exception {
        String categoryHeader = TestHelper.readFile(RESOURCE_PATH + "collection_occi_action_category.txt");
        String attributeHeader = TestHelper.readFile(RESOURCE_PATH + "collection_occi_action_attribute.txt");
        String body = null;
        Map<String, String> headers = createDefaultHeaders();
        headers.put("Category", categoryHeader);
        headers.put("X-Occi-Attribute", attributeHeader);
        TextParser instance = new TextParser();
        Collection expResult = new Collection();
        expResult.addAction(DataGenerator.getAction());
        Collection result = instance.parseCollection(MediaType.TEXT_OCCI, body, headers, CollectionType.ACTION);
        assertEquals(expResult, result);
        assertActionInstancesEqual(expResult.getActions(), result.getActions());
    }

    private void assertKindsEqual(Set<Kind> expected, Set<Kind> result) {
        assertEquals(expected.size(), result.size());

        List<Kind> expectedList = new ArrayList<>();
        expectedList.addAll(expected);
        Collections.sort(expectedList);
        List<Kind> resultList = new ArrayList<>();
        resultList.addAll(result);
        Collections.sort(resultList);
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
        Collections.sort(expectedList);
        List<Mixin> resultList = new ArrayList<>();
        resultList.addAll(result);
        Collections.sort(resultList);
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
        Collections.sort(expectedList);
        List<Action> resultList = new ArrayList<>();
        resultList.addAll(result);
        Collections.sort(resultList);
        for (int i = 0; i < expectedList.size(); i++) {
            assertActionDeepEquals(expectedList.get(i), resultList.get(i));
        }
    }

    private void assertActionDeepEquals(Action expected, Action result) {
        System.out.println("comparing " + expected + " with " + result);
        assertEquals(expected, result);

        assertEquals(expected.getTitle(), result.getTitle());
        assertAttributesEqual(expected.getAttributes(), result.getAttributes());
    }

    private void assertActionInstancesEqual(Set<ActionInstance> expected, Set<ActionInstance> result) {
        assertEquals(expected.size(), result.size());

        List<ActionInstance> expectedList = new ArrayList<>();
        expectedList.addAll(expected);
        Collections.sort(expectedList);
        List<ActionInstance> resultList = new ArrayList<>();
        resultList.addAll(result);
        Collections.sort(resultList);
        for (int i = 0; i < expectedList.size(); i++) {
            assertActionInstanceDeepEquals(expectedList.get(i), resultList.get(i));
        }
    }

    private void assertActionInstanceDeepEquals(ActionInstance expected, ActionInstance result) {
        System.out.println("comparing " + expected + " with " + result);
        assertEquals(expected, result);

        assertActionDeepEquals(expected.getAction(), result.getAction());
        for (Attribute expAttr : expected.getAttributes().keySet()) {
            if (!result.getAttributes().containsKey(expAttr)) {
                fail();
            }
            assertEquals(expected.getAttributes().get(expAttr), result.getAttributes().get(expAttr));
        }
        assertEquals(expected.getAttributes(), result.getAttributes());
    }

    private void assertLinksEqual(Set<Link> expected, Set<Link> result) {
        assertEquals(expected.size(), result.size());

        List<Link> expectedList = new ArrayList<>();
        expectedList.addAll(expected);
        Collections.sort(expectedList);
        System.out.println("expected links: " + expectedList);
        List<Link> resultList = new ArrayList<>();
        resultList.addAll(result);
        Collections.sort(resultList);
        System.out.println("result links: " + resultList);
        for (int i = 0; i < expectedList.size(); i++) {
            assertLinkDeepEquals(expectedList.get(i), resultList.get(i));
        }
    }

    private void assertLinkDeepEquals(Link expected, Link result) {
        System.out.println("comparing " + expected + " with " + result);
        assertEquals(expected, result);

        assertEquals(expected.getTitle(), result.getTitle());
        assertEquals(expected.getRelation(), result.getRelation());
        assertEquals(expected.getMixins(), result.getMixins());
        assertEquals(expected.getAttributes(), result.getAttributes());
    }

    private void assertResourcesEqual(Set<Resource> expected, Set<Resource> result) {
        assertEquals(expected.size(), result.size());

        List<Resource> expectedList = new ArrayList<>();
        expectedList.addAll(expected);
        Collections.sort(expectedList);
        List<Resource> resultList = new ArrayList<>();
        resultList.addAll(result);
        Collections.sort(resultList);
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
        assertEquals(expected.getActions(), result.getActions());
        assertLinksEqual(expected.getLinks(), result.getLinks());
    }

    private void assertAttributesEqual(Set<Attribute> expected, Set<Attribute> result) {
        assertEquals(expected.size(), result.size());

        List<Attribute> expectedList = new ArrayList<>();
        expectedList.addAll(expected);
        Collections.sort(expectedList);
        List<Attribute> resultList = new ArrayList<>();
        resultList.addAll(result);
        Collections.sort(resultList);
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
