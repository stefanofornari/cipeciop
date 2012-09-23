package ste.cipeciop.test.web.mock;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 *
 * @author ste
 */
public class RequestDispatcherMock implements RequestDispatcher  {
    
    public String path;
    public String forwardedPath;
    
    public RequestDispatcherMock(String url) {
        this.path = url;
        this.forwardedPath = null;
    }
    
    @Override
    public void forward(ServletRequest request, ServletResponse response) {
        forwardedPath = path;
    }

}
