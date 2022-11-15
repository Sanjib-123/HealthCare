package in.HCL.sanjib.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.HCL.sanjib.entity.Appointment;
import in.HCL.sanjib.entity.Patient;
import in.HCL.sanjib.entity.SlotRequest;
import in.HCL.sanjib.entity.User;
import in.HCL.sanjib.service.IAppointmentService;
import in.HCL.sanjib.service.IPatientService;
import in.HCL.sanjib.service.ISlotRequestService;

@Controller
@RequestMapping("/slots")
public class SlotRequestController {
	
	@Autowired
	private ISlotRequestService service;
	
	@Autowired
	private IAppointmentService appointmentService;
	
	@Autowired
	private IPatientService patientService;
	
	//patient id, appointment id
	@GetMapping("/book")
	public String bookSlots(
			@RequestParam Long appid,
			HttpSession session,
			Model model){
		Appointment app = appointmentService.getOneAppointment(appid);
		
		//for patient object
		User user = (User) session.getAttribute("userOb");
		String email = user.getUsername();
		
		Patient patient = patientService.getOneByEmail(email);
		
		//create slot object
		
		SlotRequest sr = new SlotRequest();
		sr.setAppointment(app);
		sr.setPatient(patient);
		sr.setStatus("PENDING");
		
		try {
			service.saveSlotRequest(sr);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
			  String appDte = sdf.format(app.getDate());
			
			String message = "Patient "+(patient.getFirstName()+" "+patient.getLastName())
					+", Request for Dr." + app.getDoctor().getFirstName()+" "+app.getDoctor().getFirstName()
					+",On Date :" + appDte +",submitted with status: "+sr.getStatus();
			model.addAttribute("message", message);
			
		}catch(Exception e) {
			e.printStackTrace();
			model.addAttribute("message","BOOKING REQUEST ALREADY MADE FOR THIS APPOINTMENT/DATE");
		}

		return "SlotRequestMessage";
	}
	
	@GetMapping("/all")
	public String viewAllReq(Model model) {
		List<SlotRequest> list = service.getAllSlotRequests();
		model.addAttribute(list);
		return "SlotRequestData";
	}
	
	@GetMapping("/patient")
	public String viewMyReq(
			Principal principal,
			Model model) {
		String email = principal.getName();
		List<SlotRequest> list = service.viewSlotsByPatientMail(email);
		model.addAttribute("list",list);
		return "SlotRequestDataPatient";
	}
	
	@GetMapping("/accept")
	public String updateSlotAccept(
			@RequestParam Long id) {
		
		service.updateSlotRequestStatus(id, "ACCEPTED");
		SlotRequest sr = service.getOneSlotRequest(id);
		if(sr.getStatus().equals("ACCEPTED")) {
			appointmentService.updateSlotCountForAppointment(
					sr.getAppointment().getId(),-1);
		}
		
		return "redirect:all";
		
	}
	@GetMapping("/reject")
	public String updateSlotReject(
			@RequestParam Long id
			) {
		
		service.updateSlotRequestStatus(id, "REJECTED");
		return "redirect:all";
		
	}
	
	@GetMapping("/cancel")
	public String cancelSlotRequest(
			@RequestParam Long id
			) {
		SlotRequest sr = service.getOneSlotRequest(id);
		if(sr.getStatus().equals("ACCEPTED")) {
			
			service.updateSlotRequestStatus(id, "CANCELLED");
			appointmentService.updateSlotCountForAppointment(
					sr.getAppointment().getId(), 1);
		}
		
		return "redirect:all";
		
	}


}
