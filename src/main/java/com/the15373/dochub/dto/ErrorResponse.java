package com.the15373.dochub.dto;

/**
 * 出现错误时返回对象
 * @author afang
 *
 * @date 2018年6月26日
 */
public class ErrorResponse extends AbstractResponse{
	String errors;

	public String getErrors() {
		return errors;
	}

	public void setErrors(String errors) {
		this.errors = errors;
	}
}
