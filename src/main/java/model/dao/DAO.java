package model.dao;

import org.hibernate.Session;

/**
 * Data Access Object to Hibernate.
 * @author skuarch
 */
public class DAO {
    
    private Session session = null; 
    
    //==========================================================================
    /**
     * this class doesn't require a constructor.
     */
    private DAO(){
    
    } // end DAO;
    
    
    private void startSession(){
    
    }
    
    
} // end class