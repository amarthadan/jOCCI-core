package cz.cesnet.cloud.occi.infrastructure.enumeration;

public enum NetworkState {

    ACTIVE, INACTIVE;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
