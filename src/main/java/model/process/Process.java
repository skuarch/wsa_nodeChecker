package model.process;

import java.io.IOException;
import model.net.ModelSocket;
import model.util.JSONUtilities;
import org.apache.log4j.Logger;
import org.json.JSONObject;

/**
 * the class that extends of this class has a lot of business logic.
 * @author skuarch
 */
abstract class Process {

    Logger logger = null;
    ModelSocket ms = null;
    JSONObject jsono = null;    
    
    //==========================================================================
    Process(ModelSocket ms, JSONObject jsono, Class klass) {

        if (ms == null) {
            throw new IllegalArgumentException("ms is null");
        }

        if (jsono == null || jsono.length() < 1) {
            throw new IllegalArgumentException("jsono is null or empty");
        }

        this.ms = ms;
        this.jsono = jsono;
        
        logger = Logger.getLogger(klass);
        
    } // end Process
    
    
    //==========================================================================
    //public abstract String getStringDispatched();

    //==========================================================================
    final void sendError(String error) {

        try {
            ms.send(JSONUtilities.getJSONError(error));
        } catch (IOException e) {
            logger.error("sendError", e);
            ms.closeStreams();
        }

    } // end sendError

} // end class
