package co.istad.mbakingapi.features.mail;

import co.istad.mbakingapi.features.mail.dto.MailRequest;
import co.istad.mbakingapi.features.mail.dto.MailResponse;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
@Slf4j
public class MaiServiceImpl implements MailService{
    private final TemplateEngine templateEngine;
    //send mail
    private final JavaMailSender javaMailSender;
    @Override
    public MailResponse send(MailRequest mailRequest) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        Context context= new Context();

        context.setVariable("name","ISTAD");

       String htmlTemplate = templateEngine.process("mail/sample",context);

        try {
            mimeMessageHelper.setTo(mailRequest.to());
            mimeMessageHelper.setSubject(mailRequest.subject());
            mimeMessageHelper.setText(htmlTemplate,true);
        } catch (MessagingException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Something went wrong sending mail");
        }
        javaMailSender.send(mimeMessage);

        return new MailResponse("Mail has been sent!!");
    }
}
