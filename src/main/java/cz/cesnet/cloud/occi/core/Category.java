package cz.cesnet.cloud.occi.core;

import cz.cesnet.cloud.occi.exception.NonexistingAttributeException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Category {

    private static final Logger LOGGER = LoggerFactory.getLogger(Category.class);
    public static final URI DEFAULT_SCHEME = makeURI("http://schemas.ogf.org/occi/core#");

    private String term;
    private URI scheme;
    private String title;
    private final Set<Attribute> attributes = new HashSet<>();

    public static URI makeURI(String uri) {
        try {
            return new URI(uri);
        } catch (URISyntaxException ex) {
            LOGGER.error("Wrong scheme URI", ex);
            return null;
        }
    }

    public Category(URI scheme, String term, String title, Collection<Attribute> attributes) {
        if (scheme == null) {
            throw new IllegalArgumentException("Category scheme cannot be null.");
        }
        if (term == null || term.isEmpty()) {
            throw new IllegalArgumentException("Category term cannot be null nor empty.");
        }

        LOGGER.debug("Creating category: scheme={}, term={}, title={}, attributes={}", scheme, term, title, attributes);
        this.scheme = scheme;
        this.term = term;
        this.title = title;

        if (attributes != null) {
            this.attributes.addAll(attributes);
        }
    }

    public Category(String term, String title, Collection<Attribute> attributes) {
        this(DEFAULT_SCHEME, term, title, attributes);
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        if (term == null || term.isEmpty()) {
            throw new IllegalArgumentException("Category term cannot be null nor empty.");
        }

        this.term = term;
    }

    public URI getScheme() {
        return scheme;
    }

    public void setScheme(URI scheme) {
        if (scheme == null) {
            throw new IllegalArgumentException("Category scheme cannot be null.");
        }

        this.scheme = scheme;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean containsAttribute(Attribute attribute) {
        return attributes.contains(attribute);
    }

    public boolean containsAttribute(String attributeName) {
        Attribute tmpAttr = new Attribute(attributeName);
        return containsAttribute(tmpAttr);
    }

    public boolean addAttribute(Attribute attribute) {
        return attributes.add(attribute);
    }

    public Attribute getAttribute(String attributeName) throws NonexistingAttributeException {
        if (!containsAttribute(attributeName)) {
            throw new NonexistingAttributeException("Category " + this + " doesn't contain attribute with name " + attributeName + ".");
        }

        return new Attribute(findAttribute(attributeName));
    }

    public boolean removeAttribute(Attribute attribute) {
        return attributes.remove(attribute);
    }

    private Attribute findAttribute(String attributeName) throws NonexistingAttributeException {
        for (Attribute attribute : attributes) {
            if (attribute.getName().equals(attributeName)) {
                return attribute;
            }
        }

        throw new NonexistingAttributeException("Category " + this + " doesn't contain attribute with name " + attributeName + ".");
    }

    public Collection<Attribute> getAttributes() {
        return Collections.unmodifiableSet(attributes);
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
        return "Category{" + "term=" + term + ", scheme=" + scheme + ", title=" + title + '}';
    }
}
