package cz.cesnet.cloud.occi.core;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Category {

    public static final URI DEFAULT_SCHEME = makeDefaultURI("http://schemas.ogf.org/occi/core#");

    public static URI makeDefaultURI(String uri) {
        try {
            return new URI(uri);
        } catch (URISyntaxException ex) {
            Logger.getLogger(Category.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private String term;
    private URI scheme;
    private String title;
    private List<Attribute> attributes;
}
