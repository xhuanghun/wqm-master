package com.wqm.service.water.BlackWater;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.wqm.entity.water.blackWater.BlackWaterEntity;
import com.wqm.repository.water.BlackWater.BlackWaterDao;


@Component
@Transactional
public class BlackWaterService {
	
	@Autowired
	private BlackWaterDao blackWaterDao;
	
	/**
	 * 分页查询污水处理厂
	 * 带查询条件spec
	 * @param pageRequest
	 * @param spec
	 * @return
	 */
	public Page<BlackWaterEntity> getBlackWatersByPage(Specification<BlackWaterEntity> spec,PageRequest pageRequest){
		return blackWaterDao.findAll(spec,pageRequest);
	}
	/**
	 * 获取全部污水厂
	 * @return
	 */
	public List<BlackWaterEntity> getAllBlackWaters(){
		return (List<BlackWaterEntity>) blackWaterDao.findAll();
	}
	/**
	 * 获取全部水源地监测点
	 * @return
	 */
	public List<BlackWaterEntity> getAllBlackWatersByIsLeaf(){
		return (List<BlackWaterEntity>) blackWaterDao.findAllParents();
	}
	/**
	 * 保存提升泵站实体
	 * @return
	 */
	public void saveBlackWater(BlackWaterEntity entity){
		blackWaterDao.save(entity);
	}
	/**
	 * 通过id获得实体
	 * @return
	 */
	public BlackWaterEntity getBlackWaterById(Long id){
		BlackWaterEntity entity=blackWaterDao.findOne(id);
		return entity;
	}
	/**
	 * 删除提升泵站实体
	 * @return
	 */
	public void deleBlackWater(List<Long> ids){
		blackWaterDao.deleBlackWatersByIds(ids);
	}
	/**
	 * 通过code获得实体
	 * @return
	 */
	public BlackWaterEntity getBlackWaterByCode(String code){
		BlackWaterEntity entity=blackWaterDao.getBlackWaterEntityByCode(code);
		return entity;
	}
	/**
	 * 按区域获得污水处理厂
	 * @param areaCode
	 * @return
	 */
	public List<BlackWaterEntity> getBlackWaterByAreaCode(String areaCode){
		List<BlackWaterEntity> sewages = blackWaterDao.getBlackWaterByAreaCode(areaCode);
		return sewages;
	}
}
