package angema.applications.hoursloader.core.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.List;

@Component
public class EmailSenderUtil {
    @Autowired
    JavaMailSender emailSender;

    final static String EMAIL_MSG = "Email enviado automaticamente. Luego se adjuntara a esta cadena, la factura correspondiente al informe de horas.";


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

    public void sendEmailWithAttachmentFromByteArray(byte[] pdfBytes, String filename,byte[] pdfFactura, String filenameFactura, List<String> emails, String msg) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        String emailString = String.join(",", emails);
        helper.setTo(InternetAddress.parse(emailString));
//        helper.setSubject("info@angema.com.ar");
        helper.setSubject(msg);
        helper.setText(EMAIL_MSG);

        ByteArrayResource file = new ByteArrayResource(pdfBytes);
        ByteArrayResource file2 = new ByteArrayResource(pdfFactura);

        helper.addAttachment(filename + ".pdf", file);
        helper.addAttachment(filenameFactura + ".pdf", file2);

        emailSender.send(message);
    }

    public void sendEmailMsg(String msg) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo("paletgerardo.devs@gmail.com");
        helper.setSubject("Errores en la carga de horas");
        helper.setText(msg);
        emailSender.send(message);
    }
}
