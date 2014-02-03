package model.process;

import java.io.IOException;
import model.beans.Notifier;
import model.common.ModelNotifier;
import model.common.Notificator;
import model.net.ModelSocket;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author skuarch
 */
public class AddNotifier extends Process {

    //==========================================================================
    public AddNotifier(ModelSocket ms, JSONObject jsono) {
        super(ms, jsono, AddNotifier.class);
    } // end AddNotifier

    //==========================================================================
    @Override
    public void run() throws IOException, NullPointerException {

        if (!jsono.has("notifierName") || !jsono.has("url")) {
            throw new IllegalArgumentException("json is incorrect");
        }

        Notifier notifier = null;

        try {

            notifier = new Notifier();
            notifier.setName(jsono.getString("notifierName"));
            notifier.setUrl(jsono.getString("url"));

            if (ModelNotifier.existsNotifier(notifier.getName())) {
                sendResponse("{\"error\":\"notifier already exists\"}");
                logger.error("run", new IllegalArgumentException("notifier already exists"));
                return;
            }

            ModelNotifier.addNotifier(notifier);
            Notificator.reloadNotificators();
            sendResponse("{\"added\":\"true\"}");

        } catch (IOException | IllegalArgumentException | JSONException | NullPointerException e) {
            logger.error("run", e);
            sendError(e.getMessage());
        } finally {
            ms.closeStreams();
        }
    } // end run

} // end class
