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
public final class SchedulerProcessor {

    private static final Logger logger = Logger.getLogger(SchedulerProcessor.class);
    private Scheduler scheduler = null;
    private ArrayList<NetworkNode> nodes = null;
    private TimerTask timerTask = null;
    private Timer timer = null;
    private boolean stop = false;
    private int maxThreads = 500;
    private int sleep = 2500;    
    private SchedulerProcessor me;

    //==========================================================================
    public SchedulerProcessor(Scheduler scheduler, ArrayList<NetworkNode> nodes, int maxThreads, int sleep) {
        this.scheduler = scheduler;
        this.nodes = nodes;
        this.maxThreads = maxThreads;
        this.sleep = sleep;
        setMe();
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

                    checkNotificators();

                    //create a copy of nodes to avoid synchronization
                    nodes2 = new ArrayList<>(nodes);

                    for (NetworkNode networkNode : nodes2) {

                        if (stop) {
                            return;
                        }

                        if (Counter.getCounter() >= maxThreads) {
                            //take a break to release some threads                            
                            Thread.sleep(sleep);
                        }

                        new Thread(new PingProcedure(networkNode,me)).start();

                    } // end for

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

    //==========================================================================
    private void checkNotificators() throws Exception {

        if (nodes == null || nodes.size() < 1) {
            JSONObject jsono = new JSONObject();
            jsono.accumulate("request", "noNodesToCheck");
            jsono.accumulate("expectedReturn", "true");
            jsono.accumulate("host", "");
            jsono.accumulate("description", "the scheduler " + scheduler.getName() + " doesn't have a nodes");
            jsono.accumulate("scheduler", scheduler.getName());
            new Notificator().sendMultipleNotification(jsono);
        }

    } // end checkNotificators   

    private void setMe(){
        me = this;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public ArrayList<NetworkNode> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<NetworkNode> nodes) {
        this.nodes = nodes;
    }

} // end class
