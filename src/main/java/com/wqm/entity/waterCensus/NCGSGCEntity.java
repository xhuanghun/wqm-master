package com.wqm.entity.waterCensus;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.wqm.entity.IdEntity;

/**
 * 供水工程
 * @author wangxj
 *
 */
@Entity
@Table(name = "BUSI_WC_NCGSGC")
public class NCGSGCEntity extends IdEntity {
	
	private static final long serialVersionUID = 1L;	
	private String province; 		//省	
	private String city;	 	//市区	
	private String area; 	//区	
	private String county; 	//县	
	private String street;  	//街道，村	
	private String areaCode;     //行政区
	private String gsName; 		//泵站名字	
	private String gsCode;	 	//水体名称	
	private String eastLngDeg; 	//东经度	
	private String eastLngMin; 	//东经分	
	private String eastLngSec;  	//东经秒	
	private String northLatDeg;     //北纬度
	private String northLatMin; 		//北纬分	
	private String northLatSec;	 	//北纬秒
	private String getWaterType;	 	//取水类型
	private String isReservoir;	 	//是否水库水
	private String type;	 	//类型
	private String beneficiaryNum;   //受益村个数
	private String gsWay;     //供水方式	
	private String isWaterIntakingPermit; 	//是否有取水许可证	
	private String waterIntakingPermitCode; 	//取水许可证编号	
	private String isSanitaryPermit;  	//是否有卫生许可	
	private String sanitaryPermitCode;     //卫生许可编号
	private String beforeLenght; 		//入户前管网长度
	private String power;	 	//功率
	private String workStatus;  	//工程建成情况	
	private String workYear;     //工程建成年
	private String workMonth; 		//工程建成月	
	private String startWorkYear;	 	//开工年	
	private String startWorkMonth; 	//开工月
	private String planScale; 	//设计供水规模
	private String gsPopulationNum; 	//设计供水人口
	private String yearTotal; 	//年供水总量
	private String yearLiveTotal; 	//生活供水
	private String yearProductionTotal; 	//生产供水
	private String yearPopulationTotal; 	//年供水人数
	private String excessiveWaterQuality; 	//水质超标项目	
	private String waterPurification; 	//净水方式
	private String monitorEquipment; 	//水质监测设备	
	private String waterMonitorType; 	//水质监测方式	
	private String gsManageArea;  	//管理单位名称	
	private String gsManageAreaCode;     //管理单位代码
	private String gsPortManageName; 		//归口管理部门
	private String feeType; 			//收费方式
	private String feePrice; 			//收费价格
	private String feeYearPrice; 			//计量收费年实收水费（万元）
	private String fixedPrice; 				//固定收费执行居民生活水价（元/户·月）
	private String fixedYearPrice; 				//固定收费年实收水费（万元）	
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
	public String getGsName() {
		return gsName;
	}
	public void setGsName(String gsName) {
		this.gsName = gsName;
	}
	public String getGsCode() {
		return gsCode;
	}
	public void setGsCode(String gsCode) {
		this.gsCode = gsCode;
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
	public String getGetWaterType() {
		return getWaterType;
	}
	public void setGetWaterType(String getWaterType) {
		this.getWaterType = getWaterType;
	}
	
	public String getIsReservoir() {
		return isReservoir;
	}
	public void setIsReservoir(String isReservoir) {
		this.isReservoir = isReservoir;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBeneficiaryNum() {
		return beneficiaryNum;
	}
	public void setBeneficiaryNum(String beneficiaryNum) {
		this.beneficiaryNum = beneficiaryNum;
	}
	public String getGsWay() {
		return gsWay;
	}
	public void setGsWay(String gsWay) {
		this.gsWay = gsWay;
	}
	public String getIsWaterIntakingPermit() {
		return isWaterIntakingPermit;
	}
	public void setIsWaterIntakingPermit(String isWaterIntakingPermit) {
		this.isWaterIntakingPermit = isWaterIntakingPermit;
	}
	public String getWaterIntakingPermitCode() {
		return waterIntakingPermitCode;
	}
	public void setWaterIntakingPermitCode(String waterIntakingPermitCode) {
		this.waterIntakingPermitCode = waterIntakingPermitCode;
	}
	public String getIsSanitaryPermit() {
		return isSanitaryPermit;
	}
	public void setIsSanitaryPermit(String isSanitaryPermit) {
		this.isSanitaryPermit = isSanitaryPermit;
	}
	public String getSanitaryPermitCode() {
		return sanitaryPermitCode;
	}
	public void setSanitaryPermitCode(String sanitaryPermitCode) {
		this.sanitaryPermitCode = sanitaryPermitCode;
	}
	public String getBeforeLenght() {
		return beforeLenght;
	}
	public void setBeforeLenght(String beforeLenght) {
		this.beforeLenght = beforeLenght;
	}
	public String getPower() {
		return power;
	}
	public void setPower(String power) {
		this.power = power;
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
	public String getPlanScale() {
		return planScale;
	}
	public void setPlanScale(String planScale) {
		this.planScale = planScale;
	}
	public String getGsPopulationNum() {
		return gsPopulationNum;
	}
	public void setGsPopulationNum(String gsPopulationNum) {
		this.gsPopulationNum = gsPopulationNum;
	}
	public String getYearTotal() {
		return yearTotal;
	}
	public void setYearTotal(String yearTotal) {
		this.yearTotal = yearTotal;
	}
	public String getYearLiveTotal() {
		return yearLiveTotal;
	}
	public void setYearLiveTotal(String yearLiveTotal) {
		this.yearLiveTotal = yearLiveTotal;
	}
	public String getYearProductionTotal() {
		return yearProductionTotal;
	}
	public void setYearProductionTotal(String yearProductionTotal) {
		this.yearProductionTotal = yearProductionTotal;
	}
	public String getYearPopulationTotal() {
		return yearPopulationTotal;
	}
	public void setYearPopulationTotal(String yearPopulationTotal) {
		this.yearPopulationTotal = yearPopulationTotal;
	}
	public String getExcessiveWaterQuality() {
		return excessiveWaterQuality;
	}
	public void setExcessiveWaterQuality(String excessiveWaterQuality) {
		this.excessiveWaterQuality = excessiveWaterQuality;
	}
	public String getWaterPurification() {
		return waterPurification;
	}
	public void setWaterPurification(String waterPurification) {
		this.waterPurification = waterPurification;
	}
	public String getMonitorEquipment() {
		return monitorEquipment;
	}
	public void setMonitorEquipment(String monitorEquipment) {
		this.monitorEquipment = monitorEquipment;
	}
	public String getWaterMonitorType() {
		return waterMonitorType;
	}
	public void setWaterMonitorType(String waterMonitorType) {
		this.waterMonitorType = waterMonitorType;
	}
	public String getGsManageArea() {
		return gsManageArea;
	}
	public void setGsManageArea(String gsManageArea) {
		this.gsManageArea = gsManageArea;
	}
	public String getGsManageAreaCode() {
		return gsManageAreaCode;
	}
	public void setGsManageAreaCode(String gsManageAreaCode) {
		this.gsManageAreaCode = gsManageAreaCode;
	}
	public String getGsPortManageName() {
		return gsPortManageName;
	}
	public void setGsPortManageName(String gsPortManageName) {
		this.gsPortManageName = gsPortManageName;
	}
	public String getFeeType() {
		return feeType;
	}
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	public String getFeePrice() {
		return feePrice;
	}
	public void setFeePrice(String feePrice) {
		this.feePrice = feePrice;
	}
	public String getFeeYearPrice() {
		return feeYearPrice;
	}
	public void setFeeYearPrice(String feeYearPrice) {
		this.feeYearPrice = feeYearPrice;
	}
	public String getFixedPrice() {
		return fixedPrice;
	}
	public void setFixedPrice(String fixedPrice) {
		this.fixedPrice = fixedPrice;
	}
	public String getFixedYearPrice() {
		return fixedYearPrice;
	}
	public void setFixedYearPrice(String fixedYearPrice) {
		this.fixedYearPrice = fixedYearPrice;
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
