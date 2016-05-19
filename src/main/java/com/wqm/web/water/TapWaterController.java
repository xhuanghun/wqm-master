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
import com.wqm.entity.water.MonitorItem;
import com.wqm.entity.water.tapWater.MonitorDataTapWater;
import com.wqm.entity.water.tapWater.TapWaterEntity;
import com.wqm.entity.water.tapWater.TapWaterMonitorEntity;
import com.wqm.service.water.AreaService;
import com.wqm.service.water.MonitorItemService;
import com.wqm.service.water.TapWater.MonitorDataTapWaterService;
import com.wqm.service.water.TapWater.TapWaterMonitorService;
import com.wqm.service.water.TapWater.TapWaterService;
import com.wqm.web.BaseController;

/**
 * 系统-污水管理Controller
 * 
 * @author xuxz
 *
 */
@Controller
@RequestMapping(value = "/tapWater")
public class TapWaterController extends BaseController{
	@Autowired
	private TapWaterService tapWaterService;
	@Autowired
	private AreaService areaService;
	@Autowired
	private TapWaterMonitorService tapWaterMonitorService;
	@Autowired
	private MonitorItemService monitorItemService;
	@Autowired
	private MonitorDataTapWaterService monitorDataTapWaterService;
	
	/**
	 * 污水提升泵站管理界面<br/>
	 * @param model
	 * @return
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET,value = "/tapWaterManager")
	public String  watersManager(Model model){		 
		return "/water/tapWater/tapWaterManager";
	}
	/**
	 * 分页查询污水处理厂
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/getTapWaterPage")
	public Map<String,Object> getTapWaterPage(HttpServletRequest request){
		//查询条件
		SpecificationFactory<TapWaterEntity> specf = new SpecificationFactory<TapWaterEntity>();
		specf.addSearchParam("area.code", Operator.EQ, "0".equals(request.getParameter("areaCode"))?"":request.getParameter("areaCode"));
		specf.addSearchParam("name", Operator.LIKE, request.getParameter("name"));
		specf.addSearchParam("code", Operator.LIKE, request.getParameter("code"));
		specf.addSearchParam("user.name", Operator.LIKE,  request.getParameter("userName"));
		//分页排序信息
		Page<TapWaterEntity> sewages= tapWaterService.getTapWatersByPage(specf.getSpecification(),buildPageRequest(request));
		return convertToResult(sewages);
	}
	/**
	 * 创建提升泵站<br/>
	 * @param water
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public Map<String,Object>  createWater(@Valid TapWaterEntity tapWater,@RequestParam(value = "areaCode", defaultValue = "0") String areaCode){
		Date date = new Date();		
		tapWater.setCreateDate(date);
		tapWater.setUpdateDate(date);
		tapWater.setUser(this.getCurrentUser()); 
		tapWater.setArea(areaService.getAreaByCode(areaCode));
		tapWaterService.saveTapWater(tapWater);
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
	@RequiresPermissions("007001001")
	public Map<String,Object>  updateWater(@Valid @ModelAttribute("tapWater") TapWaterEntity tapWater
			,@RequestParam(value = "areaCode", defaultValue = "0") String areaCode){
		Date date = new Date();
		tapWater.setUpdateDate(date);
		tapWater.setUser(this.getCurrentUser()); 
		tapWater.setArea(areaService.getAreaByCode(areaCode));
		tapWaterService.saveTapWater(tapWater);
		return convertToResult("message","更新成功");
	}
	/**
	 *  删除提升泵站实体 <br/>
	 * @param request
	 * @return Map<String,Object>
	 */
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public Map<String,Object>  deleTapWaters(@Valid String ids){
		String [] idarr = ids.split(",");
		List<Long> idlist = new ArrayList<Long>();
		for(String id : idarr){
			idlist.add(Long.valueOf(id));
		}
		tapWaterService.deleTapWater(idlist);
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
	public void getTapWater(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("tapWater", tapWaterService.getTapWaterById(id));
		}
	}
	/**
	 * 获取污水厂全部监测项
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/getTapWaterMonitorItemList")
	public List<MonitorItem> getTapWaterMonitorItemList(@RequestParam(value = "id", defaultValue = "0") Long id
			,@RequestParam(value = "monitorType", defaultValue = "0") String monitorType){
		TapWaterEntity tapWater = tapWaterService.getTapWaterById(id);
		List<MonitorItem> items = new ArrayList<MonitorItem>();
		List<TapWaterMonitorEntity> typedata=tapWaterMonitorService.getTapWaterMonitorDataByCode(String.valueOf(tapWater.getCode()), monitorType);
		for(TapWaterMonitorEntity tag:typedata){
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
	public Map<String,Object>  saveMonitorItem(@Valid @ModelAttribute("tapWater") TapWaterEntity tapWater
			,@RequestParam(value = "itemList", defaultValue = "") String itemList
			,@RequestParam(value = "MonitorType", defaultValue = "") String MonitorType){
		String tapWaterCode=tapWater.getCode();
		List<String> list = new ArrayList<String>();
		list.toArray(itemList.split(","));
		tapWaterMonitorService.deleByTapWaterCodeMonitor(tapWaterCode, MonitorType);
		for(String s:itemList.split(",")){
			MonitorItem item = monitorItemService.getMonitorItemByCode(s);
			String monitorCode=item.getCode();
			TapWaterMonitorEntity tapWaterMonitor=new TapWaterMonitorEntity();
			tapWaterMonitor.setTapWaterCode(tapWaterCode);
			tapWaterMonitor.setMoniterItemCode(monitorCode);
			tapWaterMonitor.setMonitorTypeCode(MonitorType);
			tapWaterMonitorService.saveTapWaterMonitor(tapWaterMonitor);
		}	
		return convertToResult("message","更新成功");
	}
	/**
	 * 录入数据界面
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET,value="/inputData")
	public String inputData(){
		return "/water/tapWater/tapWaterData";
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
		SpecificationFactory<MonitorDataTapWater> specf = new SpecificationFactory<MonitorDataTapWater>();
		specf.addSearchParam("tapWaterName", Operator.LIKE, request.getParameter("name"));
		specf.addSearchParam("tapWaterCode", Operator.LIKE, request.getParameter("code"));
		specf.addSearchParam("user.name", Operator.LIKE,  request.getParameter("userName"));
		//分页排序信息
		Page<MonitorDataTapWater> sewagesData= monitorDataTapWaterService.getMonitorDatasTapWaterByPage(specf.getSpecification(),buildPageRequest(request));
		return convertToResult(sewagesData);
	}
	/**
	 * 获取全部提升泵站实体<br/>
	 * @param sewage
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/tapWaterList", method = RequestMethod.GET)
	public  List<TapWaterEntity>  gettapWaterList(){
		 List<TapWaterEntity> data =tapWaterService.getAllTapWaters();
		return data;
	}
	/**
	 * 查询监点内容，找到监测项
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/getMonitorTapWater")
	public List<Map<String,String>> getMonitorTapWaterByCode(
			@RequestParam(value = "code", defaultValue = "") String code,
			@RequestParam(value = "monitorType", defaultValue = "") String monitorType,
			@RequestParam(value = "addOrupdate", defaultValue = "") String addOrupdate){
		//查找检测项		
		List<Map<String,String>> data= monitorItemService.getMonitorTapWaterByCode(code, monitorType,addOrupdate);
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
			@RequestParam(value = "tapWaterName", defaultValue = "") String tapWaterName,
			@RequestParam(value = "tapWaterCode", defaultValue = "") String tapWaterCode,
			@RequestParam(value = "monitorContent", defaultValue = "") String monitorContent,
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
				MonitorDataTapWater md=new MonitorDataTapWater();
				itemName=tem[i].substring(0,tem[i].indexOf("@@"));
				itemValue=tem[i].substring(tem[i].indexOf("@@")+2);
				md.setMonitorDate(monitortime);
				md.setCreateDate(date);
				md.setUpdateDate(date);
				md.setUser(this.getCurrentUser()); 
				md.setItemName(itemName);
				md.setItemValue(itemValue);
				md.setTapWaterName(tapWaterName);
				md.setMonitorType(monitorType);
				md.setTapWaterCode(tapWaterCode);
				monitorDataTapWaterService.saveMonitorDataTapWater(md);
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
				MonitorDataTapWater md=monitorDataTapWaterService.getMonitorDataTapWaterById(Long.parseLong(id));
				if(!"".equals(monitorTime)&&monitorTime!=null){
					md.setMonitorDate(monitortime);
				}
				md.setItemValue(itemValue);
				md.setUpdateDate(date);
				monitorDataTapWaterService.saveMonitorDataTapWater(md);
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
			@RequestParam(value = "monitorType", defaultValue = "[]") String monitorType){
		String [] Codearr = code.split(",");
		String [] MonitorTypearr = monitorType.split(",");
		try{
			for(int i=0;i<Codearr.length;i++){
				 monitorDataTapWaterService.deleMonitorDataTapWater(Codearr[i], Integer.parseInt(MonitorTypearr[i]));
			}
		}catch(Exception e){
			return convertToResult("message","删除数据失败");
		}
		return convertToResult("message","删除数据成功");
	}
	/**
	 * 提升泵站详细信息界面<br/>
	 * @param model
	 * @return
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET,value = "/tapWaterDetail")
	public String  sewageDetail(Model model,@RequestParam(value = "code", defaultValue = "") String code){
			TapWaterEntity tapWater=tapWaterService.getTapWaterByCode(code);
			model.addAttribute("code", code);
			model.addAttribute("tapWater", tapWater);
			return "/water/tapWater/tapWaterDetail";
	}
	/**
	 * 通过时间获取数据
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/getMonitorData")
	public List<MonitorDataTapWater> getMonitorData(@RequestParam(value = "code", defaultValue = "") String code,
			@RequestParam(value = "monitorTime", defaultValue = "") String monitorTime){
		List<MonitorDataTapWater> items=new ArrayList<MonitorDataTapWater>();
		if("".equals(monitorTime)){
			List<Date> data = monitorDataTapWaterService.getMonitorTimeByTapWaterCode(code);
			if(data.size()>0){
				items=monitorDataTapWaterService.getMonitorDataTapWaterByMonitorTime(data.get(0), code);
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
			items=monitorDataTapWaterService.getMonitorDataTapWaterByMonitorTime(monitortime, code);
		}
		return items;
	}
	/**
	 * 获取采样时间
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/getMonitorTime")
	public List<Map<String,String>> getMonitorTimeList(@RequestParam(value = "code", defaultValue = "") String code){
		List<Date> data = monitorDataTapWaterService.getMonitorTimeByTapWaterCode(code);	
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
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET,value = "/tapWaterLeafData")
	public String  tapWaterLeafData(Model model,@RequestParam(value = "parentCode", defaultValue = "0") String parentCode
			,@RequestParam(value = "areaCode", defaultValue = "0") String areaCode
			,@RequestParam(value = "code", defaultValue = "0") String code){	
		model.addAttribute("parentCode", parentCode);
		model.addAttribute("areaCode", areaCode);
		model.addAttribute("code", code);
		return "/water/tapWater/tapWaterLeafData";
	}
}
