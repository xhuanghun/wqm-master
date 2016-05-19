package com.wqm.web.waterCensus;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wqm.common.persistence.SearchFilter.Operator;
import com.wqm.common.persistence.SpecificationFactory;
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
@RequestMapping(value = "/waterCensus")
public class WaterCensusController extends BaseController{

	@Autowired
	private BZService bzService;
	@Autowired
	private DFGCService dfService;
	@Autowired
	private NCGSGCService gsService;
	@Autowired
	private SDZService sdzService;
	@Autowired
	private SKService skService;
	@Autowired
	private SZService szService;
	@Autowired
	private YDSGCService ydsService;
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
	@RequestMapping(method = RequestMethod.GET,value = "/BZmenu")
	public List<Map<String,Object>> getBZDataJson(){
		List<Map<String,Object>> tree =  new ArrayList<Map<String,Object>>();
		for(int i=1;i<=1;i++){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id","D0"+i);
			map.put("state","open");
			Map<String,String> attribute = new HashMap<String,String>();
			if(i==1){
				map.put("text","表格数据");
				attribute.put("url", "/waterCensus/BZtableData");
			}			
			attribute.put("openTab", "Y");
			map.put("attributes", attribute);
			tree.add(map);
		}
		return tree;
	}
	/**
	 * 录入数据界面
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET,value="/BZtableData")
	public String inputBZData(){
		return "/waterCensus/BZtableData";
	}
	/**
	 * 录入数据界面
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET,value="/BZinputDataExcel")
	public String inputBZDataS(){
		return "/waterCensus/BZinputDataExcel";
	}
	/**
	 * 分页查询水体
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/getBZPage")
	public Map<String,Object> getBZPage(HttpServletRequest request){
		//查询条件
		SpecificationFactory<BZEntity> specf = new SpecificationFactory<BZEntity>();
		specf.addSearchParam("bzName", Operator.LIKE, request.getParameter("bzName"));
		specf.addSearchParam("bzCode", Operator.LIKE, request.getParameter("bzCode"));
		//分页排序信息
		Page<BZEntity> waters= bzService.getBZByPage(specf.getSpecification(),buildPageRequest(request));
		return convertToResult(waters);
	}
	/**
	 * 按父节点加载菜单，返回菜单树JSON
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/DFGCmenu")
	public List<Map<String,Object>> getDFGCDataJson(){
		List<Map<String,Object>> tree =  new ArrayList<Map<String,Object>>();
		for(int i=1;i<=1;i++){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id","D0"+i);
			map.put("state","open");
			Map<String,String> attribute = new HashMap<String,String>();
			if(i==1){
				map.put("text","表格数据");
				attribute.put("url", "/waterCensus/DFGCtableData");
			}			
			attribute.put("openTab", "Y");
			map.put("attributes", attribute);
			tree.add(map);
		}
		return tree;
	}
	/**
	 * 录入数据界面
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET,value="/DFGCtableData")
	public String inputDFGCData(){
		return "/waterCensus/DFGCtableData";
	}
	/**
	 * 录入数据界面
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET,value="/DFGCinputDataExcel")
	public String inputDFGCDataS(){
		return "/waterCensus/DFGCinputDataExcel";
	}
	/**
	 * 分页查询水体
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/getDFGCPage")
	public Map<String,Object> getDFGCPage(HttpServletRequest request){
		//查询条件
		SpecificationFactory<DFGCEntity> specf = new SpecificationFactory<DFGCEntity>();
		specf.addSearchParam("dfName", Operator.LIKE, request.getParameter("dfName"));
		specf.addSearchParam("dfCode", Operator.LIKE, request.getParameter("dfCode"));
		//分页排序信息
		Page<DFGCEntity> waters= dfService.getDFByPage(specf.getSpecification(),buildPageRequest(request));
		return convertToResult(waters);
	}
	/**
	 * 按父节点加载菜单，返回菜单树JSON
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/NCGSGCmenu")
	public List<Map<String,Object>> getNCGSGCDataJson(){
		List<Map<String,Object>> tree =  new ArrayList<Map<String,Object>>();
		for(int i=1;i<=1;i++){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id","D0"+i);
			map.put("state","open");
			Map<String,String> attribute = new HashMap<String,String>();
			if(i==1){
				map.put("text","表格数据");
				attribute.put("url", "/waterCensus/NCGSGCtableData");
			}			
			attribute.put("openTab", "Y");
			map.put("attributes", attribute);
			tree.add(map);
		}
		return tree;
	}
	/**
	 * 录入数据界面
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET,value="/NCGSGCtableData")
	public String inputNCGSGCData(){
		return "/waterCensus/NCGSGCtableData";
	}
	/**
	 * 录入数据界面
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET,value="/NCGSGCinputDataExcel")
	public String inputNCGSGCDataS(){
		return "/waterCensus/NCGSGCinputDataExcel";
	}
	/**
	 * 分页查询水体
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/getNCGSGCPage")
	public Map<String,Object> getNCGSGCPage(HttpServletRequest request){
		//查询条件
		SpecificationFactory<NCGSGCEntity> specf = new SpecificationFactory<NCGSGCEntity>();
		specf.addSearchParam("gsName", Operator.LIKE, request.getParameter("gsName"));
		specf.addSearchParam("gsCode", Operator.LIKE, request.getParameter("gsCode"));
		//分页排序信息
		Page<NCGSGCEntity> waters= gsService.getGSByPage(specf.getSpecification(),buildPageRequest(request));
		return convertToResult(waters);
	}
	/**
	 * 按父节点加载菜单，返回菜单树JSON
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/SDZmenu")
	public List<Map<String,Object>> getSDZDataJson(){
		List<Map<String,Object>> tree =  new ArrayList<Map<String,Object>>();
		for(int i=1;i<=1;i++){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id","D0"+i);
			map.put("state","open");
			Map<String,String> attribute = new HashMap<String,String>();
			if(i==1){
				map.put("text","表格数据");
				attribute.put("url", "/waterCensus/SDZtableData");
			}			
			attribute.put("openTab", "Y");
			map.put("attributes", attribute);
			tree.add(map);
		}
		return tree;
	}
	/**
	 * 录入数据界面
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET,value="/SDZtableData")
	public String inputSDZData(){
		return "/waterCensus/SDZtableData";
	}
	/**
	 * 录入数据界面
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET,value="/SDZinputDataExcel")
	public String inputSDZDataS(){
		return "/waterCensus/SDZinputDataExcel";
	}
	/**
	 * 分页查询水体
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/getSDZPage")
	public Map<String,Object> getSDZPage(HttpServletRequest request){
		//查询条件
		SpecificationFactory<SDZEntity> specf = new SpecificationFactory<SDZEntity>();
		specf.addSearchParam("sdzName", Operator.LIKE, request.getParameter("sdzName"));
		specf.addSearchParam("sdzCode", Operator.LIKE, request.getParameter("sdzCode"));
		//分页排序信息
		Page<SDZEntity> waters= sdzService.getSDZByPage(specf.getSpecification(),buildPageRequest(request));
		return convertToResult(waters);
	}
	/**
	 * 按父节点加载菜单，返回菜单树JSON
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/SKmenu")
	public List<Map<String,Object>> getSKDataJson(){
		List<Map<String,Object>> tree =  new ArrayList<Map<String,Object>>();
		for(int i=1;i<=1;i++){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id","D0"+i);
			map.put("state","open");
			Map<String,String> attribute = new HashMap<String,String>();
			if(i==1){
				map.put("text","表格数据");
				attribute.put("url", "/waterCensus/SKtableData");
			}			
			attribute.put("openTab", "Y");
			map.put("attributes", attribute);
			tree.add(map);
		}
		return tree;
	}
	/**
	 * 录入数据界面
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET,value="/SKtableData")
	public String inputSKData(){
		return "/waterCensus/SKtableData";
	}
	/**
	 * 录入数据界面
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET,value="/SKinputDataExcel")
	public String inputSKDataS(){
		return "/waterCensus/SKinputDataExcel";
	}
	/**
	 * 分页查询水体
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/getSKPage")
	public Map<String,Object> getSKPage(HttpServletRequest request){
		//查询条件
		SpecificationFactory<SKEntity> specf = new SpecificationFactory<SKEntity>();
		specf.addSearchParam("skName", Operator.LIKE, request.getParameter("skName"));
		specf.addSearchParam("skCode", Operator.LIKE, request.getParameter("skCode"));
		//分页排序信息
		Page<SKEntity> waters= skService.getSKPage(specf.getSpecification(),buildPageRequest(request));
		return convertToResult(waters);
	}
	/**
	 * 按父节点加载菜单，返回菜单树JSON
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/SZmenu")
	public List<Map<String,Object>> getSZDataJson(){
		List<Map<String,Object>> tree =  new ArrayList<Map<String,Object>>();
		for(int i=1;i<=1;i++){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id","D0"+i);
			map.put("state","open");
			Map<String,String> attribute = new HashMap<String,String>();
			if(i==1){
				map.put("text","表格数据");
				attribute.put("url", "/waterCensus/SZtableData");
			}			
			attribute.put("openTab", "Y");
			map.put("attributes", attribute);
			tree.add(map);
		}
		return tree;
	}
	/**
	 * 录入数据界面
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET,value="/SZtableData")
	public String inputSZData(){
		return "/waterCensus/SZtableData";
	}
	/**
	 * 录入数据界面
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET,value="/SZinputDataExcel")
	public String inputSZDataS(){
		return "/waterCensus/SZinputDataExcel";
	}
	/**
	 * 分页查询水体
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/getSZPage")
	public Map<String,Object> getSZPage(HttpServletRequest request){
		//查询条件
		SpecificationFactory<SZEntity> specf = new SpecificationFactory<SZEntity>();
		specf.addSearchParam("szName", Operator.LIKE, request.getParameter("szName"));
		specf.addSearchParam("szCode", Operator.LIKE, request.getParameter("szCode"));
		//分页排序信息
		Page<SZEntity> waters= szService.getSZPage(specf.getSpecification(),buildPageRequest(request));
		return convertToResult(waters);
	}
	/**
	 * 按父节点加载菜单，返回菜单树JSON
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/YDSGCmenu")
	public List<Map<String,Object>> getYDSGCDataJson(){
		List<Map<String,Object>> tree =  new ArrayList<Map<String,Object>>();
		for(int i=1;i<=1;i++){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id","D0"+i);
			map.put("state","open");
			Map<String,String> attribute = new HashMap<String,String>();
			if(i==1){
				map.put("text","表格数据");
				attribute.put("url", "/waterCensus/YDSGCtableData");
			}			
			attribute.put("openTab", "Y");
			map.put("attributes", attribute);
			tree.add(map);
		}
		return tree;
	}
	/**
	 * 录入数据界面
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET,value="/YDSGCtableData")
	public String inputYDSGCData(){
		return "/waterCensus/YDSGCtableData";
	}
	/**
	 * 录入数据界面
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET,value="/YDSGCinputDataExcel")
	public String inputYDSGCDataS(){
		return "/waterCensus/YDSGCinputDataExcel";
	}
	/**
	 * 分页查询水体
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/getYDSGCPage")
	public Map<String,Object> getYDSGCPage(HttpServletRequest request){
		//查询条件
		SpecificationFactory<YDSGCEntity> specf = new SpecificationFactory<YDSGCEntity>();
		specf.addSearchParam("ydsName", Operator.LIKE, request.getParameter("ydsName"));
		specf.addSearchParam("ydsCode", Operator.LIKE, request.getParameter("ydsCode"));
		//分页排序信息
		Page<YDSGCEntity> waters= ydsService.getYDSGCPage(specf.getSpecification(),buildPageRequest(request));
		return convertToResult(waters);
	}
}
