package in.HCL.sanjib.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.HCL.sanjib.entity.Specialization;
import in.HCL.sanjib.exception.SpecializationNotFoundException;
import in.HCL.sanjib.repo.SpecializationRepository;
import in.HCL.sanjib.service.ISpecializationService;
import in.HCL.sanjib.util.MyCollectionsUtil;

@Service
public class SpecializationServiceImpl implements ISpecializationService {
	
	@Autowired
	private SpecializationRepository repo;

	@Override
	public Long saveSpecialization(Specialization spec) {
		
		return repo.save(spec).getId();
	}

	@Override
	public List<Specialization> getAllSpecializations() {
		
		return repo.findAll();
	}
	
	@Override
	public void removeSpecialization(Long id) {
		 //repo.deleteById(id);
		repo.delete(getOneSpecialization(id));
		
	}

	@Override
	public Specialization getOneSpecialization(Long id) {
		Optional<Specialization> optional = repo.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}else {
			throw new SpecializationNotFoundException(id+ " Not Found");
		}
		/**
		 * return repo.findById(id).orElseThrow(
		 * ()->new SpecializationNotFoundException(id+ " Not Found")
		 * );
		 */
		
	}

	@Override
	public void updateSpecialization(Specialization spec) {
		repo.save(spec);

	}

	public boolean isSpecCodeExist(String specCode) {
		/*
		 * Integer count = repo.getSpecCodeCount(specCode);
		 *  boolean exist = count > 0 ?
		 * true : false; return exist;
		 */
		return repo.getSpecCodeCount(specCode) > 0;
		
	}

	public boolean isSpecCodeExistForEdit(String specCode, Long id) {
		
		return  repo.getSpecCodeCountForEdit(specCode,id)>0;
	}

	@Override
	public Map<Long, String> getSpecIdAndName() {
		List<Object[]> list = repo.getSpecIdAndName();
		
		Map<Long,String> map = MyCollectionsUtil.convertListToMap(list);
		return map;
	}
	
	


}
