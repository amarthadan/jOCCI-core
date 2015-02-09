package cz.cesnet.cloud.occi.core;

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
 * Class representing an OCCI Category
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
     * Constructor
     *
     * @param scheme cannot be null
     * @param term cannot be null nor empty
     * @param title
     * @param location
     * @param attributes
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
     *
     * @param scheme cannot be null
     * @param term cannot be null nor empty
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
     *
     * @return
     */
    public String getTerm() {
        return term;
    }

    /**
     *
     * @param term cannot be null nor empty
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
     *
     * @return
     */
    public URI getScheme() {
        return scheme;
    }

    /**
     *
     * @param scheme cannot be null
     */
    public void setScheme(URI scheme) {
        if (scheme == null) {
            throw new NullPointerException("Category scheme cannot be null.");
        }

        this.scheme = scheme;
    }

    /**
     *
     * @return
     */
    @Override
    public String getIdentifier() {
        return getScheme().toString() + getTerm();
    }

    /**
     *
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     */
    public URI getLocation() {
        return location;
    }

    /**
     *
     * @param location
     */
    public void setLocation(URI location) {
        this.location = location;
    }

    //attributes
    /**
     *
     * @param attribute
     * @return
     */
    public boolean containsAttribute(Attribute attribute) {
        return attributes.contains(attribute);
    }

    /**
     *
     * @param attributeIdentifier
     * @return
     */
    public boolean containsAttribute(String attributeIdentifier) {
        return attributes.contains(attributeIdentifier);
    }

    /**
     *
     * @param attribute
     * @return
     */
    public boolean addAttribute(Attribute attribute) {
        return attributes.add(attribute);
    }

    /**
     *
     * @param attributeIdentifier
     * @return
     */
    public Attribute getAttribute(String attributeIdentifier) {
        return attributes.get(attributeIdentifier);
    }

    /**
     *
     * @param attribute
     * @return
     */
    public boolean removeAttribute(Attribute attribute) {
        return attributes.remove(attribute);
    }

    /**
     *
     */
    public void clearAttributes() {
        attributes.clear();
    }

    /**
     *
     * @return
     */
    public Set<Attribute> getAttributes() {
        return attributes.getSet();
    }

    //actions
    /**
     *
     * @param action
     * @return
     */
    public boolean containsAction(Action action) {
        return actions.contains(action);
    }

    /**
     *
     * @param actionIdentifier
     * @return
     */
    public boolean containsAction(String actionIdentifier) {
        return actions.contains(actionIdentifier);
    }

    /**
     *
     * @param action
     * @return
     */
    public boolean addAction(Action action) {
        return actions.add(action);
    }

    /**
     *
     * @param actionIdentifier
     * @return
     */
    public Action getAction(String actionIdentifier) {
        return actions.get(actionIdentifier);
    }

    /**
     *
     * @param action
     * @return
     */
    public boolean removeAction(Action action) {
        return actions.remove(action);
    }

    /**
     *
     */
    public void clearActions() {
        actions.clear();
    }

    /**
     *
     * @return
     */
    public Set<Action> getActions() {
        return actions.getSet();
    }

    //entities
    /**
     *
     * @param entity
     * @return
     */
    public boolean containsEntity(Entity entity) {
        return entities.contains(entity);
    }

    /**
     *
     * @param entityIdentifier
     * @return
     */
    public boolean containsEntity(String entityIdentifier) {
        return entities.contains(entityIdentifier);
    }

    /**
     *
     * @param entity
     * @return
     */
    public boolean addEntity(Entity entity) {
        return entities.add(entity);
    }

    /**
     *
     * @param entityIdentifier
     * @return
     */
    public Entity getEntity(String entityIdentifier) {
        return entities.get(entityIdentifier);
    }

    /**
     *
     * @param entity
     * @return
     */
    public boolean removeEntity(Entity entity) {
        return entities.remove(entity);
    }

    /**
     *
     */
    public void clearEntities() {
        entities.clear();
    }

    /**
     *
     * @return
     */
    public Set<Entity> getEntities() {
        return entities.getSet();
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.term);
        hash = 79 * hash + Objects.hashCode(this.scheme);
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
     *
     * @return
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
        sb.append(term);
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
     *
     * @param c
     * @return
     */
    @Override
    public int compareTo(Category c) {
        return getIdentifier().compareTo(c.getIdentifier());
    }
}
