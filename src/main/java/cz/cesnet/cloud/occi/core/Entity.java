package cz.cesnet.cloud.occi.core;

import cz.cesnet.cloud.occi.type.Identifiable;
import cz.cesnet.cloud.occi.*;
import java.net.URI;
import java.util.List;
import java.util.Map;

public abstract class Entity implements Identifiable {

    private URI id;
    private String title;
    private Kind kind;
    private List<Mixin> mixins;
    private Map<Attribute, Object> attributeValues;
    private Model model;

    @Override
    public String getIdentifier() {
        return kind.getIdentifier() + id;
    }

    public abstract void toText();

    public abstract void toJSON();
}
