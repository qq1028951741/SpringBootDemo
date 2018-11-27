package com.fufu.config.excel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Excel注解，用以生成Excel表格文件
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface Excel {
	
	/**
	 * 列名
	 * @return
	 */
	String name() default "";
	
	/**
	 * 宽度
	 * @return
	 */
	int width() default 20;

	/**
	 * 忽略该字段
	 * @return
	 */
	boolean skip() default false;
	
	/**
	 * 日期格式 默认yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	String dateFormat() default "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 浮点数的精度
	 * @return
	 */
	int precision() default -1;
	
	/**
	 * 四舍五入
	 * @return
	 */
	boolean round() default true;
	
}
