package com.wqm.service.waterCensus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.wqm.entity.waterCensus.DFGCEntity;
import com.wqm.repository.waterCensus.DFGCDao;


@Component
@Transactional
public class DFGCService {
	
	@Autowired
	private DFGCDao tfgcDao;
	

	
	/**
	 * 分页查询提防工程数据
	 * 带查询条件spec
	 * @param pageRequest
	 * @param spec
	 * @return
	 */
	public Page<DFGCEntity> getDFByPage(Specification<DFGCEntity> spec,PageRequest pageRequest){
		return tfgcDao.findAll(spec,pageRequest);
	}
	
	/**
	 * 保存提防工程
	 * @param water
	 * @return
	 */
	public DFGCEntity saveDFData(DFGCEntity TFGC){
		return tfgcDao.save(TFGC);
	}
}
