package com.dl.middleware.base.dao.Impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.dl.middleware.base.dao.UserDao;
import com.dl.middleware.base.model.Student;
import com.dl.middleware.base.model.User;

@Component
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

	//private final String namespace = "User.";
	private final String namespace = getType() + ".";
	
	/*public List<Student> listAll(){
		return getSqlMapClientTemplate().queryForList(namespace + "listAll");
	}*/
}
