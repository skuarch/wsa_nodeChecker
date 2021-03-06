package model.process;

import model.common.ExecutePing;
import model.net.ModelSocket;
import org.json.JSONObject;

/**
 *
 * @author skuarch
 */
public class ProcessSimplePing extends Process {

    //==========================================================================
    public ProcessSimplePing(ModelSocket ms, JSONObject jsono) {
        super(ms, jsono, ProcessSimplePing.class);
    } // end ProcessSimplePing

    //==========================================================================
    @Override
    public void run() {

        String host = null;
        String isAlive = "false";

        try {

            host = jsono.getString("host");
            isAlive = new ExecutePing().run(host);
            sendResponse("\"isAlive\":\"" + isAlive + "\"");

        } catch (Exception e) {
            sendError(e.getMessage());
            logger.error("runSimplePing", e);
        } finally {
            ms.closeStreams();
        }

    } // end runSimplePing

} // end class
