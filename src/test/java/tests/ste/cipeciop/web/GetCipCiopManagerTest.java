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
import java.util.Map;
import javax.servlet.http.HttpSession;
import ste.cipeciop.CipCiopManager;
import ste.cipeciop.test.web.mock.HttpServletRequestMock;
import ste.cipeciop.test.web.mock.ServletContextMock;
import tests.ste.cipeciop.CipCiopTestUtil;

/**
 *
 * @author ste
 */
public class GetCipCiopManagerTest extends BeanShellTest implements Constants {
    
    public static final String TEST_USERID = "userid@yahoo.com";
    
    public GetCipCiopManagerTest() throws Exception {
        setBshFileName("src/main/webapp/WEB-INF/commands/getCipCiopManager.bsh");
    }
    
    @Override
    protected void beanshellSetup() throws Exception {
        HttpServletRequestMock r = new HttpServletRequestMock(new ServletContextMock());
        r.getSession().setAttribute(ATTRIBUTE_CIPCIOP_MANAGER, new CipCiopManager(CipCiopTestUtil.TEST_USER1));
        
        beanshell.set("request", r);
        beanshell.set("session", r.getSession());
    }

    @Test
    public void getCipCiopManager() throws Throwable {
        CipCiopManager ccm = (CipCiopManager)exec("getCipCiopManager");
        assertNotNull(ccm);
    }

}
