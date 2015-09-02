package com.dl.middleware.base.dao;

import com.dl.middleware.base.model.Post;

public interface PostDao extends BaseDao<Post> {

	public Post getByPostName(String postName);
}
