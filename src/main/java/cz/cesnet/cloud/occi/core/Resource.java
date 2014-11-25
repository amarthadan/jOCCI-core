package cz.cesnet.cloud.occi.core;

import cz.cesnet.cloud.occi.Model;
import cz.cesnet.cloud.occi.collection.SetCover;
import cz.cesnet.cloud.occi.exception.InvalidAttributeValueException;
import cz.cesnet.cloud.occi.exception.RenderingException;
import java.util.Set;

public class Resource extends Entity {

    public static final String SUMMARY_ATTRIBUTE_NAME = "occi.core.summary";

    private final SetCover<Link> links = new SetCover<>();
    private final SetCover<Action> actions = new SetCover<>();

    public Resource(String id, Kind kind, String title, Model model, String summary) throws InvalidAttributeValueException {
        super(id, kind, title, model);

        addAttribute(SUMMARY_ATTRIBUTE_NAME, summary);
    }

    public Resource(String id, Kind kind) throws InvalidAttributeValueException {
        super(id, kind);
    }

    public String getSummary() {
        return getValue(SUMMARY_ATTRIBUTE_NAME);
    }

    public void setSummary(String summary) throws InvalidAttributeValueException {
        addAttribute(SUMMARY_ATTRIBUTE_NAME, summary);
    }

    public boolean containsLink(Link link) {
        return links.contains(link);
    }

    public boolean containsLink(String linkIdentifier) {
        return links.contains(linkIdentifier);
    }

    public boolean addLink(Link link) {
        return links.add(link);
    }

    public Link getLink(String linkIdentifier) {
        return links.get(linkIdentifier);
    }

    public boolean removeLink(Link link) {
        return links.remove(link);
    }

    public void clearLinks() {
        links.clear();
    }

    public Set<Link> getLinks() {
        return links.getSet();
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

    public static String getTermDefult() {
        return "resource";
    }

    @Override
    public String toString() {
        return "Resource{" + "class=" + getClass().getName() + ", id=" + getId() + ", kind=" + getKind() + ", title=" + getTitle() + ", mixins=" + getMixins() + ", attributes=" + getAttributes() + ", links" + links + '}';
    }

    @Override
    public String toText() throws RenderingException {
        StringBuilder sb = new StringBuilder();

        sb.append(getKind().toText());

        for (Mixin m : getMixins()) {
            sb.append("\n");
            sb.append(m.toText());
        }

        String attributesString = attributesToPrefixText();
        if (!attributesString.isEmpty()) {
            sb.append("\n");
            sb.append(attributesString);
        }

        for (Link l : getLinks()) {
            sb.append("\n");
            sb.append(l.toText());
        }

        for (Action a : getActions()) {
            sb.append("\n");
            sb.append(a.toText(getKind().getLocation().toString() + getId()));
        }

        return sb.toString();
    }

    @Override
    public String toJSON() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
