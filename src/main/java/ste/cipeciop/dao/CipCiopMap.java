package ste.cipeciop.dao;

import ste.cipeciop.dao.auto._CipCiopMap;

public class CipCiopMap extends _CipCiopMap {

    private static CipCiopMap instance;

    private CipCiopMap() {}

    public static CipCiopMap getInstance() {
        if(instance == null) {
            instance = new CipCiopMap();
        }

        return instance;
    }
}
