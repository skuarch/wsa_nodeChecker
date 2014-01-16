package model.net;

import junit.framework.TestCase;
import org.json.JSONObject;

/**
 *
 * @author skuarch
 */
public class ClientRestfulPostTest extends TestCase {

    public ClientRestfulPostTest(String testName) {
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
     * Test of send method, of class ClientRestfulPost.
     *
     * @throws java.lang.Exception
     */
    public void testSend() throws Exception {

        /*System.out.println("test restful client post");

        JSONObject json = new JSONObject();

        json.accumulate("request", "notification");
        json.accumulate("expectedReturn", "true");
        json.accumulate("url", "http://192.168.208.9:8080/sam5/notifications");

        //ClientRestfulPost instance = new ClientRestfulPost("http://192.168.207.21:8080/proxy/restful/v1/webserviceRemoteConnection");
        ClientRestfulPost instance = new ClientRestfulPost("http://192.168.208.9:8080/sam5/notifications");
        instance.send(json.toString());
        instance.post();
        System.out.println(instance.receive());        
        instance.closeClient();*/

    }
}
