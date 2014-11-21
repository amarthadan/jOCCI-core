package cz.cesnet.cloud.occi.infrastructure;

import cz.cesnet.cloud.occi.Model;
import cz.cesnet.cloud.occi.core.Category;
import cz.cesnet.cloud.occi.core.Kind;
import cz.cesnet.cloud.occi.core.Resource;
import cz.cesnet.cloud.occi.exception.InvalidAttributeValueException;
import cz.cesnet.cloud.occi.infrastructure.enumeration.ComputeState;
import cz.cesnet.cloud.occi.infrastructure.enumeration.Architecture;
import java.net.URI;

public class Compute extends Resource {

    public static final String ARCHITECTURE_ATTRIBUTE_NAME = "occi.compute.architecture";
    public static final String CORES_ATTRIBUTE_NAME = "occi.compute.cores";
    public static final String HOSTNAME_ATTRIBUTE_NAME = "occi.compute.hostname";
    public static final String SPEED_ATTRIBUTE_NAME = "occi.compute.speed";
    public static final String MEMORY_ATTRIBUTE_NAME = "occi.compute.memory";
    public static final String STATE_ATTRIBUTE_NAME = "occi.compute.state";

    public Compute(String id, Kind kind, String title, Model model, String summary) throws InvalidAttributeValueException {
        super(id, kind, title, model, summary);
    }

    public Compute(String id, Kind kind) throws InvalidAttributeValueException {
        super(id, kind);
    }

    public String getState() {
        return getValue(STATE_ATTRIBUTE_NAME);
    }

    public void setState(ComputeState state) throws InvalidAttributeValueException {
        addAttribute(STATE_ATTRIBUTE_NAME, state.toString());
    }

    public void setState(String stateName) throws InvalidAttributeValueException {
        addAttribute(STATE_ATTRIBUTE_NAME, stateName);
    }

    public String getMemory() {
        return getValue(MEMORY_ATTRIBUTE_NAME);
    }

    public void setMemory(float memory) throws InvalidAttributeValueException {
        addAttribute(MEMORY_ATTRIBUTE_NAME, String.valueOf(memory));
    }

    public void setMemory(String memory) throws InvalidAttributeValueException {
        addAttribute(MEMORY_ATTRIBUTE_NAME, memory);
    }

    public String getSpeed() {
        return getValue(SPEED_ATTRIBUTE_NAME);
    }

    public void setSpeed(float speed) throws InvalidAttributeValueException {
        addAttribute(SPEED_ATTRIBUTE_NAME, String.valueOf(speed));
    }

    public void setSpeed(String speed) throws InvalidAttributeValueException {
        addAttribute(SPEED_ATTRIBUTE_NAME, speed);
    }

    public String getHostname() {
        return getValue(HOSTNAME_ATTRIBUTE_NAME);
    }

    public void setHostname(String hostname) throws InvalidAttributeValueException {
        addAttribute(HOSTNAME_ATTRIBUTE_NAME, hostname);
    }

    public String getCores() {
        return getValue(CORES_ATTRIBUTE_NAME);
    }

    public void setCores(int cores) throws InvalidAttributeValueException {
        addAttribute(CORES_ATTRIBUTE_NAME, String.valueOf(cores));
    }

    public void setCores(String cores) throws InvalidAttributeValueException {
        addAttribute(CORES_ATTRIBUTE_NAME, cores);
    }

    public String getArchitecture() {
        return getValue(ARCHITECTURE_ATTRIBUTE_NAME);
    }

    public void setArchitecture(Architecture architecture) throws InvalidAttributeValueException {
        addAttribute(ARCHITECTURE_ATTRIBUTE_NAME, architecture.toString());
    }

    public void setArchitecture(String architectureName) throws InvalidAttributeValueException {
        addAttribute(ARCHITECTURE_ATTRIBUTE_NAME, architectureName);
    }

    @Override
    public URI getSchemeDefault() {
        return Category.SCHEME_INFRASTRUCTURE_DEFAULT;
    }

    @Override
    public String getTermDefult() {
        return "compute";
    }
}
