package com.wqm.repository.water.WaterSupply;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.wqm.entity.water.waterSupply.WaterSupplyEntity;


public interface WaterSupplyDao extends PagingAndSortingRepository<WaterSupplyEntity, Long>,JpaSpecificationExecutor<WaterSupplyEntity> {
	
	/**
	 * 获得污水泵站信息
	 * @return
	 */
	@Query("select waterSupply from WaterSupplyEntity waterSupply ")
	public List<WaterSupplyEntity> findAllParents();
	/**
	 * 删除提升泵站
	 */
	@Modifying
	@Query("delete from WaterSupplyEntity waterSupply where waterSupply.id in (?1)")
	public void deleWaterSupplysByIds(List<Long> ids);
	/**
	 * 按code获得提升泵站
	 * @return
	 */
	@Query("select waterSupply from WaterSupplyEntity waterSupply where waterSupply.code = ?1")
	public WaterSupplyEntity getWaterSupplyEntityByCode(String code);
	/**
	 * 按区域code获得全部的提升泵站实体
	 * @return
	 */
	@Query("select waterSupply from WaterSupplyEntity waterSupply where  waterSupply.area.code = ?1")
	public List<WaterSupplyEntity> getWaterSupplyByAreaCode(String code);
}
