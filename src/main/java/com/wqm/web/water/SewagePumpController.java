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
import com.wqm.entity.water.sewagePump.MonitorDataSewagePump;
import com.wqm.entity.water.sewagePump.SewagePumpEntity;
import com.wqm.entity.water.sewagePump.SewagePumpMonitorEntity;
import com.wqm.service.water.AreaService;
import com.wqm.service.water.MonitorItemService;
import com.wqm.service.water.SewagePump.MonitorDataSewagePumpService;
import com.wqm.service.water.SewagePump.SewagePumpMonitorService;
import com.wqm.service.water.SewagePump.SewagePumpService;
import com.wqm.web.BaseController;

/**
 * 系统-污水管理Controller
 * 
 * @author xuxz
 *
 */
@Controller
@RequestMapping(value = "/sewagePump")
public class SewagePumpController extends BaseController{
	@Autowired
	private SewagePumpService sewagePumpService;
	@Autowired
	private AreaService areaService;
	@Autowired
	private SewagePumpMonitorService sewagePumpMonitorService;
	@Autowired
	private MonitorItemService monitorItemService;
	@Autowired
	private MonitorDataSewagePumpService monitorDataSewagePumpService;
	
	/**
	 * 污水提升泵站管理界面<br/>
	 * @param model
	 * @return
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET,value = "/sewagePumpManager")
	public String  watersManager(Model model){		 
		return "/water/sewagePump/sewagePumpManager";
	}
	/**
	 * 分页查询污水处理厂
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/getSewagePumpPage")
	public Map<String,Object> getSewagePumpPage(HttpServletRequest request){
		//查询条件
		SpecificationFactory<SewagePumpEntity> specf = new SpecificationFactory<SewagePumpEntity>();
		specf.addSearchParam("area.code", Operator.EQ, "0".equals(request.getParameter("areaCode"))?"":request.getParameter("areaCode"));
		specf.addSearchParam("name", Operator.LIKE, request.getParameter("name"));
		specf.addSearchParam("code", Operator.LIKE, request.getParameter("code"));
		specf.addSearchParam("user.name", Operator.LIKE,  request.getParameter("userName"));
		//分页排序信息
		Page<SewagePumpEntity> sewages= sewagePumpService.getSewagePumpsByPage(specf.getSpecification(),buildPageRequest(request));
		return convertToResult(sewages);
	}
	/**
	 * 创建提升泵站<br/>
	 * @param water
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public Map<String,Object>  createWater(@Valid SewagePumpEntity sewagePump,@RequestParam(value = "areaCode", defaultValue = "0") String areaCode){
		Date date = new Date();		
		sewagePump.setCreateDate(date);
		sewagePump.setUpdateDate(date);
		sewagePump.setUser(this.getCurrentUser()); 
		sewagePump.setArea(areaService.getAreaByCode(areaCode));
		sewagePumpService.saveSewagePump(sewagePump);
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
	public Map<String,Object>  updateWater(@Valid @ModelAttribute("sewagePump") SewagePumpEntity sewagePump
			,@RequestParam(value = "areaCode", defaultValue = "0") String areaCode){
		//sewagePump=sewagePumpService.getSewagePumpById(sewagePump.getId());//暂时先这么弄，不知道为啥SewageEntity这个实体没有数据
		Date date = new Date();
		sewagePump.setUpdateDate(date);
		sewagePump.setUser(this.getCurrentUser()); 
		sewagePump.setArea(areaService.getAreaByCode(areaCode));
		sewagePumpService.saveSewagePump(sewagePump);
		return convertToResult("message","更新成功");
	}
	/**
	 *  删除提升泵站实体 <br/>
	 * @param request
	 * @return Map<String,Object>
	 */
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public Map<String,Object>  deleSewagePumps(@Valid String ids){
		String [] idarr = ids.split(",");
		List<Long> idlist = new ArrayList<Long>();
		for(String id : idarr){
			idlist.add(Long.valueOf(id));
		}
		sewagePumpService.deleSewagePump(idlist);
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
	public void getSewagePump(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("sewagePump", sewagePumpService.getSewagePumpById(id));
		}
	}
	/**
	 * 获取污水厂全部监测项
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/getSewagePumpMonitorItemList")
	public List<MonitorItem> getSewagePumpMonitorItemList(@RequestParam(value = "id", defaultValue = "0") Long id
			,@RequestParam(value = "monitorType", defaultValue = "0") String monitorType){
		SewagePumpEntity sewagePump = sewagePumpService.getSewagePumpById(id);
		List<MonitorItem> items = new ArrayList<MonitorItem>();
		List<SewagePumpMonitorEntity> typedata=sewagePumpMonitorService.getSewageMonitorDataByCode(String.valueOf(sewagePump.getCode()), monitorType);
		for(SewagePumpMonitorEntity tag:typedata){
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
	public Map<String,Object>  saveMonitorItem(@Valid @ModelAttribute("sewagePump") SewagePumpEntity sewagePump
			,@RequestParam(value = "itemList", defaultValue = "") String itemList
			,@RequestParam(value = "MonitorType", defaultValue = "") String MonitorType){
		//sewagePump=sewagePumpService.getSewageById(sewagePump.getId());//暂时先这么弄，不知道为啥SewageEntity这个实体没有数据
		String sewagePumpCode=sewagePump.getCode();
		List<String> list = new ArrayList<String>();
		list.toArray(itemList.split(","));
		sewagePumpMonitorService.deleBySewagePumpCodeMonitor(sewagePumpCode, MonitorType);
		for(String s:itemList.split(",")){
			MonitorItem item = monitorItemService.getMonitorItemByCode(s);
			String monitorCode=item.getCode();
			SewagePumpMonitorEntity sewagePumpMonitor=new SewagePumpMonitorEntity();
			sewagePumpMonitor.setSewagePumpCode(sewagePumpCode);
			sewagePumpMonitor.setMoniterItemCode(monitorCode);
			sewagePumpMonitor.setMonitorTypeCode(MonitorType);
			sewagePumpMonitorService.saveSewagePumpMonitor(sewagePumpMonitor);
		}	
		return convertToResult("message","更新成功");
	}
	/**
	 * 录入数据界面
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET,value="/inputData")
	public String inputData(){
		return "/manageData/sewagePumpData";
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
		SpecificationFactory<MonitorDataSewagePump> specf = new SpecificationFactory<MonitorDataSewagePump>();
		specf.addSearchParam("sewagePumpName", Operator.LIKE, request.getParameter("name"));
		specf.addSearchParam("sewagePumpCode", Operator.LIKE, request.getParameter("code"));
		specf.addSearchParam("user.name", Operator.LIKE,  request.getParameter("userName"));
		//分页排序信息
		Page<MonitorDataSewagePump> sewagesData= monitorDataSewagePumpService.getMonitorDatasSewagePumpByPage(specf.getSpecification(),buildPageRequest(request));
		return convertToResult(sewagesData);
	}
	/**
	 * 获取全部提升泵站实体<br/>
	 * @param sewage
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/sewagePumpList", method = RequestMethod.GET)
	public  List<SewagePumpEntity>  getSewageList(){
		 List<SewagePumpEntity> data =sewagePumpService.getAllSewagePumps();
		return data;
	}
	/**
	 * 查询监点内容，找到监测项
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/getMonitorSewage")
	public List<Map<String,String>> getMonitorSewageByCode(
			@RequestParam(value = "code", defaultValue = "") String code,
			@RequestParam(value = "monitorType", defaultValue = "") String monitorType,
			@RequestParam(value = "addOrupdate", defaultValue = "") String addOrupdate){
		//查找检测项		
		List<Map<String,String>> data= monitorItemService.getMonitorSewagePumpByCode(code, monitorType,addOrupdate);
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
			@RequestParam(value = "sewagePumpName", defaultValue = "") String sewagePumpName,
			@RequestParam(value = "sewagePumpCode", defaultValue = "") String sewagePumpCode,
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
				MonitorDataSewagePump md=new MonitorDataSewagePump();
				itemName=tem[i].substring(0,tem[i].indexOf("@@"));
				itemValue=tem[i].substring(tem[i].indexOf("@@")+2);
				md.setMonitorDate(monitortime);
				md.setCreateDate(date);
				md.setUpdateDate(date);
				md.setUser(this.getCurrentUser()); 
				md.setItemName(itemName);
				md.setItemValue(itemValue);
				md.setSewagePumpName(sewagePumpName);
				md.setMonitorType(monitorType);
				md.setSewagePumpCode(sewagePumpCode);
				monitorDataSewagePumpService.saveMonitorDataSewagePump(md);
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
				MonitorDataSewagePump md=monitorDataSewagePumpService.getMonitorDataSewagePumpById(Long.parseLong(id));
				if(!"".equals(monitorTime)&&monitorTime!=null){
					md.setMonitorDate(monitortime);
				}
				md.setItemValue(itemValue);
				md.setUpdateDate(date);
				monitorDataSewagePumpService.saveMonitorDataSewagePump(md);
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
				 monitorDataSewagePumpService.deleMonitorDataSewagePump(Codearr[i], Integer.parseInt(MonitorTypearr[i]));
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
	@RequestMapping(method = RequestMethod.GET,value = "/sewagePumpDetail")
	public String  sewageDetail(Model model,@RequestParam(value = "code", defaultValue = "") String code){
			SewagePumpEntity sewagePump=sewagePumpService.getSewagePumpByCode(code);
			model.addAttribute("code", code);
			model.addAttribute("sewagePump", sewagePump);
			return "/water/sewagePump/sewagePumpDetail";
	}
	/**
	 * 通过时间获取数据
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/getMonitorData")
	public List<MonitorDataSewagePump> getMonitorData(@RequestParam(value = "code", defaultValue = "") String code,
			@RequestParam(value = "monitorTime", defaultValue = "") String monitorTime){
		List<MonitorDataSewagePump> items=new ArrayList<MonitorDataSewagePump>();
		if("".equals(monitorTime)){
			List<Date> data = monitorDataSewagePumpService.getMonitorTimeBySewagePumpCode(code);
			if(data.size()>0){
				items=monitorDataSewagePumpService.getMonitorDataSewagePumpByMonitorTime(data.get(0), code);
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
			items=monitorDataSewagePumpService.getMonitorDataSewagePumpByMonitorTime(monitortime, code);
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
		List<Date> data = monitorDataSewagePumpService.getMonitorTimeBySewagePumpCode(code);	
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
