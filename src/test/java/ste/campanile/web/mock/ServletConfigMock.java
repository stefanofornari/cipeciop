package ste.campanile.web.mock;

import java.util.Enumeration;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;


/**
 *
 * @author ste
 */
public class ServletConfigMock implements ServletConfig {
    
    public ServletContextMock context;
    
    public ServletConfigMock(ServletContextMock context) {
        this.context = context;
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

    @Override
    public String getServletName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
