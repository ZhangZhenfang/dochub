package com.the15373.dochub.dto;

/**
 * 返回数据抽象类
 * @author afang
 *
 * @date 2018年6月26日
 */

public abstract class AbstractResponse {
	String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
