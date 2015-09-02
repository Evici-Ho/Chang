package com.dl.middleware.base.dao.Impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.dl.middleware.base.dao.StudentDao;
import com.dl.middleware.base.model.Student;

@Component
public class StudentDaoImpl2 extends BaseDaoImpl implements StudentDao {

	private final String namespace = "Student.";
	
	public List<Student> listAll(){
		return getSqlMapClientTemplate().queryForList(namespace + "listAll");
	}
}
