package com.wqm.entity.water.tapWater;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.wqm.entity.IdEntity;

/**
 * 水体跟监测项中间表
 * @author wangxj
 *
 */
@Entity
@Table(name = "BUSI_WTR_TAPWATERMONITORITEM")
public class TapWaterMonitorEntity extends IdEntity {
	
	private static final long serialVersionUID = 1L;
	
	private String tapWaterCode; 		 //水厂编号
	
	private String moniterItemCode;	 	//监测项编号
	
	private String monitorTypeCode; 	//监测类型
	


	public String getTapWaterCode() {
		return tapWaterCode;
	}

	public void setTapWaterCode(String tapWaterCode) {
		this.tapWaterCode = tapWaterCode;
	}

	public String getMoniterItemCode() {
		return moniterItemCode;
	}

	public void setMoniterItemCode(String moniterItemCode) {
		this.moniterItemCode = moniterItemCode;
	}

	public String getMonitorTypeCode() {
		return monitorTypeCode;
	}

	public void setMonitorTypeCode(String monitorTypeCode) {
		this.monitorTypeCode = monitorTypeCode;
	}
	
}
