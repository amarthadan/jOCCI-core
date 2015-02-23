package cz.cesnet.cloud.occi;

import cz.cesnet.cloud.occi.core.Action;
import cz.cesnet.cloud.occi.core.Kind;
import cz.cesnet.cloud.occi.core.Mixin;
import cz.cesnet.cloud.occi.exception.AmbiguousIdentifierException;
import cz.cesnet.cloud.occi.parser.CollectionType;
import java.net.URI;
import static java.util.Collections.list;
import java.util.HashSet;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

public class ModelTest {

    private Model model;

    public ModelTest() {
    }

    @Before
    public void setUp() throws Exception {
        model = new Model();

        for (Kind kind : DataGenerator.getFiveKinds()) {
            model.addKind(kind);
        }

        for (Mixin mixin : DataGenerator.getFiveMixins()) {
            model.addMixin(mixin);
        }

        for (Action action : DataGenerator.getFiveActions()) {
            model.addAction(action);
        }
    }

    @Test
    public void testFindKindWithURI() throws Exception {
        assertEquals(DataGenerator.getFiveKinds().get(2), model.findKind(URI.create("http://schemas.ogf.org/occi/core#link")));
        assertNull(model.findKind(URI.create("http://nonexisting.abc.org/icco/core#link")));
    }

    @Test
    public void testFindKindWithString() throws Exception {
        assertEquals(DataGenerator.getFiveKinds().get(2), model.findKind("link"));
        assertNull(model.findKind("nonexisting"));
    }

    @Test
    public void testInvalidFindKindWithString() throws Exception {
        Kind link = new Kind(new URI("http://different.uri.same/term/core#"), "link", "Link", new URI("/link/"), null);
        model.addKind(link);

        try {
            model.findKind("link");
            fail();
        } catch (AmbiguousIdentifierException ex) {
            //cool
        }
    }

    @Test
    public void testFindRelatedKindsWithURI() throws Exception {
        Set<Kind> set = new HashSet<>();
        set.add(DataGenerator.getFiveKinds().get(1));
        set.add(DataGenerator.getFiveKinds().get(2));

        assertEquals(set, new HashSet(model.findRelatedKinds(URI.create("http://schemas.ogf.org/occi/core#entity"))));
    }

    @Test
    public void testFindRelatedKindsWithString() throws Exception {
        Set<Kind> set = new HashSet<>();
        set.add(DataGenerator.getFiveKinds().get(1));
        set.add(DataGenerator.getFiveKinds().get(2));

        assertEquals(set, new HashSet(model.findRelatedKinds("entity")));
    }

    @Test
    public void testInvalidFindRelatedKindsWithString() throws Exception {
        Kind kind = new Kind(new URI("http://different.uri.same/term/core#"), "entity", "Entity", new URI("/entity/"), null);
        model.addKind(kind);

        try {
            model.findRelatedKinds("entity");
            fail();
        } catch (AmbiguousIdentifierException ex) {
            //cool
        }
    }

    @Test
    public void testFindKindTypeWithKind() throws Exception {
        assertNull(model.findKindType(DataGenerator.getFiveKinds().get(0)));
        assertEquals(CollectionType.RESOURCE, model.findKindType(DataGenerator.getFiveKinds().get(1)));
        assertEquals(CollectionType.LINK, model.findKindType(DataGenerator.getFiveKinds().get(2)));
        assertEquals(CollectionType.RESOURCE, model.findKindType(DataGenerator.getFiveKinds().get(3)));
        assertEquals(CollectionType.LINK, model.findKindType(DataGenerator.getFiveKinds().get(4)));
    }

    @Test
    public void testFindKindTypeWithString() throws Exception {
        assertNull(model.findKindType("/entity/"));
        assertEquals(CollectionType.RESOURCE, model.findKindType("/resource/"));
        assertEquals(CollectionType.LINK, model.findKindType("/link/"));
        assertEquals(CollectionType.RESOURCE, model.findKindType("/compute/"));
        assertEquals(CollectionType.LINK, model.findKindType("/storagelink/"));
    }

    @Test
    public void testFindMixinWithURI() throws Exception {
        assertEquals(DataGenerator.getFiveMixins().get(2), model.findMixin(URI.create("http://schemas.ogf.org/occi/infrastructure#resource_tpl")));
        assertNull(model.findMixin(URI.create("http://nonexisting.abc.org/icco/core#mixin")));
    }

    @Test
    public void testFindMixinWithString() throws Exception {
        assertEquals(DataGenerator.getFiveMixins().get(2), model.findMixin("resource_tpl"));
        assertNull(model.findMixin("nonexisting"));
    }

    @Test
    public void testInvalidFindMixinWithString() throws Exception {
        Mixin resourcetpl = new Mixin(new URI("http://different.uri.same/term/core#"), "resource_tpl", "Resource Template", new URI("/mixins/resource_tpl/"), null);
        model.addMixin(resourcetpl);

        try {
            model.findMixin("resource_tpl");
            fail();
        } catch (AmbiguousIdentifierException ex) {
            //cool
        }
    }

    @Test
    public void testFindMixinWithStringAndString() throws Exception {
        assertEquals(DataGenerator.getFiveMixins().get(3), model.findMixin("larger", "resource_tpl"));
        assertNull(model.findMixin("larger", "nonexisting"));
        assertNull(model.findMixin("nonexisting", "resource_tpl"));
    }

    @Test
    public void testInvalidFindMixinWithStringAndString() throws Exception {
        try {
            Mixin m = new Mixin(new URI("http://different.uri.same/term/resource_tpl#"), "larger", "Larger Instance - 4 cores and 10 GB of RAM", new URI("/mixins/larger/"), null);
            m.addRelation(model.findMixin("resource_tpl"));
            model.addMixin(m);
            model.findMixin("larger", "resource_tpl");
            fail();
        } catch (AmbiguousIdentifierException ex) {
            //cool
        }

        try {
            setUp();
            Mixin resourcetpl = new Mixin(new URI("http://different.uri.same/term/core#"), "resource_tpl", "Resource Template", new URI("/mixins/resource_tpl/"), null);
            model.addMixin(resourcetpl);
            model.findMixin("larger", "resource_tpl");
            fail();
        } catch (AmbiguousIdentifierException ex) {
            //cool
        }
    }

    @Test
    public void testFindMixinWithStringAndURI() throws Exception {
        assertEquals(DataGenerator.getFiveMixins().get(3), model.findMixin("larger", URI.create("http://schemas.ogf.org/occi/infrastructure#resource_tpl")));
        assertNull(model.findMixin("larger", URI.create("http://nonexisting.abc.org/icco/core#mixin")));
        assertNull(model.findMixin("nonexisting", URI.create("http://schemas.ogf.org/occi/infrastructure#resource_tpl")));
    }

    @Test
    public void testInvalidFindMixinWithStringAndURI() throws Exception {
        try {
            Mixin m = new Mixin(new URI("http://different.uri.same/term/resource_tpl#"), "larger", "Larger Instance - 4 cores and 10 GB of RAM", new URI("/mixins/larger/"), null);
            m.addRelation(model.findMixin("resource_tpl"));
            model.addMixin(m);
            model.findMixin("larger", "resource_tpl");
            fail();
        } catch (AmbiguousIdentifierException ex) {
            //cool
        }
    }

    @Test
    public void testFindRelatedMixinsWithURI() throws Exception {
        Set<Mixin> set = new HashSet<>();
        set.add(DataGenerator.getFiveMixins().get(4));
        Mixin m = new Mixin(new URI("https://occi.localhost/occi/infrastructure/os_tpl#"), "archlinux", "archlinux", new URI("/mixins/archlinux/"), null);
        m.addRelation(model.findMixin("os_tpl"));
        model.addMixin(m);
        set.add(m);

        assertEquals(set, new HashSet(model.findRelatedMixins(URI.create("http://schemas.ogf.org/occi/infrastructure#os_tpl"))));
    }

    @Test
    public void testFindRelatedMixinsWithString() throws Exception {
        Set<Mixin> set = new HashSet<>();
        set.add(DataGenerator.getFiveMixins().get(4));
        Mixin m = new Mixin(new URI("https://occi.localhost/occi/infrastructure/os_tpl#"), "archlinux", "archlinux", new URI("/mixins/archlinux/"), null);
        m.addRelation(model.findMixin("os_tpl"));
        model.addMixin(m);
        set.add(m);

        assertEquals(set, new HashSet(model.findRelatedMixins("os_tpl")));
    }

    @Test
    public void testInvalidFindRelatedMixinsWithString() throws Exception {
        Mixin mixin = new Mixin(new URI("http://different.uri.same/term/core#"), "os_tpl", "Operating System Template", new URI("/mixins/os_tpl/"), null);
        model.addMixin(mixin);

        try {
            model.findRelatedMixins("os_tpl");
            fail();
        } catch (AmbiguousIdentifierException ex) {
            //cool
        }
    }

    @Test
    public void testFindActionWithString() throws Exception {
        assertEquals(DataGenerator.getFiveActions().get(2), model.findAction("up"));
        assertNull(model.findAction("nonexisting"));
    }

    @Test
    public void testInvalidFindActionWithString() throws Exception {
        Action ac = new Action(new URI("http://different.uri.same/term/network/action#"), "up", "Activate network", null);
        model.addAction(ac);

        try {
            model.findAction("up");
            fail();
        } catch (AmbiguousIdentifierException ex) {
            //cool
        }
    }

    @Test
    public void testFindActionWithURI() throws Exception {
        assertEquals(DataGenerator.getFiveActions().get(2), model.findAction(URI.create("http://schemas.ogf.org/occi/infrastructure/network/action#up")));
        assertNull(model.findAction(URI.create("http://nonexisting.abc.org/icco/core#action")));
    }
}
