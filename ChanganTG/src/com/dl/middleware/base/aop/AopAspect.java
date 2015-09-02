package com.dl.middleware.base.aop;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * 
*    
* ��Ŀ���ƣ�DLMiddleware   
* �����ƣ�AopAspect   
* ��������   aop����ʱ����log���
* �����ˣ�WeiJian
* ����ʱ�䣺2014��10��7�� ����2:23:49    
* ��ע��   
* @version    1.2
*
 */
public class AopAspect {

	private static final Logger log = Logger.getLogger(AopAspect.class);

	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		long time = System.currentTimeMillis();
		Object retVal = null;
		try {
			log.info("Controller����ʼ-> ");
			retVal = pjp.proceed();
		} catch (Throwable e) {
			log.error("�����쳣", e);
			JSONObject result = new JSONObject();
			result.put("status_code", 100); // Codes.OTHER_ERROR
			result.put("status_msg", "ϵͳ�����������롣");
			return result;
		}
		time = System.currentTimeMillis() - time;
		log.info("Controller�������->"
				+ pjp.getTarget().getClass().getSimpleName() + "."
				+ pjp.getSignature().getName() + " �����ʱ: " + time + " ms");
		return retVal;
	}
}
