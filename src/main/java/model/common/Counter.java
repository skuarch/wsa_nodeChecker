package model.common;

/**
 *
 * @author skuarch
 */
public class Counter {

    private  static Integer counter = new Integer(0);

    //==========================================================================
    private Counter() {
    } // end Counter

    //==========================================================================
    public static void increaseCounter() {
        synchronized (counter) {
            ++counter;
        }
    }

    //==========================================================================
    public static void decreaseCounter() {
        synchronized (counter) {
            --counter;
        }
    }

    //==========================================================================
    public static int getCounter() {
        return counter;
    }

} // end class
