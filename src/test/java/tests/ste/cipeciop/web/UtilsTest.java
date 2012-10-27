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


import ste.cipeciop.test.web.mock.ServletConfigMock;
import java.lang.reflect.Field;
import ste.cipeciop.test.web.mock.ServletContextMock;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ste.cipeciop.test.web.mock.HttpServletRequestMock;
import ste.cipeciop.test.web.mock.HttpServletResponseMock;
import static org.junit.Assert.*;
import ste.cipeciop.Constants;
import ste.cipeciop.web.OAuthServlet;

/**
 *
 * @author ste
 */
public class OAuthServletTest implements Constants {
    
    private ServletContextMock servletContext;
    private OAuthServlet servlet;
    
    public OAuthServletTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
        servlet = new OAuthServlet();
        servletContext = new ServletContextMock();
        servletContext.requestURI = "/test";
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testInit() throws Exception {
        ServletConfigMock config = new ServletConfigMock(servletContext);
        assertNull(getPrivateField(servlet, "consumerManager"));
        
        servlet.init(config);
        
        assertNotNull(getPrivateField(servlet, "consumerManager"));
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
