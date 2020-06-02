package com.mkleo.project.model.dbflow;

import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
@Table(database = DBUser.class)
// 继承BaseModel可以基于User对象直接进行CRUD操作
public class User extends BaseModel {
}
