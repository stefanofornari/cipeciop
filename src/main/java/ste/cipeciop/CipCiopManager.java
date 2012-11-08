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
import ste.cipeciop.dao.Status;

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
        c.setMobile(cip.getMobile());
        c.setSeen(cip.getSeen());
        
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
        c.setMobile(ciop.getMobile());
        
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
    public void setSeen(final Date timestamp) {
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
    
    public void setLastVisit(final Date timestamp) {
        getStatus(true).setLastVisit(timestamp);
        
        context.commitChanges();
    }
    
    public Date getLastVisit() {
        Status s = getStatus();
        
        return (s != null) ? s.getLastVisit() : null;
    }
    
    public void setLastChange(final Date timestamp) {
        getStatus(true).setLastChange(timestamp);
        
        context.commitChanges();
    }
    
    public Date getLastChange() {
        Status s = getStatus();
        
        return (s != null) ? s.getLastChange() : null;
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
    
    /**
     * Sets the Seen flag for all the given friends to a given timestamp
     * 
     * @param friends the list of friends whose cips must be set as seen
     * @param timestamp the seen timestamp
     */
    private void setSeen(Set<String> friends, Date timestamp) {
        for (String to: friends) {
            EJBQLQuery query = new EJBQLQuery(
                "UPDATE Cip c SET c.seen=:s WHERE c.from=:f AND c.to=:t AND c.created<:c"
            );

            query.setParameter("s", new java.sql.Timestamp(timestamp.getTime()));
            query.setParameter("f", to       );
            query.setParameter("t", userId   );
            query.setParameter("c", new java.sql.Timestamp(timestamp.getTime()));
            
            context.performGenericQuery(query);
            context.commitChanges();
        }
    }

    /**
     * Returns the Status object of the user associated to this' userId.
     * 
     * @return the corresponding Status object if found, null otherwise
     */
    private Status getStatus() {
        return getStatus(false);
    }
    
    /**
     * Returns the Status object of the user associated to this' userId. A new 
     * Status object is created if not existing.
     * 
     * @return the corresponding Status object if found, null otherwise
     */
    private Status getStatus(boolean create) {
        ObjectIdQuery q = new ObjectIdQuery(
                              new ObjectId(Constants.DB_ENTITY_STATUS, Status.USERID_PK_COLUMN, userId)
                          );
        Status s = (Status)DataObjectUtils.objectForQuery(context, q);
        
        if ((s == null) && create) {
            s = context.newObject(Status.class);
            s.setUserid(userId);
        }
        
        return s;
    }


}
