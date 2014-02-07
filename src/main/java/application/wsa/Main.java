package application.wsa;

import java.io.IOException;
import model.common.CustomProperties;
import model.common.PingFile;
import model.common.SchedulerRunner;
import model.common.ShutdownHook;
import model.common.StartServer;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * this application sends a ICMP ping request for check the availability of the
 * network host.
 *
 */
public final class Main {

    private static final Logger logger = Logger.getLogger(Main.class);

    static {
        PropertyConfigurator.configure("configuration/log4j.properties");
    }

    //==========================================================================
    /**
     * create a instance.
     */
    public Main() {
    } // end Main

    //==========================================================================
    /**
     * this program doesn't use a arguments in the command line.
     *
     * @param args
     */
    public static void main(String[] args) {

        try {

            PingFile.PATH = new CustomProperties().getStringPropertie("ping.file");
            Runtime.getRuntime().addShutdownHook(new ShutdownHook());
            logger.info("**** starting program ****");

            //run schedulers
            new SchedulerRunner().runStoredSchedulers();
            new SchedulerRunner().runSchedulerDeepPing();
            
            //create the serverSocket
            new StartServer().run();

        } catch (IOException ioe) {
            logger.error("main", ioe);
        }

    } // end main

} // end class
