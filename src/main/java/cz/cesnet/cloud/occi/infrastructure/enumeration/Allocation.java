package cz.cesnet.cloud.occi.infrastructure.enumeration;

public enum Allocation {

    DYNAMIC, STATIC;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
