package com.wqm.repository.waterCensus;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.wqm.entity.waterCensus.SZEntity;


public interface SZDao extends PagingAndSortingRepository<SZEntity, Long>,JpaSpecificationExecutor<SZEntity> {
	/**
	 * 获得全部的提防工程
	 * @return
	 */
	@Query("select SZ from SZEntity SZ ")
	public List<SZEntity> findAllParents();
}
