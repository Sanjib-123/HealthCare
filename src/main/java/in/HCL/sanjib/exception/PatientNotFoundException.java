package in.HCL.sanjib.exception;

public class PatientNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public PatientNotFoundException() {
		super();
	}
	
	public PatientNotFoundException(String message) {
		super(message);
	}

}
