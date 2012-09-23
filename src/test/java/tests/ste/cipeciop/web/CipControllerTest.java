/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tests.ste.cipeciop.web;

import static org.junit.Assert.*;
import        org.junit.Test;

import ste.cipeciop.Constants;
import com.funambol.tools.test.BeanShellTest;
import ste.cipeciop.CipCiopManager;
import ste.cipeciop.test.web.mock.HttpServletRequestMock;
import ste.cipeciop.test.web.mock.ServletContextMock;

/**
 *
 * @author ste
 */
public class CipControllerTest extends BeanShellTest implements Constants {
    
    public CipControllerTest() throws Exception {
        setCommandsDirectory("src/main/webapp/WEB-INF/commands");
        setBshFileName("src/main/webapp/controllers/cip.bsh");
    }
    
    @Override
    protected void beanshellSetup() throws Exception {
        ServletContextMock context = new ServletContextMock();
        HttpServletRequestMock r = new HttpServletRequestMock(context);
        
        CipCiopManager ccm = new CipCiopManager();       
        context.setAttribute(ATTRIBUTE_CIPCIOP_MANAGER, ccm);
        
        beanshell.set("request", r);
    }

    @Test
    public void cipciopManagerExists() throws Exception {
        assertNotNull(beanshell.get("ccm"));
    }
}
