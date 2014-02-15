package model.common;

import java.util.ArrayList;
import model.beans.Notifier;
import model.net.ClientRestfulPost;
import org.apache.log4j.Logger;
import org.json.JSONObject;

/**
 *
 * @author skuarch
 */
public final class Notificator {

    private static final Logger logger = Logger.getLogger(Notificator.class);
    private static ArrayList<Notifier> notifiers = ModelNotifier.getNotifiers();

    //==========================================================================
    public Notificator() {

    } // end Notificator

    //==========================================================================
    public void sendMultipleNotification(final JSONObject json) throws Exception {

        new Thread(new Runnable() {

            @Override
            public void run() {

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
                    logger.error("sendMultipleNotification", e);
                }

            }
        }).start();

    } // end sendMultipleNotification

    //==========================================================================
    public static void reloadNotificators() {
        notifiers = ModelNotifier.getNotifiers();
    }

} // end class
