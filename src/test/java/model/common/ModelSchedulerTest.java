package model.common;

import java.util.HashSet;
import java.util.Set;
import junit.framework.TestCase;
import model.beans.NetworkNode;
import model.beans.Scheduler;
import model.dao.DAO;

/**
 *
 * @author skuarch
 */
public class ModelSchedulerTest extends TestCase {
    
    public ModelSchedulerTest(String testName) {
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
     * Test of getScheduler method, of class ModelScheduler.
     */
    public void testGetScheduler_long() {
        
        System.out.println("------------------------------------------------------------------------");
        System.out.println("getScheduler by name");
        
        Scheduler scheduler = new Scheduler();        
        scheduler.setName("uno");
        
        new DAO().create(scheduler);
        
        Scheduler s = ModelScheduler.getSchedulerByName("uno");
        System.out.println(s);
        
        new DAO().delete(scheduler);
        
    }

    /**
     * Test of getScheduler method, of class ModelScheduler.
     */
    public void testGetScheduler_String() {
        
        System.out.println("------------------------------------------------------------------------");
        System.out.println("getScheduler");
        
        Scheduler scheduler = new Scheduler();        
        scheduler.setName("uno");
        
        long id = new DAO().create(scheduler);        
        
        Scheduler s = ModelScheduler.getScheduler(id);
        System.out.println(s);
        
        new DAO().delete(scheduler);
        
    }

    /**
     * Test of createScheduler method, of class ModelScheduler.
     */
    public void testCreateScheduler() {
        
        System.out.println("------------------------------------------------------------------------");
        System.out.println("createScheduler");
        
        Scheduler s = new Scheduler();
        s.setName("uno"); 
        
        NetworkNode networkNode = new NetworkNode();
        networkNode.setHost("uno");
        
        Set<NetworkNode> nodes = new HashSet<NetworkNode>();
        nodes.add(networkNode);
        
        s.setNodes(nodes);
        
        ModelScheduler.createScheduler(s);
        
        ModelScheduler.deleteScheduler(s);
        
        //new DAO().delete(networkNode);
        
    }

    /**
     * Test of deleteScheduler method, of class ModelScheduler.
     */
    public void testDeleteScheduler() {
        
        System.out.println("------------------------------------------------------------------------");
        System.out.println("deleteScheduler");
        
        Scheduler s = new Scheduler();
        s.setName("uno");       
        
        ModelScheduler.createScheduler(s);
        
        ModelScheduler.deleteScheduler(s);
        
    }

    /**
     * Test of getSchedulers method, of class ModelScheduler.
     */
    public void testGetSchedulers() {
        
        System.out.println("------------------------------------------------------------------------");
        System.out.println("getSchedulers");
        
        Scheduler s = new Scheduler();
        s.setName("uno");       
        ModelScheduler.createScheduler(s);
        
        Scheduler s2 = new Scheduler();
        s2.setName("dos");       
        ModelScheduler.createScheduler(s2);
        
        Scheduler s3 = new Scheduler();
        s3.setName("tres");       
        ModelScheduler.createScheduler(s3);
        
        System.out.println(ModelScheduler.getSchedulers());
        
        ModelScheduler.deleteScheduler(s);
        ModelScheduler.deleteScheduler(s2);
        ModelScheduler.deleteScheduler(s3);        
        
    }
    
}
