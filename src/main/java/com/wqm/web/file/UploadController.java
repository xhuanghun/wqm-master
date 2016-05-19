package com.wqm.web.file;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wqm.common.util.FileUtil;
import com.wqm.entity.water.MonitorData;
import com.wqm.entity.water.WaterEntity;
import com.wqm.entity.waterCensus.BZEntity;
import com.wqm.entity.waterCensus.DFGCEntity;
import com.wqm.entity.waterCensus.NCGSGCEntity;
import com.wqm.entity.waterCensus.SDZEntity;
import com.wqm.entity.waterCensus.SKEntity;
import com.wqm.entity.waterCensus.SZEntity;
import com.wqm.entity.waterCensus.YDSGCEntity;
import com.wqm.service.sys.UserService;
import com.wqm.service.water.AreaService;
import com.wqm.service.water.MonitorDataService;
import com.wqm.service.water.MonitorItemService;
import com.wqm.service.water.WaterService;
import com.wqm.service.waterCensus.BZService;
import com.wqm.service.waterCensus.DFGCService;
import com.wqm.service.waterCensus.NCGSGCService;
import com.wqm.service.waterCensus.SDZService;
import com.wqm.service.waterCensus.SKService;
import com.wqm.service.waterCensus.SZService;
import com.wqm.service.waterCensus.YDSGCService;
import com.wqm.web.BaseController;

/**
 * 系统-水体管理Controller
 * 
 * @author wangxj
 *
 */
@Controller
@RequestMapping(value = "/file")
public class UploadController extends BaseController{

	@Autowired
	private WaterService waterService;
	
	@Autowired
	private AreaService areaService;
	
	@Autowired
	private MonitorDataService monitorDataService;
	
	@Autowired
	private MonitorItemService monitorItemService;
	
	@Autowired
	public UserService userService;
	@Autowired
	public BZService bZService;
	@Autowired
	public DFGCService dfService;
	@Autowired
	public NCGSGCService gsService;
	@Autowired
	public SDZService sdzService;
	@Autowired
	public SKService skService;
	@Autowired
	public SZService szService;
	@Autowired
	public YDSGCService ydsService;
	//private final static Logger logger = LoggerFactory.getLogger(ShowIndexController.class);
	
	/**
	 * 上传文件
	 * @param id
	 * @return 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST,value = "/uploadFile")
	public Map<String, Object> getUploadFile(HttpServletRequest request){
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		try{
			if(isMultipart){
	        	FileItemFactory factory = new DiskFileItemFactory();
	            ServletFileUpload upload = new ServletFileUpload(factory);
	            List<FileItem> items = upload.parseRequest(request);
	            Iterator<FileItem> iter = items.iterator();
	            String username="";
	            while (iter.hasNext()) {
	            	FileItem item = (FileItem) iter.next();
	            	if (item.isFormField()) {//获取表单里面的数据
                    	if ("username".equals(item.getFieldName())) {
                    		try {
                    			username = item.getString();
                    		} catch (Exception e) {
                    			username = "2";
                    		}
                    	}
	            	}else{
	            		String fileName = item.getName();
	            		long sizeInBytes = item.getSize();
	            		 if (!"".equals(fileName) && sizeInBytes != 0) {
	            			 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	            			 Date date = new Date();
	            			 Workbook wb = WorkbookFactory.create(item.getInputStream());
	            			 Sheet sheet = wb.getSheetAt(0);//第一个工作表
	            			 Row row = sheet.getRow(1);//声明行
	                         Cell cell = row.getCell(1);//声明单元格
	                         int coloumNum=sheet.getRow(6).getPhysicalNumberOfCells();//获得第七行的列数
	                         int rowNum=sheet.getLastRowNum();//获得行数
	                         for (int i=1; i<=coloumNum; i++) {
                 				 WaterEntity water=new WaterEntity();
	                        	 cell = sheet.getRow(6).getCell(i);
	                        	 String water_name = FileUtil.getStringFromCell(cell, "");
                 				 water=waterService.getWaterByName(water_name);
                 				 if(water==null){
                 					 continue;
	                        		 //return convertToResult("message","上传失败,理由：第"+(i+1)+"列第7行断面名称不正确，请填写系统存在的断面名称");  
                 				 }
	                        	 cell = sheet.getRow(7).getCell(i);
	                        	 String monitorPoint = FileUtil.getStringFromCell(cell, "");
	                        	 cell = sheet.getRow(8).getCell(i);
	                        	 //String monitor_date = FileUtil.getStringFromCell(cell, "");
	                        	 try{
	                        		// format.parse(monitor_date);
	                        	 }catch(Exception e){
	                        		 return convertToResult("message","上传失败,理由：第"+(i+1)+"列第8行格式不正确，正确格式是“2016-03-11 16:41:03”"); 
	                        	 }
	                        	 for(int r=9;r<=rowNum-1;r++){
	                        		 String item_name="";
	                        		 try{
		                        		 MonitorData md=new MonitorData();
		                        		 cell = sheet.getRow(r).getCell(i);
		                        		 item_name= FileUtil.getStringFromCell(sheet.getRow(r).getCell(0), "");//监测项名字
		                        		 String item_value=FileUtil.getStringFromCell(cell, "");//监测项数据
		                        		 //md.setMonitorDate(format.parse(monitor_date));
		                 				 md.setCreateDate(date);
		                 				 md.setUpdateDate(date);
		                 				 md.setUser(this.getCurrentUser()); 
		                 				 md.setItemName(item_name);
		                 				 md.setItemValue(item_value);
		                 				 md.setWater(water);
		                 				 md.setMonitorPoint(monitorPoint);
			                 			 monitorDataService.saveMonitorData(md);
			                 			 md.setWaterName(water_name);
			                 			 water.setIsMonitored("Y");
			                 			 waterService.saveWater(water);
	                        		 }catch(Exception e){
	                        			 return convertToResult("message","上传失败,理由：第"+(i+1)+"列第"+(r+1)+"行格式不正确，字段名:“"+item_name+"”,只能填写数字和小数点");
	                        		 }
	                        	 }
	                         }
	            		 }
	            	}
	            }
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return convertToResult("message","提交成功");
	}
	/**
	 * 上传文件水利普查泵站
	 * @param id
	 * @return 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST,value = "/waterCensusBZ")
	public Map<String, Object> getWaterCensusBZ(HttpServletRequest request){
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		try{
			if(isMultipart){
	        	FileItemFactory factory = new DiskFileItemFactory();
	            ServletFileUpload upload = new ServletFileUpload(factory);
	            List<FileItem> items = upload.parseRequest(request);
	            Iterator<FileItem> iter = items.iterator();
	            //String username="";
	            while (iter.hasNext()) {
	            	FileItem item = (FileItem) iter.next();
	            	if (item.isFormField()) {//获取表单里面的数据
/*                    	if ("username".equals(item.getFieldName())) {
                    		try {
                    			username = item.getString();
                    		} catch (Exception e) {
                    			username = "2";
                    		}
                    	}*/
	            	}else{
	            		String fileName = item.getName();
	            		long sizeInBytes = item.getSize();
	            		 if (!"".equals(fileName) && sizeInBytes != 0) {
	            			 Workbook wb = WorkbookFactory.create(item.getInputStream());
	            			 Sheet sheet = wb.getSheetAt(0);//第一个工作表
	            			 Row row = sheet.getRow(1);//声明行
	                         Cell cell = row.getCell(1);//声明单元格
	                         int coloumNum=sheet.getRow(0).getPhysicalNumberOfCells();//获得第一行的列数
	                         int rowNum=sheet.getLastRowNum();//获得行数
	                         for (int r=1; r<=rowNum; r++) {
	                        	 BZEntity bz=new BZEntity();
	                        	for(int c=1;c<=coloumNum;c++){
	                        		 cell = sheet.getRow(r).getCell(c);
		                        	 String tmp = FileUtil.getStringFromCell(cell, "");
		                        	 try{
		                        		 switch(c){
		                        		 	case 1:bz.setProvince(tmp);break;
		                        		 	case 2:bz.setCity(tmp);break;
		                        		 	case 3:bz.setArea(tmp);break;
		                        		 	case 4:bz.setCounty(tmp);break;
		                        		 	case 5:bz.setStreet(tmp);break;
		                        		 	case 6:bz.setAreaCode(tmp);break;
		                        		 	case 7:bz.setBzName(tmp);break;
		                        		 	case 8:bz.setBzCode(tmp);break;
		                        		 	case 9:bz.setEastLngDeg(tmp);break;
		                        		 	case 10:bz.setEastLngMin(tmp);break;
		                        		 	case 11:bz.setEastLngSec(tmp);break;
		                        		 	case 12:bz.setNorthLatDeg(tmp);break;
		                        		 	case 13:bz.setNorthLatMin(tmp);break;
		                        		 	case 14:bz.setNorthLatSec(tmp);break;
		                        		 	case 15:bz.setOnWaterResourceNameCode(tmp);break;
		                        		 	case 16:bz.setOnWaterName(tmp);break;
		                        		 	case 17:bz.setOnWaterNameCode(tmp);break;
		                        		 	case 18:bz.setOnIrrigationName(tmp);break;
		                        		 	case 19:bz.setOnIrrigationNameCode(tmp);break;
		                        		 	case 20:bz.setType(tmp);break;
		                        		 	case 21:bz.setIsJWork(tmp);break;
		                        		 	case 22:bz.setIsQWork(tmp);break;
		                        		 	case 23:bz.setWorkStatus(tmp);break;
		                        		 	case 24:bz.setWorkYear(tmp);break;
		                        		 	case 25:bz.setWorkMonth(tmp);break;
		                        		 	case 26:bz.setStartWorkYear(tmp);break;
		                        		 	case 27:bz.setStartWorkMonth(tmp);break;
		                        		 	case 28:bz.setWorkJob(tmp);break;
		                        		 	case 29:bz.setWorkType(tmp);break;
		                        		 	case 30:bz.setWorkGrade(tmp);break;
		                        		 	case 31:bz.setCapacity(tmp);break;
		                        		 	case 32:bz.setPower(tmp);break;
		                        		 	case 33:bz.setPlanDistance(tmp);break;
		                        		 	case 34:bz.setPumpsNum(tmp);break;
		                        		 	case 35:bz.setBzManageArea(tmp);break;
		                        		 	case 36:bz.setBzManageAreaCode(tmp);break;
		                        		 	case 37:bz.setBzPortManageName(tmp);break;
		                        		 	case 38:bz.setIsDivide(tmp);break;
		                        		 	case 39:bz.setIsRights(tmp);break;
		                        		 	case 40:bz.setFillName(tmp);break;
		                        		 	case 41:bz.setFillNameTel(tmp);break;
		                        		 	case 42:bz.setReviewName(tmp);break;
		                        		 	case 43:bz.setReviewNameTel(tmp);break;	                        		 	
		                        		 	case 44:bz.setCheckName(tmp);break;
		                        		 	case 45:bz.setCheckStatus(tmp);break;
		                        		 	case 46:bz.setAreaCheck(tmp);break;
		                        		 	case 47:bz.setProvinceCheck(tmp);break;
		                        		 	case 48:bz.setCentralCheck(tmp);break;
		                        		 }
		                        	 }catch(Exception e){
		                        		 return convertToResult("message","提交失败"); 
		                        	 }
	                        	}
	                        	bZService.saveBZData(bz);
	                         }
	            		 }
	            	}
	            }
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return convertToResult("message","提交成功");
	}
	/**
	 * 上传文件水利普查堤防工程
	 * @param id
	 * @return 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST,value = "/waterCensusDFGC")
	public Map<String, Object> getWaterCensusDFGC(HttpServletRequest request){
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		try{
			if(isMultipart){
	        	FileItemFactory factory = new DiskFileItemFactory();
	            ServletFileUpload upload = new ServletFileUpload(factory);
	            List<FileItem> items = upload.parseRequest(request);
	            Iterator<FileItem> iter = items.iterator();
	            //String username="";
	            while (iter.hasNext()) {
	            	FileItem item = (FileItem) iter.next();
	            	if (item.isFormField()) {//获取表单里面的数据
/*                    	if ("username".equals(item.getFieldName())) {
                    		try {
                    			username = item.getString();
                    		} catch (Exception e) {
                    			username = "2";
                    		}
                    	}*/
	            	}else{
	            		String fileName = item.getName();
	            		long sizeInBytes = item.getSize();
	            		 if (!"".equals(fileName) && sizeInBytes != 0) {
	            			 Workbook wb = WorkbookFactory.create(item.getInputStream());
	            			 Sheet sheet = wb.getSheetAt(0);//第一个工作表
	            			 Row row = sheet.getRow(1);//声明行
	                         Cell cell = row.getCell(1);//声明单元格
	                         int coloumNum=sheet.getRow(0).getPhysicalNumberOfCells();//获得第一行的列数
	                         int rowNum=sheet.getLastRowNum();//获得行数
	                         for (int r=1; r<=rowNum; r++) {
	                        	 DFGCEntity TF=new DFGCEntity();
	                        	for(int c=1;c<=coloumNum;c++){
	                        		 cell = sheet.getRow(r).getCell(c);
		                        	 String tmp = FileUtil.getStringFromCell(cell, "");
		                        	 try{
		                        		 switch(c){
		                        		 	case 1:TF.setDfName(tmp);break;
		                        		 	case 2:TF.setDfCode(tmp);break;
		                        		 	case 3:TF.setStartProvince(tmp);break;
		                        		 	case 4:TF.setStartCity(tmp);break;
		                        		 	case 5:TF.setStartArea(tmp);break;
		                        		 	case 6:TF.setStartCounty(tmp);break;
		                        		 	case 7:TF.setStartStreet(tmp);break;
		                        		 	case 8:TF.setStartAreaCode(tmp);break;
		                        		 	case 9:TF.setEndProvince(tmp);break;
		                        		 	case 10:TF.setEndCity(tmp);break;
		                        		 	case 11:TF.setEndArea(tmp);break;
		                        		 	case 12:TF.setEndCounty(tmp);break;
		                        		 	case 13:TF.setEndStreet(tmp);break;
		                        		 	case 14:TF.setEndAreaCode(tmp);break;
		                        		 	case 15:TF.setStartEastLngDeg(tmp);break;
		                        		 	case 16:TF.setStartEastLngMin(tmp);break;
		                        		 	case 17:TF.setStartEastLngSec(tmp);break;
		                        		 	case 18:TF.setStartNorthLatDeg(tmp);break;
		                        		 	case 19:TF.setStartNorthLatMin(tmp);break;
		                        		 	case 20:TF.setStartNorthLatSec(tmp);break;
		                        		 	case 21:TF.setEndEastLngDeg(tmp);break;
		                        		 	case 22:TF.setEndEastLngMin(tmp);break;
		                        		 	case 23:TF.setEndEastLngSec(tmp);break;
		                        		 	case 24:TF.setEndNorthLatDeg(tmp);break;
		                        		 	case 25:TF.setEndNorthLatMin(tmp);break;
		                        		 	case 26:TF.setEndNorthLatSec(tmp);break;
		                        		 	case 27:TF.setOnWaterName(tmp);break;
		                        		 	case 28:TF.setOnWaterNameCode(tmp);break;
		                        		 	case 29:TF.setRiverBankType(tmp);break;
		                        		 	case 30:TF.setIsTransboundary(tmp);break;
		                        		 	case 31:TF.setType(tmp);break;
		                        		 	case 32:TF.setFormat(tmp);break;
		                        		 	case 33:TF.setWorkStatus(tmp);break;
		                        		 	case 34:TF.setWorkYear(tmp);break;
		                        		 	case 35:TF.setWorkMonth(tmp);break;
		                        		 	case 36:TF.setStartWorkYear(tmp);break;
		                        		 	case 37:TF.setStartWorkMonth(tmp);break;	
		                        		 	case 38:TF.setWorkJob(tmp);break;
		                        		 	case 39:TF.setWorkGrade(tmp);break;
		                        		 	case 40:TF.setDfTerm(tmp);break;
		                        		 	case 41:TF.setLength(tmp);break;
		                        		 	case 42:TF.setOkLength(tmp);break;
		                        		 	case 43:TF.setHeightSystem(tmp);break;
		                        		 	case 44:TF.setHeightStart(tmp);break;
		                        		 	case 45:TF.setHeightEnd(tmp);break;
		                        		 	case 46:TF.setPlanHeight(tmp);break;
		                        		 	case 47:TF.setDfHeightMax(tmp);break;
		                        		 	case 48:TF.setDfHeightMin(tmp);break;
		                        		 	case 49:TF.setDfWidthMax(tmp);break;
		                        		 	case 50:TF.setDfWidthMin(tmp);break;	
		                        		 	case 51:TF.setDamNum(tmp);break;
		                        		 	case 52:TF.setPipeNum(tmp);break;
		                        		 	case 53:TF.setPumpsNum(tmp);break;
		                        		 	case 54:TF.setNvertedSiphonNum(tmp);break;		                        		 	
		                        		 	case 55:TF.setDfManageArea(tmp);break;
		                        		 	case 56:TF.setDfManageAreaCode(tmp);break;
		                        		 	case 57:TF.setDfPortManageName(tmp);break;		                        		 			                        		 			                        		 			                        		 			                        		 	
		                        		 	case 58:TF.setIsDivide(tmp);break;
		                        		 	case 59:TF.setIsRights(tmp);break;
		                        		 	case 60:TF.setFillName(tmp);break;
		                        		 	case 61:TF.setFillNameTel(tmp);break;
		                        		 	case 62:TF.setReviewName(tmp);break;
		                        		 	case 63:TF.setReviewNameTel(tmp);break;	                        		 	
		                        		 	case 64:TF.setCheckName(tmp);break;
		                        		 	case 65:TF.setCheckStatus(tmp);break;
		                        		 	case 66:TF.setAreaCheck(tmp);break;
		                        		 	case 67:TF.setProvinceCheck(tmp);break;
		                        		 	case 68:TF.setCentralCheck(tmp);break;
		                        		 }
		                        	 }catch(Exception e){
		                        		 return convertToResult("message","提交失败"); 
		                        	 }
	                        	}
	                        	dfService.saveDFData(TF);
	                         }
	            		 }
	            	}
	            }
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return convertToResult("message","提交成功");
	}
	/**
	 * 上传文件水利普查堤防工程
	 * @param id
	 * @return 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST,value = "/waterCensusNCGSGC")
	public Map<String, Object> getWaterCensusNCGSGC(HttpServletRequest request){
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		try{
			if(isMultipart){
	        	FileItemFactory factory = new DiskFileItemFactory();
	            ServletFileUpload upload = new ServletFileUpload(factory);
	            List<FileItem> items = upload.parseRequest(request);
	            Iterator<FileItem> iter = items.iterator();
	            //String username="";
	            while (iter.hasNext()) {
	            	FileItem item = (FileItem) iter.next();
	            	if (item.isFormField()) {//获取表单里面的数据
/*                    	if ("username".equals(item.getFieldName())) {
                    		try {
                    			username = item.getString();
                    		} catch (Exception e) {
                    			username = "2";
                    		}
                    	}*/
	            	}else{
	            		String fileName = item.getName();
	            		long sizeInBytes = item.getSize();
	            		 if (!"".equals(fileName) && sizeInBytes != 0) {
	            			 Workbook wb = WorkbookFactory.create(item.getInputStream());
	            			 Sheet sheet = wb.getSheetAt(0);//第一个工作表
	            			 Row row = sheet.getRow(1);//声明行
	                         Cell cell = row.getCell(1);//声明单元格
	                         int coloumNum=sheet.getRow(0).getPhysicalNumberOfCells();//获得第一行的列数
	                         int rowNum=sheet.getLastRowNum();//获得行数
	                         for (int r=1; r<=rowNum; r++) {
	                        	 NCGSGCEntity GS=new NCGSGCEntity();
	                        	for(int c=1;c<=coloumNum;c++){
	                        		 cell = sheet.getRow(r).getCell(c);
		                        	 String tmp = FileUtil.getStringFromCell(cell, "");
		                        	 try{
		                        		 switch(c){
		                        		 	case 1:GS.setProvince(tmp);break;
		                        		 	case 2:GS.setCity(tmp);break;
		                        		 	case 3:GS.setArea(tmp);break;
		                        		 	case 4:GS.setCounty(tmp);break;
		                        		 	case 5:GS.setStreet(tmp);break;
		                        		 	case 6:GS.setAreaCode(tmp);break;
		                        		 	case 7:GS.setGsName(tmp);break;
		                        		 	case 8:GS.setGsCode(tmp);break;
		                        		 	case 9:GS.setEastLngDeg(tmp);break;
		                        		 	case 10:GS.setEastLngMin(tmp);break;
		                        		 	case 11:GS.setEastLngSec(tmp);break;
		                        		 	case 12:GS.setNorthLatDeg(tmp);break;
		                        		 	case 13:GS.setNorthLatMin(tmp);break;
		                        		 	case 14:GS.setNorthLatSec(tmp);break;
		                        		 	case 15:GS.setGetWaterType(tmp);break;
		                        		 	case 16:GS.setIsReservoir(tmp);break;	
		                        		 	case 17:GS.setType(tmp);break;
		                        		 	case 18:GS.setBeneficiaryNum(tmp);break;
		                        		 	case 19:GS.setGsWay(tmp);break;
		                        		 	case 20:GS.setIsWaterIntakingPermit(tmp);break;
		                        		 	case 21:GS.setWaterIntakingPermitCode(tmp);break;
		                        		 	case 22:GS.setIsSanitaryPermit(tmp);break;
		                        		 	case 23:GS.setSanitaryPermitCode(tmp);break;
		                        		 	case 24:GS.setBeforeLenght(tmp);break;
		                        		 	case 25:GS.setPower(tmp);break;
		                        		 	case 26:GS.setWorkStatus(tmp);break;
		                        		 	case 27:GS.setWorkYear(tmp);break;
		                        		 	case 28:GS.setWorkMonth(tmp);break;
		                        		 	case 29:GS.setStartWorkYear(tmp);break;
		                        		 	case 30:GS.setStartWorkMonth(tmp);break;
		                        		 	case 31:GS.setPlanScale(tmp);break;
		                        		 	case 32:GS.setGsPopulationNum(tmp);break;
		                        		 	case 33:GS.setYearTotal(tmp);break;
		                        		 	case 34:GS.setYearLiveTotal(tmp);break;
		                        		 	case 35:GS.setYearProductionTotal(tmp);break;
		                        		 	case 36:GS.setYearPopulationTotal(tmp);break;
		                        		 	case 37:GS.setExcessiveWaterQuality(tmp);break;
		                        		 	case 38:GS.setWaterPurification(tmp);break;
		                        		 	case 39:GS.setMonitorEquipment(tmp);break;
		                        		 	case 40:GS.setWaterMonitorType(tmp);break;
		                        		 	case 41:GS.setGsManageArea(tmp);break;
		                        		 	case 42:GS.setGsManageAreaCode(tmp);break;
		                        		 	case 43:GS.setGsPortManageName(tmp);break;
		                        		 	case 44:GS.setFeeType(tmp);break;	                        		 	
		                        		 	case 45:GS.setFeePrice(tmp);break;
		                        		 	case 46:GS.setFeeYearPrice(tmp);break;
		                        		 	case 47:GS.setFixedPrice(tmp);break;
		                        		 	case 48:GS.setFixedYearPrice(tmp);break;
		                        		 	case 49:GS.setFillName(tmp);break;	
		                        		 	case 50:GS.setFillNameTel(tmp);break;
		                        		 	case 51:GS.setReviewName(tmp);break;
		                        		 	case 52:GS.setReviewNameTel(tmp);break;	                        		 	
		                        		 	case 53:GS.setCheckName(tmp);break;
		                        		 	case 54:GS.setCheckStatus(tmp);break;
		                        		 	case 55:GS.setAreaCheck(tmp);break;
		                        		 	case 56:GS.setProvinceCheck(tmp);break;
		                        		 	case 57:GS.setCentralCheck(tmp);break;	
		                        		 }
		                        	 }catch(Exception e){
		                        		 return convertToResult("message","提交失败"); 
		                        	 }
	                        	}
	                        	gsService.saveGSData(GS);
	                         }
	            		 }
	            	}
	            }
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return convertToResult("message","提交成功");
	}
	/**
	 * 上传文件水利普查泵站
	 * @param id
	 * @return 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST,value = "/waterCensusSDZ")
	public Map<String, Object> getWaterCensusSDZ(HttpServletRequest request){
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		try{
			if(isMultipart){
	        	FileItemFactory factory = new DiskFileItemFactory();
	            ServletFileUpload upload = new ServletFileUpload(factory);
	            List<FileItem> items = upload.parseRequest(request);
	            Iterator<FileItem> iter = items.iterator();
	            //String username="";
	            while (iter.hasNext()) {
	            	FileItem item = (FileItem) iter.next();
	            	if (item.isFormField()) {//获取表单里面的数据
/*                    	if ("username".equals(item.getFieldName())) {
                    		try {
                    			username = item.getString();
                    		} catch (Exception e) {
                    			username = "2";
                    		}
                    	}*/
	            	}else{
	            		String fileName = item.getName();
	            		long sizeInBytes = item.getSize();
	            		 if (!"".equals(fileName) && sizeInBytes != 0) {
	            			 Workbook wb = WorkbookFactory.create(item.getInputStream());
	            			 Sheet sheet = wb.getSheetAt(0);//第一个工作表
	            			 Row row = sheet.getRow(1);//声明行
	                         Cell cell = row.getCell(1);//声明单元格
	                         int coloumNum=sheet.getRow(0).getPhysicalNumberOfCells();//获得第一行的列数
	                         int rowNum=sheet.getLastRowNum();//获得行数
	                         for (int r=1; r<=rowNum; r++) {
	                        	 SDZEntity sdz=new SDZEntity();
	                        	for(int c=1;c<=coloumNum;c++){
	                        		 cell = sheet.getRow(r).getCell(c);
		                        	 String tmp = FileUtil.getStringFromCell(cell, "");
		                        	 try{
		                        		 switch(c){
		                        		 	case 1:sdz.setProvince(tmp);break;
		                        		 	case 2:sdz.setCity(tmp);break;
		                        		 	case 3:sdz.setArea(tmp);break;
		                        		 	case 4:sdz.setCounty(tmp);break;
		                        		 	case 5:sdz.setAreaCode(tmp);break;
		                        		 	case 6:sdz.setSdzName(tmp);break;
		                        		 	case 7:sdz.setSdzCode(tmp);break;
		                        		 	case 8:sdz.setEastLngDeg(tmp);break;
		                        		 	case 9:sdz.setEastLngMin(tmp);break;
		                        		 	case 10:sdz.setEastLngSec(tmp);break;
		                        		 	case 11:sdz.setNorthLatDeg(tmp);break;
		                        		 	case 12:sdz.setNorthLatMin(tmp);break;
		                        		 	case 13:sdz.setNorthLatSec(tmp);break;
		                        		 	case 14:sdz.setOnWaterResourceNameCode(tmp);break;
		                        		 	case 15:sdz.setOnWaterName(tmp);break;
		                        		 	case 16:sdz.setOnWaterNameCode(tmp);break;
		                        		 	case 17:sdz.setIslectricity(tmp);break;
		                        		 	case 18:sdz.setReservoirName(tmp);break;
		                        		 	case 19:sdz.setReservoirCode(tmp);break;
		                        		 	case 20:sdz.setType(tmp);break;
		                        		 	case 21:sdz.setProductionPeople(tmp);break;
		                        		 	case 22:sdz.setMovePeople(tmp);break;
		                        		 	case 23:sdz.setWorkStatus(tmp);break;
		                        		 	case 24:sdz.setWorkYear(tmp);break;
		                        		 	case 25:sdz.setWorkMonth(tmp);break;
		                        		 	case 26:sdz.setStartWorkYear(tmp);break;
		                        		 	case 27:sdz.setStartWorkMonth(tmp);break;
		                        		 	case 28:sdz.setWorkType(tmp);break;
		                        		 	case 29:sdz.setWorkGrade(tmp);break;
		                        		 	case 30:sdz.setCapacity(tmp);break;
		                        		 	case 31:sdz.setOutput(tmp);break;
		                        		 	case 32:sdz.setRatedHead(tmp);break;
		                        		 	case 33:sdz.setPumpsNum(tmp);break;		               
		                        		 	case 34:sdz.setAverageElectricity(tmp);break;
		                        		 	case 35:sdz.setYearElectricity(tmp);break;
		                        		 	case 36:sdz.setSdzManageArea(tmp);break;
		                        		 	case 37:sdz.setSdzManageAreaCode(tmp);break;
		                        		 	case 38:sdz.setInstitution(tmp);break;
		                        		 	case 39:sdz.setSdzPortManageName(tmp);break;
		                        		 	case 40:sdz.setIsDivide(tmp);break;
		                        		 	case 41:sdz.setIsRights(tmp);break;
		                        		 	case 42:sdz.setFillName(tmp);break;
		                        		 	case 43:sdz.setFillNameTel(tmp);break;
		                        		 	case 44:sdz.setReviewName(tmp);break;
		                        		 	case 45:sdz.setReviewNameTel(tmp);break;	                        		 	
		                        		 	case 46:sdz.setCheckName(tmp);break;
		                        		 	case 47:sdz.setCheckStatus(tmp);break;
		                        		 	case 48:sdz.setAreaCheck(tmp);break;
		                        		 	case 49:sdz.setProvinceCheck(tmp);break;
		                        		 	case 50:sdz.setCentralCheck(tmp);break;
		                        		 }
		                        	 }catch(Exception e){
		                        		 return convertToResult("message","提交失败"); 
		                        	 }
	                        	}
	                        	sdzService.saveSDZData(sdz);
	                         }
	            		 }
	            	}
	            }
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return convertToResult("message","提交成功");
	}
	/**
	 * 上传文件水利普查水库
	 * @param id
	 * @return 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST,value = "/waterCensusSK")
	public Map<String, Object> getWaterCensusSK(HttpServletRequest request){
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		try{
			if(isMultipart){
	        	FileItemFactory factory = new DiskFileItemFactory();
	            ServletFileUpload upload = new ServletFileUpload(factory);
	            List<FileItem> items = upload.parseRequest(request);
	            Iterator<FileItem> iter = items.iterator();
	            //String username="";
	            while (iter.hasNext()) {
	            	FileItem item = (FileItem) iter.next();
	            	if (item.isFormField()) {//获取表单里面的数据
/*                    	if ("username".equals(item.getFieldName())) {
                    		try {
                    			username = item.getString();
                    		} catch (Exception e) {
                    			username = "2";
                    		}
                    	}*/
	            	}else{
	            		String fileName = item.getName();
	            		long sizeInBytes = item.getSize();
	            		 if (!"".equals(fileName) && sizeInBytes != 0) {
	            			 Workbook wb = WorkbookFactory.create(item.getInputStream());
	            			 Sheet sheet = wb.getSheetAt(0);//第一个工作表
	            			 Row row = sheet.getRow(1);//声明行
	                         Cell cell = row.getCell(1);//声明单元格
	                         int coloumNum=sheet.getRow(0).getPhysicalNumberOfCells();//获得第一行的列数
	                         int rowNum=sheet.getLastRowNum();//获得行数
	                         for (int r=1; r<=rowNum; r++) {
	                        	 SKEntity sk=new SKEntity();
	                        	for(int c=1;c<=coloumNum;c++){
	                        		 cell = sheet.getRow(r).getCell(c);
		                        	 String tmp = FileUtil.getStringFromCell(cell, "");
		                        	 try{
		                        		 switch(c){
		                        		 	case 1:sk.setCell1(tmp);break;
		                        			case 2:sk.setCell2(tmp);break;
		                        			case 3:sk.setCell3(tmp);break;
		                        			case 4:sk.setCell4(tmp);break;
		                        			case 5:sk.setCell5(tmp);break;
		                        			case 6:sk.setSkName(tmp);break;
		                        			case 7:sk.setSkCode(tmp);break;
		                        			case 8:sk.setCell8(tmp);break;
		                        			case 9:sk.setCell9(tmp);break;
		                        			case 10:sk.setCell10(tmp);break;
		                        		 	case 11:sk.setCell11(tmp);break;
		                        			case 12:sk.setCell12(tmp);break;
		                        			case 13:sk.setCell13(tmp);break;
		                        			case 14:sk.setCell14(tmp);break;
		                        			case 15:sk.setCell15(tmp);break;
		                        			case 16:sk.setCell16(tmp);break;
		                        			case 17:sk.setCell17(tmp);break;
		                        			case 18:sk.setCell18(tmp);break;
		                        			case 19:sk.setCell19(tmp);break;
		                        			case 20:sk.setCell20(tmp);break;
		                        		 	case 21:sk.setCell21(tmp);break;
		                        			case 22:sk.setCell22(tmp);break;
		                        			case 23:sk.setCell23(tmp);break;
		                        			case 24:sk.setCell24(tmp);break;
		                        			case 25:sk.setCell25(tmp);break;
		                        			case 26:sk.setCell26(tmp);break;
		                        			case 27:sk.setCell27(tmp);break;
		                        			case 28:sk.setCell28(tmp);break;
		                        			case 29:sk.setCell29(tmp);break;
		                        			case 30:sk.setCell30(tmp);break;
		                        		 	case 31:sk.setCell31(tmp);break;
		                        			case 32:sk.setCell32(tmp);break;
		                        			case 33:sk.setCell33(tmp);break;
		                        			case 34:sk.setCell34(tmp);break;
		                        			case 35:sk.setCell35(tmp);break;
		                        			case 36:sk.setCell36(tmp);break;
		                        			case 37:sk.setCell37(tmp);break;
		                        			case 38:sk.setCell38(tmp);break;
		                        			case 39:sk.setCell39(tmp);break;
		                        			case 40:sk.setCell40(tmp);break;
		                        		 	case 41:sk.setCell41(tmp);break;
		                        			case 42:sk.setCell42(tmp);break;
		                        			case 43:sk.setCell43(tmp);break;
		                        			case 44:sk.setCell44(tmp);break;
		                        			case 45:sk.setCell45(tmp);break;
		                        			case 46:sk.setCell46(tmp);break;
		                        			case 47:sk.setCell47(tmp);break;
		                        			case 48:sk.setCell48(tmp);break;
		                        			case 49:sk.setCell49(tmp);break;
		                        			case 50:sk.setCell50(tmp);break;
		                        		 	case 51:sk.setCell51(tmp);break;
		                        			case 52:sk.setCell52(tmp);break;
		                        			case 53:sk.setCell53(tmp);break;
		                        			case 54:sk.setCell54(tmp);break;
		                        			case 55:sk.setCell55(tmp);break;
		                        			case 56:sk.setCell56(tmp);break;
		                        			case 57:sk.setCell57(tmp);break;
		                        			case 58:sk.setCell58(tmp);break;
		                        			case 59:sk.setCell59(tmp);break;
		                        			case 60:sk.setCell60(tmp);break;
		                        		 	case 61:sk.setCell61(tmp);break;
		                        			case 62:sk.setCell62(tmp);break;
		                        			case 63:sk.setCell63(tmp);break;
		                        			case 64:sk.setCell64(tmp);break;
		                        			case 65:sk.setCell65(tmp);break;
		                        			case 66:sk.setCell66(tmp);break;
		                        			case 67:sk.setCell67(tmp);break;
		                        			case 68:sk.setCell68(tmp);break;
		                        			case 69:sk.setCell69(tmp);break;
		                        			case 70:sk.setCell70(tmp);break;
		                        		 	case 71:sk.setCell71(tmp);break;
		                        			case 72:sk.setCell72(tmp);break;
		                        			case 73:sk.setCell73(tmp);break;
		                        			case 74:sk.setCell74(tmp);break;
		                        			case 75:sk.setCell75(tmp);break;
		                        			case 76:sk.setCell76(tmp);break;
		                        			case 77:sk.setCell77(tmp);break;
		                        			case 78:sk.setCell78(tmp);break;
		                        			case 79:sk.setCell79(tmp);break;
		                        			case 80:sk.setCell80(tmp);break;
		                        			case 81:sk.setCell81(tmp);break;
		                        		 }
		                        	 }catch(Exception e){
		                        		 return convertToResult("message","提交失败"); 
		                        	 }
	                        	}
	                        	skService.saveSKData(sk);
	                         }
	            		 }
	            	}
	            }
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return convertToResult("message","提交成功");
	}
	/**
	 * 上传文件水利普查水闸
	 * @param id
	 * @return 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST,value = "/waterCensusSZ")
	public Map<String, Object> getWaterCensusSZ(HttpServletRequest request){
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		try{
			if(isMultipart){
	        	FileItemFactory factory = new DiskFileItemFactory();
	            ServletFileUpload upload = new ServletFileUpload(factory);
	            List<FileItem> items = upload.parseRequest(request);
	            Iterator<FileItem> iter = items.iterator();
	            //String username="";
	            while (iter.hasNext()) {
	            	FileItem item = (FileItem) iter.next();
	            	if (item.isFormField()) {//获取表单里面的数据
/*                    	if ("username".equals(item.getFieldName())) {
                    		try {
                    			username = item.getString();
                    		} catch (Exception e) {
                    			username = "2";
                    		}
                    	}*/
	            	}else{
	            		String fileName = item.getName();
	            		long sizeInBytes = item.getSize();
	            		 if (!"".equals(fileName) && sizeInBytes != 0) {
	            			 Workbook wb = WorkbookFactory.create(item.getInputStream());
	            			 Sheet sheet = wb.getSheetAt(0);//第一个工作表
	            			 Row row = sheet.getRow(1);//声明行
	                         Cell cell = row.getCell(1);//声明单元格
	                         int coloumNum=sheet.getRow(0).getPhysicalNumberOfCells();//获得第一行的列数
	                         int rowNum=sheet.getLastRowNum();//获得行数
	                         for (int r=1; r<=rowNum; r++) {
	                        	 SZEntity sz=new SZEntity();
	                        	for(int c=1;c<=coloumNum;c++){
	                        		 cell = sheet.getRow(r).getCell(c);
		                        	 String tmp = FileUtil.getStringFromCell(cell, "");
		                        	 try{
		                        		 switch(c){
		                        		 	case 1:sz.setCell1(tmp);break;
		                        			case 2:sz.setCell2(tmp);break;
		                        			case 3:sz.setCell3(tmp);break;
		                        			case 4:sz.setCell4(tmp);break;
		                        			case 5:sz.setCell5(tmp);break;
		                        			case 6:sz.setCell6(tmp);break;
		                        			case 7:sz.setSzName(tmp);break;
		                        			case 8:sz.setSzCode(tmp);break;
		                        			case 9:sz.setCell9(tmp);break;
		                        			case 10:sz.setCell10(tmp);break;
		                        		 	case 11:sz.setCell11(tmp);break;
		                        			case 12:sz.setCell12(tmp);break;
		                        			case 13:sz.setCell13(tmp);break;
		                        			case 14:sz.setCell14(tmp);break;
		                        			case 15:sz.setCell15(tmp);break;
		                        			case 16:sz.setCell16(tmp);break;
		                        			case 17:sz.setCell17(tmp);break;
		                        			case 18:sz.setCell18(tmp);break;
		                        			case 19:sz.setCell19(tmp);break;
		                        			case 20:sz.setCell20(tmp);break;
		                        		 	case 21:sz.setCell21(tmp);break;
		                        			case 22:sz.setCell22(tmp);break;
		                        			case 23:sz.setCell23(tmp);break;
		                        			case 24:sz.setCell24(tmp);break;
		                        			case 25:sz.setCell25(tmp);break;
		                        			case 26:sz.setCell26(tmp);break;
		                        			case 27:sz.setCell27(tmp);break;
		                        			case 28:sz.setCell28(tmp);break;
		                        			case 29:sz.setCell29(tmp);break;
		                        			case 30:sz.setCell30(tmp);break;
		                        		 	case 31:sz.setCell31(tmp);break;
		                        			case 32:sz.setCell32(tmp);break;
		                        			case 33:sz.setCell33(tmp);break;
		                        			case 34:sz.setCell34(tmp);break;
		                        			case 35:sz.setCell35(tmp);break;
		                        			case 36:sz.setCell36(tmp);break;
		                        			case 37:sz.setCell37(tmp);break;
		                        			case 38:sz.setCell38(tmp);break;
		                        			case 39:sz.setCell39(tmp);break;
		                        			case 40:sz.setCell40(tmp);break;
		                        		 	case 41:sz.setCell41(tmp);break;
		                        			case 42:sz.setCell42(tmp);break;
		                        			case 43:sz.setCell43(tmp);break;
		                        			case 44:sz.setCell44(tmp);break;
		                        			case 45:sz.setCell45(tmp);break;
		                        			case 46:sz.setCell46(tmp);break;
		                        			case 47:sz.setCell47(tmp);break;
		                        			case 48:sz.setCell48(tmp);break;
		                        			case 49:sz.setCell49(tmp);break;
		                        			case 50:sz.setCell50(tmp);break;
		                        		 	case 51:sz.setCell51(tmp);break;
		                        			case 52:sz.setCell52(tmp);break;
		                        			case 53:sz.setCell53(tmp);break;
		                        			case 54:sz.setCell54(tmp);break;
		                        			case 55:sz.setCell55(tmp);break;
		                        			case 56:sz.setCell56(tmp);break;
		                        			case 57:sz.setCell57(tmp);break;
		                        			case 58:sz.setCell58(tmp);break;
		                        			case 59:sz.setCell59(tmp);break;
		                        			case 60:sz.setCell60(tmp);break;
		                        		 	case 61:sz.setCell61(tmp);break;
		                        			case 62:sz.setCell62(tmp);break;
		                        			case 63:sz.setCell63(tmp);break;
		                        			case 64:sz.setCell64(tmp);break;
		                        			case 65:sz.setCell65(tmp);break;
		                        			case 66:sz.setCell66(tmp);break;
		                        			case 67:sz.setCell67(tmp);break;
		                        			case 68:sz.setCell68(tmp);break;
		                        			case 69:sz.setCell69(tmp);break;
		                        			case 70:sz.setCell70(tmp);break;
		                        		 	case 71:sz.setCell71(tmp);break;
		                        		 }
		                        	 }catch(Exception e){
		                        		 return convertToResult("message","提交失败"); 
		                        	 }
	                        	}
	                        	szService.saveSZData(sz);
	                         }
	            		 }
	            	}
	            }
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return convertToResult("message","提交成功");
	}
	/**
	 * 上传文件水利普查水闸
	 * @param id
	 * @return 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST,value = "/waterCensusYDSGC")
	public Map<String, Object> getWaterCensusYDSGC(HttpServletRequest request){
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		try{
			if(isMultipart){
	        	FileItemFactory factory = new DiskFileItemFactory();
	            ServletFileUpload upload = new ServletFileUpload(factory);
	            List<FileItem> items = upload.parseRequest(request);
	            Iterator<FileItem> iter = items.iterator();
	            //String username="";
	            while (iter.hasNext()) {
	            	FileItem item = (FileItem) iter.next();
	            	if (item.isFormField()) {//获取表单里面的数据
/*                    	if ("username".equals(item.getFieldName())) {
                    		try {
                    			username = item.getString();
                    		} catch (Exception e) {
                    			username = "2";
                    		}
                    	}*/
	            	}else{
	            		String fileName = item.getName();
	            		long sizeInBytes = item.getSize();
	            		 if (!"".equals(fileName) && sizeInBytes != 0) {
	            			 Workbook wb = WorkbookFactory.create(item.getInputStream());
	            			 Sheet sheet = wb.getSheetAt(0);//第一个工作表
	            			 Row row = sheet.getRow(1);//声明行
	                         Cell cell = row.getCell(1);//声明单元格
	                         int coloumNum=sheet.getRow(0).getPhysicalNumberOfCells();//获得第一行的列数
	                         int rowNum=sheet.getLastRowNum();//获得行数
	                         for (int r=1; r<=rowNum; r++) {
	                        	 YDSGCEntity yds=new YDSGCEntity();
	                        	for(int c=1;c<=coloumNum;c++){
	                        		 cell = sheet.getRow(r).getCell(c);
		                        	 String tmp = FileUtil.getStringFromCell(cell, "");
		                        	 try{
		                        		 switch(c){
		                        		 	case 1:yds.setCell1(tmp);break;
		                        			case 2:yds.setCell2(tmp);break;
		                        			case 3:yds.setYdsName(tmp);break;
		                        			case 4:yds.setYdsCode(tmp);break;
		                        			case 5:yds.setCell5(tmp);break;
		                        			case 6:yds.setCell6(tmp);break;
		                        			case 7:yds.setCell7(tmp);break;
		                        			case 8:yds.setCell8(tmp);break;
		                        			case 9:yds.setCell9(tmp);break;
		                        			case 10:yds.setCell10(tmp);break;
		                        		 	case 11:yds.setCell11(tmp);break;
		                        			case 12:yds.setCell12(tmp);break;
		                        			case 13:yds.setCell13(tmp);break;
		                        			case 14:yds.setCell14(tmp);break;
		                        			case 15:yds.setCell15(tmp);break;
		                        			case 16:yds.setCell16(tmp);break;
		                        			case 17:yds.setCell17(tmp);break;
		                        			case 18:yds.setCell18(tmp);break;
		                        			case 19:yds.setCell19(tmp);break;
		                        			case 20:yds.setCell20(tmp);break;
		                        		 	case 21:yds.setCell21(tmp);break;
		                        			case 22:yds.setCell22(tmp);break;
		                        			case 23:yds.setCell23(tmp);break;
		                        			case 24:yds.setCell24(tmp);break;
		                        			case 25:yds.setCell25(tmp);break;
		                        			case 26:yds.setCell26(tmp);break;
		                        			case 27:yds.setCell27(tmp);break;
		                        			case 28:yds.setCell28(tmp);break;
		                        			case 29:yds.setCell29(tmp);break;
		                        			case 30:yds.setCell30(tmp);break;
		                        		 	case 31:yds.setCell31(tmp);break;
		                        			case 32:yds.setCell32(tmp);break;
		                        			case 33:yds.setCell33(tmp);break;
		                        			case 34:yds.setCell34(tmp);break;
		                        			case 35:yds.setCell35(tmp);break;
		                        			case 36:yds.setCell36(tmp);break;
		                        			case 37:yds.setCell37(tmp);break;
		                        			case 38:yds.setCell38(tmp);break;
		                        			case 39:yds.setCell39(tmp);break;
		                        			case 40:yds.setCell40(tmp);break;
		                        		 	case 41:yds.setCell41(tmp);break;
		                        			case 42:yds.setCell42(tmp);break;
		                        			case 43:yds.setCell43(tmp);break;
		                        			case 44:yds.setCell44(tmp);break;
		                        			case 45:yds.setCell45(tmp);break;
		                        			case 46:yds.setCell46(tmp);break;
		                        		 }
		                        	 }catch(Exception e){
		                        		 return convertToResult("message","提交失败"); 
		                        	 }
	                        	}
	                        	ydsService.saveYDSGCData(yds);
	                         }
	            		 }
	            	}
	            }
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return convertToResult("message","提交成功");
	}
}
