package angema.applications.hoursloader.core.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class EmailSenderUtil {
    @Autowired
    JavaMailSender emailSender;

    public void sendEmailWithAttachment() throws MessagingException, MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo("paletgerardo@gmail.com");
        helper.setSubject("info@angema.com.ar");
        helper.setText("hola desde mail.");

        // Agregar un archivo adjunto al correo electrónico
//        FileSystemResource file = new FileSystemResource(new File("ruta/al/archivo.pdf"));
        ClassPathResource file = new ClassPathResource("static/archivo.pdf");

        helper.addAttachment("Archivo.pdf", file);

        emailSender.send(message);
    }

    public void sendEmailWithAttachmentFromByteArray(byte[] pdfBytes, String filename) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo("paletgerardo@gmail.com");
        helper.setSubject("info@angema.com.ar");
        helper.setText("hola desde mail.");

        ByteArrayResource file = new ByteArrayResource(pdfBytes);

        helper.addAttachment(filename + ".pdf", file);

        emailSender.send(message);
    }

}
