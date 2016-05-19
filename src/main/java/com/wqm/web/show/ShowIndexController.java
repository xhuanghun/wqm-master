package com.wqm.web.show;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wqm.entity.water.AreaEntity;
import com.wqm.entity.water.MonitorData;
import com.wqm.entity.water.SewageEntity;
import com.wqm.entity.water.WaterEntity;
import com.wqm.entity.water.blackWater.BlackWaterEntity;
import com.wqm.entity.water.mediumReservoir.MediumReservoirEntity;
import com.wqm.entity.water.sewagePump.SewagePumpEntity;
import com.wqm.entity.water.tapWater.TapWaterEntity;
import com.wqm.entity.water.waterSource.WaterSourceEntity;
import com.wqm.entity.water.waterSupply.WaterSupplyEntity;
import com.wqm.service.water.AreaService;
import com.wqm.service.water.MonitorDataService;
import com.wqm.service.water.SewageService;
import com.wqm.service.water.WaterService;
import com.wqm.service.water.BlackWater.BlackWaterService;
import com.wqm.service.water.MediumReservoir.MediumReservoirService;
import com.wqm.service.water.SewagePump.SewagePumpService;
import com.wqm.service.water.TapWater.TapWaterService;
import com.wqm.service.water.WaterSource.WaterSourceService;
import com.wqm.service.water.WaterSupply.WaterSupplyService;
import com.wqm.web.BaseController;

/**
 * 系统-水体管理Controller
 * 
 * @author wangxj
 *
 */
@Controller
@RequestMapping(value = "/show")
public class ShowIndexController extends BaseController{

	@Autowired
	private WaterService waterService;
	
	@Autowired
	private AreaService areaService;
	
	@Autowired
	private MonitorDataService monitorDataService;
	@Autowired
	private SewageService sewageService;
	@Autowired
	private SewagePumpService sewagePumpService;
	@Autowired
	private WaterSourceService waterSourceService;
	@Autowired
	private TapWaterService tapWaterService;
	@Autowired
	private MediumReservoirService mediumReservoirService;
	@Autowired
	private BlackWaterService blackWaterService;
	@Autowired
	private WaterSupplyService waterSupplyService;
	
	//private final static Logger logger = LoggerFactory.getLogger(ShowIndexController.class);
	
	/**
	 * 按父节点加载菜单，返回菜单树JSON
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/getWaterTree")
	public List<Map<String,Object>> getWatersJson(@RequestParam(value = "id", defaultValue = "a0") String id,
			@RequestParam(value = "type", defaultValue = "map") String type,
			@RequestParam(value = "showLeaf", defaultValue = "N") String showLeaf){
		String code = id.substring(1);
		String areaCode = "";
		String parentCode = "";
		String url = "";
		String openTab = "Y";
		if("data".equals(type)){
			url = "/monitorData/waterData";
			if("Y".equals(showLeaf)) {
				url = "/monitorData/waterLeafData";
			}
		}else{
			url= "/show/waterMap";
			areaCode = "a";
			parentCode = "w";
		}
		List<Map<String,Object>> tree =  new ArrayList<Map<String,Object>>();
		if(id.startsWith("a")){
			List<AreaEntity> areas= areaService.getAreasByParentCode(code);
			for(AreaEntity area:areas){
				Map<String, Object> map = new HashMap<String, Object>();
				String text = area.getName();
				if(!code.equals("0104")){
					if(text.equals("市水务局")||text.equals("桂林洋开发区")){
						continue;
					}
				}
				text = "<span class='menuTreeParent'>"+text+"</span>" ;
				map.put("id","a"+area.getCode());
				map.put("iconCls", area.getIconCls());
				map.put("text",text);
				map.put("state","closed");
				Map<String,String> attribute = new HashMap<String,String>();
				attribute.put("url", url+"?areaCode="+areaCode+area.getCode());
				if("map".equals(type)) openTab = "N";
				attribute.put("openTab", openTab);
				map.put("attributes",attribute );
				tree.add(map);
			}
			List<WaterEntity> waters= waterService.getWaterByAreaCodeModuleType(code,"1");
			for(WaterEntity water:waters){
				Map<String, Object> map = new HashMap<String, Object>();
				String text = water.getName();
				if(!code.equals("0104")){
					if(text.equals("市水务局")||text.equals("桂林洋开发区")){
						continue;
					}
				}
				map.put("id","w"+water.getCode());
				map.put("iconCls", water.getIconCls());
				Map<String,String> attribute = new HashMap<String,String>();
				if(!water.getIsLeaf()){
					map.put("state","closed");
					
					attribute.put("url", url+"?parentCode="+parentCode+water.getCode()
							+"&areaCode="+areaCode+water.getArea().getCode()
							+"&waterId="+parentCode+water.getCode()
							+"&isLeaf="+("Y".equals(showLeaf)?"true":"false"));
				}else{
					attribute.put("url", url+"?code="+parentCode+water.getCode()
							+"&areaCode="+areaCode+water.getArea().getCode()
							+"&waterId="+parentCode+water.getCode()
							+"&isLeaf="+("Y".equals(showLeaf)?"true":"false"));
				}
				map.put("text",text);
				attribute.put("openTab", openTab);
				map.put("attributes",attribute );
				tree.add(map);
			}
		}else if(id.startsWith("w")){
			List<WaterEntity> waters= waterService.getWaterByAreaCodeModuleType(code,"1");
			for(WaterEntity water:waters){
				Map<String, Object> map = new HashMap<String, Object>();
				String text = water.getName();
				if(!code.equals("0104")){
					if(text.equals("市水务局")||text.equals("桂林洋开发区")){
						continue;
					}
				}
				map.put("id","w"+water.getCode());
				map.put("iconCls", water.getIconCls());
				
				Map<String,String> attribute = new HashMap<String,String>();
				if(!water.getIsLeaf()){
					map.put("state","closed");
					attribute.put("url", url+"?parentCode="+parentCode+water.getCode()
							+"&isLeaf="+("Y".equals(showLeaf)?"true":"false"));
				}else{
					if("data".equals(type))url = "/monitorData/waterLeafData";
					attribute.put("url", url+"?code="+parentCode+water.getCode()
							+"&parentCode="+parentCode+water.getParentCode()
							+"&areaCode="+areaCode+water.getArea().getCode()
							+"&waterId="+parentCode+water.getCode()
							+"&isLeaf="+("Y".equals(showLeaf)?"true":"false"));
				}
				map.put("text",text);
				attribute.put("openTab", openTab);
				map.put("attributes",attribute );
				if((water.getIsLeaf()&&"Y".equals(showLeaf))
						||(!water.getIsLeaf()))
					tree.add(map);
			}
		}
		return tree;
	}
	/**
	 * 按父节点加载菜单，返回菜单树JSON
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/getMainWaterTree")
	public List<Map<String,Object>> getMainWatersJson(@RequestParam(value = "id", defaultValue = "a0") String id,
			@RequestParam(value = "type", defaultValue = "map") String type,
			@RequestParam(value = "showLeaf", defaultValue = "N") String showLeaf){
		String code = id.substring(1);
		String areaCode = "";
		String parentCode = "";
		String url = "";
		String openTab = "Y";
		if("data".equals(type)){
			url = "/monitorData/mainWaterData";
			if("Y".equals(showLeaf)) {
				url = "/monitorData/mainWaterLeafData";
			}
		}else{
			url= "/show/waterMap";
			areaCode = "a";
			parentCode = "w";
		}
		List<Map<String,Object>> tree =  new ArrayList<Map<String,Object>>();
		if(id.startsWith("a")){
			List<AreaEntity> areas= areaService.getAreasByParentCode(code);
			for(AreaEntity area:areas){
				Map<String, Object> map = new HashMap<String, Object>();
				String text = area.getName();
				if(!code.equals("0104")){
					if(text.equals("市水务局")||text.equals("桂林洋开发区")){
						continue;
					}
				}
				text = "<span class='menuTreeParent'>"+text+"</span>" ;
				map.put("id","a"+area.getCode());
				map.put("iconCls", area.getIconCls());
				map.put("text",text);
				map.put("state","closed");
				Map<String,String> attribute = new HashMap<String,String>();
				attribute.put("url", url+"?areaCode="+areaCode+area.getCode());
				if("map".equals(type)) openTab = "N";
				attribute.put("openTab", openTab);
				map.put("attributes",attribute );
				tree.add(map);
			}
			List<WaterEntity> waters= waterService.getWaterByAreaCodeModuleType(code,"2");
			for(WaterEntity water:waters){
				Map<String, Object> map = new HashMap<String, Object>();
				String text = water.getName();
				map.put("id","w"+water.getCode());
				map.put("iconCls", water.getIconCls());
				Map<String,String> attribute = new HashMap<String,String>();
				if(!water.getIsLeaf()){
					map.put("state","closed");
					
					attribute.put("url", url+"?parentCode="+parentCode+water.getCode()
							+"&areaCode="+areaCode+water.getArea().getCode()
							+"&waterId="+parentCode+water.getCode()
							+"&isLeaf="+("Y".equals(showLeaf)?"true":"false"));
				}else{
					attribute.put("url", url+"?code="+parentCode+water.getCode()
							+"&areaCode="+areaCode+water.getArea().getCode()
							+"&waterId="+parentCode+water.getCode()
							+"&isLeaf="+("Y".equals(showLeaf)?"true":"false"));
				}
				map.put("text",text);
				attribute.put("openTab", openTab);
				map.put("attributes",attribute );
				tree.add(map);
			}
		}else if(id.startsWith("w")){
			List<WaterEntity> waters= waterService.getWaterByAreaCodeModuleType(code,"2");
			for(WaterEntity water:waters){
				Map<String, Object> map = new HashMap<String, Object>();
				String text = water.getName();
				map.put("id","w"+water.getCode());
				map.put("iconCls", water.getIconCls());
				
				Map<String,String> attribute = new HashMap<String,String>();
				if(!water.getIsLeaf()){
					map.put("state","closed");
					attribute.put("url", url+"?parentCode="+parentCode+water.getCode()
							+"&isLeaf="+("Y".equals(showLeaf)?"true":"false"));
				}else{
					if("data".equals(type))url = "/monitorData/mainWaterLeafData";
					attribute.put("url", url+"?code="+parentCode+water.getCode()
							+"&parentCode="+parentCode+water.getParentCode()
							+"&areaCode="+areaCode+water.getArea().getCode()
							+"&waterId="+parentCode+water.getCode()
							+"&isLeaf="+("Y".equals(showLeaf)?"true":"false"));
				}
				map.put("text",text);
				attribute.put("openTab", openTab);
				map.put("attributes",attribute );
				if((water.getIsLeaf()&&"Y".equals(showLeaf))
						||(!water.getIsLeaf()))
					tree.add(map);
			}
		}
		return tree;
	}
	/**
	 * 按父节点加载菜单，返回菜单树JSON
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/getSewageTree")
	public List<Map<String,Object>> getSewagesJson(@RequestParam(value = "id", defaultValue = "a0") String id,
			@RequestParam(value = "type", defaultValue = "map") String type,
			@RequestParam(value = "showLeaf", defaultValue = "N") String showLeaf){
		String code = id.substring(1);
		String areaCode = "";
		String parentCode = "";
		String url = "";;
		String openTab = "Y";
		if("data".equals(type)){
			url = "/monitorData/sewageData";
			if("Y".equals(showLeaf)) {
				url = "/monitorData/sewageLeafData";
			}
		}else{
			url= "/show/waterMap";
			areaCode = "a";
			parentCode = "w";
		}
		List<Map<String,Object>> tree =  new ArrayList<Map<String,Object>>();
		if(id.startsWith("a")){
			List<AreaEntity> areas= areaService.getAreasByParentCode(code);
			for(AreaEntity area:areas){
				Map<String, Object> map = new HashMap<String, Object>();
				String text = area.getName();
				if(!code.equals("0104")){
					if(text.equals("市水务局")||text.equals("桂林洋开发区")){
						continue;
					}
				}
				text = "<span class='menuTreeParent'>"+text+"</span>" ;
				map.put("id","a"+area.getCode());
				map.put("iconCls", area.getIconCls());
				map.put("text",text);
				map.put("state","closed");
				Map<String,String> attribute = new HashMap<String,String>();
				attribute.put("url", url+"?areaCode="+areaCode+area.getCode());
				if("map".equals(type)) openTab = "N";
				attribute.put("openTab", openTab);
				map.put("attributes",attribute );
				tree.add(map);
			}
			List<SewageEntity> sewages= sewageService.getSewageByAreaCode(code);
			for(SewageEntity sewage:sewages){
				Map<String, Object> map = new HashMap<String, Object>();
				String text = sewage.getName();
				map.put("id","w"+sewage.getCode());
				map.put("iconCls", sewage.getIconCls());
				Map<String,String> attribute = new HashMap<String,String>();
				attribute.put("url", url+"?code="+parentCode+sewage.getCode()
							+"&areaCode="+areaCode+sewage.getArea().getCode()
							+"&waterId="+parentCode+sewage.getCode()
							+"&isLeaf="+("Y".equals(showLeaf)?"true":"false"));
				map.put("text",text);
				attribute.put("openTab", openTab);
				map.put("attributes",attribute );
				tree.add(map);
			}
		}
		return tree;
	}
	/**
	 * 按父节点加载菜单，返回菜单树JSON
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/getSewagePumpTree")
	public List<Map<String,Object>> getSewagesPumpJson(@RequestParam(value = "id", defaultValue = "a0") String id,
			@RequestParam(value = "type", defaultValue = "map") String type,
			@RequestParam(value = "showLeaf", defaultValue = "N") String showLeaf){
		String code = id.substring(1);
		String areaCode = "";
		String parentCode = "";
		String url = "";;
		String openTab = "Y";
		if("data".equals(type)){
			url = "/monitorData/sewageData";//这条不用了
			if("Y".equals(showLeaf)) {
				url = "/monitorData/sewagePumpLeafData";
			}
		}else{
			url= "/show/waterMap";
			areaCode = "a";
			parentCode = "w";
		}
		List<Map<String,Object>> tree =  new ArrayList<Map<String,Object>>();
		if(id.startsWith("a")){
			List<AreaEntity> areas= areaService.getAreasByParentCode(code);
			for(AreaEntity area:areas){
				Map<String, Object> map = new HashMap<String, Object>();
				String text = area.getName();
				if(!code.equals("0104")){
					if(text.equals("市水务局")||text.equals("桂林洋开发区")){
						continue;
					}
				}
				text = "<span class='menuTreeParent'>"+text+"</span>" ;
				map.put("id","a"+area.getCode());
				map.put("iconCls", area.getIconCls());
				map.put("text",text);
				map.put("state","closed");
				Map<String,String> attribute = new HashMap<String,String>();
				attribute.put("url", url+"?areaCode="+areaCode+area.getCode());
				if("map".equals(type)) openTab = "N";
				attribute.put("openTab", openTab);
				map.put("attributes",attribute );
				tree.add(map);
			}
			List<SewagePumpEntity> sewagePumps= sewagePumpService.getSewagePumpByAreaCode(code);
			for(SewagePumpEntity sewagePump:sewagePumps){
				Map<String, Object> map = new HashMap<String, Object>();
				String text = sewagePump.getName();
				map.put("id","w"+sewagePump.getCode());
				map.put("iconCls", sewagePump.getIconCls());
				Map<String,String> attribute = new HashMap<String,String>();
				attribute.put("url", url+"?code="+parentCode+sewagePump.getCode()
							+"&areaCode="+areaCode+sewagePump.getArea().getCode()
							+"&waterId="+parentCode+sewagePump.getCode()
							+"&isLeaf="+("Y".equals(showLeaf)?"true":"false"));
				map.put("text",text);
				attribute.put("openTab", openTab);
				map.put("attributes",attribute );
				tree.add(map);
			}
		}
		return tree;
	}
	/**
	 * 按父节点加载菜单，返回菜单树JSON
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/getTapWaterTree")
	public List<Map<String,Object>> getTapWaterJson(@RequestParam(value = "id", defaultValue = "a0") String id,
			@RequestParam(value = "type", defaultValue = "map") String type,
			@RequestParam(value = "showLeaf", defaultValue = "N") String showLeaf){
		String code = id.substring(1);
		String areaCode = "";
		String parentCode = "";
		String url = "";;
		String openTab = "Y";
		if("data".equals(type)){
			url = "/monitorData/sewageData";//这条不用了
			if("Y".equals(showLeaf)) {
				url = "/tapWater/tapWaterLeafData";
			}
		}else{
			url= "/show/waterMap";
			areaCode = "a";
			parentCode = "w";
		}
		List<Map<String,Object>> tree =  new ArrayList<Map<String,Object>>();
		if(id.startsWith("a")){
			List<AreaEntity> areas= areaService.getAreasByParentCode(code);
			for(AreaEntity area:areas){
				Map<String, Object> map = new HashMap<String, Object>();
				String text = area.getName();
				if(!code.equals("0104")){
					if(text.equals("市水务局")||text.equals("桂林洋开发区")){
						continue;
					}
				}
				text = "<span class='menuTreeParent'>"+text+"</span>" ;
				map.put("id","a"+area.getCode());
				map.put("iconCls", area.getIconCls());
				map.put("text",text);
				map.put("state","closed");
				Map<String,String> attribute = new HashMap<String,String>();
				attribute.put("url", url+"?areaCode="+areaCode+area.getCode());
				if("map".equals(type)) openTab = "N";
				attribute.put("openTab", openTab);
				map.put("attributes",attribute );
				tree.add(map);
			}
			List<TapWaterEntity> tapWaters= tapWaterService.getTapWaterByAreaCode(code);
			for(TapWaterEntity tapWater:tapWaters){
				Map<String, Object> map = new HashMap<String, Object>();
				String text = tapWater.getName();
				map.put("id","w"+tapWater.getCode());
				map.put("iconCls", tapWater.getIconCls());
				Map<String,String> attribute = new HashMap<String,String>();
				attribute.put("url", url+"?code="+parentCode+tapWater.getCode()
							+"&areaCode="+areaCode+tapWater.getArea().getCode()
							+"&waterId="+parentCode+tapWater.getCode()
							+"&isLeaf="+("Y".equals(showLeaf)?"true":"false"));
				map.put("text",text);
				attribute.put("openTab", openTab);
				map.put("attributes",attribute );
				tree.add(map);
			}
		}
		return tree;
	}
	/**
	 * 按父节点加载菜单，返回菜单树JSON
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/getMediumReservoirTree")
	public List<Map<String,Object>> getMediumReservoirJson(@RequestParam(value = "id", defaultValue = "a0") String id,
			@RequestParam(value = "type", defaultValue = "map") String type,
			@RequestParam(value = "showLeaf", defaultValue = "N") String showLeaf){
		String code = id.substring(1);
		String areaCode = "";
		String parentCode = "";
		String url = "";;
		String openTab = "Y";
		if("data".equals(type)){
			url = "/monitorData/sewageData";//这条不用了
			if("Y".equals(showLeaf)) {
				url = "/mediumReservoir/mediumReservoirLeafData";
			}
		}else{
			url= "/show/waterMap";
			areaCode = "a";
			parentCode = "w";
		}
		List<Map<String,Object>> tree =  new ArrayList<Map<String,Object>>();
		if(id.startsWith("a")){
			List<AreaEntity> areas= areaService.getAreasByParentCode(code);
			for(AreaEntity area:areas){
				Map<String, Object> map = new HashMap<String, Object>();
				String text = area.getName();
				if(!code.equals("0104")){
					if(text.equals("市水务局")||text.equals("桂林洋开发区")){
						continue;
					}
				}
				text = "<span class='menuTreeParent'>"+text+"</span>" ;
				map.put("id","a"+area.getCode());
				map.put("iconCls", area.getIconCls());
				map.put("text",text);
				map.put("state","closed");
				Map<String,String> attribute = new HashMap<String,String>();
				attribute.put("url", url+"?areaCode="+areaCode+area.getCode());
				if("map".equals(type)) openTab = "N";
				attribute.put("openTab", openTab);
				map.put("attributes",attribute );
				tree.add(map);
			}
			List<MediumReservoirEntity> mediumReservoirs= mediumReservoirService.getMediumReservoirByAreaCode(code);
			for(MediumReservoirEntity mediumReservoir:mediumReservoirs){
				Map<String, Object> map = new HashMap<String, Object>();
				String text = mediumReservoir.getName();
				map.put("id","w"+mediumReservoir.getCode());
				map.put("iconCls", mediumReservoir.getIconCls());
				Map<String,String> attribute = new HashMap<String,String>();
				attribute.put("url", url+"?code="+parentCode+mediumReservoir.getCode()
							+"&areaCode="+areaCode+mediumReservoir.getArea().getCode()
							+"&waterId="+parentCode+mediumReservoir.getCode()
							+"&isLeaf="+("Y".equals(showLeaf)?"true":"false"));
				map.put("text",text);
				attribute.put("openTab", openTab);
				map.put("attributes",attribute );
				tree.add(map);
			}
		}
		return tree;
	}
	/**
	 * 按父节点加载菜单，返回菜单树JSON
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/getWaterSourceTree")
	public List<Map<String,Object>> getWaterSourceJson(@RequestParam(value = "id", defaultValue = "a0") String id,
			@RequestParam(value = "type", defaultValue = "map") String type,
			@RequestParam(value = "showLeaf", defaultValue = "N") String showLeaf){
		String code = id.substring(1);
		String areaCode = "";
		String parentCode = "";
		String url = "";;
		String openTab = "Y";
		if("data".equals(type)){
			url = "/monitorData/sewageData";//这条不用了
			if("Y".equals(showLeaf)) {
				url = "/monitorData/waterSourceLeafData";
			}
		}else{
			url= "/show/waterMap";
			areaCode = "a";
			parentCode = "w";
		}
		List<Map<String,Object>> tree =  new ArrayList<Map<String,Object>>();
		if(id.startsWith("a")){
			List<AreaEntity> areas= areaService.getAreasByParentCode(code);
			for(AreaEntity area:areas){
				Map<String, Object> map = new HashMap<String, Object>();
				String text = area.getName();
				if(!code.equals("0104")){
					if(text.equals("市水务局")||text.equals("桂林洋开发区")){
						continue;
					}
				}
				text = "<span class='menuTreeParent'>"+text+"</span>" ;
				map.put("id","a"+area.getCode());
				map.put("iconCls", area.getIconCls());
				map.put("text",text);
				map.put("state","closed");
				Map<String,String> attribute = new HashMap<String,String>();
				attribute.put("url", url+"?areaCode="+areaCode+area.getCode());
				if("map".equals(type)) openTab = "N";
				attribute.put("openTab", openTab);
				map.put("attributes",attribute );
				tree.add(map);
			}
			List<WaterSourceEntity> WaterSources= waterSourceService.getWaterSourceByAreaCode(code);
			for(WaterSourceEntity WaterSource:WaterSources){
				Map<String, Object> map = new HashMap<String, Object>();
				String text = WaterSource.getName();
				map.put("id","w"+WaterSource.getCode());
				map.put("iconCls", WaterSource.getIconCls());
				Map<String,String> attribute = new HashMap<String,String>();
				attribute.put("url", url+"?code="+parentCode+WaterSource.getCode()
							+"&areaCode="+areaCode+WaterSource.getArea().getCode()
							+"&waterId="+parentCode+WaterSource.getCode()
							+"&isLeaf="+("Y".equals(showLeaf)?"true":"false"));
				map.put("text",text);
				attribute.put("openTab", openTab);
				map.put("attributes",attribute );
				tree.add(map);
			}
		}
		return tree;
	}
	/**
	 * 按父节点加载菜单，返回菜单树JSON
	 * 黑臭水体
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/getBlackWaterTree")
	public List<Map<String,Object>> getBlackWatersJson(@RequestParam(value = "id", defaultValue = "a0") String id,
			@RequestParam(value = "type", defaultValue = "map") String type,
			@RequestParam(value = "showLeaf", defaultValue = "N") String showLeaf){
		String code = id.substring(1);
		String areaCode = "";
		String parentCode = "";
		String url = "";
		String openTab = "Y";
		if("data".equals(type)){
			url = "/monitorData/mainWaterData";
			if("Y".equals(showLeaf)) {
				url = "/blackWater/blackWaterLeafData";
			}
		}else{
			url= "/show/waterMap";
			areaCode = "a";
			parentCode = "w";
		}
		List<Map<String,Object>> tree =  new ArrayList<Map<String,Object>>();
		if(id.startsWith("a")){
			List<AreaEntity> areas= areaService.getAreasByParentCode(code);
			for(AreaEntity area:areas){
				Map<String, Object> map = new HashMap<String, Object>();
				String text = area.getName();
				if(code.equals("0104")){
					if(text.equals("桂林洋开发区")){
						continue;
					}
				}
				text = "<span class='menuTreeParent'>"+text+"</span>" ;
				map.put("id","a"+area.getCode());
				map.put("iconCls", area.getIconCls());
				map.put("text",text);
				map.put("state","closed");
				Map<String,String> attribute = new HashMap<String,String>();
				attribute.put("url", url+"?areaCode="+areaCode+area.getCode());
				if("map".equals(type)) openTab = "N";
				attribute.put("openTab", openTab);
				map.put("attributes",attribute );
				tree.add(map);
			}
			List<BlackWaterEntity> blackWaters= blackWaterService.getBlackWaterByAreaCode(code);
			for(BlackWaterEntity blackWater:blackWaters){
				Map<String, Object> map = new HashMap<String, Object>();
				String text = blackWater.getName();
				map.put("id","w"+blackWater.getCode());
				map.put("iconCls", blackWater.getIconCls());
				Map<String,String> attribute = new HashMap<String,String>();
				if(!blackWater.getIsLeaf()){
					map.put("state","closed");
					
					attribute.put("url", url+"?parentCode="+parentCode+blackWater.getCode()
							+"&areaCode="+areaCode+blackWater.getArea().getCode()
							+"&waterId="+parentCode+blackWater.getCode()
							+"&isLeaf="+("Y".equals(showLeaf)?"true":"false"));
				}else{
					attribute.put("url", url+"?code="+parentCode+blackWater.getCode()
							+"&areaCode="+areaCode+blackWater.getArea().getCode()
							+"&waterId="+parentCode+blackWater.getCode()
							+"&isLeaf="+("Y".equals(showLeaf)?"true":"false"));
				}
				map.put("text",text);
				attribute.put("openTab", openTab);
				map.put("attributes",attribute );
				tree.add(map);
			}
		}
		return tree;
	}
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/getStatisticTree")
	public List<Map<String,Object>> getStatisticTree(){
		List<Map<String,Object>> tree =  new ArrayList<Map<String,Object>>();
		for(int i=1;i<4;i++){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id","001"+i);
			map.put("text","水体统计分析"+i);
			map.put("state","open");
			Map<String,String> attribute = new HashMap<String,String>();
			attribute.put("url", "/statistic/example"+i);
			attribute.put("openTab", "Y");
			map.put("attributes", attribute);
			tree.add(map);
		}
		return tree;
	}
	/**
	 * 按父节点加载菜单，返回菜单树JSON
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/getWaterSupplyTree")
	public List<Map<String,Object>> getWaterSupplyJson(@RequestParam(value = "id", defaultValue = "a0") String id,
			@RequestParam(value = "type", defaultValue = "map") String type,
			@RequestParam(value = "showLeaf", defaultValue = "N") String showLeaf){
		String code = id.substring(1);
		String areaCode = "";
		String parentCode = "";
		String url = "";;
		String openTab = "Y";
		if("data".equals(type)){
			url = "/monitorData/sewageData";//这条不用了
			if("Y".equals(showLeaf)) {
				url = "/waterSupply/waterSupplyLeafData";
			}
		}else{
			url= "/show/waterMap";
			areaCode = "a";
			parentCode = "w";
		}
		List<Map<String,Object>> tree =  new ArrayList<Map<String,Object>>();
		if(id.startsWith("a")){
			List<AreaEntity> areas= areaService.getAreasByParentCode(code);
			for(AreaEntity area:areas){
				Map<String, Object> map = new HashMap<String, Object>();
				String text = area.getName();
				if(!code.equals("0104")){
					if(text.equals("市水务局")||text.equals("桂林洋开发区")){
						continue;
					}
				}
				text = "<span class='menuTreeParent'>"+text+"</span>" ;
				map.put("id","a"+area.getCode());
				map.put("iconCls", area.getIconCls());
				map.put("text",text);
				map.put("state","closed");
				Map<String,String> attribute = new HashMap<String,String>();
				attribute.put("url", url+"?areaCode="+areaCode+area.getCode());
				if("map".equals(type)) openTab = "N";
				attribute.put("openTab", openTab);
				map.put("attributes",attribute );
				tree.add(map);
			}
			List<WaterSupplyEntity> waterSupplys= waterSupplyService.getWaterSupplyByAreaCode(code);
			for(WaterSupplyEntity waterSupply:waterSupplys){
				Map<String, Object> map = new HashMap<String, Object>();
				String text = waterSupply.getName();
				map.put("id","w"+waterSupply.getCode());
				map.put("iconCls", waterSupply.getIconCls());
				Map<String,String> attribute = new HashMap<String,String>();
				attribute.put("url", url+"?code="+parentCode+waterSupply.getCode()
							+"&areaCode="+areaCode+waterSupply.getArea().getCode()
							+"&waterId="+parentCode+waterSupply.getCode()
							+"&isLeaf="+("Y".equals(showLeaf)?"true":"false"));
				map.put("text",text);
				attribute.put("openTab", openTab);
				map.put("attributes",attribute );
				tree.add(map);
			}
		}
		return tree;
	}
	/*
	 * 监测数据
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/getMonitorDataById")
	public List<Map<String,Object>> getMonitorDataById(@RequestParam(value = "id", defaultValue = "0") String id){
		List<Map<String,Object>> result =  new ArrayList<Map<String,Object>>();
		List<MonitorData> datas= monitorDataService.getMonitorDataByCode(id);
		for(MonitorData data:datas){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name",data.getItemName());
			map.put("value", data.getItemValue());
			result.add(map);
		}
		return result;
	}
	
	/**
	 * 监测数据文字界面
	 */
	@RequestMapping(method = RequestMethod.GET,value="/mapIndex")
	public String workbench(){
		return "/front/mapIndex";
	}
	
	/**
	 * 主index界面
	 */
	@RequestMapping(method = RequestMethod.GET,value="/index")
	public String index(){
		return "/front/index";
	}
	
	/**
	 * 地图
	 */
	@RequestMapping(method = RequestMethod.GET,value="/waterMap")
	public String waterMap(@RequestParam(value = "waterId", defaultValue = "0") String id,
			@RequestParam(value = "isLeaf", defaultValue = "0") String isLeaf,Model model){
		model.addAttribute("waterId", id);
		model.addAttribute("isLeaf", isLeaf);
		return "/water/waterMap";
	}
	
	/**
	 * 首页介绍
	 */
	@RequestMapping(method = RequestMethod.GET,value="/indexIntroduce")
	public String indexIntroduce(@RequestParam(value = "introUrl", defaultValue = "0") String introUrl){
		return introUrl;
	}
	
	/**
	 * 下拉内容页
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET,value="/accordionPanelContent")
	public String accordionPanelContent(String treeId,String url,String tabName,Model model){
		String view  = "/front/waterTree";
		model.addAttribute("treeId", treeId);
		model.addAttribute("url", url);
		model.addAttribute("tabName", tabName);
		if(url.indexOf("/sys/")>0) view  =  "/front/sysManagerTree";
		else if(url.indexOf("/statisticTree")>0)view  =  "/front/statisticTree";
		else if(url.indexOf("/manageData")>0)view  =  "/front/manageDataTree";
		return view;
	}
}
