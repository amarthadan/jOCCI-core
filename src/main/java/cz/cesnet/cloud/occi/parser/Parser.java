package cz.cesnet.cloud.occi.parser;

import com.sun.net.httpserver.Headers;
import cz.cesnet.cloud.occi.Collection;
import cz.cesnet.cloud.occi.Model;
import cz.cesnet.cloud.occi.exception.ParsingException;
import java.net.URI;
import java.util.List;

/**
 * Interface for parser of OCCI messages.
 *
 * @author Michal Kimle <kimle.michal@gmail.com>
 */
public interface Parser {

    /**
     * Parses an OCCI model either from body or headers depending on mediaType.
     *
     * @param mediaType media type of the parsed server response
     * @param body body of the server response
     * @param headers headers of the server response
     * @return OCCI model
     * @throws ParsingException when error occures during the parsing
     */
    Model parseModel(String mediaType, String body, Headers headers) throws ParsingException;

    /**
     * Parses an OCCI entity either from body or headers depending on mediaType
     * and collectionType.
     *
     * @param mediaType media type of the parsed server response
     * @param body body of the server response
     * @param headers headers of the server response
     * @param collectionType collection type representing entities that will be
     * parsed from the response
     * @return collection of parsed entities
     * @throws ParsingException when error occures during the parsing
     */
    Collection parseCollection(String mediaType, String body, Headers headers, CollectionType collectionType) throws ParsingException;

    /**
     * Parses a list of locations either from body or headers depending on
     * mediaType.
     *
     * @param mediaType media type of the parsed server response
     * @param body body of the server response
     * @param headers headers of the server response
     * @return list of locations
     * @throws ParsingException when error occures during the parsing
     */
    List<URI> parseLocations(String mediaType, String body, Headers headers) throws ParsingException;
}
