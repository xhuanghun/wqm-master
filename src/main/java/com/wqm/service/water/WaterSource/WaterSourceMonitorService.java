package com.wqm.service.water.WaterSource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.wqm.entity.water.waterSource.WaterSourceMonitorEntity;
import com.wqm.repository.water.WaterSource.WaterSourceMonitorDao;


@Component
@Transactional
public class WaterSourceMonitorService {
	
	@Autowired
	private WaterSourceMonitorDao waterSourceMonitorDao;
	
	
	/**
	 * 保存监测项对应关系
	 * @param water
	 * @return
	 */
	public WaterSourceMonitorEntity saveWaterSourceMonitor(WaterSourceMonitorEntity waterSourceMonitor){
		return waterSourceMonitorDao.save(waterSourceMonitor);
	}
	
	/**
	 * 删除水体
	 * @param ids
	 */
	public void deleByWaterSourceCodeMonitor(String waterSourceCode,String monitorType){
		waterSourceMonitorDao.deleByWaterSourceCodeMonitor(waterSourceCode,monitorType);
	}
	/*
	 * 按code找到对应关系
	 */
	public List<WaterSourceMonitorEntity> getWaterSourceMonitorDataByCode(String code,String monitorType){
		return waterSourceMonitorDao.getWaterSourceMonitorDataByWaterSourceCode(code,monitorType);
	}
}
