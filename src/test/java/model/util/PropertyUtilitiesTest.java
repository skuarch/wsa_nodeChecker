package model.util;

import java.util.Properties;
import junit.framework.TestCase;
import org.json.JSONObject;

/**
 *
 * @author skuarch
 */
public class PropertyUtilitiesTest extends TestCase {
    
    public PropertyUtilitiesTest(String testName) {
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
     * Test of getProperties method, of class PropertyUtilities.
     */
    public void testGetProperties() throws Exception {
        
        /*System.out.println("testing getProperties");        
        
        PropertyUtilities instance = new PropertyUtilities("configuration/application.properties");
        System.out.println("instance was created");
        
        int port = instance.getIntPropertie("listen.port");
        System.out.println("the application will to listen port " + port);*/
        
    }    

    /**
     * Test of getStringPropertie method, of class PropertyUtilities.
     */
    public void testGetStringPropertie() throws Exception {
        /*System.out.println("getStringPropertie");
        String key = " ";
        PropertyUtilities instance = new PropertyUtilities("configuration/application.properties");
        String expResult = null;
        String result = instance.getStringPropertie(key);
        assertEquals(expResult, result);*/
        
    }

    /**
     * Test of storeProperties method, of class PropertyUtilities.
     */
    public void testStoreProperties() throws Exception {
        
        System.out.println("------------------------------------------------------------------------");
        System.out.println("storeProperties");
        
        PropertyUtilities instance = new PropertyUtilities("configuration/configuration.properties");
        
        Properties properties = instance.getProperties();
        
        System.out.println(properties);
        
        properties.setProperty("application.name", "skuarch-lap");
        //properties.put("application.name", "juana es re puta");
        
        System.out.println(properties);
        
        instance.storeProperties(properties);
        
    }

    /**
     * Test of getIntPropertie method, of class PropertyUtilities.
     */
    public void testGetIntPropertie() throws Exception {
        /*System.out.println("getIntPropertie");
        String key = " ";
        PropertyUtilities instance = new PropertyUtilities("configuration/application.properties");
        int expResult = 0;
        int result = instance.getIntPropertie(key);
        assertEquals(expResult, result);*/
        
        JSONObject jsono = new JSONObject();
        
        jsono.accumulate("mcoos.mocos", "/132/123");
        System.out.println(jsono);
        
    }
}
