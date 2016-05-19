package com.wqm.repository.waterCensus;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.wqm.entity.waterCensus.NCGSGCEntity;


public interface NCGSGCDao extends PagingAndSortingRepository<NCGSGCEntity, Long>,JpaSpecificationExecutor<NCGSGCEntity> {
	/**
	 * 获得全部的提防工程
	 * @return
	 */
	@Query("select GS from NCGSGCEntity GS ")
	public List<NCGSGCEntity> findAllParents();
}
