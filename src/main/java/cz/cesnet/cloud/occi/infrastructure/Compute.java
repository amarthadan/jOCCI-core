package cz.cesnet.cloud.occi.infrastructure;

import cz.cesnet.cloud.occi.Model;
import cz.cesnet.cloud.occi.core.Category;
import cz.cesnet.cloud.occi.core.Kind;
import cz.cesnet.cloud.occi.core.Resource;
import cz.cesnet.cloud.occi.exception.InvalidAttributeValueException;
import cz.cesnet.cloud.occi.infrastructure.enumeration.ComputeState;
import cz.cesnet.cloud.occi.infrastructure.enumeration.Architecture;
import java.net.URI;

/**
 * Class representing an OCCI Compute
 *
 * @author Michal Kimle <kimle.michal@gmail.com>
 */
public class Compute extends Resource {

    public static final String ARCHITECTURE_ATTRIBUTE_NAME = "occi.compute.architecture";
    public static final String CORES_ATTRIBUTE_NAME = "occi.compute.cores";
    public static final String HOSTNAME_ATTRIBUTE_NAME = "occi.compute.hostname";
    public static final String SPEED_ATTRIBUTE_NAME = "occi.compute.speed";
    public static final String MEMORY_ATTRIBUTE_NAME = "occi.compute.memory";
    public static final String STATE_ATTRIBUTE_NAME = "occi.compute.state";

    /**
     * Constructor.
     *
     * @param id occi.core.id attribute. Cannot be null.
     * @param kind compute's kind. Cannot be null.
     * @param title occi.core.title attribute
     * @param model compute's model
     * @param summary compute's summary
     * @throws InvalidAttributeValueException in case of invalid id, title or
     * summary value
     */
    public Compute(String id, Kind kind, String title, Model model, String summary) throws InvalidAttributeValueException {
        super(id, kind, title, model, summary);
    }

    /**
     * Constructor.
     *
     * @param id occi.core.id attribute. Cannot be null.
     * @param kind compute's kind. Cannot be null.
     * @throws InvalidAttributeValueException in case of invalid id value
     */
    public Compute(String id, Kind kind) throws InvalidAttributeValueException {
        super(id, kind);
    }

    /**
     * Returns compute's state (attribute occi.compute.state).
     *
     * @return compute's state
     */
    public String getState() {
        return getValue(STATE_ATTRIBUTE_NAME);
    }

    /**
     * Sets compute's state.
     *
     * @param state compute's state. Cannot be null.
     * @throws InvalidAttributeValueException in case state's value is invalid
     */
    public void setState(ComputeState state) throws InvalidAttributeValueException {
        if (state == null) {
            throw new NullPointerException("state cannot be null");
        }
        addAttribute(STATE_ATTRIBUTE_NAME, state.toString());
    }

    /**
     * Sets compute's state.
     *
     * @param stateName compute's state. Cannot be null.
     * @throws InvalidAttributeValueException in case state's value is invalid
     */
    public void setState(String stateName) throws InvalidAttributeValueException {
        addAttribute(STATE_ATTRIBUTE_NAME, stateName);
    }

    /**
     * Returns compute's memory (attribute occi.compute.memory).
     *
     * @return compute's memory
     */
    public String getMemory() {
        return getValue(MEMORY_ATTRIBUTE_NAME);
    }

    /**
     * Sets compute's memory.
     *
     * @param memory compute's memory
     * @throws InvalidAttributeValueException in case value of memory is invalid
     */
    public void setMemory(float memory) throws InvalidAttributeValueException {
        addAttribute(MEMORY_ATTRIBUTE_NAME, String.valueOf(memory));
    }

    /**
     * Sets compute's memory.
     *
     * @param memory compute's memory
     * @throws InvalidAttributeValueException in case value of memory is invalid
     */
    public void setMemory(String memory) throws InvalidAttributeValueException {
        addAttribute(MEMORY_ATTRIBUTE_NAME, memory);
    }

    /**
     * Returns compute's speed (attribute occi.compute.speed).
     *
     * @return compute's speed
     */
    public String getSpeed() {
        return getValue(SPEED_ATTRIBUTE_NAME);
    }

    /**
     * Sets compute's speed.
     *
     * @param speed compute's speed
     * @throws InvalidAttributeValueException in case value of speed is invalid
     */
    public void setSpeed(float speed) throws InvalidAttributeValueException {
        addAttribute(SPEED_ATTRIBUTE_NAME, String.valueOf(speed));
    }

    /**
     * Sets compute's speed.
     *
     * @param speed compute's speed
     * @throws InvalidAttributeValueException in case value of speed is invalid
     */
    public void setSpeed(String speed) throws InvalidAttributeValueException {
        addAttribute(SPEED_ATTRIBUTE_NAME, speed);
    }

    /**
     * Returns compute's hostname (attribute occi.compute.hostname).
     *
     * @return compute's hostname
     */
    public String getHostname() {
        return getValue(HOSTNAME_ATTRIBUTE_NAME);
    }

    /**
     * Sets compute's hostname.
     *
     * @param hostname compute's hostname
     * @throws InvalidAttributeValueException in case value of hostname is
     * invalid
     */
    public void setHostname(String hostname) throws InvalidAttributeValueException {
        addAttribute(HOSTNAME_ATTRIBUTE_NAME, hostname);
    }

    /**
     * Returns number of compute's cores (attribute occi.compute.cores).
     *
     * @return number of compute's cores
     */
    public String getCores() {
        return getValue(CORES_ATTRIBUTE_NAME);
    }

    /**
     * Sets number of compute's cores.
     *
     * @param cores number of compute's cores
     * @throws InvalidAttributeValueException in case value of cores is invalid
     */
    public void setCores(int cores) throws InvalidAttributeValueException {
        addAttribute(CORES_ATTRIBUTE_NAME, String.valueOf(cores));
    }

    /**
     * Sets number of compute's cores.
     *
     * @param cores number of compute's cores
     * @throws InvalidAttributeValueException in case value of cores is invalid
     */
    public void setCores(String cores) throws InvalidAttributeValueException {
        addAttribute(CORES_ATTRIBUTE_NAME, cores);
    }

    /**
     * Returns compute's architecture (attribute occi.compute.architecture).
     *
     * @return compute's architecture
     */
    public String getArchitecture() {
        return getValue(ARCHITECTURE_ATTRIBUTE_NAME);
    }

    /**
     * Sets compute's architecture
     *
     * @param architecture compute's architecture. Cannot be null.
     * @throws InvalidAttributeValueException in case value of architecture is
     * invalid
     */
    public void setArchitecture(Architecture architecture) throws InvalidAttributeValueException {
        if (architecture == null) {
            throw new NullPointerException("architecture cannot be null");
        }
        addAttribute(ARCHITECTURE_ATTRIBUTE_NAME, architecture.toString());
    }

    /**
     * Sets compute's architecture
     *
     * @param architectureName compute's architecture
     * @throws InvalidAttributeValueException in case value of architecture is
     * invalid
     */
    public void setArchitecture(String architectureName) throws InvalidAttributeValueException {
        addAttribute(ARCHITECTURE_ATTRIBUTE_NAME, architectureName);
    }

    /**
     * Returns compute's default scheme
     * 'http://schemas.ogf.org/occi/infrastructure'
     *
     * @return compute's default scheme
     */
    public static URI getSchemeDefault() {
        return Category.SCHEME_INFRASTRUCTURE_DEFAULT;
    }

    /**
     * Returns compute's default term 'compute'
     *
     * @return compute's default term
     */
    public static String getTermDefault() {
        return "compute";
    }
}
