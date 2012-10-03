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

import ste.cipeciop.dao.auto._Cip;

/**
 * This class represents a cip; it uses Apache Cayenne for persistence
 * 
 * @author ste
 */
public class Cip extends _Cip {
    
    /**
     * Creates a new Cip and assigns to it a randomly generated id
     */
    public Cip() {
        setText("");
        setFrom(null);
    }
    
    /**
     * Creates a new Cip with the given text (calling Cip() first)
     * 
     * @param text the cip text
     */
    public Cip(String text) {
        this();
        setText(text);
    }

    /**
     * @param text the text to set - NOT NULL
     * 
     * @throws NullPointerException if text is null
     */
    @Override
    public void setText(String text) throws NullPointerException {
        if (text == null) {
            throw new NullPointerException("text cannot be null");
        }
        super.setText(text);
    }
    
    /**
     * Returns a text representation of the Cip (currently the cip's text)
     * 
     * @return a text representation of the Cip
     */
    @Override
    public String toString() {
        return getText();
    }  
    
    @Override
    public int hashCode() {
        return getText().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cip other = (Cip) obj;
        return getText().equals(other.getText())
            && getFrom().contains(other.getFrom())
            && getTo().equals(other.getTo());
    }
}
