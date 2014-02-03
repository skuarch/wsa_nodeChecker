package model.process;

import java.io.IOException;
import java.util.ArrayList;
import model.beans.Notifier;
import model.common.ModelNotifier;
import model.net.ModelSocket;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author skuarch
 */
public class GetNotifiers extends Process {

    //==========================================================================
    public GetNotifiers(ModelSocket ms, JSONObject jsono) {
        super(ms, jsono, GetNotifiers.class);
    } // end GetNotifiers

    //==========================================================================
    @Override
    public void run() throws IOException, NullPointerException {
        ArrayList<Notifier> notifiers = null;
        JSONObject jsonResponse = new JSONObject();

        try {

            notifiers = ModelNotifier.getNotifiers();
            jsonResponse.put("notifiers", notifiers);

            ms.send("{\"added\":\"true\"}");

        } catch (IOException | IllegalArgumentException | JSONException e) {
            logger.error("addNotifier", e);
            sendError(e.getMessage());
        } finally {
            ms.closeStreams();
        }
    } // end run

} // end class
