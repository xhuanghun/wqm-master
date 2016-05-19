package com.wqm.service.water.WaterSource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.wqm.entity.water.waterSource.WaterSourceEntity;
import com.wqm.repository.water.WaterSource.WaterSourceDao;


@Component
@Transactional
public class WaterSourceService {
	
	@Autowired
	private WaterSourceDao waterSourceDao;
	
	/**
	 * 分页查询污水处理厂
	 * 带查询条件spec
	 * @param pageRequest
	 * @param spec
	 * @return
	 */
	public Page<WaterSourceEntity> getWaterSourcesByPage(Specification<WaterSourceEntity> spec,PageRequest pageRequest){
		return waterSourceDao.findAll(spec,pageRequest);
	}
	/**
	 * 获取全部污水厂
	 * @return
	 */
	public List<WaterSourceEntity> getAllWaterSources(){
		return (List<WaterSourceEntity>) waterSourceDao.findAll();
	}
	/**
	 * 获取全部水源地监测点
	 * @return
	 */
	public List<WaterSourceEntity> getAllWaterSourcesByIsLeaf(){
		return (List<WaterSourceEntity>) waterSourceDao.findAllParents();
	}
	/**
	 * 保存提升泵站实体
	 * @return
	 */
	public void saveWaterSource(WaterSourceEntity entity){
		waterSourceDao.save(entity);
	}
	/**
	 * 通过id获得实体
	 * @return
	 */
	public WaterSourceEntity getWaterSourceById(Long id){
		WaterSourceEntity entity=waterSourceDao.findOne(id);
		return entity;
	}
	/**
	 * 删除提升泵站实体
	 * @return
	 */
	public void deleWaterSource(List<Long> ids){
		waterSourceDao.deleWaterSourcesByIds(ids);
	}
	/**
	 * 通过code获得实体
	 * @return
	 */
	public WaterSourceEntity getWaterSourceByCode(String code){
		WaterSourceEntity entity=waterSourceDao.getWaterSourceEntityByCode(code);
		return entity;
	}
	/**
	 * 按区域获得污水处理厂
	 * @param areaCode
	 * @return
	 */
	public List<WaterSourceEntity> getWaterSourceByAreaCode(String areaCode){
		List<WaterSourceEntity> sewages = waterSourceDao.getWaterSourceByAreaCode(areaCode);
		return sewages;
	}
}
