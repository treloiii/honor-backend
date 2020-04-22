package services;

import Entities.Notifications;
import dao.NotificationsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

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
            SimpleMailMessage message=new SimpleMailMessage();
            message.setTo(n.getEmail());
            message.setFrom("veteran-chest@mail.ru");
            message.setSubject("На нашем сайте появились новые материалы!!!");
            message.setText("Здравствуйте "+n.getName()+". Вы получили это письмо потому что подписались на рассылку." +
                    "На нашем сайте обновились материалы. Не пропустите!!");
            mailSender.send(message);
        });
    }
}
