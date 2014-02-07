package model.common;

/**
 *
 * @author skuarch
 */
public class CounterDeepPing {

    private static Integer counter = Integer.valueOf(0);

    //==========================================================================
    private CounterDeepPing() {
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
