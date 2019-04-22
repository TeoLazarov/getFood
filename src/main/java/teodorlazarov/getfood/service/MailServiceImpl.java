package teodorlazarov.getfood.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import teodorlazarov.getfood.domain.models.service.OrderServiceModel;

import javax.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService {

    private static final String EMAIL_SUBJECT = "getFood Successful order";

    private final TemplateEngine templateEngine;
    private final JavaMailSender mailSender;

    @Autowired
    public MailServiceImpl(TemplateEngine templateEngine, JavaMailSender mailSender) {
        this.templateEngine = templateEngine;
        this.mailSender = mailSender;
    }

    @Override
    @Async
    public void sendEmail(OrderServiceModel orderServiceModel) {
        final Context context = new Context();
        context.setVariable("order", orderServiceModel);
        String body = this.templateEngine.process("email-template", context);

        sendPreparedMail(orderServiceModel.getUser().getEmail(), body, true);
    }

    private void sendPreparedMail(String to, String text, Boolean isHtml) {
        try {
            MimeMessage mail = this.mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(to);
            helper.setSubject(EMAIL_SUBJECT);
            helper.setText(text, isHtml);

            this.mailSender.send(mail);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
