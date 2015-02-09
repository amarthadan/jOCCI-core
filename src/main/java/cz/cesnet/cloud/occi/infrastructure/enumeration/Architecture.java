package cz.cesnet.cloud.occi.infrastructure.enumeration;

/**
 *
 * @author Michal Kimle <kimle.michal@gmail.com>
 */
public enum Architecture {

    X_86, X_64;

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
