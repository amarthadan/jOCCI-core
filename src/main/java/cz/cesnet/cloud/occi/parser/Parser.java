package cz.cesnet.cloud.occi.parser;

import cz.cesnet.cloud.occi.Collection;
import cz.cesnet.cloud.occi.Model;
import cz.cesnet.cloud.occi.exception.ParsingException;
import java.util.List;
import java.util.Map;

public interface Parser {

    Model parseModel(MediaType mediaType, String body, Map<String, String> headers) throws ParsingException;

    Collection parseCollection(MediaType mediaType, String body, Map<String, String> headers) throws ParsingException;

    List<String> parseLocations(MediaType mediaType, String body, Map<String, String> headers) throws ParsingException;
    //obalit location nejakou uri triedou...a vlastne to pouzit na vsetky uri...
}
