package model.common;

import java.io.IOException;
import java.net.Socket;
import model.net.ModelSocket;
import org.apache.log4j.Logger;

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

        if (socket == null || socket.isClosed()) {
            logger.error("socket is null or close");
            return;
        }

        try {

            ms = new ModelSocket(socket);
            textReceived = ms.receive();

            if (textReceived == null || textReceived.length() < 1) {

                ms.closeStreams();
                throw new NullPointerException("data received from client is null or empty");

            } else {

                System.out.println(textReceived);
                new DistributeRequest(textReceived, ms).attendRequest();

            }

        } catch (IOException | NullPointerException ioe) {
            ms.closeStreams();
            logger.error("run", ioe);
        }

    } // end run    

} // end class
