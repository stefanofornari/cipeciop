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

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.cayenne.DataObjectUtils;
import org.apache.cayenne.DeleteDenyException;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.ObjectId;
import org.apache.cayenne.access.DataContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.query.EJBQLQuery;
import org.apache.cayenne.query.ObjectIdQuery;
import org.apache.cayenne.query.SelectQuery;

/**
 * This class manage a collection of cips and ciops
 * 
 * @author ste
 */
public class CipCiopManager {
    
    // ------------------------------------------------------------ Private data
    
    private ObjectContext context;
    private String userId;
    
    // ------------------------------------------------------------ Constructors
    
    /**
     * Creates a new CipCiopManager
     */
    protected CipCiopManager() {
        context = DataContext.createDataContext();
    }
    
    /**
     * Creates a new CipCiopManager for the given userid
     * 
     * @param userId the user id
     */
    public CipCiopManager(final String userId) {
        this();
        this.userId = userId;
    }
    
    // ---------------------------------------------------------- Public methods
    
    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
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
        
        Cip c = context.newObject(Cip.class);
        c.setFrom(userId);
        c.setText(cip.getText());
        c.setTo(cip.getTo());
        c.setCreated(cip.getCreated());
        c.setFlags(cip.getFlags());
        
        context.commitChanges();
    }
    
        /**
     * Adds a new ciop to the ciops collection
     * 
     * @param ciop the ciop to add - NOT NULL
     * 
     * @throws NullPointerException if ciop is null
     */
    public void addCiop(final Ciop ciop) {
        if (ciop == null) {
            throw new NullPointerException("ciop cannot be null");
        }
        
        Ciop c = context.newObject(Ciop.class);
        c.setTo(userId);
        c.setText(ciop.getText());
        c.setFrom(ciop.getFrom());
        c.setCreated(ciop.getCreated());
        c.setFlags(ciop.getFlags());
        
        context.commitChanges();
    }
    
    /**
     * Returns the collection of cips for the given user
     * 
     * @param to the recipient user id - not null
     * 
     * @return the collection of chips
     */
    public List<Cip> getCips() {
        Expression where = Expression.fromString("from=$from");
        HashMap<String,String> params = new HashMap<String, String>();
        params.put("from", userId);
        
        SelectQuery proto = new SelectQuery(Constants.DB_ENTITY_CIP, where);
        
        return context.performQuery(proto.queryWithParameters(params));
    }
    
        /**
     * Returns the collection of cips for the given user
     * 
     * @param to the recipient user id - not null
     * 
     * @return the collection of chips
     */
    public List<Ciop> getCiops() {
        Expression where = Expression.fromString("to=$to");
        HashMap<String,String> params = new HashMap<String, String>();
        params.put("to", userId);
        
        SelectQuery proto = new SelectQuery(Constants.DB_ENTITY_CIOP, where);
        
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
                              new ObjectId(Constants.DB_ENTITY_CIP, Cip.ID_PK_COLUMN, id)
                          );
        
        return deleteCipCiop(q);
    }
    
    /**
     * Deletes the Ciop with the given id. It returns <code>true</code> if the
     * ciop existed, <code>false</code> otherwise.
     * 
     * @param id ciop id
     * 
     * @return  <code>true</code> if the ciop existed, <code>false</code> otherwise
     */
    public boolean deleteCiop(Integer id) {
        ObjectIdQuery q = new ObjectIdQuery(
                              new ObjectId(Constants.DB_ENTITY_CIOP, Ciop.ID_PK_COLUMN, id)
                          );
        
        return deleteCipCiop(q);
    }
    
    /**
     * Set the seen flag to the cips corresponding to the current ciops and
     * whose creation timestamp is earlier than the provided ts.
     * 
     * @param timestamp the seen timestamp
     */
    public void setSeen(Date timestamp) {
        List<Ciop> ciops = getCiops();
        
        String from = null;
        Set<String> friends = new HashSet<String>();
        for(Ciop c: ciops) {
            from = c.getFrom();
            
            if (!friends.contains(from)) {
                friends.add(from);
            }
        }
        
        if (friends.size() > 0) {
            setSeen(friends, timestamp);
        }
    }

    // --------------------------------------------------------- Private methods
    
    /**
     * Deletes an object given its ObjectIdQuery.
     * 
     * @param q the ObjectIdQuery
     * 
     * @return <code>true</code> if the ciop existed, <code>false</code> otherwise
     * 
     * @throws DeleteDenyException 
     */
    private boolean deleteCipCiop(ObjectIdQuery q) throws DeleteDenyException {
        Object c = DataObjectUtils.objectForQuery(context, q);
        
        if (c != null) {
            context.deleteObject(c);
            context.commitChanges();
            return true;
        }
        
        return false;
    }
    
    private void setSeen(Set<String> friends, Date timestamp) {
        for (String from: friends) {
            EJBQLQuery query = new EJBQLQuery(
                String.format("UPDATE Cip c SET c.flags = 2 WHERE c.from='%s'", from)
            );
            context.performGenericQuery(query);
            context.commitChanges();
        }
    }


}
