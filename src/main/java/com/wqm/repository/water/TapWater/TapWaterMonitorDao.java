package com.wqm.repository.water.TapWater;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.wqm.entity.water.tapWater.TapWaterMonitorEntity;




public interface TapWaterMonitorDao extends PagingAndSortingRepository<TapWaterMonitorEntity, Long>,JpaSpecificationExecutor<TapWaterMonitorEntity> {
	/**
	 * 删除监测项
	 */
	@Modifying
	@Query("delete from TapWaterMonitorEntity tapWaterMonitor where tapWaterMonitor.tapWaterCode=?1 and tapWaterMonitor.monitorTypeCode=?2")
	public void deleByTapWaterCodeMonitor(String sewageCode,String moniterType);
	/*
	 * 按code查找对应关系
	 */
	@Query("select tapWaterMonitor from TapWaterMonitorEntity tapWaterMonitor where tapWaterMonitor.tapWaterCode =?1 and tapWaterMonitor.monitorTypeCode=?2")
	public List<TapWaterMonitorEntity> getTapWaterMonitorDataByTapWaterCode(String code,String monitorType);
}
