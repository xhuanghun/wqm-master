package com.wqm.repository.water;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.wqm.entity.water.SewageMonitorEntity;




public interface SewageMonitorDao extends PagingAndSortingRepository<SewageMonitorEntity, Long>,JpaSpecificationExecutor<SewageMonitorEntity> {
	/**
	 * 删除监测项
	 */
	@Modifying
	@Query("delete from SewageMonitorEntity sewageMonitor where sewageMonitor.sewageCode=?1 and sewageMonitor.monitorTypeCode=?2 and sewageMonitor.typeCode=?3")
	public void deleBySewageCodeMonitorType(String sewageCode,String moniterType,String type);
	/*
	 * 按code查找对应关系
	 */
	@Query("select sewageMonitor from SewageMonitorEntity sewageMonitor where sewageMonitor.sewageCode =?1 and sewageMonitor.monitorTypeCode=?2 and sewageMonitor.typeCode=?3")
	public List<SewageMonitorEntity> getSewageMonitorDataBySewageCode(String code,String monitorType,String type);
}
