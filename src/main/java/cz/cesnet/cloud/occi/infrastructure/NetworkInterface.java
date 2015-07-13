package cz.cesnet.cloud.occi.infrastructure;

import cz.cesnet.cloud.occi.Model;
import cz.cesnet.cloud.occi.core.Attribute;
import cz.cesnet.cloud.occi.core.Category;
import cz.cesnet.cloud.occi.core.Kind;
import cz.cesnet.cloud.occi.core.Link;
import cz.cesnet.cloud.occi.exception.InvalidAttributeValueException;
import cz.cesnet.cloud.occi.infrastructure.enumeration.NetworkState;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing an OCCI NetworkInterface
 *
 * @author Michal Kimle <kimle.michal@gmail.com>
 */
public class NetworkInterface extends Link {

    public static final String INTERFACE_ATTRIBUTE_NAME = "occi.networkinterface.interface";
    public static final String MAC_ATTRIBUTE_NAME = "occi.networkinterface.mac";
    public static final String STATE_ATTRIBUTE_NAME = "occi.networkinterface.state";
    public static final URI SCHEME_DEFAULT = Category.SCHEME_INFRASTRUCTURE_DEFAULT;
    public static final String TERM_DEFAULT = "networkinterface";
    public static final String KIND_IDENTIFIER_DEFAULT = SCHEME_DEFAULT + TERM_DEFAULT;

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
     * Returns networkinterface's default identifier
     * 'http://schemas.ogf.org/occi/infrastructure#networkinterface'
     *
     * @return networkinterface's default identifier
     */
    @Override
    public String getDefaultKindIdentifier() {
        return KIND_IDENTIFIER_DEFAULT;
    }

    /**
     * Returns networkinterface's default attributes. For NetworkInterface class
     * those are attributes occi.networkinterface.interface,
     * occi.networkinterface.mac and occi.networkinterface.state.
     *
     * @return list of networkinterface's default attributes
     */
    public static List<Attribute> getDefaultAttributes() {
        List<Attribute> list = new ArrayList<>();
        list.addAll(Link.getDefaultAttributes());
        list.add(new Attribute(INTERFACE_ATTRIBUTE_NAME, true, true));
        list.add(new Attribute(MAC_ATTRIBUTE_NAME, true, false));
        list.add(new Attribute(STATE_ATTRIBUTE_NAME, true, true));

        return list;
    }

    /**
     * Returns networkinterface's default kind instance.
     *
     * @return networkinterface's default kind
     */
    public static Kind getDefaultKind() {
        Kind kind = new Kind(SCHEME_DEFAULT, TERM_DEFAULT, "Networkinterface", URI.create("/networkinterface/"), NetworkInterface.getDefaultAttributes());
        return kind;
    }
}
