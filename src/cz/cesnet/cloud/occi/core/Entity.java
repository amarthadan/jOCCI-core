package cz.cesnet.cloud.occi.core;

import cz.cesnet.cloud.occi.*;
import java.net.URI;
import java.util.List;
import java.util.Map;

public abstract class Entity {

    private URI id;
    private String title;
    private Kind kind;
    private List<Mixin> mixins;
    private Map<Attribute, Object> attributeValues;
    private Model model;

    public abstract void toText();

    public abstract void toJSON();
}
