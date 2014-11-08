package cz.cesnet.cloud.occi.core;

import cz.cesnet.cloud.occi.collection.SetCover;
import cz.cesnet.cloud.occi.parser.TextParser;
import cz.cesnet.cloud.occi.type.Identifiable;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Category implements Identifiable {

    private static final Logger LOGGER = LoggerFactory.getLogger(Category.class);
    public static final String DEFAULT_SCHEME = "http://schemas.ogf.org/occi/core#";

    private String term;
    private String scheme;
    private String title;
    private String location;
    private final SetCover<Attribute> attributes = new SetCover<>();
    private final SetCover<Action> actions = new SetCover<>();
    private final SetCover<Entity> entities = new SetCover<>();

    public Category(String scheme, String term, String title, String location, Collection<Attribute> attributes) {
        if (scheme == null) {
            throw new NullPointerException("Category scheme cannot be null.");
        }
        if (scheme.isEmpty() || !scheme.matches(TextParser.REGEXP_URI)) {
            throw new IllegalArgumentException("Category scheme cannot be empty and has to have correct URI format.");
        }
        if (term == null) {
            throw new NullPointerException("Category term cannot be null.");
        }
        if (term.isEmpty()) {
            throw new IllegalArgumentException("Category term cannot be empty.");
        }

        LOGGER.debug("Creating category: scheme={}, term={}, title={}, attributes={}", scheme, term, title, attributes);
        this.scheme = scheme;
        this.term = term;
        this.title = title;
        this.location = location;

        if (attributes != null) {
            this.attributes.addAll(attributes);
        }
    }

    public Category(String scheme, String term) {
        this(scheme, term, null, null, null);
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        if (term == null) {
            throw new NullPointerException("Category term cannot be null.");
        }
        if (term.isEmpty()) {
            throw new IllegalArgumentException("Category term cannot be empty.");
        }

        this.term = term;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        if (scheme == null) {
            throw new NullPointerException("Category scheme cannot be null.");
        }
        if (scheme.isEmpty()) {
            throw new IllegalArgumentException("Category scheme cannot be empty.");
        }

        this.scheme = scheme;
    }

    @Override
    public String getIdentifier() {
        return getScheme() + getTerm();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    //attributes
    public boolean containsAttribute(Attribute attribute) {
        return attributes.contains(attribute);
    }

    public boolean containsAttribute(String attributeIdentifier) {
        return attributes.contains(attributeIdentifier);
    }

    public boolean addAttribute(Attribute attribute) {
        return attributes.add(attribute);
    }

    public Attribute getAttribute(String attributeIdentifier) {
        return attributes.get(attributeIdentifier);
    }

    public boolean removeAttribute(Attribute attribute) {
        return attributes.remove(attribute);
    }

    public void clearAttributes() {
        attributes.clear();
    }

    public Set<Attribute> getAttributes() {
        return attributes.getSet();
    }

    //actions
    public boolean containsAction(Action action) {
        return actions.contains(action);
    }

    public boolean containsAction(String actionIdentifier) {
        return actions.contains(actionIdentifier);
    }

    public boolean addAction(Action action) {
        return actions.add(action);
    }

    public Action getAction(String actionIdentifier) {
        return actions.get(actionIdentifier);
    }

    public boolean removeAction(Action action) {
        return actions.remove(action);
    }

    public void clearActions() {
        actions.clear();
    }

    public Set<Action> getActions() {
        return actions.getSet();
    }

    //entities
    public boolean containsEntity(Entity entity) {
        return entities.contains(entity);
    }

    public boolean containsEntity(String entityIdentifier) {
        return entities.contains(entityIdentifier);
    }

    public boolean addEntity(Entity entity) {
        return entities.add(entity);
    }

    public Entity getEntity(String entityIdentifier) {
        return entities.get(entityIdentifier);
    }

    public boolean removeEntity(Entity entity) {
        return entities.remove(entity);
    }

    public void clearEntities() {
        entities.clear();
    }

    public Set<Entity> getEntities() {
        return entities.getSet();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.term);
        hash = 79 * hash + Objects.hashCode(this.scheme);
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
        final Category other = (Category) obj;
        if (!Objects.equals(this.term, other.term)) {
            return false;
        }
        if (!Objects.equals(this.scheme, other.scheme)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Category{" + "class=" + getClass().getName() + ", term=" + term + ", scheme=" + scheme + ", title=" + title + ", attributes=" + attributes + '}';
    }
}
