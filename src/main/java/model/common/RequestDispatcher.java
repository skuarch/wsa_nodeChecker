package model.common;

import java.io.IOException;
import java.net.Socket;
import model.net.ModelSocket;
import model.process.AddNotifier;
import model.process.DeleteNotifier;
import model.process.GetConfiguration;
import model.process.GetNotifier;
import model.process.GetNotifiers;
import model.process.ProcessConnectivity;
import model.process.ProcessNetworkNodeCreate;
import model.process.ProcessSchedulerCreate;
import model.process.SaveConfiguration;
import model.util.JSONUtilities;
import org.apache.log4j.Logger;
import org.json.JSONObject;

/**
 *
 * @author skuarch
 */
public final class RequestDispatcher extends Thread {

    private static final Logger logger = Logger.getLogger(RequestDispatcher.class);
    private Socket socket = null;
    private ModelSocket ms = null;
    private String textReceived = null;

    //==========================================================================
    public RequestDispatcher(Socket socket) {
        this.socket = socket;
    } // end RequestDispatcher    

    //==========================================================================
    @Override
    public void run() {

        setName("RequestDispatcher");

        if (socket == null || socket.isClosed()) {
            logger.error("socket is null or close");
            return;
        }

        try {

            ms = new ModelSocket(socket);
            textReceived = ms.receive();

            System.out.println(textReceived);
            attendRequest(textReceived, ms);

        } catch (IOException | NullPointerException ioe) {
            ms.closeStreams();
            logger.error("run", ioe);
        }

    } // end run    

    //===========================================================================
    /**
     * distribute the request.
     *
     * @param jsonString
     * @param ms
     * @throws java.io.IOException
     */
    public void attendRequest(String jsonString, ModelSocket ms) throws IOException {

        if (jsonString == null || jsonString.length() < 1) {
            throw new IllegalArgumentException("jsonString is null or empty");
        }

        JSONObject jsono = new JSONObject(jsonString);

        if (!jsono.has("request")) {
            throw new RuntimeException("request is null or empty");
        }

        String request = jsono.getString("request");

        if (request == null || request.length() < 1) {
            throw new RuntimeException("request is null or empty");
        }

        switch (request) {

            //basic configuration ------------------------------------------
            case "connectivity":
                new ProcessConnectivity(ms, jsono).run();
                break;
            case "getConfiguration":
                new GetConfiguration(ms, jsono).run();
                break;
            case "saveConfiguration":
                new SaveConfiguration(ms, jsono).run();
                break;
            case "addNotifier":
                new AddNotifier(ms, jsono).run();
                break;
            case "getNotifiers":
                new GetNotifiers(ms, jsono).run();
                break;
            case "getNotifier":
                new GetNotifier(ms, jsono).run();
                break;
            case "deleteNotifier":
                new DeleteNotifier(ms, jsono).run();
                break;

            //--------------------------------------------------------------
            case "createScheduler":
                new ProcessSchedulerCreate(ms, jsono).run();
                break;

            case "getSchedulers":
                break;

            case "createNetworkNode":
                new ProcessNetworkNodeCreate(ms, jsono).run();
                break;

            default:
                logger.error("attendRequest", new Exception("unknown request"));
                responseUnknownResquest();
                break;

        }

    } // end attendRequest

    //==========================================================================
    private void responseUnknownResquest() throws IOException {
        ms.send(JSONUtilities.getJSONError("unknown request"));
    } // end responseUnknownResquest

} // end class
