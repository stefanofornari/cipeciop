package ste.cipeciop.dao.auto;

import java.util.Date;

import org.apache.cayenne.CayenneDataObject;

/**
 * Class _Status was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _Status extends CayenneDataObject {

    public static final String LAST_CHANGE_PROPERTY = "lastChange";
    public static final String LAST_VISIT_PROPERTY = "lastVisit";
    public static final String USERID_PROPERTY = "userid";

    public static final String USERID_PK_COLUMN = "userid";

    public void setLastChange(Date lastChange) {
        writeProperty("lastChange", lastChange);
    }
    public Date getLastChange() {
        return (Date)readProperty("lastChange");
    }

    public void setLastVisit(Date lastVisit) {
        writeProperty("lastVisit", lastVisit);
    }
    public Date getLastVisit() {
        return (Date)readProperty("lastVisit");
    }

    public void setUserid(String userid) {
        writeProperty("userid", userid);
    }
    public String getUserid() {
        return (String)readProperty("userid");
    }

}
