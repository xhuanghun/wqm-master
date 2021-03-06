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
 * 系统监测项
 * @author wangxj
 *
 */
@Entity
@Table(name = "BUSI_WTR_MONITORITEM")
public class MonitorItem extends IdEntity {
	
	private static final long serialVersionUID = 1L;
	
	private String code; 		//监测项编号
	
	private String name;	 	//监测项名称
	
	private String sortNum;     //排序号
	
	private String iconCls;		//监测项图标
	
	private UserEntity user;	//创建人员
	
	private Date createDate;	//创建时间
	
	private Date updateDate;	//更新日期
	
	private String EName;       //监测项英文名字
	private String Company;       //监测项单位

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSortNum() {
		return sortNum;
	}

	public void setSortNum(String sortNum) {
		this.sortNum = sortNum;
	}

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

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getEName() {
		return EName;
	}

	public void setEName(String eName) {
		EName = eName;
	}

	public String getCompany() {
		return Company;
	}

	public void setCompany(String company) {
		Company = company;
	}
	
}
