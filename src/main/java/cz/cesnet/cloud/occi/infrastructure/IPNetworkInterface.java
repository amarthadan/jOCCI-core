package cz.cesnet.cloud.occi.infrastructure;

import cz.cesnet.cloud.occi.Model;
import cz.cesnet.cloud.occi.core.Kind;
import cz.cesnet.cloud.occi.exception.InvalidAttributeValueException;
import cz.cesnet.cloud.occi.infrastructure.enumeration.Allocation;
import java.net.URI;

/**
 * Class representing an OCCI IPNetworkInterface
 *
 * @author Michal Kimle <kimle.michal@gmail.com>
 */
public class IPNetworkInterface extends NetworkInterface {

    public static final String ADDRESS_ATTRIBUTE_NAME = "occi.networkinterface.address";
    public static final String GATEWAY_ATTRIBUTE_NAME = "occi.networkinterface.gateway";
    public static final String ALLOCATION_ATTRIBUTE_NAME = "occi.networkinterface.allocation";

    /**
     * Constructor.
     *
     * @param id occi.core.id attribute. Cannot be null.
     * @param kind ipnetwork interface's kind. Cannot be null.
     * @param title occi.core.title attribute
     * @param model network interface's model
     * @throws InvalidAttributeValueException in case of invalid id or title
     * value
     */
    public IPNetworkInterface(String id, Kind kind, String title, Model model) throws InvalidAttributeValueException {
        super(id, kind, title, model);
    }

    /**
     * Constructor.
     *
     * @param id occi.core.id attribute. Cannot be null.
     * @param kind ipnetwork interface's kind. Cannot be null.
     * @throws InvalidAttributeValueException in case of invalid id value
     */
    public IPNetworkInterface(String id, Kind kind) throws InvalidAttributeValueException {
        super(id, kind);
    }

    /**
     * Returns ipnetwork interface's address (attribute
     * occi.networkinterface.address).
     *
     * @return ipnetwork interface's address
     */
    public String getAddress() {
        return getValue(ADDRESS_ATTRIBUTE_NAME);
    }

    /**
     * Sets ipnetwork interface's address.
     *
     * @param address ipnetwork interface's address
     * @throws InvalidAttributeValueException in case value for address is
     * invalid
     */
    public void setAddress(String address) throws InvalidAttributeValueException {
        addAttribute(ADDRESS_ATTRIBUTE_NAME, address);
    }

    /**
     * Returns ipnetwork interface's gateway (attribute
     * occi.networkinterface.gateway).
     *
     * @return ipnetwork interface's gateway
     */
    public String getGateway() {
        return getValue(GATEWAY_ATTRIBUTE_NAME);
    }

    /**
     * Sets ipnetwork interface's gateway
     *
     * @param gateway ipnetwork interface's gateway
     * @throws InvalidAttributeValueException in case value for gateway is
     * invalid
     */
    public void setGateway(String gateway) throws InvalidAttributeValueException {
        addAttribute(GATEWAY_ATTRIBUTE_NAME, gateway);
    }

    /**
     * Returns ipnetwork interface's allocation (attribute
     * occi.networkinterface.allocation).
     *
     * @return ipnetwork interface's allocation
     */
    public String getAllocation() {
        return getValue(ALLOCATION_ATTRIBUTE_NAME);
    }

    /**
     * Sets ipnetwork interface's allocation.
     *
     * @param allocation ipnetwork interface's allocation. Cannot be null.
     * @throws InvalidAttributeValueException in case value for allocation is
     * invalid
     */
    public void setAllocation(Allocation allocation) throws InvalidAttributeValueException {
        if (allocation == null) {
            throw new NullPointerException("allocation cannot be null");
        }
        addAttribute(ALLOCATION_ATTRIBUTE_NAME, allocation.toString());
    }

    /**
     * Sets ipnetwork interface's allocation.
     *
     * @param allocationName ipnetwork interface's allocation. Cannot be null.
     * @throws InvalidAttributeValueException in case value for allocation is
     * invalid
     */
    public void setAllocation(String allocationName) throws InvalidAttributeValueException {
        addAttribute(ALLOCATION_ATTRIBUTE_NAME, allocationName);
    }

    /**
     * Returns ipnetwork interface's default scheme
     * 'http://schemas.ogf.org/occi/infrastructure/networkinterface#'
     *
     * @return ipnetwork interface's default scheme
     */
    public static URI getSchemeDefault() {
        return URI.create("http://schemas.ogf.org/occi/infrastructure/networkinterface#");
    }

    /**
     * Returns ipnetwork interface's default term 'ipnetworkinterface.'.
     *
     * @return ipnetwork interface's default term
     */
    public static String getTermDefault() {
        return "ipnetworkinterface.";
    }
}
