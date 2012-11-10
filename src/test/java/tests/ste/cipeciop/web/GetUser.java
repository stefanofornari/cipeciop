/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tests.ste.cipeciop.web;

import static org.junit.Assert.*;
import        org.junit.Test;

import ste.cipeciop.Constants;
import com.funambol.tools.test.BeanShellTest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import ste.cipeciop.Cip;
import ste.cipeciop.CipCiopManager;
import ste.cipeciop.test.web.mock.HttpServletRequestMock;
import ste.cipeciop.test.web.mock.ServletContextMock;
import tests.ste.cipeciop.CipCiopManagerTest;

/**
 *
 * @author ste
 */
public class GetUser extends BeanShellTest implements Constants {
    
    public static final String TEST_USERNAME1 = "userid@yahoo.com";
    public static final String TEST_USERNAME2 = "<first last>";
    public static final String TEST_USERNAME3 = TEST_USERNAME2
                                              + TEST_USERNAME1
                                              ;
    public static final String TEST_USERNAME4 = "<>" 
                                              + TEST_USERNAME1
                                              ;
    public static final String TEST_USERNAME5 = ">"
                                              + TEST_USERNAME1
                                              ;
    public static final String TEST_USERNAME6 = "<>";
    
    public GetUser() throws Exception {
        setCommandsDirectory("src/main/webapp/WEB-INF/commands");
        setBshFileName("src/main/webapp/WEB-INF/commands/getUser.bsh");
    }
    
    @Override
    protected void beanshellSetup() throws Exception {
    }

    @Test
    public void voidAndEmpty() throws Throwable {
        String userid = (String)exec("getUser", (String)null);
        assertNull(userid);
        
        userid = (String)exec("getUser", "");
        
        assertNull(userid);
    }
    
    @Test
    public void idOnly() throws Throwable {
        HashMap<String, String> user = (HashMap)exec("getUser", TEST_USERNAME1);
        assertEquals(TEST_USERNAME1, user.get(KEY_USER_ID));
        assertNull(user.get(KEY_USER_NAME));
    }
    
    @Test
    public void displayOnly() throws Throwable {
        HashMap<String, String> user = (HashMap)exec("getUser", TEST_USERNAME2);
        assertEquals(TEST_USERNAME2.substring(1, TEST_USERNAME2.length()-1), user.get(KEY_USER_NAME));
        assertNull(user.get(KEY_USER_ID));
    }

    @Test
    public void idAndDisplay() throws Throwable {
        HashMap<String, String> user = (HashMap)exec("getUser", TEST_USERNAME3);
        assertEquals(TEST_USERNAME1, user.get(KEY_USER_ID));
        assertEquals(TEST_USERNAME2.substring(1, TEST_USERNAME2.length()-1), user.get(KEY_USER_NAME));
    }
    
    @Test
    public void cornerCases() throws Throwable {
        HashMap<String, String> user = (HashMap)exec("getUser", TEST_USERNAME4);
        assertEquals(TEST_USERNAME1, user.get(KEY_USER_ID));
        assertEquals("", user.get(KEY_USER_NAME));
        
        user = (HashMap)exec("getUser", TEST_USERNAME5);
        assertEquals(TEST_USERNAME1, user.get(KEY_USER_ID));
        assertEquals("", user.get(KEY_USER_NAME));
        
        user = (HashMap)exec("getUser", TEST_USERNAME6);
        assertNull(user.get(KEY_USER_ID));
        assertEquals("", user.get(KEY_USER_NAME));
    }

}
