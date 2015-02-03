package cz.cesnet.cloud.occi.parser;

import cz.cesnet.cloud.occi.Collection;
import cz.cesnet.cloud.occi.Model;
import cz.cesnet.cloud.occi.core.Action;
import cz.cesnet.cloud.occi.core.ActionInstance;
import cz.cesnet.cloud.occi.core.Attribute;
import cz.cesnet.cloud.occi.core.Category;
import cz.cesnet.cloud.occi.core.Entity;
import cz.cesnet.cloud.occi.core.Kind;
import cz.cesnet.cloud.occi.core.Link;
import cz.cesnet.cloud.occi.core.Mixin;
import cz.cesnet.cloud.occi.core.Resource;
import cz.cesnet.cloud.occi.exception.InvalidAttributeValueException;
import cz.cesnet.cloud.occi.exception.ParsingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextParser implements Parser {

    public static final String GROUP_TERM = "term";
    public static final String GROUP_SCHEME = "scheme";
    public static final String GROUP_CLASS = "class";
    public static final String GROUP_TITLE = "title";
    public static final String GROUP_REL = "rel";
    public static final String GROUP_LOCATION = "location";
    public static final String GROUP_ATTRIBUTES = "attributes";
    public static final String GROUP_ACTIONS = "actions";
    public static final String GROUP_URI = "uri";
    public static final String GROUP_SELF = "self";
    public static final String GROUP_CATEGORY = "category";

    public static final String REGEXP_LOALPHA = "[a-z]";
    public static final String REGEXP_ALPHA = "[a-zA-Z]";
    public static final String REGEXP_DIGIT = "[0-9]";
    public static final String REGEXP_INT = REGEXP_DIGIT + "+";
    public static final String REGEXP_FLOAT = REGEXP_INT + "\\." + REGEXP_INT;
    public static final String REGEXP_NUMBER = REGEXP_FLOAT + "|" + REGEXP_INT;
    public static final String REGEXP_BOOL = "\\b(?<!\\|)true(?!\\|)\\b|\\b(?<!\\|)false(?!\\|)\\b";
    public static final String REGEXP_QUOTED_STRING = "([^\"\\\\]|\\.)*";
    public static final String REGEXP_URI = "(?x-mi:([a-zA-Z][\\-+.a-zA-Z\\d]*):(?:((?:[\\-_.!~*'()a-zA-Z\\d;?:@&=+$,]|%[a-fA-F\\d]{2})(?:[\\-_.!~*'()a-zA-Z\\d;\\/?:@&=+$,\\[\\]]|%[a-fA-F\\d]{2})*)|(?:(?:\\/\\/(?:(?:(?:((?:[\\-_.!~*'()a-zA-Z\\d;:&=+$,]|%[a-fA-F\\d]{2})*)@)?(?:((?:(?:[a-zA-Z0-9\\-.]|%[0-9a-fA-F][0-9a-fA-F])+|\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}|\\[(?:(?:[a-fA-F\\d]{1,4}:)*(?:[a-fA-F\\d]{1,4}|\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})|(?:(?:[a-fA-F\\d]{1,4}:)*[a-fA-F\\d]{1,4})?::(?:(?:[a-fA-F\\d]{1,4}:)*(?:[a-fA-F\\d]{1,4}|\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}))?)\\]))(?::(\\d*))?))?|((?:[\\-_.!~*'()a-zA-Z\\d$,;:@&=+]|%[a-fA-F\\d]{2})+))|(?!\\/\\/))(\\/(?:[\\-_.!~*'()a-zA-Z\\d:@&=+$,]|%[a-fA-F\\d]{2})*(?:;(?:[\\-_.!~*'()a-zA-Z\\d:@&=+$,]|%[a-fA-F\\d]{2})*)*(?:\\/(?:[\\-_.!~*'()a-zA-Z\\d:@&=+$,]|%[a-fA-F\\d]{2})*(?:;(?:[\\-_.!~*'()a-zA-Z\\d:@&=+$,]|%[a-fA-F\\d]{2})*)*)*)?)(?:\\?((?:[\\-_.!~*'()a-zA-Z\\d;\\/?:@&=+$,\\[\\]]|%[a-fA-F\\d]{2})*))?)(?:\\#((?:[\\-_.!~*'()a-zA-Z\\d;\\/?:@&=+$,\\[\\]]|%[a-fA-F\\d]{2})*))?)";
    public static final String REGEXP_URI_REF = "(?:[a-zA-Z][\\-+.a-zA-Z\\d]*:(?:(?:\\/\\/(?:(?:(?:[\\-_.!~*'()a-zA-Z\\d;:&=+$,]|%[a-fA-F\\d]{2})*@)?(?:(?:[a-zA-Z0-9\\-.]|%[0-9a-fA-F][0-9a-fA-F])+|\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}|\\[(?:(?:[a-fA-F\\d]{1,4}:)*(?:[a-fA-F\\d]{1,4}|\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})|(?:(?:[a-fA-F\\d]{1,4}:)*[a-fA-F\\d]{1,4})?::(?:(?:[a-fA-F\\d]{1,4}:)*(?:[a-fA-F\\d]{1,4}|\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}))?)\\])(?::\\d*)?|(?:[\\-_.!~*'()a-zA-Z\\d$,;:@&=+]|%[a-fA-F\\d]{2})+)(?:\\/(?:[\\-_.!~*'()a-zA-Z\\d:@&=+$,]|%[a-fA-F\\d]{2})*(?:;(?:[\\-_.!~*'()a-zA-Z\\d:@&=+$,]|%[a-fA-F\\d]{2})*)*(?:\\/(?:[\\-_.!~*'()a-zA-Z\\d:@&=+$,]|%[a-fA-F\\d]{2})*(?:;(?:[\\-_.!~*'()a-zA-Z\\d:@&=+$,]|%[a-fA-F\\d]{2})*)*)*)?|\\/(?:[\\-_.!~*'()a-zA-Z\\d:@&=+$,]|%[a-fA-F\\d]{2})*(?:;(?:[\\-_.!~*'()a-zA-Z\\d:@&=+$,]|%[a-fA-F\\d]{2})*)*(?:\\/(?:[\\-_.!~*'()a-zA-Z\\d:@&=+$,]|%[a-fA-F\\d]{2})*(?:;(?:[\\-_.!~*'()a-zA-Z\\d:@&=+$,]|%[a-fA-F\\d]{2})*)*)*)(?:\\?(?:(?:[\\-_.!~*'()a-zA-Z\\d;\\/?:@&=+$,\\[\\]]|%[a-fA-F\\d]{2})*))?|(?:[\\-_.!~*'()a-zA-Z\\d;?:@&=+$,]|%[a-fA-F\\d]{2})(?:[\\-_.!~*'()a-zA-Z\\d;\\/?:@&=+$,\\[\\]]|%[a-fA-F\\d]{2})*)|(?:\\/\\/(?:(?:(?:[\\-_.!~*'()a-zA-Z\\d;:&=+$,]|%[a-fA-F\\d]{2})*@)?(?:(?:[a-zA-Z0-9\\-.]|%[0-9a-fA-F][0-9a-fA-F])+|\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}|\\[(?:(?:[a-fA-F\\d]{1,4}:)*(?:[a-fA-F\\d]{1,4}|\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})|(?:(?:[a-fA-F\\d]{1,4}:)*[a-fA-F\\d]{1,4})?::(?:(?:[a-fA-F\\d]{1,4}:)*(?:[a-fA-F\\d]{1,4}|\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}))?)\\])(?::\\d*)?|(?:[\\-_.!~*'()a-zA-Z\\d$,;:@&=+]|%[a-fA-F\\d]{2})+)(?:\\/(?:[\\-_.!~*'()a-zA-Z\\d:@&=+$,]|%[a-fA-F\\d]{2})*(?:;(?:[\\-_.!~*'()a-zA-Z\\d:@&=+$,]|%[a-fA-F\\d]{2})*)*(?:\\/(?:[\\-_.!~*'()a-zA-Z\\d:@&=+$,]|%[a-fA-F\\d]{2})*(?:;(?:[\\-_.!~*'()a-zA-Z\\d:@&=+$,]|%[a-fA-F\\d]{2})*)*)*)?|\\/(?:[\\-_.!~*'()a-zA-Z\\d:@&=+$,]|%[a-fA-F\\d]{2})*(?:;(?:[\\-_.!~*'()a-zA-Z\\d:@&=+$,]|%[a-fA-F\\d]{2})*)*(?:\\/(?:[\\-_.!~*'()a-zA-Z\\d:@&=+$,]|%[a-fA-F\\d]{2})*(?:;(?:[\\-_.!~*'()a-zA-Z\\d:@&=+$,]|%[a-fA-F\\d]{2})*)*)*|(?:[\\-_.!~*'()a-zA-Z\\d;@&=+$,]|%[a-fA-F\\d]{2})+(?:\\/(?:[\\-_.!~*'()a-zA-Z\\d:@&=+$,]|%[a-fA-F\\d]{2})*(?:;(?:[\\-_.!~*'()a-zA-Z\\d:@&=+$,]|%[a-fA-F\\d]{2})*)*(?:\\/(?:[\\-_.!~*'()a-zA-Z\\d:@&=+$,]|%[a-fA-F\\d]{2})*(?:;(?:[\\-_.!~*'()a-zA-Z\\d:@&=+$,]|%[a-fA-F\\d]{2})*)*)*)?)(?:\\?(?:[\\-_.!~*'()a-zA-Z\\d;\\/?:@&=+$,\\[\\]]|%[a-fA-F\\d]{2})*)?)?(?:#(?:[\\-_.!~*'()a-zA-Z\\d;\\/?:@&=+$,\\[\\]]|%[a-fA-F\\d]{2})*)?";

    public static final String REGEXP_TERM = REGEXP_LOALPHA + "(" + REGEXP_LOALPHA + "|" + REGEXP_DIGIT + "|-|_)*";
    public static final String REGEXP_SCHEME = REGEXP_URI + "#";
    public static final String REGEXP_TYPE_IDENTIFIER = REGEXP_SCHEME + REGEXP_TERM;
    public static final String REGEXP_CLASS = "\\b(?<!\\|)action(?!\\|)\\b|\\b(?<!\\|)mixin(?!\\|)\\b|\\b(?<!\\|)kind(?!\\|)\\b";
    public static final String REGEXP_TYPE_IDENTIFIER_LIST = REGEXP_TYPE_IDENTIFIER + "(\\s+" + REGEXP_TYPE_IDENTIFIER + ")*";

    public static final String REGEXP_ATTRIBUTE_COMPONENT = REGEXP_LOALPHA + "(" + REGEXP_LOALPHA + "|" + REGEXP_DIGIT + "|-|_)*";
    public static final String REGEXP_ATTRIBUTE_NAME = REGEXP_ATTRIBUTE_COMPONENT + "(\\." + REGEXP_ATTRIBUTE_COMPONENT + ")*";
    public static final String REGEXP_ATTRIBUTE_PROPERTIES = "\\{(?:required immutable|immutable required|required|immutable)\\}";
    public static final String REGEXP_ATTRIBUTE_DEF = "(?:" + REGEXP_ATTRIBUTE_NAME + ")(?:" + REGEXP_ATTRIBUTE_PROPERTIES + ")?";
    public static final String REGEXP_ATTRIBUTE_LIST = REGEXP_ATTRIBUTE_DEF + "(\\s+" + REGEXP_ATTRIBUTE_DEF + ")*";
    public static final String REGEXP_ATTRIBUTE_REPR = REGEXP_ATTRIBUTE_NAME + "=(\"" + REGEXP_QUOTED_STRING + "\"|" + REGEXP_NUMBER + "|" + REGEXP_BOOL + ");?";

    public static final String REGEXP_ACTION = REGEXP_TYPE_IDENTIFIER;
    public static final String REGEXP_ACTION_LIST = REGEXP_ACTION + "(\\s+" + REGEXP_ACTION + ")*";
    public static final String REGEXP_RESOURCE_TYPE = REGEXP_TYPE_IDENTIFIER + "(\\s+" + REGEXP_TYPE_IDENTIFIER + ")*";
    public static final String REGEXP_LINK_INSTANCE = REGEXP_URI_REF;
    public static final String REGEXP_LINK_TYPE = REGEXP_TYPE_IDENTIFIER + "(\\s+" + REGEXP_TYPE_IDENTIFIER + ")*";

    public static final String REGEXP_CATEGORY = "(?<" + GROUP_TERM + ">" + REGEXP_TERM + ")" // term (mandatory)
            + ";\\s*scheme=\"(?<" + GROUP_SCHEME + ">" + REGEXP_SCHEME + ")(?:" + REGEXP_TERM + ")?\"" // scheme (mandatory)
            + ";\\s*class=\"?(?<" + GROUP_CLASS + ">" + REGEXP_CLASS + ")\"?" // class (mandatory)
            + "(;\\s*title=\"(?<" + GROUP_TITLE + ">" + REGEXP_QUOTED_STRING + ")\")?" // title (optional)
            + "(;\\s*rel=\"(?<" + GROUP_REL + ">" + REGEXP_TYPE_IDENTIFIER_LIST + ")\")?" // rel (optional)
            + "(;\\s*location=\"(?<" + GROUP_LOCATION + ">" + REGEXP_URI_REF + ")\")?" // location (optional)
            + "(;\\s*attributes=\"(?<" + GROUP_ATTRIBUTES + ">" + REGEXP_ATTRIBUTE_LIST + ")\")?" // attributes (optional)
            + "(;\\s*actions=\"(?<" + GROUP_ACTIONS + ">" + REGEXP_ACTION_LIST + ")\")?" // actions (optional)
            + ";?"; // additional semicolon at the end (not specified, for interoperability)

    public static final String REGEXP_ATTRIBUTES = "(" + REGEXP_ATTRIBUTE_DEF + ")";

    public static final String REGEXP_LINK = "\\<(?<" + GROUP_URI + ">" + REGEXP_URI_REF + ")\\>" // uri (mandatory)
            + ";\\s*rel=\"(?<" + GROUP_REL + ">" + REGEXP_RESOURCE_TYPE + ")\"" // rel (mandatory)
            + "(;\\s*self=\"(?<" + GROUP_SELF + ">" + REGEXP_LINK_INSTANCE + ")\")?" // self (optional)
            + "(;\\s*category=\"(?<" + GROUP_CATEGORY + ">(;?\\s*(" + REGEXP_LINK_TYPE + "))+)\")?" // category (optional)
            + "(;\\s*(?<" + GROUP_ATTRIBUTES + ">(;?\\s*" + REGEXP_ATTRIBUTE_REPR + ")*))?" // attributes (optional)
            + ";?"; // additional semicolon at the end (not specified, for interoperability)

    public static final Pattern PATTERN_CATEGORY = Pattern.compile(REGEXP_CATEGORY);
    public static final Pattern PATTERN_ATTRIBUTES = Pattern.compile(REGEXP_ATTRIBUTES);
    public static final Pattern PATTERN_LINK = Pattern.compile(REGEXP_LINK);

    private static final Logger LOGGER = LoggerFactory.getLogger(TextParser.class);

    @Override
    public Model parseModel(MediaType mediaType, String body, Map<String, String> headers) throws ParsingException {
        LOGGER.debug("Parsing model...");

        switch (mediaType) {
            case TEXT_OCCI:
                return parseModelFromHeaders(headers);
            case TEXT_PLAIN:
            default:
                return parseModelFromBody(body);
        }
    }

    private Model parseModelFromBody(String body) throws ParsingException {
        LOGGER.debug("Reading response body.");

        body = body.trim();
        String replaced = body.replaceAll("Category:\\s*", "");

        String[] lines = replaced.split("[\\r\\n]+");
        return parseModelFromArray(lines);
    }

    private Model parseModelFromHeaders(Map<String, String> headers) throws ParsingException {
        LOGGER.debug("Reading response headers.");

        if (!headers.containsKey("Category")) {
            throw new ParsingException("No header 'Category' among headers.");
        }

        String[] categories = headers.get("Category").split(",");
        return parseModelFromArray(categories);
    }

    private Model parseModelFromArray(String[] lines) throws ParsingException {
        Model model = new Model();

        for (String line : lines) {
            LOGGER.debug("Matching line '{}' against category pattern.", line);
            Matcher matcher = PATTERN_CATEGORY.matcher(line);
            if (!matcher.find()) {
                throw new ParsingException("Invalid line: " + line);
            }
            String term = matcher.group(GROUP_TERM);
            String scheme = matcher.group(GROUP_SCHEME);
            String categoryClass = matcher.group(GROUP_CLASS);
            String location = matcher.group(GROUP_LOCATION);
            LOGGER.debug("Match: term={}, scheme={}, class={}, title={}, rel={}, location={}, attributes={}, actions={}",
                    term, scheme, categoryClass, matcher.group(GROUP_TITLE), matcher.group(GROUP_REL), location, matcher.group(GROUP_ATTRIBUTES), matcher.group(GROUP_ACTIONS));

            if (term == null || term.isEmpty()) {
                throw new ParsingException("No term found.");
            }
            if (scheme == null || scheme.isEmpty()) {
                throw new ParsingException("No scheme found.");
            }
            if (categoryClass == null || categoryClass.isEmpty()) {
                throw new ParsingException("No class found.");
            }

            switch (categoryClass) {
                case "kind":
                    if (location == null || location.isEmpty()) {
                        throw new ParsingException("No location found.");
                    }
                    model = addKind(matcher, model);
                    break;
                case "mixin":
                    if (location == null || location.isEmpty()) {
                        throw new ParsingException("No location found.");
                    }
                    model = addMixin(matcher, model);
                    break;
                case "action":
                    model = addAction(matcher, model);
                    break;
                default:
                    throw new ParsingException("Unknown class type.");
            }
        }

        return model;
    }

    private Model addKind(Matcher matcher, Model model) throws ParsingException {
        LOGGER.debug("Adding kind...");
        String actions = matcher.group(GROUP_ACTIONS);
        String rel = matcher.group(GROUP_REL);
        String term = matcher.group(GROUP_TERM);

        Kind kind = createKind(matcher);
        kind = (Kind) connectActions(actions, kind, model);

        if (rel != null && !rel.isEmpty()) {
            if (!model.containsKind(rel)) {
                throw new ParsingException("Unexpected relation " + rel + " in kind " + term + ".");
            }
            Kind k = model.getKind(rel);
            LOGGER.debug("Creating relation between {} and {}.", kind, k);
            kind.setParentKind(k);
            kind.addRelation(k);
        }

        model.addKind(kind);
        return model;
    }

    private Model addMixin(Matcher matcher, Model model) throws ParsingException {
        LOGGER.debug("Adding mixin...");
        String actions = matcher.group(GROUP_ACTIONS);
        String rel = matcher.group(GROUP_REL);
        String term = matcher.group(GROUP_TERM);

        Mixin mixin = createMixin(matcher);
        mixin = (Mixin) connectActions(actions, mixin, model);

        if (rel != null && !rel.isEmpty()) {
            if (!model.containsMixin(rel)) {
                throw new ParsingException("Unexpected relation " + rel + " in mixin " + term + ".");
            }
            Mixin m = model.getMixin(rel);
            LOGGER.debug("Creating relation between {} and {}.", mixin, m);
            mixin.addRelation(m);
        }

        model.addMixin(mixin);
        return model;
    }

    private Model addAction(Matcher matcher, Model model) throws ParsingException {
        LOGGER.debug("Adding action...");
        String term = matcher.group(GROUP_TERM);
        String scheme = matcher.group(GROUP_SCHEME);
        String title = matcher.group(GROUP_TITLE);
        String attributes = matcher.group(GROUP_ATTRIBUTES);

        String actionIdentifier = scheme + term;
        if (model.containsAction(actionIdentifier)) {
            Set<Attribute> parsedAttributes = parseAttributes(attributes);

            Action action = model.getAction(actionIdentifier);
            action.setTitle(title);
            for (Attribute attribute : parsedAttributes) {
                action.addAttribute(attribute);
            }
        } else {
            Action action = createAction(matcher);
            model.addAction(action);
        }

        return model;
    }

    private Category connectActions(String actions, Category category, Model model) throws ParsingException {
        LOGGER.debug("Connecting actions...");
        if (actions == null || actions.isEmpty()) {
            return category;
        }

        String[] splitedActions = actions.split("\\s+");
        for (String actionIdentifier : splitedActions) {
            LOGGER.debug("Action identifier: {}", actionIdentifier);
            Action action;
            if (model != null && model.containsAction(actionIdentifier)) {
                action = model.getAction(actionIdentifier);
            } else {
                try {
                    String[] splitedAction = actionIdentifier.split("#");
                    if (splitedAction.length != 2) {
                        throw new ParsingException("Invalid action identifier: " + actionIdentifier + ".");
                    }
                    action = new Action(new URI(splitedAction[0] + "#"), splitedAction[1]);
                } catch (URISyntaxException ex) {
                    throw new ParsingException("Invalid category scheme: " + actionIdentifier + ".", ex);
                }
            }

            category.addAction(action);
        }

        return category;
    }

    private Set<Attribute> parseAttributes(String attributes) {
        LOGGER.debug("Parsing attributes: {}", attributes);
        Set<Attribute> attributeSet = new HashSet<>();
        if (attributes == null || attributes.isEmpty()) {
            return attributeSet;
        }

        Matcher matcher = PATTERN_ATTRIBUTES.matcher(attributes);
        while (matcher.find()) {
            String attributeString = matcher.group();
            LOGGER.debug("Found attribute represented by string: {}", attributeString);
            Attribute attribute = parseAttribute(attributeString);
            attributeSet.add(attribute);
        }

        return attributeSet;
    }

    private Attribute parseAttribute(String attributeString) {
        String[] splitedAttribute = attributeString.split("\\{");
        Attribute attribute = new Attribute(splitedAttribute[0]);
        if (splitedAttribute.length == 2) {
            if (splitedAttribute[1].contains("immutable")) {
                attribute.setImmutable(true);
            }
            if (splitedAttribute[1].contains("required")) {
                attribute.setRequired(true);
            }
        }

        LOGGER.debug("New attribute: {}", attribute);
        return attribute;
    }

    @Override
    public List<URI> parseLocations(MediaType mediaType, String body, Map<String, String> headers) throws ParsingException {
        LOGGER.debug("Parsing location...");

        switch (mediaType) {
            case TEXT_OCCI:
                return parseLocationsFromHeaders(headers);
            case TEXT_URI_LIST:
            case TEXT_PLAIN:
            default:
                return parseLocationsFromBody(body);

        }
    }

    private List<URI> parseLocationsFromHeaders(Map<String, String> headers) throws ParsingException {
        LOGGER.debug("Reading response headers.");

        if (!headers.containsKey("Location")) {
            throw new ParsingException("No header 'Location' among headers.");
        }

        String[] locations = headers.get("Location").split(",");
        return makeURIList(locations);
    }

    private List<URI> parseLocationsFromBody(String body) throws ParsingException {
        LOGGER.debug("Reading response body.");

        body = body.trim();
        String replaced = body.replaceAll("X-OCCI-Location:\\s*", "");
        String[] lines = replaced.split("[\\r\\n]+");
        return makeURIList(lines);
    }

    private List<URI> makeURIList(String[] locations) throws ParsingException {

        List<URI> locationsURI = new ArrayList<>();
        for (String location : locations) {
            try {
                locationsURI.add(new URI(location));
            } catch (URISyntaxException ex) {
                throw new ParsingException("Invalid location: " + location + ".", ex);
            }
        }

        return locationsURI;
    }

    @Override
    public Collection parseCollection(MediaType mediaType, String body, Map<String, String> headers, CollectionType collectionType) throws ParsingException {
        LOGGER.debug("Parsing collection...");

        switch (mediaType) {
            case TEXT_OCCI:
                return parseCollectionFromHeaders(headers, collectionType);
            case TEXT_PLAIN:
            default:
                return parseCollectionFromBody(body, collectionType);
        }
    }

    private Collection parseCollectionFromHeaders(Map<String, String> headers, CollectionType collectionType) throws ParsingException {
        LOGGER.debug("Reading headers.");

        if (!headers.containsKey("Category")) {
            throw new ParsingException("No 'Category' header.");
        }

        List<String> lines = new ArrayList<>();
        lines.addAll(Arrays.asList(headers.get("Category").split(",")));

        if (headers.containsKey("X-Occi-Attribute")) {
            lines.addAll(Arrays.asList(headers.get("X-Occi-Attribute").split(",")));
        }

        if (headers.containsKey("Link")) {
            lines.addAll(Arrays.asList(headers.get("Link").split(",")));
        }

        return parseCollectionFromArray(lines.toArray(new String[0]), collectionType);
    }

    private Collection parseCollectionFromBody(String body, CollectionType collectionType) throws ParsingException {
        LOGGER.debug("Reading body.");

        body = body.trim();
        String replaced = body.replaceAll("Category:\\s*", "");
        replaced = replaced.replaceAll("Link:\\s*", "");
        replaced = replaced.replaceAll("X-OCCI-Attribute:\\s*", "");
        String[] lines = replaced.split("[\\r\\n]+");

        return parseCollectionFromArray(lines, collectionType);
    }

    private Collection parseCollectionFromArray(String[] lines, CollectionType collectionType) throws ParsingException {
        Collection collection = new Collection();
        Kind kind = null;
        Set<Mixin> mixins = new HashSet<>();
        List<String> rawAttributes = new ArrayList<>();
        Map<String, String> attributesWithValues = null;

        switch (collectionType) {
            //expecting resource instance
            case RESOURCE:
                Set<Link> links = new HashSet<>();
                Set<Action> actionLinks = new HashSet<>();

                kind = lookForKind(lines[0]);
                lines = Arrays.copyOfRange(lines, 1, lines.length);

                for (String line : lines) {
                    //looking for mixin lines
                    if (lookForMixins(line, mixins)) {
                        continue;
                    }
                    //looking for attribute lines
                    if (lookForAttributes(line, rawAttributes)) {
                        continue;
                    }
                    //looking for link lines
                    lookForLinks(line, links, actionLinks);
                }

                attributesWithValues = parseAttributesWithValues(rawAttributes.toArray(new String[0]));
                if (!attributesWithValues.containsKey(Resource.ID_ATTRIBUTE_NAME)) {
                    throw new ParsingException("No id found. Cannot construct a resource.");
                }

                Resource resource = null;
                try {
                    resource = new Resource(attributesWithValues.get(Entity.ID_ATTRIBUTE_NAME), kind);

                    attributesWithValues.remove(Entity.ID_ATTRIBUTE_NAME);
                    resource.addMixins(mixins);
                    resource.addLinks(links);
                    resource.addActions(actionLinks);
                    resource.addAttributes(attributesWithValues);
                } catch (InvalidAttributeValueException ex) {
                    throw new ParsingException("Invalid attribute value found", ex);
                }
                collection.addResource(resource);

                break;
            case LINK:
                kind = lookForKind(lines[0]);
                lines = Arrays.copyOfRange(lines, 1, lines.length);

                for (String line : lines) {
                    //looking for mixin lines
                    if (lookForMixins(line, mixins)) {
                        continue;
                    }
                    //looking for attribute lines
                    lookForAttributes(line, rawAttributes);
                }

                attributesWithValues = parseAttributesWithValues(rawAttributes.toArray(new String[0]));
                if (!attributesWithValues.containsKey(Resource.ID_ATTRIBUTE_NAME)) {
                    throw new ParsingException("No id found. Cannot construct a resource.");
                }

                Link link = null;
                try {
                    link = new Link(attributesWithValues.get(Entity.ID_ATTRIBUTE_NAME), kind);

                    attributesWithValues.remove(Entity.ID_ATTRIBUTE_NAME);
                    link.addMixins(mixins);
                    link.addAttributes(attributesWithValues);
                } catch (InvalidAttributeValueException ex) {
                    throw new ParsingException("Invalid attribute value found", ex);
                }
                collection.addLink(link);
                break;
            case ACTION:
                ActionInstance actionInstance = lookForActionInstance(lines[0]);
                lines = Arrays.copyOfRange(lines, 1, lines.length);

                for (String line : lines) {
                    //looking for attribute lines
                    lookForAttributes(line, rawAttributes);
                }

                attributesWithValues = parseAttributesWithValues(rawAttributes.toArray(new String[0]));
                actionInstance.addAttributes(attributesWithValues);

                collection.addAction(actionInstance);
                break;
            default:
                throw new ParsingException("Unknown collection type'" + collectionType + "'.");
        }

        return collection;
    }

    private Kind lookForKind(String line) throws ParsingException {
        LOGGER.debug("Matching line '{}' against category pattern.", line);
        Matcher matcher = PATTERN_CATEGORY.matcher(line);

        if (!matcher.find()) {
            throw new ParsingException("No kind specification found.");
        }

        LOGGER.debug("Match: term={}, scheme={}, class={}, title={}, rel={}, location={}, attributes={}, actions={}",
                matcher.group(GROUP_TERM), matcher.group(GROUP_SCHEME),
                matcher.group(GROUP_CLASS), matcher.group(GROUP_TITLE),
                matcher.group(GROUP_REL), matcher.group(GROUP_LOCATION),
                matcher.group(GROUP_ATTRIBUTES), matcher.group(GROUP_ACTIONS));

        String actions = matcher.group(GROUP_ACTIONS);
        String categoryClass = matcher.group(GROUP_CLASS);

        if (!categoryClass.equals("kind")) {
            throw new ParsingException("No kind specification found.");
        }

        Kind kind = createKind(matcher);
        connectActions(actions, kind, null);

        return kind;
    }

    private boolean lookForMixins(String line, Set<Mixin> mixins) throws ParsingException {
        LOGGER.debug("Matching line '{}' against category pattern.", line);
        Matcher matcher = PATTERN_CATEGORY.matcher(line);

        if (matcher.find()) {
            LOGGER.debug("Match: term={}, scheme={}, class={}, title={}, rel={}, location={}, attributes={}, actions={}",
                    matcher.group(GROUP_TERM), matcher.group(GROUP_SCHEME),
                    matcher.group(GROUP_CLASS), matcher.group(GROUP_TITLE),
                    matcher.group(GROUP_REL), matcher.group(GROUP_LOCATION),
                    matcher.group(GROUP_ATTRIBUTES), matcher.group(GROUP_ACTIONS));

            String categoryClass = matcher.group(GROUP_CLASS);
            String actions = matcher.group(GROUP_ACTIONS);

            switch (categoryClass) {
                case "mixin":
                    Mixin mixin = createMixin(matcher);
                    connectActions(actions, mixin, null);
                    mixins.add(mixin);
                    break;
                default:
                    throw new ParsingException("Unknown category class '" + categoryClass + "'.");
            }

            return true;
        }

        return false;
    }

    private boolean lookForAttributes(String line, List<String> attributes) {
        LOGGER.debug("Matching line '{}' against attribute pattern.", line);
        if (line.matches(REGEXP_ATTRIBUTE_REPR)) {
            attributes.add(line);
            return true;
        }

        return false;
    }

    private void lookForLinks(String line, Set<Link> links, Set<Action> actionLinks) throws ParsingException {
        LOGGER.debug("Matching line '{}' against link pattern.", line);
        Matcher matcher = PATTERN_LINK.matcher(line);

        if (matcher.find()) {
            LOGGER.debug("Match: uri={}, rel={}, self={}, category={}, attributes={}",
                    matcher.group(GROUP_URI), matcher.group(GROUP_REL),
                    matcher.group(GROUP_SELF), matcher.group(GROUP_CATEGORY),
                    matcher.group(GROUP_ATTRIBUTES));

            if (matcher.group(GROUP_URI).contains("?action=")) {
                Action action = createAction(matcher.group(GROUP_REL));
                actionLinks.add(action);
            } else {
                Link link = createLink(matcher);
                links.add(link);
            }
        }
    }

    private ActionInstance lookForActionInstance(String line) throws ParsingException {
        LOGGER.debug("Matching line '{}' against category pattern.", line);
        Matcher matcher = PATTERN_CATEGORY.matcher(line);

        if (!matcher.find()) {
            throw new ParsingException("No action specification found.");
        }

        LOGGER.debug("Match: term={}, scheme={}, class={}, title={}, rel={}, location={}, attributes={}, actions={}",
                matcher.group(GROUP_TERM), matcher.group(GROUP_SCHEME),
                matcher.group(GROUP_CLASS), matcher.group(GROUP_TITLE),
                matcher.group(GROUP_REL), matcher.group(GROUP_LOCATION),
                matcher.group(GROUP_ATTRIBUTES), matcher.group(GROUP_ACTIONS));

        String categoryClass = matcher.group(GROUP_CLASS);

        if (!categoryClass.equals("action")) {
            throw new ParsingException("No action specification found.");
        }

        Action action = createAction(matcher);
        ActionInstance actionInstance = new ActionInstance(action);

        return actionInstance;
    }

    private Map<String, String> parseAttributesWithValues(String[] attributes) throws ParsingException {
        LOGGER.debug("Parsing attributes with values");
        Map<String, String> result = new HashMap<>();

        for (String attribute : attributes) {
            LOGGER.debug("Attribute represented by string: {}", attribute);
            String[] parts = attribute.split("=");
            if (parts.length != 2) {
                throw new ParsingException("Wrong attribute format.");
            }

            String name = parts[0];
            String value = parts[1].replaceAll("\"", "");
            if (value.endsWith(";")) {
                value = value.substring(0, value.length() - 1);
            }

            result.put(name, value);
        }

        return result;
    }

    private Kind createKind(Matcher matcher) throws ParsingException {
        String term = matcher.group(GROUP_TERM);
        String scheme = matcher.group(GROUP_SCHEME);
        String title = matcher.group(GROUP_TITLE);
        String location = matcher.group(GROUP_LOCATION);
        String attributes = matcher.group(GROUP_ATTRIBUTES);

        try {
            Set<Attribute> parsedAttributes = parseAttributes(attributes);
            URI locationUri = null;
            if (location != null) {
                locationUri = new URI(location);
            }
            Kind kind = new Kind(new URI(scheme), term, title, locationUri, parsedAttributes);

            return kind;
        } catch (URISyntaxException ex) {
            throw new ParsingException("Invalid shceme or location.", ex);
        }
    }

    private Mixin createMixin(Matcher matcher) throws ParsingException {
        String term = matcher.group(GROUP_TERM);
        String scheme = matcher.group(GROUP_SCHEME);
        String title = matcher.group(GROUP_TITLE);
        String location = matcher.group(GROUP_LOCATION);
        String attributes = matcher.group(GROUP_ATTRIBUTES);

        try {
            URI locationUri = null;
            if (location != null) {
                locationUri = new URI(location);
            }
            Set<Attribute> parsedAttributes = parseAttributes(attributes);
            Mixin mixin = new Mixin(new URI(scheme), term, title, locationUri, parsedAttributes);

            return mixin;
        } catch (URISyntaxException ex) {
            throw new ParsingException("Invalid shceme or location.", ex);
        }
    }

    private Link createLink(Matcher matcher) throws ParsingException {
        String uri = matcher.group(GROUP_URI);
        String rel = matcher.group(GROUP_REL);
        String self = matcher.group(GROUP_SELF);
        String category = matcher.group(GROUP_CATEGORY);
        String attributes = matcher.group(GROUP_ATTRIBUTES);

        try {
            Kind kind;
            if (category != null && !category.isEmpty()) {
                String[] splitedCategory = category.split("#");
                if (splitedCategory.length != 2) {
                    throw new ParsingException("Invalid link category");
                }
                kind = new Kind(new URI(splitedCategory[0] + "#"), splitedCategory[1]);
            } else {
                kind = new Kind(Link.getSchemeDefault(), Link.getTermDefault());
            }

            Link link;
            if (self != null && !self.isEmpty()) {
                String[] splitedSelf = divideUriByLastSegment(self);
                kind.setLocation(new URI(splitedSelf[1]));
                link = new Link(splitedSelf[0], kind);
            } else {
                link = new Link(UUID.randomUUID().toString(), kind);
            }

            link.setTarget(uri);
            link.setRelation(rel);
            Map<String, String> attributesWithValues = parseAttributesWithValues(attributes.split(";"));
            for (String name : attributesWithValues.keySet()) {
                link.addAttribute(name, attributesWithValues.get(name));
            }

            return link;
        } catch (InvalidAttributeValueException ex) {
            throw new ParsingException("Invalid attribute value found", ex);
        } catch (URISyntaxException ex) {
            throw new ParsingException("Invalid shceme or location.", ex);
        }
    }

    private Action createAction(String rel) throws ParsingException {
        if (rel == null || rel.isEmpty()) {
            throw new ParsingException("Link for action is missing 'rel' element.");
        }

        String[] splited = rel.split("#");
        if (splited.length != 2) {
            throw new ParsingException("Invalid relation specification: " + rel);
        }

        return createAction(splited[0] + "#", splited[1], null, null);
    }

    private Action createAction(Matcher matcher) throws ParsingException {
        String term = matcher.group(GROUP_TERM);
        String scheme = matcher.group(GROUP_SCHEME);
        String title = matcher.group(GROUP_TITLE);
        String attributes = matcher.group(GROUP_ATTRIBUTES);

        return createAction(scheme, term, title, attributes);
    }

    private Action createAction(String scheme, String term, String title, String attributes) throws ParsingException {
        Set<Attribute> parsedAttributes = parseAttributes(attributes);
        Action action = null;
        try {
            action = new Action(new URI(scheme), term, title, parsedAttributes);
        } catch (URISyntaxException ex) {
            throw new ParsingException("Invalid URI.", ex);
        }
        return action;
    }

    public static String[] divideUriByLastSegment(String uri) {
        String[] parts = new String[2];
        parts[0] = uri.substring(uri.lastIndexOf('/') + 1);
        parts[1] = uri.substring(0, uri.lastIndexOf('/') + 1);

        return parts;
    }
}
