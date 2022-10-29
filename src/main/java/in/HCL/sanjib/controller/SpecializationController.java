package in.HCL.sanjib.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.HCL.sanjib.entity.Specialization;
import in.HCL.sanjib.exception.SpecializationNotFoundException;
import in.HCL.sanjib.service.ISpecializationService;
import in.HCL.sanjib.view.SpecializationExcelView;

@Controller
@RequestMapping("/spec")
public class SpecializationController {
	

	@Autowired
	private ISpecializationService service;
	
	/**
	 * 1.Show Register page
	 */
	
	@GetMapping("/register")
	public String displayReg() {
		return"specializationRegister";
	}
	
	/**
	 * 2. On Submit save data
	 */
	
	@PostMapping("/save")
	public String save(
			@ModelAttribute Specialization specialization,
			           Model model) 
	{
		Long id = service.saveSpecialization(specialization);
		String message = "Record ("+id+") is created";
		model.addAttribute("message", message);
		return "specializationRegister"; 
	}
	
	/**
	 * 3. display all specializations 
	 */
	
	@GetMapping("/all")
	public String viewAll(
			Model model,
			@RequestParam(value = "message", required = false) String message
			       ) {
		List<Specialization> list = service.getAllSpecializations();
		model.addAttribute("list", list);
		model.addAttribute("message", message);
		return "SpecializationData";
	}
	
	/**
	 * 4.Delete by id
	 */
	
	@GetMapping("/delete")
	public String deleteData(
			@RequestParam Long id,
			RedirectAttributes attributes
			) {
		try {
		service.removeSpecialization(id);
		attributes.addAttribute("message","Record ("+id+") is removed");
		}catch (SpecializationNotFoundException e) {
			e.printStackTrace();
			attributes.addAttribute("message", e.getMessage());
		}
		return "redirect:all";
	}
		
		/**
		 * 5.Fetch Data into Edit page
		 * End user clicks on Link,may enter ID manually.
		 * If entered id is present in DB
		 *     >Load Row as Object
		 *     >Send to edit page
		 *     >Fill in Form
		 * Else
		 *     >Redirect to all (Data page)
		 *     >Show Error message(Not found) 
		 * 
		 */
		
		@GetMapping("/edit")
		public String showEditPage(
				@RequestParam Long id,
				Model model,
				RedirectAttributes attributes
				) {
			String page = null;
			try {
				Specialization spec = service.getOneSpecialization(id);
				model.addAttribute("specialization", spec);
				page = "SpecializationEdit";
				
			}catch(SpecializationNotFoundException e) {
				e.printStackTrace();
				attributes.addAttribute("message", e.getMessage());
				page = "redirect:all";
				
			}
			return page;
		}
		
		/**
		 * 6.Update Form data and redirect to all
		 */
		@PostMapping("/update")
		public String updateData(
				@ModelAttribute Specialization specialization,
				RedirectAttributes attributes
				) {
			service.updateSpecialization(specialization);
			attributes.addAttribute("message", "Record ("+specialization.getId()+") is updated");
			return "redirect:all";
		}
		
		/**
		 * 7.Read code and check with service
		 * return message to UI
		 */
		@GetMapping("/checkCode")
		@ResponseBody
		public  String validateSpecCode(
				@RequestParam String code,
				@RequestParam Long id
				) {
			
			String message = "";
			if(id==0 && service.isSpecCodeExist(code)) {
				message = code + ",already exist";
				
			}else if(id!=0 && service.isSpecCodeExistForEdit(code,id)) { //edit check
				message = code + ",already exist";
			}	
			
			
			return message; //this is not viewName(it is message)
		}
		
		/**
		 * 8. export data to excel
		 */
		@GetMapping("/excel")
		public ModelAndView exportToExcel() {
			ModelAndView m = new ModelAndView();
			m.setView(new SpecializationExcelView());
			
			//read data from DB
			List<Specialization> list = service.getAllSpecializations();
			//send to Excel Impl class
			m.addObject("list",list);
			
			return m;
		}
		

	

}
