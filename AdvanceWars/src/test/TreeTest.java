package test;

import gameframework.core.GameEntity;
import org.junit.Before;
import org.junit.Test;
import util.Tree;

import static org.junit.Assert.assertNotNull;

/**
 * Created by alan on 19/01/17.
 */
public class TreeTest {

    private Tree<String,GameEntity> keySearch;

    @Before
    public void setup()
    {
        keySearch = new Tree<>(true,"5",2);
        keySearch.put("78aa;",null);
        keySearch.put("57",null);
    }

    @Test
    public void containsKey() throws Exception {
        assertNotNull(keySearch.containsKey("5"));
        assertNotNull(keySearch.containsKey("78aa;"));
        assertNotNull(keySearch.containsKey("57"));
    }

}