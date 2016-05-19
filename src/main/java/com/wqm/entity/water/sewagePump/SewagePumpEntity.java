package com.wqm.entity.water.sewagePump;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wqm.entity.IdEntity;
import com.wqm.entity.sys.UserEntity;
import com.wqm.entity.water.AreaEntity;
import com.wqm.entity.water.MonitorItem;

/**
 * 污水提升泵站
 * @author xuxz
 *
 */
@Entity
@Table(name = "BUSI_WTR_SEWAGEPUMP")
public class SewagePumpEntity extends IdEntity {
	
	private static final long serialVersionUID = 1L;
	
	private String code; 		//提升泵站编号	
	private String name;	 	//提升泵站名称	
	private UserEntity user;	//创建人员
	private AreaEntity area;	//所属区域
	private String iconCls;		//污水厂图标	
	private String cover;		//覆盖流域
	private String monitorPoint;//监测点位
	private Date createDate;	//创建时间	
	private Date updateDate;	//更新日期
	private List<MonitorItem> monitorItem;
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	// JPA 基于USERID列的多对一关系定义
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	@ManyToOne
	@JoinColumn(name = "areaCode",referencedColumnName = "code")
	public AreaEntity getArea() {
		return area;
	}

	public void setArea(AreaEntity area) {
		this.area = area;
	}
	
	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
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
	@ManyToMany
	@JoinTable(name = "BUSI_WTR_SEWAGEPUMPMONITORITEM", joinColumns = { @JoinColumn(name ="sewagePumpCode" ,referencedColumnName = "code")}, 
	  inverseJoinColumns = { @JoinColumn(name = "moniterItemCode",referencedColumnName = "code") })
	public List<MonitorItem> getMonitorItem() {
		return monitorItem;
	}

	public void setMonitorItem(List<MonitorItem> monitorItem) {
		this.monitorItem = monitorItem;
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
