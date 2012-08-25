package ste.campanile.web.mock;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;


/**
 *
 * @author ste
 */
public class HttpServletRequestMock implements HttpServletRequest {
    
    public ServletContextMock context;
    
    public Object attribyeValue;
    
    public HttpServletRequestMock(ServletContextMock context) {
        this.context = context;
        attribyeValue = null;
    }
    
    @Override
    public String getRequestURI() {
        return context.requestURI;
    }

    @Override
    public ServletContext getServletContext() {
        return context;
    }

    @Override
    public Object getAttribute(String name) {
        return attribyeValue;
    }

    @Override
    public void setAttribute(String name, Object value) {
        attribyeValue = value;
    }

}
