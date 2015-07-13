package cz.cesnet.cloud.occi.core;

import cz.cesnet.cloud.occi.Model;
import cz.cesnet.cloud.occi.exception.InvalidAttributeValueException;
import cz.cesnet.cloud.occi.infrastructure.NetworkInterface;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class EntityTest {

    private Entity entity;
    private Kind kind;
    private Model model;
    private Mixin mixin;

    @Before
    public void setUp() throws Exception {
        kind = new Kind(Entity.SCHEME_DEFAULT, Entity.TERM_DEFAULT);
        mixin = new Mixin(NetworkInterface.SCHEME_DEFAULT, NetworkInterface.TERM_DEFAULT);
        model = new Model();
        model.addKind(kind);
        entity = new Link("link_id", kind);
    }

    @Test
    public void testConstructor() throws Exception {
        Entity entity = new Link("entity_id", kind, "title", model);

        assertEquals("entity_id", entity.getId());
        assertEquals(kind, entity.getKind());
        assertEquals("title", entity.getTitle());
        assertEquals(model, entity.getModel());
    }

    @Test
    public void testInvalidConstructor() throws Exception {
        try {
            Entity entity = new Link(null, kind, "title", model);
            fail();
        } catch (NullPointerException ex) {
            //cool
        }

        try {
            Entity entity = new Link("entity_id", null, "title", model);
            fail();
        } catch (NullPointerException ex) {
            //cool
        }
    }

    @Test
    public void testInvalidSetId() throws Exception {
        try {
            entity.setId(null);
            fail();
        } catch (NullPointerException ex) {
            //cool
        }
    }

    @Test
    public void testInvalidSetKind() throws Exception {
        try {
            entity.setKind(null);
            fail();
        } catch (NullPointerException ex) {
            //cool
        }
    }

    @Test
    public void testAddAttribute() throws Exception {
        Attribute attrKind = new Attribute("attrKind");
        attrKind.setPattern("xyz");
        kind.addAttribute(attrKind);
        Attribute attrMixin = new Attribute("attrMixin");
        attrMixin.setPattern("abc");
        mixin.addAttribute(attrMixin);
        entity.addMixin(mixin);

        entity.addAttribute("attrKind", "xyz");
        entity.addAttribute("attrMixin", "abc");
        entity.addAttribute("nonexistingAttribute", "value");

        assertEquals("xyz", entity.getValue("attrKind"));
        assertEquals("xyz", entity.getValue(attrKind));
        assertEquals("abc", entity.getValue("attrMixin"));
        assertEquals("abc", entity.getValue(attrMixin));
        assertEquals("value", entity.getValue("nonexistingAttribute"));
    }

    @Test
    public void testInvalidAddAttribute() {
        Attribute attrKind = new Attribute("attrKind");
        attrKind.setPattern("xyz");
        kind.addAttribute(attrKind);
        Attribute attrMixin = new Attribute("attrMixin");
        attrMixin.setPattern("abc");
        mixin.addAttribute(attrMixin);
        entity.addMixin(mixin);

        try {
            entity.addAttribute("attrKind", "abc");
            fail();
        } catch (InvalidAttributeValueException ex) {
            //cool
        }

        try {
            entity.addAttribute("attrMixin", "xyz");
            fail();
        } catch (InvalidAttributeValueException ex) {
            //cool
        }
    }
}
