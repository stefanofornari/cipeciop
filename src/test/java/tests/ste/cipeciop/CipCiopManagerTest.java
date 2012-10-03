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
package tests.ste.cipeciop;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.access.DataContext;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ste.cipeciop.Cip;
import ste.cipeciop.CipCiopManager;
import static org.junit.Assert.*;

/**
 *
 * @author ste
 */
public class CipCiopManagerTest {
    
    public static final String TEST_FROM1 = "stefano_fornari";
    public static final String TEST_FROM2 = "cipeciopd";
    public static final String TEST_TO1   = TEST_FROM2;
    public static final String TEST_TO2   = TEST_FROM1;
    public static final String TEST_TEXT1 = "message from " 
                                          + TEST_FROM1
                                          + " to "
                                          + TEST_TO1
                                          ;
    public static final String TEST_TEXT2 = "message from " 
                                          + TEST_FROM2
                                          + " to "
                                          + TEST_TO2
                                          ;
    
    public CipCiopManagerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
        ObjectContext context = DataContext.createDataContext();
        context.
    }
    
    @After
    public void tearDown() {
    }
    
    @Test 
    public void createCCM() {
        CipCiopManager ccm = new CipCiopManager();
        
        assertEquals(0, ccm.getCips().size());
    }
    
    @Test
    public void addCips() {
        CipCiopManager ccm = new CipCiopManager();
        
        Cip cip1 = new Cip(TEST_TEXT1), cip2 = new Cip(TEST_TEXT2);
        cip1.setFrom(TEST_FROM1); cip1.setTo(TEST_TO1);
        cip2.setFrom(TEST_FROM2); cip2.setTo(TEST_TO2);
        
        assertEquals(0, ccm.getCips().size());
        
        ccm.addCip(cip1); 
        assertEquals(1, ccm.getCips().size());
        assertEquals(cip1, ccm.getCips().get(0));
        
        ccm.addCip(cip2);
        assertEquals(2, ccm.getCips().size());
        assertEquals(cip2, ccm.getCips().get(1));
        
        //
        // Not null
        //
        try {
            ccm.addCip(null);
            fail("cip cannot be null");
        } catch (NullPointerException e) {
            //
            // OK
            //
        }
    }
    
    @Test
    public void chipsFromUser() {
        CipCiopManager ccm = new CipCiopManager();
        
        Cip cip = new Cip(TEST_TEXT1);
        cip.setFrom(TEST_FROM1); cip.setTo(TEST_TO1);
        ccm.addCip(cip);
        
        cip = new Cip(TEST_TEXT2);
        cip.setFrom(TEST_FROM2); cip.setTo(TEST_TO2);
        ccm.addCip(cip);
        
        try {
            ccm.getCips(null);
            fail("from not null must be checked");
        } catch (NullPointerException e) {
            // OK
        }
        
        List cips = ccm.getCips(TEST_FROM1);
        assertEquals(1, cips.size());
        assertTrue(cips.get(0).toString().contains(TEST_FROM1));
        
        cips = ccm.getCips(TEST_FROM2);
        assertEquals(1, cips.size());
        assertTrue(cips.get(0).toString().contains(TEST_FROM2));
    }
}
