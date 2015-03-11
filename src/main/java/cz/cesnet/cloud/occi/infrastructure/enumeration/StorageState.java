package cz.cesnet.cloud.occi.infrastructure.enumeration;

/**
 *
 * @author Michal Kimle <kimle.michal@gmail.com>
 */
public enum StorageState {

    ONLINE, OFFLINE, BACKUP, SNAPSHOT, RESIZE, DEGRADED;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
