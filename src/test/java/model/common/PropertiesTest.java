/*
 * To change this license header, choose License Headers in Project CustomProperties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.common;

import java.io.IOException;
import junit.framework.TestCase;

/**
 *
 * @author skuarch
 */
public class PropertiesTest extends TestCase {
    
    public PropertiesTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testSomeMethod() throws IOException {
       CustomProperties p = new CustomProperties();
       p.getProperties().setProperty("", "");
    }
    
}
