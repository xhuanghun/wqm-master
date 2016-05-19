package com.wqm.service.water.BlackWater;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.wqm.entity.water.blackWater.MonitorDataBlackWater;
import com.wqm.repository.water.BlackWater.MonitorDataBlackWaterDao;



@Component
@Transactional
public class MonitorDataBlackWaterService {
	
	@Autowired
	private MonitorDataBlackWaterDao monitorDataBlackWaterDao;

	
	/**
	 * 按条件查找监测数据
	 * @parMonitorDataBlackWaterurn
	 */
	public List<MonitorDataBlackWater> getMonitorDataBlackWaterByWaterCodeMonitorCodeTypeCode(String code,String monitorType){
		return monitorDataBlackWaterDao.getMonitorDataBlackWaterByWaterCodeMonitorCodeTypeCode(code, monitorType);
	}
	/**
	 * 保存监测数据
	 * @param monitorData
	 * @return
	 */
	public MonitorDataBlackWater saveMonitorDataBlackWater(MonitorDataBlackWater monitorDataBlackWater){
		return monitorDataBlackWaterDao.save(monitorDataBlackWater);
	}
	/**
	 * 按ID查找监测数据
	 * @param id
	 * @return
	 */
	public MonitorDataBlackWater getMonitorDataBlackWaterById(Long id){
		return monitorDataBlackWaterDao.findOne(id);
	}
	/**
	 * 分页查询监测数据
	 * 带查询条件spec
	 * @param pageRequest
	 * @param spec
	 * @return
	 */
	public Page<MonitorDataBlackWater> getMonitorDatasBlackWaterByPage(Specification<MonitorDataBlackWater> spec,PageRequest pageRequest){
		return monitorDataBlackWaterDao.findAll(spec,pageRequest);
	}
	/**
	 * 删除监测数据
	 * @param ids
	 */
	public void deleMonitorDataBlackWater(String code,Integer monitorType){
		monitorDataBlackWaterDao.deleMonitorDataBlackWatersByCodeMonitorType(code, monitorType);
	}
	/**
	 * get采样时间通过code
	 * @param code
	 */
	public List<Date> getMonitorTimeByBlackWaterCode(String code){
		return monitorDataBlackWaterDao.getMonitorTimeByBlackWaterCode(code);
	}
	public List<MonitorDataBlackWater> getMonitorDataBlackWaterByMonitorTime(Date time,String code){
		return monitorDataBlackWaterDao.getMonitorDataBlackWaterByMonitorTime(time,code);
	}
}
