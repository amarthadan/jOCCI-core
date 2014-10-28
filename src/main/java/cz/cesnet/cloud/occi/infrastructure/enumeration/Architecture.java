package cz.cesnet.cloud.occi.infrastructure.enumeration;

public enum Architecture {

    X_86, X_64;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
