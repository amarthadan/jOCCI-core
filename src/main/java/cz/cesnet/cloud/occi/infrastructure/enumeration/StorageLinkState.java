package cz.cesnet.cloud.occi.infrastructure.enumeration;

/**
 *
 * @author Michal Kimle <kimle.michal@gmail.com>
 */
public enum StorageLinkState {

    ACTIVE, INACTIVE;

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
