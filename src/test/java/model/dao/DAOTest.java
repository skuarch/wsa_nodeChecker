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

        System.out.println("create");

        Notifier notifier = new Notifier();
        notifier.setName("testdelete");
        notifier.setHost("test");
        notifier.setPort(8080);
        notifier.setId(1);

        long expResult = 1;
        long result = DAO.create(notifier);

        System.out.println(result);
        
        //assertEquals(expResult, result);

        DAO.delete(notifier);

    }

    /**
     * Test of delete method, of class DAO.
     */
    public void testDelete() {

        System.out.println("delete");
        Notifier notifier = new Notifier();
        notifier.setName("testdelete");
        notifier.setHost("test");
        notifier.setPort(8080);
        notifier.setId(1);

        DAO.create(notifier);

        DAO.delete(notifier);

    }

    /**
     * Test of get method, of class DAO.
     */
    public void testGet() {

        System.out.println("get");

        Notifier notifier = new Notifier();
        notifier.setName("testdelete");
        notifier.setId(1);
        notifier.setPort(8080);
        notifier.setHost("something");

        DAO.create(notifier);

        System.out.println(DAO.get(1, new Notifier()));

        DAO.delete(notifier);

    }

    /**
     * Test of getList method, of class DAO.
     */
    public void testGetList() {

        System.out.println("getList");
        Notifier notifier = new Notifier();
        notifier.setName("testdelete");
        notifier.setId(1);
        notifier.setPort(8080);
        notifier.setHost("something");

        DAO.create(notifier);

        List<Notifier> list = DAO.getList(new Notifier());
        ArrayList<Notifier> arrayList = new ArrayList<>(list);

        System.out.println(arrayList);

        DAO.delete(notifier);
    }

    /**
     * Test of hql method, of class DAO.
     */
    public void testHql_String_GenericType() {

        System.out.println("hql");
        System.out.println(DAO.hql("from Notifier", new Notifier()));

    }

    /**
     * Test of hql method, of class DAO.
     */
    public void testHql_3args() {
        System.out.println("hql");

        System.out.println(DAO.hql("from Notifier", new Notifier(), 10));
    }

    /**
     * Test of query method, of class DAO.
     */
    public void testQuery_String_GenericType() {
        System.out.println("query");
        System.out.println(DAO.query("notifiers", new Notifier()));
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
        System.out.println("update");

        Notifier notifier = new Notifier();
        notifier.setName("testdelete");
        notifier.setId(1);
        notifier.setPort(8080);
        notifier.setHost("something");
        
        DAO.create(notifier);
        notifier.setName("testdelete");
        notifier.setPort(5000);
        DAO.update(notifier);
        DAO.delete(notifier);
    }

}
