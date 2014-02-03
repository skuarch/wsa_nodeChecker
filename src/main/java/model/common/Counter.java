package model.common;

/**
 *
 * @author skuarch
 */
public class Counter {

    private static Integer counter = Integer.valueOf(0);

    //==========================================================================
    private Counter() {
    } // end Counter

    //==========================================================================
    public static synchronized void increaseCounter() {
        ++counter;
    }

    //==========================================================================
    public static synchronized void decreaseCounter() {
        --counter;
    }

    //==========================================================================
    public static int getCounter() {
        return counter;
    }

} // end class
