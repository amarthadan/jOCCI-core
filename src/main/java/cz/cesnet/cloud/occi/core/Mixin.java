package cz.cesnet.cloud.occi.core;

import java.net.URI;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Mixin extends Category {

    private Set<Action> actions = new HashSet<>();
    private Set<Mixin> related = new HashSet<>();
    private Set<Entity> entities = new HashSet<>();
    
    public Mixin(URI scheme, String term, String title, Collection<Attribute> attributes) {
        super(scheme, term, title, attributes);
    }
    
    public Mixin(String term, String title, Collection<Attribute> attributes) {
        super(term, title, attributes);
    }

    public Mixin(URI scheme, String term, String title, Collection<Attribute> attributes, Mixin relatedMixin) {
        this(scheme, term, title, attributes);
        related.add(relatedMixin);
    }
    
    public Mixin(String term, String title, Collection<Attribute> attributes, Mixin relatedMixin) {
        this(term, title, attributes);
        related.add(relatedMixin);
    }
}
