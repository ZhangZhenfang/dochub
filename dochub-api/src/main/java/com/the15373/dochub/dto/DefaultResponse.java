package com.the15373.dochub.dto;

import java.io.Serializable;

/**
 * 默认返回对象
 * @author afang
 *
 * @date 2018年6月26日
 */
public class DefaultResponse<T> extends AbstractResponse implements Serializable {

	private static final long serialVersionUID = -918350878917674745L;

	T data;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
