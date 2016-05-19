package com.wqm.service.waterCensus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.wqm.entity.waterCensus.BZEntity;
import com.wqm.repository.waterCensus.BZDao;


@Component
@Transactional
public class BZService {
	
	@Autowired
	private BZDao bzDao;
	

	
	/**
	 * 分页查询泵站数据
	 * 带查询条件spec
	 * @param pageRequest
	 * @param spec
	 * @return
	 */
	public Page<BZEntity> getBZByPage(Specification<BZEntity> spec,PageRequest pageRequest){
		return bzDao.findAll(spec,pageRequest);
	}
	
	/**
	 * 保存水体
	 * @param water
	 * @return
	 */
	public BZEntity saveBZData(BZEntity water){
		return bzDao.save(water);
	}
}
