package model.common;

import org.apache.log4j.Logger;

/**
 *
 * @author skuarch
 */
public class SimplePing extends Thread {
    
    private static final Logger logger = Logger.getLogger(SimplePing.class);
    private String host = null;

    //==========================================================================
    public SimplePing(String host) {
        this.host = host;
    } // end SimplePing

    //==========================================================================
    @Override
    public void run() {
        
        boolean isAlive = false;
        
        try {
            
            isAlive = new ExecutePing().run(host);
            
            if (!isAlive) {
                
                //deepPing
                new Notificator().sendMultipleNotification("valio madres");
                
            }
            
        } catch (Exception e) {
            logger.error("run", e);
        }
        
    } // end run

} // end class
