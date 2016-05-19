package com.wqm.repository.water;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.wqm.entity.water.SewageEntity;


public interface SewageDao extends PagingAndSortingRepository<SewageEntity, Long>,JpaSpecificationExecutor<SewageEntity> {
	
	/**
	 * 获得污水处理厂信息
	 * @return
	 */
	@Query("select sewage from SewageEntity sewage ")
	public List<SewageEntity> findAllParents();
	/**
	 * 删除处理厂
	 */
	@Modifying
	@Query("delete from SewageEntity sewage where sewage.id in (?1)")
	public void deleSewagesByIds(List<Long> ids);
	/**
	 * 按code获得水体项
	 * @return
	 */
	@Query("select sewage from SewageEntity sewage where sewage.code = ?1")
	public SewageEntity getSewageEntityByCode(String code);
	/**
	 * 按code获得全部的污水处理厂
	 * @return
	 */
	@Query("select sewage from SewageEntity sewage where  sewage.area.code = ?1")
	public List<SewageEntity> getSewageByAreaCode(String code);
}
