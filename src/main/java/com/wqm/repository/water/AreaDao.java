package com.wqm.repository.water;


import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.wqm.entity.water.AreaEntity;



public interface AreaDao extends PagingAndSortingRepository<AreaEntity, Long>,JpaSpecificationExecutor<AreaEntity> {
	/**
	 * 根据父Id获取全部子区域
	 */
	public List<AreaEntity> getAreasByParentCode(String code,Sort sort);
	
	/**
	 * 根据父Code获取全部子区域
	 */
	@Query("select area from AreaEntity area where area.parentCode = ?1 order by area.sortNum")
	public List<AreaEntity> getAreasByParentCode(String code);
	
	/**
	 * 删除区域
	 */
	@Modifying
	@Query("delete from AreaEntity area where area.id in (?1)")
	public void deleAreasByIds(List<Long> ids);
	
	/**
	 * 按code 获取Area
	 * @param code
	 * @return
	 */
	public AreaEntity findAreaByCode(String code);
}
