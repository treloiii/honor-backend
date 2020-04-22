package dao;

import Entities.News;
import Entities.Notifications;
import Entities.Redactable;
import com.honor.back.honorwebapp.HibernateSessionFactory;
import org.aspectj.weaver.ast.Not;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NotificationsDao implements DAOSkeleton<Notifications> {
    @Override
    public void update(Notifications updatedObject) {
        Session session= HibernateSessionFactory.getSession().openSession();
        session.beginTransaction();
        session.update(updatedObject);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void save(Notifications savedObject) {
        Session session=HibernateSessionFactory.getSession().openSession();
        session.beginTransaction();
        session.save(savedObject);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Notifications get(int id) {
        Session session= HibernateSessionFactory.getSession().openSession();
        session.beginTransaction();
        Notifications news=session.get(Notifications.class,id);
        session.getTransaction().commit();
        session.close();
        return news;
    }

    @Override
    public void delete(Notifications updatedObject) {
        Session session= HibernateSessionFactory.getSession().openSession();
        session.beginTransaction();
        session.delete(updatedObject);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Notifications> getAll(int from, int to) {
        throw new UnsupportedOperationException("this operation unsupported");
    }
    public List<Notifications> getAll(){
        Session session=HibernateSessionFactory.getSession().openSession();
        session.beginTransaction();
        Query<Notifications> query=session.createQuery("From Notifications n",Notifications.class);
        List<Notifications> allNews= query.list();
        session.getTransaction().commit();
        session.close();
        return allNews;
    }

    @Override
    public Long getCount() {
        throw new UnsupportedOperationException("this operation unsupported");
    }

    @Override
    public void clearCache() {
        throw new UnsupportedOperationException("this operation unsupported");
    }
}
