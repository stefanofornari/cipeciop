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
import ste.cipeciop.Ciop;

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
        CipCiopTestUtil.deleteAllCipCiop();
    }
    
    @After
    public void tearDown() {
    }
    
    @Test 
    public void createCCM() {
        CipCiopManager ccm = new CipCiopManager(CipCiopTestUtil.TEST_USER1);
        
        assertEquals(CipCiopTestUtil.TEST_USER1, ccm.getUserId());
        assertEquals(0, ccm.getCips().size());
    }
    
    @Test
    public void addCips() {
        CipCiopManager ccm = new CipCiopManager(CipCiopTestUtil.TEST_USER1);
        
        assertEquals(0, ccm.getCips().size());
        
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
        
        Cip cip1 = new Cip(CipCiopTestUtil.TEST_TEXT1), 
            cip2 = new Cip(CipCiopTestUtil.TEST_TEXT2);
        cip1.setFrom(CipCiopTestUtil.TEST_USER1); 
        cip1.setTo(CipCiopTestUtil.TEST_USER2);
        cip2.setFrom(CipCiopTestUtil.TEST_USER1); cip2.setTo(CipCiopTestUtil.TEST_USER3);
        cip2.setCreated(10);
        
        ccm.addCip(cip1); 
        List<Cip> cips = ccm.getCips();
        assertEquals(1, cips.size());
        assertEquals(cip1, cips.get(0));
        assertEquals(0, cip1.getCreated());
        
        ccm.addCip(cip2);
        cips = ccm.getCips();
        assertEquals(2, cips.size());
        assertEquals(cip2, cips.get(1));
        assertEquals(10, cip2.getCreated());
    }
    
    @Test
    public void addCiops() throws Exception {
        CipCiopManager ccm = new CipCiopManager(CipCiopTestUtil.TEST_USER1);
        
        assertEquals(0, ccm.getCiops().size());
        
        //
        // Not null
        //
        try {
            ccm.addCiop(null);
            fail("ciop cannot be null");
        } catch (NullPointerException e) {
            //
            // OK
            //
        }
        
        Ciop ciop1 = new Ciop(CipCiopTestUtil.TEST_TEXT1), 
             ciop2 = new Ciop(CipCiopTestUtil.TEST_TEXT2),
             ciop3 = new Ciop(CipCiopTestUtil.TEST_TEXT2);
        ciop1.setFrom(CipCiopTestUtil.TEST_USER1); ciop1.setCreated(System.currentTimeMillis());
        Thread.sleep(1);
        ciop2.setFrom(CipCiopTestUtil.TEST_USER2); ciop2.setCreated(System.currentTimeMillis());
        Thread.sleep(1);
        ciop3.setFrom(CipCiopTestUtil.TEST_USER3); ciop3.setCreated(System.currentTimeMillis());
                
        ccm.addCiop(ciop1);
        List<Ciop> ciops = ccm.getCiops();
        assertEquals(1, ciops.size());
        assertEquals(CipCiopTestUtil.TEST_USER1, ciops.get(0).getFrom());
        
        ccm.addCiop(ciop2); ccm.addCiop(ciop3);
        ciops = ccm.getCiops();
        assertEquals(3, ciops.size());
        assertEquals(CipCiopTestUtil.TEST_USER2, ciops.get(1).getFrom());
        assertEquals(CipCiopTestUtil.TEST_USER3, ciops.get(2).getFrom());
        
        assertTrue(ciops.get(0).getCreated() < ciops.get(1).getCreated());
        assertTrue(ciops.get(1).getCreated() < ciops.get(2).getCreated());
    }
    
    @Test
    public void addChipWithDifferentFrom() {
        CipCiopManager ccm = new CipCiopManager(CipCiopTestUtil.TEST_USER2);
        
        //
        // From shall be always the same as the user set in the CCM
        //
        Cip cip1 = new Cip(CipCiopTestUtil.TEST_TEXT1),
            cip2 = new Cip(CipCiopTestUtil.TEST_TEXT2);
        cip1.setFrom(CipCiopTestUtil.TEST_USER2); cip1.setTo(CipCiopTestUtil.TEST_USER1);
        cip2.setFrom(CipCiopTestUtil.TEST_USER3); cip2.setTo(CipCiopTestUtil.TEST_USER1);
        ccm.addCip(cip1); ccm.addCip(cip2);
        
        List<Cip> cips = ccm.getCips();
        assertEquals(CipCiopTestUtil.TEST_USER2, cips.get(0).getFrom());
        assertEquals(CipCiopTestUtil.TEST_USER2, cips.get(1).getFrom());
    }
    
    @Test
    public void cipsForUser() {
        CipCiopManager ccm = CipCiopTestUtil.createCCMForUser1();
                
        List<Cip> cips = ccm.getCips();
        assertEquals(3, cips.size());
        assertEquals(CipCiopTestUtil.TEST_USER2, cips.get(0).getTo());
        assertEquals(CipCiopTestUtil.TEST_USER3, cips.get(1).getTo());
        assertEquals(CipCiopTestUtil.TEST_USER3, cips.get(2).getTo());
        
        ccm = CipCiopTestUtil.createCCMForUser2();
        
        cips = ccm.getCips();
        assertEquals(2, cips.size());
        assertEquals(CipCiopTestUtil.TEST_USER1, cips.get(0).getTo());
        assertEquals(CipCiopTestUtil.TEST_USER3, cips.get(1).getTo());
    }
    
    @Test
    public void ciopsForUser() {
        CipCiopManager ccm = CipCiopTestUtil.createCCMForUser1();
                
        List<Ciop> ciops = ccm.getCiops();
        assertEquals(1, ciops.size());
        assertEquals(CipCiopTestUtil.TEST_USER1, ciops.get(0).getTo());
    }
    
    @Test
    public void deleteCip() throws Exception {
        CipCiopManager ccm = new CipCiopManager(CipCiopTestUtil.TEST_USER1);
        
        Cip cip1 = new Cip(CipCiopTestUtil.TEST_TEXT1), 
            cip2 = new Cip(CipCiopTestUtil.TEST_TEXT2);
        cip1.setFrom(CipCiopTestUtil.TEST_USER1); cip1.setTo(CipCiopTestUtil.TEST_USER2);
        cip2.setFrom(CipCiopTestUtil.TEST_USER2); cip2.setTo(CipCiopTestUtil.TEST_USER1);
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
