package model.common;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
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
    private TimerTask timerTask = null;
    private Timer timer = null;
    private int maxThreads = 500;
    private int sleep = 2500;
    private int delay = 1500;

    //==========================================================================
    public SchedulerProcessor(Scheduler scheduler, ArrayList<NetworkNode> nodes, int maxThreads, int sleep) {

        this.scheduler = scheduler;
        this.nodes = nodes;
        this.maxThreads = maxThreads;
        this.sleep = sleep;

    } // end SchedulerProcessor2

    //==========================================================================
    public void run() {

        timerTask = new TimerTask() {

            @Override
            public void run() {

                try {

                    System.out.println("threads running " + Counter.getCounter() + " scheduler " + scheduler.getName());

                    checkNodes();

                    copyNodes();

                    launchThreads();

                } catch (Exception e) {
                    logger.error("run", e);
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
    private void checkNodes() throws Exception {
        if (nodes == null || nodes.size() < 1) {
            JSONObject jsono = new JSONObject();
            jsono.accumulate("request", "noNodesToCheck");
            jsono.accumulate("expectedReturn", "true");
            jsono.accumulate("host", "");
            jsono.accumulate("description", "the scheduler " + scheduler.getName() + " doesn't have a nodes");
            jsono.accumulate("scheduler", scheduler.getName());
            new Notificator().sendMultipleNotification(jsono);
        }
    } // end checkNodes

    //==========================================================================
    private void copyNodes() {
        nodesCopy = new ArrayList<>(nodes);
    } // end copyNodes

    //==========================================================================
    private void launchThreads() throws InterruptedException {

        for (NetworkNode networkNode : nodesCopy) {
            
            if (Counter.getCounter() >= maxThreads) {
                //take a break to release some threads     
                System.out.println("max  " + scheduler.getName());
                Thread.sleep(sleep);
            }

            Counter.increaseCounter();
            new Thread(new PingProcedure(networkNode, this)).start();

        } // end for

    } // end launchThreads

    //==========================================================================
    public void addNetworkNode(NetworkNode networkNode) {

        if (networkNode == null) {
            throw new NullPointerException("networkNode is null");
        }

        synchronized (nodes) {
            nodes.add(networkNode);
        }

    }

    //==========================================================================
    public void removeNetworkNode(NetworkNode networkNode) {

        if (networkNode == null) {
            throw new NullPointerException("networkNode is null");
        }

        nodes.remove(networkNode);
    }

    //==========================================================================
    public String getSchedulerName() {
        return scheduler.getName();
    }

    //==========================================================================
    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        synchronized (scheduler) {
            this.scheduler = scheduler;
        }
    }

    public ArrayList<NetworkNode> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<NetworkNode> nodes) {
        this.nodes = nodes;
    }

    public int getMaxThreads() {
        return maxThreads;
    }

    public void setMaxThreads(int maxThreads) {
        this.maxThreads = maxThreads;
    }

    public int getSleep() {
        return sleep;
    }

    public void setSleep(int sleep) {
        this.sleep = sleep;
    }

} // end class
