package javax.servlet.http;

import javax.servlet.ServletConfig;


/**
 *
 * @author ste
 */
public abstract class HttpServlet {
    public void init(ServletConfig config) {};
    protected abstract void doGet(HttpServletRequest request, HttpServletResponse response);
    protected abstract void doPost(HttpServletRequest request, HttpServletResponse response);
    public abstract String getServletInfo();
}
