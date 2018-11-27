package com.fufu.config.excel;

import java.util.HashMap;
import java.util.Map;

/**
 * Excel导入导出数据定义转换<br>
 * 举例:<br>
 * 数据导出， {lock,{0:正常，1:锁定}}<br>
 * 数据导入,{lock,{正常:0，锁定:1}}
 */
public class ExcelDataFormatter {
	/**
	 * K:{V1:V2}
	 */
	private Map<String,Map<String,String>> formatter=new HashMap<String, Map<String,String>>();
	
	public void set(String key, Map<String,String> map){
		formatter.put(key, map);
	}
	
	public Map<String,String> get(String key){
		return formatter.get(key);
	}
	
}
