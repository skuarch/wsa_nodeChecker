package model.process;

import java.io.IOException;
import java.util.ArrayList;
import model.beans.NetworkNode;
import model.beans.Scheduler;
import model.common.ModelScheduler;
import model.common.NetworkNodeContainer;
import model.common.SchedulerContainer;
import model.common.SchedulerProcessor;
import model.net.ModelSocket;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author skuarch
 */
public class ProcessScheduler extends Process {

    //==========================================================================
    public ProcessScheduler(ModelSocket ms, JSONObject jsono) {
        super(ms, jsono);
    }

    //==========================================================================
    public void createScheduler() throws IOException {

        //validate json 
        if (!jsono.has("schedulerName") || !jsono.has("schedulerPeriod") || !jsono.has("schedulerStatus")) {
            ms.send("{\"error\":\"json is incorrect\"}");
            logger.error("createScheduler", new Exception("json is incorrect"));
            return;
        }

        String name = null;
        int period;
        boolean status = false;

        try {

            name = jsono.getString("schedulerName");
            period = jsono.getInt("schedulerPeriod");
            status = jsono.getBoolean("schedulerStatus");

            if (ModelScheduler.existsScheduler(name)) {
                ms.send("{\"error\":\"scheduler already exists\"}");
                logger.error("createScheduler", new IllegalArgumentException("scheduler already exists"));
                return;
            }

            //save scheduler bean in the db 
            Scheduler scheduler = new Scheduler(name,period,status);
            ModelScheduler.createScheduler(scheduler);

            //create the context of network nodes
            ArrayList<NetworkNode> nodes = new ArrayList<>();
            NetworkNodeContainer.addArrayList(name, nodes);

            //run the process            
            SchedulerProcessor schedulerProcessor = new SchedulerProcessor(scheduler, nodes);
            SchedulerContainer.addSchedulerProcessor(schedulerProcessor);
            schedulerProcessor.runScheduler();

            ms.send("{\"created\":\"true\"}");

        } catch (JSONException | IOException | NullPointerException e) {
            logger.error("createScheduler", e);
            sendError(e.getMessage());
        } finally {
            ms.closeStreams();
        }
        
    } // end createScheduler

} // end class
