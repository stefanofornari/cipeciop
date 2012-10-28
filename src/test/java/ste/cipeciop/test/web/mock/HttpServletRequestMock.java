package ste.cipeciop.test.web.mock;

import java.util.Enumeration;
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
    public Map<String, String> parameters;
    public Map<String, String> headers;
    public String pathInfo;
    public String servletPath;
    
    public HttpServletRequestMock(ServletContextMock context) {
        this.context     = context;
        this.attributes  = new HashMap();
        this.parameters  = new HashMap<String, String>();
        this.headers     = new HashMap<String, String>();
        this.session     = new SessionMock();
        this.dispatcher  = null;
        this.pathInfo    = "";
        this.servletPath = "";
    }
    
    public HttpServletRequestMock() {
        this(new ServletContextMock());
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
        return attributes.get(name);
    }

    @Override
    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }
    
    @Override
    public String getHeader(String name) {
        return headers.get(name);
    }
    
    @Override
    public Enumeration getHeaderNames() {
        //
        // TODO
        //
        return null;
    }

    public void setHeader(String name, String value) {
        headers.put(name, value);
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
    
    public void setParameter(String name, String value) {
        parameters.put(name, value);
    }
    
    @Override
    public String getParameter(String name) {
        return parameters.get(name);
    }
    
    

}
