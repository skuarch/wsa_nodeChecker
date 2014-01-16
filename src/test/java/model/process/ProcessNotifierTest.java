package model.process;

import java.io.IOException;
import java.net.Socket;
import junit.framework.TestCase;
import model.beans.Notifier;
import model.dao.DAO;
import model.net.ModelSocket;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author skuarch
 */
public class ProcessNotifierTest extends TestCase {
    
    public ProcessNotifierTest(String testName) {
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
     * Test of addNotifier method, of class ProcessNotifier.
     */
    public void testAddNotifier() {
        System.out.println("addNotifier");
        
        
    }

    /**
     * Test of getNotifiers method, of class ProcessNotifier.
     */
    public void testGetNotifiers() throws IOException {
        System.out.println("getNotifiers");
        
        ModelSocket ms = new ModelSocket(new Socket("192.168.208.9", 8080));
        JSONObject jsono = new JSONObject();
        jsono.accumulate("request", "getNotifiers");
        
        Notifier n = new Notifier();
        n.setName("1");
        n.setPort(1);
        n.setHost("1");
        
        DAO.create(n);
        
        n.setName("2");
        n.setPort(2);
        n.setHost("2");
        
        DAO.create(n);
        
        ProcessNotifier pn = new ProcessNotifier(ms, jsono);        
        
        pn.getNotifiers();
        
        
        String j = "{\"notifiers\":[{\"port\":1,\"id\":1,\"host\":\"1\",\"name\":\"1\"},{\"port\":1,\"id\":2,\"host\":\"1\",\"name\":\"1\"},{\"port\":2,\"id\":3,\"host\":\"2\",\"name\":\"2\"}]}";
        
        JSONObject object = new JSONObject(j);
        System.out.println("madres "  + object.getJSONArray("notifiers").get(0));
        
    }
    
}
