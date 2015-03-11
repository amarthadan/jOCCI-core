package cz.cesnet.cloud.occi.infrastructure.enumeration;

/**
 *
 * @author Michal Kimle <kimle.michal@gmail.com>
 */
public enum NetworkState {

    ACTIVE, INACTIVE;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
