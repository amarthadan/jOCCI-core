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
     * @param mediaType
     * @param body
     * @param headers
     * @return OCCI model
     * @throws ParsingException
     */
    Model parseModel(String mediaType, String body, Headers headers) throws ParsingException;

    /**
     * Parses an OCCI entity either from body or headers depending on mediaType
     * and collectionType.
     *
     * @param mediaType
     * @param body
     * @param headers
     * @param collectionType
     * @return
     * @throws ParsingException
     */
    Collection parseCollection(String mediaType, String body, Headers headers, CollectionType collectionType) throws ParsingException;

    /**
     * Parses a list of locations either from body or headers depending on
     * mediaType.
     *
     * @param mediaType
     * @param body
     * @param headers
     * @return
     * @throws ParsingException
     */
    List<URI> parseLocations(String mediaType, String body, Headers headers) throws ParsingException;
}
