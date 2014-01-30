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
        int sleep = 2500;
        int maxThreads = 500;

        if (schedulers == null || schedulers.size() < 1) {
            System.out.println("no schedulers to run");
            return;
        }

        for (Scheduler scheduler : schedulers) {

            //create the context of network nodes
            ArrayList<NetworkNode> nodes = ModelNetworkNode.getNetworkNodes(scheduler);
            NetworkNodeContainer.addArrayList(scheduler.getName(), nodes);

            //run the process            
            maxThreads = new CustomProperties().getIntPropertie("thread.max.ping");
            sleep = new CustomProperties().getIntPropertie("sleep.time.1");
            SchedulerProcessor schedulerProcessor = new SchedulerProcessor(scheduler, nodes,maxThreads, sleep);
            SchedulerContainer.addSchedulerProcessor(schedulerProcessor);
            schedulerProcessor.runScheduler();
            
        }

    } // end runStoredShcedulers

} // end class
