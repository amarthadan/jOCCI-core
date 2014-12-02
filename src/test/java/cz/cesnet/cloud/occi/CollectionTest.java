package cz.cesnet.cloud.occi;

import cz.cesnet.cloud.occi.core.Action;
import cz.cesnet.cloud.occi.core.ActionInstance;
import cz.cesnet.cloud.occi.core.Kind;
import cz.cesnet.cloud.occi.core.Link;
import cz.cesnet.cloud.occi.core.Resource;
import java.net.URI;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class CollectionTest {

    public CollectionTest() {
    }

    @Test
    public void testSetModel() throws Exception {
        Kind kind = new Kind(new URI("http://dummy.kind"), "term");
        Resource resource = new Resource("resource_id", kind);
        Link link = new Link("link_id", kind);
        ActionInstance ai = new ActionInstance(new Action(new URI("http://dummy.action"), "term"));

        Collection collection = new Collection();
        collection.addAction(ai);
        collection.addLink(link);
        collection.addResource(resource);

        Model model = new Model();
        model.addKind(kind);

        collection.setModel(model);

        assertEquals(model, resource.getModel());
        assertEquals(model, link.getModel());
        assertEquals(model, ai.getModel());
    }
}
