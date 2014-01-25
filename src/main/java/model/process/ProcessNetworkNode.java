package model.process;

import java.io.IOException;
import java.util.ArrayList;
import model.beans.NetworkNode;
import model.beans.Scheduler;
import model.common.ModelNetworkNode;
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
public class ProcessNetworkNode extends Process {

    //==========================================================================
    public ProcessNetworkNode(ModelSocket ms, JSONObject jsono) {
        super(ms, jsono);
    }

    //==========================================================================
    public void createNetworkNode() {

        Scheduler scheduler = null;
        NetworkNode networkNode = null;
        SchedulerProcessor schedulerProcessor = null;
        String host = null;
        int timeout = 0;
        ArrayList<NetworkNode> nodes = null;
        String schedulerName = null;

        try {

            //validation
            if (!jsono.has("networkNodeHost") || !jsono.has("networkNodeTimeout") || !jsono.has("schedulerName")) {
                ms.send("{\"error\":\"json is incorrect\"}");
                logger.error("createNetworkNode", new IllegalArgumentException("json is incorrect"));
                return;
            }

            host = jsono.getString("networkNodeHost");
            timeout = jsono.getInt("networkNodeTimeout");
            schedulerName = (String) jsono.get("schedulerName");

            //create network node
            scheduler = ModelScheduler.getSchedulerByName(schedulerName);  
            networkNode = new NetworkNode(host,timeout,scheduler); 
            ModelNetworkNode.createNetworkNode(networkNode);

            //reload context of network nodes
            nodes = NetworkNodeContainer.getArrayList(schedulerName);
            nodes.add(networkNode);
            NetworkNodeContainer.updateArrayList(schedulerName, nodes);

            //refresh the nodes in SchedulerProcessor
            schedulerProcessor = SchedulerContainer.getSchedulerProcessor(schedulerName);            
            schedulerProcessor.stopScheduler();
            schedulerProcessor.realodNodes(nodes);
            schedulerProcessor.runScheduler();            
            
            ms.send("{\"created\":\"true\"}");

        } catch (IOException | JSONException | NullPointerException e) {
            logger.error("createNetworkNode", e);
            sendError(e.getMessage());
        } finally {
            ms.closeStreams();
        }

    } // end createNetworkNode

} // end class
