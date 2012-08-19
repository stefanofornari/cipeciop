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

/**
 * This class represents a cip
 * 
 * @author ste
 */
public class Cip {
    
    /**
     * The cip id. Please note this is not a persistent unique id for now. 
     * The lifespam of this id is supposed to be within the lifespam of the VM.
     */
    private long id;
    
    /**
     *  The cip text
     */
    private String text;
    
    /**
     * Creates a new Cip and assigns to it a randomly generated id
     */
    public Cip() {
        id = System.currentTimeMillis();
        text = "";
    }
    
    /**
     * Creates a new Cip with the given text (calling Cip() first)
     * 
     * @param text the cip text
     */
    public Cip(String text) {
        this();
        this.text = text;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set - NOT NULL
     * 
     * @throws NullPointerException if text is null
     */
    public void setText(String text) throws NullPointerException {
        if (text == null) {
            throw new NullPointerException("text cannot be null");
        }
        this.text = text;
    }
    
}
