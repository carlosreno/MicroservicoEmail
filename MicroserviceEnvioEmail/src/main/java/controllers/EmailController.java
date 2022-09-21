package controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dtos.EmailDto;
import models.EmailModel;
import repositories.EmailRepository;
import services.EmailService;


@RequestMapping("/serviceMail")
@RestController
public class EmailController {
		
	@Autowired
	EmailService emailService;
	
	@Autowired
	EmailRepository emailRepository;
	
	@PostMapping(value = "/sendingemail", produces = "application/json")
	public ResponseEntity<EmailModel> sendingEmail(@RequestBody @Valid 	EmailDto emailDto){
		EmailModel emailModel = new EmailModel();
		BeanUtils.copyProperties(emailDto, emailModel);
		emailService.sendEmail(emailModel);
		return new ResponseEntity<EmailModel>(emailModel, HttpStatus.CREATED);
		
	}
	@GetMapping(value = "/listEmail", produces = "application/json")
	public ResponseEntity<List<EmailModel>> listTodosMails(){
		
		List<EmailModel> listMails = emailRepository.findAll();
		
		return new ResponseEntity<List<EmailModel>>(listMails,HttpStatus.OK);
	}
}
