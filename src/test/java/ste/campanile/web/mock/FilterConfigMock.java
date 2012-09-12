package ste.campanile.web.mock;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;


/**
 *
 * @author ste
 */
public class FilterConfigMock implements FilterConfig {
    
    public ServletContextMock context;
    
    public FilterConfigMock(ServletContextMock context) {
        this.context = context;
    }

    @Override
    public String getFilterName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ServletContext getServletContext() {
        return context;
    }

    @Override
    public String getInitParameter(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Enumeration<String> getInitParameterNames() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
