package cz.cesnet.cloud.occi.core;

import cz.cesnet.cloud.occi.collection.AttributeMapCover;
import cz.cesnet.cloud.occi.exception.NonexistingElementException;

public class ActionInstance {

    private final AttributeMapCover attributes = new AttributeMapCover();
    private Action action;

    public ActionInstance(Action action) {
        if (action == null) {
            throw new NullPointerException("ActionInstance action cannot be null.");
        }

        this.action = action;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        if (action == null) {
            throw new NullPointerException("ActionInstance action cannot be null.");
        }

        this.action = action;
    }

    public void addAttribute(Attribute attribute, String value) {
        attributes.add(attribute, value);
    }

    public void removeAttribute(Attribute attribute) throws NonexistingElementException {
        attributes.remove(attribute);
    }

    public boolean containsAttribute(Attribute attribute) {
        return attributes.containsAttribute(attribute);
    }

    public boolean containsAttribute(String attributeName) {
        return attributes.containsAttribute(attributeName);
    }

    public String getValue(Attribute attribute) {
        return attributes.getValue(attribute);
    }

    public String getValue(String attributeName) {
        return attributes.getValue(attributeName);
    }

    public void clearAttributes() {
        attributes.clear();
    }
}
