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
        one.load(this.getClass().getResourceAsStream("/one@yahoo.com.properties"));
        
        two = new Properties(); 
        two.load(this.getClass().getResourceAsStream("/two@yahoo.com.properties"));
        
        three = new Properties(); 
        three.load(this.getClass().getResourceAsStream("/three@yahoo.com.properties"));
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
        String[] friends = (String[])exec("getFriends");
        assertNotNull(friends);
        assertEquals(2, friends.length);
        assertEquals(one.get("friends"), StringUtils.join(friends, ','));
        
        id = new HashMap();
        id.put("userid", "two@yahoo.com");
        s.setAttribute(ATTRIBUTE_IDENTIFIER, id);
        
        assertNotNull(((HttpSession)beanshell.get("session")).getAttribute(ATTRIBUTE_FRIENDS));

    }
    
    @Test
    public void getFriendsForTwo() throws Throwable {
        HttpSession s = (HttpSession)beanshell.get("session");
        
        HashMap id = new HashMap();
        id.put("userid", "two@yahoo.com");
        s.setAttribute(ATTRIBUTE_IDENTIFIER, id);

        String[] friends = (String[])exec("getFriends");
        assertNotNull(friends);
        assertEquals(1, friends.length);
        assertEquals(two.get("friends"), friends[0]);
    }
    
    
    @Test
    public void getNoFriendList() throws Throwable {
        HttpSession s = (HttpSession)beanshell.get("session");
        
        HashMap id = new HashMap();
        id.put("userid", "none@yahoo.com");
        s.setAttribute(ATTRIBUTE_IDENTIFIER, id);

        String[] friends = (String[])exec("getFriends");
        assertNotNull(friends);
        assertEquals(0, friends.length);
    }

}
