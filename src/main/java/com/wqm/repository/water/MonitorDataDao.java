package com.wqm.repository.water;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.wqm.entity.water.MonitorData;




public interface MonitorDataDao extends PagingAndSortingRepository<MonitorData, Long>,JpaSpecificationExecutor<MonitorData> {
	/**
	 * 删除监测项
	 */
	@Modifying
	@Query("delete from MonitorData monitorData where monitorData.id in (?1)")
	public void deleMonitorDatasByIds(List<Long> ids);
	/**
	 * 删除监测项,(下面方法不能用？换个方法试试。）
	 */
/*	@Modifying
	@Query("delete from MonitorData monitorData where monitorData.water.code=?1 and monitorData.monitorType=?2")
	public void deleMonitorDataByCodeMonitorType(String code,String monitorType);*/
	
	@Query("select monitorData from MonitorData monitorData where monitorData.water.code =?1")
	public List<MonitorData> getMonitorDataByWaterCode(String code);
	/**
	 * 
	 * @param 通过code获得时间列表
	 * @return
	 */
	@Query("select monitorData.monitorDate from MonitorData monitorData where monitorData.water.code =?1 group by monitorData.monitorDate")
	public List<Date> getMonitorTimeByWaterCode(String code);
	/**
	 * 
	 * 通过时间查找对应的数据
	 * @param 
	 * @param 
	 * @return
	 */
	@Query("select monitorData from MonitorData monitorData where monitorData.monitorDate =?1 and monitorData.water.code =?2")
	public List<MonitorData> getMonitorDataByMonitorTime(Date time,String code);
}
