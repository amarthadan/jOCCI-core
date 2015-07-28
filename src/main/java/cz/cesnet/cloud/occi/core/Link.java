package cz.cesnet.cloud.occi.core;

import com.sun.net.httpserver.Headers;
import cz.cesnet.cloud.occi.Model;
import cz.cesnet.cloud.occi.exception.InvalidAttributeValueException;
import cz.cesnet.cloud.occi.exception.RenderingException;
import cz.cesnet.cloud.occi.renderer.TextRenderer;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class representing an OCCI Link.
 *
 * @author Michal Kimle <kimle.michal@gmail.com>
 */
public class Link extends Entity {

    public static final String SOURCE_ATTRIBUTE_NAME = "occi.core.source";
    public static final String TARGET_ATTRIBUTE_NAME = "occi.core.target";
    public static final URI SCHEME_DEFAULT = Category.SCHEME_CORE_DEFAULT;
    public static final String TERM_DEFAULT = "link";
    public static final String KIND_IDENTIFIER_DEFAULT = SCHEME_DEFAULT + TERM_DEFAULT;
    private String relation;

    /**
     * Constructor.
     *
     * @param id occi.core.id attribute. Cannot be null.
     * @param kind link's kind. Cannot be null.
     * @param title occi.core.title attribute
     * @param model link's model
     * @throws InvalidAttributeValueException in case of invalid id or title
     * value
     */
    public Link(String id, Kind kind, String title, Model model) throws InvalidAttributeValueException {
        super(id, kind, title, model);
    }

    /**
     * Constructor.
     *
     * @param id occi.core.id attribute. Cannot be null.
     * @param kind link's kind. Cannot be null.
     * @throws InvalidAttributeValueException in case of invalid id value
     */
    public Link(String id, Kind kind) throws InvalidAttributeValueException {
        super(id, kind);
    }

    /**
     * Returns link's source (value of occi.core.source attribute).
     *
     * @return link's source
     */
    public String getSource() {
        return getValue(SOURCE_ATTRIBUTE_NAME);
    }

    /**
     * Sets link's source.
     *
     * @param source link's source. Cannot be null.
     * @throws InvalidAttributeValueException in case source value is invalid
     */
    public void setSource(Resource source) throws InvalidAttributeValueException {
        if (source == null) {
            throw new NullPointerException("source cannot be null");
        }
        addAttribute(SOURCE_ATTRIBUTE_NAME, source.getLocation());
    }

    /**
     * Sets link's source.
     *
     * @param sourceLocation location of link's source
     * @throws InvalidAttributeValueException in case source value is invalid
     */
    public void setSource(String sourceLocation) throws InvalidAttributeValueException {
        addAttribute(SOURCE_ATTRIBUTE_NAME, sourceLocation);
    }

    /**
     * Returns link's target (value of occi.core.terget attribute).
     *
     * @return link's target
     */
    public String getTarget() {
        return getValue(TARGET_ATTRIBUTE_NAME);
    }

    /**
     * Sets link's target.
     *
     * @param target link's target. Cannot be null.
     * @throws InvalidAttributeValueException in case target value is invalid
     */
    public void setTarget(Resource target) throws InvalidAttributeValueException {
        if (target == null) {
            throw new NullPointerException("target cannot be null");
        }
        addAttribute(TARGET_ATTRIBUTE_NAME, target.getLocation());
    }

    /**
     * Sets link's target.
     *
     * @param targetLocation location of link's target
     * @throws InvalidAttributeValueException in case target value is invalid
     */
    public void setTarget(String targetLocation) throws InvalidAttributeValueException {
        addAttribute(TARGET_ATTRIBUTE_NAME, targetLocation);
    }

    /**
     * Returns link's relation.
     *
     * @return link's relation
     */
    public String getRelation() {
        return relation;
    }

    /**
     * Sets link's relation.
     *
     * @param relation
     */
    public void setRelation(String relation) {
        this.relation = relation;
    }

    /**
     * Returns link's default identifier 'http://schemas.ogf.org/occi/core#link'
     *
     * @return link's default identifier
     */
    @Override
    public String getDefaultKindIdentifier() {
        return KIND_IDENTIFIER_DEFAULT;
    }

    public static List<Attribute> getDefaultAttributes() {
        List<Attribute> list = new ArrayList<>();
        list.addAll(Entity.getDefaultAttributes());
        list.add(new Attribute(SOURCE_ATTRIBUTE_NAME, true, false));
        list.add(new Attribute(TARGET_ATTRIBUTE_NAME, true, false));

        return list;
    }

    public static Kind getDefaultKind() {
        Kind kind = new Kind(SCHEME_DEFAULT, TERM_DEFAULT, "Link", URI.create("/link/"), Link.getDefaultAttributes());
        return kind;
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

        sb.append(getKind().toText(false));

        List<Mixin> mixinList = new ArrayList<>(getMixins());
        Collections.sort(mixinList);
        for (Mixin m : mixinList) {
            sb.append("\n");
            sb.append(m.toText(false));
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

        headers.putAll(getKind().toHeaders(false));

        List<Mixin> mixinList = new ArrayList<>(getMixins());
        Collections.sort(mixinList);
        for (Mixin m : mixinList) {
            Headers mixinHeaders = m.toHeaders(false);
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
        sb.append("=\"");
        sb.append(getKind().getIdentifier());
        for (Mixin mixin : getMixins()) {
            sb.append(" ");
            sb.append(mixin.getIdentifier());
        }
        sb.append("\";");

        sb.append(attributesToOneLineText());

        return sb.toString();
    }

    /**
     * Returns a JSON representation of link instance as described in OCCI
     * standard. NOT IMPLEMENTED YET!
     *
     * @return JSON representation of link instance
     */
    @Override
    public String toJSON() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
