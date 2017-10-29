package com.bocsoft.bocop.mdm.controller.util;

import java.io.Serializable;

/**
 * 接口调用返回报文
 * @author dzg7151
 */
public class LcResponse implements Serializable{

		private static final long serialVersionUID = 1L;
		// 返回状态
		private String stat;
		// 返回描述
		private String desc;
		// 包体
		private Object result;
		
		public String getStat() {
			return stat;
		}
		public void setStat(String stat) {
			this.stat = stat;
		}
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
		public Object getResult() {
			return result;
		}
		public void setResult(Object result) {
			this.result = result;
		}
		public static long getSerialversionuid() {
			return serialVersionUID;
		}
}
