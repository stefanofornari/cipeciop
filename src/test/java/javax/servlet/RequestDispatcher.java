package javax.servlet;

/**
 *
 * @author ste
 */
public interface RequestDispatcher {
    public void forward(ServletRequest request, ServletResponse response);
}
