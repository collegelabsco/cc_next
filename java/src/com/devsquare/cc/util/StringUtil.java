package com.devsquare.cc.util;

import java.util.Arrays;

public class StringUtil {

	public static String sortString(String s) {

		char[] c = s.toCharArray();
		Arrays.sort(c);
		return new String(c);
	}

}
