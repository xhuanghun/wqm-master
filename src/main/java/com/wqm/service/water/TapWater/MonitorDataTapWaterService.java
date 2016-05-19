package com.wqm.service.water.TapWater;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.wqm.entity.water.tapWater.MonitorDataTapWater;
import com.wqm.repository.water.TapWater.MonitorDataTapWaterDao;



@Component
@Transactional
public class MonitorDataTapWaterService {
	
	@Autowired
	private MonitorDataTapWaterDao monitorDataTapWaterDao;

	
	/**
	 * 按条件查找监测数据
	 * @parMonitorDataTapWaterurn
	 */
	public List<MonitorDataTapWater> getMonitorDataTapWaterByWaterCodeMonitorCodeTypeCode(String code,String monitorType){
		return monitorDataTapWaterDao.getMonitorDataTapWaterByWaterCodeMonitorCodeTypeCode(code, monitorType);
	}
	/**
	 * 保存监测数据
	 * @param monitorData
	 * @return
	 */
	public MonitorDataTapWater saveMonitorDataTapWater(MonitorDataTapWater monitorDataTapWater){
		return monitorDataTapWaterDao.save(monitorDataTapWater);
	}
	/**
	 * 按ID查找监测数据
	 * @param id
	 * @return
	 */
	public MonitorDataTapWater getMonitorDataTapWaterById(Long id){
		return monitorDataTapWaterDao.findOne(id);
	}
	/**
	 * 分页查询监测数据
	 * 带查询条件spec
	 * @param pageRequest
	 * @param spec
	 * @return
	 */
	public Page<MonitorDataTapWater> getMonitorDatasTapWaterByPage(Specification<MonitorDataTapWater> spec,PageRequest pageRequest){
		return monitorDataTapWaterDao.findAll(spec,pageRequest);
	}
	/**
	 * 删除监测数据
	 * @param ids
	 */
	public void deleMonitorDataTapWater(String code,Integer monitorType){
		monitorDataTapWaterDao.deleMonitorDataTapWatersByCodeMonitorType(code, monitorType);
	}
	/**
	 * get采样时间通过code
	 * @param code
	 */
	public List<Date> getMonitorTimeByTapWaterCode(String code){
		return monitorDataTapWaterDao.getMonitorTimeByTapWaterCode(code);
	}
	public List<MonitorDataTapWater> getMonitorDataTapWaterByMonitorTime(Date time,String code){
		return monitorDataTapWaterDao.getMonitorDataTapWaterByMonitorTime(time,code);
	}
}
