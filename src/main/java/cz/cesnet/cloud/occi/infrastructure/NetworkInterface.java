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

    /**
     *
     */
    public static final String INTERFACE_ATTRIBUTE_NAME = "occi.networkinterface.interface";

    /**
     *
     */
    public static final String MAC_ATTRIBUTE_NAME = "occi.networkinterface.mac";

    /**
     *
     */
    public static final String STATE_ATTRIBUTE_NAME = "occi.networkinterface.state";

    /**
     *
     * @param id
     * @param kind
     * @param title
     * @param model
     * @throws InvalidAttributeValueException
     */
    public NetworkInterface(String id, Kind kind, String title, Model model) throws InvalidAttributeValueException {
        super(id, kind, title, model);
    }

    /**
     *
     * @param id
     * @param kind
     * @throws InvalidAttributeValueException
     */
    public NetworkInterface(String id, Kind kind) throws InvalidAttributeValueException {
        super(id, kind);
    }

    /**
     *
     * @return
     */
    public String getNetworkInterface() {
        return getValue(INTERFACE_ATTRIBUTE_NAME);
    }

    /**
     *
     * @param networkInterface
     * @throws InvalidAttributeValueException
     */
    public void setNetworkInterface(String networkInterface) throws InvalidAttributeValueException {
        addAttribute(INTERFACE_ATTRIBUTE_NAME, networkInterface);
    }

    /**
     *
     * @return
     */
    public String getMac() {
        return getValue(MAC_ATTRIBUTE_NAME);
    }

    /**
     *
     * @param mac
     * @throws InvalidAttributeValueException
     */
    public void setMac(String mac) throws InvalidAttributeValueException {
        addAttribute(MAC_ATTRIBUTE_NAME, mac);
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
     * @param state
     * @throws InvalidAttributeValueException
     */
    public void setState(NetworkState state) throws InvalidAttributeValueException {
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
    public static URI getSchemeDefault() {
        return Category.SCHEME_INFRASTRUCTURE_DEFAULT;
    }

    /**
     *
     * @return
     */
    public static String getTermDefault() {
        return "networkinterface";
    }
}
