package cz.cesnet.cloud.occi.parser;

import cz.cesnet.cloud.occi.Model;
import cz.cesnet.cloud.occi.core.Resource;
import cz.cesnet.cloud.occi.exception.ParsingException;
import java.util.List;
import java.util.Map;

public interface Parser {

    Model parseModel(MediaType mediaType, String body, Map<String, String> headers) throws ParsingException;

    Resource parseResource(MediaType mediaType, String body, Map<String, String> headers) throws ParsingException;

    List<String> parseLocations(MediaType mediaType, String body, Map<String, String> headers) throws ParsingException;
}
