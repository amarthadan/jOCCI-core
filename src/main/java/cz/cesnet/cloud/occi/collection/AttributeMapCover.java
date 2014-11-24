package cz.cesnet.cloud.occi.collection;

import cz.cesnet.cloud.occi.core.Attribute;
import cz.cesnet.cloud.occi.parser.TextParser;
import cz.cesnet.cloud.occi.renderer.TextRenderer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AttributeMapCover {

    private final Map<Attribute, String> attributes = new HashMap<>();

    public void add(Attribute attribute, String value) {
        if (attribute == null) {
            throw new NullPointerException("Cannot add null attribute.");
        }
        if (value == null) {
            throw new NullPointerException("Cannot add null value.");
        }

        attributes.put(attribute, value);
    }

    public void remove(Attribute attribute) {
        if (attribute == null) {
            throw new NullPointerException("Cannot remove null attribute.");
        }

        attributes.remove(attribute);
    }

    public void remove(String attributeName) {
        Attribute attTmp = new Attribute(attributeName);
        attributes.remove(attTmp);
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

    public Map<Attribute, String> getAttributes() {
        return Collections.unmodifiableMap(attributes);
    }

    public void clear() {
        attributes.clear();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.attributes);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AttributeMapCover other = (AttributeMapCover) obj;
        if (!Objects.equals(this.attributes, other.attributes)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AttributeMapCover{" + attributes + '}';
    }

    private List<String> toList() {
        List<String> list = new ArrayList<>();
        for (Attribute attribute : attributes.keySet()) {
            StringBuilder sb = new StringBuilder(attribute.getName());
            String value = attributes.get(attribute);
            if (value.matches(TextParser.REGEXP_NUMBER)) {
                sb.append(TextRenderer.surroundString(value, "=", ""));
            } else {
                sb.append(TextRenderer.surroundString(value, "=\"", "\""));
            }
            list.add(sb.toString());
        }

        return list;
    }

    public String toPrefixText() {
        String prefix = "X-OCCI-Attribute: ";
        StringBuilder sb = new StringBuilder();

        for (String s : toList()) {
            sb.append(prefix);
            sb.append(s);
            sb.append("\n");
        }

        if (!sb.toString().isEmpty()) {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }

    public String toOneLineText() {
        StringBuilder sb = new StringBuilder();

        for (String s : toList()) {
            sb.append(s);
            sb.append(";");
        }

        return sb.toString();
    }
}
