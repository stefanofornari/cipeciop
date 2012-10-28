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


import org.apache.commons.lang.StringEscapeUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import ste.cipeciop.Constants;
import ste.cipeciop.web.Utils;
import tests.ste.cipeciop.CipCiopTestUtil;

/**
 *
 * @author ste
 */
public class UtilsTest implements Constants {
    
    public final String TEST_ALL_EMO_CODES = 
        "o) :-/ :3 :'( 3:) :( :O :-O :D 8) >:( :-x :x ^_^ :* :-* :v :) -_- 8| " +
        ":p >:O ;) >:D< =(( X( :)) =)) o:-) :-h :| :-? =D> :q :-q :@) ~:> "  +
        "~O) [-X \\:D/ :bz :-bd :bd";
    public final String TEST_NEW_LINES = "This is a cip\r\nwith new line";
    public final String TEST_URL1 = "http://www.yahoo.com&param1=value1&param=value%202";
    public final String TEST_URL2 = "https://www.yahoo.com&param1=value1&param=value%202";
    public final String TEST_URL_TEXT = "this message contains " + TEST_URL1 + " and " + TEST_URL2 + " !";
    public final String TEST_URL_ESCAPE = TEST_URL_TEXT 
                                        + " <script lang=\"javascript\"> " 
                                        + TEST_ALL_EMO_CODES
                                        ;
    
    public UtilsTest() {
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
    public void noTranslations() {
        assertEquals(CipCiopTestUtil.TEST_TEXT1, Utils.htmlize(CipCiopTestUtil.TEST_TEXT1));
    }
    
    @Test
    public void htmlizeEmoticons() throws Exception {
        final String s = Utils.htmlize(TEST_ALL_EMO_CODES);
        
        assertTrue(s.contains("<img src=\"images/emoticons/angel.gif")      );
        assertTrue(s.contains("<img src=\"images/emoticons/angry.gif")      );
        assertTrue(s.contains("<img src=\"images/emoticons/bee.gif")        );
        assertTrue(s.contains("<img src=\"images/emoticons/applause.gif")   );
        assertTrue(s.contains("<img src=\"images/emoticons/brokenheart.gif"));
        assertTrue(s.contains("<img src=\"images/emoticons/chicken.gif")    );
        assertTrue(s.contains("<img src=\"images/emoticons/confused.gif")   );
        assertTrue(s.contains("<img src=\"images/emoticons/colonthree.gif") );
        assertTrue(s.contains("<img src=\"images/emoticons/cry.gif")        );
        assertTrue(s.contains("<img src=\"images/emoticons/dance.gif")      );
        assertTrue(s.contains("<img src=\"images/emoticons/devil.gif")      );
        assertTrue(s.contains("<img src=\"images/emoticons/dontlike.gif")   );
        assertTrue(s.contains("<img src=\"images/emoticons/grin.gif")       );
        assertTrue(s.contains("<img src=\"images/emoticons/glasses.gif")    );
        assertTrue(s.contains("<img src=\"images/emoticons/grumpy.gif")     );
        assertTrue(s.contains("<img src=\"images/emoticons/hug.gif")        );
        assertTrue(s.contains("<img src=\"images/emoticons/laugh.gif")      );
        assertTrue(s.contains("<img src=\"images/emoticons/like.gif")       );
        assertTrue(s.contains("<img src=\"images/emoticons/love.gif")       );
        assertTrue(s.contains("<img src=\"images/emoticons/kiki.gif")       );
        assertTrue(s.contains("<img src=\"images/emoticons/kiss.gif")       );
        assertTrue(s.contains("<img src=\"images/emoticons/pacman.gif")     );
        assertTrue(s.contains("<img src=\"images/emoticons/pig.gif")        );
        assertTrue(s.contains("<img src=\"images/emoticons/rollonfloor.gif"));
        assertTrue(s.contains("<img src=\"images/emoticons/shame.gif")      );
        assertTrue(s.contains("<img src=\"images/emoticons/smile.gif")      );
        assertTrue(s.contains("<img src=\"images/emoticons/squint.gif")     );
        assertTrue(s.contains("<img src=\"images/emoticons/sad.gif")        );
        assertTrue(s.contains("<img src=\"images/emoticons/straight.gif")   );
        assertTrue(s.contains("<img src=\"images/emoticons/sunglasses.gif") );
        assertTrue(s.contains("<img src=\"images/emoticons/surprise.gif")   );
        assertTrue(s.contains("<img src=\"images/emoticons/think.gif")      );
        assertTrue(s.contains("<img src=\"images/emoticons/thoungue.gif")   );
        assertTrue(s.contains("<img src=\"images/emoticons/upset.gif")      );
        assertTrue(s.contains("<img src=\"images/emoticons/wave.gif")       );
        assertTrue(s.contains("<img src=\"images/emoticons/wink.gif")       );
    }
    
    @Test
    public void htmlizeNewLines() {
        assertTrue(Utils.htmlize(TEST_NEW_LINES).contains("<br>"));
    }
    
    @Test
    public void htmlizeUrls() {
        String s = Utils.htmlize(TEST_URL_TEXT);
        
        assertTrue(s.contains(String.format("<a href=\"%s\"", TEST_URL1)));
        assertTrue(s.contains(String.format("<a href=\"%s\"", TEST_URL2)));
    }
    
    @Test
    public void escapeHtmlize() {
        String s = Utils.htmlize(TEST_URL_ESCAPE);
        
        assertTrue(s.contains(String.format("<a href=\"%s\"", TEST_URL1)));
        assertTrue(s.contains("<img src=\"images/emoticons/hug.gif"));
        assertTrue(s.contains("&lt;script"));
    }
}
