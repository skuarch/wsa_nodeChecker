package model.common;

import junit.framework.TestCase;
import model.beans.NetworkNode;
import model.beans.Scheduler;
import model.dao.DAO;

/**
 *
 * @author skuarch
 */
public class ModelNetworkNodeTest extends TestCase {

    public ModelNetworkNodeTest(String testName) {
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
     * Test of createNetworkNode method, of class ModelNetworkNode.
     */
    public void testCreateNetworkNode() {
        System.out.println("------------------------------------------------------------------------");
        System.out.println("createNetworkNode");
        
        NetworkNode networkNode = new NetworkNode();
        networkNode.setHost("theHost");
        Scheduler scheduler = new Scheduler();
        scheduler.setName("theScheduler");
        
        new DAO().create(scheduler);

        networkNode.setScheduler(scheduler);

        System.out.println(ModelNetworkNode.createNetworkNode(networkNode));

        ModelNetworkNode.deleteNetworkNode(networkNode);
        
        ModelScheduler.deleteScheduler(scheduler);
        
    }

    /**
     * Test of deleteNetworkNode method, of class ModelNetworkNode.
     */
    public void testDeleteNetworkNode() {
        
        System.out.println("------------------------------------------------------------------------");
        System.out.println("deleteNetworkNode");
        
        NetworkNode networkNode = new NetworkNode();
        networkNode.setHost("theHost");
        Scheduler scheduler = new Scheduler();
        scheduler.setName("theScheduler");

        new DAO().create(scheduler);
        
        networkNode.setScheduler(scheduler);

        System.out.println(ModelNetworkNode.createNetworkNode(networkNode));

        ModelNetworkNode.deleteNetworkNode(networkNode);
        
        ModelScheduler.deleteScheduler(scheduler);
        
    }

}
