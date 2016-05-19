package com.wqm.repository.waterCensus;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.wqm.entity.waterCensus.DFGCEntity;


public interface DFGCDao extends PagingAndSortingRepository<DFGCEntity, Long>,JpaSpecificationExecutor<DFGCEntity> {
	/**
	 * 获得全部的提防工程
	 * @return
	 */
	@Query("select DF from DFGCEntity DF ")
	public List<DFGCEntity> findAllParents();
}
