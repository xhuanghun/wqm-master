package com.wqm.repository.water.SewagePump;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.wqm.entity.water.SewageMonitorEntity;
import com.wqm.entity.water.sewagePump.SewagePumpMonitorEntity;




public interface SewagePumpMonitorDao extends PagingAndSortingRepository<SewagePumpMonitorEntity, Long>,JpaSpecificationExecutor<SewagePumpMonitorEntity> {
	/**
	 * 删除监测项
	 */
	@Modifying
	@Query("delete from SewagePumpMonitorEntity sewagePumpMonitor where sewagePumpMonitor.sewagePumpCode=?1 and sewagePumpMonitor.monitorTypeCode=?2")
	public void deleBySewagePumpCodeMonitor(String sewageCode,String moniterType);
	/*
	 * 按code查找对应关系
	 */
	@Query("select sewagePumpMonitor from SewagePumpMonitorEntity sewagePumpMonitor where sewagePumpMonitor.sewagePumpCode =?1 and sewagePumpMonitor.monitorTypeCode=?2")
	public List<SewagePumpMonitorEntity> getSewagePumpMonitorDataBySewagePumpCode(String code,String monitorType);
}
