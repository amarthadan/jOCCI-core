package cz.cesnet.cloud.occi.core;

import java.util.*;

public class Resource extends Entity {

    Link target;
    Collection<Link> source;
    private String summary;
    private List<Link> links;

    @Override
    public void toText() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void toJSON() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
