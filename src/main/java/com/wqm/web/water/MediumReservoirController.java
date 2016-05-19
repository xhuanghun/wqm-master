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
import com.wqm.entity.water.mediumReservoir.MonitorDataMediumReservoir;
import com.wqm.entity.water.mediumReservoir.MediumReservoirEntity;
import com.wqm.entity.water.mediumReservoir.MediumReservoirMonitorEntity;
import com.wqm.service.water.AreaService;
import com.wqm.service.water.MonitorItemService;
import com.wqm.service.water.MediumReservoir.MonitorDataMediumReservoirService;
import com.wqm.service.water.MediumReservoir.MediumReservoirMonitorService;
import com.wqm.service.water.MediumReservoir.MediumReservoirService;
import com.wqm.web.BaseController;

/**
 * 系统-污水管理Controller
 * 
 * @author xuxz
 *
 */
@Controller
@RequestMapping(value = "/mediumReservoir")
public class MediumReservoirController extends BaseController{
	@Autowired
	private MediumReservoirService mediumReservoirService;
	@Autowired
	private AreaService areaService;
	@Autowired
	private MediumReservoirMonitorService mediumReservoirMonitorService;
	@Autowired
	private MonitorItemService monitorItemService;
	@Autowired
	private MonitorDataMediumReservoirService monitorDataMediumReservoirService;
	
	/**
	 * 污水提升泵站管理界面<br/>
	 * @param model
	 * @return
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET,value = "/mediumReservoirManager")
	public String  watersManager(Model model){		 
		return "/water/mediumReservoir/mediumReservoirManager";
	}
	/**
	 * 分页查询污水处理厂
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/getMediumReservoirPage")
	public Map<String,Object> getMediumReservoirPage(HttpServletRequest request){
		//查询条件
		SpecificationFactory<MediumReservoirEntity> specf = new SpecificationFactory<MediumReservoirEntity>();
		specf.addSearchParam("area.code", Operator.EQ, "0".equals(request.getParameter("areaCode"))?"":request.getParameter("areaCode"));
		specf.addSearchParam("name", Operator.LIKE, request.getParameter("name"));
		specf.addSearchParam("code", Operator.LIKE, request.getParameter("code"));
		specf.addSearchParam("user.name", Operator.LIKE,  request.getParameter("userName"));
		//分页排序信息
		Page<MediumReservoirEntity> sewages= mediumReservoirService.getMediumReservoirsByPage(specf.getSpecification(),buildPageRequest(request));
		return convertToResult(sewages);
	}
	/**
	 * 创建提升泵站<br/>
	 * @param water
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public Map<String,Object>  createWater(@Valid MediumReservoirEntity mediumReservoir,@RequestParam(value = "areaCode", defaultValue = "0") String areaCode){
		Date date = new Date();		
		mediumReservoir.setCreateDate(date);
		mediumReservoir.setUpdateDate(date);
		mediumReservoir.setUser(this.getCurrentUser()); 
		mediumReservoir.setArea(areaService.getAreaByCode(areaCode));
		mediumReservoirService.saveMediumReservoir(mediumReservoir);
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
	public Map<String,Object>  updateWater(@Valid @ModelAttribute("mediumReservoir") MediumReservoirEntity mediumReservoir
			,@RequestParam(value = "areaCode", defaultValue = "0") String areaCode){
		Date date = new Date();
		mediumReservoir.setUpdateDate(date);
		mediumReservoir.setUser(this.getCurrentUser()); 
		mediumReservoir.setArea(areaService.getAreaByCode(areaCode));
		mediumReservoirService.saveMediumReservoir(mediumReservoir);
		return convertToResult("message","更新成功");
	}
	/**
	 *  删除提升泵站实体 <br/>
	 * @param request
	 * @return Map<String,Object>
	 */
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public Map<String,Object>  deleMediumReservoirs(@Valid String ids){
		String [] idarr = ids.split(",");
		List<Long> idlist = new ArrayList<Long>();
		for(String id : idarr){
			idlist.add(Long.valueOf(id));
		}
		mediumReservoirService.deleMediumReservoir(idlist);
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
	public void getMediumReservoir(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("mediumReservoir", mediumReservoirService.getMediumReservoirById(id));
		}
	}
	/**
	 * 获取污水厂全部监测项
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/getMediumReservoirMonitorItemList")
	public List<MonitorItem> getMediumReservoirMonitorItemList(@RequestParam(value = "id", defaultValue = "0") Long id
			,@RequestParam(value = "monitorType", defaultValue = "0") String monitorType){
		MediumReservoirEntity mediumReservoir = mediumReservoirService.getMediumReservoirById(id);
		List<MonitorItem> items = new ArrayList<MonitorItem>();
		List<MediumReservoirMonitorEntity> typedata=mediumReservoirMonitorService.getMediumReservoirMonitorDataByCode(String.valueOf(mediumReservoir.getCode()), monitorType);
		for(MediumReservoirMonitorEntity tag:typedata){
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
	public Map<String,Object>  saveMonitorItem(@Valid @ModelAttribute("mediumReservoir") MediumReservoirEntity mediumReservoir
			,@RequestParam(value = "itemList", defaultValue = "") String itemList
			,@RequestParam(value = "MonitorType", defaultValue = "") String MonitorType){
		String mediumReservoirCode=mediumReservoir.getCode();
		List<String> list = new ArrayList<String>();
		list.toArray(itemList.split(","));
		mediumReservoirMonitorService.deleByMediumReservoirCodeMonitor(mediumReservoirCode, MonitorType);
		for(String s:itemList.split(",")){
			MonitorItem item = monitorItemService.getMonitorItemByCode(s);
			String monitorCode=item.getCode();
			MediumReservoirMonitorEntity mediumReservoirMonitor=new MediumReservoirMonitorEntity();
			mediumReservoirMonitor.setMediumReservoirCode(mediumReservoirCode);
			mediumReservoirMonitor.setMoniterItemCode(monitorCode);
			mediumReservoirMonitor.setMonitorTypeCode(MonitorType);
			mediumReservoirMonitorService.saveMediumReservoirMonitor(mediumReservoirMonitor);
		}	
		return convertToResult("message","更新成功");
	}
	/**
	 * 录入数据界面
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET,value="/inputData")
	public String inputData(){
		return "/water/mediumReservoir/mediumReservoirData";
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
		SpecificationFactory<MonitorDataMediumReservoir> specf = new SpecificationFactory<MonitorDataMediumReservoir>();
		specf.addSearchParam("mediumReservoirName", Operator.LIKE, request.getParameter("name"));
		specf.addSearchParam("mediumReservoirCode", Operator.LIKE, request.getParameter("code"));
		specf.addSearchParam("user.name", Operator.LIKE,  request.getParameter("userName"));
		//分页排序信息
		Page<MonitorDataMediumReservoir> sewagesData= monitorDataMediumReservoirService.getMonitorDatasMediumReservoirByPage(specf.getSpecification(),buildPageRequest(request));
		return convertToResult(sewagesData);
	}
	/**
	 * 获取全部提升泵站实体<br/>
	 * @param sewage
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/mediumReservoirList", method = RequestMethod.GET)
	public  List<MediumReservoirEntity>  getmediumReservoirList(){
		 List<MediumReservoirEntity> data =mediumReservoirService.getAllMediumReservoirs();
		return data;
	}
	/**
	 * 查询监点内容，找到监测项
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/getMonitorMediumReservoir")
	public List<Map<String,String>> getMonitorMediumReservoirByCode(
			@RequestParam(value = "code", defaultValue = "") String code,
			@RequestParam(value = "monitorType", defaultValue = "") String monitorType,
			@RequestParam(value = "addOrupdate", defaultValue = "") String addOrupdate){
		//查找检测项		
		List<Map<String,String>> data= monitorItemService.getMonitorMediumReservoirByCode(code, monitorType,addOrupdate);
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
			@RequestParam(value = "mediumReservoirName", defaultValue = "") String mediumReservoirName,
			@RequestParam(value = "mediumReservoirCode", defaultValue = "") String mediumReservoirCode,
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
				MonitorDataMediumReservoir md=new MonitorDataMediumReservoir();
				itemName=tem[i].substring(0,tem[i].indexOf("@@"));
				itemValue=tem[i].substring(tem[i].indexOf("@@")+2);
				md.setMonitorDate(monitortime);
				md.setCreateDate(date);
				md.setUpdateDate(date);
				md.setUser(this.getCurrentUser()); 
				md.setItemName(itemName);
				md.setItemValue(itemValue);
				md.setMediumReservoirName(mediumReservoirName);
				md.setMonitorType(monitorType);
				md.setMediumReservoirCode(mediumReservoirCode);
				monitorDataMediumReservoirService.saveMonitorDataMediumReservoir(md);
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
				MonitorDataMediumReservoir md=monitorDataMediumReservoirService.getMonitorDataMediumReservoirById(Long.parseLong(id));
				if(!"".equals(monitorTime)&&monitorTime!=null){
					md.setMonitorDate(monitortime);
				}
				md.setItemValue(itemValue);
				md.setUpdateDate(date);
				monitorDataMediumReservoirService.saveMonitorDataMediumReservoir(md);
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
				 monitorDataMediumReservoirService.deleMonitorDataMediumReservoir(Codearr[i], Integer.parseInt(MonitorTypearr[i]));
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
	@RequestMapping(method = RequestMethod.GET,value = "/mediumReservoirDetail")
	public String  sewageDetail(Model model,@RequestParam(value = "code", defaultValue = "") String code){
			MediumReservoirEntity mediumReservoir=mediumReservoirService.getMediumReservoirByCode(code);
			model.addAttribute("code", code);
			model.addAttribute("mediumReservoir", mediumReservoir);
			return "/water/mediumReservoir/mediumReservoirDetail";
	}
	/**
	 * 通过时间获取数据
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/getMonitorData")
	public List<MonitorDataMediumReservoir> getMonitorData(@RequestParam(value = "code", defaultValue = "") String code,
			@RequestParam(value = "monitorTime", defaultValue = "") String monitorTime){
		List<MonitorDataMediumReservoir> items=new ArrayList<MonitorDataMediumReservoir>();
		if("".equals(monitorTime)){
			List<Date> data = monitorDataMediumReservoirService.getMonitorTimeByMediumReservoirCode(code);
			if(data.size()>0){
				items=monitorDataMediumReservoirService.getMonitorDataMediumReservoirByMonitorTime(data.get(0), code);
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
			items=monitorDataMediumReservoirService.getMonitorDataMediumReservoirByMonitorTime(monitortime, code);
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
		List<Date> data = monitorDataMediumReservoirService.getMonitorTimeByMediumReservoirCode(code);	
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
	@RequestMapping(method = RequestMethod.GET,value = "/mediumReservoirLeafData")
	public String  mediumReservoirLeafData(Model model,@RequestParam(value = "parentCode", defaultValue = "0") String parentCode
			,@RequestParam(value = "areaCode", defaultValue = "0") String areaCode
			,@RequestParam(value = "code", defaultValue = "0") String code){	
		model.addAttribute("parentCode", parentCode);
		model.addAttribute("areaCode", areaCode);
		model.addAttribute("code", code);
		return "/water/mediumReservoir/mediumReservoirLeafData";
	}
}
