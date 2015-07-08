package cz.cesnet.cloud.occi.infrastructure;

import cz.cesnet.cloud.occi.Model;
import cz.cesnet.cloud.occi.core.Attribute;
import cz.cesnet.cloud.occi.core.Category;
import cz.cesnet.cloud.occi.core.Kind;
import cz.cesnet.cloud.occi.core.Link;
import cz.cesnet.cloud.occi.core.Mixin;
import cz.cesnet.cloud.occi.exception.InvalidAttributeValueException;
import cz.cesnet.cloud.occi.infrastructure.enumeration.StorageLinkState;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing an OCCI StorageLink
 *
 * @author Michal Kimle <kimle.michal@gmail.com>
 */
public class StorageLink extends Link {

    public static final String DEVICE_ID_ATTRIBUTE_NAME = "occi.storagelink.deviceid";
    public static final String MOUNTPOINT_ATTRIBUTE_NAME = "occi.storagelink.mountpoint";
    public static final String STATE_ATTRIBUTE_NAME = "occi.storagelink.state";
    public static final URI SCHEME_DEFAULT = Category.SCHEME_INFRASTRUCTURE_DEFAULT;
    public static final String TERM_DEFAULT = "storagelink";
    public static final String KIND_IDENTIFIER_DEFAULT = SCHEME_DEFAULT + TERM_DEFAULT;

    /**
     * Constructor.
     *
     * @param id occi.core.id attribute. Cannot be null.
     * @param kind storage link's kind. Cannot be null.
     * @param title occi.core.title attribute
     * @param model storage link's model
     * @throws InvalidAttributeValueException in case of invalid id or title
     * value
     */
    public StorageLink(String id, Kind kind, String title, Model model) throws InvalidAttributeValueException {
        super(id, kind, title, model);
    }

    /**
     * Constructor.
     *
     * @param id occi.core.id attribute. Cannot be null.
     * @param kind storage link's kind. Cannot be null.
     * @throws InvalidAttributeValueException in case of invalid id value
     */
    public StorageLink(String id, Kind kind) throws InvalidAttributeValueException {
        super(id, kind);
    }

    /**
     * Returns storage link's device id (attribute occi.storagelink.deviceid).
     *
     * @return storage link's device id
     */
    public String getDeviceId() {
        return getValue(DEVICE_ID_ATTRIBUTE_NAME);
    }

    /**
     * Sets storage link's device id.
     *
     * @param id storage link's device id
     * @throws InvalidAttributeValueException in case value for devide id is
     * invalid
     */
    public void setDeviceId(String id) throws InvalidAttributeValueException {
        addAttribute(DEVICE_ID_ATTRIBUTE_NAME, id);
    }

    /**
     * Returns storage link's mountpoint (attribute
     * occi.storagelink.mountpoint).
     *
     * @return storage link's mountpoint
     */
    public String getMountpoint() {
        return getValue(MOUNTPOINT_ATTRIBUTE_NAME);
    }

    /**
     * Sets storage link's mountpoint
     *
     * @param mountpoint storage link's mountpoint
     * @throws InvalidAttributeValueException in case value for moutnpoint is
     * invalid
     */
    public void setMountpoint(String mountpoint) throws InvalidAttributeValueException {
        addAttribute(MOUNTPOINT_ATTRIBUTE_NAME, mountpoint);
    }

    /**
     * Returns storage link's state (attribute occi.storagelink.state).
     *
     * @return storage link's state
     */
    public String getState() {
        return getValue(STATE_ATTRIBUTE_NAME);
    }

    /**
     * Sets storage link's state.
     *
     * @param state storage link's state. Cannot be null.
     * @throws InvalidAttributeValueException in case value for state is invalid
     */
    public void setState(StorageLinkState state) throws InvalidAttributeValueException {
        if (state == null) {
            throw new NullPointerException("state cannot be null");
        }
        addAttribute(STATE_ATTRIBUTE_NAME, state.toString());
    }

    /**
     * Sets storage link's state.
     *
     * @param stateName storage link's state
     * @throws InvalidAttributeValueException in case value for state is invalid
     */
    public void setState(String stateName) throws InvalidAttributeValueException {
        addAttribute(STATE_ATTRIBUTE_NAME, stateName);
    }

    /**
     * Returns storagelink's default identifier
     * 'http://schemas.ogf.org/occi/infrastructure#storagelink'
     *
     * @return storagelink's default identifier
     */
    @Override
    public String getDefaultKindIdentifier() {
        return KIND_IDENTIFIER_DEFAULT;
    }

    public static List<Attribute> getDefaultAttributes() {
        List<Attribute> list = new ArrayList<>();
        list.addAll(Storage.getDefaultAttributes());
        list.add(new Attribute(DEVICE_ID_ATTRIBUTE_NAME, true, false));
        list.add(new Attribute(MOUNTPOINT_ATTRIBUTE_NAME, false, false));
        list.add(new Attribute(STATE_ATTRIBUTE_NAME, true, true));

        return list;
    }

    public static Kind getDefaultKind() {
        Kind kind = new Kind(SCHEME_DEFAULT, TERM_DEFAULT, "Storage Link", URI.create("/storagelink/"), StorageLink.getDefaultAttributes());
        return kind;
    }
}
