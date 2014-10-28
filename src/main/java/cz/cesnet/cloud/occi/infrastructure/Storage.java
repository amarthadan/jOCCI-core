package cz.cesnet.cloud.occi.infrastructure;

import cz.cesnet.cloud.occi.Model;
import cz.cesnet.cloud.occi.core.Kind;
import cz.cesnet.cloud.occi.core.Resource;
import cz.cesnet.cloud.occi.exception.InvalidAttributeException;
import cz.cesnet.cloud.occi.exception.InvalidAttributeValueException;
import cz.cesnet.cloud.occi.infrastructure.enumeration.StorageState;
import java.net.URI;

public class Storage extends Resource {

    public static final String SIZE_ATTRIBUTE_NAME = "occi.storage.size";
    public static final String STATE_ATTRIBUTE_NAME = "occi.storage.state";

    public Storage(URI id, Kind kind, String title, Model model, String summary) {
        super(id, kind, title, model, summary);
    }

    public Storage(URI id, Kind kind) {
        super(id, kind);
    }

    public String getSize() {
        return getValue(SIZE_ATTRIBUTE_NAME);
    }

    public void setSize(float size) throws InvalidAttributeException, InvalidAttributeValueException {
        addAttribute(SIZE_ATTRIBUTE_NAME, String.valueOf(size));
    }

    public void setSize(String size) throws InvalidAttributeException, InvalidAttributeValueException {
        addAttribute(SIZE_ATTRIBUTE_NAME, size);
    }

    public String getState() {
        return getValue(STATE_ATTRIBUTE_NAME);
    }

    public void setState(StorageState state) throws InvalidAttributeException, InvalidAttributeValueException {
        addAttribute(STATE_ATTRIBUTE_NAME, state.toString());
    }

    public void setState(String stateName) throws InvalidAttributeException, InvalidAttributeValueException {
        addAttribute(STATE_ATTRIBUTE_NAME, stateName);
    }
}
