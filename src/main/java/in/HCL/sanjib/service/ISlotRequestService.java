package in.HCL.sanjib.service;

import java.util.List;

import in.HCL.sanjib.entity.SlotRequest;

public interface ISlotRequestService {
	
	//Patient can book slot
	Long saveSlotRequest(SlotRequest sr);
	
	//fetch one
	SlotRequest getOneSlotRequest(Long id);
	
	//Admin can view all slots
	List<SlotRequest> getAllSlotRequests();
	
	//Admin/patient can update status
	void updateSlotRequestStatus(Long id,String status);
	
	//Patient can see his slots
	List<SlotRequest> viewSlotsByPatientMail(String patientMail);
	
	//DOCTOR can see his slots
	List<SlotRequest> viewSlotsByDoctorMail(String doctorMail);

}
