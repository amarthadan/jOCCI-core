package cz.cesnet.cloud.occi;

import cz.cesnet.cloud.occi.collection.SetCover;
import cz.cesnet.cloud.occi.core.ActionInstance;
import cz.cesnet.cloud.occi.core.Link;
import cz.cesnet.cloud.occi.core.Resource;
import java.util.Objects;
import java.util.Set;

/**
 * Class representing a collection of OCCI instances.
 *
 * @author Michal Kimle <kimle.michal@gmail.com>
 */
public class Collection {

    private final SetCover<Resource> resources = new SetCover<>();
    private final SetCover<Link> links = new SetCover<>();
    private final SetCover<ActionInstance> actions = new SetCover<>();
    private Model model;

    /**
     *
     * @param resource
     * @return
     */
    public boolean containsResource(Resource resource) {
        return resources.contains(resource);
    }

    /**
     *
     * @param resourceIdentifier
     * @return
     */
    public boolean containsResource(String resourceIdentifier) {
        return resources.contains(resourceIdentifier);
    }

    /**
     *
     * @param resource
     * @return
     */
    public boolean addResource(Resource resource) {
        return resources.add(resource);
    }

    /**
     *
     * @param resourceIdentifier
     * @return
     */
    public Resource getResource(String resourceIdentifier) {
        return resources.get(resourceIdentifier);
    }

    /**
     *
     * @param resource
     * @return
     */
    public boolean removeResource(Resource resource) {
        return resources.remove(resource);
    }

    /**
     *
     */
    public void clearResources() {
        resources.clear();
    }

    /**
     *
     * @return
     */
    public Set<Resource> getResources() {
        return resources.getSet();
    }

    /**
     *
     * @param link
     * @return
     */
    public boolean containsLink(Link link) {
        return links.contains(link);
    }

    /**
     *
     * @param linkIdentifier
     * @return
     */
    public boolean containsLink(String linkIdentifier) {
        return links.contains(linkIdentifier);
    }

    /**
     *
     * @param link
     * @return
     */
    public boolean addLink(Link link) {
        return links.add(link);
    }

    /**
     *
     * @param linkIdentifier
     * @return
     */
    public Link getLink(String linkIdentifier) {
        return links.get(linkIdentifier);
    }

    /**
     *
     * @param link
     * @return
     */
    public boolean removeLink(Link link) {
        return links.remove(link);
    }

    /**
     *
     */
    public void clearLinks() {
        links.clear();
    }

    /**
     *
     * @return
     */
    public Set<Link> getLinks() {
        return links.getSet();
    }

    /**
     *
     * @param action
     * @return
     */
    public boolean containsAction(ActionInstance action) {
        return actions.contains(action);
    }

    /**
     *
     * @param actionIdentifier
     * @return
     */
    public boolean containsAction(String actionIdentifier) {
        return actions.contains(actionIdentifier);
    }

    /**
     *
     * @param action
     * @return
     */
    public boolean addAction(ActionInstance action) {
        return actions.add(action);
    }

    /**
     *
     * @param actionIdentifier
     * @return
     */
    public ActionInstance getAction(String actionIdentifier) {
        return actions.get(actionIdentifier);
    }

    /**
     *
     * @param action
     * @return
     */
    public boolean removeAction(ActionInstance action) {
        return actions.remove(action);
    }

    /**
     *
     */
    public void clearActions() {
        actions.clear();
    }

    /**
     *
     * @return
     */
    public Set<ActionInstance> getActions() {
        return actions.getSet();
    }

    /**
     *
     * @param collection
     */
    public void merge(Collection collection) {
        resources.addAll(collection.getResources());
        links.addAll(collection.getLinks());
        actions.addAll(collection.getActions());
    }

    /**
     * Sets model for the whole collection (all the resources, links and actions
     * in the collection)
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
        for (ActionInstance ai : actions.getSet(true)) {
            ai.setModel(model);
        }
    }

    /**
     *
     * @return
     */
    public Model getModel() {
        return model;
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.resources);
        hash = 41 * hash + Objects.hashCode(this.links);
        return hash;
    }

    /**
     *
     * @param obj
     * @return
     */
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

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Collection{" + "resources=" + resources + ", links=" + links + ", model=" + model + '}';
    }
}
