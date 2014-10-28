package cz.cesnet.cloud.occi.infrastructure;

import cz.cesnet.cloud.occi.Model;
import cz.cesnet.cloud.occi.core.Kind;
import cz.cesnet.cloud.occi.core.Resource;
import cz.cesnet.cloud.occi.exception.InvalidAttributeException;
import cz.cesnet.cloud.occi.exception.InvalidAttributeValueException;
import cz.cesnet.cloud.occi.infrastructure.enumeration.NetworkState;
import java.net.URI;

public class Network extends Resource {

    public static final String VLAN_ATTRIBUTE_NAME = "occi.network.vlan";
    public static final String LABEL_ATTRIBUTE_NAME = "occi.network.label";
    public static final String STATE_ATTRIBUTE_NAME = "occi.network.state";

    public Network(URI id, Kind kind, String title, Model model, String summary) {
        super(id, kind, title, model, summary);
    }

    public Network(URI id, Kind kind) {
        super(id, kind);
    }

    public String getVlan() {
        return getValue(VLAN_ATTRIBUTE_NAME);
    }

    public void setVlan(int vlan) throws InvalidAttributeException, InvalidAttributeValueException {
        addAttribute(VLAN_ATTRIBUTE_NAME, String.valueOf(vlan));
    }

    public void setVlan(String vlan) throws InvalidAttributeException, InvalidAttributeValueException {
        addAttribute(VLAN_ATTRIBUTE_NAME, vlan);
    }

    public String getLabel() {
        return getValue(LABEL_ATTRIBUTE_NAME);
    }

    public void setLabel(String label) throws InvalidAttributeException, InvalidAttributeValueException {
        addAttribute(LABEL_ATTRIBUTE_NAME, label);
    }

    public String getState() {
        return getValue(STATE_ATTRIBUTE_NAME);
    }

    public void setState(NetworkState state) throws InvalidAttributeException, InvalidAttributeValueException {
        addAttribute(STATE_ATTRIBUTE_NAME, state.toString());
    }

    public void setState(String stateName) throws InvalidAttributeException, InvalidAttributeValueException {
        addAttribute(STATE_ATTRIBUTE_NAME, stateName);
    }
}
