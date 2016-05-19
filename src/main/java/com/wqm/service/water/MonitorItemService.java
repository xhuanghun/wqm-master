package com.wqm.service.water;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.wqm.common.tags.PageTag;
import com.wqm.common.util.CommonUtil;
import com.wqm.entity.water.MonitorItem;
import com.wqm.repository.water.MonitorItemDao;



@Component
@Transactional
public class MonitorItemService {
	
	@Autowired
	private MonitorItemDao monitorItemDao;
	
	@PersistenceContext
	private EntityManager entityManager;
	/**
	 * 按ID查找监测项
	 * @param id
	 * @return
	 */
	public MonitorItem getMonitorItemById(Long id){
		return monitorItemDao.findOne(id);
	}
	
	/**
	 * 获取全部监测项
	 * @return
	 */
	public List<MonitorItem> getAllMonitorItems(){
		return (List<MonitorItem>) monitorItemDao.findAll();
	}
	
	/**
	 * 按codes获取全部监测项
	 * @return
	 */
	public List<MonitorItem> getMonitorItemsByCodes(List<String> codes){
		return (List<MonitorItem>) monitorItemDao.getMonitorItemsByCodes(codes);
	}
	
	/**
	 * 按code获取监测项
	 * @return
	 */
	public MonitorItem getMonitorItemByCode(String code){
		return (MonitorItem) monitorItemDao.getMonitorItemByCode(code);
	}
	/**
	 * 按name获取监测项
	 * @return
	 */
	public MonitorItem getMonitorItemByName(String name){
		return  monitorItemDao.getMonitorItemByName(name);
	}
	
	/**
	 * 分页查询监测项
	 * 带查询条件spec
	 * @param pageRequest
	 * @param spec
	 * @return
	 */
	public Page<MonitorItem> getMonitorItemsByPage(Specification<MonitorItem> spec,PageRequest pageRequest){
		return monitorItemDao.findAll(spec,pageRequest);
	}
	
	/**
	 * 保存监测项
	 * @param monitorItem
	 * @return
	 */
	public MonitorItem saveMonitorItem(MonitorItem monitorItem){
		return monitorItemDao.save(monitorItem);
	}
	
	/**
	 * 删除监测项
	 * @param ids
	 */
	public void deleMonitorItem(List<Long> ids){
		monitorItemDao.deleMonitorItemsByIds(ids);
	}
	/**
	 * 按userId获取全部监测项
	 * @return
	 */
	/*
	 * 监测项内容查找的sql语句
	 */
	private String createMonitorByUserIdQuery() {
		StringBuffer sb = new StringBuffer();
			sb.append(" select  water_name,monitor_date,max(water_code) water_code,max(create_date) create_date,max(update_date) update_date,");
			sb.append(" max(CASE item_name WHEN'溶解氧' THEN item_value ELSE 0 END) dissolvedOxyge,");
			sb.append(" max(CASE item_name WHEN'PH值' THEN item_value ELSE 0 END) PH,");
			sb.append(" max(CASE item_name WHEN'水温' THEN item_value ELSE 0 END) waterTemperature,");
			sb.append(" max(CASE item_name WHEN'高锰酸盐指数' THEN item_value ELSE 0 END) permanganate,");
			sb.append(" max(CASE item_name WHEN'总磷' THEN item_value ELSE 0 END) phosphorusTotal,");
			sb.append(" max(CASE item_name WHEN'氨氮' THEN item_value ELSE 0 END) ammoniaNitrogen,");
			sb.append(" max(CASE item_name WHEN'氯化物' THEN item_value ELSE 0 END) chloride,");
			sb.append(" max(CASE item_name WHEN'盐度' THEN item_value ELSE 0 END) salinity,");
			sb.append(" max(CASE item_name WHEN'电导率' THEN item_value ELSE 0 END) conductivity,");
			sb.append(" max(CASE item_name WHEN'浑浊度' THEN item_value ELSE 0 END) turbidity,");
			sb.append(" max(CASE item_name WHEN'色度' THEN item_value ELSE 0 END) chroma,");
			sb.append(" max(CASE item_name WHEN'化学需氧量' THEN item_value ELSE 0 END) chemicalOxygen,");
			sb.append(" max(CASE item_name WHEN'臭和味' THEN item_value ELSE 0 END) stink,");
			sb.append(" max(CASE item_name WHEN'总氮' THEN item_value ELSE 0 END)  nitrogenTotal,");
			sb.append(" max(CASE item_name WHEN'粪大肠菌群' THEN item_value ELSE 0 END) fecalFlora,");
			sb.append(" max(CASE item_name WHEN'透明度' THEN item_value ELSE 0 END) transparency,");
			sb.append(" max(CASE item_name WHEN'叶绿素a' THEN item_value ELSE 0 END) chlorophyllA,");
			sb.append(" max(CASE item_name WHEN'氧化还原电' THEN item_value ELSE 0 END) redox");
			sb.append(" FROM busi_wtr_monitordata GROUP BY water_name,monitor_date");
		return sb.toString();
	}

	@SuppressWarnings("unchecked")
	public PageTag getMonitorByUserId(int page, int pageRows){
		PageTag data=new PageTag();
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		String listSql = this.createMonitorByUserIdQuery();
		Query listQuery = this.entityManager.createNativeQuery(listSql);
		listQuery.setFirstResult((page - 1) * pageRows);
		listQuery.setMaxResults(pageRows);
		List<Object[]> objList = listQuery.getResultList();
		for(Object[] objs : objList) {
			Map<String, String> view = new HashMap<String, String>();
				view.put("water_name", String.valueOf(objs[0]));
				view.put("monitor_date", CommonUtil.getDateFormat(String.valueOf(objs[1])));
				view.put("water_code", String.valueOf(objs[2]));
				view.put("create_date", String.valueOf(objs[3]));
				view.put("update_date", CommonUtil.getDateFormat(String.valueOf(objs[4])));
				view.put("dissolvedOxyge", String.valueOf(objs[5]));
				view.put("PH", String.valueOf(objs[6]));
				view.put("waterTemperature", String.valueOf(objs[7]));
				view.put("permanganate", String.valueOf(objs[8]));
				view.put("phosphorusTotal", String.valueOf(objs[9]));
				view.put("ammoniaNitrogen", String.valueOf(objs[10]));
				view.put("chloride", String.valueOf(objs[11]));
				view.put("salinity", String.valueOf(objs[12]));
				view.put("conductivity", String.valueOf(objs[13]));
				view.put("turbidity", String.valueOf(objs[14]));
				view.put("chroma", String.valueOf(objs[15]));
				view.put("chemicalOxygen", String.valueOf(objs[16]));
				view.put("stink", String.valueOf(objs[17]));
				view.put("nitrogenTotal", String.valueOf(objs[18]));
				view.put("fecalFlora", String.valueOf(objs[19]));
				view.put("transparency", String.valueOf(objs[20]));
				view.put("chlorophyllA", String.valueOf(objs[21]));
				view.put("redox", String.valueOf(objs[22]));
				list.add(view);
		}
		data.setRows(list);
		data.setTotal(objList.size());
		return  data;
	}
	/**
	 * 按userId获取全部监测项
	 * @return
	 */
	/*
	 * 监测项内容查找的sql语句
	 */
	private String createMonitorByCodeQuery(String code,String monitorType,String addOrupdate) {
		StringBuffer sb = new StringBuffer();
		if("update".equals(addOrupdate)){
			sb.append("  select item_name,item_value,id ");
			sb.append("  from busi_wtr_monitordata where water_code='"+code+"' and monitor_type='"+monitorType+"'");
		}else{
			sb.append(" select wm.name,wm.ename ");
			sb.append(" from busi_wtr_watermonitoritem wwm left join busi_wtr_monitoritem wm on wwm.moniter_item_code=wm.code where wwm.water_code='"+code+"'  and monitor_type_code='"+monitorType+"' ");
		}
		return sb.toString();
	}

	@SuppressWarnings("unchecked")
	public List<Map<String,String>> getMonitorByCode(String code,String monitorType,String addOrupdate){
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		String listSql = this.createMonitorByCodeQuery(code,monitorType,addOrupdate);
		Query listQuery = this.entityManager.createNativeQuery(listSql);
		List<Object[]> objList = listQuery.getResultList();
		for(Object[] objs : objList) {
			Map<String, String> view = new HashMap<String, String>();
				view.put("name", String.valueOf(objs[0]).replace("null", ""));
				if("update".equals(addOrupdate)){
					view.put("value",String.valueOf(objs[1]).replace("null", ""));
					view.put("id",String.valueOf(objs[2]).replace("null", ""));
				}else{
					view.put("ename",String.valueOf(objs[1]).replace("null", ""));
				}
				list.add(view);
		}
		return  list;
	}
	/**
	 * 按code,monitorType,type获取全部监测项
	 * @return
	 */
	private String createMonitorSewageByCodeQuery(String code,String monitorType,String addOrupdate) {
		StringBuffer sb = new StringBuffer();
		if("1".equals(addOrupdate)){
			sb.append(" select item_name,item_value,id ");
			sb.append("  from busi_wtr_monitorda_sewagepump where sewage_pump_code='"+code+"' and monitor_type='"+monitorType+"'");
		}else{
			sb.append(" select wm.name,wm.ename ");
			sb.append(" from busi_wtr_sewagepumpmonitoritem wwm left join busi_wtr_monitoritem wm on wwm.moniter_item_code=wm.code where wwm.sewage_pump_code='"+code+"' and wwm.monitor_type_code='"+monitorType+"' ");
		}
		return sb.toString();
	}

	@SuppressWarnings("unchecked")
	public List<Map<String,String>> getMonitorSewageByCode(String code,String monitorType,String type,String addOrupdate){
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		String listSql = this.createMonitorSewageByCodeQuery(code,monitorType,type,addOrupdate);
		Query listQuery = this.entityManager.createNativeQuery(listSql);
		List<Object[]> objList = listQuery.getResultList();
		for(Object[] objs : objList) {
			Map<String, String> view = new HashMap<String, String>();
				view.put("name", String.valueOf(objs[0]).replace("null", ""));
				if("1".equals(addOrupdate)){
					view.put("value",String.valueOf(objs[1]).replace("null", ""));
					view.put("id",String.valueOf(objs[2]).replace("null", ""));
				}else{
					view.put("ename",String.valueOf(objs[1]).replace("null", ""));
				}
				list.add(view);
		}
		return  list;
	}
	/**
	 * 按code,monitorType,type获取全部监测项
	 * @return
	 */
	private String createMonitorSewageByCodeQuery(String code,String monitorType,String type,String addOrupdate) {
		StringBuffer sb = new StringBuffer();
		if("1".equals(addOrupdate)){
			sb.append(" select item_name,item_value,id ");
			sb.append("  from busi_wtr_monitordatanew where water_code='"+code+"' and monitor_type='"+monitorType+"' and type='"+type+"'");
		}else{
			sb.append(" select wm.name,wm.ename ");
			sb.append(" from busi_wtr_sewagemonitoritem wwm left join busi_wtr_monitoritem wm on wwm.moniter_item_code=wm.code where wwm.sewage_code='"+code+"' and wwm.monitor_type_code='"+monitorType+"' and wwm.type_code='"+type+"'");
		}
		return sb.toString();
	}

	@SuppressWarnings("unchecked")
	public List<Map<String,String>> getMonitorSewagePumpByCode(String code,String monitorType,String addOrupdate){
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		String listSql = this.createMonitorSewageByCodeQuery(code,monitorType,addOrupdate);
		Query listQuery = this.entityManager.createNativeQuery(listSql);
		List<Object[]> objList = listQuery.getResultList();
		for(Object[] objs : objList) {
			Map<String, String> view = new HashMap<String, String>();
				view.put("name", String.valueOf(objs[0]).replace("null", ""));
				if("1".equals(addOrupdate)){
					view.put("value",String.valueOf(objs[1]).replace("null", ""));
					view.put("id",String.valueOf(objs[2]).replace("null", ""));
				}else{
					view.put("ename",String.valueOf(objs[1]).replace("null", ""));
				}
				list.add(view);
		}
		return  list;
	}
	/**
	 * 按code,monitorType,type获取全部监测项
	 * @return
	 */
	private String createMonitorWaterSourceByCodeQuery(String code,String monitorType,String addOrupdate) {
		StringBuffer sb = new StringBuffer();
		if("1".equals(addOrupdate)){
			sb.append(" select item_name,item_value,id ");
			sb.append("  from busi_wtr_monitor_watersource where water_source_code='"+code+"' and monitor_type='"+monitorType+"'");
		}else{
			sb.append(" select wm.name,wm.ename ");
			sb.append(" from busi_wtr_watersourcemonitoritem wwm left join busi_wtr_monitoritem wm on wwm.moniter_item_code=wm.code where wwm.water_source_code='"+code+"' and wwm.monitor_type_code='"+monitorType+"'");
		}
		return sb.toString();
	}

	@SuppressWarnings("unchecked")
	public List<Map<String,String>> getMonitorWaterSourceByCode(String code,String monitorType,String addOrupdate){
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		String listSql = this.createMonitorWaterSourceByCodeQuery(code,monitorType,addOrupdate);
		Query listQuery = this.entityManager.createNativeQuery(listSql);
		List<Object[]> objList = listQuery.getResultList();
		for(Object[] objs : objList) {
			Map<String, String> view = new HashMap<String, String>();
				view.put("name", String.valueOf(objs[0]).replace("null", ""));
				if("1".equals(addOrupdate)){
					view.put("value",String.valueOf(objs[1]).replace("null", ""));
					view.put("id",String.valueOf(objs[2]).replace("null", ""));
				}else{
					view.put("ename",String.valueOf(objs[1]).replace("null", ""));
				}
				list.add(view);
		}
		return  list;
	}
	/**
	 * 按code,monitorType,type获取全部监测项
	 * @return
	 */
	private String createMonitorTapWaterByCodeQuery(String code,String monitorType,String addOrupdate) {
		StringBuffer sb = new StringBuffer();
		if("1".equals(addOrupdate)){
			sb.append(" select item_name,item_value,id ");
			sb.append("  from busi_wtr_monitorda_tapwater where tap_water_code='"+code+"' and monitor_type='"+monitorType+"'");
		}else{
			sb.append(" select wm.name,wm.ename ");
			sb.append(" from busi_wtr_tapwatermonitoritem wwm left join busi_wtr_monitoritem wm on wwm.moniter_item_code=wm.code where wwm.tap_water_code='"+code+"' and wwm.monitor_type_code='"+monitorType+"'");
		}
		return sb.toString();
	}

	@SuppressWarnings("unchecked")
	public List<Map<String,String>> getMonitorTapWaterByCode(String code,String monitorType,String addOrupdate){
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		String listSql = this.createMonitorTapWaterByCodeQuery(code,monitorType,addOrupdate);
		Query listQuery = this.entityManager.createNativeQuery(listSql);
		List<Object[]> objList = listQuery.getResultList();
		for(Object[] objs : objList) {
			Map<String, String> view = new HashMap<String, String>();
				view.put("name", String.valueOf(objs[0]).replace("null", ""));
				if("1".equals(addOrupdate)){
					view.put("value",String.valueOf(objs[1]).replace("null", ""));
					view.put("id",String.valueOf(objs[2]).replace("null", ""));
				}else{
					view.put("ename",String.valueOf(objs[1]).replace("null", ""));
				}
				list.add(view);
		}
		return  list;
	}
	/**
	 * 按code,monitorType,type获取全部监测项
	 * @return
	 */
	private String createMonitorMediumReservoirByCodeQuery(String code,String monitorType,String addOrupdate) {
		StringBuffer sb = new StringBuffer();
		if("1".equals(addOrupdate)){
			sb.append(" select item_name,item_value,id ");
			sb.append("  from busi_wtr_monitor_mediumreservoir where medium_reservoir_code='"+code+"' and monitor_type='"+monitorType+"'");
		}else{
			sb.append(" select wm.name,wm.ename ");
			sb.append(" from busi_wtr_mediumreservoirmonitoritem wwm left join busi_wtr_monitoritem wm on wwm.moniter_item_code=wm.code where wwm.medium_reservoir_code='"+code+"' and wwm.monitor_type_code='"+monitorType+"'");
		}
		return sb.toString();
	}

	@SuppressWarnings("unchecked")
	public List<Map<String,String>> getMonitorMediumReservoirByCode(String code,String monitorType,String addOrupdate){
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		String listSql = this.createMonitorMediumReservoirByCodeQuery(code,monitorType,addOrupdate);
		Query listQuery = this.entityManager.createNativeQuery(listSql);
		List<Object[]> objList = listQuery.getResultList();
		for(Object[] objs : objList) {
			Map<String, String> view = new HashMap<String, String>();
				view.put("name", String.valueOf(objs[0]).replace("null", ""));
				if("1".equals(addOrupdate)){
					view.put("value",String.valueOf(objs[1]).replace("null", ""));
					view.put("id",String.valueOf(objs[2]).replace("null", ""));
				}else{
					view.put("ename",String.valueOf(objs[1]).replace("null", ""));
				}
				list.add(view);
		}
		return  list;
	}
	/**
	 * 按code,monitorType,type获取全部监测项
	 * @return
	 */
	private String createMonitorBlackWaterByCodeQuery(String code,String monitorType,String addOrupdate) {
		StringBuffer sb = new StringBuffer();
		if("1".equals(addOrupdate)){
			sb.append(" select item_name,item_value,id ");
			sb.append("  from busi_wtr_monitor_blackwater where black_water_code='"+code+"' and monitor_type='"+monitorType+"'");
		}else{
			sb.append(" select wm.name,wm.ename ");
			sb.append(" from busi_wtr_blackwatermonitoritem wwm left join busi_wtr_monitoritem wm on wwm.moniter_item_code=wm.code where wwm.black_water_code='"+code+"' and wwm.monitor_type_code='"+monitorType+"'");
		}
		return sb.toString();
	}

	@SuppressWarnings("unchecked")
	public List<Map<String,String>> getMonitorBlackWaterByCode(String code,String monitorType,String addOrupdate){
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		String listSql = this.createMonitorBlackWaterByCodeQuery(code,monitorType,addOrupdate);
		Query listQuery = this.entityManager.createNativeQuery(listSql);
		List<Object[]> objList = listQuery.getResultList();
		for(Object[] objs : objList) {
			Map<String, String> view = new HashMap<String, String>();
				view.put("name", String.valueOf(objs[0]).replace("null", ""));
				if("1".equals(addOrupdate)){
					view.put("value",String.valueOf(objs[1]).replace("null", ""));
					view.put("id",String.valueOf(objs[2]).replace("null", ""));
				}else{
					view.put("ename",String.valueOf(objs[1]).replace("null", ""));
				}
				list.add(view);
		}
		return  list;
	}
	/**
	 * 按code,monitorType,type获取全部监测项
	 * @return
	 */
	private String createMonitorWaterSupplyByCodeQuery(String code,String monitorType,String addOrupdate) {
		StringBuffer sb = new StringBuffer();
		if("1".equals(addOrupdate)){
			sb.append(" select item_name,item_value,id ");
			sb.append("  from busi_wtr_monitor_watersupply where water_supply_code='"+code+"' and monitor_type='"+monitorType+"'");
		}else{
			sb.append(" select wm.name,wm.ename ");
			sb.append(" from busi_wtr_watersupplymonitoritem wwm left join busi_wtr_monitoritem wm on wwm.moniter_item_code=wm.code where wwm.water_supply_code='"+code+"' and wwm.monitor_type_code='"+monitorType+"'");
		}
		return sb.toString();
	}

	@SuppressWarnings("unchecked")
	public List<Map<String,String>> getMonitorWaterSupplyByCode(String code,String monitorType,String addOrupdate){
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		String listSql = this.createMonitorWaterSupplyByCodeQuery(code,monitorType,addOrupdate);
		Query listQuery = this.entityManager.createNativeQuery(listSql);
		List<Object[]> objList = listQuery.getResultList();
		for(Object[] objs : objList) {
			Map<String, String> view = new HashMap<String, String>();
				view.put("name", String.valueOf(objs[0]).replace("null", ""));
				if("1".equals(addOrupdate)){
					view.put("value",String.valueOf(objs[1]).replace("null", ""));
					view.put("id",String.valueOf(objs[2]).replace("null", ""));
				}else{
					view.put("ename",String.valueOf(objs[1]).replace("null", ""));
				}
				list.add(view);
		}
		return  list;
	}
	/*
	 * 删除检测项数据
	 */
	public Integer deleteMonitorByCodeUserIdCreadeDate(Long UserId,String Code,String CreateDate){
		Integer successNum=this.entityManager.createNativeQuery("DELETE FROM busi_wtr_monitordata WHERE  user_id="+UserId+" and water_code='"+Code+"' and create_date='"+CreateDate+"'").executeUpdate();
		return  successNum;
	}
	/*
	 * 更新监测项数据
	 */
	public Integer updateMonitorByCodeUserIdCreadeDate(Long UserId,String Code,String CreateDate,String ItemName,String itemValue,String date){
		String Sql="UPDATE busi_wtr_monitordata SET item_value="+itemValue+",update_date='"+date+"'   WHERE  user_id="+Code+" and water_code='"+Code+"' and create_date='"+CreateDate+"' and item_name='"+ItemName+"'";
		Integer successNum=this.entityManager.createNativeQuery("UPDATE busi_wtr_monitordata SET item_value="+itemValue+",update_date='"+date+"'   WHERE  user_id="+UserId+" and water_code='"+Code+"' and create_date='"+CreateDate+"' and item_name='"+ItemName+"'").executeUpdate();
		return  successNum;
	}
}
