package com.dl.middleware.base.web;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dl.middleware.base.model.Student;
import com.dl.middleware.base.service.StudentService;
import com.dl.middleware.base.service.Impl.StudentServiceImpl;

@Controller
@RequestMapping(value="/student")
public class StudentController{

	@Resource
	private StudentService studentService;
	
	@RequestMapping(value = "/student-list")
	@ResponseBody
	public JSON getStudentList(HttpServletRequest request, HttpServletResponse response) throws SQLException{
		JSONObject result = new JSONObject();
		List<Student> list = studentService.listAll();
		result.put("studentList", list);
		return result;
	}
}
