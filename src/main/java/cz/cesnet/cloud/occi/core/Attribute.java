package cz.cesnet.cloud.occi.core;

import cz.cesnet.cloud.occi.type.Identifiable;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Attribute implements Identifiable {

    private static final Logger LOGGER = LoggerFactory.getLogger(Attribute.class);

    private String name;
    private boolean required;
    private boolean immutable;
    private String type;
    private String pattern;
    private String defaultValue;
    private String description;

    public Attribute(String name, boolean required, boolean immutable, String type, String pattern, String defaultValue, String description) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Attribute name cannot be null nor empty.");
        }
        LOGGER.debug("Creating attribute: name={}, required={}, immutable={}, type={}, pattern={}, defaultValue={}, description={}",
                name, required, immutable, type, pattern, defaultValue, description);
        this.name = name;
        this.required = required;
        this.immutable = immutable;
        this.type = type;
        this.pattern = pattern;
        this.defaultValue = defaultValue;
        this.description = description;
    }

    public Attribute(String name, boolean required, boolean immutable) {
        this(name, required, immutable, null, null, null, null);
    }

    public Attribute(String name) {
        this(name, false, false, null, null, null, null);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Attribute name cannot be null nor empty.");
        }

        this.name = name;
    }

    @Override
    public String getIdentifier() {
        return getName();
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean isImmutable() {
        return immutable;
    }

    public void setImmutable(boolean immutable) {
        this.immutable = immutable;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.name);
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
        final Attribute other = (Attribute) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Attribute{" + "name=" + name + ", required=" + required + ", immutable=" + immutable + ", type=" + type + ", pattern=" + pattern + ", defaultValue=" + defaultValue + ", description=" + description + '}';
    }
}
