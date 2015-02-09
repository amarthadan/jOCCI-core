package cz.cesnet.cloud.occi.core;

import cz.cesnet.cloud.occi.renderer.TextRenderer;
import cz.cesnet.cloud.occi.type.Identifiable;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class representing an OCCI attribute.
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
     * Constructor
     *
     * @param name cannot be null nor empty
     * @param required
     * @param immutable
     * @param type
     * @param pattern
     * @param defaultValue
     * @param description
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
     *
     * @param name
     * @param required
     * @param immutable
     */
    public Attribute(String name, boolean required, boolean immutable) {
        this(name, required, immutable, null, null, null, null);
    }

    /**
     *
     * @param name
     */
    public Attribute(String name) {
        this(name, false, false, null, null, null, null);
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
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
     *
     * @return
     */
    @Override
    public String getIdentifier() {
        return getName();
    }

    /**
     *
     * @return
     */
    public boolean isRequired() {
        return required;
    }

    /**
     *
     * @param required
     */
    public void setRequired(boolean required) {
        this.required = required;
    }

    /**
     *
     * @return
     */
    public boolean isImmutable() {
        return immutable;
    }

    /**
     *
     * @param immutable
     */
    public void setImmutable(boolean immutable) {
        this.immutable = immutable;
    }

    /**
     *
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     */
    public String getPattern() {
        return pattern;
    }

    /**
     *
     * @param pattern
     */
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    /**
     *
     * @return
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     *
     * @param defaultValue
     */
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.name);
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
        final Attribute other = (Attribute) obj;
        if (!Objects.equals(this.name, other.name)) {
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
     *
     * @param a
     * @return
     */
    @Override
    public int compareTo(Attribute a) {
        return getIdentifier().compareTo(a.getIdentifier());
    }
}
