package com.wqm.entity.water.sewagePump;

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
@Table(name = "BUSI_WTR_MONITORDA_SEWAGEPUMP")
public class MonitorDataSewagePump extends IdEntity {
	
	private static final long serialVersionUID = 1L;
	
	private String sewagePumpName;         
	private String sewagePumpCode;  	
	private String itemName;	
	private String  itemValue;	
	private String company;		//单位
	private Integer monitorType;		//监测类型，频率
	private String cover;		//覆盖流域
	private String monitorPoint;//监测点位
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


	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}


	public Integer getMonitorType() {
		return monitorType;
	}

	public void setMonitorType(Integer monitorType) {
		this.monitorType = monitorType;
	}

	public String getSewagePumpName() {
		return sewagePumpName;
	}

	public void setSewagePumpName(String sewagePumpName) {
		this.sewagePumpName = sewagePumpName;
	}

	public String getSewagePumpCode() {
		return sewagePumpCode;
	}

	public void setSewagePumpCode(String sewagePumpCode) {
		this.sewagePumpCode = sewagePumpCode;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getMonitorPoint() {
		return monitorPoint;
	}

	public void setMonitorPoint(String monitorPoint) {
		this.monitorPoint = monitorPoint;
	}

}