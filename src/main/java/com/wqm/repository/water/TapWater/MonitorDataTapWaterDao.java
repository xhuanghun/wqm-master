package com.wqm.repository.water.TapWater;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.wqm.entity.water.tapWater.MonitorDataTapWater;




public interface MonitorDataTapWaterDao extends PagingAndSortingRepository<MonitorDataTapWater, Long>,JpaSpecificationExecutor<MonitorDataTapWater> {

	/**
	 * 删除监测项
	 */
	@Modifying
	@Query("delete from MonitorDataTapWater monitorDataTapWater where monitorDataTapWater.tapWaterCode=?1  and monitorDataTapWater.monitorType=?2")
	public void deleMonitorDataTapWatersByCodeMonitorType(String code,Integer monitorCode);
	
	@Query("select monitorDataTapWater from MonitorDataTapWater monitorDataTapWater where monitorDataTapWater.tapWaterCode =?1")
	public List<MonitorDataTapWater> getMonitorDataTapWaterByWaterCode(String code);
	/**
	 * 
	 * @param 通过code获得时间列表
	 * @return
	 */
	@Query("select monitorDataTapWater.monitorDate from MonitorDataTapWater monitorDataTapWater where monitorDataTapWater.tapWaterCode =?1 group by monitorDataTapWater.monitorDate")
	public List<Date> getMonitorTimeByTapWaterCode(String code);
	/**
	 * 
	 * 通过时间查找对应的数据
	 * @param 
	 * @param 
	 * @return
	 */
	@Query("select monitorDataTapWater from MonitorDataTapWater monitorDataTapWater where monitorDataTapWater.monitorDate =?1 and monitorDataTapWater.tapWaterCode =?2")
	public List<MonitorDataTapWater> getMonitorDataTapWaterByMonitorTime(Date time,String code);
	/*
	 * 查找监测数据
	 */
	@Query("select monitorDataTapWater from MonitorDataTapWater monitorDataTapWater where monitorDataTapWater.tapWaterCode =?1 and monitorDataTapWater.monitorType=?2")
	public List<MonitorDataTapWater> getMonitorDataTapWaterByWaterCodeMonitorCodeTypeCode(String code,String monitorType);
}
