package cz.cesnet.cloud.occi.core;

import cz.cesnet.cloud.occi.Model;
import cz.cesnet.cloud.occi.collection.SetCover;
import java.net.URI;
import java.util.Set;

public class Resource extends Entity {

    private String summary;
    private final SetCover<Link> links = new SetCover<>();

    public Resource(URI id, Kind kind, String title, Model model, String summary) {
        super(id, kind, title, model);

        this.summary = summary;
    }

    public Resource(URI id, Kind kind) {
        super(id, kind);
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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
