package com.wqm.service.water;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.wqm.entity.water.WaterEntity;
import com.wqm.repository.water.WaterDao;


@Component
@Transactional
public class WaterService {
	
	@Autowired
	private WaterDao waterDao;
	@PersistenceContext
	private EntityManager entityManager;
	/**
	 * 获取一级水体
	 * @return
	 */
	public List<WaterEntity> getWaterListByRoot(){
		return waterDao.getWatersByParentCode("root",new Sort(Direction.ASC, "sortNum"));
	}
	
	/**
	 * 按ID查找水体
	 * @param id
	 * @return
	 */
	public WaterEntity getWaterById(Long id){
		return waterDao.findOne(id);
	}
	
	/**
	 * 按ID查找水体
	 * @param id
	 * @return
	 */
	public WaterEntity getWaterByCode(String code){
		return waterDao.getWaterEntityByCode(code);
	}
	/**
	 * 按水体name查找
	 * @param name
	 * @return
	 */
	public WaterEntity getWaterByName(String name){
		return waterDao.getWaterEntityByName(name);
	}
	
	/**
	 * 按ID获取子水体
	 * @param id
	 * @return
	 */
	public List<WaterEntity> getWaterListById(String code,Sort sort){
		return waterDao.getWatersByParentCode(code,sort);
	}
	
	/**
	 * 按区域获得水体
	 * @param areaCode
	 * @return
	 */
	public List<WaterEntity> getWaterByAreaCode(String areaCode){
		List<WaterEntity> waters = waterDao.getWaterByAreaCode(areaCode);
		return waters;
	}
	/**
	 * 按区域获得水体
	 * @param areaCode
	 * @return
	 */
	public List<WaterEntity> getWaterByAreaCodeModuleType(String areaCode,String moduleType){
		List<WaterEntity> waters = waterDao.getWaterByAreaCodeModuleType(areaCode,moduleType);
		return waters;
	}
	
	/**
	 * 按code获取子水体
	 * @param id
	 * @return
	 */
	public List<WaterEntity> getWaterListByCode(String code){
		return waterDao.getWatersByParentCode(code);
	}
	
	/**
	 * 获取全部水体
	 * @return
	 */
	public List<WaterEntity> getAllWaters(){
		return (List<WaterEntity>) waterDao.findAll();
	}
	
	/**
	 * 分页查询水体
	 * 带查询条件spec
	 * @param pageRequest
	 * @param spec
	 * @return
	 */
	public Page<WaterEntity> getWatersByPage(Specification<WaterEntity> spec,PageRequest pageRequest){
		return waterDao.findAll(spec,pageRequest);
	}
	
	/**
	 * 获取全部的父水体项
	 * @return
	 */
	public List<WaterEntity> getAllParents(){
		List<WaterEntity> waters = (List<WaterEntity>) waterDao.findAllParents();
		WaterEntity water = new WaterEntity();
		water.setId(0L);
		water.setCode("0");
		water.setName("全部水体");
		waters.add(water);
		return waters;
	}
	
	/**
	 * 保存水体
	 * @param water
	 * @return
	 */
	public WaterEntity saveWater(WaterEntity water){
		return waterDao.save(water);
	}
	
	/**
	 * 删除水体(这个删除的时候有错误，从数据类型 varchar 转换为 bigint 时出错）
	 * @param ids
	 */
	public void deleWater(List<Long> ids){
		waterDao.deleWatersByIds(ids);
	}
	public void deleWaterListId(List<Long> ids){
		for(Long id : ids){
			this.entityManager.createNativeQuery("DELETE FROM busi_wtr_water WHERE  id="+id+"").executeUpdate();
		}
	}
	/**
	 * 按水体类型获取水体
	 * @param areaCode
	 * @return
	 */
	public List<WaterEntity> getWaterByIs_leaf(boolean Is_leaf){
		List<WaterEntity> waters = waterDao.getWaterByIs_leaf(Is_leaf);
		return waters;
	}
	/**
	 * 按水体类型和模块类型获取水体
	 * @param areaCode
	 * @return
	 */
	public List<WaterEntity> getWaterByIs_leafModuleType(boolean Is_leaf,String moduleType){
		List<WaterEntity> waters = waterDao.getWaterByIs_leafModuleType(Is_leaf,moduleType);
		return waters;
	}
}
