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
 *
 * @author ste
 */
public interface Constants {
    
    public final static String PARAM_CIP_TEXT = "cip";
    
    public final static String ATTRIBUTE_FRIENDS    = "friends"    ;
    public final static String ATTRIBUTE_IDENTIFIER = "openid_user";
    public final static String ATTRIBUTE_VERSION    = "version"    ;
    public final static String ATTRIBUTE_RESULT     = "result"     ;
    public final static String ATTRIBUTE_ERROR      = "error"      ;
    
    public final static String KEY_APPLICATION_NAME    = "application.name"   ;
    public final static String KEY_APPLICATION_VERSION = "application.version";
    public final static String KEY_USER_NAME           = "name";
    public final static String KEY_USER_ID             = "id";
    
    public final static String ALIAS_EMAIL   = "email";
    public final static String ALIAS_USER_ID = "userid";
    
    public final static String AJAX_ACTION        = "action";
    public final static String AJAX_ACTION_DELETE = "delete";
    public final static String AJAX_ACTION_CHECK  = "check" ;
    
    public final static String AJAX_PARAM_ID   = "id"  ;
    public final static String AJAX_PARAM_TYPE = "type";
    
    public final static String DB_ENTITY_CIP    = "Cip"   ;
    public final static String DB_ENTITY_CIOP   = "Ciop"  ;
    public final static String DB_ENTITY_STATUS = "Status";
    
    public final static int MAX_CIP_LENGTH = 950;

}
