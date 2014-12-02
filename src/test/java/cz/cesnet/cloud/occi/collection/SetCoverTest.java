package cz.cesnet.cloud.occi.collection;

import cz.cesnet.cloud.occi.core.Mixin;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class SetCoverTest {

    private SetCover<Mixin> set = new SetCover<>();

    @Before
    public void setUp() throws Exception {
        set.add(new Mixin(new URI("http://dummy.mixin1/"), "term1"));
        set.add(new Mixin(new URI("http://dummy.mixin2/"), "term2"));
        set.add(new Mixin(new URI("http://dummy.mixin3/"), "term3"));
    }

    @Test
    public void testContainsWithGenericType() throws Exception {
        assertTrue(set.contains(new Mixin(new URI("http://dummy.mixin1/"), "term1")));
        assertFalse(set.contains(new Mixin(new URI("http://nonexisting.mixin/"), "aaa")));
    }

    @Test
    public void testContainsWithString() {
        assertTrue(set.contains("http://dummy.mixin1/term1"));
        assertFalse(set.contains("http://nonexisting.mixin/aaa"));
    }

    @Test
    public void testAdd() throws Exception {
        SetCover<Mixin> set = new SetCover<>();
        assertEquals(0, set.size());
        set.add(new Mixin(new URI("http://dummy.mixin1/"), "term1"));
        assertEquals(1, set.size());
        assertTrue(set.contains("http://dummy.mixin1/term1"));
    }

    @Test
    public void testInvalidAdd() {
        try {
            set.add(null);
            fail();
        } catch (NullPointerException ex) {
            //cool
        }
    }

    @Test
    public void testAddAll() throws Exception {
        List<Mixin> list = new ArrayList<>();
        list.add(new Mixin(new URI("http://dummy.mixin1/"), "term1"));
        list.add(new Mixin(new URI("http://dummy.mixin2/"), "term2"));
        list.add(new Mixin(new URI("http://dummy.mixin3/"), "term3"));

        SetCover<Mixin> set = new SetCover<>();
        assertEquals(0, set.size());
        set.addAll(list);
        assertEquals(3, set.size());
    }

    @Test
    public void testInvalidAddAll() throws Exception {
        List<Mixin> list = new ArrayList<>();
        list.add(new Mixin(new URI("http://dummy.mixin1/"), "term1"));
        list.add(null);
        list.add(new Mixin(new URI("http://dummy.mixin3/"), "term3"));

        try {
            set.addAll(list);
            fail();
        } catch (NullPointerException ex) {
            //cool
        }
    }

    @Test
    public void testGet() throws Exception {
        assertEquals(new Mixin(new URI("http://dummy.mixin1/"), "term1"), set.get("http://dummy.mixin1/term1"));
        assertNull(set.get("nonexisting_element"));
    }

    @Test
    public void testRemove() throws Exception {
        assertEquals(3, set.size());
        set.remove(new Mixin(new URI("http://dummy.mixin1/"), "term1"));
        assertEquals(2, set.size());
        assertFalse(set.contains("http://dummy.mixin1/term1"));
    }

    @Test
    public void testInvalidRemove() {
        try {
            set.remove(null);
            fail();
        } catch (NullPointerException ex) {
            //cool
        }
    }

    @Test
    public void testClear() {
        assertEquals(3, set.size());
        set.clear();
        assertEquals(0, set.size());
    }

    @Test
    public void testGetSet() throws Exception {
        Set<Mixin> expected = new HashSet<>();
        expected.add(new Mixin(new URI("http://dummy.mixin1/"), "term1"));
        expected.add(new Mixin(new URI("http://dummy.mixin2/"), "term2"));
        expected.add(new Mixin(new URI("http://dummy.mixin3/"), "term3"));

        assertEquals(expected, set.getSet());
    }

    @Test
    public void testSize() throws Exception {
        assertEquals(3, set.size());
        set.add(new Mixin(new URI("http://dummy.mixin4/"), "term4"));
        assertEquals(4, set.size());
        set.remove(new Mixin(new URI("http://dummy.mixin2/"), "term2"));
        assertEquals(3, set.size());
    }
}
