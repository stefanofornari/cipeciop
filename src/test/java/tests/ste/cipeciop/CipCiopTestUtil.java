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

import java.util.Date;
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
    
    public static final String TEST_USER1 = "user1@yahoo.com";
    public static final String TEST_USER2 = "user2@yahoo.com";
    public static final String TEST_USER3 = "user3@yahoo.com";
    public static final String TEST_TEXT1 = "message test one";
    public static final String TEST_TEXT2 = "message test two";
    public static final String TEST_TEXT3 = "message test three";
    
    public static final String TEST_TEXT_WITH_ICONS_AND_URLS =
        ";) some text http://www.yahoo.com/pippo :D some othertest " +
        "http://thecampanileproject.org/campanile?param1=1&param2=xxx%32yy gfjkhf";
    
    public static void deleteAllCipCiop() {
        ObjectContext cayenne = DataContext.createDataContext();
        cayenne.performQuery(new SQLTemplate(Constants.DB_ENTITY_CIP, "delete from cips"));
        cayenne.performQuery(new SQLTemplate(Constants.DB_ENTITY_CIOP, "delete from ciops"));
        cayenne.commitChanges();
    }
    
    public static CipCiopManager prepareEnvironment() {
        CipCiopManager ccm1 = new CipCiopManager(TEST_USER1);
        CipCiopManager ccm2 = new CipCiopManager(TEST_USER2);
        CipCiopManager ccm3 = new CipCiopManager(TEST_USER3);
        
        //
        // USER1
        //
        Cip cip1_1_2 = new Cip(TEST_TEXT1); cip1_1_2.setFrom(TEST_USER1);
        cip1_1_2.setTo(TEST_USER2); cip1_1_2.setCreated(new Date());
        ccm1.addCip(cip1_1_2);
        Ciop ciop1_1_2 = new Ciop(cip1_1_2.getText());
        ciop1_1_2.setFrom(cip1_1_2.getFrom()); ciop1_1_2.setCreated(cip1_1_2.getCreated());
        ccm2.addCiop(ciop1_1_2);
        
        Cip cip2_1_3 = new Cip(TEST_TEXT2); cip2_1_3.setFrom(TEST_USER1);
        cip2_1_3.setTo(TEST_USER3); cip2_1_3.setCreated(new Date());
        ccm1.addCip(cip2_1_3);
        Ciop ciop2_1_3 = new Ciop(cip2_1_3.getText());
        ciop2_1_3.setFrom(cip2_1_3.getFrom()); ciop2_1_3.setCreated(cip2_1_3.getCreated());
        ccm3.addCiop(ciop2_1_3);
        
        Cip cip3_1_3 = new Cip(TEST_TEXT3); cip3_1_3.setFrom(TEST_USER1);
        cip3_1_3.setTo(TEST_USER3); cip3_1_3.setCreated(new Date());
        ccm1.addCip(cip3_1_3);
        Ciop ciop3_1_3 = new Ciop(cip2_1_3.getText());
        ciop3_1_3.setFrom(cip2_1_3.getFrom()); ciop3_1_3.setCreated(cip2_1_3.getCreated());
        ccm3.addCiop(ciop3_1_3);
        
        //
        // USER2
        //
        Cip cip4_2_1 = new Cip(TEST_TEXT3); cip4_2_1.setFrom(TEST_USER2);
        cip4_2_1.setTo(TEST_USER1); cip4_2_1.setCreated(new Date());
        ccm2.addCip(cip4_2_1);
        Ciop ciop4_2_1 = new Ciop(cip4_2_1.getText());
        ciop4_2_1.setFrom(cip4_2_1.getFrom()); ciop4_2_1.setCreated(cip4_2_1.getCreated());
        ccm1.addCiop(ciop4_2_1);
        
        Cip cip5_2_3 = new Cip(TEST_TEXT2); cip5_2_3.setFrom(TEST_USER2);
        cip5_2_3.setTo(TEST_USER3); cip5_2_3.setCreated(new Date());
        ccm2.addCip(cip5_2_3);
        Ciop ciop5_2_3 = new Ciop(cip5_2_3.getText());
        ciop5_2_3.setFrom(cip5_2_3.getFrom()); ciop5_2_3.setCreated(cip5_2_3.getCreated());
        ccm3.addCiop(ciop5_2_3);
        
        //
        // USER 3
        //
        Cip cip6_3_1 = new Cip(TEST_TEXT1); cip6_3_1.setFrom(TEST_USER3);
        cip6_3_1.setTo(TEST_USER1); cip6_3_1.setCreated(new Date());
        ccm3.addCip(cip6_3_1);
        Ciop ciop6_3_1 = new Ciop(cip6_3_1.getText());
        ciop6_3_1.setFrom(cip6_3_1.getFrom()); ciop6_3_1.setCreated(cip6_3_1.getCreated());
        ccm1.addCiop(ciop6_3_1);
        
       return ccm1;
    }
}
