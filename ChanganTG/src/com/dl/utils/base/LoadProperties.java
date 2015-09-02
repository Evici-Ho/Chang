package com.dl.utils.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class LoadProperties {

	private static String conf = "classPath:project.properties";
	private static Properties properties;

	public static Properties load() {
		String classPath = LoadProperties.class.getResource("/").getPath().replaceAll("%20", " ");
		conf = conf.replace("classPath:", classPath);
		properties = new Properties();
		File pfile = new File(conf);
		if (pfile.exists()) {
			try {
				properties.load(new FileInputStream(pfile));
			} catch (FileNotFoundException e) {
				System.out.println("未找到properties文件");
			} catch (IOException e) {
				System.out.println("properties文件读取异常");
			} catch (Exception ee) {
				ee.printStackTrace();
			}
		}
		return properties;
	}

	/**
	 * path 格式:classPath+src下的位置路径,如"classPath:project.properties"
	 * 
	 * @param path
	 * @return
	 */
	public static Properties load(String path) {
		String classPath = LoadProperties.class.getResource("/").getPath().replaceAll("%20", " ");
		path = path.replace("classPath:", classPath);
		properties = new Properties();
		File pfile = new File(path);
		if (pfile.exists()) {
			try {
				//BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(fileName),"UTF-8")); 
				//properties.load(new FileInputStream(pfile));
				properties.load(new InputStreamReader(new FileInputStream(pfile),"UTF-8"));
			} catch (FileNotFoundException e) {
				System.out.println("未找到properties文件");
			} catch (IOException e) {
				System.out.println("properties文件读取异常");
			} catch (Exception ee) {
				System.out.println(ee);
			}
		}
		return properties;
	}

	public static void main(String[] args) {
		//String classPath = LoadProperties.class.getResource("/").getPath().replaceAll("%20"," ");
				//System.out.println(classPath);
				String path = "classPath:wechat/wechat.properties";
				Properties pro = load(path);
				//path = path.replace("classPath:", classPath);
				System.out.println(pro.getProperty("subscribeMsg"));
	}
}
