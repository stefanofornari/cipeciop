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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import ste.cipeciop.Constants;

/**
 *
 * @author ste
 */
public class Utils implements Constants {

    public static final String REGEX_URL = 
        "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

    // ---------------------------------------------------------- Public methods
    /**
     * Translates the given text into html applying he following
     * transformations: <ul> <li>emoticons codes into images <li>url into
     * hyperlinks <li>new lines into line breaks </ul>
     *
     * The list of emoticons is as follows:
     * <pre>
     *  see <a href="http://messenger.yahoo.com/features/emoticons/">http://messenger.yahoo.com/features/emoticons/</a>
     *  and <a href="http://messenger.yahoo.com/features/hiddenemoticons/">http://messenger.yahoo.com/features/hiddenemoticons/</a>
     *
     *  Exceptions:
     *  :'( -> cry
     *  :d -> like
     * </pre>
     *
     * @param s the string to translate; it is supposed the string is html encoded
     *
     * @return the translated string
     */
    public static String htmlizeEmoticons(final String s) {
        final Map<String, String> emoticons = new HashMap<String, String>();

        emoticons.put(StringEscapeUtils.escapeHtml("o)")   , "angel"      );
        emoticons.put(StringEscapeUtils.escapeHtml("o:-)") , "angel"      );
        emoticons.put(StringEscapeUtils.escapeHtml("X(")   , "angry"      );
        emoticons.put(StringEscapeUtils.escapeHtml("=D>")  , "applause"   );
        emoticons.put(StringEscapeUtils.escapeHtml(":bz")  , "bee"        );
        emoticons.put(StringEscapeUtils.escapeHtml("=((")  , "brokenheart");
        emoticons.put(StringEscapeUtils.escapeHtml("~:>")  , "chicken"    );
        emoticons.put(StringEscapeUtils.escapeHtml("~O)")  , "coffee"     );
        emoticons.put(StringEscapeUtils.escapeHtml(":-/")  , "confused"   );
        emoticons.put(StringEscapeUtils.escapeHtml(":3")   , "colonthree" );
        emoticons.put(StringEscapeUtils.escapeHtml(":'(")  , "cry"        );
        emoticons.put(StringEscapeUtils.escapeHtml("3:)")  , "devil"      );
        emoticons.put(StringEscapeUtils.escapeHtml("\\:D/"), "dance"      );
        emoticons.put(StringEscapeUtils.escapeHtml(":-q")  , "dontlike"   );
        emoticons.put(StringEscapeUtils.escapeHtml(":q")   , "dontlike"   );
        emoticons.put(StringEscapeUtils.escapeHtml(":(")   , "sad"        );
        emoticons.put(StringEscapeUtils.escapeHtml(":D")   , "grin"       );
        emoticons.put(StringEscapeUtils.escapeHtml("8)")   , "glasses"    );
        emoticons.put(StringEscapeUtils.escapeHtml(">:(")  , "grumpy"     );
        emoticons.put(StringEscapeUtils.escapeHtml(">:D<") , "hug"        );
        emoticons.put(StringEscapeUtils.escapeHtml(":))")  , "laugh"      );
        emoticons.put(StringEscapeUtils.escapeHtml(":-bd") , "like"       );
        emoticons.put(StringEscapeUtils.escapeHtml(":bd")  , "like"       );
        emoticons.put(StringEscapeUtils.escapeHtml(":-x")  , "love"       );
        emoticons.put(StringEscapeUtils.escapeHtml(":x")   , "love"       );
        emoticons.put(StringEscapeUtils.escapeHtml("^_^")  , "kiki"       );
        emoticons.put(StringEscapeUtils.escapeHtml(":-*")  , "kiss"       );
        emoticons.put(StringEscapeUtils.escapeHtml(":*")   , "kiss"       );
        emoticons.put(StringEscapeUtils.escapeHtml(":v")   , "pacman"     );
        emoticons.put(StringEscapeUtils.escapeHtml(":@)")  , "pig"        );
        emoticons.put(StringEscapeUtils.escapeHtml("=))")  , "rollonfloor");
        emoticons.put(StringEscapeUtils.escapeHtml("[-X")  , "shame"      );
        emoticons.put(StringEscapeUtils.escapeHtml(":)")   , "smile"      );
        emoticons.put(StringEscapeUtils.escapeHtml("-_-")  , "squint"     );
        emoticons.put(StringEscapeUtils.escapeHtml(":|")   , "straight"   );
        emoticons.put(StringEscapeUtils.escapeHtml("8|")   , "sunglasses" );
        emoticons.put(StringEscapeUtils.escapeHtml(":-O")  , "surprise"   );
        emoticons.put(StringEscapeUtils.escapeHtml(":O")   , "surprise"   );
        emoticons.put(StringEscapeUtils.escapeHtml(":-?")  , "think"      );
        emoticons.put(StringEscapeUtils.escapeHtml(":p")   , "thoungue"   );
        emoticons.put(StringEscapeUtils.escapeHtml(">:O")  , "upset"      );
        emoticons.put(StringEscapeUtils.escapeHtml(":-h")  , "wave"       );
        emoticons.put(StringEscapeUtils.escapeHtml(";)")   , "wink"       );

        ArrayList<String> patterns = new ArrayList<String>();
        for (String k : emoticons.keySet()) {
            patterns.add(Pattern.quote(k));
        }

        Matcher matcher = Pattern.compile(
                StringUtils.join(patterns, "|")).matcher(s);

        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb,
                String.format("<img src=\"images/emoticons/%s.gif\" border='0' />", emoticons.get(matcher.group()))
            );
        }
        matcher.appendTail(sb);

        return sb.toString();
    }

    /**
     * Translates new lines characters into html line break (&lt;br&gt;).
     *
     * @param s the string to translate
     *
     * @return the translated string
     */
    public static String htmlizeNewLines(final String s) {
        return s.replaceAll(Pattern.quote("\r\n"), "<br>");
    }

    /**
     * Turns urls into html &lt;a href="..."&gt;
     *
     * @param s the string to translate
     * @return the translated string
     */
    public static String htmlizeUrls(final String s) {
        Matcher matcher = Pattern.compile(REGEX_URL).matcher(s);
        
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String url = StringEscapeUtils.unescapeHtml(matcher.group());
            matcher.appendReplacement(sb,
                String.format("<a href=\"%s\" target=\"_blank\">%s</a>",url, url)
            );
        }
        matcher.appendTail(sb);
        
        return sb.toString();
    }
    
    public static String htmlizeEscape(final String s) {
        return StringEscapeUtils.escapeHtml(s);
    }

    public static String htmlize(final String s) {
        return htmlizeNewLines(
                   htmlizeUrls(
                       htmlizeEmoticons(
                           htmlizeEscape(s)
                       )
                   )
               );
    }
    
    public static Map<String, Object> convertToMap(Object obj) throws
            IllegalAccessException,
            IllegalArgumentException,
            InvocationTargetException {
        Class<?> pomclass = obj.getClass();
        pomclass = obj.getClass();
        Method[] methods = obj.getClass().getMethods();

        String propertyName = null;
        Map<String, Object> map = new HashMap<String, Object>();
        for (Method m : methods) {
            propertyName = m.getName();
            if ((propertyName.length() > 3) && propertyName.startsWith("get") && !propertyName.startsWith("getClass")) {
                Object value = (Object) m.invoke(obj);
                propertyName = propertyName.substring(3).toLowerCase();
                map.put(propertyName, (Object) value);
            }
        }
        
        return map;
    }
    
    public static final String userToString(Map<String,String> u) {
        return '<' + u.get(KEY_USER_NAME) + '>' + u.get(KEY_USER_ID);
    }
}
