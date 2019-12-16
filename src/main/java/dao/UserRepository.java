package dao;

import Entities.User;
import com.honor.back.honorwebapp.HibernateSessionFactory;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;

@Component("userRepository")
public class UserRepository implements DAOSkeleton {
    public User findByUsername(String username){
        Session session=HibernateSessionFactory.getSession().openSession();
        session.beginTransaction();

        Query query=session.createQuery("From User WHERE username = :paramName",User.class);
        query.setParameter("paramName",username);
        User user=(User) query.getSingleResult();
       // User user=session.get(User.class,username);
        session.getTransaction().commit();
        session.close();
        return user;
    }

    @Override
    public void update(Object updatedObject) {

    }

    @Override
    public void save(Object savedObject) {
        Session session= HibernateSessionFactory.getSession().openSession();
        session.beginTransaction();
        session.save(savedObject);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public User get(int id) {
        Session session=HibernateSessionFactory.getSession().openSession();
        session.beginTransaction();
        User post=session.get(User.class,id);
        session.getTransaction().commit();
        session.close();
        return post;
    }

    @Override
    public void delete(Object updatedObject) {

    }

    @Override
    public List<User> getAll(int from,int to) {
        Session session=HibernateSessionFactory.getSession().openSession();
        session.beginTransaction();
        List<User> posts= session.createQuery("From Post", User.class).list();
        session.getTransaction().commit();
        session.close();
        return posts;
    }

    @Override
    public Long getCount() {
        return null;
    }

    @Override
    public void clearCache() {
    }
}
