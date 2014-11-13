package cz.cesnet.cloud.occi.parser;

import cz.cesnet.cloud.occi.Collection;
import cz.cesnet.cloud.occi.Model;
import cz.cesnet.cloud.occi.exception.ParsingException;
import java.util.List;
import java.util.Map;

public class JSONParser implements Parser {

    @Override
    public Model parseModel(MediaType mediaType, String body, Map<String, String> headers) throws ParsingException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection parseCollection(MediaType mediaType, String body, Map<String, String> headers) throws ParsingException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> parseLocations(MediaType mediaType, String body, Map<String, String> headers) throws ParsingException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
