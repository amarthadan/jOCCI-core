package cz.cesnet.cloud.occi.core;

import com.sun.net.httpserver.Headers;
import cz.cesnet.cloud.occi.renderer.TextRenderer;
import cz.cesnet.cloud.occi.type.Identifiable;
import java.net.URI;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class representing an OCCI Action structure.
 *
 * @author Michal Kimle <kimle.michal@gmail.com>
 */
public class Action implements Identifiable, Comparable<Action> {

    private static final Logger LOGGER = LoggerFactory.getLogger(Action.class);
    private Category category;

    /**
     * Constructor. Creates an Action instance with given scheme, term, title
     * and set of attributes.
     *
     * @param scheme action's scheme. Cannot be null.
     * @param term action's term. Cannot be null nor empty.
     * @param title action's title
     * @param attributes set of action's attributes
     */
    public Action(URI scheme, String term, String title, Collection<Attribute> attributes) {
        LOGGER.debug("Creating action: scheme={}, term={}, title={}, attributes={}", scheme, term, title, attributes);
        this.category = new Category(scheme, term, title, null, attributes);
    }

    /**
     * Constructor. Creates an Action instance with given scheme and term.
     *
     * @param scheme action's scheme. Cannot be null.
     * @param term action's term. Cannot be null nor empty.
     */
    public Action(URI scheme, String term) {
        this(scheme, term, null, null);
    }

    /**
     * Returns action's scheme.
     *
     * @return action's scheme.
     */
    public URI getScheme() {
        return category.getScheme();
    }

    /**
     * Sets action's scheme.
     *
     * @param scheme action's scheme. Cannot be null.
     */
    public void setScheme(URI scheme) {
        category.setScheme(scheme);
    }

    /**
     * Returns action's term.
     *
     * @return action's term.
     */
    public String getTerm() {
        return category.getTerm();
    }

    /**
     * Sets action's term.
     *
     * @param term action's term. Cannot be null nor empty.
     */
    public void setTerm(String term) {
        category.setTerm(term);
    }

    /**
     * Returns action's identifier.
     *
     * @return action's identifier
     */
    @Override
    public String getIdentifier() {
        return category.getIdentifier();
    }

    /**
     * Returns action's title.
     *
     * @return action's title
     */
    public String getTitle() {
        return category.getTitle();
    }

    /**
     * Sets action's title.
     *
     * @param title action's title
     */
    public void setTitle(String title) {
        category.setTitle(title);
    }

    /**
     * Checks whether action has the attribute.
     *
     * @param attribute attribute to be looked up
     * @return true if action has the attribute, false otherwise
     */
    public boolean containsAttribute(Attribute attribute) {
        return category.containsAttribute(attribute);
    }

    /**
     * Checks whether action has the attribute.
     *
     * @param attributeName name of the attribute to be looked up
     * @return true if action has the attribute, false otherwise
     */
    public boolean containsAttribute(String attributeName) {
        return category.containsAttribute(attributeName);
    }

    /**
     * Adds attribute to the action.
     *
     * @param attribute attribute to be added
     * @return true if the addition was successful, false otherwise
     */
    public boolean addAttribute(Attribute attribute) {
        return category.addAttribute(attribute);
    }

    /**
     * Retrieves an attribute from the action.
     *
     * @param attributeName name of the attribute to be retrieved
     * @return attribute
     */
    public Attribute getAttribute(String attributeName) {
        return category.getAttribute(attributeName);
    }

    /**
     * Removes attribute from the action.
     *
     * @param attribute attribute to be removed
     * @return true if the removal was successful, false otherwise
     */
    public boolean removeAttribute(Attribute attribute) {
        return category.removeAttribute(attribute);
    }

    /**
     * Returns all action's attribute in form of set.
     *
     * @return all action's attribute in form of set
     */
    public Set<Attribute> getAttributes() {
        return category.getAttributes();
    }

    /**
     * Returns action's default scheme 'http://schemas.ogf.org/occi/core#'
     *
     * @return action's default scheme
     */
    public URI getSchemeDefault() {
        return Category.SCHEME_CORE_DEFAULT;
    }

    /**
     * Returns action's default term 'term'
     *
     * @return action's default term
     */
    public String getTermDefault() {
        return "action";
    }

    /**
     * @see Object#hashCode()
     * @return action's hash code
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.category);
        return hash;
    }

    /**
     * @see Object#equals(java.lang.Object)
     * @param obj object to compare action with
     * @return true if two actions are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Action other = (Action) obj;
        if (!Objects.equals(this.category, other.category)) {
            return false;
        }
        return true;
    }

    /**
     * Resturns string representation of the action
     *
     * @see Object#toString()
     * @return string representation of the action
     */
    @Override
    public String toString() {
        return "Action{" + "category=" + category + '}';
    }

    /**
     * Returns a text representation of action link as described in OCCI
     * standard.
     *
     * @param resourceLocation
     * @return text representation of action link
     */
    public String toText(String resourceLocation) {
        StringBuilder sb = new StringBuilder("Link: ");
        sb.append(textBody(resourceLocation));

        return sb.toString();
    }

    /**
     * Returns an occi text representation of action link as described in OCCI
     * standard in form of headers.
     *
     * @param resourceLocation
     * @return text representation of action link
     */
    public Headers toHeaders(String resourceLocation) {
        Headers headers = new Headers();
        headers.add("Link", textBody(resourceLocation));

        return headers;
    }

    private String textBody(String resourceLocation) {
        StringBuilder sb = new StringBuilder("");

        String descriptor = resourceLocation + "?action=" + getTerm();
        sb.append(TextRenderer.surroundString(descriptor, "<", ">;"));

        sb.append("rel");
        sb.append(TextRenderer.surroundString(getScheme().toString() + getTerm()));

        return sb.toString();
    }

    /**
     * Comapres two actions lexicographically based on their identifier.
     *
     * @see Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(Action a) {
        return getIdentifier().compareTo(a.getIdentifier());
    }
}
