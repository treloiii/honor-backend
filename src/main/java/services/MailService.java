package services;

import Entities.Notifications;
import dao.NotificationsDao;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.util.List;

@Component
public class MailService {
    @Autowired
    NotificationsDao dao;
    @Autowired
    public JavaMailSender mailSender;
    public void doMailing(){
        List<Notifications> notifications=dao.getAll();
        notifications.forEach(n->{
            try {
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
                helper.setTo(n.getEmail());
                helper.setFrom("veteran-chest@mail.ru");
                helper.setSubject("На нашем сайте появились новые материалы!!!");
                helper.setText(
                        "Здравствуйте " + n.getName() + "!<br> " +
                                "<p>Вы получили это письмо потому что подписались на рассылку.</p>" +
                                "<p>На нашем <a href=\"http://veteran-chest.ru\">сайте</a> обновились материалы. Не пропустите!!</p><br>" +
                                "<a href=\"http://server.veteran-chest.ru/public/unsubscribe?id=" + n.getId() + "\">Отписаться от рассылки</a>",true);
                mailSender.send(message);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        });
    }
    public void subscribe(String email,String name){
        Notifications notifications=new Notifications();
        notifications.setEmail(email);
        notifications.setName(name);
        dao.save(notifications);
    }
    public void unsubscribe(int id){
        Notifications notifications=dao.get(id);
        dao.delete(notifications);
    }
}
