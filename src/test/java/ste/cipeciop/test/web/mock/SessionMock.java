package ste.cipeciop.test.web.mock;

import java.util.HashMap;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ste
 */
public class SessionMock extends HashMap implements HttpSession  {
    public String requestURI = "";
    
    @Override
    public Object getAttribute(String name) {
        return get(name);
    }
    
    @Override
    public void setAttribute(String name, Object value) {
        put(name, value);
    }
}
