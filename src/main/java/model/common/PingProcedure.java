package model.common;

import java.util.ArrayList;
import model.beans.NetworkNode;
import org.apache.log4j.Logger;
import org.json.JSONObject;

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
                
                removeNodeFromContext();
                new DeepPing(networkNode,schedulerProcessor).run();                

            } 

        } catch (Exception e) {
            logger.error("run", e);
        } finally {
            Counter.decreaseCounter();
        }

    } // end run

    //==========================================================================
    private void removeNodeFromContext() {

        ArrayList<NetworkNode> nodes = schedulerProcessor.getNodes();
        nodes.remove(networkNode);
        schedulerProcessor.setNodes(nodes);

    }

    //==========================================================================
    private JSONObject getJsonNotification() {

        JSONObject jsonNotification = new JSONObject();
        jsonNotification.accumulate("request", "notification");
        jsonNotification.accumulate("expectedReturn", "true");
        jsonNotification.accumulate("host", networkNode.getHost());
        jsonNotification.accumulate("isAlive", false);
        jsonNotification.accumulate("timestamp", System.currentTimeMillis());
        jsonNotification.accumulate("description", "the host " + networkNode.getHost() + " didn't responsed to icmp ping");

        return jsonNotification;

    } // end getJsonNotification

} // end class
