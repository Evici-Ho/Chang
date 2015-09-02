package com.dl.middleware.base.service;

import java.util.List;

import com.dl.middleware.base.model.User;

public interface UserService {
	
	public void insert(User user);

	public List<User> listAll();
}
