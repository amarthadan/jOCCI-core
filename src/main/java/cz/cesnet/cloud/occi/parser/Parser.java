package cz.cesnet.cloud.occi.parser;

import com.sun.net.httpserver.Headers;
import cz.cesnet.cloud.occi.Collection;
import cz.cesnet.cloud.occi.Model;
import cz.cesnet.cloud.occi.exception.ParsingException;
import java.net.URI;
import java.util.List;

public interface Parser {

    Model parseModel(String mediaType, String body, Headers headers) throws ParsingException;

    Collection parseCollection(String mediaType, String body, Headers headers, CollectionType collectionType) throws ParsingException;

    List<URI> parseLocations(String mediaType, String body, Headers headers) throws ParsingException;
}
