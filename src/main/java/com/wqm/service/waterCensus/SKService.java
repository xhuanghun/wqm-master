package com.wqm.service.waterCensus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.wqm.entity.waterCensus.SKEntity;
import com.wqm.repository.waterCensus.SKDao;


@Component
@Transactional
public class SKService {
	
	@Autowired
	private SKDao skDao;
	

	
	/**
	 * 分页查询水库工程数据
	 * 带查询条件spec
	 * @param pageRequest
	 * @param spec
	 * @return
	 */
	public Page<SKEntity> getSKPage(Specification<SKEntity> spec,PageRequest pageRequest){
		return skDao.findAll(spec,pageRequest);
	}
	
	/**
	 * 保存水库工程
	 * @param water
	 * @return
	 */
	public SKEntity saveSKData(SKEntity sk){
		return skDao.save(sk);
	}
}
