package dao;

import Entities.Ordens;
import com.honor.back.honorwebapp.HibernateSessionFactory;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import java.util.List;
@Component("ordensDao")
public class OrdensDAO implements DAOSkeleton {
    @Override
    public void update(Object updatedObject) {

    }

    @Override
    public void save(Object savedObject) {

    }

    @Override
    public Ordens get(int id) {
        Session session= HibernateSessionFactory.getSession().openSession();
        session.beginTransaction();
        Ordens orden=session.get(Ordens.class,id);
        session.getTransaction().commit();
        session.close();
        return orden;
    }

    @Override
    public void delete(Object updatedObject) {

    }

    @Override
    public List<Ordens> getAll(int from,int to) {
        Session session= HibernateSessionFactory.getSession().openSession();
        session.beginTransaction();
        List<Ordens> ordens = session.createQuery("From Ordens o",Ordens.class).list();
        session.getTransaction().commit();
        session.close();
        return ordens;
    }
}
