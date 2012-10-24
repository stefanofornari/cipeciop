/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tests.ste.cipeciop.ajax;

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
import tests.ste.cipeciop.CipCiopTestUtil;

/**
 *
 * @author ste
 */
public class CipControllerTest extends BeanShellTest implements Constants {
    
    
    
    public CipControllerTest() throws Exception {
        setCommandsDirectory("src/main/webapp/WEB-INF/commands");
        setBshFileName("src/main/webapp/ajax/controllers/cip.bsh");
    }
    
    @Override
    protected void beanshellSetup() throws Exception {
        CipCiopTestUtil.deleteAllCipCiop();
        
        ServletContextMock context = new ServletContextMock();
        HttpServletRequestMock r = new HttpServletRequestMock(context);
        
        CipCiopManager ccm = CipCiopTestUtil.createCCMForUser1();
        
        HttpSession s = r.getSession();
        Map attributes = new HashMap();
        attributes.put(ALIAS_USER_ID, CipCiopTestUtil.TEST_USER1);
        s.setAttribute(ATTRIBUTE_IDENTIFIER, attributes);
        
        beanshell.set("request", r);
        beanshell.set("session", r.getSession());
    }

    @Test
    public void deleteCip() throws Exception {
        //
        // No id given, no cips deleted
        //
        beanshell.set(AJAX_ACTION, AJAX_ACTION_DELETE);
        
        exec();
        
        CipCiopManager ccm  = new CipCiopManager(CipCiopTestUtil.TEST_USER1);
        assertEquals(3, ccm.getCips().size());
        
        //
        // now we delete one cip, the change should be reflected by ccm
        //
        Cip cip = ccm.getCips().get(0);
        beanshell.set(AJAX_PARAM_ID, String.valueOf(cip.getId()));
        
        exec();
        
        assertEquals(2, ccm.getCips().size());
        assertFalse(cip.getId() == ccm.getCips().get(0).getId());
        
    }
    
   
}
