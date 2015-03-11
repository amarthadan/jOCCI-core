package cz.cesnet.cloud.occi.core;

import com.sun.net.httpserver.Headers;
import cz.cesnet.cloud.occi.collection.SetCover;
import cz.cesnet.cloud.occi.renderer.TextRenderer;
import cz.cesnet.cloud.occi.type.Identifiable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class representing an OCCI Category.
 *
 * @author Michal Kimle <kimle.michal@gmail.com>
 */
public class Category implements Identifiable, Comparable<Category> {

    private static final Logger LOGGER = LoggerFactory.getLogger(Category.class);
    public static final URI SCHEME_CORE_DEFAULT = makeURI("http://schemas.ogf.org/occi/core#");
    public static final URI SCHEME_INFRASTRUCTURE_DEFAULT = makeURI("http://schemas.ogf.org/occi/infrastructure#");

    private String term;
    private URI scheme;
    private String title;
    private URI location;
    private final SetCover<Attribute> attributes = new SetCover<>();
    private final SetCover<Action> actions = new SetCover<>();
    private final SetCover<Entity> entities = new SetCover<>();

    /**
     * Constructor. Creates a category with scheme, term, title, location and
     * attributes.
     *
     * @param scheme category's scheme. Cannot be null.
     * @param term category's term. Cannot be null nor empty.
     * @param title category's title
     * @param location category's location
     * @param attributes category's attributes
     */
    public Category(URI scheme, String term, String title, URI location, Collection<Attribute> attributes) {
        LOGGER.debug("Creating category: scheme={}, term={}, title={}, location={}, attributes={}", scheme, term, title, location, attributes);

        if (scheme == null) {
            throw new NullPointerException("Category scheme cannot be null.");
        }
        if (term == null) {
            throw new NullPointerException("Category term cannot be null.");
        }
        if (term.isEmpty()) {
            throw new IllegalArgumentException("Category term cannot be empty.");
        }

        this.scheme = scheme;
        this.term = term;
        this.title = title;
        this.location = location;

        if (attributes != null) {
            this.attributes.addAll(attributes);
        }
    }

    /**
     * Constructor. Creates a category with scheme and term.
     *
     * @param scheme category's scheme. Cannot be null.
     * @param term category's term. Cannot be null nor empty.
     */
    public Category(URI scheme, String term) {
        this(scheme, term, null, null, null);
    }

    private static URI makeURI(String uri) {
        try {
            return new URI(uri);
        } catch (URISyntaxException ex) {
            LOGGER.error("Wrong scheme URI", ex);
            return null;
        }
    }

    /**
     * Returns category's term.
     *
     * @return category's term
     */
    public String getTerm() {
        return term;
    }

    /**
     * Sets category's term.
     *
     * @param term category's term. Cannot be null nor empty.
     */
    public void setTerm(String term) {
        if (term == null) {
            throw new NullPointerException("Category term cannot be null.");
        }
        if (term.isEmpty()) {
            throw new IllegalArgumentException("Category term cannot be empty.");
        }

        this.term = term;
    }

    /**
     * Returns category's scheme.
     *
     * @return category's scheme
     */
    public URI getScheme() {
        return scheme;
    }

    /**
     * Sets category's scheme.
     *
     * @param scheme category's scheme. Cannot be null.
     */
    public void setScheme(URI scheme) {
        if (scheme == null) {
            throw new NullPointerException("Category scheme cannot be null.");
        }

        this.scheme = scheme;
    }

    /**
     * Returns category's identifier.
     *
     * @return category's identifier
     */
    @Override
    public String getIdentifier() {
        return getScheme().toString() + getTerm();
    }

    /**
     * Returns category's title.
     *
     * @return category's title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets category's title
     *
     * @param title category's title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns category's location.
     *
     * @return category's location
     */
    public URI getLocation() {
        return location;
    }

    /**
     * Sets category's location
     *
     * @param location category's location
     */
    public void setLocation(URI location) {
        this.location = location;
    }

    //attributes
    /**
     * Checks whether category has given attribute.
     *
     * @param attribute attribute to be looked up
     * @return true if category has given attribute, false otherwise
     */
    public boolean containsAttribute(Attribute attribute) {
        return attributes.contains(attribute);
    }

    /**
     * Checks whether category has given attribute.
     *
     * @param attributeIdentifier identifier of attribute to be looked up
     * @return true if category has given attribute, false otherwise
     */
    public boolean containsAttribute(String attributeIdentifier) {
        return attributes.contains(attributeIdentifier);
    }

    /**
     * Adds given attribute to category.
     *
     * @param attribute attribute to be added
     * @return true if the addition was successful, false otherwise
     */
    public boolean addAttribute(Attribute attribute) {
        return attributes.add(attribute);
    }

    /**
     * Returns attribute from category.
     *
     * @param attributeIdentifier identifier of the requested attribute
     * @return attribute from category
     */
    public Attribute getAttribute(String attributeIdentifier) {
        return attributes.get(attributeIdentifier);
    }

    /**
     * Removes attribute from category
     *
     * @param attribute to be removed
     * @return true if the removal was successful, false otherwise
     */
    public boolean removeAttribute(Attribute attribute) {
        return attributes.remove(attribute);
    }

    /**
     * Removes all attributes from category.
     */
    public void clearAttributes() {
        attributes.clear();
    }

    /**
     * Returns all category's attributes in form of set.
     *
     * @return all category's attributes in form of set
     */
    public Set<Attribute> getAttributes() {
        return attributes.getSet();
    }

    //actions
    /**
     * Checks whether category has given action.
     *
     * @param action action to be looked up
     * @return true if category has given action, false otherwise
     */
    public boolean containsAction(Action action) {
        return actions.contains(action);
    }

    /**
     * Checks whether category has given action.
     *
     * @param actionIdentifier identifier of action to be looked up
     * @return true if category has given action, false otherwise
     */
    public boolean containsAction(String actionIdentifier) {
        return actions.contains(actionIdentifier);
    }

    /**
     * Adds given action to category.
     *
     * @param action action to be added
     * @return true if the addition was successful, false otherwise
     */
    public boolean addAction(Action action) {
        return actions.add(action);
    }

    /**
     * Returns action from category.
     *
     * @param actionIdentifier identifier of the requested action
     * @return action from category
     */
    public Action getAction(String actionIdentifier) {
        return actions.get(actionIdentifier);
    }

    /**
     * Removes action from category
     *
     * @param action to be removed
     * @return true if the removal was successful, false otherwise
     */
    public boolean removeAction(Action action) {
        return actions.remove(action);
    }

    /**
     * Removes all actions from category.
     */
    public void clearActions() {
        actions.clear();
    }

    /**
     * Returns all category's actions in form of set.
     *
     * @return all category's actions in form of set
     */
    public Set<Action> getActions() {
        return actions.getSet();
    }

    //entities
    /**
     * Checks whether category has given entity.
     *
     * @param entity entity to be looked up
     * @return true if category has given entity, false otherwise
     */
    public boolean containsEntity(Entity entity) {
        return entities.contains(entity);
    }

    /**
     * Checks whether category has given entity.
     *
     * @param entityIdentifier identifier of entity to be looked up
     * @return true if category has given entity, false otherwise
     */
    public boolean containsEntity(String entityIdentifier) {
        return entities.contains(entityIdentifier);
    }

    /**
     * Adds given entity to category.
     *
     * @param entity entity to be added
     * @return true if the addition was successful, false otherwise
     */
    public boolean addEntity(Entity entity) {
        return entities.add(entity);
    }

    /**
     * Returns entity from category.
     *
     * @param entityIdentifier identifier of the requested entity
     * @return entity from category
     */
    public Entity getEntity(String entityIdentifier) {
        return entities.get(entityIdentifier);
    }

    /**
     * Removes entity from category
     *
     * @param entity to be removed
     * @return true if the removal was successful, false otherwise
     */
    public boolean removeEntity(Entity entity) {
        return entities.remove(entity);
    }

    /**
     * Removes all entities from category.
     */
    public void clearEntities() {
        entities.clear();
    }

    /**
     * Returns all category's entities in form of set.
     *
     * @return all category's entities in form of set
     */
    public Set<Entity> getEntities() {
        return entities.getSet();
    }

    /**
     * @see Object#hashCode()
     * @return category's hash code
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.term);
        hash = 79 * hash + Objects.hashCode(this.scheme);
        return hash;
    }

    /**
     * @see Object#equals(java.lang.Object)
     * @param obj object to compare category with
     * @return true if two categories are equal, false otherwise
     */
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

    /**
     * Resturns string representation of the category
     *
     * @see Object#toString()
     * @return string representation of the category
     */
    @Override
    public String toString() {
        return "Category{" + "term=" + term + ", scheme=" + scheme + ", title=" + title + ", location=" + location + ", attributes=" + attributes + '}';
    }

    /**
     * Returns a plain text representation of category and its subtypes (kinds
     * and mixins) according to OCCI standard.
     *
     * @return plain text representation of category
     */
    public String toText() {
        StringBuilder sb = new StringBuilder("Category: ");
        sb.append(textBody());

        return sb.toString();
    }

    /**
     * Returns an occi text representation of category and its subtypes (kinds
     * and mixins) according to OCCI standard in form of headers.
     *
     * @return plain text representation of category
     */
    public Headers toHeaders() {
        Headers headers = new Headers();
        headers.add("Category", textBody());

        return headers;
    }

    private String textBody() {
        StringBuilder sb = new StringBuilder(term);
        sb.append(";");
        sb.append("scheme");
        sb.append(TextRenderer.surroundString(scheme.toString()));
        sb.append("class");
        sb.append(TextRenderer.surroundString(this.getClass().getSimpleName().toLowerCase()));

        if (title != null && !title.isEmpty()) {
            sb.append("title");
            sb.append(TextRenderer.surroundString(title));
        }

        if (this instanceof Kind) {
            Kind kind = (Kind) this;
            if (kind.getRelations().size() == 1) {
                sb.append("rel");
                for (Kind k : kind.getRelations()) {
                    sb.append(TextRenderer.surroundString(k.getIdentifier()));
                }
            }
        }

        if (this instanceof Mixin) {
            Mixin mixin = (Mixin) this;
            if (mixin.getRelations().size() == 1) {
                sb.append("rel");
                for (Mixin m : mixin.getRelations()) {
                    sb.append(TextRenderer.surroundString(m.getIdentifier()));
                }
            }
        }

        if (location != null) {
            sb.append("location");
            sb.append(TextRenderer.surroundString(location.toString()));
        }

        if (attributes != null && !attributes.getSet().isEmpty()) {
            sb.append("attributes");
            StringBuilder attrSB = new StringBuilder();
            List<Attribute> attributeList = new ArrayList<>(attributes.getSet());
            Collections.sort(attributeList);
            for (Attribute attribute : attributeList) {
                attrSB.append(attribute.toText());
                attrSB.append(" ");
            }
            attrSB.deleteCharAt(attrSB.length() - 1);
            sb.append(TextRenderer.surroundString(attrSB.toString()));
        }

        if (actions != null && !actions.getSet().isEmpty()) {
            sb.append("actions");
            StringBuilder actionsSB = new StringBuilder();
            List<Action> actionList = new ArrayList<>(actions.getSet());
            Collections.sort(actionList);
            for (Action action : actionList) {
                actionsSB.append(action.getIdentifier());
                actionsSB.append(" ");
            }
            actionsSB.deleteCharAt(actionsSB.length() - 1);
            sb.append(TextRenderer.surroundString(actionsSB.toString()));
        }

        if (sb.charAt(sb.length() - 1) == ';') {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }

    /**
     * Comapres two cetegories lexicographically based on their identifier.
     *
     * @see Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(Category c) {
        return getIdentifier().compareTo(c.getIdentifier());
    }
}
