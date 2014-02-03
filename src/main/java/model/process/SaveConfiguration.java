package model.process;

import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;
import model.common.ModelConfiguration;
import model.net.ModelSocket;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author skuarch
 */
public class SaveConfiguration extends Process {

    //==========================================================================
    public SaveConfiguration(ModelSocket ms, JSONObject jsono) {
        super(ms, jsono, SaveConfiguration.class);
    }

    //==========================================================================
    @Override
    public void run() throws IOException, NullPointerException {

        Properties properties = null;
        Iterator iterator = null;
        String key = null;
        String value = null;

        try {

            properties = new Properties();
            iterator = jsono.keys();

            while (iterator.hasNext()) {

                key = (String) iterator.next();
                value = jsono.getString(key);

                properties.setProperty(key, (String) value);

            }

            ModelConfiguration.saveConfiguration(properties);
            ms.send("{\"saved\":\"true\"}");

        } catch (JSONException | IOException e) {
            logger.error("run", e);
            sendError(e.getMessage());
        } finally {
            ms.closeStreams();
        }
        
    } // end run

} // end class
