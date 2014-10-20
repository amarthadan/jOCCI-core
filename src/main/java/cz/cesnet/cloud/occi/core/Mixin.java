package cz.cesnet.cloud.occi.core;

import java.net.URI;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Mixin extends Category {

    private Set<Mixin> related = new HashSet<>();

    public Mixin(URI scheme, String term, String title, String location, Collection<Attribute> attributes) {
        super(scheme, term, title, location, attributes);
    }

    public Mixin(URI scheme, String term) {
        this(scheme, term, null, null, null);
    }
}
