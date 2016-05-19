package com.wqm.repository.water.SewagePump;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.wqm.entity.water.SewageEntity;
import com.wqm.entity.water.sewagePump.SewagePumpEntity;


public interface SewagePumpDao extends PagingAndSortingRepository<SewagePumpEntity, Long>,JpaSpecificationExecutor<SewagePumpEntity> {
	
	/**
	 * 获得污水泵站信息
	 * @return
	 */
	@Query("select sewagepump from SewagePumpEntity sewagepump ")
	public List<SewagePumpEntity> findAllParents();
	/**
	 * 删除提升泵站
	 */
	@Modifying
	@Query("delete from SewagePumpEntity sewagepump where sewagepump.id in (?1)")
	public void deleSewagePumpsByIds(List<Long> ids);
	/**
	 * 按code获得提升泵站
	 * @return
	 */
	@Query("select sewagepump from SewagePumpEntity sewagepump where sewagepump.code = ?1")
	public SewagePumpEntity getSewagePumpEntityByCode(String code);
	/**
	 * 按区域code获得全部的提升泵站实体
	 * @return
	 */
	@Query("select sewagepump from SewagePumpEntity sewagepump where  sewagepump.area.code = ?1")
	public List<SewagePumpEntity> getSewagePumpByAreaCode(String code);
}
