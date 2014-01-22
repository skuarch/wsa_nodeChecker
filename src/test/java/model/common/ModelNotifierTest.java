package model.common;

import junit.framework.TestCase;
import model.beans.Notifier;
import model.dao.DAO;

/**
 *
 * @author skuarch
 */
public class ModelNotifierTest extends TestCase {
    
    public ModelNotifierTest(String testName) {
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
     * Test of addNotifier method, of class ModelNotifier.
     */
    public void testAddNotifier() {
        System.out.println("addNotifier");
        
        Notifier n = new Notifier();
        n.setName("testdelete");
        n.setUrl("http://192.168.208.9:8080/sam5/notifications");        
        
        System.out.println(ModelNotifier.addNotifier(n));
        ModelNotifier.removeNotifier(n);
        
    }

    /**
     * Test of removeNotifier method, of class ModelNotifier.
     */
    public void testRemoveNotifier() {
        Notifier n = new Notifier();
        n.setName("testdelete");
        n.setUrl("http://192.168.208.9:8080/sam5/notifications");        
        
        System.out.println(ModelNotifier.addNotifier(n));
        ModelNotifier.removeNotifier(n);
    }

    /**
     * Test of updateNotifier method, of class ModelNotifier.
     */
    public void testUpdateNotifier() {
        Notifier n = new Notifier();
        n.setName("testdelete");
        n.setUrl("http://192.168.208.9:8080/sam5/notifications");        
        
        System.out.println(ModelNotifier.addNotifier(n));
        
        n.setUrl("http://192.168.208.9:8080/sam5/notifications");        
        
        ModelNotifier.updateNotifier(n);
        
        ModelNotifier.removeNotifier(n);
    }

    /**
     * Test of getNotifiers method, of class ModelNotifier.
     */
    public void testGetNotifiers() {
        System.out.println("getNotifiers");
        
        Notifier n = new Notifier();
        n.setName("testdelete");
        n.setUrl("http://192.168.208.9:8080/sam5/notifications");        
        
        System.out.println(ModelNotifier.getNotifiers());
        ModelNotifier.removeNotifier(n);
        
    }
    
    /**
     * Test of getNotifiers method, of class ModelNotifier.
     */
    public void testGetNotifier() {
        System.out.println("getNotifier");
        
        Notifier n = new Notifier();
        n.setName("testdelete");
        n.setUrl("http://192.168.208.9:8080/sam5/notifications");        
        
        DAO.create(n);
        
        System.out.println(ModelNotifier.getNotifier("testdelete"));
        
        DAO.delete(n);
        
    }
    
}
