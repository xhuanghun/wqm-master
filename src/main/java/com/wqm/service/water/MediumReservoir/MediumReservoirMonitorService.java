package com.wqm.service.water.MediumReservoir;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.wqm.entity.water.mediumReservoir.MediumReservoirMonitorEntity;
import com.wqm.repository.water.MediumReservoir.MediumReservoirMonitorDao;


@Component
@Transactional
public class MediumReservoirMonitorService {
	
	@Autowired
	private MediumReservoirMonitorDao mediumReservoirMonitorDao;
	
	
	/**
	 * 保存监测项对应关系
	 * @param water
	 * @return
	 */
	public MediumReservoirMonitorEntity saveMediumReservoirMonitor(MediumReservoirMonitorEntity mediumReservoirMonitor){
		return mediumReservoirMonitorDao.save(mediumReservoirMonitor);
	}
	
	/**
	 * 删除水体
	 * @param ids
	 */
	public void deleByMediumReservoirCodeMonitor(String mediumReservoirCode,String monitorType){
		mediumReservoirMonitorDao.deleByMediumReservoirCodeMonitor(mediumReservoirCode,monitorType);
	}
	/*
	 * 按code找到对应关系
	 */
	public List<MediumReservoirMonitorEntity> getMediumReservoirMonitorDataByCode(String code,String monitorType){
		return mediumReservoirMonitorDao.getMediumReservoirMonitorDataByMediumReservoirCode(code,monitorType);
	}
}
