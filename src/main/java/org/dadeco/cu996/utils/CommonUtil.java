package org.dadeco.cu996.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class CommonUtil {
	public final static String STATUS_TRUE = "1";
	public final static String STATUS_FALSE = "0";

	public final static int AR_CONFIRM_CONFIRM_PERIOD_BY_MONTH = 2;

	public static boolean isEmptyString(String str) {
		if (str != null && str.trim().length() > 0 && !str.trim().isEmpty() && !str.equals("")) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean isEmptyMap(Map<?, ?> map) {
		if (map != null && !map.isEmpty() && map.size() > 0) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean isEmptyList(List<?> list) {
		if (list != null && !list.isEmpty() && list.size() > 0) {
			return false;
		} else {
			return true;
		}
	}

	public static String setEmptyString(String str) {
		if (!isEmptyString(str)) {
			return str;
		} else {
			return "";
		}
	}

	public static boolean isInteger(String value) {
		if (CommonUtil.isEmptyString(value)) {
			return false;
		} else {
			String regex = "^(-?[0-9]\\d*)$";
			return value.matches(regex);
		}
	}

	public static boolean isNumeric(String value) {
		if (CommonUtil.isEmptyString(value)) {
			return false;
		} else {
			String regex = "^(-?[1-9]\\d*\\.?\\d*)|(-?0\\.\\d*[1-9])|(-?[0])|(-?[0]\\.\\d*)$";
			return value.matches(regex);
		}
	}

	public static Properties initDefaultProperties() {
		Properties properties = null;

		String packageName = CommonUtil.class.getPackage().getName();
		String path = CommonUtil.class.getResource("").getPath();

		path = path.replaceAll(packageName.replace(".", "/") + "/", "");
		path = path.replaceAll("classes/", "");

		String resourceFile = path + "default.properties";
		// InputStream is =
		// event.getServletContext().getResourceAsStream(resourceFile);
		// Properties proper = new Properties();

		try {
			FileReader fr = new FileReader(resourceFile);
			BufferedReader br = new BufferedReader(fr);
			properties = new Properties();
			properties.load(br);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return properties;
	}
}
