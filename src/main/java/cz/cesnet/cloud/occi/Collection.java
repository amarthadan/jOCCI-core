package cz.cesnet.cloud.occi;

import cz.cesnet.cloud.occi.collection.SetCover;
import cz.cesnet.cloud.occi.core.Link;
import cz.cesnet.cloud.occi.core.Resource;
import java.util.Objects;
import java.util.Set;

public class Collection {

    private final SetCover<Resource> resources = new SetCover<>();
    private final SetCover<Link> links = new SetCover<>();
    private Model model;

    public boolean containsResource(Resource resource) {
        return resources.contains(resource);
    }

    public boolean containsResource(String resourceIdentifier) {
        return resources.contains(resourceIdentifier);
    }

    public boolean addResource(Resource resource) {
        return resources.add(resource);
    }

    public Resource getResource(String resourceIdentifier) {
        return resources.get(resourceIdentifier);
    }

    public boolean removeResource(Resource resource) {
        return resources.remove(resource);
    }

    public void clearResources() {
        resources.clear();
    }

    public Set<Resource> getResources() {
        return resources.getSet();
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

    /**
     * Sets model for the whole collection (all the resources and links in the
     * collection)
     *
     * @param model
     */
    public void setModel(Model model) {
        this.model = model;
        for (Link link : links.getSet(true)) {
            link.setModel(model);
        }
        for (Resource resource : resources.getSet(true)) {
            resource.setModel(model);
        }
    }

    public Model getModel() {
        return model;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.resources);
        hash = 41 * hash + Objects.hashCode(this.links);
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
        final Collection other = (Collection) obj;
        if (!Objects.equals(this.resources, other.resources)) {
            return false;
        }
        if (!Objects.equals(this.links, other.links)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Collection{" + "resources=" + resources + ", links=" + links + ", model=" + model + '}';
    }
}
