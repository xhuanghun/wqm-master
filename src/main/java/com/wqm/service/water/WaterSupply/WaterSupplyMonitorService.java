package com.wqm.service.water.WaterSupply;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.wqm.entity.water.waterSupply.WaterSupplyMonitorEntity;
import com.wqm.repository.water.WaterSupply.WaterSupplyMonitorDao;


@Component
@Transactional
public class WaterSupplyMonitorService {
	
	@Autowired
	private WaterSupplyMonitorDao waterSupplyMonitorDao;
	
	
	/**
	 * 保存监测项对应关系
	 * @param water
	 * @return
	 */
	public WaterSupplyMonitorEntity saveWaterSupplyMonitor(WaterSupplyMonitorEntity waterSupplyMonitor){
		return waterSupplyMonitorDao.save(waterSupplyMonitor);
	}
	
	/**
	 * 删除水体
	 * @param ids
	 */
	public void deleByWaterSupplyCodeMonitor(String waterSupplyCode,String monitorType){
		waterSupplyMonitorDao.deleByWaterSupplyCodeMonitor(waterSupplyCode,monitorType);
	}
	/*
	 * 按code找到对应关系
	 */
	public List<WaterSupplyMonitorEntity> getWaterSupplyMonitorDataByCode(String code,String monitorType){
		return waterSupplyMonitorDao.getWaterSupplyMonitorDataByWaterSupplyCode(code,monitorType);
	}
}
