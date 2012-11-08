/*
 * Cip&Ciop
 * Copyright (C) 2012 Stefano Fornari
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License version 3 as published by
 * the Free Software Foundation with the addition of the following permission
 * added to Section 15 as permitted in Section 7(a): FOR ANY PART OF THE COVERED
 * WORK IN WHICH THE COPYRIGHT IS OWNED BY Stefano Fornari, Stefano Fornari
 * DISCLAIMS THE WARRANTY OF NON INFRINGEMENT OF THIRD PARTY RIGHTS.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, see http://www.gnu.org/licenses or write to
 * the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301 USA.
 */
package tests.ste.cipeciop.ajax;

import static org.junit.Assert.*;
import        org.junit.Test;

import ste.cipeciop.Constants;
import com.funambol.tools.test.BeanShellTest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import ste.cipeciop.Ciop;
import ste.cipeciop.Cip;
import ste.cipeciop.CipCiopManager;
import ste.cipeciop.test.web.mock.HttpServletRequestMock;
import ste.cipeciop.test.web.mock.ServletContextMock;
import tests.ste.cipeciop.CipCiopTestUtil;

/**
 *
 * @author ste
 */
public class PublicControllerTest extends BeanShellTest implements Constants {
    
    public static final String TEST_NO  = "no" ;
    public static final String TEST_YES = "yes";
    
    public PublicControllerTest() throws Exception {
        setCommandsDirectory("src/main/webapp/WEB-INF/commands");
        setBshFileName("src/main/webapp/ajax/controllers/public.bsh");
    }
    
    @Override
    protected void beanshellSetup() throws Exception {
        ServletContextMock context = new ServletContextMock();
        HttpServletRequestMock r = new HttpServletRequestMock(context);
        
        HttpSession s = r.getSession();
        Map attributes = new HashMap();
        
        beanshell.set("request", r);
        beanshell.set("session", r.getSession());
    }

    @Test
    public void checkNoUserid() throws Exception {
        //
        // No action given, no result available
        //
        assertNull(beanshell.eval("request.getAttribute(\"result\");"));
        
        //
        // No userid given, answer NO
        //
        beanshell.set(AJAX_ACTION, AJAX_ACTION_CHECK);
        
        exec();
        assertEquals(TEST_NO, beanshell.eval("request.getAttribute(\"result\");"));
    }
    
    @Test
    public void checkYesAndNo() throws Exception {
        CipCiopManager ccm = new CipCiopManager(CipCiopTestUtil.TEST_USER1);
        ccm.setLastChange(null); ccm.setLastVisit(null);
        
        beanshell.set(AJAX_PARAM_ID, CipCiopTestUtil.TEST_USER1);
        beanshell.set(AJAX_ACTION, AJAX_ACTION_CHECK);
        
        exec();
        assertEquals(TEST_NO, beanshell.eval("request.getAttribute(\"result\");"));
        
        Date ts = new Date();
        ccm.setLastChange(ts);
        
        exec();
        assertEquals(TEST_YES, beanshell.eval("request.getAttribute(\"result\");"));
        
        ccm.setLastVisit(new Date(ts.getTime()-1000));
        exec();
        assertEquals(TEST_YES, beanshell.eval("request.getAttribute(\"result\");"));
        
        ccm.setLastVisit(new Date(ts.getTime()+1000));
        exec();
        assertEquals(TEST_NO, beanshell.eval("request.getAttribute(\"result\");"));
    }
}
