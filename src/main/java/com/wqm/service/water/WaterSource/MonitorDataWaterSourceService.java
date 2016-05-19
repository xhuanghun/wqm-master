package com.wqm.service.water.WaterSource;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.wqm.entity.water.waterSource.MonitorDataWaterSource;
import com.wqm.repository.water.WaterSource.MonitorDataWaterSourceDao;



@Component
@Transactional
public class MonitorDataWaterSourceService {
	
	@Autowired
	private MonitorDataWaterSourceDao monitorDataWaterSourceDao;

	
	/**
	 * 按条件查找监测数据
	 * @parMonitorDataWaterSourceurn
	 */
	public List<MonitorDataWaterSource> getMonitorDataWaterSourceByWaterCodeMonitorCodeTypeCode(String code,String monitorType){
		return monitorDataWaterSourceDao.getMonitorDataWaterSourceByWaterCodeMonitorCodeTypeCode(code, monitorType);
	}
	/**
	 * 保存监测数据
	 * @param monitorData
	 * @return
	 */
	public MonitorDataWaterSource saveMonitorDataWaterSource(MonitorDataWaterSource monitorDataWaterSource){
		return monitorDataWaterSourceDao.save(monitorDataWaterSource);
	}
	/**
	 * 按ID查找监测数据
	 * @param id
	 * @return
	 */
	public MonitorDataWaterSource getMonitorDataWaterSourceById(Long id){
		return monitorDataWaterSourceDao.findOne(id);
	}
	/**
	 * 分页查询监测数据
	 * 带查询条件spec
	 * @param pageRequest
	 * @param spec
	 * @return
	 */
	public Page<MonitorDataWaterSource> getMonitorDatasWaterSourceByPage(Specification<MonitorDataWaterSource> spec,PageRequest pageRequest){
		return monitorDataWaterSourceDao.findAll(spec,pageRequest);
	}
	/**
	 * 删除监测数据
	 * @param ids
	 */
	public void deleMonitorDataWaterSource(String code,Integer monitorType){
		monitorDataWaterSourceDao.deleMonitorDataWaterSourcesByCodeMonitorType(code, monitorType);
	}
	/**
	 * get采样时间通过code
	 * @param code
	 */
	public List<Date> getMonitorTimeByWaterSourceCode(String code){
		return monitorDataWaterSourceDao.getMonitorTimeByWaterSourceCode(code);
	}
	public List<MonitorDataWaterSource> getMonitorDataWaterSourceByMonitorTime(Date time,String code){
		return monitorDataWaterSourceDao.getMonitorDataWaterSourceByMonitorTime(time,code);
	}
}
