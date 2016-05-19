package com.wqm.repository.waterCensus;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.wqm.entity.waterCensus.SDZEntity;


public interface SDZDao extends PagingAndSortingRepository<SDZEntity, Long>,JpaSpecificationExecutor<SDZEntity> {
	/**
	 * 获得全部的提防工程
	 * @return
	 */
	@Query("select SDZ from SDZEntity SDZ ")
	public List<SDZEntity> findAllParents();
}
