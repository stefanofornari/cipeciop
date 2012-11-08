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


import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;
import ste.cipeciop.CipCiopManager;
import ste.cipeciop.test.web.mock.ServletContextMock;
import ste.cipeciop.test.web.mock.FilterChainMock;
import ste.cipeciop.test.web.mock.HttpServletRequestMock;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ste.cipeciop.web.CipCiopFilter;
import static org.junit.Assert.*;
import ste.cipeciop.Constants;
import tests.ste.cipeciop.CipCiopTestUtil;

/**
 *
 * @author ste
 */
public class CipCiopFilterTest {
    
    public static final String TEST_AUTH_URL = "/auth?openid=https%3A%2F%2Fme.yahoo.com"; 
    
    private CipCiopFilter filter;
    private ServletContextMock servletContext;
    
    public CipCiopFilterTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
        filter = new CipCiopFilter();
        servletContext = new ServletContextMock();
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testForceLogin() throws Exception {
        HttpServletRequestMock r = new HttpServletRequestMock(servletContext);
        
        r.servletPath = "/authorize";
        filter.doFilter(r, null, new FilterChainMock());
        assertEquals(TEST_AUTH_URL, r.dispatcher.forwardedPath);
        
        r.servletPath = "/ajax/cip.bsh";
        filter.doFilter(r, null, new FilterChainMock());
        assertEquals(TEST_AUTH_URL, r.dispatcher.forwardedPath);
    }
    
    @Test
    /**
     * The urls /auth and /free should not be redirected to the authentication
     * url
     */
    public void testPassThroughURLs() throws Exception {
        HttpServletRequestMock r = new HttpServletRequestMock(servletContext);
        
        r.servletPath = "/auth";
        filter.doFilter(r, null, new FilterChainMock());
        assertNull(r.dispatcher);
        
        r.servletPath = "/ajax/public.bsh";
        filter.doFilter(r, null, new FilterChainMock());
        assertNull(r.dispatcher);
    }
    
    // --------------------------------------------------------- Private methods
    
    private Object getPrivateField(Object o, String name) throws Exception {
        if (o == null) {
            return null;
        }
        Field f = o.getClass().getDeclaredField(name); f.setAccessible(true);
        return f.get(o);
    }
}
