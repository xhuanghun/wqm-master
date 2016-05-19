package com.wqm.repository.water.WaterSource;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.wqm.entity.water.waterSource.WaterSourceMonitorEntity;




public interface WaterSourceMonitorDao extends PagingAndSortingRepository<WaterSourceMonitorEntity, Long>,JpaSpecificationExecutor<WaterSourceMonitorEntity> {
	/**
	 * 删除监测项
	 */
	@Modifying
	@Query("delete from WaterSourceMonitorEntity waterSourceMonitor where waterSourceMonitor.waterSourceCode=?1 and waterSourceMonitor.monitorTypeCode=?2")
	public void deleByWaterSourceCodeMonitor(String sewageCode,String moniterType);
	/*
	 * 按code查找对应关系
	 */
	@Query("select waterSourceMonitor from WaterSourceMonitorEntity waterSourceMonitor where waterSourceMonitor.waterSourceCode =?1 and waterSourceMonitor.monitorTypeCode=?2")
	public List<WaterSourceMonitorEntity> getWaterSourceMonitorDataByWaterSourceCode(String code,String monitorType);
}
