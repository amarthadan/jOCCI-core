package cz.cesnet.cloud.occi;

import cz.cesnet.cloud.occi.core.*;
import java.util.List;

public class Collection {

    private List<Resource> resources;
    private List<Link> links;
    private Model model;

    public List<Resource> getResources() {
        return this.resources;
    }

    /**
     *
     * @param resources
     */
    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public List<Link> getLinks() {
        return this.links;
    }

    /**
     *
     * @param links
     */
    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public Model getModel() {
        return this.model;
    }

    /**
     *
     * @param model
     */
    public void setModel(Model model) {
        this.model = model;
    }
}
