package model.common;

import java.util.HashMap;

/**
 *
 * @author skuarch
 */
public class SchedulerContainer {

    private static final HashMap<String, SchedulerProcessor> schedulerContainer = new HashMap<>();

    //==========================================================================
    private SchedulerContainer() {
    }

    //==========================================================================
    public static void addSchedulerProcessor(SchedulerProcessor schedulerProcessor) {

        String name = schedulerProcessor.getSchedulerName();

        if (name == null || name.length() < 1) {
            throw new IllegalArgumentException("schedulerProcessor doesn't have name");
        }

        if (schedulerContainer.containsKey(name)) {
            throw new RuntimeException(schedulerProcessor.getSchedulerName() + "already exists");
        }

        schedulerContainer.put(schedulerProcessor.getSchedulerName(), schedulerProcessor);

    } // end addSchedulerProcessor

    //==========================================================================
    public static SchedulerProcessor getSchedulerProcessor(String name) {

        if (name == null || name.length() < 1) {
            throw new IllegalArgumentException("schedulerProcessor doesn't have name");
        }

        SchedulerProcessor schedulerProcessor = schedulerContainer.get(name);

        if (schedulerProcessor == null) {
            throw new NullPointerException("schedulerProcessor " + name + " doesn't exists");
        }

        return schedulerProcessor;

    } // end getSchedulerProcessor

} // end class
