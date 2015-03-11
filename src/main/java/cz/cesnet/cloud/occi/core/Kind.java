package cz.cesnet.cloud.occi.core;

import cz.cesnet.cloud.occi.collection.SetCover;
import java.net.URI;
import java.util.Collection;
import java.util.Set;

/**
 * Class representing an OCCI Kind.
 *
 * @author Michal Kimle <kimle.michal@gmail.com>
 */
public class Kind extends Category {

    private final SetCover<Kind> related = new SetCover<>();
    private String entityType;
    private Kind parentKind = null;

    /**
     * Constructor. Creates new kind with scheme, term, title, location and
     * attributes.
     *
     * @param scheme kind's scheme. Cannot be null.
     * @param term kind's term. Cannot be null nor empty.
     * @param title kind's title
     * @param location kind's location
     * @param attributes kind's attributes
     */
    public Kind(URI scheme, String term, String title, URI location, Collection<Attribute> attributes) {
        super(scheme, term, title, location, attributes);
    }

    /**
     * Constructor. Creates new kind with scheme and term.
     *
     * @param scheme kind's scheme. Cannot be null.
     * @param term kind's term. Cannot be null nor empty.
     */
    public Kind(URI scheme, String term) {
        this(scheme, term, null, null, null);
    }

    /**
     * Returns kind's entity type.
     *
     * @return kind's entity type
     */
    public String getEntityType() {
        return entityType;
    }

    /**
     * Sets kind's entity type.
     *
     * @param entityType kind's entity type
     */
    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    /**
     * Returns kind's parent kind. For example kind
     * 'http://schemas.ogf.org/occi/infrastructure#compute' has parent kind
     * 'http://schemas.ogf.org/occi/core#resource'.
     *
     * @return kind's parent kind
     */
    public Kind getParentKind() {
        return parentKind;
    }

    /**
     * Sets kind's parent kind.
     *
     * @param parentKind kind's parent kind
     */
    public void setParentKind(Kind parentKind) {
        this.parentKind = parentKind;
    }

    /**
     * Checks whether this kind is in relation with given kind.
     *
     * @param kind kind to chcek relation with
     * @return true if there is relation between kinds, false otherwise
     */
    public boolean relatesTo(Kind kind) {
        return related.contains(kind);
    }

    /**
     * Checks whether this kind is in relation with given kind.
     *
     * @param kindIdentifier identifier of kind to chcek relation with
     * @return true if there is relation between kinds, false otherwise
     */
    public boolean relatesTo(String kindIdentifier) {
        return related.contains(kindIdentifier);
    }

    /**
     * Creates a relation with given kind.
     *
     * @param kind kind to create a relation with
     * @return true if the relation was created successfully, false otherwise
     */
    public boolean addRelation(Kind kind) {
        return related.add(kind);
    }

    /**
     * Creates a relation with given kind.
     *
     * @param kindIdentifier identifier of kind to create a relation with
     * @return true if the relation was created successfully, false otherwise
     */
    public Kind getRelatedKind(String kindIdentifier) {
        return related.get(kindIdentifier);
    }

    /**
     * Removes relation with given kind.
     *
     * @param kind kind with which relation will be removed
     * @return true if the relation was removed successfully, false otherwise
     */
    public boolean removeRelation(Kind kind) {
        return related.remove(kind);
    }

    /**
     * Remove all relations.
     */
    public void clearRelations() {
        related.clear();
    }

    /**
     * Returns all related kinds in form of set.
     *
     * @return all related kinds in form of set
     */
    public Set<Kind> getRelations() {
        return related.getSet();
    }

    /**
     * Resturns string representation of kind
     *
     * @see Object#toString()
     * @return string representation of kind
     */
    @Override
    public String toString() {
        return "Kind{" + "term=" + getTerm() + ", scheme=" + getScheme() + ", title=" + getTitle() + ", location=" + getLocation() + ", attributes=" + getAttributes() + ", related=" + related + '}';
    }
}
