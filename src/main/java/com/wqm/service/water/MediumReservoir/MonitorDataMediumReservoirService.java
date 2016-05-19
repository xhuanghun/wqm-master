package com.wqm.service.water.MediumReservoir;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.wqm.entity.water.mediumReservoir.MonitorDataMediumReservoir;
import com.wqm.repository.water.MediumReservoir.MonitorDataMediumReservoirDao;



@Component
@Transactional
public class MonitorDataMediumReservoirService {
	
	@Autowired
	private MonitorDataMediumReservoirDao monitorDataMediumReservoirDao;

	
	/**
	 * 按条件查找监测数据
	 * @parMonitorDataMediumReservoirurn
	 */
	public List<MonitorDataMediumReservoir> getMonitorDataMediumReservoirByWaterCodeMonitorCodeTypeCode(String code,String monitorType){
		return monitorDataMediumReservoirDao.getMonitorDataMediumReservoirByWaterCodeMonitorCodeTypeCode(code, monitorType);
	}
	/**
	 * 保存监测数据
	 * @param monitorData
	 * @return
	 */
	public MonitorDataMediumReservoir saveMonitorDataMediumReservoir(MonitorDataMediumReservoir monitorDataMediumReservoir){
		return monitorDataMediumReservoirDao.save(monitorDataMediumReservoir);
	}
	/**
	 * 按ID查找监测数据
	 * @param id
	 * @return
	 */
	public MonitorDataMediumReservoir getMonitorDataMediumReservoirById(Long id){
		return monitorDataMediumReservoirDao.findOne(id);
	}
	/**
	 * 分页查询监测数据
	 * 带查询条件spec
	 * @param pageRequest
	 * @param spec
	 * @return
	 */
	public Page<MonitorDataMediumReservoir> getMonitorDatasMediumReservoirByPage(Specification<MonitorDataMediumReservoir> spec,PageRequest pageRequest){
		return monitorDataMediumReservoirDao.findAll(spec,pageRequest);
	}
	/**
	 * 删除监测数据
	 * @param ids
	 */
	public void deleMonitorDataMediumReservoir(String code,Integer monitorType){
		monitorDataMediumReservoirDao.deleMonitorDataMediumReservoirsByCodeMonitorType(code, monitorType);
	}
	/**
	 * get采样时间通过code
	 * @param code
	 */
	public List<Date> getMonitorTimeByMediumReservoirCode(String code){
		return monitorDataMediumReservoirDao.getMonitorTimeByMediumReservoirCode(code);
	}
	public List<MonitorDataMediumReservoir> getMonitorDataMediumReservoirByMonitorTime(Date time,String code){
		return monitorDataMediumReservoirDao.getMonitorDataMediumReservoirByMonitorTime(time,code);
	}
}
