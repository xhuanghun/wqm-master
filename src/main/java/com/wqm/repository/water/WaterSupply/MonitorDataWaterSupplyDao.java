package com.wqm.repository.water.WaterSupply;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.wqm.entity.water.waterSupply.MonitorDataWaterSupply;




public interface MonitorDataWaterSupplyDao extends PagingAndSortingRepository<MonitorDataWaterSupply, Long>,JpaSpecificationExecutor<MonitorDataWaterSupply> {

	/**
	 * 删除监测项
	 */
	@Modifying
	@Query("delete from MonitorDataWaterSupply monitorDataWaterSupply where monitorDataWaterSupply.waterSupplyCode=?1  and monitorDataWaterSupply.monitorType=?2")
	public void deleMonitorDataWaterSupplysByCodeMonitorType(String code,Integer monitorCode);
	
	@Query("select monitorDataWaterSupply from MonitorDataWaterSupply monitorDataWaterSupply where monitorDataWaterSupply.waterSupplyCode =?1")
	public List<MonitorDataWaterSupply> getMonitorDataWaterSupplyByWaterCode(String code);
	/**
	 * 
	 * @param 通过code获得时间列表
	 * @return
	 */
	@Query("select monitorDataWaterSupply.monitorDate from MonitorDataWaterSupply monitorDataWaterSupply where monitorDataWaterSupply.waterSupplyCode =?1 group by monitorDataWaterSupply.monitorDate")
	public List<Date> getMonitorTimeByWaterSupplyCode(String code);
	/**
	 * 
	 * 通过时间查找对应的数据
	 * @param 
	 * @param 
	 * @return
	 */
	@Query("select monitorDataWaterSupply from MonitorDataWaterSupply monitorDataWaterSupply where monitorDataWaterSupply.monitorDate =?1 and monitorDataWaterSupply.waterSupplyCode =?2")
	public List<MonitorDataWaterSupply> getMonitorDataWaterSupplyByMonitorTime(Date time,String code);
	/*
	 * 查找监测数据
	 */
	@Query("select monitorDataWaterSupply from MonitorDataWaterSupply monitorDataWaterSupply where monitorDataWaterSupply.waterSupplyCode =?1 and monitorDataWaterSupply.monitorType=?2")
	public List<MonitorDataWaterSupply> getMonitorDataWaterSupplyByWaterCodeMonitorCodeTypeCode(String code,String monitorType);
}
