package com.wqm.repository.water.WaterSource;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.wqm.entity.water.waterSource.WaterSourceEntity;


public interface WaterSourceDao extends PagingAndSortingRepository<WaterSourceEntity, Long>,JpaSpecificationExecutor<WaterSourceEntity> {
	
	/**
	 * 获得污水泵站信息
	 * @return
	 */
	@Query("select watersource from WaterSourceEntity watersource where watersource.isLeaf=true")
	public List<WaterSourceEntity> findAllParents();
	/**
	 * 删除提升泵站
	 */
	@Modifying
	@Query("delete from WaterSourceEntity watersource where watersource.id in (?1)")
	public void deleWaterSourcesByIds(List<Long> ids);
	/**
	 * 按code获得提升泵站
	 * @return
	 */
	@Query("select watersource from WaterSourceEntity watersource where watersource.code = ?1")
	public WaterSourceEntity getWaterSourceEntityByCode(String code);
	/**
	 * 按区域code获得全部的提升泵站实体
	 * @return
	 */
	@Query("select watersource from WaterSourceEntity watersource where  watersource.area.code = ?1 and watersource.isLeaf=true")
	public List<WaterSourceEntity> getWaterSourceByAreaCode(String code);
}
