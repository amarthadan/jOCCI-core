package cz.cesnet.cloud.occi.infrastructure;

import cz.cesnet.cloud.occi.Model;
import cz.cesnet.cloud.occi.core.Category;
import cz.cesnet.cloud.occi.core.Kind;
import cz.cesnet.cloud.occi.core.Resource;
import cz.cesnet.cloud.occi.exception.InvalidAttributeValueException;
import cz.cesnet.cloud.occi.infrastructure.enumeration.StorageState;
import java.net.URI;

/**
 * Class representing an OCCI Storage
 *
 * @author Michal Kimle <kimle.michal@gmail.com>
 */
public class Storage extends Resource {

    public static final String SIZE_ATTRIBUTE_NAME = "occi.storage.size";
    public static final String STATE_ATTRIBUTE_NAME = "occi.storage.state";

    /**
     *
     * @param id cannot be null
     * @param kind cannot be null
     * @param title
     * @param model
     * @param summary
     * @throws InvalidAttributeValueException
     */
    public Storage(String id, Kind kind, String title, Model model, String summary) throws InvalidAttributeValueException {
        super(id, kind, title, model, summary);
    }

    /**
     *
     * @param id cannot be null
     * @param kind cannot be null
     * @throws InvalidAttributeValueException
     */
    public Storage(String id, Kind kind) throws InvalidAttributeValueException {
        super(id, kind);
    }

    /**
     *
     * @return
     */
    public String getSize() {
        return getValue(SIZE_ATTRIBUTE_NAME);
    }

    /**
     *
     * @param size
     * @throws InvalidAttributeValueException
     */
    public void setSize(float size) throws InvalidAttributeValueException {
        addAttribute(SIZE_ATTRIBUTE_NAME, String.valueOf(size));
    }

    /**
     *
     * @param size
     * @throws InvalidAttributeValueException
     */
    public void setSize(String size) throws InvalidAttributeValueException {
        addAttribute(SIZE_ATTRIBUTE_NAME, size);
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
     * @param state cannot be null
     * @throws InvalidAttributeValueException
     */
    public void setState(StorageState state) throws InvalidAttributeValueException {
        if (state == null) {
            throw new NullPointerException("state cannot be null");
        }
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
        return "storage";
    }
}
