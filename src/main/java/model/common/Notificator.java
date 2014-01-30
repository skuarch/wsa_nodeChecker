package model.common;

import java.util.ArrayList;
import model.beans.Notifier;
import model.net.ClientRestfulPost;
import org.json.JSONObject;

/**
 *
 * @author skuarch
 */
public final class Notificator {

    private static ArrayList<Notifier> notifiers = ModelNotifier.getNotifiers();    

    //==========================================================================
    public Notificator() {

    } // end Notificator

    //==========================================================================
    public void sendMultipleNotification(JSONObject json) throws Exception {

        String proxyUrl = null;

        try {

            proxyUrl = new CustomProperties().getStringPropertie("proxy.url");
            
            for (Notifier notifier : notifiers) {

                json.accumulate("url", notifier.getUrl());

                ClientRestfulPost crp = new ClientRestfulPost(proxyUrl);
                crp.send(json.toString());
                crp.post();
                //System.out.println(crp.receive());
                crp.closeClient();

            }

        } catch (Exception e) {
            throw e;
        }

    } // end sendMultipleNotification

    //==========================================================================
    public static void reloadNotificators() {
        notifiers = ModelNotifier.getNotifiers();
    }

} // end class
