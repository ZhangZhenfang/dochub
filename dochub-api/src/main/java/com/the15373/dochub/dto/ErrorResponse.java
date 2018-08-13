package com.the15373.dochub.dto;

import java.io.Serializable;

/**
 * 出现错误时返回对象
 * @author afang
 *
 * @date 2018年6月26日
 */
public class ErrorResponse extends AbstractResponse implements Serializable {

	private static final long serialVersionUID = -4103112369610211342L;

	String errors;

	public String getErrors() {
		return errors;
	}

	public void setErrors(String errors) {
		this.errors = errors;
	}
}
