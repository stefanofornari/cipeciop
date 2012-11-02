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
package tests.ste.cipeciop.web;

import static org.junit.Assert.*;
import        org.junit.Test;

import ste.cipeciop.Constants;
import com.funambol.tools.test.BeanShellTest;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.servlet.http.HttpSession;
import ste.cipeciop.test.web.mock.HttpServletRequestMock;
import ste.cipeciop.test.web.mock.ServletContextMock;
import tests.ste.cipeciop.CipCiopTestUtil;

/**
 *
 * @author ste
 */
public class VersionTest extends BeanShellTest 
                               implements Constants {
    
    public VersionTest() throws Exception {
        setCommandsDirectory("src/main/webapp/WEB-INF/commands");
        setBshFileName("src/main/webapp/controllers/version.bsh");
    }
    
    @Override
    protected void beanshellSetup() throws Exception {
        CipCiopTestUtil.deleteAllCipCiop();
        
        HttpServletRequestMock r = new HttpServletRequestMock(new ServletContextMock());
        
        HttpSession s = r.getSession();
        Map attributes = new HashMap();
        attributes.put(ALIAS_USER_ID, CipCiopTestUtil.TEST_USER1);
        s.setAttribute(ATTRIBUTE_IDENTIFIER, attributes);
        
        beanshell.set("request", r);
        beanshell.set("session", r.getSession());
    }

    @Test
    public void cipciopVersion() throws Exception {
        assertNotNull(((HttpSession)beanshell.get("session"))
                       .getAttribute(ATTRIBUTE_VERSION));
    }
}
