package cz.cesnet.cloud.occi.core;

import java.net.URI;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Kind extends Category {

    private Set<Action> actions = new HashSet<>();
    private Set<Kind> related = new HashSet<>();
    private String entityType;
    private Set<Entity> entities;
    
    public Kind(URI scheme, String term, String title, Collection<Attribute> attributes, String entityType) {
        super(scheme, term, title, attributes);
        
        if (entityType == null || entityType.isEmpty()) {
            throw new IllegalArgumentException("Kind entity type cannot be null nor empty.");
        }
        this.entityType = entityType;
    }
    
    public Kind(String term, String title, Collection<Attribute> attributes, String entityType) {
        super(term, title, attributes);
        
        if (entityType == null || entityType.isEmpty()) {
            throw new IllegalArgumentException("Kind entity type cannot be null nor empty.");
        }
        this.entityType = entityType;
    }

    public Kind(URI scheme, String term, String title, Collection<Attribute> attributes, String entityType, Kind relatedKind) {
        this(scheme, term, title, attributes, entityType);
        related.add(relatedKind);
    }
    
    public Kind(String term, String title, Collection<Attribute> attributes, String entityType, Kind relatedKind) {
        this(term, title, attributes, entityType);
        related.add(relatedKind);
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }
}
