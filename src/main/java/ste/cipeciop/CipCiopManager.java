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
package ste.cipeciop;

import java.util.HashMap;
import java.util.List;
import org.apache.cayenne.DataObjectUtils;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.ObjectId;
import org.apache.cayenne.access.DataContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.query.ObjectIdQuery;
import org.apache.cayenne.query.SelectQuery;

/**
 * This class manage a collection of cips and ciops
 * 
 * @author ste
 */
public class CipCiopManager {
    
    private ObjectContext context;
    
    /**
     * Creates a new CipCiopManager
     */
    public CipCiopManager() {
        context = DataContext.createDataContext();
    }
    
    /**
     * Adds a new cip to the cips collection
     * 
     * @param cip the cip to add - NOT NULL
     * 
     * @throws NullPointerException if cip is null
     */
    public void addCip(final Cip cip) {
        if (cip == null) {
            throw new NullPointerException("cip cannot be null");
        }
        Cip cipDAO = context.newObject(Cip.class);
        cipDAO.setFrom(cip.getFrom());
        cipDAO.setText(cip.getText());
        cipDAO.setTo(cip.getTo());
        
        context.commitChanges();
    }
    
    /**
     * Returns the collection of cips
     * 
     * @return the collection of chips
     */
    public List<Cip> getCips() {
        SelectQuery query = new SelectQuery(Cip.class);
        return context.performQuery(query);
    }
    
    /**
     * Returns the collection of cips for the given user
     * 
     * @param to the recipient user id - not null
     * 
     * @return the collection of chips
     */
    public List<Cip> getCips(String user) {
        if (user == null) {
            throw new NullPointerException("from cannot be null");
        }
        Expression where = Expression.fromString("to=$to or from=$from");
        HashMap<String,String> params = new HashMap<String, String>();
        params.put("to", user);
        params.put("from", user);
        
        SelectQuery proto = new SelectQuery(Cip.class, where);
        
        return context.performQuery(proto.queryWithParameters(params));
    }
    
    /**
     * Deletes the Cip with the given id. It returns <code>true</code> if the
     * cip existed, <code>false</code> otherwise.
     * 
     * @param id cip id
     * 
     * @return  <code>true</code> if the cip existed, <code>false</code> otherwise
     */
    public boolean deleteCip(Integer id) {
        ObjectIdQuery q = new ObjectIdQuery(
                              new ObjectId("Cip", Cip.ID_PK_COLUMN, id)
                          );
        Cip c = (Cip)DataObjectUtils.objectForQuery(context, q);
        
        if (c != null) {
            context.deleteObject(c);
            context.commitChanges();
            return true;
        }
        
        return false;
    }
}
