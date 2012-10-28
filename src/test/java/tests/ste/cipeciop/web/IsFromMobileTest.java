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
public class IsFromMobileTest extends BeanShellTest implements Constants {
    
    public static final String TEST_X_WAP_PROFILE = "http://wap.samsungmobile.com/uaprof/GT-I9000.xml";
    
    public IsFromMobileTest() throws Exception {
        setBshFileName("src/main/webapp/WEB-INF/commands/isFromMobile.bsh");
    }
    
    @Override
    protected void beanshellSetup() throws Exception {
    }

    @Test
    public void failAndOk() throws Throwable {
        HttpServletRequestMock r = new HttpServletRequestMock();
        beanshell.set("request", r);
        
        assertFalse((Boolean)exec("isFromMobile"));
        
        r.setHeader("x-wap-profile", TEST_X_WAP_PROFILE);
        assertTrue((Boolean)exec("isFromMobile"));
    }
}
