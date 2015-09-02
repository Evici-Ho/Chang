package com.dl.middleware.base.service.Impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.dl.middleware.base.dao.StudentDao;
import com.dl.middleware.base.model.Student;
import com.dl.middleware.base.service.StudentService;

@Component
public class StudentServiceImpl implements StudentService {

	@Resource
	private StudentDao studentDao;
	
	public List<Student> listAll(){
		return studentDao.listAll();
	}
}
