package com.dl.middleware.base.aop;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * 
*    
* 项目名称：DLMiddleware   
* 类名称：AopAspect   
* 类描述：   aop计算时间与log入参
* 创建人：WeiJian
* 创建时间：2014年10月7日 下午2:23:49    
* 备注：   
* @version    1.2
*
 */
public class AopAspect {

	private static final Logger log = Logger.getLogger(AopAspect.class);

	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		long time = System.currentTimeMillis();
		Object retVal = null;
		try {
			log.info("Controller请求开始-> ");
			retVal = pjp.proceed();
		} catch (Throwable e) {
			log.error("出现异常", e);
			JSONObject result = new JSONObject();
			result.put("status_code", 100); // Codes.OTHER_ERROR
			result.put("status_msg", "系统出错，请检查输入。");
			return result;
		}
		time = System.currentTimeMillis() - time;
		log.info("Controller请求结束->"
				+ pjp.getTarget().getClass().getSimpleName() + "."
				+ pjp.getSignature().getName() + " 请求耗时: " + time + " ms");
		return retVal;
	}
}
