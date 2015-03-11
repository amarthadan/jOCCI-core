package cz.cesnet.cloud.occi;

import cz.cesnet.cloud.occi.collection.SetCover;
import cz.cesnet.cloud.occi.core.ActionInstance;
import cz.cesnet.cloud.occi.core.Link;
import cz.cesnet.cloud.occi.core.Resource;
import java.util.Objects;
import java.util.Set;

/**
 * Class representing a collection of OCCI instances. It can contain instances
 * of classes Resource, Link and ActionInstance. Collection can be assigned a
 * Model instance which will represent a OCCI model structure for all the
 * instances in the collection.
 *
 * @author Michal Kimle <kimle.michal@gmail.com>
 */
public class Collection {

    private final SetCover<Resource> resources = new SetCover<>();
    private final SetCover<Link> links = new SetCover<>();
    private final SetCover<ActionInstance> actions = new SetCover<>();
    private Model model;

    /**
     * Creates an empty collection instance.
     */
    public Collection() {
    }

    /**
     * Checks whether collection contains the resource.
     *
     * @param resource resource looked up in the collection
     * @return true if collection contains the resource, false otherwise
     */
    public boolean containsResource(Resource resource) {
        return resources.contains(resource);
    }

    /**
     * Checks whether collection contains the resource specified by its
     * identifier.
     *
     * @param resourceIdentifier identifier of the resource looked up in the
     * collection
     * @return true if collection contains the resource, false otherwise
     */
    public boolean containsResource(String resourceIdentifier) {
        return resources.contains(resourceIdentifier);
    }

    /**
     * Adds resource instance to the collection.
     *
     * @param resource resource to be added to the collection
     * @return true if the addition was successful, false otherwise
     */
    public boolean addResource(Resource resource) {
        return resources.add(resource);
    }

    /**
     * Retrieves the resource specified by its identifier from the collection.
     *
     * @param resourceIdentifier identifier of the retrieved resource
     * @return specified resource if in collection, null otherwise
     */
    public Resource getResource(String resourceIdentifier) {
        return resources.get(resourceIdentifier);
    }

    /**
     * Removes resource from the collection.
     *
     * @param resource resource instance to remove from the collection
     * @return true if the removal was successful, false otherwise
     */
    public boolean removeResource(Resource resource) {
        return resources.remove(resource);
    }

    /**
     * Removes all resources from the collection.
     */
    public void clearResources() {
        resources.clear();
    }

    /**
     * Returns all resources in the collection in form of a set.
     *
     * @return set of all resources in the collection
     */
    public Set<Resource> getResources() {
        return resources.getSet();
    }

    /**
     * Checks whether collection contains the link.
     *
     * @param link link looked up in the collection
     * @return true if collection contains the link, false otherwise
     */
    public boolean containsLink(Link link) {
        return links.contains(link);
    }

    /**
     * Checks whether collection contains the link specified by its identifier.
     *
     * @param linkIdentifier identifier of the link looked up in the collection
     * @return true if collection contains the link, false otherwise
     */
    public boolean containsLink(String linkIdentifier) {
        return links.contains(linkIdentifier);
    }

    /**
     * Adds link instance to the collection.
     *
     * @param link link to be added to the collection
     * @return true if the addition was successful, false otherwise
     */
    public boolean addLink(Link link) {
        return links.add(link);
    }

    /**
     * Retrieves the link specified by its identifier from the collection.
     *
     * @param linkIdentifier identifier of the retrieved link
     * @return specified link if in collection, null otherwise
     */
    public Link getLink(String linkIdentifier) {
        return links.get(linkIdentifier);
    }

    /**
     * Removes link from the collection.
     *
     * @param link link instance to remove from the collection
     * @return true if the removal was successful, false otherwise
     */
    public boolean removeLink(Link link) {
        return links.remove(link);
    }

    /**
     * Removes all links from the collection.
     */
    public void clearLinks() {
        links.clear();
    }

    /**
     * Returns all links in the collection in form of a set.
     *
     * @return set of all links in the collection
     */
    public Set<Link> getLinks() {
        return links.getSet();
    }

    /**
     * Checks whether collection contains the action.
     *
     * @param action action looked up in the collection
     * @return true if collection contains the action, false otherwise
     */
    public boolean containsAction(ActionInstance action) {
        return actions.contains(action);
    }

    /**
     * Checks whether collection contains the action specified by its
     * identifier.
     *
     * @param actionIdentifier identifier of the action looked up in the
     * collection
     * @return true if collection contains the action, false otherwise
     */
    public boolean containsAction(String actionIdentifier) {
        return actions.contains(actionIdentifier);
    }

    /**
     * Adds action instance to the collection.
     *
     * @param action action to be added to the collection
     * @return true if the addition was successful, false otherwise
     */
    public boolean addAction(ActionInstance action) {
        return actions.add(action);
    }

    /**
     * Retrieves the action specified by its identifier from the collection.
     *
     * @param actionIdentifier identifier of the retrieved action
     * @return specified action if in collection, null otherwise
     */
    public ActionInstance getAction(String actionIdentifier) {
        return actions.get(actionIdentifier);
    }

    /**
     * Removes action from the collection.
     *
     * @param action action instance to remove from the collection
     * @return true if the removal was successful, false otherwise
     */
    public boolean removeAction(ActionInstance action) {
        return actions.remove(action);
    }

    /**
     * Removes all actions from the collection.
     */
    public void clearActions() {
        actions.clear();
    }

    /**
     * Returns all actions in the collection in form of a set.
     *
     * @return set of all actions in the collection
     */
    public Set<ActionInstance> getActions() {
        return actions.getSet();
    }

    /**
     * Merges collection's content to the current collection.
     *
     * @param collection collection which content should be merged
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
     * @param model model to be set for this collection
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
     * Returns collection's model.
     *
     * @return collection's model
     */
    public Model getModel() {
        return model;
    }

    /**
     * @see Object#hashCode()
     * @return collection's hash code
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.resources);
        hash = 41 * hash + Objects.hashCode(this.links);
        return hash;
    }

    /**
     * @see Object#equals(java.lang.Object)
     * @param obj object to compare collection with
     * @return true if two collections are equal, false otherwise
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
        if (!Objects.equals(this.actions, other.actions)) {
            return false;
        }
        return true;
    }

    /**
     * Resturns string representation of the collection
     *
     * @see Object#toString()
     * @return string representation of the collection
     */
    @Override
    public String toString() {
        return "Collection{" + "resources=" + resources + ", links=" + links + ", model=" + model + '}';
    }
}
