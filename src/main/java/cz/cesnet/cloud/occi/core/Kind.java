package cz.cesnet.cloud.occi.core;

import java.net.URI;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Kind extends Category {

    private Set<Kind> related = new HashSet<>();
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
}
