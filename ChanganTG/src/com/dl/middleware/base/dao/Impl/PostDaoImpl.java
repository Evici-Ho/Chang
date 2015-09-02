package com.dl.middleware.base.dao.Impl;

import org.springframework.stereotype.Component;

import com.dl.middleware.base.dao.PostDao;
import com.dl.middleware.base.model.Post;

@Component
public class PostDaoImpl extends BaseDaoImpl<Post> implements PostDao {

	//private final String namespace = "Post.";
	private final String namespace = getType() + ".";
	
	@SuppressWarnings("deprecation")
	public Post getByPostName(String postName){
		return (Post) getSqlMapClientTemplate().queryForObject(namespace + "getByPostName", postName);
	}
}
