package cz.cesnet.cloud.occi.core;

import com.sun.net.httpserver.Headers;
import cz.cesnet.cloud.occi.Model;
import cz.cesnet.cloud.occi.collection.SetCover;
import cz.cesnet.cloud.occi.exception.InvalidAttributeValueException;
import cz.cesnet.cloud.occi.exception.RenderingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.slf4j.LoggerFactory;

/**
 * Class representing an OCCI Resource.
 *
 * @author Michal Kimle <kimle.michal@gmail.com>
 */
public class Resource extends Entity {

    public static final String SUMMARY_ATTRIBUTE_NAME = "occi.core.summary";
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Resource.class);
    private final SetCover<Link> links = new SetCover<>();
    private final SetCover<Action> actions = new SetCover<>();

    /**
     * Constructor.
     *
     * @param id occi.core.id attribute. Cannot be null.
     * @param kind resource's kind. Cannot be null.
     * @param title occi.core.title attribute
     * @param model resource's model
     * @param summary resource's summary
     * @throws InvalidAttributeValueException in case of invalid id, title or
     * summary value
     */
    public Resource(String id, Kind kind, String title, Model model, String summary) throws InvalidAttributeValueException {
        super(id, kind, title, model);

        addAttribute(SUMMARY_ATTRIBUTE_NAME, summary);
    }

    /**
     * Constructor.
     *
     * @param id occi.core.id attribute. Cannot be null.
     * @param kind resource's kind. Cannot be null.
     * @throws InvalidAttributeValueException in case of invalid id value
     */
    public Resource(String id, Kind kind) throws InvalidAttributeValueException {
        super(id, kind);
    }

    /**
     * Returns resource's summary.
     *
     * @return resource's summary
     */
    public String getSummary() {
        return getValue(SUMMARY_ATTRIBUTE_NAME);
    }

    /**
     * Sets resource's summary.
     *
     * @param summary resource's summary
     * @throws InvalidAttributeValueException in case of invalid summary value
     */
    public void setSummary(String summary) throws InvalidAttributeValueException {
        addAttribute(SUMMARY_ATTRIBUTE_NAME, summary);
    }

    /**
     * Checks whether resource contains given link.
     *
     * @param link link to be looked up
     * @return true is resource contains given link, false otherwise
     */
    public boolean containsLink(Link link) {
        return links.contains(link);
    }

    /**
     * Checks whether resource contains given link.
     *
     * @param linkIdentifier identifier of link to be looked up
     * @return true is resource contains given link, false otherwise
     */
    public boolean containsLink(String linkIdentifier) {
        return links.contains(linkIdentifier);
    }

    /**
     * Adds link to the resource and automatically sets link's source to
     * resource.
     *
     * @param link to be added
     * @return true if addition was successful, false otherwise
     */
    public boolean addLink(Link link) {
        if (link.getSource() == null) {
            try {
                link.setSource(this);
            } catch (InvalidAttributeValueException ex) {
                LOGGER.error("This should not be happening!");
            }
        }
        return links.add(link);
    }

    /**
     * Adds links to the resource and automatically sets all links' sources to
     * resource.
     *
     * @param links collections of links to be added
     * @return true if addition was successful, false otherwise
     */
    public boolean addLinks(Collection<Link> links) {
        for (Link link : links) {
            if (link.getSource() == null) {
                try {
                    link.setSource(this);
                } catch (InvalidAttributeValueException ex) {
                    LOGGER.error("This should not be happening!");
                }
            }
        }
        return this.links.addAll(links);
    }

    /**
     * Returns resource's link.
     *
     * @param linkIdentifier identifier of requested link
     * @return resource's link
     */
    public Link getLink(String linkIdentifier) {
        return links.get(linkIdentifier);
    }

    /**
     * Removes link from resource.
     *
     * @param link link to be removed
     * @return true if the removal was successful, false otherwise
     */
    public boolean removeLink(Link link) {
        return links.remove(link);
    }

    /**
     * Removes all links from resource.
     */
    public void clearLinks() {
        links.clear();
    }

    /**
     * Returns all resource's links in form of set.
     *
     * @return all resource's links in form of set
     */
    public Set<Link> getLinks() {
        return links.getSet();
    }

    /**
     * Checks whether resource contains given action.
     *
     * @param action action to be looked up
     * @return true is resource contains given action, false otherwise
     */
    public boolean containsAction(Action action) {
        return actions.contains(action);
    }

    /**
     * Checks whether resource contains given action.
     *
     * @param actionIdentifier identifier of action to be looked up
     * @return true is resource contains given action, false otherwise
     */
    public boolean containsAction(String actionIdentifier) {
        return actions.contains(actionIdentifier);
    }

    /**
     * Adds action to the resource.
     *
     * @param action to be added
     * @return true if addition was successful, false otherwise
     */
    public boolean addAction(Action action) {
        return actions.add(action);
    }

    /**
     * Adds actions to the resource.
     *
     * @param actions collections of actions to be added
     * @return true if addition was successful, false otherwise
     */
    public boolean addActions(Collection<Action> actions) {
        return this.actions.addAll(actions);
    }

    /**
     * Returns resource's action.
     *
     * @param actionIdentifier identifier of requested action
     * @return resource's action
     */
    public Action getAction(String actionIdentifier) {
        return actions.get(actionIdentifier);
    }

    /**
     * Removes action from resource.
     *
     * @param action action to be removed
     * @return true if the removal was successful, false otherwise
     */
    public boolean removeAction(Action action) {
        return actions.remove(action);
    }

    /**
     * Removes all actions from resource.
     */
    public void clearActions() {
        actions.clear();
    }

    /**
     * Returns all resource's actions in form of set.
     *
     * @return all resource's actions in form of set
     */
    public Set<Action> getActions() {
        return actions.getSet();
    }

    /**
     * Returns resource's location.
     *
     * @return resource's location
     */
    public String getLocation() {
        return getKind().getLocation().toString() + getId();
    }

    /**
     * Returns resource's default term 'resource'.
     *
     * @return resource's default term
     */
    public static String getTermDefault() {
        return "resource";
    }

    /**
     * Returns resource's default identifier
     * 'http://schemas.ogf.org/occi/core#resource'
     *
     * @return resource's default identifier
     */
    public static String getIdentifierDefault() {
        return getSchemeDefault().toString() + getTermDefault();
    }

    /**
     * Resturns string representation of resource
     *
     * @see Object#toString()
     * @return string representation of resource
     */
    @Override
    public String toString() {
        return "Resource{" + "class=" + getClass().getName() + ", id=" + getId() + ", kind=" + getKind() + ", title=" + getTitle() + ", mixins=" + getMixins() + ", attributes=" + getAttributes() + ", links" + links + '}';
    }

    /**
     * Returns a plain text representation of resource instance as described in
     * OCCI standard.
     *
     * @return plain text representation of resource instance
     * @throws RenderingException
     */
    @Override
    public String toText() throws RenderingException {
        StringBuilder sb = new StringBuilder();

        sb.append(getKind().toText());

        List<Mixin> mixinList = new ArrayList<>(getMixins());
        Collections.sort(mixinList);
        for (Mixin m : mixinList) {
            sb.append("\n");
            sb.append(m.toText());
        }

        String attributesString = attributesToPrefixText();
        if (!attributesString.isEmpty()) {
            sb.append("\n");
            sb.append(attributesString);
        }

        List<Link> linkList = new ArrayList<>(getLinks());
        Collections.sort(linkList);
        for (Link l : linkList) {
            sb.append("\n");
            sb.append(l.toInlineText());
        }

        List<Action> actionList = new ArrayList<>(getActions());
        Collections.sort(actionList);
        for (Action a : actionList) {
            sb.append("\n");
            sb.append(a.toText(getKind().getLocation().toString() + getId()));
        }

        return sb.toString();
    }

    /**
     * Returns a occi text representation of resource instance as described in
     * OCCI standard in form of headers.
     *
     * @return plain text representation of resource instance
     * @throws RenderingException
     */
    @Override
    public Headers toHeaders() throws RenderingException {
        Headers headers = new Headers();

        headers.putAll(getKind().toHeaders());

        List<Mixin> mixinList = new ArrayList<>(getMixins());
        Collections.sort(mixinList);
        for (Mixin m : mixinList) {
            Headers mixinHeaders = m.toHeaders();
            for (String name : mixinHeaders.keySet()) {
                for (String value : mixinHeaders.get(name)) {
                    headers.add(name, value);
                }
            }
        }

        Headers attributeHeaders = attributesToHeaders();
        if (!attributeHeaders.isEmpty()) {
            headers.putAll(attributeHeaders);
        }

        List<Link> linkList = new ArrayList<>(getLinks());
        Collections.sort(linkList);
        for (Link l : linkList) {
            Headers linkHeaders = l.toInlineHeaders();
            for (String name : linkHeaders.keySet()) {
                for (String value : linkHeaders.get(name)) {
                    headers.add(name, value);
                }
            }
        }

        List<Action> actionList = new ArrayList<>(getActions());
        Collections.sort(actionList);
        for (Action a : actionList) {
            Headers actionHeaders = a.toHeaders(getKind().getLocation().toString() + getId());
            for (String name : actionHeaders.keySet()) {
                for (String value : actionHeaders.get(name)) {
                    headers.add(name, value);
                }
            }
        }

        return headers;
    }

    /**
     * Returns a JSON representation of resource instance as described in OCCI
     * standard. NOT IMPLEMENTED YET!
     *
     * @return JSON representation of resource instance
     */
    @Override
    public String toJSON() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
