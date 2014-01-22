package model.common;

import java.io.IOException;

/**
 *
 * @author skuarch
 */
public final class ExecutePing {

    private static final String FILE = "/home/skuarch/NetBeansProjects/wsa_nodeChecker/ping.sh ";

    //==========================================================================
    public ExecutePing() {
    } // end ExecutePing

    //==========================================================================
    public boolean run(String host) throws IOException, InterruptedException, Exception {

        return Boolean.parseBoolean(ExecuteCommand.exec(FILE + host));

    } // end run

} // end class
