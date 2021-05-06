package pe.com.sedapal.agc.util;
 
import java.lang.String;
import java.util.List;

//import javax.mail.MessagingException;
//import javax.mail.internet.MimeMessage;
 
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
//import org.thymeleaf.context.Context;
//import org.thymeleaf.spring5.SpringTemplateEngine;

import pe.com.sedapal.agc.model.request.EnvioCargaRequest;
import pe.com.sedapal.agc.model.Adjunto;
 
@Service
public class EmailService {
	/*
    @Autowired
    private JavaMailSender sender;
    
    @Autowired
    private SpringTemplateEngine templateEngine;  
    
	public void enviarMensaje(String from, String to, String subject, String text, boolean html) throws MessagingException {
		
		MimeMessage mimeMessage = sender.createMimeMessage();
		MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true,"UTF-8");
		
		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text, html);
		sender.send(mimeMessage);
		
	}

	public String crearCuerpo(EnvioCargaRequest carga, List<Adjunto> adjuntos, boolean sedapal) {
		try {		    	
			final Context context = new Context();			
	        context.setVariable("uidCarga", carga.getUidCarga());
	        context.setVariable("desContratista",carga.getDesContratista() );
	        context.setVariable("adjuntos", adjuntos);
	        context.setVariable("sedapal", sedapal); 
	        final String htmlContent = this.templateEngine.process("Correo_SEDAPAL.html", context);
	        return htmlContent;
		} catch (Exception e) {
			throw e;
		}
	}*/
}