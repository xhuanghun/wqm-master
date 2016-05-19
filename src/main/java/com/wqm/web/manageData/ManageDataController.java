package com.wqm.web.manageData;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wqm.common.persistence.SearchFilter.Operator;
import com.wqm.common.persistence.SpecificationFactory;
import com.wqm.common.tags.PageTag;
import com.wqm.entity.water.MonitorData;
import com.wqm.entity.water.MonitorDataNew;
import com.wqm.entity.water.WaterEntity;
import com.wqm.service.sys.UserService;
import com.wqm.service.water.AreaService;
import com.wqm.service.water.MonitorDataService;
import com.wqm.service.water.MonitorItemService;
import com.wqm.service.water.WaterService;
import com.wqm.web.BaseController;

/**
 * 系统-水体管理Controller
 * 
 * @author wangxj
 *
 */
@Controller
@RequestMapping(value = "/manageData")
public class ManageDataController extends BaseController{

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

	
	//private final static Logger logger = LoggerFactory.getLogger(ShowIndexController.class);
	
	/**
	 * 按父节点加载菜单，返回菜单树JSON
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/menu")
	public List<Map<String,Object>> getManageDataJson(@RequestParam(value = "id", defaultValue = "D0") String id){
		List<Map<String,Object>> tree =  new ArrayList<Map<String,Object>>();
		if(id.equals("D0")){
			for(int i=1;i<=10;i++){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id","D0"+i);
				Map<String,String> attribute = new HashMap<String,String>();
				if(i==1){
					map.put("text","水网动力和主要河流内容录入");
					attribute.put("openTab", "N");
					attribute.put("url", "/manageData/inputData");
				}else if(i==2){
					map.put("text","水网动力内容上传");
					attribute.put("url", "/manageData/inputDataExcel");
				}else if(i==3){
					map.put("text","污水厂数据录入");
					attribute.put("url", "/sewage/inputData");
				}else if(i==4){
					map.put("text","污水提升泵站数据录入");
					attribute.put("url", "/sewagePump/inputData");
				}else if(i==5){
					map.put("text","饮用水水源地数据录入");
					attribute.put("url", "/waterSource/inputData");
				}else if(i==6){
					map.put("text","自来水厂数据录入");
					attribute.put("url", "/tapWater/inputData");
				}else if(i==7){
					map.put("text","中型水库数据录入");
					attribute.put("url", "/mediumReservoir/inputData");
				}else if(i==8){
					map.put("text","黑臭水体数据录入");
					attribute.put("url", "/blackWater/inputData");
				}else if(i==9){
					map.put("text","供水网点数据录入");
					attribute.put("url", "/waterSupply/inputData");
				}else if(i==10){
					map.put("state","closed");
					map.put("text","<span class='menuTreeParent'>水利普查数据录入</span>");
					attribute.put("url", "/");
					attribute.put("openType", "IFRAME");
				}
				map.put("attributes", attribute);
				tree.add(map);
			}
		}else{
			for(int i=1;i<=7;i++){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id","D010"+i);
				map.put("state","open");
				Map<String,String> attribute = new HashMap<String,String>();
				attribute.put("openType", "HREF");
				if(i==1){
					map.put("text","泵站");
					attribute.put("url", "/waterCensus/BZinputDataExcel");
				}else if(i==2){
					map.put("text","堤防工程");
					attribute.put("url", "/waterCensus/DFGCinputDataExcel");
				}else if(i==3){
					map.put("text","农村供水工程");
					attribute.put("url", "/waterCensus/NCGSGCinputDataExcel");
				}else if(i==4){
					map.put("text","水电站");
					attribute.put("url", "/waterCensus/SDZinputDataExcel");
				}else if(i==5){
					map.put("text","水库");
					attribute.put("url", "/waterCensus/SKinputDataExcel");
				}else if(i==6){
					map.put("text","水闸");
					attribute.put("url", "/waterCensus/SZinputDataExcel");
				}else if(i==7){
					map.put("text","引调水工程");
					attribute.put("url", "/waterCensus/YDSGCinputDataExcel");
				}
				map.put("iconCls", "pic_7");
				map.put("attributes", attribute);
				tree.add(map);
			}
		}
		
		return tree;
	}
	/**
	 * 录入数据界面
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET,value="/inputData")
	public String inputData(){
		return "/manageData/inputData";
	}
	/**
	 * 录入数据界面
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET,value="/inputDataExcel")
	public String inputDataS(){
		return "/manageData/inputDataExcel";
	}
	/**
	 * 查询监点内容
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/getMonitorName")
	public PageTag getMonitorByUserId(HttpServletRequest request){
		//查找检测项		
		Integer page=Integer.valueOf(request.getParameter("page"));
		Integer pageRows=Integer.valueOf(request.getParameter("rows"));
		PageTag data= monitorItemService.getMonitorByUserId(page, pageRows);
		return data;
	}
	/**
	 * 查询监点内容
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/getMonitorEName")
	public List<Map<String,String>> getMonitorByCode(
			@RequestParam(value = "code", defaultValue = "") String code,
			@RequestParam(value = "monitorType", defaultValue = "") String monitorType,
			@RequestParam(value = "addOrupdate", defaultValue = "") String addOrupdate){
		//查找检测项		
		List<Map<String,String>> data= monitorItemService.getMonitorByCode(code,monitorType,addOrupdate);
		return data;
	}
	/**
	 * 查询监点内容
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/getMonitorSewage")
	public List<Map<String,String>> getMonitorSewageByCode(
			@RequestParam(value = "code", defaultValue = "") String code,
			@RequestParam(value = "monitorType", defaultValue = "") String monitorType,
			@RequestParam(value = "type", defaultValue = "") String type,
			@RequestParam(value = "addOrupdate", defaultValue = "") String addOrupdate){
		//查找检测项		
		List<Map<String,String>> data= monitorItemService.getMonitorSewageByCode(code, monitorType, type,addOrupdate);
		return data;
	}
	/**
	 * 分页查询污水处理厂监测数据
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/getMonitorDataNewList")
	public Map<String,Object> getMonitorDataNewList(HttpServletRequest request){
		//查询条件
		SpecificationFactory<MonitorDataNew> specf = new SpecificationFactory<MonitorDataNew>();
		specf.addSearchParam("waterName", Operator.LIKE, request.getParameter("name"));
		specf.addSearchParam("waterCode", Operator.LIKE, request.getParameter("code"));
		specf.addSearchParam("user.name", Operator.LIKE,  request.getParameter("userName"));
		//分页排序信息
		Page<MonitorDataNew> sewagesData= monitorDataService.getMonitorDatasNewByPage(specf.getSpecification(),buildPageRequest(request));
		return convertToResult(sewagesData);
	}
	/**
	 * 更改水体状态<br/>
	 * @param water
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public Map<String,Object>  CreateMonitorData(
			@RequestParam(value = "waterName", defaultValue = "") String waterName,
			@RequestParam(value = "waterCode", defaultValue = "") String waterCode,
			@RequestParam(value = "monitorContent", defaultValue = "") String monitorContent,
			@RequestParam(value = "monitorType", defaultValue = "") String monitorType,
			@RequestParam(value = "monitorTime", defaultValue = "") String monitorTime){
		Date date = new Date();
		String[] tem=monitorContent.split("##");
		String itemName="";
		String itemValue="";
		SimpleDateFormat sd= new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date monitortime = null;
		try {
			monitortime = sd.parse(monitorTime);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		try{
			for(int i=1;i<tem.length;i++){
				MonitorData md=new MonitorData();
				itemName=tem[i].substring(0,tem[i].indexOf("@@"));
				itemValue=tem[i].substring(tem[i].indexOf("@@")+2);
				md.setMonitorDate(monitortime);
				md.setCreateDate(date);
				md.setUpdateDate(date);
				md.setUser(this.getCurrentUser()); 
				md.setItemName(itemName);
				md.setItemValue(itemValue);
				md.setMonitorType(monitorType);
				WaterEntity water=new WaterEntity();
				water=waterService.getWaterByCode(waterCode);
				md.setWater(water);
				monitorDataService.saveMonitorData(md);
				md.setWaterName(waterName);
				water.setIsMonitored("Y");
				waterService.saveWater(water);
			}
		}catch(Exception e){
			e.printStackTrace();
			return convertToResult("message","录入数据失败");
		}
		return convertToResult("message","录入数据成功");
	}
	/**
	 * 删除监测数据
	 * @param water
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public Map<String,Object>  DeleteMonitorData(
			@RequestParam(value = "code", defaultValue = "") String code,
			@RequestParam(value = "monitorType", defaultValue = "") String monitorType){
		String [] Codearr = code.split(",");
		String [] MonitorTypearr = monitorType.split(",");
		try{
			Integer num=0;
			for(int i=0;i<Codearr.length;i++){
				num=monitorDataService.deleMonitorDataByCodeMonitorType(Codearr[i], MonitorTypearr[i]);
			}
			if(num>0){
				return convertToResult("message","删除成功");
			}else{
				return convertToResult("message","删除数据失败");
			}
		}catch(Exception e){
			return convertToResult("message","删除数据失败");
		}
	}
	/**
	 * 更新监测项数据<br/>
	 * @param water
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public Map<String,Object>  UpdateMonitorData(
			@RequestParam(value = "monitorContent", defaultValue = "") String monitorContent,
			@RequestParam(value = "monitorTime", defaultValue = "") String monitorTime){
		Date date = new Date();
		String[] tem=monitorContent.split("##");
		String itemName="";
		String itemValue="";
		Date monitortime = null;
		if(!"".equals(monitorTime)&&monitorTime!=null){
			SimpleDateFormat sd= new SimpleDateFormat("yyyy-MM-dd HH:mm");
			try {
				monitortime = sd.parse(monitorTime);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		}
		try{
			for(int i=1;i<tem.length;i++){
				itemName=tem[i].substring(0,tem[i].indexOf("@@"));
				itemValue=tem[i].substring(tem[i].indexOf("@@")+2);
				MonitorData md=monitorDataService.getMonitorDataById(Long.parseLong(itemName));
				md.setItemValue(itemValue);
				md.setUpdateDate(date);
				if(!"".equals(monitorTime)&&monitorTime!=null){
					md.setMonitorDate(monitortime);
				}
				monitorDataService.saveMonitorData(md);
			}
		}catch(Exception e){
			e.printStackTrace();
			return convertToResult("message","更新数据失败");
		}
		return convertToResult("message","更新数据成功");
	}
}
