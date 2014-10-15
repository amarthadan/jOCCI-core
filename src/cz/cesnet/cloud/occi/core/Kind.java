package cz.cesnet.cloud.occi.core;

import java.util.List;

public class Kind extends Category {

    private List<Action> actions;
    private List<Kind> related;
    private String entityType;
    private List<Entity> entities;
    private Category category;
}
