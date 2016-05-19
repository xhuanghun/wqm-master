package com.wqm.repository.water.MediumReservoir;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.wqm.entity.water.mediumReservoir.MediumReservoirEntity;


public interface MediumReservoirDao extends PagingAndSortingRepository<MediumReservoirEntity, Long>,JpaSpecificationExecutor<MediumReservoirEntity> {
	
	/**
	 * 获得污水泵站信息
	 * @return
	 */
	@Query("select mediumReservoir from MediumReservoirEntity mediumReservoir ")
	public List<MediumReservoirEntity> findAllParents();
	/**
	 * 删除提升泵站
	 */
	@Modifying
	@Query("delete from MediumReservoirEntity mediumReservoir where mediumReservoir.id in (?1)")
	public void deleMediumReservoirsByIds(List<Long> ids);
	/**
	 * 按code获得提升泵站
	 * @return
	 */
	@Query("select mediumReservoir from MediumReservoirEntity mediumReservoir where mediumReservoir.code = ?1")
	public MediumReservoirEntity getMediumReservoirEntityByCode(String code);
	/**
	 * 按区域code获得全部的提升泵站实体
	 * @return
	 */
	@Query("select mediumReservoir from MediumReservoirEntity mediumReservoir where  mediumReservoir.area.code = ?1")
	public List<MediumReservoirEntity> getMediumReservoirByAreaCode(String code);
}
