package com.wqm.repository.waterCensus;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.wqm.entity.waterCensus.SKEntity;


public interface SKDao extends PagingAndSortingRepository<SKEntity, Long>,JpaSpecificationExecutor<SKEntity> {
	/**
	 * 获得全部的提防工程
	 * @return
	 */
	@Query("select SK from SKEntity SK ")
	public List<SKEntity> findAllParents();
}
