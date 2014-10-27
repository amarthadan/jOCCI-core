package cz.cesnet.cloud.occi.core;

import cz.cesnet.cloud.occi.Model;
import cz.cesnet.cloud.occi.collection.AttributeMapCover;
import cz.cesnet.cloud.occi.type.Identifiable;
import cz.cesnet.cloud.occi.collection.SetCover;
import cz.cesnet.cloud.occi.exception.InvalidAttributeException;
import cz.cesnet.cloud.occi.exception.NonexistingElementException;
import java.net.URI;
import java.util.Objects;
import java.util.Set;

public abstract class Entity implements Identifiable {

    private URI id;
    private Kind kind;
    private String title;
    private Model model;
    private final SetCover<Mixin> mixins = new SetCover<>();
    private final AttributeMapCover attributes = new AttributeMapCover();

    public Entity(URI id, Kind kind, String title, Model model) {
        if (id == null) {
            throw new NullPointerException("Entity id cannot be null.");
        }
        if (kind == null) {
            throw new NullPointerException("Entity kind cannot be null.");
        }

        this.id = id;
        this.kind = kind;
        this.title = title;
        this.model = model;
    }

    public Entity(URI id, Kind kind) {
        this(id, kind, null, null);
    }

    public URI getId() {
        return id;
    }

    public void setId(URI id) {
        if (id == null) {
            throw new NullPointerException("Entity id cannot be null.");
        }

        this.id = id;
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
        return kind.getIdentifier() + id.toString();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public void addAttribute(Attribute attribute, String value) throws InvalidAttributeException {
        if (!isValidAttribute(attribute)) {
            throw new InvalidAttributeException(this + " cannot have " + attribute);
        }
        //TODO add value checking

        attributes.add(attribute, value);
    }

    private boolean isValidAttribute(Attribute attribute) {
        return kind.getAttributes().contains(attribute);
    }

    public void removeAttribute(Attribute attribute) throws NonexistingElementException {
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

    public void clearAttributes() {
        attributes.clear();
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

    public Mixin getMixin(String mixinIdentifier) throws NonexistingElementException {
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.id);
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
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.kind, other.kind)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity{" + "class=" + getClass().getName() + ", id=" + id + ", kind=" + kind + ", title=" + title + ", mixins=" + mixins + ", attributes=" + attributes + '}';
    }

    public abstract void toText();

    public abstract void toJSON();
}
