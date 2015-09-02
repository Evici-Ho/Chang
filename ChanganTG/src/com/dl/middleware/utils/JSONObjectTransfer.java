package com.dl.middleware.utils;

import java.io.Serializable;

import net.sf.json.JSONObject;

/**
 * description:转JSONObject 接口
 */
public interface JSONObjectTransfer extends Serializable {
	public JSONObject toJSONObject();
}
