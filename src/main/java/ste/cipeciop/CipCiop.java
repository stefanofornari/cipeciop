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

/**
 *
 * @author ste
 */
public interface CipCiop extends Comparable {    
    /**
     * The creation timestamp
     * 
     * @return the creation timestamp
     */
    public Date getCreated();
    
    /**
     * Has this cip or ciop been sent from a mobile device?
     * 
     * @return true if sent from a mobile device, false otherwise
     */
    boolean isFromMobile();

    /**
     * @param text the text to set - NOT NULL
     *
     * @throws NullPointerException if text is null
     */
    void setText(String text) throws NullPointerException;

    String getFrom();

    int getId();

    String getText();

    String getTo();

    void setCreated(Date created);

    void setFrom(String from);

    void setId(int id);

    void setTo(String to);
    
    void setMobile(Boolean mobile);
}
