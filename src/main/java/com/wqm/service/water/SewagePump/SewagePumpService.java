package com.wqm.service.water.SewagePump;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.wqm.entity.water.SewageEntity;
import com.wqm.entity.water.WaterEntity;
import com.wqm.entity.water.sewagePump.SewagePumpEntity;
import com.wqm.repository.water.SewageDao;
import com.wqm.repository.water.SewagePump.SewagePumpDao;


@Component
@Transactional
public class SewagePumpService {
	
	@Autowired
	private SewagePumpDao sewagePumpDao;
	
	/**
	 * 分页查询污水处理厂
	 * 带查询条件spec
	 * @param pageRequest
	 * @param spec
	 * @return
	 */
	public Page<SewagePumpEntity> getSewagePumpsByPage(Specification<SewagePumpEntity> spec,PageRequest pageRequest){
		return sewagePumpDao.findAll(spec,pageRequest);
	}
	/**
	 * 获取全部污水厂
	 * @return
	 */
	public List<SewagePumpEntity> getAllSewagePumps(){
		return (List<SewagePumpEntity>) sewagePumpDao.findAll();
	}
	/**
	 * 保存提升泵站实体
	 * @return
	 */
	public void saveSewagePump(SewagePumpEntity entity){
		sewagePumpDao.save(entity);
	}
	/**
	 * 通过id获得实体
	 * @return
	 */
	public SewagePumpEntity getSewagePumpById(Long id){
		SewagePumpEntity entity=sewagePumpDao.findOne(id);
		return entity;
	}
	/**
	 * 删除提升泵站实体
	 * @return
	 */
	public void deleSewagePump(List<Long> ids){
		sewagePumpDao.deleSewagePumpsByIds(ids);
	}
	/**
	 * 通过code获得实体
	 * @return
	 */
	public SewagePumpEntity getSewagePumpByCode(String code){
		SewagePumpEntity entity=sewagePumpDao.getSewagePumpEntityByCode(code);
		return entity;
	}
	/**
	 * 按区域获得污水处理厂
	 * @param areaCode
	 * @return
	 */
	public List<SewagePumpEntity> getSewagePumpByAreaCode(String areaCode){
		List<SewagePumpEntity> sewages = sewagePumpDao.getSewagePumpByAreaCode(areaCode);
		return sewages;
	}
}
