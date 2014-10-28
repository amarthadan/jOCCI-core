package cz.cesnet.cloud.occi.infrastructure.enumeration;

public enum ComputeState {

    ACTIVE, INACTIVE, SUSPENDED;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
