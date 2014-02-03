package model.process;

import java.io.IOException;
import java.util.ArrayList;
import model.beans.NetworkNode;
import model.beans.Scheduler;
import model.common.ModelNetworkNode;
import model.common.ModelScheduler;
import model.common.SchedulerContainer;
import model.common.SchedulerProcessor;
import model.net.ModelSocket;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * create network node.
 *
 * @author skuarch
 */
public class ProcessNetworkNodeCreate extends Process {

    private String host = null;
    private int timeout = 0;
    private String schedulerName = null;
    private int triggerAlarm = 0;
    private Scheduler scheduler = null;
    private NetworkNode networkNode = null;    
    private SchedulerProcessor schedulerProcessor = null;

    //==========================================================================
    public ProcessNetworkNodeCreate(ModelSocket ms, JSONObject jsono) {
        super(ms, jsono, ProcessNetworkNodeCreate.class);
    } // end ProcessNetworkNodeCreate

    //==========================================================================
    public void run() {

        try {

            if (!validateJson()) {
                return;
            }

            initVariables();
            
            if(!validateIfExistsScheduler()){
                sendResponse("{\"created\":\"false\",\"error\":\"invalid scheduler\"}");
                return;
            }

            if (!validateMaxNodesScheduler()) {
                sendResponse("{\"created\":\"false\",\"error\":\"max nodes\"}");
                return;
            }

            saveNetworkNode();

            addNodeSchedulerProcessor();            

            sendResponse("{\"created\":\"true\"}");

        } catch (IOException | JSONException | NullPointerException e) {
            logger.error("run", e);
            sendError(e.getMessage());
        } finally {
            ms.closeStreams();
        }

    } // end run

    //==========================================================================
    private boolean validateJson() throws IOException {

        if (!jsono.has("networkNodeHost") || !jsono.has("networkNodeTimeout") || !jsono.has("schedulerName") || !jsono.has("triggerAlarm")) {
            ms.send("{\"error\":\"json is incorrect\"}");
            logger.error("createNetworkNode", new IllegalArgumentException("json is incorrect"));
            return false;
        }

        return true;

    } // end validateJson

    //==========================================================================
    private void initVariables() {
        host = jsono.getString("networkNodeHost");
        timeout = jsono.getInt("networkNodeTimeout");
        triggerAlarm = jsono.getInt("triggerAlarm");
        schedulerName = (String) jsono.get("schedulerName");
        scheduler = ModelScheduler.getSchedulerByName(schedulerName);
        networkNode = new NetworkNode(host, timeout, scheduler, triggerAlarm);
        schedulerProcessor = SchedulerContainer.getSchedulerProcessor(schedulerName);
    } // end initVariables 
    
    //==========================================================================
    public boolean validateIfExistsScheduler(){        
        return scheduler != null;
    } // end validateIfExistsScheduler

    //==========================================================================
    private boolean validateMaxNodesScheduler() throws IOException {

        ArrayList<NetworkNode> n = schedulerProcessor.getNodes();

        if (n.size() >= 254) {
            ms.send("{\"error\":\"only 254 nodes are allowed per scheduler\"}");
            return false;
        }

        return true;
        
    } // end validateMaxNodesScheduler

    //==========================================================================
    private void saveNetworkNode() {
        ModelNetworkNode.createNetworkNode(networkNode);
    } // end saveNetworkNode

    //==========================================================================
    private void addNodeSchedulerProcessor() {
        schedulerProcessor.addNetworkNode(networkNode);
    } // end reloadContextNetworkNode
    

} // end class
