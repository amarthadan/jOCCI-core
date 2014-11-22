package cz.cesnet.cloud.occi.core;

import cz.cesnet.cloud.occi.Model;
import cz.cesnet.cloud.occi.exception.InvalidAttributeValueException;

public class Link extends Entity {

    public static final String SOURCE_ATTRIBUTE_NAME = "occi.core.source";
    public static final String TARGET_ATTRIBUTE_NAME = "occi.core.target";

    private String relation;

    public Link(String id, Kind kind, String title, Model model) throws InvalidAttributeValueException {
        super(id, kind, title, model);
    }

    public Link(String id, Kind kind) throws InvalidAttributeValueException {
        super(id, kind);
    }

    public String getSource() {
        return getValue(SOURCE_ATTRIBUTE_NAME);
    }

    public void setSource(Resource source) throws InvalidAttributeValueException {
        addAttribute(SOURCE_ATTRIBUTE_NAME, source.getIdentifier());
    }

    public void setSource(String sourceIdentifier) throws InvalidAttributeValueException {
        addAttribute(SOURCE_ATTRIBUTE_NAME, sourceIdentifier);
    }

    public String getTarget() {
        return getValue(TARGET_ATTRIBUTE_NAME);
    }

    public void setTarget(Resource target) throws InvalidAttributeValueException {
        addAttribute(TARGET_ATTRIBUTE_NAME, target.getIdentifier());
    }

    public void setTarget(String targetIdentifier) throws InvalidAttributeValueException {
        addAttribute(TARGET_ATTRIBUTE_NAME, targetIdentifier);
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public static String getTermDefult() {
        return "link";
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
