package com.wqm.repository.water.TapWater;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.wqm.entity.water.tapWater.TapWaterEntity;


public interface TapWaterDao extends PagingAndSortingRepository<TapWaterEntity, Long>,JpaSpecificationExecutor<TapWaterEntity> {
	
	/**
	 * 获得污水泵站信息
	 * @return
	 */
	@Query("select tapwater from TapWaterEntity tapwater ")
	public List<TapWaterEntity> findAllParents();
	/**
	 * 删除提升泵站
	 */
	@Modifying
	@Query("delete from TapWaterEntity tapwater where tapwater.id in (?1)")
	public void deleTapWatersByIds(List<Long> ids);
	/**
	 * 按code获得提升泵站
	 * @return
	 */
	@Query("select tapwater from TapWaterEntity tapwater where tapwater.code = ?1")
	public TapWaterEntity getTapWaterEntityByCode(String code);
	/**
	 * 按区域code获得全部的提升泵站实体
	 * @return
	 */
	@Query("select tapwater from TapWaterEntity tapwater where  tapwater.area.code = ?1")
	public List<TapWaterEntity> getTapWaterByAreaCode(String code);
}
