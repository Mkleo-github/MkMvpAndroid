package com.mkleo.project.model.dbflow;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * 用户数据库
 */
@Database(name = DBUser.NAME, version = DBUser.VERSION)
public class DBUser {
    public static final String NAME = "db_user";
    public static final int VERSION = 1;
}
