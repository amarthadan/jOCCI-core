package cz.cesnet.cloud.occi.core;

import com.sun.net.httpserver.Headers;
import cz.cesnet.cloud.occi.Model;
import cz.cesnet.cloud.occi.collection.AttributeMapCover;
import cz.cesnet.cloud.occi.type.Identifiable;
import cz.cesnet.cloud.occi.collection.SetCover;
import cz.cesnet.cloud.occi.exception.InvalidAttributeValueException;
import cz.cesnet.cloud.occi.exception.RenderingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract class representing an OCCI Entity.
 *
 * @author Michal Kimle <kimle.michal@gmail.com>
 */
public abstract class Entity implements Identifiable, Comparable<Entity> {

    public static final String ID_ATTRIBUTE_NAME = "occi.core.id";
    public static final String TITLE_ATTRIBUTE_NAME = "occi.core.title";
    public static final URI SCHEME_DEFAULT = Category.SCHEME_CORE_DEFAULT;
    public static final String TERM_DEFAULT = "entity";
    public static final String KIND_IDENTIFIER_DEFAULT = SCHEME_DEFAULT + TERM_DEFAULT;

    private static final Logger LOGGER = LoggerFactory.getLogger(Entity.class);
    private Kind kind;
    private Model model;
    private final SetCover<Mixin> mixins = new SetCover<>();
    private final AttributeMapCover attributes = new AttributeMapCover();

    /**
     * Constructor.
     *
     * @param id occi.core.id attribute. Cannot be null.
     * @param kind entity's kind. Cannot be null.
     * @param title occi.core.title attribute
     * @param model entity's model
     * @throws InvalidAttributeValueException in case of invalid id or title
     * value
     */
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

    /**
     * Constructor.
     *
     * @param id occi.core.id attribute. Cannot be null.
     * @param kind entity's kind. Cannot be null.
     * @throws InvalidAttributeValueException in case of invalid id or title
     * value
     */
    public Entity(String id, Kind kind) throws InvalidAttributeValueException {
        this(id, kind, null, null);
    }

    /**
     * Returns entity's id.
     *
     * @return entity's id
     */
    public String getId() {
        return getValue(ID_ATTRIBUTE_NAME);
    }

    /**
     * Sets entity's id.
     *
     * @param id entity's id. Cannot be null.
     * @throws InvalidAttributeValueException in case of invalid id value
     */
    public void setId(String id) throws InvalidAttributeValueException {
        if (id == null) {
            throw new NullPointerException("Entity id cannot be null.");
        }

        addAttribute(ID_ATTRIBUTE_NAME, id);
    }

    /**
     * Returns entity's kind.
     *
     * @return entity's kind
     */
    public Kind getKind() {
        return kind;
    }

    /**
     * Sets entity's kind.
     *
     * @param kind entity's kind. Cannot be null.
     */
    public void setKind(Kind kind) {
        if (kind == null) {
            throw new NullPointerException("Entity kind cannot be null.");
        }

        this.kind = kind;
    }

    /**
     * Returns entity's identifier.
     *
     * @return entity's identifier
     */
    @Override
    public String getIdentifier() {
        return kind.getIdentifier() + "|" + getId();
    }

    /**
     * Returns entity's title.
     *
     * @return entity's title
     */
    public String getTitle() {
        return getValue(TITLE_ATTRIBUTE_NAME);
    }

    /**
     * Sets entity's title.
     *
     * @param title entity's title
     * @throws InvalidAttributeValueException in case of invalit tile value
     */
    public void setTitle(String title) throws InvalidAttributeValueException {
        addAttribute(TITLE_ATTRIBUTE_NAME, title);
    }

    /**
     * Returns entity's model.
     *
     * @return entity's model
     */
    public Model getModel() {
        return model;
    }

    /**
     * Sets entity's model.
     *
     * @param model entity's model
     */
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

    /**
     * Adds attribute and its value. If attribute has a content restriction,
     * value is checked.
     *
     * @param attributeIdentifier
     * @param value
     * @throws InvalidAttributeValueException
     */
    public void addAttribute(String attributeIdentifier, String value) throws InvalidAttributeValueException {
        privateAddAttribute(attributeIdentifier, value);
    }

    /**
     * Adds attributes and their values. If attributes have a content
     * restriction, values are checked.
     *
     * @param attributes
     * @throws InvalidAttributeValueException
     */
    public void addAttributes(Map<String, String> attributes) throws InvalidAttributeValueException {
        for (String name : attributes.keySet()) {
            privateAddAttribute(name, attributes.get(name));
        }
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

    /**
     * Removes attribute from entity.
     *
     * @param attributeIdentifier identifier of the attribute to be removed
     */
    public void removeAttribute(String attributeIdentifier) {
        attributes.remove(attributeIdentifier);
    }

    /**
     * Checks whether entity has given attribute.
     *
     * @param attribute attribute to be looked up
     * @return true if entity has given attribute, false otherwise
     */
    public boolean containsAttribute(Attribute attribute) {
        return attributes.containsAttribute(attribute);
    }

    /**
     * Checks whether entity has given attribute.
     *
     * @param attributeName name of the attribute to be looked up
     * @return true if entity has given attribute, false otherwise
     */
    public boolean containsAttribute(String attributeName) {
        return attributes.containsAttribute(attributeName);
    }

    /**
     * Returns value of given attribute.
     *
     * @param attribute attribute which value will be returned
     * @return value of given attribute
     */
    public String getValue(Attribute attribute) {
        return attributes.getValue(attribute);
    }

    /**
     * Returns value of given attribute.
     *
     * @param attributeName name of the attribute which value will be returned
     * @return value of given attribute
     */
    public String getValue(String attributeName) {
        return attributes.getValue(attributeName);
    }

    /**
     * Returns all entity's attributes and their values in form of map.
     *
     * @return all entity's attributes and their values in form of map
     */
    public Map<Attribute, String> getAttributes() {
        return attributes.getAttributes();
    }

    /**
     * Removes all entity's attributes.
     */
    public void clearAttributes() {
        attributes.clear();
    }

    /**
     * Returns text representation of entity's attributes in one line.
     *
     * @return text representation of entity's attributes in one line
     */
    protected String attributesToOneLineText() {
        return attributes.toOneLineText();
    }

    /**
     * Returns a text representation of entity's attributes with prefix.
     *
     * @return text representation of entity's attributes with prefix
     */
    protected String attributesToPrefixText() {
        return attributes.toPrefixText();
    }

    /**
     * Returns an occi text representation of entity's attributes in form of
     * headers.
     *
     * @return occi text representation of entity's attributes in form of
     * headers
     */
    protected Headers attributesToHeaders() {
        return attributes.toHeaders();
    }

    /**
     * Checks whether the entity has given mixin.
     *
     * @param mixin mixin to be looked up
     * @return true if entity has given mixin, false otherwise
     */
    public boolean containsMixin(Mixin mixin) {
        return mixins.contains(mixin);
    }

    /**
     * Checks whether the entity has given mixin.
     *
     * @param mixinIdentifier identifier of mixin to be looked up
     * @return true if entity has given mixin, false otherwise
     */
    public boolean containsMixin(String mixinIdentifier) {
        return mixins.contains(mixinIdentifier);
    }

    /**
     * Adds mixin to the entity.
     *
     * @param mixin mixin to be added
     * @return true if the addition was successful, false otherwise
     */
    public boolean addMixin(Mixin mixin) {
        return mixins.add(mixin);
    }

    /**
     * Adds all the mixins from given collection to the entity.
     *
     * @param mixins collection of mixins
     * @return true if the addition was successful, false otherwise
     */
    public boolean addMixins(Collection<Mixin> mixins) {
        return this.mixins.addAll(mixins);
    }

    /**
     * Returns mixin form entity.
     *
     * @param mixinIdentifier identifier of requested mixin
     * @return mixin form entity
     */
    public Mixin getMixin(String mixinIdentifier) {
        return mixins.get(mixinIdentifier);
    }

    /**
     * Removes mixin from entity.
     *
     * @param mixin mixin to be removed
     * @return true if the removal was successful, false otherwise
     */
    public boolean removeMixin(Mixin mixin) {
        return mixins.remove(mixin);
    }

    /**
     * Removes all mixins from entity.
     */
    public void clearMixins() {
        mixins.clear();
    }

    /**
     * Returns all mixins from entity in form of a set.
     *
     * @return all mixins from entity in form of a set
     */
    public Set<Mixin> getMixins() {
        return mixins.getSet();
    }

    /**
     * @see Object#hashCode()
     * @return entity's hash code
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(getId());
        hash = 89 * hash + Objects.hashCode(this.kind);
        return hash;
    }

    /**
     * @see Object#equals(java.lang.Object)
     * @param obj object to compare entity with
     * @return true if two entity are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Entity)) {
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

    /**
     * Resturns string representation of the entity
     *
     * @see Object#toString()
     * @return string representation of the entity
     */
    @Override
    public String toString() {
        return "Entity{" + "class=" + getClass().getName() + ", id=" + getId() + ", kind=" + kind + ", title=" + getTitle() + ", mixins=" + mixins + ", attributes=" + attributes + '}';
    }

    /**
     * Renders entity to its plain text form as described in OCCI standard.
     *
     * @return plain text form of entity
     * @throws RenderingException
     */
    public abstract String toText() throws RenderingException;

    /**
     * Renders entity to its occi text form as described in OCCI standard in
     * form of headers.
     *
     * @return occi text form of entity
     * @throws RenderingException
     */
    public abstract Headers toHeaders() throws RenderingException;

    /**
     * Renders entity to its JSON form as described in OCCI standard.
     *
     * @return JSON form of entity
     */
    public abstract String toJSON();

    /**
     * Comapres two entities lexicographically based on their identifier.
     *
     * @see Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(Entity e) {
        return getIdentifier().compareTo(e.getIdentifier());
    }

    /**
     * Returns entity's default kind identifier (scheme+term). For Entity class
     * this equals to 'http://schemas.ogf.org/occi/core#entity'.
     *
     * @return entity's default kind identifier
     */
    public String getDefaultKindIdentifier() {
        return KIND_IDENTIFIER_DEFAULT;
    }

    /**
     * Returns entity's default attributes. For Entity class those are
     * attributes occi.core.id and occi.core.title.
     *
     * @return list of entity's default attributes
     */
    public static List<Attribute> getDefaultAttributes() {
        List<Attribute> list = new ArrayList<>();
        list.add(new Attribute(ID_ATTRIBUTE_NAME, true, true));
        list.add(new Attribute(TITLE_ATTRIBUTE_NAME, false, false));

        return list;
    }
}
