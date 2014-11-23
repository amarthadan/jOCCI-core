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

    public static final String REGEXP_CATEGORY = "(?<term>" + REGEXP_TERM + ")" // term (mandatory)
            + ";\\s*scheme=\"(?<scheme>" + REGEXP_SCHEME + ")(?:" + REGEXP_TERM + ")?\"" // scheme (mandatory)
            + ";\\s*class=\"?(?<class>" + REGEXP_CLASS + ")\"?" // class (mandatory)
            + "(;\\s*title=\"(?<title>" + REGEXP_QUOTED_STRING + ")\")?" // title (optional)
            + "(;\\s*rel=\"(?<rel>" + REGEXP_TYPE_IDENTIFIER_LIST + ")\")?" // rel (optional)
            + "(;\\s*location=\"(?<location>" + REGEXP_URI_REF + ")\")?" // location (optional)
            + "(;\\s*attributes=\"(?<attributes>" + REGEXP_ATTRIBUTE_LIST + ")\")?" // attributes (optional)
            + "(;\\s*actions=\"(?<actions>" + REGEXP_ACTION_LIST + ")\")?" // actions (optional)
            + ";?"; // additional semicolon at the end (not specified, for interoperability)

    public static final String REGEXP_ATTRIBUTES = "(" + REGEXP_ATTRIBUTE_DEF + ")";

    public static final String REGEXP_LINK = "\\<(?<uri>" + REGEXP_URI_REF + ")\\>" // uri (mandatory)
            + ";\\s*rel=\"(?<rel>" + REGEXP_RESOURCE_TYPE + ")\"" // rel (mandatory)
            + "(;\\s*self=\"(?<self>" + REGEXP_LINK_INSTANCE + ")\")?" // self (optional)
            + "(;\\s*category=\"(?<category>(;?\\s*(" + REGEXP_LINK_TYPE + "))+)\")?" // category (optional)
            + "(;\\s*(?<attributes>(;?\\s*" + REGEXP_ATTRIBUTE_REPR + ")*))?" // attributes (optional)
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
            String term = matcher.group("term");
            String scheme = matcher.group("scheme");
            String categoryClass = matcher.group("class");
            String title = matcher.group("title");
            String rel = matcher.group("rel");
            String location = matcher.group("location");
            String attributes = matcher.group("attributes");
            String actions = matcher.group("actions");
            LOGGER.debug("Match: term={}, scheme={}, class={}, title={}, rel={}, location={}, attributes={}, actions={}", term, scheme, categoryClass, title, rel, location, attributes, actions);

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
                    addKind(term, scheme, title, rel, location, attributes, actions, model);
                    break;
                case "mixin":
                    addMixin(term, scheme, title, rel, location, attributes, actions, model);
                    break;
                case "action":
                    addAction(term, scheme, title, location, attributes, model);
                    break;
                default:
                    throw new ParsingException("Unknown class type.");
            }
        }

        return model;
    }

    private void addKind(String term, String scheme, String title, String rel, String location, String attributes, String actions, Model model) throws ParsingException {
        LOGGER.debug("Adding kind...");
        Kind kind = createKind(term, scheme, title, location, attributes);
        connectActions(actions, kind, model);

        if (rel != null && !rel.isEmpty()) {
            if (!model.containsKind(rel)) {
                throw new ParsingException("Unexpected relation " + rel + " in kind " + term + ".");
            }
            Kind k = model.getKind(rel);
            LOGGER.debug("Creating relation between {} and {}.", kind, k);
            kind.addRelation(k);
        }

        model.addKind(kind);
    }

    private void addMixin(String term, String scheme, String title, String rel, String location, String attributes, String actions, Model model) throws ParsingException {
        LOGGER.debug("Adding mixin...");
        Mixin mixin = createMixin(term, scheme, title, location, attributes);
        connectActions(actions, mixin, model);

        if (rel != null && !rel.isEmpty()) {
            if (!model.containsMixin(rel)) {
                throw new ParsingException("Unexpected relation " + rel + " in mixin " + term + ".");
            }
            Mixin m = model.getMixin(rel);
            LOGGER.debug("Creating relation between {} and {}.", mixin, m);
            mixin.addRelation(m);
        }

        model.addMixin(mixin);
    }

    private void connectActions(String actions, Category category, Model model) throws ParsingException {
        LOGGER.debug("Connecting actions...");
        if (actions == null || actions.isEmpty()) {
            return;
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
    }

    private void addAction(String term, String scheme, String title, String location, String attributes, Model model) throws ParsingException {
        LOGGER.debug("Adding action...");
        String actionIdentifier = scheme + term;
        try {
            if (model.containsAction(actionIdentifier)) {
                Set<Attribute> parsedAttributes = parseAttributes(attributes);
                URI locationUri = null;

                if (location != null) {
                    locationUri = new URI(location);
                }
                Action action = model.getAction(actionIdentifier);
                action.setTitle(title);
                action.setLocation(locationUri);
                for (Attribute attribute : parsedAttributes) {
                    action.addAttribute(attribute);
                }
            } else {
                Action action = createAction(scheme, term, title, location, attributes);
                model.addAction(action);
            }
        } catch (URISyntaxException ex) {
            throw new ParsingException("Invalid shceme or location.", ex);
        }
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

        String replaced = body.replaceAll("Category:\\s*", "");
        replaced = replaced.replaceAll("Link:\\s*", "");
        replaced = replaced.replaceAll("X-OCCI-Attribute:\\s*", "");
        String[] lines = replaced.split("[\\r\\n]+");

        return parseCollectionFromArray(lines, collectionType);
    }

    private Collection parseCollectionFromArray(String[] lines, CollectionType collectionType) throws ParsingException {
        Kind kind = null;
        ActionInstance actionInstance = null;
        Set<Mixin> mixins = new HashSet<>();
        Set<Link> links = new HashSet<>();
        List<String> rawAttributes = new ArrayList<>();

        for (String line : lines) {
            LOGGER.debug("Matching line '{}' against category pattern.", line);
            Matcher matcher = PATTERN_CATEGORY.matcher(line);
            if (matcher.find()) {
                String term = matcher.group("term");
                String scheme = matcher.group("scheme");
                String categoryClass = matcher.group("class");
                String title = matcher.group("title");
                String location = matcher.group("location");
                String attributes = matcher.group("attributes");
                String actions = matcher.group("actions");

                LOGGER.debug("Match: term={}, scheme={}, class={}, title={}, location={}, attributes={}, actions={}", term, scheme, categoryClass, title, location, attributes, actions);

                switch (categoryClass) {
                    case "kind":
                        kind = createKind(term, scheme, title, location, attributes);
                        connectActions(actions, kind, null);
                        break;
                    case "mixin":
                        Mixin mixin = createMixin(term, scheme, title, location, attributes);
                        connectActions(actions, mixin, null);
                        mixins.add(mixin);
                        break;
                    case "action":
                        Action action = createAction(scheme, term, title, location, attributes);
                        actionInstance = new ActionInstance(action);
                        break;
                    default:
                        throw new ParsingException("Unknown category class '" + categoryClass + "'.");
                }

                continue;
            }

            LOGGER.debug("Matching line '{}' against attribute pattern.", line);
            if (line.matches(REGEXP_ATTRIBUTE_REPR)) {
                rawAttributes.add(line);
                continue;
            }

            LOGGER.debug("Matching line '{}' against link pattern.", line);
            matcher = PATTERN_LINK.matcher(line);
            if (matcher.find()) {
                String uri = matcher.group("uri");
                String rel = matcher.group("rel");
                String self = matcher.group("self");
                String category = matcher.group("category");
                String attributes = matcher.group("attributes");

                LOGGER.debug("Match: uri={}, rel={}, self={}, category={}, attributes={}", uri, rel, self, category, attributes);

                Link link = createLink(uri, rel, self, category, attributes);
                links.add(link);
            }
        }

        Map<String, String> attributesWithValues = parseAttributesWithValues(rawAttributes.toArray(new String[0]));
        Collection collection = new Collection();

        switch (collectionType) {
            case RESOURCE:
                if (kind == null) {
                    throw new ParsingException("No kind specification found.");
                }
                if (!attributesWithValues.containsKey(Resource.ID_ATTRIBUTE_NAME)) {
                    throw new ParsingException("No id found. Cannot construct a resource.");
                }

                Resource resource = null;
                try {
                    resource = new Resource(attributesWithValues.get(Entity.ID_ATTRIBUTE_NAME), kind);

                    attributesWithValues.remove(Entity.ID_ATTRIBUTE_NAME);
                    for (Mixin mixin : mixins) {
                        resource.addMixin(mixin);
                    }
                    for (Link link : links) {
                        link.setSource(resource);
                        resource.addLink(link);
                    }
                    for (String name : attributesWithValues.keySet()) {
                        resource.addAttribute(name, attributesWithValues.get(name));
                    }
                } catch (InvalidAttributeValueException ex) {
                    throw new ParsingException("Invalid attribute value found", ex);
                }
                collection.addResource(resource);
                break;
            case LINK:
                if (kind == null) {
                    throw new ParsingException("No kind specification found.");
                }
                if (!attributesWithValues.containsKey(Resource.ID_ATTRIBUTE_NAME)) {
                    throw new ParsingException("No id found. Cannot construct a resource.");
                }

                Link link = null;
                try {
                    link = new Link(attributesWithValues.get(Entity.ID_ATTRIBUTE_NAME), kind);

                    attributesWithValues.remove(Entity.ID_ATTRIBUTE_NAME);
                    for (Mixin mixin : mixins) {
                        link.addMixin(mixin);
                    }
                    for (String name : attributesWithValues.keySet()) {
                        link.addAttribute(name, attributesWithValues.get(name));
                    }
                } catch (InvalidAttributeValueException ex) {
                    throw new ParsingException("Invalid attribute value found", ex);
                }
                collection.addLink(link);
                break;
            case ACTION:
                if (actionInstance == null) {
                    throw new ParsingException("No action specification found.");
                }

                for (String name : attributesWithValues.keySet()) {
                    actionInstance.addAttribute(new Attribute(name), attributesWithValues.get(name));
                }
                collection.addAction(actionInstance);
                break;
            default:
                throw new ParsingException("Unknown collection type'" + collectionType + "'.");
        }

        return collection;
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

    private Kind createKind(String term, String scheme, String title, String location, String attributes) throws ParsingException {
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

    private Mixin createMixin(String term, String scheme, String title, String location, String attributes) throws ParsingException {
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

    private Link createLink(String uri, String rel, String self, String category, String attributes) throws ParsingException {
        try {
            Kind kind;
            if (category != null && !category.isEmpty()) {
                String[] splitedCategory = category.split("#");
                if (splitedCategory.length != 2) {
                    throw new ParsingException("Invalid link category");
                }
                kind = new Kind(new URI(splitedCategory[0] + "#"), splitedCategory[1]);
            } else {
                kind = new Kind(Link.getSchemeDefault(), Link.getTermDefult());
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

    private String[] divideUriByLastSegment(String uri) {
        String[] parts = new String[2];
        parts[0] = uri.substring(uri.lastIndexOf('/') + 1);
        parts[1] = uri.substring(0, uri.lastIndexOf('/') + 1);

        return parts;
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
                throw new ParsingException("Invalid location: " + location + ".");
            }
        }

        return locationsURI;
    }

    private Action createAction(String scheme, String term, String title, String location, String attributes) throws ParsingException {
        Set<Attribute> parsedAttributes = parseAttributes(attributes);
        URI locationUri = null;
        Action action = null;
        try {
            if (location != null) {
                locationUri = new URI(location);
            }

            action = new Action(new URI(scheme), term, title, locationUri, parsedAttributes);
        } catch (URISyntaxException ex) {
            throw new ParsingException("Invalid URI.");
        }
        return action;
    }
}
