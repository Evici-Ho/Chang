package com.dl.middleware.utils;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
/**
 * 
*    
* 项目名称：WeiJianUtils   
* 类名称：RandomUtil   
* 类描述：随机数工具类   
* 创建人：WeiJian
* 创建时间�?2014�?8�?22�? 上午11:13:46    
* 备注�?   
* @version    1.0
*
 */
public class RandomUtil {
	private static final String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String letterChar = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String digitChar = "0123456789";

	/**
	 * 返回�?个定长的随机字符�? (只包含数�?)
	 * @param length
	 *            随机字符串长�?
	 * 	@return 随机字符�?
	 */
	public static String generateDigitString(int length){
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for(int i=0;i<length;i++){
			sb.append(digitChar.charAt(random.nextInt(digitChar.length())));
		}
		return sb.toString();
	}
	/**
	 * 生成�?个位于最小�?�与�?大�?�之间的整数
	 * @param min
	 * 		�?小�??
	 * @param max
	 * 		�?大�??
	 * @return
	 * 		�?
	 */
	public static int generateIntBetweenMinMax(int min,int max){
		Random random = new Random();
		if(min > max){
			int tmp = 0;
			tmp = min;
			min = max;
			max = tmp;
		}
		return random.nextInt(max - min) + min; 
	}
	/**
	 * 返回�?个定长的随机字符�?(只包含大小写字母、数�?)
	 * 
	 * @param length
	 *            随机字符串长�?
	 * @return 随机字符�?
	 */
	public static String generateString(int length) {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(allChar.charAt(random.nextInt(allChar.length())));
		}
		return sb.toString();
	}
	
	/**
	 * 生成�?个位于两数�?�之间的随机长度字符�?
	 * @param minLen  	�?小长度�??
	 * @param maxLen	�?大长度�??
	 * @return 字符�?
	 */
	public static String generateString(int minLen,int maxLen){
		Random random = new Random();
		if(minLen > maxLen){
			int tmp = 0;
			tmp = minLen;
			minLen = maxLen;
			maxLen = tmp;
		}
		return generateString(random.nextInt(maxLen - minLen) + minLen); 
	}
	
	/**
	 * 返回�?个定长的随机纯字母字符串(只包含大小写字母)
	 * 
	 * @param length
	 *            随机字符串长�?
	 * @return 随机字符�?
	 */
	public static String generateMixString(int length) {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(allChar.charAt(random.nextInt(letterChar.length())));
		}
		return sb.toString();
	}

	/**
	 * 返回�?个定长的随机纯小写字母字符串(只包含小写字�?)
	 * 
	 * @param length
	 *            随机字符串长�?
	 * @return 随机字符�?
	 */
	public static String generateLowerString(int length) {
		return generateMixString(length).toLowerCase();
	}

	/**
	 * 返回�?个定长的随机纯大写字母字符串(只包含大写字�?)
	 * 
	 * @param length
	 *            随机字符串长�?
	 * @return 随机字符�?
	 */
	public static String generateUpperString(int length) {
		return generateMixString(length).toUpperCase();
	}

	/**
	 * 生成�?个定长的�?0字符�?
	 * 
	 * @param length
	 *            字符串长�?
	 * @return �?0字符�?
	 */
	public static String generateZeroString(int length) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append('0');
		}
		return sb.toString();
	}

	/**
	 * 根据数字生成�?个定长的字符串，长度不够前面�?0
	 * 
	 * @param num
	 *            数字
	 * @param fixdlenth
	 *            字符串长�?
	 * @return 定长的字符串
	 */
	public static String toFixdLengthString(long num, int fixdlenth) {
		StringBuilder sb = new StringBuilder();
		String strNum = String.valueOf(num);
		if (fixdlenth - strNum.length() >= 0) {
			sb.append(generateZeroString(fixdlenth - strNum.length()));
		} else {
			throw new RuntimeException("将数�?" + num + "转化为长度为" + fixdlenth
					+ "的字符串发生异常�?");
		}
		sb.append(strNum);
		return sb.toString();
	}

	/**
	 * 根据数字生成�?个定长的字符串，长度不够前面�?0
	 * 
	 * @param num
	 *            数字
	 * @param fixdlenth
	 *            字符串长�?
	 * @return 定长的字符串
	 */
	public static String toFixdLengthString(int num, int fixdlenth) {
		StringBuilder sb = new StringBuilder();
		String strNum = String.valueOf(num);
		if (fixdlenth - strNum.length() >= 0) {
			sb.append(generateZeroString(fixdlenth - strNum.length()));
		} else {
			throw new RuntimeException("将数�?" + num + "转化为长度为" + fixdlenth
					+ "的字符串发生异常�?");
		}
		sb.append(strNum);
		return sb.toString();
	}
	
	/**
	 * 将正整数n随机拆分为n个正整数
	 * @param n
	 * @param l
	 */
	public static int[] getRandomArray(int n,int l){
		Set<Integer> set=new TreeSet<Integer>();
		int a[]=new int[n];
		 Random rand = new Random(System.currentTimeMillis());
		for(int i=0;i<l-1;i++){
			int result=rand.nextInt(n)+1;
			while(set.contains(result) || result==n){
				result=rand.nextInt(n)+1;
			}
			set.add(result);
		}
		System.out.println(set);
		Iterator<Integer> it=set.iterator();
		int bTemp=0,i=0;
		while(it.hasNext()){
			Integer r=it.next();
//			System.out.println("r="+r+" bTemp="+bTemp+" r-bTemp="+(r-bTemp));
			a[i++]=r-bTemp;
			bTemp=r;
		}
		a[l-1]=n-bTemp;
		for(i=0;i<l;i++){
			System.out.print(a[i]+" ");
		}
		return a;
	}
	
	public static void main(String[] args) {
		getRandomArray(8, 3);
	}
}