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
        super(ms, jsono);
    } // end ProcessSimplePing

    //==========================================================================
    public void runSimplePing() {

        String host = null;
        boolean isAlive = false;

        try {
            
            host = jsono.getString("host");
            isAlive = new ExecutePing().run(host);

            ms.send("\"isAlive\":\"" + isAlive + "\"");

        } catch (Exception e) {
            sendError(e.getMessage());
            logger.error("runSimplePing", e);
        }finally{
            ms.closeStreams();
        }

    } // end runSimplePing

} // end class
