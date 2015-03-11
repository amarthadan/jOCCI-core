package cz.cesnet.cloud.occi.core;

import com.sun.net.httpserver.Headers;
import cz.cesnet.cloud.occi.Model;
import cz.cesnet.cloud.occi.collection.AttributeMapCover;
import cz.cesnet.cloud.occi.renderer.TextRenderer;
import cz.cesnet.cloud.occi.type.Identifiable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class representing an instance of Action
 *
 * @author Michal Kimle <kimle.michal@gmail.com>
 */
public class ActionInstance implements Identifiable, Comparable<ActionInstance> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActionInstance.class);
    private final AttributeMapCover attributes = new AttributeMapCover();
    private Action action;
    private Model model;

    /**
     * Constructor. Creates an instance of ActionInstance with given Action.
     *
     * @param action action to create instance of. Cannot be null.
     */
    public ActionInstance(Action action) {
        LOGGER.debug("Creating ActionInstance: action={}", action);

        if (action == null) {
            throw new NullPointerException("ActionInstance action cannot be null.");
        }

        this.action = action;
    }

    /**
     * Returns action of this instance.
     *
     * @return action of this instance
     */
    public Action getAction() {
        return action;
    }

    /**
     * Sets action for this instance.
     *
     * @param action action for this instance. Cannot be null.
     */
    public void setAction(Action action) {
        if (action == null) {
            throw new NullPointerException("ActionInstance action cannot be null.");
        }

        this.action = action;
    }

    /**
     * Returns action's identifier.
     *
     * @return action's identifier
     */
    @Override
    public String getIdentifier() {
        return action.getIdentifier();
    }

    /**
     * Returns instance's model.
     *
     * @return instance's model
     */
    public Model getModel() {
        return model;
    }

    /**
     * Sets model for the instance.
     *
     * @param model model for the instance
     */
    public void setModel(Model model) {
        this.model = model;
    }

    /**
     * Adds attribute and its value for the instance.
     *
     * @param attribute attribute to be added
     * @param value value of the attribute to be added
     */
    public void addAttribute(Attribute attribute, String value) {
        attributes.add(attribute, value);
    }

    /**
     * Adds all attributes and their values from the given map.
     *
     * @param attributes map of attributes and their values
     */
    public void addAttributes(Map<String, String> attributes) {
        for (String name : attributes.keySet()) {
            addAttribute(new Attribute(name), attributes.get(name));
        }
    }

    /**
     * Removes attribute and its value from the instance.
     *
     * @param attribute attribute to be removed
     */
    public void removeAttribute(Attribute attribute) {
        attributes.remove(attribute);
    }

    /**
     * Checks whether the instance has an attribute.
     *
     * @param attribute attribute to be looked up
     * @return true if instance has the attribute, false otherwise
     */
    public boolean containsAttribute(Attribute attribute) {
        return attributes.containsAttribute(attribute);
    }

    /**
     * Checks whether the instance has an attribute.
     *
     * @param attributeName name of the attribute to be looked up
     * @return true if instance has the attribute, false otherwise
     */
    public boolean containsAttribute(String attributeName) {
        return attributes.containsAttribute(attributeName);
    }

    /**
     * Returns value of the given attribute.
     *
     * @param attribute attribute which value is returned
     * @return value of the given attribute
     */
    public String getValue(Attribute attribute) {
        return attributes.getValue(attribute);
    }

    /**
     * Returns value of the given attribute.
     *
     * @param attributeName name of the attribute which value is returned
     * @return value of the given attribute
     */
    public String getValue(String attributeName) {
        return attributes.getValue(attributeName);
    }

    /**
     * Returns all instance's attributes and their values in form of map.
     *
     * @return all instance's attributes and their values in form of map
     */
    public Map<Attribute, String> getAttributes() {
        return attributes.getAttributes();
    }

    /**
     * Removes all attributes with their values from the instance.
     */
    public void clearAttributes() {
        attributes.clear();
    }

    /**
     * Returns a plain text representation of action instance as described in
     * OCCI standard.
     *
     * @return text representation of action instance
     */
    public String toText() {
        StringBuilder sb = new StringBuilder("Category: ");
        sb.append(textBody());

        String attributesString = attributes.toPrefixText();
        if (!attributesString.isEmpty()) {
            sb.append("\n");
            sb.append(attributesString);
        }

        return sb.toString();
    }

    /**
     * Returns an occi text representation of action instance as described in
     * OCCI standard in form of headers.
     *
     * @return occi text representation of action instance in form of headers
     */
    public Headers toHeaders() {
        Headers headers = new Headers();
        headers.add("Category", textBody());

        Headers attributesHeaders = attributes.toHeaders();
        if (!attributesHeaders.isEmpty()) {
            headers.putAll(attributesHeaders);
        }

        return headers;
    }

    private String textBody() {
        StringBuilder sb = new StringBuilder(action.getTerm());
        sb.append(";");
        sb.append("scheme");
        sb.append(TextRenderer.surroundString(action.getScheme().toString()));
        sb.append("class");
        sb.append(TextRenderer.surroundString(action.getClass().getSimpleName().toLowerCase()));

        String title = action.getTitle();
        if (title != null && !title.isEmpty()) {
            sb.append("title");
            sb.append(TextRenderer.surroundString(title));
        }

        Set<Attribute> actionAttributes = action.getAttributes();
        if (actionAttributes != null && !actionAttributes.isEmpty()) {
            sb.append("attributes");
            StringBuilder attrSB = new StringBuilder();
            List<Attribute> attributeList = new ArrayList<>(actionAttributes);
            Collections.sort(attributeList);
            for (Attribute attribute : attributeList) {
                attrSB.append(attribute.toText());
                attrSB.append(" ");
            }
            attrSB.deleteCharAt(attrSB.length() - 1);
            sb.append(TextRenderer.surroundString(attrSB.toString()));
        }

        return sb.toString();
    }

    /**
     * @see Object#hashCode()
     * @return instance's hash code
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.attributes);
        hash = 41 * hash + Objects.hashCode(this.action);
        return hash;
    }

    /**
     * @see Object#equals(java.lang.Object)
     * @param obj object to compare instance with
     * @return true if two instances are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ActionInstance other = (ActionInstance) obj;
        if (!Objects.equals(this.attributes, other.attributes)) {
            return false;
        }
        if (!Objects.equals(this.action, other.action)) {
            return false;
        }
        return true;
    }

    /**
     * Resturns string representation of the instance
     *
     * @see Object#toString()
     * @return string representation of the instance
     */
    @Override
    public String toString() {
        return "ActionInstance{" + "attributes=" + attributes + ", action=" + action + '}';
    }

    /**
     * Comapres two instances lexicographically based on their actions.
     *
     * @see Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(ActionInstance a) {
        return getIdentifier().compareTo(a.getIdentifier());
    }
}
