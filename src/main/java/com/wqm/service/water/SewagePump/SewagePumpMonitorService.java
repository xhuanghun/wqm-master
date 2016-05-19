package com.wqm.service.water.SewagePump;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.wqm.entity.water.MonitorData;
import com.wqm.entity.water.SewageMonitorEntity;
import com.wqm.entity.water.WaterEntity;
import com.wqm.entity.water.WaterMonitorEntity;
import com.wqm.entity.water.sewagePump.SewagePumpMonitorEntity;
import com.wqm.repository.water.SewageMonitorDao;
import com.wqm.repository.water.WaterDao;
import com.wqm.repository.water.WaterMonitorDao;
import com.wqm.repository.water.SewagePump.SewagePumpMonitorDao;


@Component
@Transactional
public class SewagePumpMonitorService {
	
	@Autowired
	private SewagePumpMonitorDao sewagePumpMonitorDao;
	
	
	/**
	 * 保存监测项对应关系
	 * @param water
	 * @return
	 */
	public SewagePumpMonitorEntity saveSewagePumpMonitor(SewagePumpMonitorEntity sewagePumpMonitor){
		return sewagePumpMonitorDao.save(sewagePumpMonitor);
	}
	
	/**
	 * 删除水体
	 * @param ids
	 */
	public void deleBySewagePumpCodeMonitor(String sewagePumpCode,String monitorType){
		sewagePumpMonitorDao.deleBySewagePumpCodeMonitor(sewagePumpCode,monitorType);
	}
	/*
	 * 按code找到对应关系
	 */
	public List<SewagePumpMonitorEntity> getSewageMonitorDataByCode(String code,String monitorType){
		return sewagePumpMonitorDao.getSewagePumpMonitorDataBySewagePumpCode(code,monitorType);
	}
}
