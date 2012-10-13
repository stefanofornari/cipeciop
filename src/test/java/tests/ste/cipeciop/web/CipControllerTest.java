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
import tests.ste.cipeciop.CipCiopTestUtil;

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
        CipCiopTestUtil.deleteAllCips();
        
        ServletContextMock context = new ServletContextMock();
        HttpServletRequestMock r = new HttpServletRequestMock(context);
        
        CipCiopManager ccm = new CipCiopManager();
        
        Cip cip1 = new Cip(CipCiopTestUtil.TEST_TEXT1), cip2 = new Cip(CipCiopTestUtil.TEST_TEXT2);
        cip1.setFrom(CipCiopTestUtil.TEST_FROM1); cip1.setTo(CipCiopTestUtil.TEST_TO1);
        cip2.setFrom(CipCiopTestUtil.TEST_FROM2); cip2.setTo(CipCiopTestUtil.TEST_TO2);
        
        ccm.addCip(cip1); ccm.addCip(cip2);
        
        context.setAttribute(ATTRIBUTE_CIPCIOP_MANAGER, ccm);
        
        HttpSession s = r.getSession();
        Map attributes = new HashMap();
        attributes.put(ALIAS_USER_ID, CipCiopTestUtil.TEST_FROM1);
        s.setAttribute(ATTRIBUTE_IDENTIFIER, attributes);
        
        beanshell.set("request", r);
        beanshell.set("session", r.getSession());
    }

    @Test
    public void cipciopManagerExists() throws Exception {
        assertNotNull(beanshell.get("ccm"));
    }
    
    @Test
    //
    // I should get only one cip out of 2
    //
    public void myCips() throws Exception {
        List cips = (List)beanshell.eval("request.getAttribute(\"cips\")");
        
        assertEquals(1, cips.size());
    }
    
    @Test
    public void noSession() throws Throwable {
        HttpSession s = (HttpSession)beanshell.get("session");
        s.setAttribute(ATTRIBUTE_IDENTIFIER, null);
        
        exec();
        
        //
        // Nothing to do, just no exceptions
        //
    }
    
    @Test
    public void addCip() throws Exception {
        //
        // As TEST_FROM1 nothing changes
        //
        beanshell.set("to", CipCiopTestUtil.TEST_TO1);
        beanshell.set("cip", CipCiopTestUtil.TEST_TEXT2);
        
        exec();
        
        List cips = (List)beanshell.eval("request.getAttribute(\"cips\")");
        assertEquals(1, cips.size());
        
        //
        // As TEST_FROM2 I have a new cip
        //
        HttpSession s = (HttpSession)beanshell.get("session");
        Map attributes = (Map)s.getAttribute(ATTRIBUTE_IDENTIFIER);
        attributes.put(ALIAS_USER_ID, CipCiopTestUtil.TEST_FROM2);
        beanshell.set("to", CipCiopTestUtil.TEST_TO2);
        beanshell.set("cip", "some text");
        
        exec();
        
        cips = (List)beanshell.eval("request.getAttribute(\"cips\")");
        assertEquals(2, cips.size());
        assertEquals(CipCiopTestUtil.TEST_FROM1, ((Cip)cips.get(1)).getFrom());
        assertEquals(CipCiopTestUtil.TEST_TEXT2, ((Cip)cips.get(1)).getText());
    }
}
