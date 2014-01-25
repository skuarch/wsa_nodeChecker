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

        Scheduler scheduler = new DAO().get(id, new Scheduler());

        if (scheduler == null) {
            throw new NullPointerException("scheduler " + id + " doesn't exists");
        }

        return scheduler;

    } // end getScheduler

    //==========================================================================
    public static Scheduler getSchedulerByName(String name) {

        if (name == null || name.length() < 1) {
            throw new IllegalArgumentException("name is null or empty");
        }

        List<Scheduler> list = null;
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("name", name);
        list = new DAO().query("getSchedulerByName", parameters, new Scheduler());

        if (list == null || list.size() < 1) {
            throw new NullPointerException("scheduler " + name + " doesn't exists");
        }

        Scheduler scheduler = list.get(0);

        return scheduler;

    } // end getScheduler

    //==========================================================================
    public static boolean existsScheduler(String name) {

        if (name == null || name.length() < 1) {
            throw new IllegalArgumentException("name is null or empty");
        }

        List<Scheduler> list = null;
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("name", name);
        list = new DAO().query("getSchedulerByName", parameters, new Scheduler());

        if (list == null || list.size() < 1) {
            return false;
        } else {
            return true;
        }

    } // end getScheduler

    //==========================================================================
    /**
     * create Scheduler in the database.
     *
     * @param scheduler
     * @return
     */
    public static long createScheduler(Scheduler scheduler) {

        if (scheduler == null) {
            throw new IllegalArgumentException("scheduler is null");
        }
        System.out.println("creando scheduler");
        return new DAO().create(scheduler);

    } // end createScheduler

    //==========================================================================
    public static void deleteScheduler(Scheduler scheduler) {

        if (scheduler == null) {
            throw new IllegalArgumentException("scheduler is null");
        }

        new DAO().delete(scheduler);

    } // end deleteScheduler

    //==========================================================================
    public static ArrayList<Scheduler> getSchedulers() {

        ArrayList<Scheduler> schedulers = new ArrayList<>(new DAO().getList(new Scheduler()));
        return schedulers;

    } // getSchedulers

} // end class
