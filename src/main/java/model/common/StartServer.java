package model.common;

import java.io.IOException;
import model.net.SocketCreator;

/**
 * Read some properties in order to start the server.
 *
 * @author skuarch
 */
public final class StartServer {

    //==========================================================================
    /**
     * create a instance.
     */
    public StartServer() {
    } // end StartServer

    //==========================================================================
    /**
     * read the application file to get the port, end call the SocketProccesor
     * to listen the port
     *
     * @throws IOException
     */
    public void run() throws IOException {

        CustomProperties properties = new CustomProperties();
        int port;
        
        try {

            //retrieving data from application.properties               
            port = properties.getIntPropertie("listen.port");

            // ok, let's go to listen a port
            new SocketCreator().startServer(port);

        } catch (IOException e) {
            throw e;
        } finally {
            properties = null;
        }

    } // end run

} // end class
