package model.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import model.beans.Scheduler;
import model.dao.DAO;

/**
 *
 * @author skuarch
 */
public class ModelScheduler {

    //==========================================================================
    private ModelScheduler() {
    } // end ModelScheduler 

    //==========================================================================
    public static Scheduler getScheduler(long id) {

        if (id < 1) {
            throw new IllegalArgumentException("id is incorrect");
        }

        Scheduler scheduler = DAO.get(id, new Scheduler());

        return scheduler;

    } // end getScheduler

    //==========================================================================
    public static Scheduler getScheduler(String name) {

        if (name == null || name.length() < 1) {
            throw new IllegalArgumentException("name is null or empty");
        }

        List<Scheduler> list = null;
        HashMap parameters = new HashMap();
        parameters.put("name", name);
        list = DAO.query("getSchedulerByName", parameters, new Scheduler());

        if (list == null || list.size() < 1) {
            return null;
        }

        Scheduler scheduler = list.get(0);

        return scheduler;

    } // end getScheduler

    //==========================================================================
    public static long createScheduler(Scheduler scheduler) {

        if (scheduler == null) {
            throw new IllegalArgumentException("shceduler is null");
        }

        return DAO.create(scheduler);

    } // end createScheduler

    //==========================================================================
    public static void deleteScheduler(Scheduler scheduler) {

        if (scheduler == null) {
            throw new IllegalArgumentException("shceduler is null");
        }

        DAO.delete(scheduler);

    } // end deleteScheduler

    //==========================================================================
    public static ArrayList<Scheduler> getSchedulers() {

        ArrayList<Scheduler> schedulers = new ArrayList<>(DAO.getList(new Scheduler()));
        return schedulers;

    } // getSchedulers

} // end class
