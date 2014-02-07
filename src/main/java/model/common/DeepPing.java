package model.common;

import model.beans.NetworkNode;
import org.apache.log4j.Logger;
import org.json.JSONObject;

/**
 *
 * @author skuarch
 */
public final class DeepPing {

    private static final Logger logger = Logger.getLogger(DeepPing.class);
    
    private NetworkNode networkNode = null;
    private SchedulerProcessor schedulerProcessor = null;

    //==========================================================================
    public DeepPing(NetworkNode networkNode, SchedulerProcessor schedulerProcessor) {
        this.networkNode = networkNode;
        this.schedulerProcessor = schedulerProcessor;
    } // end DeepPing

    //==========================================================================   
    public void run() {

        boolean isAlive = false;
        long endTime = System.currentTimeMillis() + (networkNode.getTriggerAlarm() * 1000);

        try {

            CounterDeepPing.increaseCounter();
            removeNodeFromContext();

            while (endTime >= System.currentTimeMillis()) {

                if (isAlive) {

                    //add node to the context
                    addNodeContext();
                    CounterDeepPing.decreaseCounter();
                    Counter.decreaseCounter();
                    return;

                }

                isAlive = new ExecutePing().run(networkNode.getHost());

            }

            sendNotification();
            new ContinuePing(networkNode, schedulerProcessor).run();

        } catch (Exception e) {
            logger.error("run", e);
        }

    } // end run

    //==========================================================================
    private void sendNotification() throws Exception {

        JSONObject jsonNotification = new JSONObject();
        jsonNotification.accumulate("request", "notification");
        jsonNotification.accumulate("expectedReturn", "true");
        jsonNotification.accumulate("host", networkNode.getHost());
        jsonNotification.accumulate("isAlive", false);
        jsonNotification.accumulate("timestamp", System.currentTimeMillis());
        jsonNotification.accumulate("description", "the host " + networkNode.getHost() + " didn't responsed to icmp ping");

        new Notificator().sendMultipleNotification(jsonNotification);

    } // end sendNotification

    //==========================================================================
    private void removeNodeFromContext() {

        schedulerProcessor.removeNetworkNode(networkNode);

    } // end removeNodeFromContext
    
    //==========================================================================
    private void addNodeContext() {

        schedulerProcessor.addNetworkNode(networkNode);

    } // end addNodeContext

} // end class
