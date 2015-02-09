package cz.cesnet.cloud.occi.infrastructure;

import cz.cesnet.cloud.occi.Model;
import cz.cesnet.cloud.occi.core.Kind;
import cz.cesnet.cloud.occi.exception.InvalidAttributeValueException;
import cz.cesnet.cloud.occi.infrastructure.enumeration.Allocation;
import java.net.URI;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class representing an OCCI IPNetwork
 *
 * @author Michal Kimle <kimle.michal@gmail.com>
 */
public class IPNetwork extends Network {

    private static final Logger LOGGER = LoggerFactory.getLogger(IPNetwork.class);

    /**
     *
     */
    public static final String ADDRESS_ATTRIBUTE_NAME = "occi.network.address";

    /**
     *
     */
    public static final String GATEWAY_ATTRIBUTE_NAME = "occi.network.gateway";

    /**
     *
     */
    public static final String ALLOCATION_ATTRIBUTE_NAME = "occi.network.allocation";

    /**
     *
     * @param id
     * @param kind
     * @param title
     * @param model
     * @param summary
     * @throws InvalidAttributeValueException
     */
    public IPNetwork(String id, Kind kind, String title, Model model, String summary) throws InvalidAttributeValueException {
        super(id, kind, title, model, summary);
    }

    /**
     *
     * @param id
     * @param kind
     * @throws InvalidAttributeValueException
     */
    public IPNetwork(String id, Kind kind) throws InvalidAttributeValueException {
        super(id, kind);
    }

    /**
     *
     * @return
     */
    public String getAddress() {
        return getValue(ADDRESS_ATTRIBUTE_NAME);
    }

    /**
     *
     * @param address
     * @throws InvalidAttributeValueException
     */
    public void setAddress(String address) throws InvalidAttributeValueException {
        addAttribute(ADDRESS_ATTRIBUTE_NAME, address);
    }

    /**
     *
     * @return
     */
    public String getGateway() {
        return getValue(GATEWAY_ATTRIBUTE_NAME);
    }

    /**
     *
     * @param gateway
     * @throws InvalidAttributeValueException
     */
    public void setGateway(String gateway) throws InvalidAttributeValueException {
        addAttribute(GATEWAY_ATTRIBUTE_NAME, gateway);
    }

    /**
     *
     * @return
     */
    public String getAllocation() {
        return getValue(ALLOCATION_ATTRIBUTE_NAME);
    }

    /**
     *
     * @param allocation
     * @throws InvalidAttributeValueException
     */
    public void setAllocation(Allocation allocation) throws InvalidAttributeValueException {
        addAttribute(ALLOCATION_ATTRIBUTE_NAME, allocation.toString());
    }

    /**
     *
     * @param allocationName
     * @throws InvalidAttributeValueException
     */
    public void setAllocation(String allocationName) throws InvalidAttributeValueException {
        addAttribute(ALLOCATION_ATTRIBUTE_NAME, allocationName);
    }

    /**
     *
     * @return
     */
    public static URI getSchemeDefault() {
        try {
            return new URI("http://schemas.ogf.org/occi/infrastructure/network#");
        } catch (URISyntaxException ex) {
            LOGGER.error("Wrong scheme URI", ex);
            return null;
        }
    }

    /**
     *
     * @return
     */
    public static String getTermDefault() {
        return "ipnetwork";
    }
}
