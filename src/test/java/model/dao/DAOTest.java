package model.dao;

import java.util.ArrayList;
import java.util.List;
import model.beans.Notifier;
import junit.framework.TestCase;

/**
 *
 * @author skuarch
 */
public class DAOTest extends TestCase {

    public DAOTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of create method, of class DAO.
     */
    public void testCreate() {

        System.out.println("------------------------------------------------------------------------");
        System.out.println("create");

        Notifier notifier = new Notifier();
        notifier.setName("testdelete");
        notifier.setUrl("http://192.168.208.9:8080/sam5/notifications");
        notifier.setId(1);

        long expResult = 1;
        long result = new DAO().create(notifier);

        System.out.println(result);

        //assertEquals(expResult, result);
        new DAO().delete(notifier);

    }

    /**
     * Test of delete method, of class DAO.
     */
    public void testDelete() {

        System.out.println("------------------------------------------------------------------------");
        System.out.println("delete");
        
        Notifier notifier = new Notifier();
        notifier.setName("testdelete");
        notifier.setUrl("http://192.168.208.9:8080/sam5/notifications");
        notifier.setId(1);

        new DAO().create(notifier);

        new DAO().delete(notifier);

    }

    /**
     * Test of get method, of class DAO.
     */
    public void testGet() {

        System.out.println("------------------------------------------------------------------------");
        System.out.println("get");

        Notifier notifier = new Notifier();
        notifier.setName("testdelete");
        notifier.setId(1);
        notifier.setUrl("http://192.168.208.9:8080/sam5/notifications");

        new DAO().create(notifier);

        System.out.println(new DAO().get(1, new Notifier()));

        new DAO().delete(notifier);

    }

    /**
     * Test of getList method, of class DAO.
     */
    public void testGetList() {

        System.out.println("------------------------------------------------------------------------");
        System.out.println("getList");
        Notifier notifier = new Notifier();
        notifier.setName("testdelete");
        notifier.setId(1);
        notifier.setUrl("http://192.168.208.9:8080/sam5/notifications");

        new DAO().create(notifier);

        List<Notifier> list = new DAO().getList(new Notifier());
        ArrayList<Notifier> arrayList = new ArrayList<>(list);

        System.out.println(arrayList);

        new DAO().delete(notifier);
    }

    /**
     * Test of hql method, of class DAO.
     */
    public void testHql_String_GenericType() {

        System.out.println("------------------------------------------------------------------------");
        System.out.println("hql");
        
        System.out.println(new DAO().hql("from Notifier", new Notifier()));

    }

    /**
     * Test of hql method, of class DAO.
     */
    public void testHql_3args() {
        
        System.out.println("------------------------------------------------------------------------");
        System.out.println("hql");

        System.out.println(new DAO().hql("from Notifier", new Notifier(), 10));
    }

    /**
     * Test of query method, of class DAO.
     */
    public void testQuery_String_GenericType() {
        
        System.out.println("------------------------------------------------------------------------");        
        System.out.println("query");
        
        System.out.println(new DAO().query("notifiers", new Notifier()));
    }

    /**
     * Test of query method, of class DAO.
     */
    public void testQuery_3args() {

    }

    /**
     * Test of query method, of class DAO.
     */
    public void testQuery_4args() {
        System.out.println("query");

    }

    /**
     * Test of update method, of class DAO.
     */
    public void testUpdate() {
        
        System.out.println("------------------------------------------------------------------------");
        System.out.println("update");

        Notifier notifier = new Notifier();
        notifier.setName("testdelete");
        notifier.setId(1);
        notifier.setUrl("http://192.168.208.9:8080/sam5/notifications");

        new DAO().create(notifier);
        notifier.setName("testdelete");
        notifier.setUrl("http://192.168.208.9:8080/sam5/notifications");
        new DAO().update(notifier);
        new DAO().delete(notifier);
    }

}
