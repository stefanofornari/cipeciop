package javax.servlet;

/**
 *
 * @author ste
 */
public interface ServletRequest {

    ServletContext getServletContext();

    public Object getAttribute(String name);

    public void setAttribute(String name, Object value);
    
    public RequestDispatcher getRequestDispatcher(String path);
}
