package cz.cesnet.cloud.occi.core;

import cz.cesnet.cloud.occi.collection.SetCover;
import java.net.URI;
import java.util.Collection;
import java.util.Set;

public class Mixin extends Category {

    private final SetCover<Mixin> related = new SetCover<>();

    public Mixin(URI scheme, String term, String title, String location, Collection<Attribute> attributes) {
        super(scheme, term, title, location, attributes);
    }

    public Mixin(URI scheme, String term) {
        this(scheme, term, null, null, null);
    }

    public boolean relatesTo(Mixin mixin) {
        return related.contains(mixin);
    }

    public boolean relatesTo(String mixinIdentifier) {
        return related.contains(mixinIdentifier);
    }

    public boolean addRelation(Mixin mixin) {
        return related.add(mixin);
    }

    public Mixin getRelatedMixin(String mixinIdentifier) {
        return related.get(mixinIdentifier);
    }

    public boolean removeRelation(Mixin mixin) {
        return related.remove(mixin);
    }

    public void clearRelations() {
        related.clear();
    }

    public Set<Mixin> getRelations() {
        return related.getSet();
    }
}
