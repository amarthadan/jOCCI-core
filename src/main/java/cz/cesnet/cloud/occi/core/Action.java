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
     *
     * @param scheme cannot be null
     * @param term cannot be null nor empty
     * @param title
     * @param attributes
     */
    public Action(URI scheme, String term, String title, Collection<Attribute> attributes) {
        LOGGER.debug("Creating action: scheme={}, term={}, title={}, attributes={}", scheme, term, title, attributes);
        this.category = new Category(scheme, term, title, null, attributes);
    }

    /**
     *
     * @param scheme cannot be null
     * @param term cannot be null nor empty
     */
    public Action(URI scheme, String term) {
        this(scheme, term, null, null);
    }

    /**
     *
     * @return
     */
    public URI getScheme() {
        return category.getScheme();
    }

    /**
     *
     * @param scheme cannot be null
     */
    public void setScheme(URI scheme) {
        category.setScheme(scheme);
    }

    /**
     *
     * @return
     */
    public String getTerm() {
        return category.getTerm();
    }

    /**
     *
     * @param term cannot be null nor empty
     */
    public void setTerm(String term) {
        category.setTerm(term);
    }

    /**
     *
     * @return
     */
    @Override
    public String getIdentifier() {
        return category.getIdentifier();
    }

    /**
     *
     * @return
     */
    public String getTitle() {
        return category.getTitle();
    }

    /**
     *
     * @param title
     */
    public void setTitle(String title) {
        category.setTitle(title);
    }

    /**
     *
     * @param attribute
     * @return
     */
    public boolean containsAttribute(Attribute attribute) {
        return category.containsAttribute(attribute);
    }

    /**
     *
     * @param attributeName
     * @return
     */
    public boolean containsAttribute(String attributeName) {
        return category.containsAttribute(attributeName);
    }

    /**
     *
     * @param attribute
     * @return
     */
    public boolean addAttribute(Attribute attribute) {
        return category.addAttribute(attribute);
    }

    /**
     *
     * @param attributeName
     * @return
     */
    public Attribute getAttribute(String attributeName) {
        return category.getAttribute(attributeName);
    }

    /**
     *
     * @param attribute
     * @return
     */
    public boolean removeAttribute(Attribute attribute) {
        return category.removeAttribute(attribute);
    }

    /**
     *
     * @return
     */
    public Set<Attribute> getAttributes() {
        return category.getAttributes();
    }

    /**
     *
     * @return
     */
    public URI getSchemeDefault() {
        return Category.SCHEME_CORE_DEFAULT;
    }

    /**
     *
     * @return
     */
    public String getTermDefault() {
        return "action";
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.category);
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
        final Action other = (Action) obj;
        if (!Objects.equals(this.category, other.category)) {
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
     *
     * @param a
     * @return
     */
    @Override
    public int compareTo(Action a) {
        return getIdentifier().compareTo(a.getIdentifier());
    }
}
