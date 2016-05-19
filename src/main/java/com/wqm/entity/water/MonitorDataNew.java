package com.wqm.entity.water;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wqm.entity.IdEntity;
import com.wqm.entity.sys.UserEntity;

/**
 * 水体监测数据
 * @author wangxj
 *
 */
@Entity
@Table(name = "BUSI_WTR_MONITORDATANEW")
public class MonitorDataNew extends IdEntity {
	
	private static final long serialVersionUID = 1L;
	
	private String waterName;         
	private String waterCode;  	
	private String itemName;	
	private String  itemValue;	
	private String company;		//单位
	private Integer type;		//类别
	private Integer monitorType;		//监测类型，频率
	private Integer waterType;		//水体类型，2为污水处理厂
	private UserEntity user;	//创建人员	
	private Date createDate;	//创建时间
	private Date monitorDate;
	private Date updateDate;	//更新日期


	// JPA 基于USERID列的多对一关系定义
	@ManyToOne
	@JoinColumn(name = "userId")
	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getMonitorDate() {
		return monitorDate;
	}

	public void setMonitorDate(Date monitorDate) {
		this.monitorDate = monitorDate;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemValue() {
		return itemValue;
	}

	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}



	public String getWaterName() {
		return waterName;
	}

	public void setWaterName(String waterName) {
		this.waterName = waterName;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getMonitorType() {
		return monitorType;
	}

	public void setMonitorType(Integer monitorType) {
		this.monitorType = monitorType;
	}

	public String getWaterCode() {
		return waterCode;
	}

	public void setWaterCode(String waterCode) {
		this.waterCode = waterCode;
	}

	public Integer getWaterType() {
		return waterType;
	}

	public void setWaterType(Integer waterType) {
		this.waterType = waterType;
	}
	
}
