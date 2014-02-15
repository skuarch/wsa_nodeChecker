package model.common;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import model.beans.NetworkNode;
import model.beans.Scheduler;
import org.apache.log4j.Logger;
import org.json.JSONObject;

/**
 *
 * @author skuarch
 */
public class SchedulerProcessor {

    private static final Logger logger = Logger.getLogger(SchedulerProcessor.class);
    private Scheduler scheduler = null;
    private ArrayList<NetworkNode> nodes = null;
    private ArrayList<NetworkNode> nodesCopy = null;
    private ExecutorService executorService = null;
    private TimerTask timerTask = null;
    private Timer timer = null;
    private short maxThreads = 500;
    private short sleep = 2500;
    private short delay = 1500;

    //==========================================================================
    public SchedulerProcessor(final Scheduler scheduler, final ArrayList<NetworkNode> nodes, final short maxThreads, final short sleep) {

        this.scheduler = scheduler;
        this.nodes = nodes;
        this.maxThreads = maxThreads;
        this.sleep = sleep;
        executorService = Executors.newFixedThreadPool(maxThreads);
        //executorService = Executors.newCachedThreadPool();

    } // end SchedulerProcessor2

    //==========================================================================
    public void run() {        

        timerTask = new TimerTask() {

            @Override
            public void run() {

                try {

                    System.out.println("threads running " + Counter.getCounter() + " scheduler " + scheduler.getName());

                    copyNodes();

                    launchThreads();

                } catch (InterruptedException e) {
                    logger.error("run", e);
                } finally {
                    System.out.println("termine");
                }

            }

        };

        timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, delay, scheduler.getPeriod() * 1000);

    } // end run

    //==========================================================================
    /*public void stopScheduler() {
     timer.cancel();
     timerTask.cancel();
     timer = null;
     timerTask = null;
     } // end stopScheduler  */
    //==========================================================================
    private void sendNotification() throws Exception {

        JSONObject jsono = new JSONObject();
        jsono.accumulate("request", "noNodesToCheck");
        jsono.accumulate("expectedReturn", "true");
        jsono.accumulate("host", "");
        jsono.accumulate("description", "the scheduler " + scheduler.getName() + " doesn't have a nodes");
        jsono.accumulate("scheduler", scheduler.getName());
        new Notificator().sendMultipleNotification(jsono);

    } // end checkNodes

    //==========================================================================
    private boolean validateNodes() {

        if (this.nodes == null || this.nodes.size() < 1) {
            return false;
        }
        return true;

    }

    //==========================================================================
    private void copyNodes() {

        if (this.nodes == null || this.nodes.size() < 1) {
            nodesCopy = new ArrayList<>();
        } else {
            nodesCopy = new ArrayList<>(this.nodes);
        }

    } // end copyNodes

    //==========================================================================
    private void launchThreads() throws InterruptedException {

        try {

            for (int i = 0; i < nodesCopy.size(); i++) {

                if (Counter.getCounter() >= maxThreads) {
                    //take a break to release some threads                     
                    Thread.sleep(sleep);

                    if (i <= 0) {
                        i = 0;
                    } else {
                        i--;
                    }

                } else {
                    executorService.execute(new PingProcedure(nodesCopy.get(i), this));
                }

            } // end for

            //executorService.
        } catch (InterruptedException e) {
            throw e;
        }

    } // end launchThreads

    //==========================================================================
    public synchronized void addNetworkNode(NetworkNode networkNode) {

        if (networkNode == null) {
            throw new NullPointerException("networkNode is null");
        }

        this.nodes.add(networkNode);

    }

    //==========================================================================
    public synchronized void removeNetworkNode(NetworkNode networkNode) {

        if (networkNode == null) {
            throw new NullPointerException("networkNode is null");
        }

        this.nodes.remove(networkNode);
    }

    //==========================================================================
    public String getSchedulerName() {
        return scheduler.getName();
    }

    //==========================================================================
    public Scheduler getScheduler() {
        return scheduler;
    }

} // end class
