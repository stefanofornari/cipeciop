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
import ste.cipeciop.Ciop;
import ste.cipeciop.Cip;
import ste.cipeciop.CipCiop;
import ste.cipeciop.CipCiopManager;
import ste.cipeciop.test.web.mock.HttpServletRequestMock;
import ste.cipeciop.test.web.mock.ServletContextMock;
import tests.ste.cipeciop.CipCiopTestUtil;

/**
 *
 * @author ste
 */
public class CipControllerTest extends BeanShellTest 
                               implements Constants {
    
    
    
    public CipControllerTest() throws Exception {
        setCommandsDirectory("src/main/webapp/WEB-INF/commands");
        setBshFileName("src/main/webapp/controllers/cip.bsh");
    }
    
    @Override
    protected void beanshellSetup() throws Exception {
        CipCiopTestUtil.deleteAllCipCiop();
        
        HttpServletRequestMock r = new HttpServletRequestMock(new ServletContextMock());
        
        HttpSession s = r.getSession();
        Map attributes = new HashMap();
        attributes.put(ALIAS_USER_ID, CipCiopTestUtil.TEST_USER1);
        s.setAttribute(ATTRIBUTE_IDENTIFIER, attributes);
        CipCiopTestUtil.prepareEnvironment();
        
        beanshell.set("request", r);
        beanshell.set("session", r.getSession());
    }

    @Test
    public void cipciopManagerExists() throws Exception {
        assertNotNull(beanshell.get("ccm"));
    }
        
    @Test
    public void myCipsAndCiops() throws Exception {
        exec();
        
        List cips = (List)beanshell.eval("request.getAttribute(\"cips\")");
        
        assertEquals(5, cips.size());
        
        //
        // Check the correct ordering
        //
        assertTrue(((CipCiop)cips.get(0)).getCreated().before(((CipCiop)cips.get(1)).getCreated()));
        assertTrue(((CipCiop)cips.get(1)).getCreated().before(((CipCiop)cips.get(2)).getCreated()));
        assertTrue(((CipCiop)cips.get(2)).getCreated().before(((CipCiop)cips.get(3)).getCreated()));
    }
    
    @Test
    public void noSession() throws Throwable {
        HttpServletRequestMock r = new HttpServletRequestMock(new ServletContextMock());
        beanshell.set("request", r);
        beanshell.set("session", r.getSession());
        
        exec();
    }
    
    @Test
    public void addCip() throws Exception {
        //
        // As TEST_FROM1 nothing changes
        //
        beanshell.set("to", CipCiopTestUtil.TEST_USER2);
        beanshell.set("cip", CipCiopTestUtil.TEST_TEXT2);
        
        exec();
        
        List cips = (List)beanshell.eval("request.getAttribute(\"cips\")");
        assertEquals(6, cips.size());
        
        //
        // As TEST_FROM2 I have a new ciop
        //
        HttpSession s = (HttpSession)beanshell.get("session");
        Map attributes = (Map)s.getAttribute(ATTRIBUTE_IDENTIFIER);
        attributes.put(ALIAS_USER_ID, CipCiopTestUtil.TEST_USER2);
        
        beanshell.unset("cip"); beanshell.unset("to");
        exec();
        
        cips = (List)beanshell.eval("request.getAttribute(\"cips\")");
        assertEquals(4, cips.size());
        assertEquals(CipCiopTestUtil.TEST_USER1, ((Ciop)cips.get(0)).getFrom());
    }
    
    @Test
    public void friendsAreAvailable() throws Throwable {
        HttpSession s = (HttpSession)beanshell.get("session");
        s.setAttribute(ATTRIBUTE_FRIENDS, null);
        exec();
        assertNotNull(s.getAttribute(ATTRIBUTE_FRIENDS));
    }
    
    @Test
    public void emoticons() throws Exception {
        beanshell.set("to", CipCiopTestUtil.TEST_USER2);
        beanshell.set("cip", CipCiopTestUtil.TEST_TEXT_WITH_ICONS_AND_URLS);
        
        exec();
        
        List<Cip> cips = (List)beanshell.eval("request.getAttribute(\"cips\")");
        assertEquals(6, cips.size());
        //
        // here we want just to make sure the text has been translated. See 
        // htmlize() for all cases
        //
        
        String t = cips.get(5).getText();
        assertFalse(t.contains(";)"));
        assertFalse(t.contains(":D"));
        assertTrue(t.contains("<img src"));
        assertTrue(t.contains("<a href"));
    }
    
    @Test
    public void cipFromMobile() throws Throwable {
        //
        // When a cip is sent from the mobile, its SEND_FROM_MOBILE flag shall
        // be set
        //
        
        //
        // Not from mobile
        //
        beanshell.set("to", CipCiopTestUtil.TEST_USER2);
        beanshell.set("cip", CipCiopTestUtil.TEST_TEXT_WITH_ICONS_AND_URLS);
        
        exec();
        
        List<Cip> cips = (List)beanshell.eval("request.getAttribute(\"cips\")");
        assertEquals(6, cips.size());
        assertFalse(cips.get(5).isFromMobile());
        assertFalse(cips.get(5).isSeen());
        
        //
        // From mobile
        //
        HttpServletRequestMock r = (HttpServletRequestMock)beanshell.get("request");
        r.setHeader("x-wap-profile", IsFromMobileTest.TEST_X_WAP_PROFILE);
        beanshell.set("to", CipCiopTestUtil.TEST_USER2);
        beanshell.set("cip", CipCiopTestUtil.TEST_TEXT_WITH_ICONS_AND_URLS);
        
        exec();
        
        cips = (List)beanshell.eval("request.getAttribute(\"cips\")");
        assertEquals(7, cips.size());
        assertTrue(cips.get(6).isFromMobile());
        
        CipCiopManager ccm = new CipCiopManager(CipCiopTestUtil.TEST_USER2);
        List<Ciop> ciops = ccm.getCiops();
        assertTrue(ciops.get(2).isFromMobile());
    }
    
    //@Test
    public void seen() throws Throwable {
        //
        // Once seen all ciops shall be flagged as seen
        //
        CipCiopManager ccm = new CipCiopManager(CipCiopTestUtil.TEST_USER2);
        List<Cip> cips = ccm.getCips();
        assertFalse(cips.get(0).isSeen());
        assertFalse(cips.get(1).isSeen());
        
        exec();

        cips = ccm.getCips();

        assertTrue(cips.get(0).isSeen());
        assertFalse(cips.get(1).isSeen());
    }
}
