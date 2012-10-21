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
        CipCiopTestUtil.deleteAllCips();
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
        
        Cip cip1 = new Cip(CipCiopTestUtil.TEST_TEXT1), 
            cip2 = new Cip(CipCiopTestUtil.TEST_TEXT2);
        cip1.setFrom(CipCiopTestUtil.TEST_FROM1); cip1.setTo(CipCiopTestUtil.TEST_TO1);
        cip2.setFrom(CipCiopTestUtil.TEST_FROM2); cip2.setTo(CipCiopTestUtil.TEST_TO2);
        
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
    public void chipsForUser() {
        CipCiopManager ccm = CipCiopTestUtil.createCCM();
        
        try {
            ccm.getCips(null);
            fail("from not null must be checked");
        } catch (NullPointerException e) {
            // OK
        }
        
        List<Cip> cips = ccm.getCips(CipCiopTestUtil.TEST_FROM1);
        assertEquals(3, cips.size());
        assertEquals(CipCiopTestUtil.TEST_TO1, cips.get(0).getTo());
        assertEquals(CipCiopTestUtil.TEST_TO2, cips.get(1).getTo());
        assertEquals(CipCiopTestUtil.TEST_TO3, cips.get(2).getTo());
        
        cips = ccm.getCips(CipCiopTestUtil.TEST_FROM3);
        assertEquals(1, cips.size());
        assertEquals(CipCiopTestUtil.TEST_FROM3, cips.get(0).getFrom());
    }
    
    @Test
    public void deleteCip() throws Exception {
        CipCiopManager ccm = new CipCiopManager();
        
        Cip cip1 = new Cip(CipCiopTestUtil.TEST_TEXT1), 
            cip2 = new Cip(CipCiopTestUtil.TEST_TEXT2);
        cip1.setFrom(CipCiopTestUtil.TEST_FROM1); cip1.setTo(CipCiopTestUtil.TEST_TO1);
        cip2.setFrom(CipCiopTestUtil.TEST_FROM2); cip2.setTo(CipCiopTestUtil.TEST_TO2);
        ccm.addCip(cip1); ccm.addCip(cip2);
        
        //
        // deleting a not existing cip results in nothing done
        //
        assertFalse(ccm.deleteCip((Integer)0));
        assertEquals(2, ccm.getCips().size());
        
        //
        // deleteing an existing cip results in the cip to be removed from the list
        //
        List<Cip>cips = ccm.getCips();
        Integer id = cips.get(1).getId();
        assertTrue(ccm.deleteCip(id));
        cips = ccm.getCips();
        assertEquals(1, cips.size());
        assertFalse(id.equals(cips.get(0).getId()));
        
    }
}
