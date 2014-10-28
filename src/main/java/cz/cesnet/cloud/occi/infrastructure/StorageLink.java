package cz.cesnet.cloud.occi.infrastructure;

import cz.cesnet.cloud.occi.Model;
import cz.cesnet.cloud.occi.core.Kind;
import cz.cesnet.cloud.occi.core.Link;
import cz.cesnet.cloud.occi.exception.InvalidAttributeException;
import cz.cesnet.cloud.occi.exception.InvalidAttributeValueException;
import cz.cesnet.cloud.occi.infrastructure.enumeration.StorageLinkState;
import java.net.URI;

public class StorageLink extends Link {

    public static final String DEVICE_ID_ATTRIBUTE_NAME = "occi.storagelink.deviceid";
    public static final String MOUNTPOINT_ATTRIBUTE_NAME = "occi.storagelink.mountpoint";
    public static final String STATE_ATTRIBUTE_NAME = "occi.storagelink.state";

    public StorageLink(URI id, Kind kind, String title, Model model) {
        super(id, kind, title, model);
    }

    public StorageLink(URI id, Kind kind) {
        super(id, kind);
    }

    public String getDeviceId() {
        return getValue(DEVICE_ID_ATTRIBUTE_NAME);
    }

    public void setDeviceId(String id) throws InvalidAttributeException, InvalidAttributeValueException {
        addAttribute(DEVICE_ID_ATTRIBUTE_NAME, id);
    }

    public String getMountpoint() {
        return getValue(MOUNTPOINT_ATTRIBUTE_NAME);
    }

    public void setMountpoint(String mountpoint) throws InvalidAttributeException, InvalidAttributeValueException {
        addAttribute(MOUNTPOINT_ATTRIBUTE_NAME, mountpoint);
    }

    public String getState() {
        return getValue(STATE_ATTRIBUTE_NAME);
    }

    public void setState(StorageLinkState state) throws InvalidAttributeException, InvalidAttributeValueException {
        addAttribute(STATE_ATTRIBUTE_NAME, state.toString());
    }

    public void setState(String stateName) throws InvalidAttributeException, InvalidAttributeValueException {
        addAttribute(STATE_ATTRIBUTE_NAME, stateName);
    }
}
