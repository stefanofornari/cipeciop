/*
 * Funambol is a mobile platform developed by Funambol, Inc.
 * Copyright (C) 2009 Funambol, Inc.
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License version 3 as published by
 * the Free Software Foundation with the addition of the following permission
 * added to Section 15 as permitted in Section 7(a): FOR ANY PART OF THE COVERED
 * WORK IN WHICH THE COPYRIGHT IS OWNED BY FUNAMBOL, FUNAMBOL DISCLAIMS THE
 * WARRANTY OF NON INFRINGEMENT  OF THIRD PARTY RIGHTS.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, see http://www.gnu.org/licenses or write to
 * the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301 USA.
 *
 * You can contact Funambol, Inc. headquarters at 643 Bair Island Road, Suite
 * 305, Redwood City, CA 94063, USA, or at email address info@funambol.com.
 *
 * The interactive user interfaces in modified source and object code versions
 * of this program must display Appropriate Legal Notices, as required under
 * Section 5 of the GNU Affero General Public License version 3.
 *
 * In accordance with Section 7(b) of the GNU Affero General Public License
 * version 3, these Appropriate Legal Notices must retain the display of the
 * "Powered by Funambol" logo. If the display of the logo is not reasonably
 * feasible for technical reasons, the Appropriate Legal Notices must display
 * the words "Powered by Funambol".
 */

package tests.ste.cipeciop;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.access.DataContext;
import org.apache.cayenne.query.SQLTemplate;
import ste.cipeciop.Cip;

/**
 *
 * @author ste
 */
public class CipCiopTestUtil {
    
    public static final String TEST_FROM1 = "stefano_fornari";
    public static final String TEST_FROM2 = "cipeciopd";
    public static final String TEST_TO1   = TEST_FROM2;
    public static final String TEST_TO2   = TEST_FROM1;
    public static final String TEST_TEXT1 = "message from " 
                                          + TEST_FROM1
                                          + " to "
                                          + TEST_TO1
                                          ;
    public static final String TEST_TEXT2 = "message from " 
                                          + TEST_FROM2
                                          + " to "
                                          + TEST_TO2
                                          ;
    
    public static void deleteAllCips() {
        ObjectContext cayenne = DataContext.createDataContext();
        cayenne.performQuery(new SQLTemplate(Cip.class, "delete from cip"));
        cayenne.commitChanges();
    }
}
