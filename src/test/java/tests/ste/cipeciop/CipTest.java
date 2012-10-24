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

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ste.cipeciop.Cip;
import static org.junit.Assert.*;

/**
 *
 * @author ste
 */
public class CipTest {
    
    public static final String TEST_NEW_CHIP_TXT  = "thi is a new cip";
    public static final String TEST_FROM1 = "stefano_fornari";
    public static final String TEST_TO1 = "someone";
    
    public CipTest() {
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
        Cip cip = new Cip();
        
        //assertFalse(cip.getId() == 0);
        assertEquals("", cip.getText());
        
        cip = new Cip(TEST_NEW_CHIP_TXT);
        assertEquals(TEST_NEW_CHIP_TXT, cip.getText());
    }
    
    @Test
    public void cipText() {
        Cip cip = new Cip();
        
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
    
    @Test
    public void cipFrom() {
        Cip cip = new Cip();
        
        assertNull(cip.getFrom());
        
        cip.setFrom(TEST_FROM1); assertEquals(TEST_FROM1, cip.getFrom());
    }
    
    @Test
    public void comparisons() {
        Cip cip1 = new Cip(), cip2 = new Cip();
        cip1.setCreated(10); cip2.setCreated(20);
        
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
    
    @Test
    //
    // It cannot be called toString as it is altready a method of the class
    //
    public void toText() {
        Cip cip = new Cip();

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

        cip.setCreated(111222);
        assertTrue(cip.toString().contains(String.valueOf(111222)));
    }
}
