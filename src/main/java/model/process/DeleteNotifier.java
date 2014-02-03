package model.process;

import java.io.IOException;
import model.beans.Notifier;
import model.common.ModelNotifier;
import model.net.ModelSocket;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author skuarch
 */
public class DeleteNotifier extends Process {

    //==========================================================================
    public DeleteNotifier(ModelSocket ms, JSONObject jsono) {
        super(ms, jsono, DeleteNotifier.class);
    } // end DeleteNotifier

    //==========================================================================
    @Override
    public void run() throws IOException, NullPointerException {
        
        if (!jsono.has("name")) {
            throw new IllegalArgumentException("json is incorrect");
        }
        
        String name = null;
        Notifier notifier = null;
        
        try {
            
            name = jsono.getString("name");
            notifier = ModelNotifier.getNotifier(name);
            ModelNotifier.removeNotifier(notifier);
            
            sendResponse("{\"deleted\";\"true\"}");
            
        } catch (JSONException e) {
            logger.error("deleteNotifier", e);
            sendError(e.getMessage());
        } finally {
            ms.closeStreams();
        }
        
    } // end run
    
} // end class
