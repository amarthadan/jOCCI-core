package cz.cesnet.cloud.occi.collection;

import cz.cesnet.cloud.occi.core.Attribute;
import cz.cesnet.cloud.occi.exception.NonexistingElementException;
import java.util.HashMap;
import java.util.Map;

public class AttributeMapCover {

    private final Map<Attribute, String> attributes = new HashMap<>();

    public void add(Attribute attribute, String value) {
        if (attribute == null) {
            throw new NullPointerException("Cannot add null attribute.");
        }
        if (value == null) {
            throw new NullPointerException("Cannot add null value.");
        }
        if (value.isEmpty()) {
            throw new IllegalArgumentException("Cannot add empty value.");
        }

        attributes.put(attribute, value);
    }

    public void remove(Attribute attribute) throws NonexistingElementException {
        if (attribute == null) {
            throw new NullPointerException("Cannot remove null attribute.");
        }
        if (!containsAttribute(attribute)) {
            throw new NonexistingElementException("ActionInstance " + this + "doesn't contain attribute " + attribute + ". Cannot be removed.");
        }

        attributes.remove(attribute);
    }

    public boolean containsAttribute(Attribute attribute) {
        return attributes.containsKey(attribute);
    }

    public boolean containsAttribute(String attributeName) {
        Attribute attTmp = new Attribute(attributeName);
        return containsAttribute(attTmp);
    }

    public String getValue(Attribute attribute) {
        return attributes.get(attribute);
    }

    public String getValue(String attributeName) {
        Attribute attTmp = new Attribute(attributeName);
        return getValue(attTmp);
    }

    public void clear() {
        attributes.clear();
    }

    @Override
    public String toString() {
        return "AttributeMapCover{" + attributes + '}';
    }
}
