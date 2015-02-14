package cz.cesnet.cloud.occi.core;

import com.sun.net.httpserver.Headers;
import cz.cesnet.cloud.occi.Model;
import cz.cesnet.cloud.occi.exception.InvalidAttributeValueException;
import cz.cesnet.cloud.occi.exception.RenderingException;
import cz.cesnet.cloud.occi.renderer.TextRenderer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class representing an OCCI Link
 *
 * @author Michal Kimle <kimle.michal@gmail.com>
 */
public class Link extends Entity {

    public static final String SOURCE_ATTRIBUTE_NAME = "occi.core.source";
    public static final String TARGET_ATTRIBUTE_NAME = "occi.core.target";
    private String relation;

    /**
     *
     * @param id cannot be null
     * @param kind cannot be null
     * @param title
     * @param model
     * @throws InvalidAttributeValueException
     */
    public Link(String id, Kind kind, String title, Model model) throws InvalidAttributeValueException {
        super(id, kind, title, model);
    }

    /**
     *
     * @param id cannot be null
     * @param kind cannot be null
     * @throws InvalidAttributeValueException
     */
    public Link(String id, Kind kind) throws InvalidAttributeValueException {
        super(id, kind);
    }

    /**
     *
     * @return
     */
    public String getSource() {
        return getValue(SOURCE_ATTRIBUTE_NAME);
    }

    /**
     *
     * @param source cannot be null
     * @throws InvalidAttributeValueException
     */
    public void setSource(Resource source) throws InvalidAttributeValueException {
        if (source == null) {
            throw new NullPointerException("source cannot be null");
        }
        addAttribute(SOURCE_ATTRIBUTE_NAME, source.getLocation());
    }

    /**
     *
     * @param sourceLocation
     * @throws InvalidAttributeValueException
     */
    public void setSource(String sourceLocation) throws InvalidAttributeValueException {
        addAttribute(SOURCE_ATTRIBUTE_NAME, sourceLocation);
    }

    /**
     *
     * @return
     */
    public String getTarget() {
        return getValue(TARGET_ATTRIBUTE_NAME);
    }

    /**
     *
     * @param target cannot be null
     * @throws InvalidAttributeValueException
     */
    public void setTarget(Resource target) throws InvalidAttributeValueException {
        if (target == null) {
            throw new NullPointerException("target cannot be null");
        }
        addAttribute(TARGET_ATTRIBUTE_NAME, target.getLocation());
    }

    /**
     *
     * @param targetLocation
     * @throws InvalidAttributeValueException
     */
    public void setTarget(String targetLocation) throws InvalidAttributeValueException {
        addAttribute(TARGET_ATTRIBUTE_NAME, targetLocation);
    }

    /**
     *
     * @return
     */
    public String getRelation() {
        return relation;
    }

    /**
     *
     * @param relation
     */
    public void setRelation(String relation) {
        this.relation = relation;
    }

    /**
     *
     * @return
     */
    public static String getTermDefault() {
        return "link";
    }

    /**
     *
     * @return
     */
    public static String getIdentifierDefault() {
        return getSchemeDefault().toString() + getTermDefault();
    }

    /**
     * Returns a plain text representation of link instance as described in OCCI
     * standard.
     *
     * @return plain text representation of link instance
     */
    @Override
    public String toText() {
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

        return sb.toString();
    }

    /**
     * Returns an occi text representation of link instance as described in OCCI
     * standard in form of headers.
     *
     * @return plain text representation of link instance
     */
    @Override
    public Headers toHeaders() {
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

        return headers;
    }

    /**
     * Returns an inline plain text representation of link instance as described
     * in OCCI standard.
     *
     * @return inline plain text representation of link instance
     * @throws RenderingException
     */
    public String toInlineText() throws RenderingException {
        StringBuilder sb = new StringBuilder("Link: ");
        sb.append(inlineTextBody());

        return sb.toString();
    }

    /**
     * Returns an inline occi text representation of link instance as described
     * in OCCI standard in form of headers.
     *
     * @return inline occi text representation of link instance in form of
     * headers
     * @throws RenderingException
     */
    public Headers toInlineHeaders() throws RenderingException {
        Headers headers = new Headers();
        headers.add("Link", inlineTextBody());

        return headers;
    }

    private String inlineTextBody() throws RenderingException {
        StringBuilder sb = new StringBuilder("");
        if (getTarget() == null || getTarget().isEmpty()) {
            throw new RenderingException("Link " + this + " is missing a target attribute.");
        }
        sb.append(TextRenderer.surroundString(getTarget(), "<", ">;"));

        if (relation == null || relation.isEmpty()) {
            throw new RenderingException("Link " + this + " is missing a relation.");
        }
        sb.append("rel");
        sb.append(TextRenderer.surroundString(relation));

        if (getKind().getLocation() != null && getId() != null && !getId().isEmpty()) {
            sb.append("self");
            sb.append(TextRenderer.surroundString(getKind().getLocation().toString() + getId()));
        }

        sb.append("category");
        sb.append(TextRenderer.surroundString(getKind().getIdentifier()));

        sb.append(attributesToOneLineText());

        return sb.toString();
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
