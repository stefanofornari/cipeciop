/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tests.ste.cipeciop.web;

import static org.junit.Assert.*;
import        org.junit.Test;

import ste.cipeciop.Constants;
import com.funambol.tools.test.BeanShellTest;
import java.util.HashMap;
import java.util.Properties;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import ste.cipeciop.test.web.mock.HttpServletRequestMock;
import ste.cipeciop.test.web.mock.ServletContextMock;
import ste.cipeciop.web.Utils;

/**
 *
 * @author ste
 */
public class GetFriendsTest extends BeanShellTest implements Constants {
    
    Properties one, two, three;
    
    public GetFriendsTest() throws Exception {
        setCommandsDirectory("src/main/webapp/WEB-INF/commands");
        setBshFileName("src/main/webapp/WEB-INF/commands/getFriends.bsh");
    }
    
    @Override
    protected void beanshellSetup() throws Exception {
        ServletContextMock context = new ServletContextMock();
        HttpServletRequestMock r = new HttpServletRequestMock(context);
        
        HttpSession s = r.getSession();
        beanshell.set("session", s);
        
        one = new Properties(); 
        one.load(this.getClass().getResourceAsStream("/user1@yahoo.com.properties"));
        
        two = new Properties(); 
        two.load(this.getClass().getResourceAsStream("/user2@yahoo.com.properties"));
        
        three = new Properties(); 
        three.load(this.getClass().getResourceAsStream("/user3@yahoo.com.properties"));
    }

    @Test
    public void getFriendsForOne() throws Throwable {
        HttpSession s = (HttpSession)beanshell.get("session");
        
        HashMap id = new HashMap();
        id.put("userid", "one@yahoo.com");
        s.setAttribute(ATTRIBUTE_IDENTIFIER, id);
        
        //
        // get the friends the first time (i.e. from there persisted list)
        //
        HashMap<String,String>[] friends = (HashMap[])exec("getFriends");
        assertNotNull(friends);
        assertEquals(2, friends.length);
        assertEquals(one.get("friends"), join(friends));
        
        id = new HashMap();
        id.put("userid", "user2@yahoo.com");
        s.setAttribute(ATTRIBUTE_IDENTIFIER, id);
        
        assertNotNull(((HttpSession)beanshell.get("session")).getAttribute(ATTRIBUTE_FRIENDS));

    }

    @Test
    public void getFriendsForTwo() throws Throwable {
        HttpSession s = (HttpSession)beanshell.get("session");
        
        HashMap id = new HashMap();
        id.put("userid", "user2@yahoo.com");
        s.setAttribute(ATTRIBUTE_IDENTIFIER, id);

        HashMap<String,String>[] friends = (HashMap[])exec("getFriends");
        assertNotNull(friends);
        assertEquals(1, friends.length);
        assertEquals(two.get("friends"), join(friends));
    }
    
    
    @Test
    public void getNoFriendList() throws Throwable {
        HttpSession s = (HttpSession)beanshell.get("session");
        
        HashMap id = new HashMap();
        id.put("userid", "none@yahoo.com");
        s.setAttribute(ATTRIBUTE_IDENTIFIER, id);

        HashMap<String,String>[] friends = (HashMap[])exec("getFriends");
        assertNotNull(friends);
        assertEquals(0, friends.length);
    }

    private String join(final HashMap<String, String>[] friends) {
        String[] friendnames = new String[friends.length];
        int i = 0;
        for (HashMap<String, String> f: friends) {
            friendnames[i++] = Utils.userToString(f);
        }
        
        return StringUtils.join(friendnames, ',');
        
    }

}
