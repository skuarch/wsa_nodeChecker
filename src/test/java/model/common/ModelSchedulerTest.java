package model.common;

import java.util.ArrayList;
import junit.framework.TestCase;
import model.beans.Scheduler;

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
        System.out.println("getScheduler");
        
        Scheduler s = ModelScheduler.getScheduler("uno");
        System.out.println(s);
        
    }

    /**
     * Test of getScheduler method, of class ModelScheduler.
     */
    public void testGetScheduler_String() {
        System.out.println("getScheduler");
        
        Scheduler s = ModelScheduler.getScheduler(1);
        System.out.println(s);
        
    }

    /**
     * Test of createScheduler method, of class ModelScheduler.
     */
    public void testCreateScheduler() {
        System.out.println("createScheduler");
        
        Scheduler s = new Scheduler();
        s.setName("uno");       
        
        ModelScheduler.createScheduler(s);
        
        ModelScheduler.deleteScheduler(s);
    }

    /**
     * Test of deleteScheduler method, of class ModelScheduler.
     */
    public void testDeleteScheduler() {
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
        System.out.println("getSchedulers");
        
        Scheduler s = new Scheduler();
        s.setName("uno");       
        ModelScheduler.createScheduler(s);
        
        s.setName("dos");       
        ModelScheduler.createScheduler(s);
        
        s.setName("tres");       
        ModelScheduler.createScheduler(s);
        
        System.out.println(ModelScheduler.getSchedulers());
        
        
        
    }
    
}
