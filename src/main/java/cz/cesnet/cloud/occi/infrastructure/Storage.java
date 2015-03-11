package cz.cesnet.cloud.occi.infrastructure;

import cz.cesnet.cloud.occi.Model;
import cz.cesnet.cloud.occi.core.Category;
import cz.cesnet.cloud.occi.core.Kind;
import cz.cesnet.cloud.occi.core.Resource;
import cz.cesnet.cloud.occi.exception.InvalidAttributeValueException;
import cz.cesnet.cloud.occi.infrastructure.enumeration.StorageState;
import java.net.URI;

/**
 * Class representing an OCCI Storage.
 *
 * @author Michal Kimle <kimle.michal@gmail.com>
 */
public class Storage extends Resource {

    public static final String SIZE_ATTRIBUTE_NAME = "occi.storage.size";
    public static final String STATE_ATTRIBUTE_NAME = "occi.storage.state";

    /**
     * Constructor.
     *
     * @param id occi.core.id attribute. Cannot be null.
     * @param kind storage's kind. Cannot be null.
     * @param title occi.core.title attribute
     * @param model storage's model
     * @param summary storage's summary
     * @throws InvalidAttributeValueException in case of invalid id, title or
     * summary value
     */
    public Storage(String id, Kind kind, String title, Model model, String summary) throws InvalidAttributeValueException {
        super(id, kind, title, model, summary);
    }

    /**
     * Constructor.
     *
     * @param id occi.core.id attribute. Cannot be null.
     * @param kind storage's kind. Cannot be null.
     * @throws InvalidAttributeValueException in case of invalid id value
     */
    public Storage(String id, Kind kind) throws InvalidAttributeValueException {
        super(id, kind);
    }

    /**
     * Returns storage's size (attribute occi.storage.size).
     *
     * @return storage's size
     */
    public String getSize() {
        return getValue(SIZE_ATTRIBUTE_NAME);
    }

    /**
     * Sets storage's size.
     *
     * @param size storage's size
     * @throws InvalidAttributeValueException in case value for size is invalid
     */
    public void setSize(float size) throws InvalidAttributeValueException {
        addAttribute(SIZE_ATTRIBUTE_NAME, String.valueOf(size));
    }

    /**
     * Sets storage's size.
     *
     * @param size storage's size
     * @throws InvalidAttributeValueException in case value for size is invalid
     */
    public void setSize(String size) throws InvalidAttributeValueException {
        addAttribute(SIZE_ATTRIBUTE_NAME, size);
    }

    /**
     * Returns storage's state (attribute occi.storage.state).
     *
     * @return storage's state
     */
    public String getState() {
        return getValue(STATE_ATTRIBUTE_NAME);
    }

    /**
     * Sets storage's state.
     *
     * @param state storage's state. Cannot be null.
     * @throws InvalidAttributeValueException in case value for state is invalid
     */
    public void setState(StorageState state) throws InvalidAttributeValueException {
        if (state == null) {
            throw new NullPointerException("state cannot be null");
        }
        addAttribute(STATE_ATTRIBUTE_NAME, state.toString());
    }

    /**
     * Sets storage's state.
     *
     * @param stateName storage's state
     * @throws InvalidAttributeValueException in case value for state is invalid
     */
    public void setState(String stateName) throws InvalidAttributeValueException {
        addAttribute(STATE_ATTRIBUTE_NAME, stateName);
    }

    /**
     * Returns storage's default scheme
     * 'http://schemas.ogf.org/occi/infrastructure#'
     *
     * @return storage's default scheme
     */
    public static URI getSchemeDefault() {
        return Category.SCHEME_INFRASTRUCTURE_DEFAULT;
    }

    /**
     * Returns storage's default term 'storage'.
     *
     * @return storage's default term
     */
    public static String getTermDefault() {
        return "storage";
    }
}
