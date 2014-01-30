package model.common;

import java.util.ArrayList;
import junit.framework.TestCase;
import model.beans.NetworkNode;
import model.beans.Scheduler;

/**
 *
 * @author skuarch
 */
public class SchedulerContainerTest extends TestCase {
    
    public SchedulerContainerTest(String testName) {
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
     * Test of addSchedulerProcessor method, of class SchedulerContainer.
     */
    public void testAddSchedulerProcessor() {
        
        System.out.println("------------------------------------------------------------------------");
        System.out.println("addSchedulerProcessor");
        
        Scheduler s = new Scheduler();
        s.setName("one");
        
        SchedulerContainer.addSchedulerProcessor(new SchedulerProcessor(s, new ArrayList<NetworkNode>(),500,500));
    }

    /**
     * Test of getSchedulerProcessor method, of class SchedulerContainer.
     */
    public void testGetSchedulerProcessor() {
        
        System.out.println("------------------------------------------------------------------------");
        System.out.println("getSchedulerProcessor");
        
        Scheduler s = new Scheduler();
        s.setName("two");
        
        SchedulerContainer.addSchedulerProcessor(new SchedulerProcessor(s, new ArrayList<NetworkNode>(),500,500));
        
        System.out.println(SchedulerContainer.getSchedulerProcessor("two"));
        
    }
    
}
