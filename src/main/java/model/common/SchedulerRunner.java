package model.common;

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
    public void runStoredSchedulers() {

        ArrayList<Scheduler> schedulers = ModelScheduler.getSchedulers();

        if (schedulers == null || schedulers.size() < 1) {
            System.out.println("no schedulers to run");
            return;
        }

        for (Scheduler scheduler : schedulers) {

            //create the context of network nodes
            ArrayList<NetworkNode> nodes = ModelNetworkNode.getNetworkNodes(scheduler);
            NetworkNodeContainer.addArrayList(scheduler.getName(), nodes);

            //run the process            
            SchedulerProcessor schedulerProcessor = new SchedulerProcessor(scheduler, nodes);
            SchedulerContainer.addSchedulerProcessor(schedulerProcessor);
            schedulerProcessor.runScheduler();
            
        }

    } // end runStoredShcedulers

} // end class
