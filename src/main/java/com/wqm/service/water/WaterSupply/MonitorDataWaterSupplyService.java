package com.wqm.service.water.WaterSupply;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.wqm.entity.water.waterSupply.MonitorDataWaterSupply;
import com.wqm.repository.water.WaterSupply.MonitorDataWaterSupplyDao;



@Component
@Transactional
public class MonitorDataWaterSupplyService {
	
	@Autowired
	private MonitorDataWaterSupplyDao monitorDataWaterSupplyDao;

	
	/**
	 * 按条件查找监测数据
	 * @parMonitorDataWaterSupplyurn
	 */
	public List<MonitorDataWaterSupply> getMonitorDataWaterSupplyByWaterCodeMonitorCodeTypeCode(String code,String monitorType){
		return monitorDataWaterSupplyDao.getMonitorDataWaterSupplyByWaterCodeMonitorCodeTypeCode(code, monitorType);
	}
	/**
	 * 保存监测数据
	 * @param monitorData
	 * @return
	 */
	public MonitorDataWaterSupply saveMonitorDataWaterSupply(MonitorDataWaterSupply monitorDataWaterSupply){
		return monitorDataWaterSupplyDao.save(monitorDataWaterSupply);
	}
	/**
	 * 按ID查找监测数据
	 * @param id
	 * @return
	 */
	public MonitorDataWaterSupply getMonitorDataWaterSupplyById(Long id){
		return monitorDataWaterSupplyDao.findOne(id);
	}
	/**
	 * 分页查询监测数据
	 * 带查询条件spec
	 * @param pageRequest
	 * @param spec
	 * @return
	 */
	public Page<MonitorDataWaterSupply> getMonitorDatasWaterSupplyByPage(Specification<MonitorDataWaterSupply> spec,PageRequest pageRequest){
		return monitorDataWaterSupplyDao.findAll(spec,pageRequest);
	}
	/**
	 * 删除监测数据
	 * @param ids
	 */
	public void deleMonitorDataWaterSupply(String code,Integer monitorType){
		monitorDataWaterSupplyDao.deleMonitorDataWaterSupplysByCodeMonitorType(code, monitorType);
	}
	/**
	 * get采样时间通过code
	 * @param code
	 */
	public List<Date> getMonitorTimeByWaterSupplyCode(String code){
		return monitorDataWaterSupplyDao.getMonitorTimeByWaterSupplyCode(code);
	}
	public List<MonitorDataWaterSupply> getMonitorDataWaterSupplyByMonitorTime(Date time,String code){
		return monitorDataWaterSupplyDao.getMonitorDataWaterSupplyByMonitorTime(time,code);
	}
}
