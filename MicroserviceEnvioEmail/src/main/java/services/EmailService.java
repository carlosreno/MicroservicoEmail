package services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import enums.StatusEmail;
import models.EmailModel;
import repositories.EmailRepository;

@Service
public class EmailService {

	@Autowired
	EmailRepository emailRepository;
	
	@Autowired
	JavaMailSender javaMailSender;

	@SuppressWarnings("finally")
	public EmailModel sendEmail(EmailModel emailModel) {
		emailModel.setSendDateTime(LocalDateTime.now());
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(emailModel.getEmailFrom());
			message.setTo(emailModel.getEmailTo());
			message.setSubject(emailModel.getSubject());
			message.setText(emailModel.getText());
			javaMailSender.send(message);
			
			emailModel.setStatusEmail(StatusEmail.SENT);
			
			
		} catch (Exception e) {
			
				emailModel.setStatusEmail(StatusEmail.ERROR);
				System.out.println(e);
			
		}finally {
			return emailRepository.save(emailModel);
		}
		
		
	}
	

	
}
