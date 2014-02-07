package model.common;

import java.io.IOException;
import java.util.ArrayList;
import model.beans.NetworkNode;
import model.beans.Scheduler;

/**
 *
 * @author skuarch
 */
public final class SchedulerRunner {

    //==========================================================================
    public SchedulerRunner() {

    } // end SchedulerRunner

    //==========================================================================
    public void runStoredSchedulers() throws IOException {

        ArrayList<Scheduler> schedulers = ModelScheduler.getSchedulers();
        short sleep = 2500;
        short maxThreads = 500;

        try {

            for (Scheduler scheduler : schedulers) {

                //create the context of network nodes
                ArrayList<NetworkNode> nodes = getNodes(scheduler);

                //run the process            
                maxThreads = new CustomProperties().getShortPropertie("thread.max.ping");
                sleep = new CustomProperties().getShortPropertie("sleep.time.1");
                SchedulerProcessor schedulerProcessor = new SchedulerProcessor(scheduler, nodes, maxThreads, sleep);                
                SchedulerContainer.addSchedulerProcessor(schedulerProcessor);
                schedulerProcessor.run();

            }

        } catch (IOException e) {
            throw e;
        }

    } // end runStoredShcedulers

    //==========================================================================
    public void runSchedulerDeepPing() {

        new SchedulerDeepPing().run();

    } // end runSchedulerDeepPing

    //==========================================================================
    private ArrayList<NetworkNode> getNodes(Scheduler scheduler) {

        ArrayList<NetworkNode> nodes = new ArrayList<>();

        try {

            nodes = ModelNetworkNode.getNetworkNodes(scheduler);

        } catch (NullPointerException npe) {
            System.out.println("trying to run scheduler without nodes");
        }

        return nodes;

    } // end getNodes

} // end class

