package cz.cesnet.cloud.occi.core;

import cz.cesnet.cloud.occi.Model;
import cz.cesnet.cloud.occi.exception.InvalidAttributeException;
import java.net.URI;

public class Link extends Entity {

    public static final String SOURCE_ATTRIBUTE_NAME = "occi.core.source";
    public static final String TARGET_ATTRIBUTE_NAME = "occi.core.target";

    public Link(URI id, Kind kind, String title, Model model) {
        super(id, kind, title, model);
    }

    public Link(URI id, Kind kind) {
        super(id, kind);
    }

    public String getSource() {
        return getValue(SOURCE_ATTRIBUTE_NAME);
    }

    public void setSource(Resource source) throws InvalidAttributeException {
        Attribute sourceAttribute = new Attribute(SOURCE_ATTRIBUTE_NAME);
        addAttribute(sourceAttribute, source.getIdentifier());
    }

    public void setSource(String sourceIdentifier) throws InvalidAttributeException {
        Attribute sourceAttribute = new Attribute(SOURCE_ATTRIBUTE_NAME);
        addAttribute(sourceAttribute, sourceIdentifier);
    }

    public String getTarget() {
        return getValue(TARGET_ATTRIBUTE_NAME);
    }

    public void setTarget(Resource target) throws InvalidAttributeException {
        Attribute sourceAttribute = new Attribute(TARGET_ATTRIBUTE_NAME);
        addAttribute(sourceAttribute, target.getIdentifier());
    }

    public void setTarget(String targetIdentifier) throws InvalidAttributeException {
        Attribute sourceAttribute = new Attribute(TARGET_ATTRIBUTE_NAME);
        addAttribute(sourceAttribute, targetIdentifier);
    }

    @Override
    public void toText() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void toJSON() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
