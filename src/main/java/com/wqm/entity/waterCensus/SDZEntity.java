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
@Table(name = "BUSI_WC_SDZ")
public class SDZEntity extends IdEntity {
	
	private static final long serialVersionUID = 1L;	
	private String province; 		//省	
	private String city;	 	//市区	
	private String area; 	//	
	private String county; 	//县	
	private String areaCode;     //行政区
	private String sdzName; 		//水电站名字	
	private String sdzCode;	 	//水电站编码	
	private String eastLngDeg; 	//东经度	
	private String eastLngMin; 	//东经分	
	private String eastLngSec;  	//东经秒	
	private String northLatDeg;     //北纬度
	private String northLatMin; 		//北纬分	
	private String northLatSec;	 	//北纬秒	
	private String onWaterResourceNameCode; 	//所在水资源三级区名称及编码	
	private String onWaterName; 	//所在河流名称	
	private String onWaterNameCode;  	//所在河流名称编码	
	private String islectricity;      //是否利用水库发电
	private String reservoirName;     //水库名字
	private String reservoirCode;     //水库编号	
	private String type;	 	//类型
	private String productionPeople;   //生产安置人口
	private String movePeople;       //搬迁安置人口	
	private String workStatus;  	//工程建成情况	
	private String workYear;     //工程建成年
	private String workMonth; 		//工程建成月	
	private String startWorkYear;	 	//开工年	
	private String startWorkMonth; 	//开工月	
	private String workType;  	//工程类别	
	private String workGrade;     //工程级别
	private String capacity; 		//容量
	private String output;       //保证出力
	private String ratedHead;   //额定水头
	private String pumpsNum; 	//水泵数量
	private String averageElectricity ;     //平均发电
	private String yearElectricity ;     //2011发电	
	private String sdzManageArea;  	//泵站管理单位名称	
	private String sdzManageAreaCode;     //泵站管理单位代码
	private String institution;     //独立单位
	private String sdzPortManageName; 		//泵站归口管理部门	
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
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getSdzName() {
		return sdzName;
	}
	public void setSdzName(String sdzName) {
		this.sdzName = sdzName;
	}
	public String getSdzCode() {
		return sdzCode;
	}
	public void setSdzCode(String sdzCode) {
		this.sdzCode = sdzCode;
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
	public String getIslectricity() {
		return islectricity;
	}
	public void setIslectricity(String islectricity) {
		this.islectricity = islectricity;
	}
	public String getReservoirName() {
		return reservoirName;
	}
	public void setReservoirName(String reservoirName) {
		this.reservoirName = reservoirName;
	}
	public String getReservoirCode() {
		return reservoirCode;
	}
	public void setReservoirCode(String reservoirCode) {
		this.reservoirCode = reservoirCode;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getProductionPeople() {
		return productionPeople;
	}
	public void setProductionPeople(String productionPeople) {
		this.productionPeople = productionPeople;
	}
	public String getMovePeople() {
		return movePeople;
	}
	public void setMovePeople(String movePeople) {
		this.movePeople = movePeople;
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
	public String getOutput() {
		return output;
	}
	public void setOutput(String output) {
		this.output = output;
	}
	public String getRatedHead() {
		return ratedHead;
	}
	public void setRatedHead(String ratedHead) {
		this.ratedHead = ratedHead;
	}
	public String getPumpsNum() {
		return pumpsNum;
	}
	public void setPumpsNum(String pumpsNum) {
		this.pumpsNum = pumpsNum;
	}
	public String getAverageElectricity() {
		return averageElectricity;
	}
	public void setAverageElectricity(String averageElectricity) {
		this.averageElectricity = averageElectricity;
	}
	public String getYearElectricity() {
		return yearElectricity;
	}
	public void setYearElectricity(String yearElectricity) {
		this.yearElectricity = yearElectricity;
	}
	public String getSdzManageArea() {
		return sdzManageArea;
	}
	public void setSdzManageArea(String sdzManageArea) {
		this.sdzManageArea = sdzManageArea;
	}
	public String getSdzManageAreaCode() {
		return sdzManageAreaCode;
	}
	public void setSdzManageAreaCode(String sdzManageAreaCode) {
		this.sdzManageAreaCode = sdzManageAreaCode;
	}
	public String getInstitution() {
		return institution;
	}
	public void setInstitution(String institution) {
		this.institution = institution;
	}
	public String getSdzPortManageName() {
		return sdzPortManageName;
	}
	public void setSdzPortManageName(String sdzPortManageName) {
		this.sdzPortManageName = sdzPortManageName;
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
