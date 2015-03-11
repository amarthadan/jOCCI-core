package cz.cesnet.cloud.occi.infrastructure.enumeration;

/**
 *
 * @author Michal Kimle <kimle.michal@gmail.com>
 */
public enum ComputeState {

    ACTIVE, INACTIVE, SUSPENDED;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
