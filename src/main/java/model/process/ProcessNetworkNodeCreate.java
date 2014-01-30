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
    private ArrayList<NetworkNode> nodes = null;
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

            if (!validateMaxNodesScheduler()) {
                return;
            }

            saveNetworkNode();

            reloadContextNetworkNode();

            refreshSchedulerProcessor();

            sendResponse();

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
    } // end initVariables 

    //==========================================================================
    private boolean validateMaxNodesScheduler() throws IOException {

        ArrayList<NetworkNode> n = NetworkNodeContainer.getArrayList(schedulerName);

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
    private void reloadContextNetworkNode() {
        nodes = NetworkNodeContainer.getArrayList(schedulerName);
        nodes.add(networkNode);
        NetworkNodeContainer.updateArrayList(schedulerName, nodes);
    } // end reloadContextNetworkNode

    //==========================================================================
    private void refreshSchedulerProcessor() {
        schedulerProcessor = SchedulerContainer.getSchedulerProcessor(schedulerName);
        schedulerProcessor.stopScheduler();
        schedulerProcessor.realodNodes(nodes);
        schedulerProcessor.runScheduler();
    } // end refreshSchedulerProcessor

    //==========================================================================
    private void sendResponse() throws IOException {
        ms.send("{\"created\":\"true\"}");
    } // end sendResponse

} // end class
