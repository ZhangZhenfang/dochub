package com.the15373.dochub.dto;

import java.io.Serializable;

/**
 * 返回数据抽象类
 * @author afang
 *
 * @date 2018年6月26日
 */

public abstract class AbstractResponse implements Serializable {

	private static final long serialVersionUID = 7546308580113752656L;

	String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
