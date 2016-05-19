package com.wqm.web.water;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wqm.common.persistence.SearchFilter.Operator;
import com.wqm.common.persistence.SpecificationFactory;
import com.wqm.entity.water.MonitorDataNew;
import com.wqm.entity.water.MonitorItem;
import com.wqm.entity.water.SewageEntity;
import com.wqm.entity.water.SewageMonitorEntity;
import com.wqm.service.water.AreaService;
import com.wqm.service.water.MonitorDataService;
import com.wqm.service.water.MonitorItemService;
import com.wqm.service.water.SewageMonitorService;
import com.wqm.service.water.SewageService;
import com.wqm.web.BaseController;

/**
 * 系统-污水管理Controller
 * 
 * @author xuxz
 *
 */
@Controller
@RequestMapping(value = "/sewage")
public class SewageController extends BaseController{
	
	@Autowired
	private SewageService sewageService;
	@Autowired
	private AreaService areaService;
	@Autowired
	private SewageMonitorService sewageMonitorService;
	@Autowired
	private MonitorItemService monitorItemService;
	@Autowired
	private MonitorDataService monitorDataService;
	
	
	private final static Logger logger = LoggerFactory.getLogger(WaterController.class);
	/**
	 * 污水处理厂管理界面<br/>
	 * @param model
	 * @return
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET,value = "/sewageManager")
	public String  watersManager(Model model){		 
		return "/water/sewageManager";
	}
	/**
	 * 分页查询污水处理厂
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/getSewagePage")
	public Map<String,Object> getSewagePage(HttpServletRequest request){
		//查询条件
		SpecificationFactory<SewageEntity> specf = new SpecificationFactory<SewageEntity>();
		specf.addSearchParam("area.code", Operator.EQ, "0".equals(request.getParameter("areaCode"))?"":request.getParameter("areaCode"));
		specf.addSearchParam("name", Operator.LIKE, request.getParameter("name"));
		specf.addSearchParam("code", Operator.LIKE, request.getParameter("sewageCode"));
		specf.addSearchParam("user.name", Operator.LIKE,  request.getParameter("userName"));
		//分页排序信息
		Page<SewageEntity> sewages= sewageService.getSewagesByPage(specf.getSpecification(),buildPageRequest(request));
		return convertToResult(sewages);
	}
	/**
	 * 创建污水厂<br/>
	 * @param water
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public Map<String,Object>  createWater(@Valid SewageEntity sewage,@RequestParam(value = "areaCode", defaultValue = "0") String areaCode){
		Date date = new Date();		
		sewage.setCreateDate(date);
		sewage.setUpdateDate(date);
		sewage.setUser(this.getCurrentUser()); 
		sewage.setArea(areaService.getAreaByCode(areaCode));
		sewageService.saveSewage(sewage);
		return convertToResult("message","新增成功");
	}
	
	/**
	 * 更新更新污水厂<br/>
	 * 权限编码 007001003
	 * @param sewage
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@RequiresPermissions("007001003")
	public Map<String,Object>  updateWater(@Valid @ModelAttribute("sewage") SewageEntity sewage
			,@RequestParam(value = "areaCode", defaultValue = "0") String areaCode){
		//sewage=sewageService.getSewageById(sewage.getId());//暂时先这么弄，不知道为啥SewageEntity这个实体没有数据
		Date date = new Date();
		sewage.setUpdateDate(date);
		sewage.setUser(this.getCurrentUser()); 
		sewage.setArea(areaService.getAreaByCode(areaCode));
		sewageService.saveSewage(sewage);
		return convertToResult("message","更新成功");
	}
	/**
	 *  删除处理厂 <br/>
	 * @param request
	 * @return Map<String,Object>
	 */
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public Map<String,Object>  deleWaters(@Valid String ids){
		String [] idarr = ids.split(",");
		List<Long> idlist = new ArrayList<Long>();
		for(String id : idarr){
			idlist.add(Long.valueOf(id));
		}
		sewageService.deleSewage(idlist);
		return convertToResult("message","删除成功");
	}
	/**
	 * 二次绑定效果： 即从数据库里先根据ID查出实体再与前台传来的部分属性绑定  
	 * 主要用于update 	
	 * 通用   在使用时加上 @ModelAttribute("water") 注解
	 * @param id
	 * @param model
	 */
	@ModelAttribute
	public void getSewage(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("sewage", sewageService.getSewagesById(id));
		}
	}
	/**
	 * 获取污水厂全部监测项
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/getSewageMonitorItemList")
	public List<MonitorItem> getSewageMonitorItemList(@RequestParam(value = "id", defaultValue = "0") Long id
			,@RequestParam(value = "monitorType", defaultValue = "0") String monitorType
			,@RequestParam(value = "type", defaultValue = "0") String type){
		SewageEntity sewage = sewageService.getSewagesById(id);
		List<MonitorItem> items = new ArrayList<MonitorItem>();
		List<SewageMonitorEntity> typedata=sewageMonitorService.getSewageMonitorDataByCode(String.valueOf(sewage.getCode()),monitorType,type);
		for(SewageMonitorEntity tag:typedata){
			MonitorItem mt=new MonitorItem();
			mt=monitorItemService.getMonitorItemByCode(tag.getMoniterItemCode());
			items.add(mt);
		}
		return items;
	}
	/**
	 * 保存监测项<br/>
	 * 权限编码 007001003
	 * @param water
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveMonitorItem", method = RequestMethod.POST)
	public Map<String,Object>  saveMonitorItem(@Valid @ModelAttribute("sewage") SewageEntity sewage
			,@RequestParam(value = "itemList", defaultValue = "") String itemList
			,@RequestParam(value = "MonitorType", defaultValue = "") String MonitorType
			,@RequestParam(value = "type", defaultValue = "") String type){
		//sewage=sewageService.getSewageById(sewage.getId());//暂时先这么弄，不知道为啥SewageEntity这个实体没有数据
		String sewageCode=sewage.getCode();
		List<String> list = new ArrayList<String>();
		list.toArray(itemList.split(","));
		sewageMonitorService.deleBySewageCodeMonitorType(sewageCode, MonitorType,type);
		for(String s:itemList.split(",")){
			MonitorItem item = monitorItemService.getMonitorItemByCode(s);
			String monitorCode=item.getCode();
			SewageMonitorEntity sewageMonitor=new SewageMonitorEntity();
			sewageMonitor.setSewageCode(sewageCode);
			sewageMonitor.setMoniterItemCode(monitorCode);
			sewageMonitor.setMonitorTypeCode(MonitorType);
			sewageMonitor.setTypeCode(type);
			sewageMonitorService.saveSewageMonitor(sewageMonitor);
		}	
		return convertToResult("message","更新成功");
	}
	/**
	 * 录入数据界面
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET,value="/inputData")
	public String inputData(){
		return "/manageData/sewageData";
	}
	/**
	 * 获取全部污水厂<br/>
	 * @param sewage
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/sewageList", method = RequestMethod.GET)
	public  List<SewageEntity>  getSewageList(){
		 List<SewageEntity> data =sewageService.getAllSewages();
		return data;
	}
	/**
	 * 保存监测数据<br/>
	 * @param water
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/createmonitor", method = RequestMethod.GET)
	public Map<String,Object>  CreateMonitorData(
			@RequestParam(value = "sewageName", defaultValue = "") String sewageName,
			@RequestParam(value = "sewageCode", defaultValue = "") String sewageCode,
			@RequestParam(value = "monitorContent", defaultValue = "") String monitorContent,
			@RequestParam(value = "type", defaultValue = "0") Integer type,
			@RequestParam(value = "monitorType", defaultValue = "0") Integer monitorType,
			@RequestParam(value = "monitorTime", defaultValue = "") String monitorTime){
		SimpleDateFormat sd= new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date monitortime = null;
		try {
			monitortime = sd.parse(monitorTime);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		Date date = new Date();
		String[] tem=monitorContent.split("##");
		String itemName="";
		String itemValue="";
		try{
			for(int i=1;i<tem.length;i++){
				MonitorDataNew md=new MonitorDataNew();
				itemName=tem[i].substring(0,tem[i].indexOf("@@"));
				itemValue=tem[i].substring(tem[i].indexOf("@@")+2);
				md.setMonitorDate(monitortime);
				md.setCreateDate(date);
				md.setUpdateDate(date);
				md.setUser(this.getCurrentUser()); 
				md.setItemName(itemName);
				md.setItemValue(itemValue);
				md.setWaterName(sewageName);
				md.setType(type);
				md.setMonitorType(monitorType);
				md.setWaterCode(sewageCode);
				monitorDataService.saveMonitorDataNew(md);
			}
		}catch(Exception e){
			e.printStackTrace();
			return convertToResult("message","录入数据失败");
		}
		return convertToResult("message","录入数据成功");
	}
	/**
	 * 更新监测项数据<br/>
	 * @param water
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updatemonitor", method = RequestMethod.GET)
	public Map<String,Object>  UpdateMonitorData(
			@RequestParam(value = "monitorContent", defaultValue = "") String monitorContent,
			@RequestParam(value = "monitorTime", defaultValue = "") String monitorTime){
		Date date = new Date();
		String[] tem=monitorContent.split("##");
		String id="";
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
				id=tem[i].substring(0,tem[i].indexOf("@@"));
				itemValue=tem[i].substring(tem[i].indexOf("@@")+2);
				MonitorDataNew md=monitorDataService.getMonitorDataNewById(Long.parseLong(id));
				if(!"".equals(monitorTime)&&monitorTime!=null){
					md.setMonitorDate(monitortime);
				}
				md.setItemValue(itemValue);
				md.setUpdateDate(date);
				monitorDataService.saveMonitorDataNew(md);
			}
		}catch(Exception e){
			e.printStackTrace();
			return convertToResult("message","更新数据失败");
		}
		return convertToResult("message","更新数据成功");
	}
	/**
	 * 删除监测数据
	 * @param water
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deletemonitor", method = RequestMethod.GET)
	public Map<String,Object>  DeleteMonitorData(
			@RequestParam(value = "code", defaultValue = "[]") String code,
			@RequestParam(value = "type", defaultValue = "[]") String type,
			@RequestParam(value = "monitorType", defaultValue = "[]") String monitorType){
		String [] Codearr = code.split(",");
		String [] Typearr = type.split(",");
		String [] MonitorTypearr = monitorType.split(",");
		try{
			for(int i=0;i<Codearr.length;i++){
				 monitorDataService.deleMonitorDataNew(Codearr[i], Integer.parseInt(Typearr[i]), Integer.parseInt(MonitorTypearr[i]));
			}
		}catch(Exception e){
			return convertToResult("message","删除数据失败");
		}
		return convertToResult("message","删除数据成功");
	}
	/**
	 * 污水处理厂详细信息界面<br/>
	 * @param model
	 * @return
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET,value = "/sewageDetail")
	public String  sewageDetail(Model model,@RequestParam(value = "code", defaultValue = "") String code){
			SewageEntity sewage=sewageService.getSewageByCode(code);
			model.addAttribute("code", code);
			model.addAttribute("sewage", sewage);
			return "/water/sewageDetail";
	}
	/**
	 * 获取采样时间
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/getMonitorTime")
	public List<Map<String,String>> getMonitorTimeList(@RequestParam(value = "code", defaultValue = "") String code){
		List<Date> data = monitorDataService.getMonitorTimeBySewageCode(code);	
		List<Map<String,String>> items=new ArrayList<Map<String,String>>();
		SimpleDateFormat sd= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int i=1;
		for(Date tag:data){
			Map<String,String> map=new HashMap<String, String>();
			map.put("time", sd.format(tag));
			map.put("id", String.valueOf(i));
			i++;
			items.add(map);
		}
		return items;
	}
	/**
	 * 通过时间获取数据
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/getMonitorData")
	public List<MonitorDataNew> getMonitorData(@RequestParam(value = "code", defaultValue = "") String code,
			@RequestParam(value = "monitorTime", defaultValue = "") String monitorTime){
		List<MonitorDataNew> items=new ArrayList<MonitorDataNew>();
		if("".equals(monitorTime)){
			List<Date> data = monitorDataService.getMonitorTimeBySewageCode(code);
			if(data.size()>0){
				items=monitorDataService.getMonitorDataNewByMonitorTime(data.get(0),code);
			}
		}else{
			Date monitortime = null;
			if(!"".equals(monitorTime)&&monitorTime!=null){
				SimpleDateFormat sd= new SimpleDateFormat("yyyy-MM-dd HH:mm");
				try {
					monitortime = sd.parse(monitorTime);
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			}
			items=monitorDataService.getMonitorDataNewByMonitorTime(monitortime,code);
		}
		return items;
	}
}
