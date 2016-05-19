package com.wqm.repository.water.SewagePump;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.wqm.entity.water.sewagePump.MonitorDataSewagePump;




public interface MonitorDataSewagePumpDao extends PagingAndSortingRepository<MonitorDataSewagePump, Long>,JpaSpecificationExecutor<MonitorDataSewagePump> {

	/**
	 * 删除监测项
	 */
	@Modifying
	@Query("delete from MonitorDataSewagePump monitorDataSewagePump where monitorDataSewagePump.sewagePumpCode=?1  and monitorDataSewagePump.monitorType=?2")
	public void deleMonitorDataSewagePumpsByCodeMonitorType(String code,Integer monitorCode);
	
	@Query("select monitorDataSewagePump from MonitorDataSewagePump monitorDataSewagePump where monitorDataSewagePump.sewagePumpCode =?1")
	public List<MonitorDataSewagePump> getMonitorDataSewagePumpByWaterCode(String code);
	/**
	 * 
	 * @param 通过code获得时间列表
	 * @return
	 */
	@Query("select monitorDataSewagePump.monitorDate from MonitorDataSewagePump monitorDataSewagePump where monitorDataSewagePump.sewagePumpCode =?1 group by monitorDataSewagePump.monitorDate")
	public List<Date> getMonitorTimeBySewagePumpCode(String code);
	/**
	 * 
	 * 通过时间查找对应的数据
	 * @param 
	 * @param 
	 * @return
	 */
	@Query("select monitorDataSewagePump from MonitorDataSewagePump monitorDataSewagePump where monitorDataSewagePump.monitorDate =?1 and monitorDataSewagePump.sewagePumpCode =?2")
	public List<MonitorDataSewagePump> getMonitorDataSewagePumpByMonitorTime(Date time,String code);
	/*
	 * 查找监测数据
	 */
	@Query("select monitorDataSewagePump from MonitorDataSewagePump monitorDataSewagePump where monitorDataSewagePump.sewagePumpCode =?1 and monitorDataSewagePump.monitorType=?2")
	public List<MonitorDataSewagePump> getMonitorDataSewagePumpByWaterCodeMonitorCodeTypeCode(String code,String monitorType);
}
