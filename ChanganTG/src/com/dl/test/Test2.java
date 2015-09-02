package com.dl.test;

import com.dl.middleware.base.dao.Impl.UserDaoImpl;

public class Test2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		UserDaoImpl dao = new UserDaoImpl();
		System.out.println(dao.getType());
	}

}
