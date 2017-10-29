package com.bocsoft.bocop.mdm.entity.qo;

/**
 * @author dzg7151
 * Serice返回报文
 */
public class ResponseQo {

private Object result;
	
	/**状态描述*/
	private String desc;

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
