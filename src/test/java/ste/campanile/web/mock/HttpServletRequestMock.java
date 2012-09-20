package ste.campanile.web.mock;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 *
 * @author ste
 */
public class HttpServletRequestMock implements HttpServletRequest {
    
    public ServletContextMock     context   ;
    public SessionMock            session   ;
    public RequestDispatcherMock  dispatcher;
    
    public Map attributes;
    public String pathInfo;
    public String servletPath;
    
    public HttpServletRequestMock(ServletContextMock context) {
        this.context     = context;
        this.attributes  = new HashMap();
        this.session     = new SessionMock();
        this.dispatcher  = null;
        this.pathInfo    = "";
        this.servletPath = "";
    }
    
    @Override
    public String getRequestURI() {
        return context.requestURI;
    }

    @Override
    public ServletContext getServletContext() {
        return context;
    }

    // TO DO to be removed
    @Override
    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    // TO DO to be removed
    @Override
    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }
        
    @Override
    public HttpSession getSession() {
        return session;
    }
    
    @Override
    public RequestDispatcher getRequestDispatcher(String url) {
        return (dispatcher = new RequestDispatcherMock(url));
    }
    
    @Override
    public String getPathInfo() {
        return pathInfo;
    }
    
    @Override
    public String getServletPath() {
        return servletPath;
    }

}
