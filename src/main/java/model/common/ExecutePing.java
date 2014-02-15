package model.common;

import java.io.IOException;

/**
 *
 * @author skuarch
 */
public final class ExecutePing {

    private static final String FILE = PingFile.PATH;

    //==========================================================================
    public ExecutePing() {
    } // end ExecutePing

    //==========================================================================
    public String run(String host) throws IOException, InterruptedException, Exception {

        //return Boolean.parseBoolean(ExecuteCommand.exec(FILE + host));
        return ExecuteCommand.exec(FILE + host);

    } // end run

} // end class
