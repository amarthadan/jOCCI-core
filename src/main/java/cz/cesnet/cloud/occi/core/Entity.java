package cz.cesnet.cloud.occi.core;

import cz.cesnet.cloud.occi.Model;
import cz.cesnet.cloud.occi.collection.AttributeMapCover;
import cz.cesnet.cloud.occi.type.Identifiable;
import cz.cesnet.cloud.occi.collection.SetCover;
import cz.cesnet.cloud.occi.exception.InvalidAttributeValueException;
import cz.cesnet.cloud.occi.exception.RenderingException;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Entity implements Identifiable, Comparable<Entity> {

    public static final String ID_ATTRIBUTE_NAME = "occi.core.id";
    public static final String TITLE_ATTRIBUTE_NAME = "occi.core.title";

    private static final Logger LOGGER = LoggerFactory.getLogger(Entity.class);
    private Kind kind;
    private Model model;
    private final SetCover<Mixin> mixins = new SetCover<>();
    private final AttributeMapCover attributes = new AttributeMapCover();

    public Entity(String id, Kind kind, String title, Model model) throws InvalidAttributeValueException {
        LOGGER.debug("Creating Entity: class={}, id={}, kind={}, title={}, model={}", getClass().getName(), id, kind, title, model);

        if (id == null) {
            throw new NullPointerException("Entity id cannot be null.");
        }
        if (kind == null) {
            throw new NullPointerException("Entity kind cannot be null.");
        }

        privateAddAttribute(ID_ATTRIBUTE_NAME, id);
        if (title != null && !title.isEmpty()) {
            privateAddAttribute(TITLE_ATTRIBUTE_NAME, title);
        }
        this.kind = kind;
        this.model = model;
    }

    public Entity(String id, Kind kind) throws InvalidAttributeValueException {
        this(id, kind, null, null);
    }

    public String getId() {
        return getValue(ID_ATTRIBUTE_NAME);
    }

    public void setId(String id) throws InvalidAttributeValueException {
        if (id == null) {
            throw new NullPointerException("Entity id cannot be null.");
        }

        addAttribute(ID_ATTRIBUTE_NAME, id);
    }

    public Kind getKind() {
        return kind;
    }

    public void setKind(Kind kind) {
        if (kind == null) {
            throw new NullPointerException("Entity kind cannot be null.");
        }

        this.kind = kind;
    }

    @Override
    public String getIdentifier() {
        return kind.getIdentifier() + "|" + getId();
    }

    public String getTitle() {
        return getValue(TITLE_ATTRIBUTE_NAME);
    }

    public void setTitle(String title) throws InvalidAttributeValueException {
        addAttribute(TITLE_ATTRIBUTE_NAME, title);
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    private void privateAddAttribute(String attributeIdentifier, String value) throws InvalidAttributeValueException {
        if (!isValidAttributeValue(attributeIdentifier, value)) {
            Attribute attribute = getAttribute(attributeIdentifier);
            throw new InvalidAttributeValueException("'" + value + "' is not a suitable value for " + attribute);
        }

        Attribute attribute = getAttribute(attributeIdentifier);
        if (attribute == null) {
            attribute = new Attribute(attributeIdentifier);
        }
        attributes.add(attribute, value);
    }

    public void addAttribute(String attributeIdentifier, String value) throws InvalidAttributeValueException {
        privateAddAttribute(attributeIdentifier, value);
    }

    private boolean isValidAttributeValue(String attributeIdentifier, String value) {
        Attribute attribute = getAttribute(attributeIdentifier);

        if (attribute == null) {
            return true;
        }

        if (attribute.getPattern() == null || attribute.getPattern().isEmpty()) {
            return true;
        }

        return value.matches(attribute.getPattern());
    }

    private Attribute getAttribute(String attributeIdentifier) {
        Attribute attribute = getAttributeFromKindsIfExists(kind, attributeIdentifier);
        if (attribute == null) {
            attribute = getAttributeFromMixinsIfExists(getMixins(), attributeIdentifier);
        }

        return attribute;
    }

    private Attribute getAttributeFromKindsIfExists(Kind kind, String attributeIdentifier) {
        if (kind == null) {
            return null;
        }

        if (kind.containsAttribute(attributeIdentifier)) {
            return kind.getAttribute(attributeIdentifier);
        }

        Attribute attribute = null;
        for (Kind k : kind.getRelations()) {
            if (attribute != null) {
                return attribute;
            }

            attribute = getAttributeFromKindsIfExists(k, attributeIdentifier);
        }

        return attribute;
    }

    private Attribute getAttributeFromMixinsIfExists(Set<Mixin> mixins, String attributeIdentifier) {
        for (Mixin m : mixins) {
            if (m.containsAttribute(attributeIdentifier)) {
                return m.getAttribute(attributeIdentifier);
            }
            Attribute attribute = getAttributeFromMixinsIfExists(m.getRelations(), attributeIdentifier);
            if (attribute != null) {
                return attribute;
            }
        }

        return null;
    }

    public void removeAttribute(String attributeIdentifier) {
        attributes.remove(attributeIdentifier);
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

    protected String attributesToOneLineText(List<String> exclude) {
        return attributes.toOneLineText(exclude);
    }

    protected String attributesToPrefixText() {
        return attributes.toPrefixText();
    }

    public boolean containsMixin(Mixin mixin) {
        return mixins.contains(mixin);
    }

    public boolean containsMixin(String mixinIdentifier) {
        return mixins.contains(mixinIdentifier);
    }

    public boolean addMixin(Mixin mixin) {
        return mixins.add(mixin);
    }

    public Mixin getMixin(String mixinIdentifier) {
        return mixins.get(mixinIdentifier);
    }

    public boolean removeMixin(Mixin mixin) {
        return mixins.remove(mixin);
    }

    public void clearMixins() {
        mixins.clear();
    }

    public Set<Mixin> getMixins() {
        return mixins.getSet();
    }

    public static URI getSchemeDefault() {
        return Category.SCHEME_CORE_DEFAULT;
    }

    public static String getTermDefault() {
        return "entity";
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(getId());
        hash = 89 * hash + Objects.hashCode(this.kind);
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
        final Entity other = (Entity) obj;
        if (!Objects.equals(getId(), other.getId())) {
            return false;
        }
        if (!Objects.equals(this.kind, other.kind)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity{" + "class=" + getClass().getName() + ", id=" + getId() + ", kind=" + kind + ", title=" + getTitle() + ", mixins=" + mixins + ", attributes=" + attributes + '}';
    }

    public abstract String toText() throws RenderingException;

    public abstract String toJSON();

    @Override
    public int compareTo(Entity e) {
        return getIdentifier().compareTo(e.getIdentifier());
    }
}
