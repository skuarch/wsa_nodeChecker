package model.process;

import java.io.IOException;
import java.util.ArrayList;
import model.beans.NetworkNode;
import model.beans.Scheduler;
import model.common.CustomProperties;
import model.common.ModelScheduler;
import model.common.NetworkNodeContainer;
import model.common.SchedulerContainer;
import model.common.SchedulerProcessor;
import model.net.ModelSocket;
import org.apache.commons.lang.NumberUtils;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author skuarch
 */
public class ProcessSchedulerCreate extends Process {

    private String name = null;
    private int period;
    private boolean status = false;
    private int sleep = 2500;
    private int maxThreads = 500;
    private Scheduler scheduler = null;
    private ArrayList<NetworkNode> nodes = null;

    //==========================================================================
    public ProcessSchedulerCreate(ModelSocket ms, JSONObject jsono) {
        super(ms, jsono, ProcessSchedulerCreate.class);
    }

    //==========================================================================
    public void run() throws IOException {

        try {

            if (!validateJson()) {
                return;
            }

            initVariables();

            if (!validateScheduler()) {
                return;
            }

            saveScheduler();
            createContextNetworkNode();
            runProcess();
            sendResponse();

        } catch (JSONException | IOException | NullPointerException e) {
            logger.error("run", e);
            sendError(e.getMessage());
        } finally {
            ms.closeStreams();
        }

    } // end createScheduler

    //==========================================================================
    private boolean validateJson() throws IOException {

        if (!jsono.has("schedulerName") || !jsono.has("schedulerPeriod") || !jsono.has("schedulerStatus")) {
            ms.send("{\"error\":\"json is incorrect\"}");
            logger.error("createScheduler", new Exception("json is incorrect"));
            return false;
        }

        return true;
    } // end validateJson

    //==========================================================================
    private void initVariables() {

        name = jsono.getString("schedulerName");
        period = jsono.getInt("schedulerPeriod");
        status = jsono.getBoolean("schedulerStatus");

    } // end initVariables

    //==========================================================================
    private boolean validateScheduler() throws IOException {

        if (NumberUtils.isNumber(name)) {
            ms.send("{\"error\":\"scheduler name is incorrect\"}");
            logger.error("createScheduler", new IllegalArgumentException("scheduler name is incorrect"));
            return false;
        }

        if (ModelScheduler.existsScheduler(name)) {
            ms.send("{\"error\":\"scheduler already exists\"}");
            logger.error("createScheduler", new IllegalArgumentException("scheduler already exists"));
            return false;
        }

        return true;

    } // end validateScheduler

    //==========================================================================
    private void saveScheduler() {
        scheduler = new Scheduler(name, period, status);
        ModelScheduler.createScheduler(scheduler);
    } // end saveScheduler

    //==========================================================================
    private void createContextNetworkNode() {
        nodes = new ArrayList<>();
        NetworkNodeContainer.addArrayList(name, nodes);
    } // end createContextNetworkNode

    //==========================================================================
    private void runProcess() throws IOException {
        maxThreads = new CustomProperties().getIntPropertie("thread.max.ping");
        sleep = new CustomProperties().getIntPropertie("sleep.time.1");
        SchedulerProcessor schedulerProcessor = new SchedulerProcessor(scheduler, nodes, maxThreads, sleep);
        SchedulerContainer.addSchedulerProcessor(schedulerProcessor);
        schedulerProcessor.runScheduler();
    } // end runProcess

    //==========================================================================
    private void sendResponse() throws IOException {
        ms.send("{\"created\":\"true\"}");
    } // end sendResponse

} // end class
