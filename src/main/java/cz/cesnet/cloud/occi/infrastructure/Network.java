package cz.cesnet.cloud.occi.infrastructure;

import cz.cesnet.cloud.occi.Model;
import cz.cesnet.cloud.occi.core.Category;
import cz.cesnet.cloud.occi.core.Kind;
import cz.cesnet.cloud.occi.core.Resource;
import cz.cesnet.cloud.occi.exception.InvalidAttributeValueException;
import cz.cesnet.cloud.occi.infrastructure.enumeration.NetworkState;
import java.net.URI;

public class Network extends Resource {

    public static final String VLAN_ATTRIBUTE_NAME = "occi.network.vlan";
    public static final String LABEL_ATTRIBUTE_NAME = "occi.network.label";
    public static final String STATE_ATTRIBUTE_NAME = "occi.network.state";

    public Network(String id, Kind kind, String title, Model model, String summary) throws InvalidAttributeValueException {
        super(id, kind, title, model, summary);
    }

    public Network(String id, Kind kind) throws InvalidAttributeValueException {
        super(id, kind);
    }

    public String getVlan() {
        return getValue(VLAN_ATTRIBUTE_NAME);
    }

    public void setVlan(int vlan) throws InvalidAttributeValueException {
        addAttribute(VLAN_ATTRIBUTE_NAME, String.valueOf(vlan));
    }

    public void setVlan(String vlan) throws InvalidAttributeValueException {
        addAttribute(VLAN_ATTRIBUTE_NAME, vlan);
    }

    public String getLabel() {
        return getValue(LABEL_ATTRIBUTE_NAME);
    }

    public void setLabel(String label) throws InvalidAttributeValueException {
        addAttribute(LABEL_ATTRIBUTE_NAME, label);
    }

    public String getState() {
        return getValue(STATE_ATTRIBUTE_NAME);
    }

    public void setState(NetworkState state) throws InvalidAttributeValueException {
        addAttribute(STATE_ATTRIBUTE_NAME, state.toString());
    }

    public void setState(String stateName) throws InvalidAttributeValueException {
        addAttribute(STATE_ATTRIBUTE_NAME, stateName);
    }

    public static URI getSchemeDefault() {
        return Category.SCHEME_INFRASTRUCTURE_DEFAULT;
    }

    public static String getTermDefult() {
        return "network";
    }
}
