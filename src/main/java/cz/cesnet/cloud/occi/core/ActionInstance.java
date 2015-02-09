package cz.cesnet.cloud.occi.core;

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
     * Constructor
     *
     * @param action cannot be null
     */
    public ActionInstance(Action action) {
        LOGGER.debug("Creating ActionInstance: action={}", action);

        if (action == null) {
            throw new NullPointerException("ActionInstance action cannot be null.");
        }

        this.action = action;
    }

    /**
     *
     * @return
     */
    public Action getAction() {
        return action;
    }

    /**
     *
     * @param action
     */
    public void setAction(Action action) {
        if (action == null) {
            throw new NullPointerException("ActionInstance action cannot be null.");
        }

        this.action = action;
    }

    /**
     *
     * @return
     */
    @Override
    public String getIdentifier() {
        return action.getIdentifier();
    }

    /**
     *
     * @return
     */
    public Model getModel() {
        return model;
    }

    /**
     *
     * @param model
     */
    public void setModel(Model model) {
        this.model = model;
    }

    /**
     *
     * @param attribute
     * @param value
     */
    public void addAttribute(Attribute attribute, String value) {
        attributes.add(attribute, value);
    }

    /**
     *
     * @param attributes
     */
    public void addAttributes(Map<String, String> attributes) {
        for (String name : attributes.keySet()) {
            addAttribute(new Attribute(name), attributes.get(name));
        }
    }

    /**
     *
     * @param attribute
     */
    public void removeAttribute(Attribute attribute) {
        attributes.remove(attribute);
    }

    /**
     *
     * @param attribute
     * @return
     */
    public boolean containsAttribute(Attribute attribute) {
        return attributes.containsAttribute(attribute);
    }

    /**
     *
     * @param attributeName
     * @return
     */
    public boolean containsAttribute(String attributeName) {
        return attributes.containsAttribute(attributeName);
    }

    /**
     *
     * @param attribute
     * @return
     */
    public String getValue(Attribute attribute) {
        return attributes.getValue(attribute);
    }

    /**
     *
     * @param attributeName
     * @return
     */
    public String getValue(String attributeName) {
        return attributes.getValue(attributeName);
    }

    /**
     *
     * @return
     */
    public Map<Attribute, String> getAttributes() {
        return attributes.getAttributes();
    }

    /**
     *
     */
    public void clearAttributes() {
        attributes.clear();
    }

    /**
     * Returns a text representation of action instance as described in OCCI
     * standard.
     *
     * @return text representation of action instance
     */
    public String toText() {
        StringBuilder sb = new StringBuilder("Category: ");
        sb.append(action.getTerm());
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

        String attributesString = attributes.toPrefixText();
        if (!attributesString.isEmpty()) {
            sb.append("\n");
            sb.append(attributesString);
        }

        return sb.toString();
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.attributes);
        hash = 41 * hash + Objects.hashCode(this.action);
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
     *
     * @return
     */
    @Override
    public String toString() {
        return "ActionInstance{" + "attributes=" + attributes + ", action=" + action + '}';
    }

    /**
     *
     * @param a
     * @return
     */
    @Override
    public int compareTo(ActionInstance a) {
        return getIdentifier().compareTo(a.getIdentifier());
    }
}
