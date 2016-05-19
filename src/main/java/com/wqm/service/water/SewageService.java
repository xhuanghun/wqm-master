package com.wqm.service.water;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.wqm.entity.water.SewageEntity;
import com.wqm.entity.water.WaterEntity;
import com.wqm.repository.water.SewageDao;


@Component
@Transactional
public class SewageService {
	
	@Autowired
	private SewageDao sewageDao;
	
	/**
	 * 分页查询污水处理厂
	 * 带查询条件spec
	 * @param pageRequest
	 * @param spec
	 * @return
	 */
	public Page<SewageEntity> getSewagesByPage(Specification<SewageEntity> spec,PageRequest pageRequest){
		return sewageDao.findAll(spec,pageRequest);
	}
	/**
	 * 获取全部污水厂
	 * @return
	 */
	public List<SewageEntity> getAllSewages(){
		return (List<SewageEntity>) sewageDao.findAll();
	}
	/**
	 * 保存污水处理厂
	 * @param water
	 * @return
	 */
	public SewageEntity saveSewage(SewageEntity sewage){
		return sewageDao.save(sewage);
	}
	/**
	 * 删除水体
	 * @param ids
	 */
	public void deleSewage(List<Long> ids){
		sewageDao.deleSewagesByIds(ids);
	}
	/**
	 * 按ID查找污水厂
	 * @param id
	 * @return
	 */
	public SewageEntity getSewagesById(Long id){
		return sewageDao.findOne(id);
	}
	/**
	 * 按ID查找水体
	 * @param id
	 * @return
	 */
	public SewageEntity getSewageById(Long id){
		return sewageDao.findOne(id);
	}
	
	/**
	 * 按ID查找水体
	 * @param id
	 * @return
	 */
	public SewageEntity getSewageByCode(String code){
		return sewageDao.getSewageEntityByCode(code);
	}
	/**
	 * 按区域获得污水处理厂
	 * @param areaCode
	 * @return
	 */
	public List<SewageEntity> getSewageByAreaCode(String areaCode){
		List<SewageEntity> sewages = sewageDao.getSewageByAreaCode(areaCode);
		return sewages;
	}
}
