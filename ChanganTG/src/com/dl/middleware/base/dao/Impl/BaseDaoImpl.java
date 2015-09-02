package com.dl.middleware.base.dao.Impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.dl.middleware.base.dao.BaseDao;
import com.ibatis.sqlmap.client.SqlMapClient;

public class BaseDaoImpl<T> extends SqlMapClientDaoSupport implements BaseDao<T> {
	
	/*@Resource(name="sqlMapClient")
	public void getSqlMapClient(SqlMapClient sqlMapClient){
		super.setSqlMapClient(sqlMapClient);
	}*/
	
	private static final String POSTFIX_INSERT = ".insert";
	private static final String POSTFIX_DELETE = ".delete";
	private static final String POSTFIX_UPDATE = ".update";
	private static final String POSTFIX_SELECTONE = ".getById";
	private static final String POSTFIX_SELECTALL = ".listAll";
	
	public String getType(){
		ParameterizedType type = (ParameterizedType)this.getClass().getGenericSuperclass();
		String fullName = type.getActualTypeArguments()[0].toString();
		//return fullName;
		return fullName.split("\\.")[fullName.split("\\.").length - 1];
	}
	
	public List<T> listAll(){
		return getSqlMapClientTemplate().queryForList(getType() + POSTFIX_SELECTALL);
	}
	
	public T getById(Long id){
		return (T)getSqlMapClientTemplate().queryForObject(getType() + POSTFIX_SELECTONE, id);
	}
	
	public Object insert(T obj){
		return getSqlMapClientTemplate().insert(obj.getClass().getSimpleName() + POSTFIX_INSERT, obj);
	}
	
	public int delete(Long id){
		return getSqlMapClientTemplate().delete(getType() + POSTFIX_DELETE, id);
	}
	
	public int update(T obj){
		return getSqlMapClientTemplate().update(obj.getClass().getSimpleName() + POSTFIX_UPDATE, obj);
	}

}
