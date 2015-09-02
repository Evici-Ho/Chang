package com.dl.middleware.base.web;

import java.util.List;

import org.springframework.context.ApplicationContext;

import com.dl.middleware.base.model.Student;
import com.dl.middleware.base.service.StudentService;

public class Test {

	public static ApplicationContext applicationContext;
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		StudentService studentService = (StudentService)applicationContext.getBean("studentService");
		List<Student> stuList = studentService.listAll();
		System.out.println(stuList.size());
	}

}
