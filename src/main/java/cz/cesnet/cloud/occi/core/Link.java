package cz.cesnet.cloud.occi.core;

import cz.cesnet.cloud.occi.Model;
import cz.cesnet.cloud.occi.exception.InvalidAttributeValueException;
import cz.cesnet.cloud.occi.exception.RenderingException;
import cz.cesnet.cloud.occi.renderer.TextRenderer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        addAttribute(SOURCE_ATTRIBUTE_NAME, source.getLocation());
    }

    public void setSource(String sourceLocation) throws InvalidAttributeValueException {
        addAttribute(SOURCE_ATTRIBUTE_NAME, sourceLocation);
    }

    public String getTarget() {
        return getValue(TARGET_ATTRIBUTE_NAME);
    }

    public void setTarget(Resource target) throws InvalidAttributeValueException {
        addAttribute(TARGET_ATTRIBUTE_NAME, target.getLocation());
    }

    public void setTarget(String targetLocation) throws InvalidAttributeValueException {
        addAttribute(TARGET_ATTRIBUTE_NAME, targetLocation);
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public static String getTermDefault() {
        return "link";
    }

    public static String getIdentifierDefault() {
        return getSchemeDefault().toString() + getTermDefault();
    }

    @Override
    public String toText() {
        StringBuilder sb = new StringBuilder();

        sb.append(getKind().toText());

        List<Mixin> mixinList = new ArrayList<>(getMixins());
        Collections.sort(mixinList);
        for (Mixin m : mixinList) {
            sb.append("\n");
            sb.append(m.toText());
        }

        String attributesString = attributesToPrefixText();
        if (!attributesString.isEmpty()) {
            sb.append("\n");
            sb.append(attributesString);
        }

        return sb.toString();
    }

    public String toInlineText() throws RenderingException {
        StringBuilder sb = new StringBuilder("Link: ");
        if (getTarget() == null || getTarget().isEmpty()) {
            throw new RenderingException("Link " + this + " is missing a target attribute.");
        }
        sb.append(TextRenderer.surroundString(getTarget(), "<", ">;"));

        if (relation == null || relation.isEmpty()) {
            throw new RenderingException("Link " + this + " is missing a relation.");
        }
        sb.append("rel");
        sb.append(TextRenderer.surroundString(relation));

        if (getKind().getLocation() != null && getId() != null && !getId().isEmpty()) {
            sb.append("self");
            sb.append(TextRenderer.surroundString(getKind().getLocation().toString() + getId()));
        }

        sb.append("category");
        sb.append(TextRenderer.surroundString(getKind().getIdentifier()));

        sb.append(attributesToOneLineText());

        return sb.toString();
    }

    @Override
    public String toJSON() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
