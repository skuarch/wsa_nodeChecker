package model.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import model.common.CustomProperties;
import org.apache.log4j.Logger;
import model.util.IOUtilities;
import model.common.RequestDispatcher;

/**
 * start a server and delegate the socket to RequestDispatcher
 *
 * @author skuarch
 */
public final class SocketCreator {

    private static final Logger logger = Logger.getLogger(SocketCreator.class);

    //==========================================================================
    /**
     * create a instance
     */
    public SocketCreator() {
    } // end SocketCreator

    //==========================================================================
    /**
     * start to listen a socket.
     *
     * @param port integer number of port.
     */
    public void startServer(int port) {

        CustomProperties customProperties = new CustomProperties();
        ServerSocket serverSocket = null;
        Socket socket = null; //client
        int timeout = 0;

        try {

            timeout = customProperties.getIntPropertie("socket.timeout");
            serverSocket = new ServerSocket(port);
            logger.info("listening on port " + port);

            //here create a socket to each request
            while (true) {

                socket = serverSocket.accept();
                socket.setSoTimeout(timeout);
                socket.setKeepAlive(false);

                //dispatch each client in another thread in order to continue listening
                new Thread(new RequestDispatcher(socket)).start();
            }

        } catch (IOException e) {
            logger.error("the process is already running or another process is using the same port");
            System.exit(0);
        } finally {
            IOUtilities.closeServerSocket(serverSocket);
        }

    } // end startServer
    
} // end class
