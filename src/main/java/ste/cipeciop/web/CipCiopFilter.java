/*
 * Cip&Ciop
 * Copyright (C) 2012 Stefano Fornari
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License version 3 as published by
 * the Free Software Foundation with the addition of the following permission
 * added to Section 15 as permitted in Section 7(a): FOR ANY PART OF THE COVERED
 * WORK IN WHICH THE COPYRIGHT IS OWNED BY Stefano Fornari, Stefano Fornari
 * DISCLAIMS THE WARRANTY OF NON INFRINGEMENT OF THIRD PARTY RIGHTS.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, see http://www.gnu.org/licenses or write to
 * the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301 USA.
 */
package ste.cipeciop.web;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import ste.cipeciop.CipCiopManager;
import ste.cipeciop.Constants;


/**
 * This is a web filter used to make available to the bsh controllers the ORM
 * Cayenne context.
 * 
 * @author ste
 */
public final class CipCiopFilter implements Filter, Constants {
    
    private static final Logger log = Logger.getLogger("ste.cipeciop.web");

    @Override
    public void init(FilterConfig fc) throws ServletException {
        
    }

    @Override
    public void doFilter(ServletRequest  request , 
                         ServletResponse response, 
                         FilterChain     next    ) throws IOException, ServletException {
        if (log.isLoggable(Level.FINEST)) {
            log.finest("----This is the Cip&Ciop filter!!! " + ((HttpServletRequest)request).getRequestURI());
        }
        
        synchronized (this) {
            CipCiopManager ccm = (CipCiopManager)request.getServletContext().getAttribute(ATTRIBUTE_CIPCIOP_MANAGER);

            if (ccm == null) {
                System.out.println("--> creating a new manager");
                ccm = new CipCiopManager();
                request.getServletContext().setAttribute(ATTRIBUTE_CIPCIOP_MANAGER, ccm);
            } 
        }

        next.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
