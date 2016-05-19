package com.wqm.repository.water.BlackWater;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.wqm.entity.water.blackWater.BlackWaterMonitorEntity;




public interface BlackWaterMonitorDao extends PagingAndSortingRepository<BlackWaterMonitorEntity, Long>,JpaSpecificationExecutor<BlackWaterMonitorEntity> {
	/**
	 * 删除监测项
	 */
	@Modifying
	@Query("delete from BlackWaterMonitorEntity blackWaterMonitor where blackWaterMonitor.blackWaterCode=?1 and blackWaterMonitor.monitorTypeCode=?2")
	public void deleByBlackWaterCodeMonitor(String sewageCode,String moniterType);
	/*
	 * 按code查找对应关系
	 */
	@Query("select blackWaterMonitor from BlackWaterMonitorEntity blackWaterMonitor where blackWaterMonitor.blackWaterCode =?1 and blackWaterMonitor.monitorTypeCode=?2")
	public List<BlackWaterMonitorEntity> getBlackWaterMonitorDataByBlackWaterCode(String code,String monitorType);
}
