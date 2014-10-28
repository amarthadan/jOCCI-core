package cz.cesnet.cloud.occi.infrastructure.enumeration;

public enum StorageLinkState {

    ACTIVE, INACTIVE;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
