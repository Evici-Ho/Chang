package com.dl.middleware.base.dao;

import java.util.List;

public interface BaseDao<T> {

	public List<T> listAll();
	
	public T getById(Long id);
	
	public Object insert(T obj);
	
	public int delete(Long id);
	
	public int update(T obj);
}
