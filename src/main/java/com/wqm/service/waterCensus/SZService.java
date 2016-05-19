package com.wqm.service.waterCensus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.wqm.entity.waterCensus.SZEntity;
import com.wqm.repository.waterCensus.SZDao;


@Component
@Transactional
public class SZService {
	
	@Autowired
	private SZDao szDao;
	

	
	/**
	 * 分页查询水闸工程数据
	 * 带查询条件spec
	 * @param pageRequest
	 * @param spec
	 * @return
	 */
	public Page<SZEntity> getSZPage(Specification<SZEntity> spec,PageRequest pageRequest){
		return szDao.findAll(spec,pageRequest);
	}
	
	/**
	 * 保存水闸工程
	 * @param water
	 * @return
	 */
	public SZEntity saveSZData(SZEntity SZ){
		return szDao.save(SZ);
	}
}
