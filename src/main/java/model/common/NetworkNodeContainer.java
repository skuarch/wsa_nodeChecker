package model.common;

import java.util.ArrayList;
import java.util.HashMap;
import model.beans.NetworkNode;

/**
 *
 * @author skuarch
 */
public class NetworkNodeContainer {

    private static final HashMap<String, ArrayList<NetworkNode>> networkNodeContainer = new HashMap<>();

    //==========================================================================
    private NetworkNodeContainer() {
    } // end NetworkNodeContainer

    //==========================================================================
    public static void addArrayList(String nameScheduler, ArrayList<NetworkNode> nodes) {

        if (nameScheduler == null) {
            throw new IllegalArgumentException("name of scheduler is null or empty");
        }

        //if (nodes == null || nodes.size() < 1) {
        if (nodes == null) {
            throw new IllegalArgumentException("nodes are null");
        }

        if (networkNodeContainer.containsKey(nameScheduler)) {
            throw new RuntimeException("the container already has " + nameScheduler);
        }

        networkNodeContainer.put(nameScheduler, nodes);

    } // end addArrayList

    //==========================================================================
    public static void removeArrayList(String nameScheduler) {

        if (nameScheduler == null) {
            throw new IllegalArgumentException("name of scheduler is null or empty");
        }

        networkNodeContainer.remove(nameScheduler);

    } // end addArrayList

    //==========================================================================
    public static ArrayList<NetworkNode> getArrayList(String nameScheduler) {

        if (nameScheduler == null) {
            throw new IllegalArgumentException("name of scheduler is null or empty");
        }

        ArrayList<NetworkNode> nodes = networkNodeContainer.get(nameScheduler);

        if (nodes == null) {
            throw new NullPointerException("arrayList " + nameScheduler + " doesn't exists");
        }

        return nodes;

    } // end getArrayList

    //==========================================================================
    public static void updateArrayList(String nameScheduler, ArrayList<NetworkNode> nodes) {

        if (!networkNodeContainer.containsKey(nameScheduler)) {
            throw new RuntimeException("the arrayList " + nameScheduler + " doesn't exists");
        }

        removeArrayList(nameScheduler);
        addArrayList(nameScheduler, nodes);

    } // end update

} // end class
