package com.sakthipriyan.cricscore.utils;

public class StringUtils {
	public static String getOnlyNumbers(String str){
		return str.replaceAll("[^0-9]", "");
	}
	
	public static String getNonNumeric(String str){
		return str.replaceAll("[*&0-9/]", "");
	}
	
	public static boolean isNumeric(String input){
		return (input != null && input.matches("[0-9]+"));
	}
	
	public static boolean isNumericPlus(String input){
		return (input != null && input.matches("[0-9 ]+"));
	}
	
	public static long getLong(String input){
		long output = 0;
		if (StringUtils.isNumeric(input)) {
			output = Long.parseLong(input);
		}
		return output;
	}
	
	public static int getSpaceCount(String input) {
		int ret = -1;
		int lastIndex = 0;
		while (lastIndex != -1) {
			lastIndex = input.indexOf(" ", ++lastIndex);
			ret++;
		}
		return ret;
	}
	
}
