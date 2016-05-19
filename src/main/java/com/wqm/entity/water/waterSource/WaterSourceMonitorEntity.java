package com.wqm.entity.water.waterSource;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.wqm.entity.IdEntity;

/**
 * 水体跟监测项中间表
 * @author wangxj
 *
 */
@Entity
@Table(name = "BUSI_WTR_WATERSOURCEMONITORITEM")
public class WaterSourceMonitorEntity extends IdEntity {
	
	private static final long serialVersionUID = 1L;
	
	private String waterSourceCode; 		     //编号
	
	private String moniterItemCode;	 	//监测项编号
	
	private String monitorTypeCode; 	//监测类型
	


	public String getWaterSourceCode() {
		return waterSourceCode;
	}

	public void setWaterSourceCode(String waterSourceCode) {
		this.waterSourceCode = waterSourceCode;
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
