package cz.cesnet.cloud.occi.core;

import cz.cesnet.cloud.occi.collection.AttributeMapCover;
import cz.cesnet.cloud.occi.type.Identifiable;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActionInstance implements Identifiable, Comparable<ActionInstance> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActionInstance.class);
    private final AttributeMapCover attributes = new AttributeMapCover();
    private Action action;

    public ActionInstance(Action action) {
        LOGGER.debug("Creating ActionInstance: action={}", action);

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

    @Override
    public String getIdentifier() {
        return action.getIdentifier();
    }

    public void addAttribute(Attribute attribute, String value) {
        attributes.add(attribute, value);
    }

    public void removeAttribute(Attribute attribute) {
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

    public Map<Attribute, String> getAttributes() {
        return attributes.getAttributes();
    }

    public void clearAttributes() {
        attributes.clear();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.attributes);
        hash = 41 * hash + Objects.hashCode(this.action);
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
        final ActionInstance other = (ActionInstance) obj;
        if (!Objects.equals(this.attributes, other.attributes)) {
            return false;
        }
        if (!Objects.equals(this.action, other.action)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ActionInstance{" + "attributes=" + attributes + ", action=" + action + '}';
    }

    @Override
    public int compareTo(ActionInstance a) {
        return getIdentifier().compareTo(a.getIdentifier());
    }
}
