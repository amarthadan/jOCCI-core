package cz.cesnet.cloud.occi.core;

import org.junit.Test;
import org.junit.Before;

public class CategoryTest {

    private Category category;

    @Before
    public void setUp() {
        category = new Category(Category.SCHEME_CORE_DEFAULT, Entity.getTermDefault());
    }

    @Test
    public void testInvalidSetTerm() {
        try {
            category.setTerm(null);
        } catch (NullPointerException ex) {
            //cool
        }

        try {
            category.setTerm("");
        } catch (IllegalArgumentException ex) {
            //cool
        }
    }

    @Test
    public void testInvalidSetScheme() {
        try {
            category.setScheme(null);
        } catch (NullPointerException ex) {
            //cool
        }
    }
}
