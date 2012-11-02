/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tests.ste.cipeciop.web;

import static org.junit.Assert.*;
import        org.junit.Test;

import ste.cipeciop.Constants;
import com.funambol.tools.test.BeanShellTest;
import java.util.HashMap;
import java.util.Properties;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import ste.cipeciop.test.web.mock.HttpServletRequestMock;
import ste.cipeciop.test.web.mock.ServletContextMock;

/**
 *
 * @author ste
 */
public class GetVersionTest extends BeanShellTest implements Constants {
    
    public static final String TEST_VERSION = "1.0-TEST";
    
    public GetVersionTest() throws Exception {
        setBshFileName("src/main/webapp/WEB-INF/commands/getVersion.bsh");
    }
    
    @Override
    protected void beanshellSetup() throws Exception {
    }

    @Test
    public void getVersion() throws Throwable {
        Properties p = (Properties)exec("getVersion");
        assertNotNull(p);
        assertEquals(TEST_VERSION, p.get(KEY_VERSION));
    }
}
