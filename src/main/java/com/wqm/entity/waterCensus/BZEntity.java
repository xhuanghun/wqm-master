package com.wqm.entity.waterCensus;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.wqm.entity.IdEntity;

/**
 * 系统水体
 * @author wangxj
 *
 */
@Entity
@Table(name = "BUSI_WC_BZ")
public class BZEntity extends IdEntity {
	
	private static final long serialVersionUID = 1L;	
	private String province; 		//省	
	private String city;	 	//市区	
	private String area; 	//区	
	private String county; 	//县	
	private String street;  	//街道，村	
	private String areaCode;     //行政区
	private String bzName; 		//泵站名字	
	private String bzCode;	 	//水体名称	
	private String eastLngDeg; 	//东经度	
	private String eastLngMin; 	//东经分	
	private String eastLngSec;  	//东经秒	
	private String northLatDeg;     //北纬度
	private String northLatMin; 		//北纬分	
	private String northLatSec;	 	//北纬秒	
	private String onWaterResourceNameCode; 	//所在水资源三级区名称及编码	
	private String onWaterName; 	//所在河流名称	
	private String onWaterNameCode;  	//所在河流名称编码	
	private String onIrrigationName;     //所在灌区(引调水工程)名称
	private String onIrrigationNameCode; 		//所在灌区(引调水工程)编码	
	private String type;	 	//类型	
	private String isJWork; 	//是否是闸站工程	
	private String isQWork; 	//是否是引泉工程	
	private String workStatus;  	//工程建成情况	
	private String workYear;     //工程建成年
	private String workMonth; 		//工程建成月	
	private String startWorkYear;	 	//开工年	
	private String startWorkMonth; 	//开工月	
	private String workJob; 	//工程作用	
	private String workType;  	//工程类别	
	private String workGrade;     //工程级别
	private String capacity; 		//容量	
	private String power;	 	//功率	
	private String planDistance; 	//设计扬程	
	private String pumpsNum; 	//水泵数量	
	private String bzManageArea;  	//泵站管理单位名称	
	private String bzManageAreaCode;     //泵站管理单位代码
	private String bzPortManageName; 		//泵站归口管理部门	
	private String isDivide;	 	//是否完成划分	
	private String isRights; 	//是否完成确权	
	private String fillName; 	//填表人员	
	private String fillNameTel;  	//填表人电话
	private String reviewName;     //复核人
	private String reviewNameTel;     //复核人电话
	private String checkName;     //审核人
	private String checkStatus; 		//审核标志	
	private String areaCheck;	 	//地方审核	
	private String provinceCheck; 	//省级审核	
	private String centralCheck; 	//中央审核
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getBzName() {
		return bzName;
	}

	public void setBzName(String bzName) {
		this.bzName = bzName;
	}

	public String getBzCode() {
		return bzCode;
	}

	public void setBzCode(String bzCode) {
		this.bzCode = bzCode;
	}

	public String getEastLngDeg() {
		return eastLngDeg;
	}

	public void setEastLngDeg(String eastLngDeg) {
		this.eastLngDeg = eastLngDeg;
	}

	public String getEastLngMin() {
		return eastLngMin;
	}

	public void setEastLngMin(String eastLngMin) {
		this.eastLngMin = eastLngMin;
	}

	public String getEastLngSec() {
		return eastLngSec;
	}

	public void setEastLngSec(String eastLngSec) {
		this.eastLngSec = eastLngSec;
	}

	public String getNorthLatDeg() {
		return northLatDeg;
	}

	public void setNorthLatDeg(String northLatDeg) {
		this.northLatDeg = northLatDeg;
	}

	public String getNorthLatMin() {
		return northLatMin;
	}

	public void setNorthLatMin(String northLatMin) {
		this.northLatMin = northLatMin;
	}

	public String getNorthLatSec() {
		return northLatSec;
	}

	public void setNorthLatSec(String northLatSec) {
		this.northLatSec = northLatSec;
	}

	public String getOnWaterResourceNameCode() {
		return onWaterResourceNameCode;
	}

	public void setOnWaterResourceNameCode(String onWaterResourceNameCode) {
		this.onWaterResourceNameCode = onWaterResourceNameCode;
	}

	public String getOnWaterName() {
		return onWaterName;
	}

	public void setOnWaterName(String onWaterName) {
		this.onWaterName = onWaterName;
	}

	public String getOnWaterNameCode() {
		return onWaterNameCode;
	}

	public void setOnWaterNameCode(String onWaterNameCode) {
		this.onWaterNameCode = onWaterNameCode;
	}

	public String getOnIrrigationName() {
		return onIrrigationName;
	}

	public void setOnIrrigationName(String onIrrigationName) {
		this.onIrrigationName = onIrrigationName;
	}

	public String getOnIrrigationNameCode() {
		return onIrrigationNameCode;
	}

	public void setOnIrrigationNameCode(String onIrrigationNameCode) {
		this.onIrrigationNameCode = onIrrigationNameCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIsJWork() {
		return isJWork;
	}

	public void setIsJWork(String isJWork) {
		this.isJWork = isJWork;
	}

	public String getIsQWork() {
		return isQWork;
	}

	public void setIsQWork(String isQWork) {
		this.isQWork = isQWork;
	}

	public String getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}

	public String getWorkYear() {
		return workYear;
	}

	public void setWorkYear(String workYear) {
		this.workYear = workYear;
	}

	public String getWorkMonth() {
		return workMonth;
	}

	public void setWorkMonth(String workMonth) {
		this.workMonth = workMonth;
	}

	public String getStartWorkYear() {
		return startWorkYear;
	}

	public void setStartWorkYear(String startWorkYear) {
		this.startWorkYear = startWorkYear;
	}

	public String getStartWorkMonth() {
		return startWorkMonth;
	}

	public void setStartWorkMonth(String startWorkMonth) {
		this.startWorkMonth = startWorkMonth;
	}

	public String getWorkJob() {
		return workJob;
	}

	public void setWorkJob(String workJob) {
		this.workJob = workJob;
	}

	public String getWorkType() {
		return workType;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}

	public String getWorkGrade() {
		return workGrade;
	}

	public void setWorkGrade(String workGrade) {
		this.workGrade = workGrade;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

	public String getPower() {
		return power;
	}

	public void setPower(String power) {
		this.power = power;
	}

	public String getPlanDistance() {
		return planDistance;
	}

	public void setPlanDistance(String planDistance) {
		this.planDistance = planDistance;
	}

	public String getPumpsNum() {
		return pumpsNum;
	}

	public void setPumpsNum(String pumpsNum) {
		this.pumpsNum = pumpsNum;
	}

	public String getBzManageArea() {
		return bzManageArea;
	}

	public void setBzManageArea(String bzManageArea) {
		this.bzManageArea = bzManageArea;
	}

	public String getBzManageAreaCode() {
		return bzManageAreaCode;
	}

	public void setBzManageAreaCode(String bzManageAreaCode) {
		this.bzManageAreaCode = bzManageAreaCode;
	}

	public String getBzPortManageName() {
		return bzPortManageName;
	}

	public void setBzPortManageName(String bzPortManageName) {
		this.bzPortManageName = bzPortManageName;
	}

	public String getIsDivide() {
		return isDivide;
	}

	public void setIsDivide(String isDivide) {
		this.isDivide = isDivide;
	}

	public String getIsRights() {
		return isRights;
	}

	public void setIsRights(String isRights) {
		this.isRights = isRights;
	}

	public String getFillName() {
		return fillName;
	}

	public void setFillName(String fillName) {
		this.fillName = fillName;
	}

	public String getFillNameTel() {
		return fillNameTel;
	}

	public void setFillNameTel(String fillNameTel) {
		this.fillNameTel = fillNameTel;
	}
	
	public String getReviewName() {
		return reviewName;
	}

	public void setReviewName(String reviewName) {
		this.reviewName = reviewName;
	}

	public String getReviewNameTel() {
		return reviewNameTel;
	}

	public void setReviewNameTel(String reviewNameTel) {
		this.reviewNameTel = reviewNameTel;
	}

	public String getCheckName() {
		return checkName;
	}

	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}

	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getAreaCheck() {
		return areaCheck;
	}

	public void setAreaCheck(String areaCheck) {
		this.areaCheck = areaCheck;
	}

	public String getProvinceCheck() {
		return provinceCheck;
	}

	public void setProvinceCheck(String provinceCheck) {
		this.provinceCheck = provinceCheck;
	}

	public String getCentralCheck() {
		return centralCheck;
	}

	public void setCentralCheck(String centralCheck) {
		this.centralCheck = centralCheck;
	}
}
