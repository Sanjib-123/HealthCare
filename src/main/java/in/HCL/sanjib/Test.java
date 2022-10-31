package in.HCL.sanjib;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Test {

	public static void main(String[] args) {
		
		String pwd = "SAM";
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encPwd = encoder.encode(null);
		System.out.println(encPwd);

	}

}
