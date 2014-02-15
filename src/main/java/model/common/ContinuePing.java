package model.common;

import model.beans.NetworkNode;
import org.apache.log4j.Logger;
import org.json.JSONObject;

/**
 *
 * @author skuarch
 */
public class ContinuePing {

    private static final Logger logger = Logger.getLogger(ContinuePing.class);
    private NetworkNode networkNode = null;
    private SchedulerProcessor schedulerProcessor = null;

    //==========================================================================
    public ContinuePing(NetworkNode networkNode, SchedulerProcessor schedulerProcessor) {
        this.networkNode = networkNode;
        this.schedulerProcessor = schedulerProcessor;
    } // end ContinuePing

    //==========================================================================
    public void run() {

        boolean isAlive = false;

        try {

            while (true) {

                if (isAlive) {

                    addNodeContext();
                    sendNotification();
                    CounterDeepPing.decreaseCounter();
                    Counter.decreaseCounter();
                    return;

                }

                //isAlive = new ExecutePing().run(networkNode.getHost());

            }

        } catch (Exception e) {
            logger.error("run", e);
        }

    } // end run

    //==========================================================================
    private void addNodeContext() {
        schedulerProcessor.addNetworkNode(networkNode);
    }

    //==========================================================================
    private void sendNotification() throws Exception {

        JSONObject jsonNotification = new JSONObject();
        jsonNotification.accumulate("request", "notification");
        jsonNotification.accumulate("expectedReturn", "true");
        jsonNotification.accumulate("host", networkNode.getHost());
        jsonNotification.accumulate("isAlive", false);
        jsonNotification.accumulate("timestamp", System.currentTimeMillis());
        jsonNotification.accumulate("description", "the host " + networkNode.getHost() + " is alive");

        new Notificator().sendMultipleNotification(jsonNotification);

    } // end sendNotification

} // end class

