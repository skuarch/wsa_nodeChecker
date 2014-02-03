package model.process;

import java.io.IOException;
import java.util.HashMap;
import model.common.ModelConfiguration;
import model.net.ModelSocket;
import org.json.JSONObject;

/**
 *
 * @author skuarch
 */
public class GetConfiguration extends Process {

    //==========================================================================
    public GetConfiguration(ModelSocket ms, JSONObject jsono) {
        super(ms, jsono, GetConfiguration.class);
    } // end GetConfiguration

    //==========================================================================
    @Override
    public void run() throws IOException, NullPointerException {

        HashMap<String, Object> configuration = null;

        try {

            configuration = ModelConfiguration.getHashMapConfiguration();
            ms.send(new JSONObject(configuration).toString());

        } catch (IOException e) {
            logger.error("run", e);
            sendError(e.getMessage());
        } finally {
            ms.closeStreams();
        }
    } // end run

} // end class
