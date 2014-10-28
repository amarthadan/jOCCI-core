package cz.cesnet.cloud.occi.infrastructure.enumeration;

public enum StorageState {

    ONLINE, OFFLINE, BACKUP, SNAPSHOT, RESIZE, DEGRADED;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
