package model.common;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import org.apache.log4j.Logger;
import org.json.JSONObject;

/**
 *
 * @author skuarch
 */
public class SchedulerDeepPing {

    private static final Logger logger = Logger.getLogger(SchedulerDeepPing.class);
    private TimerTask timerTask = null;
    private Timer timer = null;
    private short maxThreads = 500;
    private double percentage = 0;
    private short trigger = 60;
    private boolean isContinue = false;
    private short counter = 0;

    //==========================================================================
    public SchedulerDeepPing() throws IOException {
        maxThreads = new CustomProperties().getShortPropertie("thread.max.ping");
    } // end SchedulerDeepPing

    //==========================================================================
    public void run() {
        timerTask = new TimerTask() {

            @Override
            public void run() {

                try {

                    System.out.println("pings " + Counter.getCounter() + " deepPing " + CounterDeepPing.getCounter());
                    checkDeepPing();

                } catch (Exception ex) {
                    logger.error("run", ex);
                }

            }
        };

        timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    } // end run    

    //==========================================================================
    private void checkDeepPing() throws IOException, Exception {

        percentage = maxThreads - (maxThreads * 0.1);

        if (CounterDeepPing.getCounter() >= percentage) {
            counter++;
        } else {
            counter--;
        }

        if (counter < 0) {
            counter = 0;
        }

        if (counter >= trigger) {
            sendNotification();
        }

    } // end checkDeepPing

    //==========================================================================
    private void sendNotification() throws Exception {
        JSONObject jsono = new JSONObject();
        jsono.accumulate("request", "maxThreads");
        jsono.accumulate("expectedReturn", "true");
        jsono.accumulate("host", "");
        jsono.accumulate("description", "");
        new Notificator().sendMultipleNotification(jsono);
    } // end sendNotification

} // end class
