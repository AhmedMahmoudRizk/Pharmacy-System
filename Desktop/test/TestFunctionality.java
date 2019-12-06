/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import DBManagementLayer.UserQuery;
import DBManagementLayer.myconnector;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Yahia
 */
public class TestFunctionality {

    UserQuery userQuery;
    myconnector con;

    public TestFunctionality() {
        try {
            con = new myconnector();
            userQuery = userQuery.getInstance(con);
        } catch (Exception ex) {
            Logger.getLogger(TestFunctionality.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testUserInsertionMethod() {

        assertEquals("must return empty string for success", "", userQuery.createUser("ya@gmail.com", "s", "01234", "yahia", "sh",
                "11918"));
        assertEquals("must return error since duplicate email", "Email Error", userQuery.createUser("ya@gmail.com", "s", "01234", "yahia", "sh",
                "11918"));
        assertEquals("one of the field is empty", "Parameter Error", userQuery.createUser("ya@gmail.com", "s", "", "yahia", "sh",
                "11918"));

    }

    @Test
    public void testlogInMethod() {
        try {

            assertEquals("must return empty string for success", "", userQuery.createUser("ah@gmail.com", "s", "01234", "yahia", "sh",
                    "11918"));
            assertTrue("Signed in successfully", userQuery.logIn("ah@gmail.com", "01234"));
            assertFalse("should be false for wrong password", userQuery.logIn("ah@gmail.com", "5112"));
            assertFalse("should be false for wrong email", userQuery.logIn("s@gmail.com", "01234"));
        } catch (SQLException ex) {
            Logger.getLogger(TestFunctionality.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Test
    public void testAddDrugMethod() {

        assertEquals("must return empty string for success", "", userQuery.addNewDrug("ahah", "isp", "skskdj", "50", "2",
                "1", "Packet", "sdksk", "1990-6-12", "1", "1990-8-13"));
        assertEquals("Must return wrong since id is duplicate", "DRUG ID already exist", userQuery.addNewDrug("ahah", "isp", "skskdj", "50", "2",
                "1", "Packet", "sdksk", "1990-6-12", "1", "1990-8-13"));
        assertEquals("one of the parameters is empty", "Parameter Error", userQuery.addNewDrug("", "isp", "skskdj", "50", "2",
                "1", "Packet", "sdksk", "1990-6-12", "1", "1990-8-13"));
        assertEquals("one of the int parameters is non integer", "Parameter Error", userQuery.addNewDrug("adsd", "isp", "skskdj", "assa", "2",
                "1", "Packet", "sdksk", "1990-6-12", "1", "1990-8-13"));

    }

    @Test
    public void editDrug() {

        assertEquals("must return empty string for success", "", userQuery.addNewDrug("signup", "isp", "skskdj", "50", "2",
                "1", "Packet", "sdksk", "1990-6-12", "1", "1990-8-13"));
        assertEquals("must return empty string for success", "", userQuery.editDrug("signup", "sds", "s", "50", "2",
                "1", "Packet"));
        assertEquals("Must return wrong since one of the parameter is empty", "Parameter Error", userQuery.editDrug("signup", "", "skskdj", "50", "2",
                "1", "Packet"));
        assertEquals("Must return wrong since int parameter was changed to string", "Parameter Error", userQuery.editDrug("signup", "isp", "skskdj", "ssas", "2",
                "1", "Packet"));

    }

    @Test
    public void editUser() {
        assertEquals("must return empty string for success", "", userQuery.createUser("mark@gmail.com", "s", "01234", "yahia", "sh",
                "11918"));
        assertEquals("must return empty string for success", "", userQuery.editPersonalInformation("email", "mark", "1223", "udusud", "sdsdad",
                "011"));
        assertEquals("Must return wrong since one of the parameters is empty", "Parameter Error", userQuery.editPersonalInformation("email", "mark", "", "udusud", "sdsdad",
                "011"));
    }
    
   
    

}
