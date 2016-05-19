package com.wqm.repository.water.WaterSource;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.wqm.entity.water.waterSource.MonitorDataWaterSource;




public interface MonitorDataWaterSourceDao extends PagingAndSortingRepository<MonitorDataWaterSource, Long>,JpaSpecificationExecutor<MonitorDataWaterSource> {

	/**
	 * 删除监测项
	 */
	@Modifying
	@Query("delete from MonitorDataWaterSource monitorDataWaterSource where monitorDataWaterSource.waterSourceCode=?1  and monitorDataWaterSource.monitorType=?2")
	public void deleMonitorDataWaterSourcesByCodeMonitorType(String code,Integer monitorCode);
	
	@Query("select monitorDataWaterSource from MonitorDataWaterSource monitorDataWaterSource where monitorDataWaterSource.waterSourceCode =?1")
	public List<MonitorDataWaterSource> getMonitorDataWaterSourceByWaterCode(String code);
	/**
	 * 
	 * @param 通过code获得时间列表
	 * @return
	 */
	@Query("select monitorDataWaterSource.monitorDate from MonitorDataWaterSource monitorDataWaterSource where monitorDataWaterSource.waterSourceCode =?1 group by monitorDataWaterSource.monitorDate")
	public List<Date> getMonitorTimeByWaterSourceCode(String code);
	/**
	 * 
	 * 通过时间查找对应的数据
	 * @param 
	 * @param 
	 * @return
	 */
	@Query("select monitorDataWaterSource from MonitorDataWaterSource monitorDataWaterSource where monitorDataWaterSource.monitorDate =?1 and monitorDataWaterSource.waterSourceCode =?2")
	public List<MonitorDataWaterSource> getMonitorDataWaterSourceByMonitorTime(Date time,String code);
	/*
	 * 查找监测数据
	 */
	@Query("select monitorDataWaterSource from MonitorDataWaterSource monitorDataWaterSource where monitorDataWaterSource.waterSourceCode =?1 and monitorDataWaterSource.monitorType=?2")
	public List<MonitorDataWaterSource> getMonitorDataWaterSourceByWaterCodeMonitorCodeTypeCode(String code,String monitorType);
}
