package model.common;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import model.beans.NetworkNode;
import model.beans.Scheduler;
import org.apache.log4j.Logger;

/**
 *
 * @author skuarch
 */
public final class SchedulerProcessor {

    private static final Logger logger = Logger.getLogger(SchedulerProcessor.class);
    private Scheduler scheduler = null;
    private ArrayList<NetworkNode> nodes = null;
    private TimerTask timerTask = null;
    private Timer timer = null;
    private boolean stop = false;

    //==========================================================================
    public SchedulerProcessor(Scheduler scheduler, ArrayList<NetworkNode> nodes) {
        this.scheduler = scheduler;
        this.nodes = nodes;
    } // end SchedulerProcessor

    //==========================================================================    
    public String getSchedulerName() {
        return scheduler.getName();
    } // end getSchedulerName

    //==========================================================================
    public void runScheduler() {

        timerTask = new TimerTask() {

            @Override
            public void run() {

                System.out.println("run  " + scheduler.getName());
                stop = false;
                ArrayList<NetworkNode> nodes2 = null;

                try {

                    if (nodes == null || nodes.size() < 1) {
                        new Notificator().sendMultipleNotification("nodes are empty");
                    }

                    nodes2 = new ArrayList<>(nodes);

                    for (NetworkNode networkNode : nodes2) {

                        if (stop) {
                            return;
                        }

                        new Thread(new SimplePing(networkNode.getHost())).start();

                    }

                } catch (Exception e) {
                    logger.error("run", e);
                }
            }

        };

        timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 1500, scheduler.getPeriod() * 1000);

    } // end runScheduler

    //==========================================================================
    public void realodNodes(ArrayList<NetworkNode> nodes) {

        if (nodes == null || nodes.size() < 1) {
            throw new IllegalArgumentException("nodes is null or empty");
        }

        this.nodes = nodes;

    } // end realodNodes

    //==========================================================================
    public void stopScheduler() {
        stop = true;
        timer.cancel();
        timerTask.cancel();
        timer = null;
        timerTask = null;
    } // end stopScheduler

} // end class
