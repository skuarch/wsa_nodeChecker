package model.common;

import java.util.ArrayList;
import model.beans.Notifier;
import model.net.ClientRestfulPost;
import org.hibernate.Hibernate;
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
    /**
     * send notification to all the notifiers
     *
     * @param text
     * @throws java.lang.Exception
     */
    public void sendMultipleNotification(String text) throws Exception {

        JSONObject json = new JSONObject();
        String proxyUrl = null;

        try {

            Hibernate.initialize(Notifier.class);

            proxyUrl = new CustomProperties().getStringPropertie("proxy.url");

            json.accumulate("request", "notification");
            json.accumulate("expectedReturn", "true");
            json.accumulate("description", text);

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
    public void sendMultipleNotification(JSONObject json) throws Exception {

        String proxyUrl = null;

        try {
            System.out.println(notifiers.size() + " putos");
            Hibernate.initialize(Notifier.class);

            proxyUrl = new CustomProperties().getStringPropertie("proxy.url");

            json.accumulate("request", "notification");
            json.accumulate("expectedReturn", "true");

            for (Notifier notifier : notifiers) {

                json.accumulate("url", notifier.getUrl());

                ClientRestfulPost crp = new ClientRestfulPost(proxyUrl);
                crp.send(json.toString());
                crp.post();
                System.out.println(crp.receive());
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
