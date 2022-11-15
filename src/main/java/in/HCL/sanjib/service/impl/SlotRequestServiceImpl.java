package in.HCL.sanjib.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import in.HCL.sanjib.constants.SlotStatus;
import in.HCL.sanjib.entity.SlotRequest;
import in.HCL.sanjib.repo.SlotRequestRepository;
import in.HCL.sanjib.service.ISlotRequestService;

public class SlotRequestServiceImpl implements ISlotRequestService {
	
	@Autowired
	private SlotRequestRepository repo;

	@Override
	public Long saveSlotRequest(SlotRequest sr) {
		
		return repo.save(sr).getId();
	}

	@Override
	public List<SlotRequest> getAllSlotRequests() {
		
		return repo.findAll();
	}
     
	@Transactional
	@Override
	public void updateSlotRequestStatus(Long id, String status) {
		
		repo.updateSlotsRequestStatus(id, status);

	}

	@Override
	public List<SlotRequest> viewSlotsByPatientMail(String patientMail) {
		
		return repo.getAllPatientSlots(patientMail);
	}

	@Override
	public SlotRequest getOneSlotRequest(Long id) {
		Optional<SlotRequest> opt = repo.findById(id);
		if(opt != null) {
			return opt.get();
		}
		return null;
	}

	@Override
	public List<SlotRequest> viewSlotsByDoctorMail(String doctorMail) {
		
		return repo.getAllDoctorSlots(doctorMail,SlotStatus.ACCEPTED.name());
	}

	

}
