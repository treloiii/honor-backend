package dao;

import Entities.Actions;
import com.honor.back.honorwebapp.HibernateSessionFactory;
import org.hibernate.Session;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
@Component("rallyDao")
@Scope("prototype")
public class ActionsDAO implements DAOSkeleton {
    @Override
    public void update(Object updatedObject) {

    }

    @Override
    public void save(Object savedObject) {

    }

    @Override
    public Actions get(int id) {
        Session session=HibernateSessionFactory.getSession().openSession();
        session.beginTransaction();
        Actions actions =session.get(Actions.class,id);
        session.getTransaction().commit();
        session.close();
        return actions;
    }

    @Override
    public void delete(Object updatedObject) {

    }

    @Override
    public List<Actions> getAll(int type) {
        Session session= HibernateSessionFactory.getSession().openSession();
        session.beginTransaction();
        List<Actions> rallies= session.createQuery("From Actions WHERE type="+type, Actions.class).list();
        session.getTransaction().commit();
        session.close();
        return rallies;
    }
    public Actions getLast(int type){
        Session session= HibernateSessionFactory.getSession().openSession();
        session.beginTransaction();
        Actions news=session.createQuery("From Actions where type="+type+" order by id desc",Actions.class).setMaxResults(1).getSingleResult();
        session.getTransaction().commit();
        session.close();
        return news;
    }
}
