package cz.cesnet.cloud.occi.core;

import cz.cesnet.cloud.occi.renderer.TextRenderer;
import cz.cesnet.cloud.occi.type.Identifiable;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class representing an OCCI attribute. Attributes are used to store properties
 * of OCCI classes.
 *
 * @author Michal Kimle <kimle.michal@gmail.com>
 */
public class Attribute implements Identifiable, Comparable<Attribute> {

    private static final Logger LOGGER = LoggerFactory.getLogger(Attribute.class);
    private String name;
    private boolean required;
    private boolean immutable;
    private String type;
    private String pattern;
    private String defaultValue;
    private String description;

    /**
     * Constructor.
     *
     * @param name name of the attribute. Cannot be null nor empty.
     * @param required whether attribute is required or not
     * @param immutable whether attribute is immutable or not
     * @param type attribute's type
     * @param pattern attribute's pattern
     * @param defaultValue attribute's default value
     * @param description attribute's description
     */
    public Attribute(String name, boolean required, boolean immutable, String type, String pattern, String defaultValue, String description) {
        LOGGER.debug("Creating attribute: name={}, required={}, immutable={}, type={}, pattern={}, defaultValue={}, description={}",
                name, required, immutable, type, pattern, defaultValue, description);

        if (name == null) {
            throw new NullPointerException("Attribute name cannot be null.");
        }
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Attribute name cannot be empty.");
        }

        this.name = name;
        this.required = required;
        this.immutable = immutable;
        this.type = type;
        this.pattern = pattern == null ? ".*" : pattern;
        this.defaultValue = defaultValue;
        this.description = description;
    }

    /**
     * Constructor.
     *
     * @param name name of the attribute. Cannot be null nor empty.
     * @param required whether attribute is required or not
     * @param immutable whether attribute is immutable or not
     */
    public Attribute(String name, boolean required, boolean immutable) {
        this(name, required, immutable, null, null, null, null);
    }

    /**
     * Constructor.
     *
     * @param name name of the attribute. Cannot be null nor empty.
     */
    public Attribute(String name) {
        this(name, false, false, null, null, null, null);
    }

    /**
     * Returns attribute's name.
     *
     * @return attribute's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets attribute's name.
     *
     * @param name attribute's name. Cannot be null nor empty
     */
    public void setName(String name) {
        if (name == null) {
            throw new NullPointerException("Attribute name cannot be null.");
        }
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Attribute name cannot be empty.");
        }

        this.name = name;
    }

    /**
     * Returns attribute's identifier.
     *
     * @return attribute's identifier
     */
    @Override
    public String getIdentifier() {
        return getName();
    }

    /**
     * Returns whether atttribute is required.
     *
     * @return true if attribute is required, false otherwise
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * Sets attribute's requiredness.
     *
     * @param required whether the attribute should be required or not
     */
    public void setRequired(boolean required) {
        this.required = required;
    }

    /**
     * Returns whether atttribute is immutable.
     *
     * @return true if attribute is immutable, false otherwise
     */
    public boolean isImmutable() {
        return immutable;
    }

    /**
     * Sets attribute's immutability.
     *
     * @param immutable whether the attribute should be immutable or not
     */
    public void setImmutable(boolean immutable) {
        this.immutable = immutable;
    }

    /**
     * Returns attribute's type.
     *
     * @return attribute's type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets attribute's type.
     *
     * @param type attribute's type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Returns attribute's pattern.
     *
     * @return attribute's pattern
     */
    public String getPattern() {
        return pattern;
    }

    /**
     * Sets attribute's pattern.
     *
     * @param pattern attribute's pattern
     */
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    /**
     * Returns attribute's default value.
     *
     * @return attribute's default value
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * Sets attribute's default value.
     *
     * @param defaultValue attribute's default value
     */
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * Returns attribute's description.
     *
     * @return attribute's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets attribute's description.
     *
     * @param description attribute's description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @see Object#hashCode()
     * @return attribute's hash code
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.name);
        return hash;
    }

    /**
     * @see Object#equals(java.lang.Object)
     * @param obj object to compare attribute with
     * @return true if two attributes are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Attribute other = (Attribute) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    /**
     * Resturns string representation of the attribute
     *
     * @see Object#toString()
     * @return string representation of the attribute
     */
    @Override
    public String toString() {
        return "Attribute{" + "name=" + name + ", required=" + required + ", immutable=" + immutable + ", type=" + type + ", pattern=" + pattern + ", defaultValue=" + defaultValue + ", description=" + description + '}';
    }

    /**
     * Returns plain text representation of the attribute according to OCCI
     * standard.
     *
     * @return plain text representation of the attribute
     */
    public String toText() {
        StringBuilder sb = new StringBuilder(name);

        StringBuilder properties = null;
        if (required) {
            properties = new StringBuilder("required");
        }

        if (immutable) {
            if (properties == null) {
                properties = new StringBuilder("immutable");
            } else {
                properties.append(" immutable");
            }
        }

        if (properties != null) {
            sb.append(TextRenderer.surroundString(properties.toString(), "{", "}"));
        }

        return sb.toString();
    }

    /**
     * Comapres two attributes lexicographically based on their identifier.
     *
     * @see Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(Attribute a) {
        return getIdentifier().compareTo(a.getIdentifier());
    }
}
