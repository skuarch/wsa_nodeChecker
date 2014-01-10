package model.net;

/**
 *
 * @author skuarch
 */
public interface ClientRestful {
    
    public void send(String text) throws Exception;
    public String receive() throws Exception;
    //public void execute();        
    
} // end interface