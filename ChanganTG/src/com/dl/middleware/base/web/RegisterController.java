package com.dl.middleware.base.web;

import java.sql.SQLException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dl.middleware.base.config.Codes;
import com.dl.middleware.base.model.Post;
import com.dl.middleware.base.model.User;
import com.dl.middleware.base.service.PostService;
import com.dl.middleware.base.service.UserService;
import com.dl.middleware.utils.DateJsonValueProcessor;

@Controller
@RequestMapping(value = "/login")
public class RegisterController extends BaseController {

	@Resource
	private UserService userService;
	@Resource
	private PostService postService;

	@RequestMapping(value = "/validate-code")
	@ResponseBody
	public JSON validateCode(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		JSONObject result = new JSONObject();
		String port = ":" + request.getServerPort();
		String s =request.getScheme()+"://"+request.getServerName()+port+request.getRequestURI();
		result.put("url", s);
		result.put("status_code", 0);
		return result;
	}

	@RequestMapping(value = "/register")
	@ResponseBody
	public JSON register(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		JSONObject result = new JSONObject();
		String openId = request.getParameter("openId");
		String contactName = request.getParameter("contactName");
		String contactPhone = request.getParameter("contactPhone");
		String password = request.getParameter("password");
		String postName = request.getParameter("post");
		if (!validateInParameters(openId, contactName, contactPhone, password, postName)) {
			result.put("status_code", Codes.PARAM_ERROR);
			return result;
		}
		Post post = postService.getByPostName(postName);
		if(post == null){
			result.put("status_code", Codes.OTHER_ERROR);
			result.put("msg", "此职位名称不存在，请确认再提交。");
			return result;
		}
		
		User user = new User();
		user.setContactName(contactName);
		user.setContactPhone(contactPhone);
		user.setOpenId(openId);
		user.setPassword(password);
		user.setPost(post.getPostId());
		user.setRegisterDate(new Date());
		userService.insert(user);
		
		//JsonConfig config = new JsonConfig();
		//config.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor());
		//JSONArray ary = JSONArray.fromObject(userService.listAll(), config);

		//result.put("user_list", ary);
		result.put("status_code", Codes.SUCCESS);
		return result;
	}
}
