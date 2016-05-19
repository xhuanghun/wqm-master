package com.wqm.service.water.BlackWater;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.wqm.entity.water.blackWater.BlackWaterMonitorEntity;
import com.wqm.repository.water.BlackWater.BlackWaterMonitorDao;


@Component
@Transactional
public class BlackWaterMonitorService {
	
	@Autowired
	private BlackWaterMonitorDao blackWaterMonitorDao;
	
	
	/**
	 * 保存监测项对应关系
	 * @param water
	 * @return
	 */
	public BlackWaterMonitorEntity saveBlackWaterMonitor(BlackWaterMonitorEntity blackWaterMonitor){
		return blackWaterMonitorDao.save(blackWaterMonitor);
	}
	
	/**
	 * 删除水体
	 * @param ids
	 */
	public void deleByBlackWaterCodeMonitor(String blackWaterCode,String monitorType){
		blackWaterMonitorDao.deleByBlackWaterCodeMonitor(blackWaterCode,monitorType);
	}
	/*
	 * 按code找到对应关系
	 */
	public List<BlackWaterMonitorEntity> getBlackWaterMonitorDataByCode(String code,String monitorType){
		return blackWaterMonitorDao.getBlackWaterMonitorDataByBlackWaterCode(code,monitorType);
	}
}
