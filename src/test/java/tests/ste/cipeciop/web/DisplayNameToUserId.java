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
public class DisplayNameToUserId extends BeanShellTest implements Constants {
    
    public static final String TEST_USERNAME1 = "userid@yahoo.com";
    public static final String TEST_USERNAME2 = "<userid>";
    public static final String TEST_USERNAME3 = "<userid>userid@yahoo.com";
    
    public DisplayNameToUserId() throws Exception {
        setCommandsDirectory("src/main/webapp/WEB-INF/commands");
        setBshFileName("src/main/webapp/WEB-INF/commands/displayNameToUserId.bsh");
    }
    
    @Override
    protected void beanshellSetup() throws Exception {
    }

    @Test
    public void voidAndEmpty() throws Throwable {
        String userid = (String)exec("displayNameToUserId", (String)null);
        assertNull(userid);
        
        userid = (String)exec("displayNameToUserId", "");
        
        assertNull(userid);
    }
    
    @Test
    public void idOnly() throws Throwable {
        String userid = (String)exec("displayNameToUserId", TEST_USERNAME1);
        assertEquals(TEST_USERNAME1, userid);
    }
    
    @Test
    public void displayOnly() throws Throwable {
        String userid = (String)exec("displayNameToUserId", TEST_USERNAME2);
        assertEquals(TEST_USERNAME2, userid);
    }
    
    @Test
    public void idAndDisplay() throws Throwable {
        String userid = (String)exec("displayNameToUserId", TEST_USERNAME3);
        assertEquals(TEST_USERNAME1, userid);
    }

}
