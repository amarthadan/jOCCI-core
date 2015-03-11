package cz.cesnet.cloud.occi.collection;

import com.sun.net.httpserver.Headers;
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
     * @param attribute attribute to be stored. Cannot be null.
     * @param value value to be stored for the attribute. Cannot be null.
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
     * Removes attribute from the collection.
     *
     * @param attribute attribute to be removed. Cannot be null.
     */
    public void remove(Attribute attribute) {
        if (attribute == null) {
            throw new NullPointerException("Cannot remove null attribute.");
        }

        attributes.remove(attribute);
    }

    /**
     * Removes attribute from the collection.
     *
     * @param attributeName name of the attribute to be removed. Cannot be null.
     */
    public void remove(String attributeName) {
        Attribute attTmp = new Attribute(attributeName);
        attributes.remove(attTmp);
    }

    /**
     * Checks whether collection contains the attribute.
     *
     * @param attribute attribute to be looked up in the collection
     * @return true if collection contains the attribute, false otherwise
     */
    public boolean containsAttribute(Attribute attribute) {
        return attributes.containsKey(attribute);
    }

    /**
     * Checks whether collection contains the attribute.
     *
     * @param attributeName name of the attribute to be looked up in the
     * collection. Cannot be null.
     * @return true if collection contains the attribute, false otherwise
     */
    public boolean containsAttribute(String attributeName) {
        Attribute attTmp = new Attribute(attributeName);
        return containsAttribute(attTmp);
    }

    /**
     * Returns the value for the given attribute.
     *
     * @param attribute attribute of which value is returned.
     * @return value for the given attribute
     */
    public String getValue(Attribute attribute) {
        return attributes.get(attribute);
    }

    /**
     * Returns the value for the given attribute.
     *
     * @param attributeName name of the attribute of which value is returned.
     * Cannot be null.
     * @return value for the given attribute
     */
    public String getValue(String attributeName) {
        Attribute attTmp = new Attribute(attributeName);
        return getValue(attTmp);
    }

    /**
     * Returns all the attributes and their values in form of map.
     *
     * @return all the attributes and their values in form of map
     */
    public Map<Attribute, String> getAttributes() {
        return Collections.unmodifiableMap(attributes);
    }

    /**
     * Removes all attributes and their values from the collection.
     */
    public void clear() {
        attributes.clear();
    }

    /**
     * Returns the number of attributes in the collection.
     *
     * @return the number of attributes in the collection
     */
    public int size() {
        return attributes.size();
    }

    /**
     * @see Object#hashCode()
     * @return collection's hash code
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.attributes);
        return hash;
    }

    /**
     * @see Object#equals(java.lang.Object)
     * @param obj object to compare collection with
     * @return true if two collections are equal, false otherwise
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
     * Resturns string representation of the collection
     *
     * @see Object#toString()
     * @return string representation of the collection
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
     * Returns a text representation of OCCI attributes with prefix.
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
     * Returns an occi text representation of OCCI attributes in form of
     * headers.
     *
     * @return occi text representation of OCCI attributes in form of headers
     */
    public Headers toHeaders() {
        Headers headers = new Headers();

        for (String s : toList()) {
            headers.add("X-OCCI-Attribute", s);
        }

        return headers;
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
