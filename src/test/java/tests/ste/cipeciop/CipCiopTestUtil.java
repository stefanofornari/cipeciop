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
import ste.cipeciop.Ciop;
import ste.cipeciop.Cip;
import ste.cipeciop.CipCiopManager;
import ste.cipeciop.Constants;

/**
 *
 * @author ste
 */
public class CipCiopTestUtil {
    
    public static final String TEST_USER1 = "stefano_fornari@yahoo.com";
    public static final String TEST_USER2 = "cipeciopd@yahoo.com";
    public static final String TEST_USER3 = "cipeciopa@yahoo.com";
    public static final String TEST_TEXT1 = "message test one";
    public static final String TEST_TEXT2 = "message test two";
    public static final String TEST_TEXT3 = "message test three";
    
    public static void deleteAllCipCiop() {
        ObjectContext cayenne = DataContext.createDataContext();
        cayenne.performQuery(new SQLTemplate(Constants.DB_ENTITY_CIP, "delete from cips"));
        cayenne.performQuery(new SQLTemplate(Constants.DB_ENTITY_CIOP, "delete from ciops"));
        cayenne.commitChanges();
    }
    
    public static CipCiopManager createCCMForUser1() {
        CipCiopManager ccm = new CipCiopManager(TEST_USER1);
        
        //
        // Cips
        //
        Cip cip = new Cip(CipCiopTestUtil.TEST_TEXT1);
        cip.setTo(TEST_USER2); ccm.addCip(cip);
        
        cip = new Cip(CipCiopTestUtil.TEST_TEXT2);
        cip.setTo(TEST_USER3); ccm.addCip(cip);
        
        cip = new Cip(CipCiopTestUtil.TEST_TEXT3);
        cip.setTo(TEST_USER3); ccm.addCip(cip);
        
        //
        // Ciops
        //
        Ciop ciop = new Ciop(CipCiopTestUtil.TEST_TEXT1);
        ciop.setFrom(TEST_USER2); ccm.addCiop(ciop);
        
        return ccm;
    }
    
    public static CipCiopManager createCCMForUser2() {
        CipCiopManager ccm = new CipCiopManager(TEST_USER2);
        
        Cip cip = new Cip(CipCiopTestUtil.TEST_TEXT1);
        cip.setTo(CipCiopTestUtil.TEST_USER1); ccm.addCip(cip);
        
        cip = new Cip(CipCiopTestUtil.TEST_TEXT2);
        cip.setTo(CipCiopTestUtil.TEST_USER3); ccm.addCip(cip);
        
        return ccm;
    }
}
