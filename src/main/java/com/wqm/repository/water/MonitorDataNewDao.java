package com.wqm.repository.water;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.wqm.entity.water.MonitorDataNew;




public interface MonitorDataNewDao extends PagingAndSortingRepository<MonitorDataNew, Long>,JpaSpecificationExecutor<MonitorDataNew> {

	/**
	 * 删除监测项
	 */
	@Modifying
	@Query("delete from MonitorDataNew monitorDataNew where monitorDataNew.waterCode=?1 and monitorDataNew.type=?2 and monitorDataNew.monitorType=?3")
	public void deleMonitorDataNewsByCodeMonitorType(String code,Integer type,Integer monitorCode);
	
	@Query("select monitorDataNew from MonitorDataNew monitorDataNew where monitorDataNew.waterCode =?1")
	public List<MonitorDataNew> getMonitorDataNewByWaterCode(String code);
	/**
	 * 
	 * @param 通过code获得时间列表
	 * @return
	 */
	@Query("select monitorDataNew.monitorDate from MonitorDataNew monitorDataNew where monitorDataNew.waterCode =?1 group by monitorDataNew.monitorDate")
	public List<Date> getMonitorTimeBySewageCode(String code);
	/**
	 * 
	 * 通过时间查找对应的数据
	 * @param 
	 * @param 
	 * @return
	 */
	@Query("select monitorDataNew from MonitorDataNew monitorDataNew where monitorDataNew.monitorDate =?1 and monitorDataNew.waterCode =?2")
	public List<MonitorDataNew> getMonitorDataNewByMonitorTime(Date time,String code);
	/*
	 * 查找监测数据
	 */
	@Query("select monitorDataNew from MonitorDataNew monitorDataNew where monitorDataNew.waterCode =?1 and monitorDataNew.monitorType=?2 and monitorDataNew.type=?3")
	public List<MonitorDataNew> getMonitorDataNewByWaterCodeMonitorCodeTypeCode(String code,String monitorType,String type);
}
