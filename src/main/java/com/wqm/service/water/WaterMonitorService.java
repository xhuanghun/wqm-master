package com.wqm.service.water;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.wqm.entity.water.MonitorData;
import com.wqm.entity.water.WaterEntity;
import com.wqm.entity.water.WaterMonitorEntity;
import com.wqm.repository.water.WaterDao;
import com.wqm.repository.water.WaterMonitorDao;


@Component
@Transactional
public class WaterMonitorService {
	
	@Autowired
	private WaterMonitorDao waterMonitorDao;
	
	
	/**
	 * 保存监测项对应关系
	 * @param water
	 * @return
	 */
	public WaterMonitorEntity saveWaterMonitor(WaterMonitorEntity waterMonitor){
		return waterMonitorDao.save(waterMonitor);
	}
	
	/**
	 * 删除水体
	 * @param ids
	 */
	public void deleByWaterCodeMonitorType(String waterCode,String monitorType){
		waterMonitorDao.deleByWaterCodeMonitorType(waterCode,monitorType);
	}
	/*
	 * 按code找到对应关系
	 */
	public List<WaterMonitorEntity> getWaterMonitorDataByCode(String code,String monitorType){
		return waterMonitorDao.getWaterMonitorDataByWaterCode(code,monitorType);
	}
}
