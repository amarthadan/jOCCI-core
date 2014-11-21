package cz.cesnet.cloud.occi.infrastructure;

import cz.cesnet.cloud.occi.Model;
import cz.cesnet.cloud.occi.core.Category;
import cz.cesnet.cloud.occi.core.Kind;
import cz.cesnet.cloud.occi.core.Link;
import cz.cesnet.cloud.occi.exception.InvalidAttributeValueException;
import cz.cesnet.cloud.occi.infrastructure.enumeration.NetworkState;
import java.net.URI;

public class NetworkInterface extends Link {

    public static final String INTERFACE_ATTRIBUTE_NAME = "occi.networkinterface.interface";
    public static final String MAC_ATTRIBUTE_NAME = "occi.networkinterface.mac";
    public static final String STATE_ATTRIBUTE_NAME = "occi.networkinterface.state";

    public NetworkInterface(String id, Kind kind, String title, Model model) throws InvalidAttributeValueException {
        super(id, kind, title, model);
    }

    public NetworkInterface(String id, Kind kind) throws InvalidAttributeValueException {
        super(id, kind);
    }

    public String getNetworkInterface() {
        return getValue(INTERFACE_ATTRIBUTE_NAME);
    }

    public void setNetworkInterface(String networkInterface) throws InvalidAttributeValueException {
        addAttribute(INTERFACE_ATTRIBUTE_NAME, networkInterface);
    }

    public String getMac() {
        return getValue(MAC_ATTRIBUTE_NAME);
    }

    public void setMac(String mac) throws InvalidAttributeValueException {
        addAttribute(MAC_ATTRIBUTE_NAME, mac);
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

    @Override
    public URI getSchemeDefault() {
        return Category.SCHEME_INFRASTRUCTURE_DEFAULT;
    }

    @Override
    public String getTermDefult() {
        return "networkinterface";
    }
}
