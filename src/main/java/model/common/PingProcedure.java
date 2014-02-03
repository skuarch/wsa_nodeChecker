package model.common;

import java.util.ArrayList;
import model.beans.NetworkNode;
import org.apache.log4j.Logger;

/**
 *
 * @author skuarch
 */
public final class PingProcedure extends Thread{

    private static final Logger logger = Logger.getLogger(PingProcedure.class);
    private NetworkNode networkNode = null;
    private SchedulerProcessor schedulerProcessor = null;

    //==========================================================================
    public PingProcedure(NetworkNode networkNode, SchedulerProcessor schedulerProcessor) {
        this.networkNode = networkNode;
        this.schedulerProcessor = schedulerProcessor;
    } // end PingProcedure

    //==========================================================================    
    @Override
    public void run() {

        boolean isAlive = false;

        try {

            
            isAlive = new ExecutePing().run(networkNode.getHost());

            if (!isAlive) {
                
                removeNodeFromSchedulerProcessor();
                new DeepPing(networkNode,schedulerProcessor).run();                

            } 

        } catch (Exception e) {
            logger.error("run", e);
        } finally {
            Counter.decreaseCounter();
        }

    } // end run

    //==========================================================================
    private void removeNodeFromSchedulerProcessor() {

        ArrayList<NetworkNode> nodes = schedulerProcessor.getNodes();
        nodes.remove(networkNode);
        schedulerProcessor.setNodes(nodes);

    } // end removeNodeFromSchedulerProcessor
    
} // end class
