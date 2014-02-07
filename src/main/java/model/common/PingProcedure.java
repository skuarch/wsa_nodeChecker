package model.common;

import model.beans.NetworkNode;
import org.apache.log4j.Logger;

/**
 *
 * @author skuarch
 */
public final class PingProcedure extends Thread {

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
            
            Counter.increaseCounter();
            isAlive = new ExecutePing().run(networkNode.getHost());

            if (!isAlive) {

                new DeepPing(networkNode, schedulerProcessor).run();

            } else {
                Counter.decreaseCounter();
            }

        } catch (Exception e) {
            logger.error("run", e);
        }

    } // end run   

} // end class
