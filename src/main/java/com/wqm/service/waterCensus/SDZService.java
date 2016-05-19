package com.wqm.service.waterCensus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.wqm.entity.waterCensus.SDZEntity;
import com.wqm.repository.waterCensus.SDZDao;


@Component
@Transactional
public class SDZService {
	
	@Autowired
	private SDZDao sdzDao;
	

	
	/**
	 * 分页查询供水工程数据
	 * 带查询条件spec
	 * @param pageRequest
	 * @param spec
	 * @return
	 */
	public Page<SDZEntity> getSDZByPage(Specification<SDZEntity> spec,PageRequest pageRequest){
		return sdzDao.findAll(spec,pageRequest);
	}
	
	/**
	 * 保存供水工程
	 * @param water
	 * @return
	 */
	public SDZEntity saveSDZData(SDZEntity SDZ){
		return sdzDao.save(SDZ);
	}
}
