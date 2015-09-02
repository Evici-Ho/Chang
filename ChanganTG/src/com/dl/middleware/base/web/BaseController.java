package com.dl.middleware.base.web;

public class BaseController {

	public boolean validateInParameters(Object... params) {
		for (Object obj : params) {
			if (obj == null)
				return false;
		}
		return true;
	}

}
