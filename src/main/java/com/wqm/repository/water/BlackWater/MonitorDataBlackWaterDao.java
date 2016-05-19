package com.wqm.repository.water.BlackWater;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.wqm.entity.water.blackWater.MonitorDataBlackWater;




public interface MonitorDataBlackWaterDao extends PagingAndSortingRepository<MonitorDataBlackWater, Long>,JpaSpecificationExecutor<MonitorDataBlackWater> {

	/**
	 * 删除监测项
	 */
	@Modifying
	@Query("delete from MonitorDataBlackWater monitorDataBlackWater where monitorDataBlackWater.blackWaterCode=?1  and monitorDataBlackWater.monitorType=?2")
	public void deleMonitorDataBlackWatersByCodeMonitorType(String code,Integer monitorCode);
	
	@Query("select monitorDataBlackWater from MonitorDataBlackWater monitorDataBlackWater where monitorDataBlackWater.blackWaterCode =?1")
	public List<MonitorDataBlackWater> getMonitorDataBlackWaterByWaterCode(String code);
	/**
	 * 
	 * @param 通过code获得时间列表
	 * @return
	 */
	@Query("select monitorDataBlackWater.monitorDate from MonitorDataBlackWater monitorDataBlackWater where monitorDataBlackWater.blackWaterCode =?1 group by monitorDataBlackWater.monitorDate")
	public List<Date> getMonitorTimeByBlackWaterCode(String code);
	/**
	 * 
	 * 通过时间查找对应的数据
	 * @param 
	 * @param 
	 * @return
	 */
	@Query("select monitorDataBlackWater from MonitorDataBlackWater monitorDataBlackWater where monitorDataBlackWater.monitorDate =?1 and monitorDataBlackWater.blackWaterCode =?2")
	public List<MonitorDataBlackWater> getMonitorDataBlackWaterByMonitorTime(Date time,String code);
	/*
	 * 查找监测数据
	 */
	@Query("select monitorDataBlackWater from MonitorDataBlackWater monitorDataBlackWater where monitorDataBlackWater.blackWaterCode =?1 and monitorDataBlackWater.monitorType=?2")
	public List<MonitorDataBlackWater> getMonitorDataBlackWaterByWaterCodeMonitorCodeTypeCode(String code,String monitorType);
}
