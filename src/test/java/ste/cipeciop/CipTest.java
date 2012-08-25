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

package ste.cipeciop;

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
public class CipTest {
    
    public static final String TEST_NEW_CHIP_TXT  = "thi is a new cip";
    
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
        
        assertFalse(cip.getId() == 0);
        assertEquals("", cip.getText());
        
        cip = new Cip(TEST_NEW_CHIP_TXT);
        assertEquals(TEST_NEW_CHIP_TXT, cip.getText());
    }
    
    @Test
    public void testCipText() {
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
    public void testToString() {
        Cip cip = new Cip();
        
        assertEquals("", cip.toString());
        
        cip = new Cip(TEST_NEW_CHIP_TXT);
        assertEquals(TEST_NEW_CHIP_TXT, cip.toString());
    }
}
