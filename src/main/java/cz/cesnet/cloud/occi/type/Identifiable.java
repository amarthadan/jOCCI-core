package cz.cesnet.cloud.occi.type;

/**
 * @author Michal Kimle <kimle.michal@gmail.com>
 */
public interface Identifiable {

    /**
     * Returns identifier by which the object is recognized.
     *
     * @return object's identifier
     */
    public String getIdentifier();
}
