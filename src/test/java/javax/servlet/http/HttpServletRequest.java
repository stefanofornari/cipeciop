package javax.servlet.http;

import java.util.Enumeration;
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
    public String getParameter(String name);
    public String getHeader(String name);
    public Enumeration getHeaderNames();
}
