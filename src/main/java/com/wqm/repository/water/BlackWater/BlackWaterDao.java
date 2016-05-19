package com.wqm.repository.water.BlackWater;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.wqm.entity.water.blackWater.BlackWaterEntity;


public interface BlackWaterDao extends PagingAndSortingRepository<BlackWaterEntity, Long>,JpaSpecificationExecutor<BlackWaterEntity> {
	
	/**
	 * 获得污水泵站信息
	 * @return
	 */
	@Query("select blackWater from BlackWaterEntity blackWater where blackWater.isLeaf=true")
	public List<BlackWaterEntity> findAllParents();
	/**
	 * 删除提升泵站
	 */
	@Modifying
	@Query("delete from BlackWaterEntity blackWater where blackWater.id in (?1)")
	public void deleBlackWatersByIds(List<Long> ids);
	/**
	 * 按code获得提升泵站
	 * @return
	 */
	@Query("select blackWater from BlackWaterEntity blackWater where blackWater.code = ?1")
	public BlackWaterEntity getBlackWaterEntityByCode(String code);
	/**
	 * 按区域code获得全部的提升泵站实体
	 * @return
	 */
	@Query("select blackWater from BlackWaterEntity blackWater where  blackWater.area.code = ?1 and blackWater.isLeaf=true")
	public List<BlackWaterEntity> getBlackWaterByAreaCode(String code);
}
