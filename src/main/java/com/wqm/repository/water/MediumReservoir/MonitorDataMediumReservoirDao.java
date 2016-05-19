package com.wqm.repository.water.MediumReservoir;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.wqm.entity.water.mediumReservoir.MonitorDataMediumReservoir;




public interface MonitorDataMediumReservoirDao extends PagingAndSortingRepository<MonitorDataMediumReservoir, Long>,JpaSpecificationExecutor<MonitorDataMediumReservoir> {

	/**
	 * 删除监测项
	 */
	@Modifying
	@Query("delete from MonitorDataMediumReservoir monitorDataMediumReservoir where monitorDataMediumReservoir.mediumReservoirCode=?1  and monitorDataMediumReservoir.monitorType=?2")
	public void deleMonitorDataMediumReservoirsByCodeMonitorType(String code,Integer monitorCode);
	
	@Query("select monitorDataMediumReservoir from MonitorDataMediumReservoir monitorDataMediumReservoir where monitorDataMediumReservoir.mediumReservoirCode =?1")
	public List<MonitorDataMediumReservoir> getMonitorDataMediumReservoirByWaterCode(String code);
	/**
	 * 
	 * @param 通过code获得时间列表
	 * @return
	 */
	@Query("select monitorDataMediumReservoir.monitorDate from MonitorDataMediumReservoir monitorDataMediumReservoir where monitorDataMediumReservoir.mediumReservoirCode =?1 group by monitorDataMediumReservoir.monitorDate")
	public List<Date> getMonitorTimeByMediumReservoirCode(String code);
	/**
	 * 
	 * 通过时间查找对应的数据
	 * @param 
	 * @param 
	 * @return
	 */
	@Query("select monitorDataMediumReservoir from MonitorDataMediumReservoir monitorDataMediumReservoir where monitorDataMediumReservoir.monitorDate =?1 and monitorDataMediumReservoir.mediumReservoirCode =?2")
	public List<MonitorDataMediumReservoir> getMonitorDataMediumReservoirByMonitorTime(Date time,String code);
	/*
	 * 查找监测数据
	 */
	@Query("select monitorDataMediumReservoir from MonitorDataMediumReservoir monitorDataMediumReservoir where monitorDataMediumReservoir.mediumReservoirCode =?1 and monitorDataMediumReservoir.monitorType=?2")
	public List<MonitorDataMediumReservoir> getMonitorDataMediumReservoirByWaterCodeMonitorCodeTypeCode(String code,String monitorType);
}
