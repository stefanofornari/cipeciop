package javax.servlet.http;

import javax.servlet.ServletRequest;


/**
 *
 * @author ste
 */
public interface HttpServletRequest extends ServletRequest {
    public String getRequestURI();
    public String getPathInfo();
    public String getServletPath();
    public HttpSession getSession();
    
}
