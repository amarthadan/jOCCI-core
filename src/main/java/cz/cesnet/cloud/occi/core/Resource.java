package cz.cesnet.cloud.occi.core;

import cz.cesnet.cloud.occi.Model;
import cz.cesnet.cloud.occi.collection.SetCover;
import cz.cesnet.cloud.occi.exception.InvalidAttributeValueException;
import java.util.Set;

public class Resource extends Entity {

    public static final String SUMMARY_ATTRIBUTE_NAME = "occi.core.summary";

    private final SetCover<Link> links = new SetCover<>();

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

    @Override
    public void toText() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void toJSON() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
