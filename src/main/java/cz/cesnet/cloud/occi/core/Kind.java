package cz.cesnet.cloud.occi.core;

import cz.cesnet.cloud.occi.collection.SetCover;
import java.net.URI;
import java.util.Collection;
import java.util.Set;

/**
 * Class representing an OCCI Kind
 *
 * @author Michal Kimle <kimle.michal@gmail.com>
 */
public class Kind extends Category {

    private final SetCover<Kind> related = new SetCover<>();
    private String entityType;
    private Kind parentKind = null;

    /**
     *
     * @param scheme cannot be null
     * @param term cannot be null nor empty
     * @param title
     * @param location
     * @param attributes
     */
    public Kind(URI scheme, String term, String title, URI location, Collection<Attribute> attributes) {
        super(scheme, term, title, location, attributes);
    }

    /**
     *
     * @param scheme cannot be null
     * @param term cannot be null nor empty
     */
    public Kind(URI scheme, String term) {
        this(scheme, term, null, null, null);
    }

    /**
     *
     * @return
     */
    public String getEntityType() {
        return entityType;
    }

    /**
     *
     * @param entityType
     */
    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    /**
     *
     * @return
     */
    public Kind getParentKind() {
        return parentKind;
    }

    /**
     *
     * @param parentKind
     */
    public void setParentKind(Kind parentKind) {
        this.parentKind = parentKind;
    }

    /**
     *
     * @param kind
     * @return
     */
    public boolean relatesTo(Kind kind) {
        return related.contains(kind);
    }

    /**
     *
     * @param kindIdentifier
     * @return
     */
    public boolean relatesTo(String kindIdentifier) {
        return related.contains(kindIdentifier);
    }

    /**
     *
     * @param kind
     * @return
     */
    public boolean addRelation(Kind kind) {
        return related.add(kind);
    }

    /**
     *
     * @param kindIdentifier
     * @return
     */
    public Kind getRelatedKind(String kindIdentifier) {
        return related.get(kindIdentifier);
    }

    /**
     *
     * @param kind
     * @return
     */
    public boolean removeRelation(Kind kind) {
        return related.remove(kind);
    }

    /**
     *
     */
    public void clearRelations() {
        related.clear();
    }

    /**
     *
     * @return
     */
    public Set<Kind> getRelations() {
        return related.getSet();
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Kind{" + "term=" + getTerm() + ", scheme=" + getScheme() + ", title=" + getTitle() + ", location=" + getLocation() + ", attributes=" + getAttributes() + ", related=" + related + '}';
    }
}
