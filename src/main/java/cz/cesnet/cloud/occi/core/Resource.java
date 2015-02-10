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
 * Class representing an OCCI Resource
 *
 * @author Michal Kimle <kimle.michal@gmail.com>
 */
public class Resource extends Entity {

    public static final String SUMMARY_ATTRIBUTE_NAME = "occi.core.summary";
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Resource.class);
    private final SetCover<Link> links = new SetCover<>();
    private final SetCover<Action> actions = new SetCover<>();

    /**
     *
     * @param id cannot be null
     * @param kind cannot be null
     * @param title
     * @param model
     * @param summary
     * @throws InvalidAttributeValueException
     */
    public Resource(String id, Kind kind, String title, Model model, String summary) throws InvalidAttributeValueException {
        super(id, kind, title, model);

        addAttribute(SUMMARY_ATTRIBUTE_NAME, summary);
    }

    /**
     *
     * @param id cannot be null
     * @param kind cannot be null
     * @throws InvalidAttributeValueException
     */
    public Resource(String id, Kind kind) throws InvalidAttributeValueException {
        super(id, kind);
    }

    /**
     *
     * @return
     */
    public String getSummary() {
        return getValue(SUMMARY_ATTRIBUTE_NAME);
    }

    /**
     *
     * @param summary
     * @throws InvalidAttributeValueException
     */
    public void setSummary(String summary) throws InvalidAttributeValueException {
        addAttribute(SUMMARY_ATTRIBUTE_NAME, summary);
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
     * Adds link to the resource and automatically sets link's source to
     * resource.
     *
     * @param link
     * @return
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
     * @param links
     * @return
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
    public boolean containsAction(Action action) {
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
    public boolean addAction(Action action) {
        return actions.add(action);
    }

    /**
     *
     * @param actions
     * @return
     */
    public boolean addActions(Collection<Action> actions) {
        return this.actions.addAll(actions);
    }

    /**
     *
     * @param actionIdentifier
     * @return
     */
    public Action getAction(String actionIdentifier) {
        return actions.get(actionIdentifier);
    }

    /**
     *
     * @param action
     * @return
     */
    public boolean removeAction(Action action) {
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
    public Set<Action> getActions() {
        return actions.getSet();
    }

    /**
     *
     * @return
     */
    public String getLocation() {
        return getKind().getLocation().toString() + getId();
    }

    /**
     *
     * @return
     */
    public static String getTermDefault() {
        return "resource";
    }

    /**
     *
     * @return
     */
    public static String getIdentifierDefault() {
        return getSchemeDefault().toString() + getTermDefault();
    }

    /**
     *
     * @return
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
    public Headers toHeaders() throws RenderingException {
        Headers headers = new Headers();

        headers.putAll(getKind().toHeaders());

        List<Mixin> mixinList = new ArrayList<>(getMixins());
        Collections.sort(mixinList);
        for (Mixin m : mixinList) {
            headers.putAll(m.toHeaders());
        }

        Headers attributeHeaders = attributesToHeaders();
        if (!attributeHeaders.isEmpty()) {
            headers.putAll(attributeHeaders);
        }

        List<Link> linkList = new ArrayList<>(getLinks());
        Collections.sort(linkList);
        for (Link l : linkList) {
            headers.putAll(l.toInlineHeaders());
        }

        List<Action> actionList = new ArrayList<>(getActions());
        Collections.sort(actionList);
        for (Action a : actionList) {
            headers.putAll(a.toHeaders(getKind().getLocation().toString() + getId()));
        }

        return headers;
    }

    /**
     *
     * @return
     */
    @Override
    public String toJSON() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
