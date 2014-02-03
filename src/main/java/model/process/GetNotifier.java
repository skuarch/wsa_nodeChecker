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
public class GetNotifier extends Process {

    //==========================================================================
    public GetNotifier(ModelSocket ms, JSONObject jsono) {
        super(ms, jsono, GetNotifier.class);
    } // end GetNotifier

    //==========================================================================
    @Override
    public void run() throws IOException, NullPointerException {
        
        if (!jsono.has("name")) {
            throw new IllegalArgumentException("json is incorrect");
        }

        String name = null;
        Notifier notifier = null;
        JSONObject jsonRsponse = null;

        try {

            name = jsono.getString("name");
            notifier = ModelNotifier.getNotifier(name);

            jsonRsponse = new JSONObject();
            jsonRsponse.put("is", notifier.getId());
            jsonRsponse.put("name", notifier.getName());
            jsonRsponse.put("url", notifier.getUrl());

            sendResponse(jsonRsponse.toString());

        } catch (IOException | JSONException e) {
            logger.error("deleteNotifier", e);
            sendError(e.getMessage());
        } finally {
            ms.closeStreams();
        }
    }

} // end class
