package com.wqm.repository.water;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.wqm.entity.water.WaterMonitorEntity;




public interface WaterMonitorDao extends PagingAndSortingRepository<WaterMonitorEntity, Long>,JpaSpecificationExecutor<WaterMonitorEntity> {
	/**
	 * 删除监测项
	 */
	@Modifying
	@Query("delete from WaterMonitorEntity waterMonitor where waterMonitor.waterCode=?1 and waterMonitor.monitorTypeCode=?2")
	public void deleByWaterCodeMonitorType(String waterCode,String moniterType);
	/*
	 * 按code查找对应关系
	 */
	@Query("select waterMonitor from WaterMonitorEntity waterMonitor where waterMonitor.waterCode =?1 and waterMonitor.monitorTypeCode=?2")
	public List<WaterMonitorEntity> getWaterMonitorDataByWaterCode(String code,String monitorType);
}
