package com.wqm.service.waterCensus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.wqm.entity.waterCensus.NCGSGCEntity;
import com.wqm.repository.waterCensus.NCGSGCDao;


@Component
@Transactional
public class NCGSGCService {
	
	@Autowired
	private NCGSGCDao gsDao;
	

	
	/**
	 * 分页查询供水工程数据
	 * 带查询条件spec
	 * @param pageRequest
	 * @param spec
	 * @return
	 */
	public Page<NCGSGCEntity> getGSByPage(Specification<NCGSGCEntity> spec,PageRequest pageRequest){
		return gsDao.findAll(spec,pageRequest);
	}
	
	/**
	 * 保存供水工程
	 * @param water
	 * @return
	 */
	public NCGSGCEntity saveGSData(NCGSGCEntity GS){
		return gsDao.save(GS);
	}
}
