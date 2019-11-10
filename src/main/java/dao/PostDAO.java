package dao;

import com.honor.back.honorwebapp.HibernateSessionFactory;
import com.honor.back.honorwebapp.Post;
import dao.DAOSkeleton;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PostDAO implements DAOSkeleton {
    @Override
    public void update(Object updatedObject) {

    }

    @Override
    public void save(Object savedObject) {
        Session session=HibernateSessionFactory.getSession();
        session.beginTransaction();
        session.save(savedObject);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Post get(int id) {
        return HibernateSessionFactory.getSession().get(Post.class,id);

    }

    @Override
    public void delete(Object updatedObject) {

    }

    @Override
    public List<Post> getAll() {
        List<Post> posts= (List<Post>) HibernateSessionFactory.getSession().createQuery("From Post").list();
        return posts;
    }
}
