package model.common;

import com.oracle.jrockit.jfr.ContentType;
import model.process.ProcessConnectivity;
import model.process.ProcessConfiguration;
import java.io.IOException;
import javassist.tools.reflect.Reflection;
import model.net.ModelSocket;
import model.process.ProcessNotifier;
import model.util.JSONUtilities;
import org.apache.log4j.Logger;
import org.json.JSONObject;

/**
 * distribute the request.
 *
 * @author skuarch
 */
public final class DistributeRequest {

    private static final Logger logger = Logger.getLogger(DistributeRequest.class);
    private String request = null;
    private String jsonString = null;
    private ModelSocket ms = null;
    private JSONObject jsono = null;

    //==========================================================================
    /**
     * create a instance.
     *
     * @param jsonString String in JSON format
     * @param ms ModelSocket to response the requests
     */
    public DistributeRequest(String jsonString, ModelSocket ms) {
        this.jsonString = jsonString;
        this.ms = ms;
    } // end DistributeRequest

    //===========================================================================
    /**
     * distribute the request.
     */
    public void attendRequest() {

        if (jsonString == null || jsonString.length() < 1) {
            throw new IllegalArgumentException("jsonString is null or empty");
        }

        jsono = new JSONObject(jsonString);

        if (!jsono.has("request")) {
            throw new RuntimeException("request is null or empty");
        }

        request = jsono.getString("request");

        if (request == null || request.length() < 1) {
            throw new RuntimeException("request is null or empty");
        }

        try {

            switch (request) {

                //basic configuration ------------------------------------------
                case "connectivity":
                    new ProcessConnectivity(ms, jsono).connectivity();
                    break;
                case "getConfiguration":
                    new ProcessConfiguration(ms, jsono).getConfiguration();
                    break;
                case "saveConfiguration":
                    new ProcessConfiguration(ms, jsono).saveConfiguration();                    
                    break;
                case "addNotifier":
                    new ProcessNotifier(ms, jsono).addNotifier();
                    break;
                case "getNotifiers":
                    new ProcessNotifier(ms, jsono).getNotifiers();
                    break;
                case "getNotifier":
                    new ProcessNotifier(ms, jsono).getNotifier();
                    break;
                case "deleteServerNotifier":
                    new ProcessNotifier(ms, jsono).deleteNotifier();
                    break;
                    
                //--------------------------------------------------------------
                case "createTask":
                    // to do here
                    break;    
                    
                default:
                    responseUnknownResquest();
                    break;

            }
            
        } catch (Exception e) {
            logger.error("attendRequest", e);
        } finally {
            ms.closeStreams();
        }

    } // end attendRequest

    //==========================================================================
    private void responseUnknownResquest() throws IOException {
        ms.send(JSONUtilities.getJSONError("unknown request"));
    } // end responseUnknownResquest

} // end class
