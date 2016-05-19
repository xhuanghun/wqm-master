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

import org.apache.commons.lang3.StringUtils;
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
import com.wqm.entity.water.MonitorData;
import com.wqm.entity.water.MonitorItem;
import com.wqm.entity.water.WaterEntity;
import com.wqm.entity.water.WaterMonitorEntity;
import com.wqm.service.water.AreaService;
import com.wqm.service.water.MonitorDataService;
import com.wqm.service.water.MonitorItemService;
import com.wqm.service.water.WaterMonitorService;
import com.wqm.service.water.WaterService;
import com.wqm.web.BaseController;

/**
 * 系统-水体管理Controller
 * 
 * @author wangxj
 *
 */
@Controller
@RequestMapping(value = "/water")
public class WaterController extends BaseController{

	@Autowired
	private WaterService waterService;
	
	@Autowired
	private MonitorItemService monitorItemService;
	
	@Autowired
	private MonitorDataService monitorDataService;
	
	@Autowired
	private AreaService areaService;
	@Autowired
	private WaterMonitorService waterMonitorService;
	
	private final static Logger logger = LoggerFactory.getLogger(WaterController.class);
	
	/**
	 * 按父节点加载水体，返回水体树JSON
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/getWaters")
	public List<Map<String,Object>> getWatersJson(@RequestParam(value = "code", defaultValue = "0") String code){
		List<WaterEntity> waters= waterService.getWaterListByCode(code);
		List<Map<String,Object>> tree =  new ArrayList<Map<String,Object>>();
		for(WaterEntity water:waters){
			Map<String, Object> map = new HashMap<String, Object>();
			String text = water.getName();
			map.put("id",water.getId()+"");
			map.put("iconCls", water.getIconCls());
			if(!water.getIsLeaf()){
				map.put("state","closed");
				text = "<span class='waterTreeParent'>"+text+"</span>" ;
			}
			map.put("text",text);
			
			Map<String,String> attribute = new HashMap<String,String>();
			attribute.put("url", water.getWaterUrl());
			map.put("attributes",attribute );
			tree.add(map);
		}
		logger.info("加载水体:"+tree.toString());
		return tree;
	}
	
	/**
	 * 水体管理界面<br/>
	 * @param model
	 * @return
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET,value = "/waterManager")
	public String  watersManager(Model model){		 
		return "/water/waterManager";
	}
	
	/**
	 * 水体详细信息界面<br/>
	 * @param model
	 * @return
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET,value = "/waterDetail")
	public String  waterDetail(Model model,@RequestParam(value = "code", defaultValue = "0") String code){	
		WaterEntity water = waterService.getWaterByCode(code);
		WaterEntity parentWater=waterService.getWaterByCode(water.getParentCode());
		model.addAttribute("water", parentWater);
		model.addAttribute("waterZD", water);
		return "/water/waterLeafDetail";
	}
	
	/**
	 * 水体编辑，新增界面
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET,value = "/waterEdit")
	public String  watersEditForm(Model model,@Valid Long id){	
		WaterEntity water = null;
		String sortType = "create";
		if(id != null){
			water =  waterService.getWaterById(id);
			sortType = "update";
		}
		model.addAttribute("water",water);
		model.addAttribute("sortType", sortType);
		return "/water/waterForm";
	}
	
	/**
	 * 获取全部水体
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/getWatersList")
	public List<WaterEntity> getWatersList(){
		List<WaterEntity> waters= waterService.getAllWaters();
		return waters;
	}
	
	/**
	 * 获取全部子水体
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/getWatersByParent")
	public List<WaterEntity> getWatersByParent(@RequestParam(value = "code", defaultValue = "0") String code){
		List<WaterEntity> waters= waterService.getWaterListByCode(code);
		return waters;
	}
	
	/**
	 * 获取水体全部监测项
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/getWaterMonitorItemList")
	public List<MonitorItem> getWaterMonitorItemList(@RequestParam(value = "id", defaultValue = "0") Long id
			,@RequestParam(value = "monitorType", defaultValue = "0") String  monitorType){
		WaterEntity water = waterService.getWaterById(id);
		List<MonitorItem> items = new ArrayList<MonitorItem>();
		List<WaterMonitorEntity> type=waterMonitorService.getWaterMonitorDataByCode(String.valueOf(water.getCode()),monitorType);		
		for(WaterMonitorEntity tag:type){
			MonitorItem mt=new MonitorItem();
			mt=monitorItemService.getMonitorItemByCode(tag.getMoniterItemCode());
			items.add(mt);
		}
		return items;
	}
	
	/**
	 * 获取水体全部监测项
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/getWaterMonitorItemByCode")
	public List<Map<String,String>> getWaterMonitorItemByCode(@RequestParam(value = "code", defaultValue = "0") String code){
		WaterEntity water = waterService.getWaterByCode(code);
		List<MonitorItem> items = water.getMonitorItem();
		List<Map<String,String>> results = new ArrayList<Map<String,String>>();
		for(MonitorItem item:items){
			Map<String,String> result = new HashMap<String,String>();
			result.put("code", item.getCode());
			result.put("name", item.getName());
			result.put("id", item.getId()+"");
			results.add(result);
		}
		return results;
	}
	
	/**
	 * 分页查询水体
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/getWatersPage")
	public Map<String,Object> getWatersPage(HttpServletRequest request){
		//查询条件
		SpecificationFactory<WaterEntity> specf = new SpecificationFactory<WaterEntity>();
		specf.addSearchParam("name", Operator.LIKE, request.getParameter("name"));
		specf.addSearchParam("code", Operator.LIKE, request.getParameter("code"));
		specf.addSearchParam("user.name", Operator.LIKE,  request.getParameter("userName"));
		specf.addSearchParam("isLeaf", Operator.EQ,  StringUtils.isBlank(request.getParameter("isLeaf"))?
				"":Boolean.valueOf(request.getParameter("isLeaf")));
		//分页排序信息
		Page<WaterEntity> waters= waterService.getWatersByPage(specf.getSpecification(),buildPageRequest(request));
		return convertToResult(waters);
	}
	
	/**
	 * 获得全部的父水体项，即所有的栏目。
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/getParents")
	public List<WaterEntity> getParents(){
		List<WaterEntity> waters= waterService.getAllParents();
		return waters;
	}
	
	/**
	 * 获得全部的水体项，通过区域编码。
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/getWaterByAreaCode")
	public List<WaterEntity> getWaterByAreaCode(@RequestParam(value = "areaCode", defaultValue = "0") String areaCode){
		WaterEntity root = new WaterEntity();
		root.setCode("0");
		root.setName("全部水体");
		root.setSortNum("0");
		List<WaterEntity> waters = waterService.getWaterByAreaCode(areaCode);
		waters.add(root);
		return waters;
	}
	
	/**
	 * 获得全部的已监测水体项。
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/getWaterData")
	public Map<String,Object> getWaterData(HttpServletRequest request){
		SpecificationFactory<WaterEntity> specf = new SpecificationFactory<WaterEntity>();
		specf.addSearchParam("parentCode", Operator.EQ, "0".equals(request.getParameter("parentCode"))?"":request.getParameter("parentCode"));
		specf.addSearchParam("area.code", Operator.EQ, "0".equals(request.getParameter("areaCode"))?"":request.getParameter("areaCode"));
		specf.addSearchParam("code", Operator.EQ, "0".equals(request.getParameter("waterCode"))?"":request.getParameter("waterCode"));
		specf.addSearchParam("isLeaf",Operator.EQ, Boolean.valueOf(request.getParameter("isLeaf")));
		specf.addSearchParam("moduleType",Operator.EQ, "0".equals(request.getParameter("moduleType"))?"":request.getParameter("moduleType"));
		Page<WaterEntity> waters= waterService.getWatersByPage(specf.getSpecification(),buildPageRequest(request));
		return convertToResult(waters);
	}
	
	/**
	 * 创建水体<br/>
	 * @param water
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public Map<String,Object>  createWater(@Valid WaterEntity water,@RequestParam(value = "areaCode", defaultValue = "0") String areaCode){
		Date date = new Date();		
		water.setCreateDate(date);
		water.setUpdateDate(date);
		water.setUser(this.getCurrentUser()); 
		water.setArea(areaService.getAreaByCode(areaCode));
		waterService.saveWater(water);
		return convertToResult("message","新增成功");
	}
	
	/**
	 * 更新水体<br/>
	 * 权限编码 007001003
	 * @param water
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@RequiresPermissions("007001003")
	public Map<String,Object>  updateWater(@Valid @ModelAttribute("water") WaterEntity water
			,@RequestParam(value = "areaCode", defaultValue = "0") String areaCode){
		Date date = new Date();
		water.setUpdateDate(date);
		water.setUser(this.getCurrentUser()); 
		water.setArea(areaService.getAreaByCode(areaCode));
		waterService.saveWater(water);
		return convertToResult("message","更新成功");
	}
	
	/**
	 * 更新水体<br/>
	 * 权限编码 007001003
	 * @param water
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveMonitorItem", method = RequestMethod.POST)
	public Map<String,Object>  saveMonitorItem(@Valid @ModelAttribute("water") WaterEntity water
			,@RequestParam(value = "itemList", defaultValue = "") String itemList,
			@RequestParam(value = "MonitorType", defaultValue = "") String MonitorType){
		String waterCode=water.getCode();
		List<String> list = new ArrayList<String>();
		list.toArray(itemList.split(","));
		waterMonitorService.deleByWaterCodeMonitorType(waterCode, MonitorType);
		for(String s:itemList.split(",")){
			MonitorItem item = monitorItemService.getMonitorItemByCode(s);
			String monitorCode=item.getCode();
			WaterMonitorEntity waterMonitor=new WaterMonitorEntity();
			waterMonitor.setWaterCode(waterCode);
			waterMonitor.setMoniterItemCode(monitorCode);
			waterMonitor.setMonitorTypeCode(MonitorType);
			waterMonitorService.saveWaterMonitor(waterMonitor);
		}	
		return convertToResult("message","更新成功");
	}
	
	/**
	 * 更改水体状态<br/>
	 * @param water
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateStatus", method = RequestMethod.GET)
	public Map<String,Object>  updateWaterStatus(@Valid @ModelAttribute("water") WaterEntity water){
		Date date = new Date();
		water.setUpdateDate(date);
		water.setUser(this.getCurrentUser()); 
		waterService.saveWater(water);
		return convertToResult("message","更新水体状态成功");
	}
	
	/**
	 *  删除水体 <br/>
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
		waterService.deleWaterListId(idlist);//这里删除有个错误，我把它改了
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
	public void getWater(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("water", waterService.getWaterById(id));
		}
	}
	/**
	 * 获得全部的水体项，通过水体类型。
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/getWaterByIs_leaf")
	public List<WaterEntity> getWaterByIs_leaf(@RequestParam(value = "Is_leaf", defaultValue = "false") boolean Is_leaf){
		List<WaterEntity> waters = waterService.getWaterByIs_leaf(Is_leaf);
		return waters;
	}
	/**
	 * 获得全部的水体项，通过水体类型和模块类型。
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/getWaterByIs_leafModuleType")
	public List<WaterEntity> getWaterByIs_leafModuleType(@RequestParam(value = "Is_leaf", defaultValue = "false") boolean Is_leaf,
			@RequestParam(value = "moduleType", defaultValue = "1") String moduleType){
		List<WaterEntity> waters = waterService.getWaterByIs_leafModuleType(Is_leaf,moduleType);
		return waters;
	}
	/**
	 * 通过时间获取数据
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/getMonitorData")
	public List<MonitorData> getMonitorData(@RequestParam(value = "code", defaultValue = "") String code,
			@RequestParam(value = "monitorTime", defaultValue = "") String monitorTime){
		List<MonitorData> items=new ArrayList<MonitorData>();
		if("".equals(monitorTime)){
			List<Date> data = monitorDataService.getMonitorTimeByWaterCode(code);
			if(data.size()>0){
				items=monitorDataService.getMonitorDataByMonitorTime(data.get(0),code);
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
			items=monitorDataService.getMonitorDataByMonitorTime(monitortime,code);
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
		List<Date> data = monitorDataService.getMonitorTimeByWaterCode(code);	
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
}
