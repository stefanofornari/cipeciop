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
        
        Cip cip1 = new Cip(), cip2 = new Cip();
        
        assertEquals(0, ccm.getCips().size());
        
        ccm.addCip(cip1); 
        assertEquals(1, ccm.getCips().size());
        assertSame(cip1, ccm.getCips().get(0));
        
        ccm.addCip(cip2);
        assertEquals(2, ccm.getCips().size());
        assertSame(cip2, ccm.getCips().get(1));
        
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
        
        Cip cip = new Cip();
        cip.setFrom(TEST_FROM1);
        cip.setText("Cip from " + TEST_FROM1);
        ccm.addCip(cip);
        
        ccm.addCip(cip = new Cip());
        cip.setFrom(TEST_FROM2);
        cip.setText("Cip from " + TEST_FROM2);
        
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
