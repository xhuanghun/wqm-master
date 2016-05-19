package com.wqm.service.water.MediumReservoir;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.wqm.entity.water.mediumReservoir.MediumReservoirEntity;
import com.wqm.repository.water.MediumReservoir.MediumReservoirDao;


@Component
@Transactional
public class MediumReservoirService {
	
	@Autowired
	private MediumReservoirDao mediumReservoirDao;
	
	/**
	 * 分页查询污水处理厂
	 * 带查询条件spec
	 * @param pageRequest
	 * @param spec
	 * @return
	 */
	public Page<MediumReservoirEntity> getMediumReservoirsByPage(Specification<MediumReservoirEntity> spec,PageRequest pageRequest){
		return mediumReservoirDao.findAll(spec,pageRequest);
	}
	/**
	 * 获取全部污水厂
	 * @return
	 */
	public List<MediumReservoirEntity> getAllMediumReservoirs(){
		return (List<MediumReservoirEntity>) mediumReservoirDao.findAll();
	}
	/**
	 * 保存提升泵站实体
	 * @return
	 */
	public void saveMediumReservoir(MediumReservoirEntity entity){
		mediumReservoirDao.save(entity);
	}
	/**
	 * 通过id获得实体
	 * @return
	 */
	public MediumReservoirEntity getMediumReservoirById(Long id){
		MediumReservoirEntity entity=mediumReservoirDao.findOne(id);
		return entity;
	}
	/**
	 * 删除提升泵站实体
	 * @return
	 */
	public void deleMediumReservoir(List<Long> ids){
		mediumReservoirDao.deleMediumReservoirsByIds(ids);
	}
	/**
	 * 通过code获得实体
	 * @return
	 */
	public MediumReservoirEntity getMediumReservoirByCode(String code){
		MediumReservoirEntity entity=mediumReservoirDao.getMediumReservoirEntityByCode(code);
		return entity;
	}
	/**
	 * 按区域获得污水处理厂
	 * @param areaCode
	 * @return
	 */
	public List<MediumReservoirEntity> getMediumReservoirByAreaCode(String areaCode){
		List<MediumReservoirEntity> sewages = mediumReservoirDao.getMediumReservoirByAreaCode(areaCode);
		return sewages;
	}
}
