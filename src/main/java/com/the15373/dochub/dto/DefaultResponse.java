package com.the15373.dochub.dto;

/**
 * 默认返回对象
 * @author afang
 *
 * @date 2018年6月26日
 */
public class DefaultResponse<T> extends AbstractResponse{
	T data;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
