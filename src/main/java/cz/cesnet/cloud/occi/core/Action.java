package cz.cesnet.cloud.occi.core;

import cz.cesnet.cloud.occi.type.Identifiable;
import cz.cesnet.cloud.occi.exception.NonexistingElementException;
import java.net.URI;
import java.util.Collection;
import java.util.Objects;

public class Action implements Identifiable {

    private Category category;

    public Action(URI scheme, String term, String title, String location, Collection<Attribute> attributes) {
        this.category = new Category(scheme, term, title, location, attributes);
    }

    public Action(URI scheme, String term) {
        this(scheme, term, null, null, null);
    }

    public URI getScheme() {
        return category.getScheme();
    }

    public void setScheme(URI scheme) {
        category.setScheme(scheme);
    }

    public String getTerm() {
        return category.getTerm();
    }

    public void setTerm(String term) {
        category.setTerm(term);
    }

    @Override
    public String getIdentifier() {
        return category.getIdentifier();
    }

    public String getTitle() {
        return category.getTitle();
    }

    public void setTitle(String title) {
        category.setTitle(title);
    }

    public String getLocation() {
        return category.getLocation();
    }

    public void setLocation(String location) {
        category.setLocation(location);
    }

    public boolean containsAttribute(Attribute attribute) {
        return category.containsAttribute(attribute);
    }

    public boolean containsAttribute(String attributeName) {
        return category.containsAttribute(attributeName);
    }

    public boolean addAttribute(Attribute attribute) {
        return category.addAttribute(attribute);
    }

    public Attribute getAttribute(String attributeName) throws NonexistingElementException {
        return category.getAttribute(attributeName);
    }

    public boolean removeAttribute(Attribute attribute) {
        return category.removeAttribute(attribute);
    }

    public Collection<Attribute> getAttributes() {
        return category.getAttributes();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.category);
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
        final Action other = (Action) obj;
        if (!Objects.equals(this.category, other.category)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Action{" + "category=" + category + '}';
    }
}
