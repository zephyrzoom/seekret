package com.bocsoft.bocop.mdm.utils;

/**
 * @author dzg7151
 *
 */
public class CommonConstants {

	public static final String LC_SUCCESS = "0000";
	
	public static final String LC_FAILURE = "1111";
	
	//显示多少天内的
	public static final int  DAYSDIFF = 30;
	
	//展示多少条
	public static final int  ITEMTOSHOW = 20;
	
	//展示范围：五公里内
	public static final int  DISTANCETOSHOW = 5000;
	
	//分类范围
	public static final int CLASSIFYCRITERIA_DISTANCE = 10;
	
	//分类范围：纬度差
	// public static final double CLASSIFYCRITERIA_LATITUDE = 0.00014;//地图20倍
	public static final double CLASSIFYCRITERIA_LATITUDE = 0.000903628;//地图17倍

	//分类范围：大经度
	//public static final double CLASSIFYCRITERIA_LANGITUDE = 0.0017;
	public static final double CLASSIFYCRITERIA_LANGITUDE = 0.013903775;
	
	//分类范围：经度系数
	//public static final double LANGITUDE_FACTOR = 0.00007;
	public static final double LANGITUDE_FACTOR = 0.000556151;
}
