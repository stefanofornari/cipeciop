package javax.servlet.http;

import javax.servlet.*;

/**
 *
 * @author ste
 */
public interface HttpSession {
    public Object getAttribute(String name);
    public void setAttribute(String name, Object value);
}
