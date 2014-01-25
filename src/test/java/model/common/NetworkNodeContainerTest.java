package model.common;

import java.util.ArrayList;
import junit.framework.TestCase;
import model.beans.NetworkNode;
import model.beans.Scheduler;

/**
 *
 * @author skuarch
 */
public class NetworkNodeContainerTest extends TestCase {
    
    public NetworkNodeContainerTest(String testName) {
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
     * Test of addArrayList method, of class NetworkNodeContainer.
     */
    public void testAddArrayList() {
        
        System.out.println("------------------------------------------------------------------------");
        System.out.println("addArrayList");       
                 
        ArrayList<NetworkNode> nodes = new ArrayList<>();        
        NetworkNodeContainer.addArrayList("one", nodes);
        
    }

    /**
     * Test of removeArrayList method, of class NetworkNodeContainer.
     */
    public void testRemoveArrayList() {
        
        System.out.println("------------------------------------------------------------------------");
        System.out.println("removeArrayList");
        
        ArrayList<NetworkNode> nodes = new ArrayList<>();        
        NetworkNodeContainer.addArrayList("tow", nodes);        
        NetworkNodeContainer.removeArrayList("two");
        
    }

    /**
     * Test of getArrayList method, of class NetworkNodeContainer.
     */
    public void testGetArrayList() {
        
        System.out.println("------------------------------------------------------------------------");
        System.out.println("getArrayList");       
        
        
        ArrayList<NetworkNode> nodes = new ArrayList<>();        
        NetworkNodeContainer.addArrayList("three", nodes);        
        System.out.println(NetworkNodeContainer.getArrayList("three"));
        
        
    }

    /**
     * Test of updateArrayList method, of class NetworkNodeContainer.
     */
    public void testUpdateArrayList() {
        
        System.out.println("------------------------------------------------------------------------");
        System.out.println("updateArrayList");
        
        ArrayList<NetworkNode> nodes = new ArrayList<>();
        
        NetworkNodeContainer.addArrayList("four", nodes);        
        nodes.add(new NetworkNode("four",1,new Scheduler()));        
        System.out.println(NetworkNodeContainer.getArrayList("four"));
        
    }
    
}
