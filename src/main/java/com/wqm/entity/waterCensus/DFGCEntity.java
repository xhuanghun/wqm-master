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
@Table(name = "BUSI_WC_DFGC")
public class DFGCEntity extends IdEntity {
	
	private static final long serialVersionUID = 1L;
	
	private String dfName; 		//提防名字
	private String dfCode;	 	//提防编码
	private String startProvince; 		//省	
	private String startCity;	 	//市区	
	private String startArea; 	//区	
	private String startCounty; 	//县	
	private String startStreet;  	//街道，村	
	private String startAreaCode;     //行政区
	private String endProvince; 		//省	
	private String endCity;	 	//市区	
	private String endArea; 	//区	
	private String endCounty; 	//县	
	private String endStreet;  	//街道，村	
	private String endAreaCode;     //行政区
	private String startEastLngDeg; 	//东经度	
	private String startEastLngMin; 	//东经分	
	private String startEastLngSec;  	//东经秒	
	private String startNorthLatDeg;     //北纬度
	private String startNorthLatMin; 		//北纬分	
	private String startNorthLatSec;	 	//北纬秒
	private String endEastLngDeg; 	//东经度	
	private String endEastLngMin; 	//东经分	
	private String endEastLngSec;  	//东经秒	
	private String endNorthLatDeg;     //北纬度
	private String endNorthLatMin; 		//北纬分	
	private String endNorthLatSec;	 	//北纬秒
	private String onWaterName; 	//所在河流名称	
	private String onWaterNameCode;  	//所在河流名称编码		
	private String riverBankType;	 	//河岸岸别
	private String isTransboundary;    //是否跨界
	private String type;	 	//提防类别
	private String format;     //提防形式
	private String workStatus;  	//工程建成情况	
	private String workYear;     //工程建成年
	private String workMonth; 		//工程建成月	
	private String startWorkYear;	 	//开工年	
	private String startWorkMonth; 	//开工月	
	private String workJob; 	//工程作用
	private String workGrade;     //工程级别
	private String dfTerm;		//规划防洪(潮)标准［重现期］（年）
	private String length;		//堤防长度(m)
	private String okLength;		//达到规划防洪（潮）标准的长度(m)
	private String heightSystem;		//高程系统
	private String heightStart;		//起点高程
	private String heightEnd;		//终点高程
	private String planHeight; 	//设计水位高多少	
	private String dfHeightMax; 	//提防高度最大
	private String dfHeightMin; 	//提防高度最小
	private String dfWidthMax; 	//提防宽度最大
	private String dfWidthMin; 	//提防宽度最小
	private String damNum;            //水闸数量
	private String pipeNum;            //管涵数量
	private String pumpsNum; 	//水泵数量
	private String nvertedSiphonNum;//倒虹吸数量
	private String dfManageArea;  	//泵站管理单位名称	
	private String dfManageAreaCode;     //泵站管理单位代码
	private String dfPortManageName; 		//泵站归口管理部门	
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
	public String getDfName() {
		return dfName;
	}

	public void setDfName(String dfName) {
		this.dfName = dfName;
	}

	public String getDfCode() {
		return dfCode;
	}

	public void setDfCode(String dfCode) {
		this.dfCode = dfCode;
	}

	public String getStartProvince() {
		return startProvince;
	}

	public void setStartProvince(String startProvince) {
		this.startProvince = startProvince;
	}

	public String getStartCity() {
		return startCity;
	}

	public void setStartCity(String startCity) {
		this.startCity = startCity;
	}

	public String getStartArea() {
		return startArea;
	}

	public void setStartArea(String startArea) {
		this.startArea = startArea;
	}

	public String getStartCounty() {
		return startCounty;
	}

	public void setStartCounty(String startCounty) {
		this.startCounty = startCounty;
	}

	public String getStartStreet() {
		return startStreet;
	}

	public void setStartStreet(String startStreet) {
		this.startStreet = startStreet;
	}

	public String getStartAreaCode() {
		return startAreaCode;
	}

	public void setStartAreaCode(String startAreaCode) {
		this.startAreaCode = startAreaCode;
	}

	public String getEndProvince() {
		return endProvince;
	}

	public void setEndProvince(String endProvince) {
		this.endProvince = endProvince;
	}

	public String getEndCity() {
		return endCity;
	}

	public void setEndCity(String endCity) {
		this.endCity = endCity;
	}

	public String getEndArea() {
		return endArea;
	}

	public void setEndArea(String endArea) {
		this.endArea = endArea;
	}

	public String getEndCounty() {
		return endCounty;
	}

	public void setEndCounty(String endCounty) {
		this.endCounty = endCounty;
	}

	public String getEndStreet() {
		return endStreet;
	}

	public void setEndStreet(String endStreet) {
		this.endStreet = endStreet;
	}

	public String getEndAreaCode() {
		return endAreaCode;
	}

	public void setEndAreaCode(String endAreaCode) {
		this.endAreaCode = endAreaCode;
	}

	public String getStartEastLngDeg() {
		return startEastLngDeg;
	}

	public void setStartEastLngDeg(String startEastLngDeg) {
		this.startEastLngDeg = startEastLngDeg;
	}

	public String getStartEastLngMin() {
		return startEastLngMin;
	}

	public void setStartEastLngMin(String startEastLngMin) {
		this.startEastLngMin = startEastLngMin;
	}

	public String getStartEastLngSec() {
		return startEastLngSec;
	}

	public void setStartEastLngSec(String startEastLngSec) {
		this.startEastLngSec = startEastLngSec;
	}

	public String getStartNorthLatDeg() {
		return startNorthLatDeg;
	}

	public void setStartNorthLatDeg(String startNorthLatDeg) {
		this.startNorthLatDeg = startNorthLatDeg;
	}

	public String getStartNorthLatMin() {
		return startNorthLatMin;
	}

	public void setStartNorthLatMin(String startNorthLatMin) {
		this.startNorthLatMin = startNorthLatMin;
	}

	public String getStartNorthLatSec() {
		return startNorthLatSec;
	}

	public void setStartNorthLatSec(String startNorthLatSec) {
		this.startNorthLatSec = startNorthLatSec;
	}

	public String getEndEastLngDeg() {
		return endEastLngDeg;
	}

	public void setEndEastLngDeg(String endEastLngDeg) {
		this.endEastLngDeg = endEastLngDeg;
	}

	public String getEndEastLngMin() {
		return endEastLngMin;
	}

	public void setEndEastLngMin(String endEastLngMin) {
		this.endEastLngMin = endEastLngMin;
	}

	public String getEndEastLngSec() {
		return endEastLngSec;
	}

	public void setEndEastLngSec(String endEastLngSec) {
		this.endEastLngSec = endEastLngSec;
	}

	public String getEndNorthLatDeg() {
		return endNorthLatDeg;
	}

	public void setEndNorthLatDeg(String endNorthLatDeg) {
		this.endNorthLatDeg = endNorthLatDeg;
	}

	public String getEndNorthLatMin() {
		return endNorthLatMin;
	}

	public void setEndNorthLatMin(String endNorthLatMin) {
		this.endNorthLatMin = endNorthLatMin;
	}

	public String getEndNorthLatSec() {
		return endNorthLatSec;
	}

	public void setEndNorthLatSec(String endNorthLatSec) {
		this.endNorthLatSec = endNorthLatSec;
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

	public String getRiverBankType() {
		return riverBankType;
	}

	public void setRiverBankType(String riverBankType) {
		this.riverBankType = riverBankType;
	}

	public String getIsTransboundary() {
		return isTransboundary;
	}

	public void setIsTransboundary(String isTransboundary) {
		this.isTransboundary = isTransboundary;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
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

	public String getWorkGrade() {
		return workGrade;
	}

	public void setWorkGrade(String workGrade) {
		this.workGrade = workGrade;
	}

	public String getDfTerm() {
		return dfTerm;
	}

	public void setDfTerm(String dfTerm) {
		this.dfTerm = dfTerm;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getOkLength() {
		return okLength;
	}

	public void setOkLength(String okLength) {
		this.okLength = okLength;
	}

	public String getHeightSystem() {
		return heightSystem;
	}

	public void setHeightSystem(String heightSystem) {
		this.heightSystem = heightSystem;
	}

	public String getHeightStart() {
		return heightStart;
	}

	public void setHeightStart(String heightStart) {
		this.heightStart = heightStart;
	}

	public String getHeightEnd() {
		return heightEnd;
	}

	public void setHeightEnd(String heightEnd) {
		this.heightEnd = heightEnd;
	}

	public String getPlanHeight() {
		return planHeight;
	}

	public void setPlanHeight(String planHeight) {
		this.planHeight = planHeight;
	}

	public String getDfHeightMax() {
		return dfHeightMax;
	}

	public void setDfHeightMax(String dfHeightMax) {
		this.dfHeightMax = dfHeightMax;
	}

	public String getDfHeightMin() {
		return dfHeightMin;
	}

	public void setDfHeightMin(String dfHeightMin) {
		this.dfHeightMin = dfHeightMin;
	}
	
	public String getDfWidthMax() {
		return dfWidthMax;
	}

	public void setDfWidthMax(String dfWidthMax) {
		this.dfWidthMax = dfWidthMax;
	}

	public String getDfWidthMin() {
		return dfWidthMin;
	}

	public void setDfWidthMin(String dfWidthMin) {
		this.dfWidthMin = dfWidthMin;
	}

	public String getDamNum() {
		return damNum;
	}

	public void setDamNum(String damNum) {
		this.damNum = damNum;
	}

	public String getPipeNum() {
		return pipeNum;
	}

	public void setPipeNum(String pipeNum) {
		this.pipeNum = pipeNum;
	}

	public String getPumpsNum() {
		return pumpsNum;
	}

	public void setPumpsNum(String pumpsNum) {
		this.pumpsNum = pumpsNum;
	}

	public String getNvertedSiphonNum() {
		return nvertedSiphonNum;
	}

	public void setNvertedSiphonNum(String nvertedSiphonNum) {
		this.nvertedSiphonNum = nvertedSiphonNum;
	}

	public String getDfManageArea() {
		return dfManageArea;
	}

	public void setDfManageArea(String dfManageArea) {
		this.dfManageArea = dfManageArea;
	}

	public String getDfManageAreaCode() {
		return dfManageAreaCode;
	}

	public void setDfManageAreaCode(String dfManageAreaCode) {
		this.dfManageAreaCode = dfManageAreaCode;
	}

	public String getDfPortManageName() {
		return dfPortManageName;
	}

	public void setDfPortManageName(String dfPortManageName) {
		this.dfPortManageName = dfPortManageName;
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
