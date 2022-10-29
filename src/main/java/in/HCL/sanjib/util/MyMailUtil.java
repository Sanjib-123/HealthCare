package in.HCL.sanjib.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import javax.mail.internet.MimeMessage;

import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.MimeMessageHelper;

@Component
public class MyMailUtil {
	@Autowired
	private JavaMailSender mailSender;

	public boolean send(
			String to[],
			String cc[],
			String bcc[],
			String subject,
			String text,
			Resource files[]) {

		boolean sent = false;

		try {
			//1. create one empty message
			MimeMessage message = mailSender.createMimeMessage();

			//2.fill details(message, attachmentExists?)
			MimeMessageHelper helper = new MimeMessageHelper(message,  files != null && files.length>0);

			helper.setTo(to);

			if(cc != null)
				helper.setCc(cc);
			if(bcc!=null)
				helper.setBcc(bcc);

			helper.setSubject(subject);
			helper.setText(text);

			if(files!=null) {
				for(Resource rob: files) {
					helper.addAttachment(rob.getFilename(), rob);
				}
			}
			//3. send email
			mailSender.send(message);

			sent = true;

		}catch(Exception e) {
			e.printStackTrace();
			sent = false;
		}
		return sent;
	}

	/**Overloaded methods*/
	 public boolean send(
			 String to,
			 String subject,
			 String text,

			 Resource file) 
	 {
		 return send(new String[] {to},
				 null,null, 
				 subject, text, 
				 file!=null?new Resource[] {file}:null);
	 }

	 public boolean send(
			 String to,
			 String subject,
			 String text

			) {
		 return send(to, subject, text,null);

}
}
