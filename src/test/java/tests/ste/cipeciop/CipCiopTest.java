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

import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ste.cipeciop.Cip;
import static org.junit.Assert.*;
import ste.cipeciop.Ciop;
import ste.cipeciop.CipCiop;

/**
 *
 * @author ste
 */
public class CipCiopTest {
    
    public static final String TEST_NEW_CHIP_TXT  = "thi is a new cip";
    public static final String TEST_FROM1 = "user1";
    public static final String TEST_TO1 = "someone";
    
    public CipCiopTest() {
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
    public void newCip() {
        Cip c = new Cip();
        newCipCiop(c);
        assertFalse(c.isSeen());
    }
    
    @Test
    public void newCiop() {
        newCipCiop(new Ciop());
    }
        
    @Test
    public void text() {
        text(new Cip());
        text(new Ciop());
    }
    
    @Test
    public void from() {
        from(new Cip());
        from(new Ciop());
    }
    
    @Test
    public void comparisons() {
        comparisons(new Cip(), new Cip());
        comparisons(new Ciop(), new Ciop());
    }
    
    @Test
    //
    // It cannot be called toString as it is altready a method of the class
    //
    public void toText() {
        toText(new Cip());
        toText(new Ciop());
    }
    
    @Test
    public void sentFromMobile() {
        sentFromMobile(new Cip());
        sentFromMobile(new Ciop());
    }
    
    @Test
    public void seen() {
        Cip c = new Cip();
        assertFalse(c.isSeen());
        
        c.setSeen(new Date()); assertTrue(c.isSeen());
    }
        
    // --------------------------------------------------------- Private methods

    private void newCipCiop(CipCiop c) {
        assertEquals(0, c.getId());
        assertEquals("", c.getText());
        assertFalse(c.isFromMobile());
        
        c = new Cip(TEST_NEW_CHIP_TXT);
        assertEquals(TEST_NEW_CHIP_TXT, c.getText());
    }

    private void text(CipCiop cip) throws NullPointerException {
        cip.setText(TEST_NEW_CHIP_TXT);
        assertEquals(TEST_NEW_CHIP_TXT, cip.getText());
        
        try {
            cip.setText(null);
            fail("text cannot be null");
        } catch (NullPointerException e) {
            //
            // This is ok!
            //
        }
    }

    private void from(CipCiop cip) {
        assertNull(cip.getFrom());
        
        cip.setFrom(TEST_FROM1); assertEquals(TEST_FROM1, cip.getFrom());
    }

    private void comparisons(CipCiop cip1, CipCiop cip2) {
        cip1.setCreated(new Date()); cip2.setCreated(new Date(cip1.getCreated().getTime()+10));
        
        try {
            cip1.compareTo(null);
            fail("the object cannot be null");
        } catch (NullPointerException e) {
            //
            // OK
            //
        }
        
        try {
            cip1.compareTo(new String());
            fail("the object cannot be null");
        } catch (IllegalArgumentException e) {
            //
            // OK
            //
        }
        assertTrue(cip1.compareTo(cip2)<0);
        assertTrue(cip2.compareTo(cip1)>0);
        assertTrue(cip1.compareTo(cip1)==0);
    }

    private void toText(CipCiop cip) {
        assertTrue(cip.toString().contains("From null"));
        assertTrue(cip.toString().contains("to null"));

        cip = new Cip(TEST_NEW_CHIP_TXT);
        assertTrue(cip.toString().contains(TEST_NEW_CHIP_TXT));
        assertTrue(cip.toString().contains("From null"));
        assertTrue(cip.toString().contains("to null"));

        cip.setTo(TEST_TO1);
        assertTrue(cip.toString().contains(TEST_NEW_CHIP_TXT));
        assertTrue(cip.toString().contains("From null"));
        assertFalse(cip.toString().contains("to null"));

        cip.setFrom(TEST_FROM1);
        assertTrue(cip.toString().contains(TEST_NEW_CHIP_TXT));
        assertTrue(cip.toString().contains(TEST_FROM1));
        assertTrue(cip.toString().contains(TEST_TO1));

        Date now = new Date();
        cip.setCreated(now);
        assertTrue(cip.toString().contains(String.valueOf(now)));
    }

    private void sentFromMobile(CipCiop c) {
        assertFalse(c.isFromMobile());
        
        c.setMobile(Boolean.TRUE); assertTrue(c.isFromMobile());
    }
}
