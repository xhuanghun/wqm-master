package com.wqm.service.waterCensus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.wqm.entity.waterCensus.SZEntity;
import com.wqm.entity.waterCensus.YDSGCEntity;
import com.wqm.repository.waterCensus.SZDao;
import com.wqm.repository.waterCensus.YDSGCDao;


@Component
@Transactional
public class YDSGCService {
	
	@Autowired
	private YDSGCDao ydsDao;
	

	
	/**
	 * 分页查询引调水工程数据
	 * 带查询条件spec
	 * @param pageRequest
	 * @param spec
	 * @return
	 */
	public Page<YDSGCEntity> getYDSGCPage(Specification<YDSGCEntity> spec,PageRequest pageRequest){
		return ydsDao.findAll(spec,pageRequest);
	}
	
	/**
	 * 保存引调水工程
	 * @param water
	 * @return
	 */
	public YDSGCEntity saveYDSGCData(YDSGCEntity YDS){
		return ydsDao.save(YDS);
	}
}
