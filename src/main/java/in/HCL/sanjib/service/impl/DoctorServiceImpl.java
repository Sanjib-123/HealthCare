package in.HCL.sanjib.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.HCL.sanjib.constants.UserRoles;
import in.HCL.sanjib.entity.Doctor;
import in.HCL.sanjib.entity.User;
import in.HCL.sanjib.exception.DoctorNotFoundException;
import in.HCL.sanjib.repo.DoctorRepository;
import in.HCL.sanjib.service.IDoctorService;
import in.HCL.sanjib.service.IUserService;
import in.HCL.sanjib.util.MyCollectionsUtil;
import in.HCL.sanjib.util.MyMailUtil;
import in.HCL.sanjib.util.UserUtil;

@Service

public class DoctorServiceImpl implements IDoctorService  {
	
	@Autowired
	private DoctorRepository repo;
	
	@Autowired
	private IUserService userService;
	
	
	@Autowired
	private UserUtil util;
	
	@Autowired
	private MyMailUtil mailUtil;
	
	
	

	@Override
	public Long saveDoctor(Doctor doc) {
		Long id = repo.save(doc).getId();
		if(id != null) {
			String pwd = util.genPwd();
			User user = new User();
			user.setDisplayName(doc.getFirstName()+" "+doc.getLastName());
			user.setUsername(doc.getEmail());
			user.setPassword(util.genPwd());
			user.setRole(UserRoles.DOCTOR.name());
			//userService.saveUser(user);
			//TODO :Email part is pending
		
				Long genId = userService.saveUser(user);
				if(genId!=null)
					new Thread(new Runnable() {	
					public void run() {
				String text  = "Your name is " + doc.getEmail() + ",password is "+pwd;
				mailUtil.send(doc.getEmail(),"PATIENT ADDED",text);
					}
			}).start();
			
		}
		return id;
	}

	@Override
	public List<Doctor> getAllDoctors() {
		
		return repo.findAll();
	}

	@Override
	public void removeDoctor(Long id) {
		
		repo.delete(getOneDoctor(id));

	}

	@Override
	public Doctor getOneDoctor(Long id) {
		
		return repo.findById(id).orElseThrow(
				()->new DoctorNotFoundException(id+", not exist")
				);
	}

	@Override
	public void updateDoctor(Doctor doc) {
		if(repo.existsById(doc.getId()))
		    repo.save(doc);	
		else throw new DoctorNotFoundException(doc.getId()+", not exists");
			

	}

	@Override
	public Map<Long, String> getDoctorIdAndNames() {
		List<Object[]> list = repo.getDoctorIdAndNames();
		return MyCollectionsUtil.convertToMapIndex(list);
		
	}

}
