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
public class GetUserNameTest extends BeanShellTest implements Constants {
    
    public static final String TEST_USERID = "userid@yahoo.com";
    
    public GetUserNameTest() throws Exception {
        setCommandsDirectory("src/main/webapp/WEB-INF/commands");
        //setBshFileName("src/main/webapp/controllers/cip.bsh");
    }
    
    @Override
    protected void beanshellSetup() throws Exception {
        ServletContextMock context = new ServletContextMock();
        HttpServletRequestMock r = new HttpServletRequestMock(context);
        
        HttpSession s = r.getSession();
        Map attributes = new HashMap();
        attributes.put("userid", TEST_USERID);
        s.setAttribute(ATTRIBUTE_IDENTIFIER, attributes);
        
        beanshell.set("session", s);
    }

    @Test
    public void cipciopManagerExists() throws Exception {
        String userid = (String)beanshell.eval("getUserName()");
        assertEquals(TEST_USERID, userid);
    }

}
