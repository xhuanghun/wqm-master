package com.wqm.service.water;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.wqm.entity.water.MonitorData;
import com.wqm.entity.water.MonitorDataNew;
import com.wqm.repository.water.MonitorDataDao;
import com.wqm.repository.water.MonitorDataNewDao;



@Component
@Transactional
public class MonitorDataService {
	
	@Autowired
	private MonitorDataDao monitorDataDao;
	@Autowired
	private MonitorDataNewDao monitorDataNewDao;
	@PersistenceContext
	private EntityManager entityManager;
	
	/**
	 * 按ID查找监测数据
	 * @param id
	 * @return
	 */
	public MonitorData getMonitorDataById(Long id){
		return monitorDataDao.findOne(id);
	}
	
	public List<MonitorData> getMonitorDataByCode(String code){
		return monitorDataDao.getMonitorDataByWaterCode(code);
	}
	/**
	 * get采样时间通过code
	 * @param code
	 */
	public List<Date> getMonitorTimeByWaterCode(String code){
		return monitorDataDao.getMonitorTimeByWaterCode(code);
	}
	/**
	 * 通过时间获取实体
	 * @param code
	 */
	public List<MonitorData> getMonitorDataByMonitorTime(Date time,String code){
		return monitorDataDao.getMonitorDataByMonitorTime(time,code);
	}
	/**
	 * 按条件查找监测数据
	 * @param id
	 * @return
	 */
	public List<MonitorDataNew> getMonitorDataNewByWaterCodeMonitorCodeTypeCode(String code,String monitorType,String type){
		return monitorDataNewDao.getMonitorDataNewByWaterCodeMonitorCodeTypeCode(code, monitorType, type);
	}
	/**
	 * 获取全部监测数据
	 * @return
	 */
	public List<MonitorData> getAllMonitorDatas(){
		return (List<MonitorData>) monitorDataDao.findAll();
	}
	
	/**
	 * 分页查询监测数据
	 * 带查询条件spec
	 * @param pageRequest
	 * @param spec
	 * @return
	 */
	public Page<MonitorData> getMonitorDatasByPage(Specification<MonitorData> spec,PageRequest pageRequest){
		return monitorDataDao.findAll(spec,pageRequest);
	}
	
	/**
	 * 保存监测数据
	 * @param monitorData
	 * @return
	 */
	public MonitorData saveMonitorData(MonitorData monitorData){
		return monitorDataDao.save(monitorData);
	}
	/**
	 * 保存监测数据
	 * @param monitorData
	 * @return
	 */
	public MonitorDataNew saveMonitorDataNew(MonitorDataNew monitorDataNew){
		return monitorDataNewDao.save(monitorDataNew);
	}
	/**
	 * 按ID查找监测数据
	 * @param id
	 * @return
	 */
	public MonitorDataNew getMonitorDataNewById(Long id){
		return monitorDataNewDao.findOne(id);
	}
	/**
	 * 分页查询监测数据
	 * 带查询条件spec
	 * @param pageRequest
	 * @param spec
	 * @return
	 */
	public Page<MonitorDataNew> getMonitorDatasNewByPage(Specification<MonitorDataNew> spec,PageRequest pageRequest){
		return monitorDataNewDao.findAll(spec,pageRequest);
	}
	/**
	 * 删除监测数据
	 * @param ids
	 */
	public void deleMonitorDataNew(String code,Integer type,Integer monitorType){
		monitorDataNewDao.deleMonitorDataNewsByCodeMonitorType(code, type, monitorType);
	}
	/**
	 * get采样时间通过code
	 * @param code
	 */
	public List<Date> getMonitorTimeBySewageCode(String code){
		return monitorDataNewDao.getMonitorTimeBySewageCode(code);
	}
	public List<MonitorDataNew> getMonitorDataNewByMonitorTime(Date time,String code){
		return monitorDataNewDao.getMonitorDataNewByMonitorTime(time,code);
	}
	/**
	 * 删除监测数据
	 * @param ids
	 */
	public void deleMonitorData(List<Long> ids){
		monitorDataDao.deleMonitorDatasByIds(ids);
	}
	/**
	 * 删除监测数据(因为用jpa封装的删除方法报错了，具体原因写在monitorDataDao类里面了）
	 * @param ids
	 */
	public Integer deleMonitorDataByCodeMonitorType(String code,String monitorType){
		Integer successNum=this.entityManager.createNativeQuery("DELETE FROM busi_wtr_monitordata WHERE  water_code='"+code+"' and monitor_type='"+monitorType+"'").executeUpdate();
		return  successNum;
	}
}
