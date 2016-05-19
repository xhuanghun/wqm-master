package com.wqm.service.water.SewagePump;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.wqm.entity.water.sewagePump.MonitorDataSewagePump;
import com.wqm.repository.water.SewagePump.MonitorDataSewagePumpDao;



@Component
@Transactional
public class MonitorDataSewagePumpService {
	
	@Autowired
	private MonitorDataSewagePumpDao monitorDataSewagePumpDao;

	
	/**
	 * 按条件查找监测数据
	 * @parMonitorDataSewagePumpurn
	 */
	public List<MonitorDataSewagePump> getMonitorDataSewagePumpByWaterCodeMonitorCodeTypeCode(String code,String monitorType){
		return monitorDataSewagePumpDao.getMonitorDataSewagePumpByWaterCodeMonitorCodeTypeCode(code, monitorType);
	}
	/**
	 * 保存监测数据
	 * @param monitorData
	 * @return
	 */
	public MonitorDataSewagePump saveMonitorDataSewagePump(MonitorDataSewagePump monitorDataSewagePump){
		return monitorDataSewagePumpDao.save(monitorDataSewagePump);
	}
	/**
	 * 按ID查找监测数据
	 * @param id
	 * @return
	 */
	public MonitorDataSewagePump getMonitorDataSewagePumpById(Long id){
		return monitorDataSewagePumpDao.findOne(id);
	}
	/**
	 * 分页查询监测数据
	 * 带查询条件spec
	 * @param pageRequest
	 * @param spec
	 * @return
	 */
	public Page<MonitorDataSewagePump> getMonitorDatasSewagePumpByPage(Specification<MonitorDataSewagePump> spec,PageRequest pageRequest){
		return monitorDataSewagePumpDao.findAll(spec,pageRequest);
	}
	/**
	 * 删除监测数据
	 * @param ids
	 */
	public void deleMonitorDataSewagePump(String code,Integer monitorType){
		monitorDataSewagePumpDao.deleMonitorDataSewagePumpsByCodeMonitorType(code, monitorType);
	}
	/**
	 * get采样时间通过code
	 * @param code
	 */
	public List<Date> getMonitorTimeBySewagePumpCode(String code){
		return monitorDataSewagePumpDao.getMonitorTimeBySewagePumpCode(code);
	}
	public List<MonitorDataSewagePump> getMonitorDataSewagePumpByMonitorTime(Date time,String code){
		return monitorDataSewagePumpDao.getMonitorDataSewagePumpByMonitorTime(time,code);
	}
}
