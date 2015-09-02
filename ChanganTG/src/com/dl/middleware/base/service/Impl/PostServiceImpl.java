package com.dl.middleware.base.service.Impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.dl.middleware.base.dao.PostDao;
import com.dl.middleware.base.model.Post;
import com.dl.middleware.base.service.PostService;

@Component
public class PostServiceImpl implements PostService {

	@Resource
	private PostDao postDao;
	
	public Post getByPostName(String postName){
		return postDao.getByPostName(postName);
	}
}
