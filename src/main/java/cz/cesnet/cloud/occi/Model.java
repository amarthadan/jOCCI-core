package cz.cesnet.cloud.occi;

import cz.cesnet.cloud.occi.collection.SetCover;
import cz.cesnet.cloud.occi.core.Action;
import cz.cesnet.cloud.occi.core.Kind;
import cz.cesnet.cloud.occi.core.Mixin;
import java.util.Objects;
import java.util.Set;

public class Model {

    private final SetCover<Kind> kinds = new SetCover<>();
    private final SetCover<Mixin> mixins = new SetCover<>();
    private final SetCover<Action> actions = new SetCover<>();

    public boolean containsKind(Kind kind) {
        return kinds.contains(kind);
    }

    public boolean containsKind(String kindIdentifier) {
        return kinds.contains(kindIdentifier);
    }

    public boolean addKind(Kind kind) {
        return kinds.add(kind);
    }

    public Kind getKind(String kindIdentifier) {
        return kinds.get(kindIdentifier);
    }

    public boolean removeKind(Kind kind) {
        return kinds.remove(kind);
    }

    public void clearKinds() {
        kinds.clear();
    }

    public Set<Kind> getKinds() {
        return kinds.getSet();
    }

    public boolean containsMixin(Mixin mixin) {
        return mixins.contains(mixin);
    }

    public boolean containsMixin(String mixinIdentifier) {
        return mixins.contains(mixinIdentifier);
    }

    public boolean addMixin(Mixin mixin) {
        return mixins.add(mixin);
    }

    public Mixin getMixin(String mixinIdentifier) {
        return mixins.get(mixinIdentifier);
    }

    public boolean removeMixin(Mixin mixin) {
        return mixins.remove(mixin);
    }

    public void clearMixins() {
        mixins.clear();
    }

    public Set<Mixin> getMixins() {
        return mixins.getSet();
    }

    public boolean containsAction(Action action) {
        return actions.contains(action);
    }

    public boolean containsAction(String actionIdentifier) {
        return actions.contains(actionIdentifier);
    }

    public boolean addAction(Action action) {
        return actions.add(action);
    }

    public Action getAction(String actionIdentifier) {
        return actions.get(actionIdentifier);
    }

    public boolean removeAction(Action action) {
        return actions.remove(action);
    }

    public void clearActions() {
        actions.clear();
    }

    public Set<Action> getActions() {
        return actions.getSet();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.kinds);
        hash = 59 * hash + Objects.hashCode(this.mixins);
        hash = 59 * hash + Objects.hashCode(this.actions);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Model other = (Model) obj;
        if (!Objects.equals(this.kinds, other.kinds)) {
            return false;
        }
        if (!Objects.equals(this.mixins, other.mixins)) {
            return false;
        }
        if (!Objects.equals(this.actions, other.actions)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model{" + "kinds=" + kinds + ", mixins=" + mixins + ", actions=" + actions + '}';
    }
}
