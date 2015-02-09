package cz.cesnet.cloud.occi.core;

import cz.cesnet.cloud.occi.collection.SetCover;
import java.net.URI;
import java.util.Collection;
import java.util.Set;

/**
 * Class representing an OCCI Mixin
 *
 * @author Michal Kimle <kimle.michal@gmail.com>
 */
public class Mixin extends Category {

    private final SetCover<Mixin> related = new SetCover<>();

    /**
     *
     * @param scheme cannot be null
     * @param term cannot be null nor empty
     * @param title
     * @param location
     * @param attributes
     */
    public Mixin(URI scheme, String term, String title, URI location, Collection<Attribute> attributes) {
        super(scheme, term, title, location, attributes);
    }

    /**
     *
     * @param scheme cannot be null
     * @param term cannot be null nor empty
     */
    public Mixin(URI scheme, String term) {
        this(scheme, term, null, null, null);
    }

    /**
     *
     * @param mixin
     * @return
     */
    public boolean relatesTo(Mixin mixin) {
        return related.contains(mixin);
    }

    /**
     *
     * @param mixinIdentifier
     * @return
     */
    public boolean relatesTo(String mixinIdentifier) {
        return related.contains(mixinIdentifier);
    }

    /**
     *
     * @param mixin
     * @return
     */
    public boolean addRelation(Mixin mixin) {
        return related.add(mixin);
    }

    /**
     *
     * @param mixinIdentifier
     * @return
     */
    public Mixin getRelatedMixin(String mixinIdentifier) {
        return related.get(mixinIdentifier);
    }

    /**
     *
     * @param mixin
     * @return
     */
    public boolean removeRelation(Mixin mixin) {
        return related.remove(mixin);
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
    public Set<Mixin> getRelations() {
        return related.getSet();
    }
}
