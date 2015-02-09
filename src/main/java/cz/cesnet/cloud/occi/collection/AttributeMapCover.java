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

/**
 * Class representing attributes and their values.
 *
 * @author Michal Kimle <kimle.michal@gmail.com>
 */
public class AttributeMapCover {

    private final Map<Attribute, String> attributes = new HashMap<>();

    /**
     * Stores attribute and its value.
     *
     * @param attribute cannot be null
     * @param value cannot be null
     */
    public void add(Attribute attribute, String value) {
        if (attribute == null) {
            throw new NullPointerException("Cannot add null attribute.");
        }
        if (value == null) {
            throw new NullPointerException("Cannot add null value.");
        }

        attributes.put(attribute, value);
    }

    /**
     *
     * @param attribute cannot be null
     */
    public void remove(Attribute attribute) {
        if (attribute == null) {
            throw new NullPointerException("Cannot remove null attribute.");
        }

        attributes.remove(attribute);
    }

    /**
     *
     * @param attributeName cannot be null
     */
    public void remove(String attributeName) {
        Attribute attTmp = new Attribute(attributeName);
        attributes.remove(attTmp);
    }

    /**
     *
     * @param attribute
     * @return
     */
    public boolean containsAttribute(Attribute attribute) {
        return attributes.containsKey(attribute);
    }

    /**
     *
     * @param attributeName cannot be null
     * @return
     */
    public boolean containsAttribute(String attributeName) {
        Attribute attTmp = new Attribute(attributeName);
        return containsAttribute(attTmp);
    }

    /**
     *
     * @param attribute
     * @return
     */
    public String getValue(Attribute attribute) {
        return attributes.get(attribute);
    }

    /**
     *
     * @param attributeName cannot be null
     * @return
     */
    public String getValue(String attributeName) {
        Attribute attTmp = new Attribute(attributeName);
        return getValue(attTmp);
    }

    /**
     *
     * @return
     */
    public Map<Attribute, String> getAttributes() {
        return Collections.unmodifiableMap(attributes);
    }

    /**
     *
     */
    public void clear() {
        attributes.clear();
    }

    /**
     *
     * @return
     */
    public int size() {
        return attributes.size();
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.attributes);
        return hash;
    }

    /**
     *
     * @param obj
     * @return
     */
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

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "AttributeMapCover{" + attributes + '}';
    }

    /**
     * Generates a list of strings from attributes and their values
     *
     * @return attributes and their values in form of list of strings
     */
    private List<String> toList() {
        List<String> list = new ArrayList<>();
        List<Attribute> attributeList = new ArrayList<>(attributes.keySet());
        Collections.sort(attributeList);
        for (Attribute attribute : attributeList) {
            String name = attribute.getName();
            StringBuilder sb = new StringBuilder(name);
            String value = attributes.get(attribute);
            if (value.matches(TextParser.REGEXP_NUMBER)) {
                sb.append(TextRenderer.surroundString(value, "=", ""));
            } else {
                sb.append(TextRenderer.surroundString(value, "=\"", "\""));
            }
            list.add(sb.toString());
        }

        Collections.sort(list);
        return list;
    }

    /**
     * Returns text representation of OCCI attributes with prefix.
     *
     * @return text representation of OCCI attributes with prefix
     */
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

    /**
     * Returns text representation of OCCI attributes in one line.
     *
     * @return text representation of OCCI attributes in one line
     */
    public String toOneLineText() {
        StringBuilder sb = new StringBuilder();

        for (String s : toList()) {

            sb.append(s);
            sb.append(";");
        }

        return sb.toString();
    }
}
