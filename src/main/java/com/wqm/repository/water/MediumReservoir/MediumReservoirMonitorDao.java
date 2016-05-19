package com.wqm.repository.water.MediumReservoir;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.wqm.entity.water.mediumReservoir.MediumReservoirMonitorEntity;




public interface MediumReservoirMonitorDao extends PagingAndSortingRepository<MediumReservoirMonitorEntity, Long>,JpaSpecificationExecutor<MediumReservoirMonitorEntity> {
	/**
	 * 删除监测项
	 */
	@Modifying
	@Query("delete from MediumReservoirMonitorEntity mediumReservoirMonitor where mediumReservoirMonitor.mediumReservoirCode=?1 and mediumReservoirMonitor.monitorTypeCode=?2")
	public void deleByMediumReservoirCodeMonitor(String sewageCode,String moniterType);
	/*
	 * 按code查找对应关系
	 */
	@Query("select mediumReservoirMonitor from MediumReservoirMonitorEntity mediumReservoirMonitor where mediumReservoirMonitor.mediumReservoirCode =?1 and mediumReservoirMonitor.monitorTypeCode=?2")
	public List<MediumReservoirMonitorEntity> getMediumReservoirMonitorDataByMediumReservoirCode(String code,String monitorType);
}
