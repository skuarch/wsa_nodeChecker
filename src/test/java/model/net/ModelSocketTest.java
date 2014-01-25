package model.net;

import java.io.IOException;
import java.net.Socket;
import junit.framework.TestCase;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author skuarch
 */
public class ModelSocketTest extends TestCase {
    
    public ModelSocketTest(String testName) {
        super(testName);
        PropertyConfigurator.configure("configuration/log4j.properties");
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
     * Test of receive method, of class ModelSocket.
     */
    public void testReceive() throws IOException {
        /*System.out.println("receive");
        
        Socket socket = new Socket("gdc.com.mx", 80);
        
        ModelSocket instance = new ModelSocket(socket);
        instance.setKeepAlive(false);
        instance.setSoTimeout(3000);
        String expResult = null;
        String result = instance.receive();
        assertEquals(expResult, result);
        */
    }

    /**
     * Test of send method, of class ModelSocket.
     */
    public void testSend() throws IOException {
        /*System.out.println("send");
        
        Socket socket = new Socket("gdc.com.mx", 80);
        
        String text = "";
        ModelSocket instance = new ModelSocket(socket);
        instance.send(text);*/
        
    }

    /**
     * Test of closeStreams method, of class ModelSocket.
     */
    public void testCloseStreams() throws IOException {
        
        System.out.println("------------------------------------------------------------------------");
        System.out.println("closeStreams");
        
        Socket socket = new Socket("gdc.com.mx", 80);
        
        ModelSocket instance = new ModelSocket(socket);
        instance.closeStreams();
        
    }
    
}
