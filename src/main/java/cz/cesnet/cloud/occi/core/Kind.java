package cz.cesnet.cloud.occi.core;

import cz.cesnet.cloud.occi.collection.SetCover;
import cz.cesnet.cloud.occi.exception.NonexistingElementException;
import java.net.URI;
import java.util.Collection;
import java.util.Set;

public class Kind extends Category {

    private final SetCover<Kind> related = new SetCover<>();
    private String entityType;

    public Kind(URI scheme, String term, String title, String location, Collection<Attribute> attributes, String entityType) {
        super(scheme, term, title, location, attributes);

        this.entityType = entityType;
    }

    public Kind(URI scheme, String term) {
        this(scheme, term, null, null, null, null);
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public boolean relatesTo(Kind kind) {
        return related.contains(kind);
    }

    public boolean relatesTo(String kindIdentifier) {
        return related.contains(kindIdentifier);
    }

    public boolean addRelation(Kind kind) {
        return related.add(kind);
    }

    public Kind getRelatedKind(String kindIdentifier) throws NonexistingElementException {
        return related.get(kindIdentifier);
    }

    public boolean removeRelation(Kind kind) {
        return related.remove(kind);
    }

    public void clearRelations() {
        related.clear();
    }

    public Set<Kind> getRelations() {
        return related.getSet();
    }
}
