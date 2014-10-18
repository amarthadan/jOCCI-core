package cz.cesnet.cloud.occi.parser;

import cz.cesnet.cloud.occi.*;
import cz.cesnet.cloud.occi.core.*;

public interface Parser {

    /**
     *
     * @param model
     */
    Model parseModel(String model);

    /**
     *
     * @param entity
     * @param entityClass
     */
    Entity parseEntity(String entity, Class entityClass);
}
