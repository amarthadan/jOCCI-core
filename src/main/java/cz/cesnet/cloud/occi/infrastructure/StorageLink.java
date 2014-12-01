package cz.cesnet.cloud.occi.infrastructure;

import cz.cesnet.cloud.occi.Model;
import cz.cesnet.cloud.occi.core.Category;
import cz.cesnet.cloud.occi.core.Kind;
import cz.cesnet.cloud.occi.core.Link;
import cz.cesnet.cloud.occi.exception.InvalidAttributeValueException;
import cz.cesnet.cloud.occi.infrastructure.enumeration.StorageLinkState;
import java.net.URI;

public class StorageLink extends Link {

    public static final String DEVICE_ID_ATTRIBUTE_NAME = "occi.storagelink.deviceid";
    public static final String MOUNTPOINT_ATTRIBUTE_NAME = "occi.storagelink.mountpoint";
    public static final String STATE_ATTRIBUTE_NAME = "occi.storagelink.state";

    public StorageLink(String id, Kind kind, String title, Model model) throws InvalidAttributeValueException {
        super(id, kind, title, model);
    }

    public StorageLink(String id, Kind kind) throws InvalidAttributeValueException {
        super(id, kind);
    }

    public String getDeviceId() {
        return getValue(DEVICE_ID_ATTRIBUTE_NAME);
    }

    public void setDeviceId(String id) throws InvalidAttributeValueException {
        addAttribute(DEVICE_ID_ATTRIBUTE_NAME, id);
    }

    public String getMountpoint() {
        return getValue(MOUNTPOINT_ATTRIBUTE_NAME);
    }

    public void setMountpoint(String mountpoint) throws InvalidAttributeValueException {
        addAttribute(MOUNTPOINT_ATTRIBUTE_NAME, mountpoint);
    }

    public String getState() {
        return getValue(STATE_ATTRIBUTE_NAME);
    }

    public void setState(StorageLinkState state) throws InvalidAttributeValueException {
        addAttribute(STATE_ATTRIBUTE_NAME, state.toString());
    }

    public void setState(String stateName) throws InvalidAttributeValueException {
        addAttribute(STATE_ATTRIBUTE_NAME, stateName);
    }

    public static URI getSchemeDefault() {
        return Category.SCHEME_INFRASTRUCTURE_DEFAULT;
    }

    public static String getTermDefault() {
        return "storagelink";
    }
}
