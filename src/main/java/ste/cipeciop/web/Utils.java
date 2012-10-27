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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author ste
 */
public class Utils {

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
     * @param s the string to translate
     *
     * @return the translated string
     */
    public static String htmlizeEmoticons(final String s) {
        final Map<String, String> emoticons = new HashMap<String, String>();

        emoticons.put("o)", "angel");
        emoticons.put("o:-)", "angel");
        emoticons.put("X(", "angry");
        emoticons.put("=D>", "applause");
        emoticons.put(":bz", "bee");
        emoticons.put("=((", "brokenheart");
        emoticons.put("~:>", "chicken");
        emoticons.put("~O)", "coffee");
        emoticons.put(":-/", "confused");
        emoticons.put(":3", "colonthree");
        emoticons.put(":'(", "cry");
        emoticons.put("3:)", "devil");
        emoticons.put("\\:D/", "dance");
        emoticons.put(":-q", "dontlike");
        emoticons.put(":q", "dontlike");
        emoticons.put(":(", "sad");
        emoticons.put(":D", "grin");
        emoticons.put("8)", "glasses");
        emoticons.put(">:(", "grumpy");
        emoticons.put(">:D<", "hug");
        emoticons.put(":))", "laugh");
        emoticons.put(":-bd", "like");
        emoticons.put(":bd", "like");
        emoticons.put(":-x", "love");
        emoticons.put(":x", "love");
        emoticons.put("^_^", "kiki");
        emoticons.put(":-*", "kiss");
        emoticons.put(":*", "kiss");
        emoticons.put(":v", "pacman");
        emoticons.put(":@)", "pig");
        emoticons.put("=))", "rollonfloor");
        emoticons.put("[-X", "shame");
        emoticons.put(":)", "smile");
        emoticons.put("-_-", "squint");
        emoticons.put(":|", "straight");
        emoticons.put("8|", "sunglasses");
        emoticons.put(":-O", "surprise");
        emoticons.put(":O", "surprise");
        emoticons.put(":-?", "think");
        emoticons.put(":p", "thoungue");
        emoticons.put(">:O", "upset");
        emoticons.put(":-h", "wave");
        emoticons.put(";)", "wink");

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
            String url = matcher.group();
            matcher.appendReplacement(sb,
                String.format("<a href=\"%s\" target=\"_blank\">%s</a>",url, url)
            );
        }
        matcher.appendTail(sb);
        
        return sb.toString();
    }

    public static String htmlize(final String s) {
        return htmlizeNewLines(
                   htmlizeUrls(
                       htmlizeEmoticons(s)
                   )
               );
    }
}
