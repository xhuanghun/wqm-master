package com.wqm.repository.waterCensus;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.wqm.entity.waterCensus.BZEntity;


public interface BZDao extends PagingAndSortingRepository<BZEntity, Long>,JpaSpecificationExecutor<BZEntity> {
	/**
	 * 获得全部的父水体项
	 * @return
	 */
	@Query("select BZ from BZEntity BZ ")
	public List<BZEntity> findAllParents();
}
