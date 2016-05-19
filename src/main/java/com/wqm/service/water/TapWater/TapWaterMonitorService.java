package com.wqm.service.water.TapWater;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.wqm.entity.water.tapWater.TapWaterMonitorEntity;
import com.wqm.repository.water.TapWater.TapWaterMonitorDao;


@Component
@Transactional
public class TapWaterMonitorService {
	
	@Autowired
	private TapWaterMonitorDao tapWaterMonitorDao;
	
	
	/**
	 * 保存监测项对应关系
	 * @param water
	 * @return
	 */
	public TapWaterMonitorEntity saveTapWaterMonitor(TapWaterMonitorEntity tapWaterMonitor){
		return tapWaterMonitorDao.save(tapWaterMonitor);
	}
	
	/**
	 * 删除水体
	 * @param ids
	 */
	public void deleByTapWaterCodeMonitor(String tapWaterCode,String monitorType){
		tapWaterMonitorDao.deleByTapWaterCodeMonitor(tapWaterCode,monitorType);
	}
	/*
	 * 按code找到对应关系
	 */
	public List<TapWaterMonitorEntity> getTapWaterMonitorDataByCode(String code,String monitorType){
		return tapWaterMonitorDao.getTapWaterMonitorDataByTapWaterCode(code,monitorType);
	}
}
