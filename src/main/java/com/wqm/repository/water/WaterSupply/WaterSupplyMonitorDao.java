package com.wqm.repository.water.WaterSupply;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.wqm.entity.water.waterSupply.WaterSupplyMonitorEntity;




public interface WaterSupplyMonitorDao extends PagingAndSortingRepository<WaterSupplyMonitorEntity, Long>,JpaSpecificationExecutor<WaterSupplyMonitorEntity> {
	/**
	 * 删除监测项
	 */
	@Modifying
	@Query("delete from WaterSupplyMonitorEntity waterSupplyMonitor where waterSupplyMonitor.waterSupplyCode=?1 and waterSupplyMonitor.monitorTypeCode=?2")
	public void deleByWaterSupplyCodeMonitor(String sewageCode,String moniterType);
	/*
	 * 按code查找对应关系
	 */
	@Query("select waterSupplyMonitor from WaterSupplyMonitorEntity waterSupplyMonitor where waterSupplyMonitor.waterSupplyCode =?1 and waterSupplyMonitor.monitorTypeCode=?2")
	public List<WaterSupplyMonitorEntity> getWaterSupplyMonitorDataByWaterSupplyCode(String code,String monitorType);
}
