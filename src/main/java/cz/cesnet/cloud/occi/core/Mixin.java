package cz.cesnet.cloud.occi.core;

import cz.cesnet.cloud.occi.collection.SetCover;
import java.net.URI;
import java.util.Collection;
import java.util.Set;

/**
 * Class representing an OCCI Mixin.
 *
 * @author Michal Kimle <kimle.michal@gmail.com>
 */
public class Mixin extends Category {

    private final SetCover<Mixin> related = new SetCover<>();

    /**
     * Constructor. Creates new mixin with scheme, term, title, location and
     * attributes.
     *
     * @param scheme mixin's scheme. Cannot be null.
     * @param term mixin's term. Cannot be null nor empty.
     * @param title mixin's title
     * @param location mixin's location
     * @param attributes mixin's attributes
     */
    public Mixin(URI scheme, String term, String title, URI location, Collection<Attribute> attributes) {
        super(scheme, term, title, location, attributes);
    }

    /**
     * Constructor. Creates new mixin with scheme and term.
     *
     * @param scheme mixin's scheme. Cannot be null.
     * @param term mixin's term. Cannot be null nor empty.
     */
    public Mixin(URI scheme, String term) {
        this(scheme, term, null, null, null);
    }

    /**
     * Checks whether this mixin is in relation with given mixin.
     *
     * @param mixin mixin to chcek relation with
     * @return true if there is relation between mixins, false otherwise
     */
    public boolean relatesTo(Mixin mixin) {
        return related.contains(mixin);
    }

    /**
     * Checks whether this mixin is in relation with given mixin.
     *
     * @param mixinIdentifier identifier of mixin to chcek relation with
     * @return true if there is relation between mixins, false otherwise
     */
    public boolean relatesTo(String mixinIdentifier) {
        return related.contains(mixinIdentifier);
    }

    /**
     * Creates a relation with given mixin.
     *
     * @param mixin mixin to create a relation with
     * @return true if the relation was created successfully, false otherwise
     */
    public boolean addRelation(Mixin mixin) {
        return related.add(mixin);
    }

    /**
     * Creates a relation with given mixin.
     *
     * @param mixinIdentifier identifier of mixin to create a relation with
     * @return true if the relation was created successfully, false otherwise
     */
    public Mixin getRelatedMixin(String mixinIdentifier) {
        return related.get(mixinIdentifier);
    }

    /**
     * Removes relation with given mixin.
     *
     * @param mixin mixin with which relation will be removed
     * @return true if the relation was removed successfully, false otherwise
     */
    public boolean removeRelation(Mixin mixin) {
        return related.remove(mixin);
    }

    /**
     * Remove all relations.
     */
    public void clearRelations() {
        related.clear();
    }

    /**
     * Returns all related mixins in form of set.
     *
     * @return all related mixins in form of set
     */
    public Set<Mixin> getRelations() {
        return related.getSet();
    }
}
