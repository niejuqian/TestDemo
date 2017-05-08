package com.testdemo.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.testdemo.entity.Area;

import java.sql.SQLException;
import java.util.List;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 04 06 11:06
 * @DESC：
 */

public class AreaDao {
    private Context context;
    private Dao<Area, Integer> areaDao;
    private DatabaseHelper helper;

    public AreaDao(Context context) {
        this.context = context;
        try {
            helper = DatabaseHelper.getInstance(context);
            areaDao = helper.getDao(Area.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void executeSql(String sql) {
        try {
            areaDao.updateRaw(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void executeSql(List<String> sqlList) {
        try {
            if (null != sqlList && sqlList.size() > 0) {
                for (String sql : sqlList) {
                    areaDao.updateRaw(sql);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Area> getList() {
        try {
            return areaDao.queryBuilder().limit(200L).offset(1L).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void deleteAll() {
        try {
            List<Area> areaList = getList();
            if (null != areaList) {
                for (Area area : areaList) {
                    areaDao.delete(area);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
