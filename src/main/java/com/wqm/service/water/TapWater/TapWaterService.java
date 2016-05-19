package com.wqm.service.water.TapWater;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.wqm.entity.water.tapWater.TapWaterEntity;
import com.wqm.repository.water.TapWater.TapWaterDao;


@Component
@Transactional
public class TapWaterService {
	
	@Autowired
	private TapWaterDao tapWaterDao;
	
	/**
	 * 分页查询污水处理厂
	 * 带查询条件spec
	 * @param pageRequest
	 * @param spec
	 * @return
	 */
	public Page<TapWaterEntity> getTapWatersByPage(Specification<TapWaterEntity> spec,PageRequest pageRequest){
		return tapWaterDao.findAll(spec,pageRequest);
	}
	/**
	 * 获取全部污水厂
	 * @return
	 */
	public List<TapWaterEntity> getAllTapWaters(){
		return (List<TapWaterEntity>) tapWaterDao.findAll();
	}
	/**
	 * 保存提升泵站实体
	 * @return
	 */
	public void saveTapWater(TapWaterEntity entity){
		tapWaterDao.save(entity);
	}
	/**
	 * 通过id获得实体
	 * @return
	 */
	public TapWaterEntity getTapWaterById(Long id){
		TapWaterEntity entity=tapWaterDao.findOne(id);
		return entity;
	}
	/**
	 * 删除提升泵站实体
	 * @return
	 */
	public void deleTapWater(List<Long> ids){
		tapWaterDao.deleTapWatersByIds(ids);
	}
	/**
	 * 通过code获得实体
	 * @return
	 */
	public TapWaterEntity getTapWaterByCode(String code){
		TapWaterEntity entity=tapWaterDao.getTapWaterEntityByCode(code);
		return entity;
	}
	/**
	 * 按区域获得污水处理厂
	 * @param areaCode
	 * @return
	 */
	public List<TapWaterEntity> getTapWaterByAreaCode(String areaCode){
		List<TapWaterEntity> sewages = tapWaterDao.getTapWaterByAreaCode(areaCode);
		return sewages;
	}
}
