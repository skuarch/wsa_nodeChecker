package model.process;

import java.io.IOException;
import java.util.ArrayList;
import model.beans.NetworkNode;
import model.beans.Scheduler;
import model.common.CustomProperties;
import model.common.ModelScheduler;
import model.common.SchedulerContainer;
import model.common.SchedulerProcessor;
import model.net.ModelSocket;
import org.apache.commons.lang.NumberUtils;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * business logic to create a Scheduler task.
 * @author skuarch
 */
public class ProcessSchedulerCreate extends Process {

    private String name = null;
    private short period;
    private boolean status = false;
    private short sleep = 2500;
    private short maxThreads = 500;
    private Scheduler scheduler = null;
    private SchedulerProcessor schedulerProcessor = null;
    private ArrayList<NetworkNode> nodes = null;

    //==========================================================================
    /**
     * create a instance.
     * @param ms ModelSocket
     * @param jsono JSONObject
     */
    public ProcessSchedulerCreate(ModelSocket ms, JSONObject jsono) {
        super(ms, jsono, ProcessSchedulerCreate.class);
    } // end ProcessSchedulerCreate

    //==========================================================================
    @Override
    public void run() throws IOException {

        try {

            if (!validateJson()) {
                ms.send("{\"error\":\"json is incorrect\"}");
                return;
            }

            initVariables();

            if (!validateNameScheduler()) {
                sendResponse("{\"error\":\"scheduler name is incorrect\"}");
                return;
            }

            if (!validateIfExistsScheduler()) {
                sendResponse("{\"error\":\"scheduler already exists\"}");
                return;
            }
            
            saveScheduler();
            addSchedulerProccesor();
            runSchedulerProcess();
            sendResponse("{\"created\":\"true\"}");

        } catch (JSONException | IOException | NullPointerException e) {
            logger.error("run", e);
            sendError(e.getMessage());
        } finally {
            ms.closeStreams();
        }

    } // end run

    //==========================================================================
    /**
     * validate if the JSON has a correct parameters.
     * @return boolean
     * @throws IOException 
     */
    private boolean validateJson() throws IOException {

        if (!jsono.has("schedulerName") || !jsono.has("schedulerPeriod") || !jsono.has("schedulerStatus")) {
            logger.error("createScheduler", new Exception("json is incorrect"));
            return false;
        }

        return true;
    } // end validateJson

    //==========================================================================
    /**
     * set up the variables.
     * @throws IOException 
     */
    private void initVariables() throws IOException {

        name = jsono.getString("schedulerName");
        period = (short) jsono.getInt("schedulerPeriod");
        status = jsono.getBoolean("schedulerStatus");
        maxThreads = new CustomProperties().getShortPropertie("thread.max.ping");
        sleep = new CustomProperties().getShortPropertie("sleep.time.1");
        nodes = new ArrayList<>();
        scheduler = new Scheduler(name, period, status);
        schedulerProcessor = new SchedulerProcessor(scheduler, nodes, maxThreads, sleep);

    } // end initVariables

    //==========================================================================
    /**
     * the name of the scheduler can't be a number.
     * @return boolean
     * @throws IOException 
     */
    private boolean validateNameScheduler() throws IOException {

        if (NumberUtils.isNumber(name)) {
            logger.error("validateNameScheduler", new IllegalArgumentException("scheduler name is incorrect"));
            return false;
        }

        return true;

    } // end validateScheduler

    //==========================================================================
    /**
     * validate if the scheduler already exists.
     * @return boolean
     */
    private boolean validateIfExistsScheduler() {

        if (ModelScheduler.existsScheduler(name)) {
            logger.error("validateIfExistsScheduler", new IllegalArgumentException("scheduler already exists"));
            return false;
        }

        return true;
    } // end validateIfExistsScheduler

    //==========================================================================
    /**
     * save scheduler in the database.
     */
    private void saveScheduler() {
        ModelScheduler.createScheduler(scheduler);
    } // end saveScheduler

    //==========================================================================
    /**
     * add SchedulerProcessor to the container.
     */
    private void addSchedulerProccesor() {
        SchedulerContainer.addSchedulerProcessor(schedulerProcessor);
    } // end addSchedulerProccesor

    //==========================================================================
    /**
     * start the scheduler task.
     * @throws IOException 
     */
    private void runSchedulerProcess() throws IOException {
        schedulerProcessor.run();
    } // end runProcess

} // end class
