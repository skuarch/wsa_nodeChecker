package model.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import model.beans.NetworkNode;
import model.dao.DAO;

/**
 *
 * @author skuarch
 */
public class ModelNetworkNode {

    //==========================================================================
    private ModelNetworkNode() {
    } // end ModelNetworkNode

    //==========================================================================
    public static long createNetworkNode(NetworkNode networkNode) {

        if (networkNode == null) {
            throw new NullPointerException("networkNode is null");
        }

        if (networkNode.getScheduler() == null) {
            throw new NullPointerException("networkNode scheduler is null");
        }

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("host", networkNode.getHost());
        List<NetworkNode> list = new ArrayList<>(new DAO().query("getNetworkNodeByHost", parameters, new NetworkNode()));

        if (list.size() > 0) {
            throw new RuntimeException("network node already exists");
        }

        long id;

        id = new DAO().create(networkNode);

        return id;

    } // end createNetworkNode

    //==========================================================================
    public static void deleteNetworkNode(NetworkNode networkNode) {

        if (networkNode == null) {
            throw new NullPointerException("networkNode is null");
        }

        if (networkNode.getScheduler() == null) {
            throw new NullPointerException("networkNode scheduler is null");
        }

        new DAO().delete(networkNode);

    } // end deleteNetworkNode

} // end clas
