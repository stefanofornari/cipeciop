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
package ste.cipeciop.web;


import ste.cipeciop.Constants;
import ste.cipeciop.CipCiopManager;
import ste.campanile.web.mock.ServletContextMock;
import ste.campanile.web.mock.FilterChainMock;
import ste.campanile.web.mock.HttpServletRequestMock;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ste
 */
public class CipCiopFilterTest {
    
    public static final String TEST_AUTH_URL = "/index.bsh"; 
    
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
        
        servletContext.requestURI = "/test";
    }
    
    @After
    public void tearDown() {
    }
    
    /**
     * Test of doFilter method, of class CayenneFilter.
     */
    @Test
    public void testDoFilter() throws Exception {
        CipCiopManager ccm = 
            (CipCiopManager)servletContext.getAttribute(CipCiopFilter.ATTRIBUTE_CIPCIOP_MANAGER);
        
        assertNull(ccm);
        
        HttpServletRequestMock q = new HttpServletRequestMock(servletContext);
        
        filter.doFilter(q, null, new FilterChainMock());
        
        ccm = (CipCiopManager)servletContext.getAttribute(CipCiopFilter.ATTRIBUTE_CIPCIOP_MANAGER);
        
        assertNotNull(ccm);
    }
    
    @Test
    public void testForceLogin() throws Exception {
        HttpServletRequestMock q = new HttpServletRequestMock(servletContext);
        
        filter.doFilter(q, null, new FilterChainMock());
        
        assertEquals(TEST_AUTH_URL, q.dispatcher.forwardedPath);
    }

    /**
     * Test of destroy method, of class CayenneFilter.
     */
    @Test
    public void testDestroy() {
        System.out.println("destroy");
        CipCiopFilter instance = new CipCiopFilter();
        instance.destroy();
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }
}
