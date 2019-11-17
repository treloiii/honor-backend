package dao;

import Entities.Rally;
import com.honor.back.honorwebapp.HibernateSessionFactory;
import org.hibernate.Session;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
@Component("rallyDao")
@Scope("prototype")
public class RallyDAO implements DAOSkeleton {
    @Override
    public void update(Object updatedObject) {

    }

    @Override
    public void save(Object savedObject) {

    }

    @Override
    public Rally get(int id) {
        Session session=HibernateSessionFactory.getSession().openSession();
        session.beginTransaction();
        Rally rally=session.get(Rally.class,id);
        session.getTransaction().commit();
        session.close();
        return rally;
    }

    @Override
    public void delete(Object updatedObject) {

    }

    @Override
    public List<Rally> getAll() {
        Session session= HibernateSessionFactory.getSession().openSession();
        session.beginTransaction();
        List<Rally> rallies= session.createQuery("From Rally", Rally.class).list();
        session.getTransaction().commit();
        session.close();
        return rallies;
    }
}
