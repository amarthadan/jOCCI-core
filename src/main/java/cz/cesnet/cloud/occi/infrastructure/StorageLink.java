package cz.cesnet.cloud.occi.infrastructure;

import cz.cesnet.cloud.occi.Model;
import cz.cesnet.cloud.occi.core.Category;
import cz.cesnet.cloud.occi.core.Kind;
import cz.cesnet.cloud.occi.core.Link;
import cz.cesnet.cloud.occi.exception.InvalidAttributeValueException;
import cz.cesnet.cloud.occi.infrastructure.enumeration.StorageLinkState;
import java.net.URI;

/**
 * Class representing an OCCI StorageLink
 *
 * @author Michal Kimle <kimle.michal@gmail.com>
 */
public class StorageLink extends Link {

    /**
     *
     */
    public static final String DEVICE_ID_ATTRIBUTE_NAME = "occi.storagelink.deviceid";

    /**
     *
     */
    public static final String MOUNTPOINT_ATTRIBUTE_NAME = "occi.storagelink.mountpoint";

    /**
     *
     */
    public static final String STATE_ATTRIBUTE_NAME = "occi.storagelink.state";

    /**
     *
     * @param id
     * @param kind
     * @param title
     * @param model
     * @throws InvalidAttributeValueException
     */
    public StorageLink(String id, Kind kind, String title, Model model) throws InvalidAttributeValueException {
        super(id, kind, title, model);
    }

    /**
     *
     * @param id
     * @param kind
     * @throws InvalidAttributeValueException
     */
    public StorageLink(String id, Kind kind) throws InvalidAttributeValueException {
        super(id, kind);
    }

    /**
     *
     * @return
     */
    public String getDeviceId() {
        return getValue(DEVICE_ID_ATTRIBUTE_NAME);
    }

    /**
     *
     * @param id
     * @throws InvalidAttributeValueException
     */
    public void setDeviceId(String id) throws InvalidAttributeValueException {
        addAttribute(DEVICE_ID_ATTRIBUTE_NAME, id);
    }

    /**
     *
     * @return
     */
    public String getMountpoint() {
        return getValue(MOUNTPOINT_ATTRIBUTE_NAME);
    }

    /**
     *
     * @param mountpoint
     * @throws InvalidAttributeValueException
     */
    public void setMountpoint(String mountpoint) throws InvalidAttributeValueException {
        addAttribute(MOUNTPOINT_ATTRIBUTE_NAME, mountpoint);
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
    public void setState(StorageLinkState state) throws InvalidAttributeValueException {
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
        return "storagelink";
    }
}
