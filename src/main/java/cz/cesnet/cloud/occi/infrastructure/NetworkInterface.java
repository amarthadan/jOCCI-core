package cz.cesnet.cloud.occi.infrastructure;

import cz.cesnet.cloud.occi.Model;
import cz.cesnet.cloud.occi.core.Category;
import cz.cesnet.cloud.occi.core.Kind;
import cz.cesnet.cloud.occi.core.Link;
import cz.cesnet.cloud.occi.exception.InvalidAttributeValueException;
import cz.cesnet.cloud.occi.infrastructure.enumeration.NetworkState;
import java.net.URI;

/**
 * Class representing an OCCI NetworkInterface
 *
 * @author Michal Kimle <kimle.michal@gmail.com>
 */
public class NetworkInterface extends Link {

    public static final String INTERFACE_ATTRIBUTE_NAME = "occi.networkinterface.interface";
    public static final String MAC_ATTRIBUTE_NAME = "occi.networkinterface.mac";
    public static final String STATE_ATTRIBUTE_NAME = "occi.networkinterface.state";

    /**
     * Constructor.
     *
     * @param id occi.core.id attribute. Cannot be null.
     * @param kind network interface's kind. Cannot be null.
     * @param title occi.core.title attribute
     * @param model network interface's model
     * @throws InvalidAttributeValueException in case of invalid id or title
     * value
     */
    public NetworkInterface(String id, Kind kind, String title, Model model) throws InvalidAttributeValueException {
        super(id, kind, title, model);
    }

    /**
     * Constructor.
     *
     * @param id occi.core.id attribute. Cannot be null.
     * @param kind network interface's kind. Cannot be null.
     * @throws InvalidAttributeValueException in case of invalid id value
     */
    public NetworkInterface(String id, Kind kind) throws InvalidAttributeValueException {
        super(id, kind);
    }

    /**
     * Returns network interface's interface (attribute
     * occi.networkinterface.interface).
     *
     * @return network interface's interface
     */
    public String getNetworkInterface() {
        return getValue(INTERFACE_ATTRIBUTE_NAME);
    }

    /**
     * Sets network interface's interface
     *
     * @param networkInterface network interface's interface
     * @throws InvalidAttributeValueException in case value for interface is
     * invalid
     */
    public void setNetworkInterface(String networkInterface) throws InvalidAttributeValueException {
        addAttribute(INTERFACE_ATTRIBUTE_NAME, networkInterface);
    }

    /**
     * Returns network interface's mac address.
     *
     * @return network interface's mac address
     */
    public String getMac() {
        return getValue(MAC_ATTRIBUTE_NAME);
    }

    /**
     * Sets network interface's mac address
     *
     * @param mac network interface's mac address
     * @throws InvalidAttributeValueException in case value for mac address is
     * invalid
     */
    public void setMac(String mac) throws InvalidAttributeValueException {
        addAttribute(MAC_ATTRIBUTE_NAME, mac);
    }

    /**
     * Returns network interface's state.
     *
     * @return network interface's state
     */
    public String getState() {
        return getValue(STATE_ATTRIBUTE_NAME);
    }

    /**
     * Sets network interface's state.
     *
     * @param state network interface's state. Cannot be null.
     * @throws InvalidAttributeValueException in case value for state is invalid
     */
    public void setState(NetworkState state) throws InvalidAttributeValueException {
        if (state == null) {
            throw new NullPointerException("state cannot be null");
        }
        addAttribute(STATE_ATTRIBUTE_NAME, state.toString());
    }

    /**
     * Sets network interface's state.
     *
     * @param stateName network interface's state. Cannot be null.
     * @throws InvalidAttributeValueException in case value for state is invalid
     */
    public void setState(String stateName) throws InvalidAttributeValueException {
        addAttribute(STATE_ATTRIBUTE_NAME, stateName);
    }

    /**
     * Returns network interface's default scheme
     * 'http://schemas.ogf.org/occi/infrastructure#'.
     *
     * @return network interface's default scheme
     */
    public static URI getSchemeDefault() {
        return Category.SCHEME_INFRASTRUCTURE_DEFAULT;
    }

    /**
     * Returns network interface's default term 'networkinterface'.
     *
     * @return network interface's default term
     */
    public static String getTermDefault() {
        return "networkinterface";
    }
}
