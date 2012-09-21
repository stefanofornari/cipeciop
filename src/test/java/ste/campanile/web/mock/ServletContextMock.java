package ste.campanile.web.mock;

import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;

/**
 *
 * @author ste
 */
public class ServletContextMock extends HashMap implements ServletContext  {
    public String requestURI = "";
    
    @Override
    public Object getAttribute(String name) {
        return get(name);
    }
    
    @Override
    public void setAttribute(String name, Object value) {
        put(name, value);
    }
    
    @Override
    public RequestDispatcher getRequestDispatcher(String url) {
        return new RequestDispatcherMock(url);
    }
}
