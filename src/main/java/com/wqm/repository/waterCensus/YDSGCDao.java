package com.wqm.repository.waterCensus;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.wqm.entity.waterCensus.SZEntity;
import com.wqm.entity.waterCensus.YDSGCEntity;


public interface YDSGCDao extends PagingAndSortingRepository<YDSGCEntity, Long>,JpaSpecificationExecutor<YDSGCEntity> {
	/**
	 * 获得全部的提防工程
	 * @return
	 */
	@Query("select YDS from YDSGCEntity YDS ")
	public List<YDSGCEntity> findAllParents();
}
