package cz.cesnet.cloud.occi.core;

import static org.junit.Assert.fail;
import org.junit.Test;
import org.junit.Before;

public class CategoryTest {

    private Category category;

    @Before
    public void setUp() {
        category = new Category(Category.SCHEME_CORE_DEFAULT, Entity.TERM_DEFAULT);
    }

    @Test
    public void testInvalidSetTerm() {
        try {
            category.setTerm(null);
            fail();
        } catch (NullPointerException ex) {
            //cool
        }

        try {
            category.setTerm("");
            fail();
        } catch (IllegalArgumentException ex) {
            //cool
        }
    }

    @Test
    public void testInvalidSetScheme() {
        try {
            category.setScheme(null);
            fail();
        } catch (NullPointerException ex) {
            //cool
        }
    }
}
