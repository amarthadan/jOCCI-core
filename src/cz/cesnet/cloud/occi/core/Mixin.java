package cz.cesnet.cloud.occi.core;

import java.util.List;

public class Mixin extends Category {

    private List<Action> actions;
    private List<Mixin> related;
    private List<Entity> entities;
    private Category category;
}
