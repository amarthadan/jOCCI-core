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
     *
     * @param id cannot be null
     * @param kind cannot be null
     * @param title
     * @param model
     * @param summary
     * @throws InvalidAttributeValueException
     */
    public Compute(String id, Kind kind, String title, Model model, String summary) throws InvalidAttributeValueException {
        super(id, kind, title, model, summary);
    }

    /**
     *
     * @param id cannot be null
     * @param kind cannot be null
     * @throws InvalidAttributeValueException
     */
    public Compute(String id, Kind kind) throws InvalidAttributeValueException {
        super(id, kind);
    }

    /**
     *
     * @return
     */
    public String getState() {
        return getValue(STATE_ATTRIBUTE_NAME);
    }

    /**
     *
     * @param state cannot be null
     * @throws InvalidAttributeValueException
     */
    public void setState(ComputeState state) throws InvalidAttributeValueException {
        if (state == null) {
            throw new NullPointerException("state cannot be null");
        }
        addAttribute(STATE_ATTRIBUTE_NAME, state.toString());
    }

    /**
     *
     * @param stateName
     * @throws InvalidAttributeValueException
     */
    public void setState(String stateName) throws InvalidAttributeValueException {
        addAttribute(STATE_ATTRIBUTE_NAME, stateName);
    }

    /**
     *
     * @return
     */
    public String getMemory() {
        return getValue(MEMORY_ATTRIBUTE_NAME);
    }

    /**
     *
     * @param memory
     * @throws InvalidAttributeValueException
     */
    public void setMemory(float memory) throws InvalidAttributeValueException {
        addAttribute(MEMORY_ATTRIBUTE_NAME, String.valueOf(memory));
    }

    /**
     *
     * @param memory
     * @throws InvalidAttributeValueException
     */
    public void setMemory(String memory) throws InvalidAttributeValueException {
        addAttribute(MEMORY_ATTRIBUTE_NAME, memory);
    }

    /**
     *
     * @return
     */
    public String getSpeed() {
        return getValue(SPEED_ATTRIBUTE_NAME);
    }

    /**
     *
     * @param speed
     * @throws InvalidAttributeValueException
     */
    public void setSpeed(float speed) throws InvalidAttributeValueException {
        addAttribute(SPEED_ATTRIBUTE_NAME, String.valueOf(speed));
    }

    /**
     *
     * @param speed
     * @throws InvalidAttributeValueException
     */
    public void setSpeed(String speed) throws InvalidAttributeValueException {
        addAttribute(SPEED_ATTRIBUTE_NAME, speed);
    }

    /**
     *
     * @return
     */
    public String getHostname() {
        return getValue(HOSTNAME_ATTRIBUTE_NAME);
    }

    /**
     *
     * @param hostname
     * @throws InvalidAttributeValueException
     */
    public void setHostname(String hostname) throws InvalidAttributeValueException {
        addAttribute(HOSTNAME_ATTRIBUTE_NAME, hostname);
    }

    /**
     *
     * @return
     */
    public String getCores() {
        return getValue(CORES_ATTRIBUTE_NAME);
    }

    /**
     *
     * @param cores
     * @throws InvalidAttributeValueException
     */
    public void setCores(int cores) throws InvalidAttributeValueException {
        addAttribute(CORES_ATTRIBUTE_NAME, String.valueOf(cores));
    }

    /**
     *
     * @param cores
     * @throws InvalidAttributeValueException
     */
    public void setCores(String cores) throws InvalidAttributeValueException {
        addAttribute(CORES_ATTRIBUTE_NAME, cores);
    }

    /**
     *
     * @return
     */
    public String getArchitecture() {
        return getValue(ARCHITECTURE_ATTRIBUTE_NAME);
    }

    /**
     *
     * @param architecture cannot be null
     * @throws InvalidAttributeValueException
     */
    public void setArchitecture(Architecture architecture) throws InvalidAttributeValueException {
        if (architecture == null) {
            throw new NullPointerException("architecture cannot be null");
        }
        addAttribute(ARCHITECTURE_ATTRIBUTE_NAME, architecture.toString());
    }

    /**
     *
     * @param architectureName
     * @throws InvalidAttributeValueException
     */
    public void setArchitecture(String architectureName) throws InvalidAttributeValueException {
        addAttribute(ARCHITECTURE_ATTRIBUTE_NAME, architectureName);
    }

    /**
     *
     * @return
     */
    public static URI getSchemeDefault() {
        return Category.SCHEME_INFRASTRUCTURE_DEFAULT;
    }

    /**
     *
     * @return
     */
    public static String getTermDefault() {
        return "compute";
    }
}
