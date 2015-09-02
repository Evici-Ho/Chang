package com.dl.middleware.base.service.Impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.dl.middleware.base.dao.UserDao;
import com.dl.middleware.base.model.User;
import com.dl.middleware.base.service.UserService;

@Component
public class UserServiceImpl implements UserService {

	@Resource
	private UserDao userDao;
	
	public void insert(User user){
		userDao.insert(user);
	}
	
	public List<User> listAll(){
		return userDao.listAll();
	}
}
