package in.HCL.sanjib.exception;

//import javax.management.RuntimeErrorException;

public class SpecializationNotFoundException extends RuntimeException {
	
private static final long serialVersionUID = 1L;
	
	public SpecializationNotFoundException () {
		super();
	}
	
	public SpecializationNotFoundException (String message) {
		super(message);
	}


}
