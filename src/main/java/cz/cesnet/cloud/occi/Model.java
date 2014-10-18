package cz.cesnet.cloud.occi;

import cz.cesnet.cloud.occi.core.*;
import java.util.List;

public class Model {

    private List<Kind> kinds;
    private List<Mixin> mixins;
    private List<Action> actions;

    public List<Kind> getKinds() {
        return this.kinds;
    }

    /**
     *
     * @param kinds
     */
    public void setKinds(List<Kind> kinds) {
        this.kinds = kinds;
    }

    public List<Mixin> getMixins() {
        return this.mixins;
    }

    /**
     *
     * @param mixins
     */
    public void setMixins(List<Mixin> mixins) {
        this.mixins = mixins;
    }

    public List<Action> getActions() {
        return this.actions;
    }

    /**
     *
     * @param actions
     */
    public void setActions(List<Action> actions) {
        this.actions = actions;
    }
}
