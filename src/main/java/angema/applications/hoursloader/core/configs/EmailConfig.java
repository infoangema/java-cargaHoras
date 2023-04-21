package angema.applications.hoursloader.core.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig {

    @Value("${spring.mail.host}")
    private String EMAIL_HOST;

    @Value("${spring.mail.port}")
    private Integer EMAIL_PORT;

    @Value("${spring.mail.password}")
    private String EMAIL_PASSWORD;

    @Value("${spring.mail.username}")
    private String EMAIL_USER;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private boolean EMAIL_AUTH;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private boolean EMAIL_TLS;
    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(EMAIL_HOST);
        mailSender.setPort(EMAIL_PORT);

        mailSender.setUsername(EMAIL_USER);
        mailSender.setPassword(EMAIL_PASSWORD);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", EMAIL_AUTH);
        props.put("mail.smtp.starttls.enable", EMAIL_TLS);
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.debug", "true");

        return mailSender;
    }
}
