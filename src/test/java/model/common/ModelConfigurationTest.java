package model.common;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import junit.framework.TestCase;

/**
 *
 * @author skuarch
 */
public class ModelConfigurationTest extends TestCase {
    
    public ModelConfigurationTest(String testName) {
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

    /**
     * Test of getHashMapConfiguration method, of class ModelConfiguration.
     */
    public void testGetConfiguration() throws IOException {
        
        System.out.println("getHashMapConfiguration");
        
        System.out.println(ModelConfiguration.getHashMapConfiguration());
        
    }
    
    
    /**
     * Test of getHashMapConfiguration method, of class ModelConfiguration.
     */
    public void testGetJSONConfiguration() throws IOException {
        
        System.out.println("getJSONConfiguration");
        
        System.out.println(ModelConfiguration.getJSONConfiguration());
        
    }

    /**
     * Test of getHashMapConfiguration method, of class ModelConfiguration.
     */
    public void testGetHashMapConfiguration() throws Exception {
        System.out.println("getHashMapConfiguration");
        HashMap<String, Object> expResult = null;
        HashMap<String, Object> result = ModelConfiguration.getHashMapConfiguration();
        //assertEquals(expResult, result);
        
    }

    /**
     * Test of saveConfiguration method, of class ModelConfiguration.
     */
    public void testSaveConfiguration() throws Exception {
        System.out.println("saveConfiguration");
        
        //Properties properties = new Properties(); // is possible but is a bad used
        Properties properties = new CustomProperties().getProperties();
        
        properties.setProperty("test", "delete");
        
        ModelConfiguration.saveConfiguration(properties);
        
    }
    
}
