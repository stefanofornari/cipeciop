/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javax.servlet;

/**
 *
 * @author ste
 */
public interface ServletContext {

    Object getAttribute(String name);
    void setAttribute(String name, Object value);
    RequestDispatcher getRequestDispatcher(String d);
    
}
