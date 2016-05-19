package com.wqm.service.water.WaterSupply;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.wqm.entity.water.waterSupply.WaterSupplyEntity;
import com.wqm.repository.water.WaterSupply.WaterSupplyDao;


@Component
@Transactional
public class WaterSupplyService {
	
	@Autowired
	private WaterSupplyDao waterSupplyDao;
	
	/**
	 * 分页查询污水处理厂
	 * 带查询条件spec
	 * @param pageRequest
	 * @param spec
	 * @return
	 */
	public Page<WaterSupplyEntity> getWaterSupplysByPage(Specification<WaterSupplyEntity> spec,PageRequest pageRequest){
		return waterSupplyDao.findAll(spec,pageRequest);
	}
	/**
	 * 获取全部污水厂
	 * @return
	 */
	public List<WaterSupplyEntity> getAllWaterSupplys(){
		return (List<WaterSupplyEntity>) waterSupplyDao.findAll();
	}
	/**
	 * 保存提升泵站实体
	 * @return
	 */
	public void saveWaterSupply(WaterSupplyEntity entity){
		waterSupplyDao.save(entity);
	}
	/**
	 * 通过id获得实体
	 * @return
	 */
	public WaterSupplyEntity getWaterSupplyById(Long id){
		WaterSupplyEntity entity=waterSupplyDao.findOne(id);
		return entity;
	}
	/**
	 * 删除提升泵站实体
	 * @return
	 */
	public void deleWaterSupply(List<Long> ids){
		waterSupplyDao.deleWaterSupplysByIds(ids);
	}
	/**
	 * 通过code获得实体
	 * @return
	 */
	public WaterSupplyEntity getWaterSupplyByCode(String code){
		WaterSupplyEntity entity=waterSupplyDao.getWaterSupplyEntityByCode(code);
		return entity;
	}
	/**
	 * 按区域获得污水处理厂
	 * @param areaCode
	 * @return
	 */
	public List<WaterSupplyEntity> getWaterSupplyByAreaCode(String areaCode){
		List<WaterSupplyEntity> sewages = waterSupplyDao.getWaterSupplyByAreaCode(areaCode);
		return sewages;
	}
}
