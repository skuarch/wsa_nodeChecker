package model.common;

import org.apache.log4j.Logger;
import org.json.JSONObject;

/**
 *
 * @author skuarch
 */
public class SimplePing extends Thread {

    private static final Logger logger = Logger.getLogger(SimplePing.class);
    private String host = null;

    //==========================================================================
    public SimplePing(String host) {
        this.host = host;
    } // end SimplePing

    //==========================================================================
    @Override
    public void run() {

        boolean isAlive = false;

        try {

            Counter.increaseCounter();
            isAlive = new ExecutePing().run(host);

            System.out.println("isAlive: " + isAlive + " " +host);

            if (!isAlive) {

                //deepPing
                JSONObject jsonNotification = new JSONObject();
                jsonNotification.accumulate("host", host);
                jsonNotification.accumulate("isAlive", false);
                jsonNotification.accumulate("timestamp", System.currentTimeMillis());
                jsonNotification.accumulate("description", "the host didn't response to icmp ping");
                new Notificator().sendMultipleNotification(jsonNotification);

            }

        } catch (Exception e) {
            logger.error("run", e);
        } finally {
            Counter.decreaseCounter();
        }

    } // end run

} // end class
