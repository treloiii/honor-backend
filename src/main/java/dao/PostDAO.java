package dao;

import com.honor.back.honorwebapp.HibernateSessionFactory;
import com.honor.back.honorwebapp.Post;
import dao.DAOSkeleton;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
@Component("postDao")
@Scope("prototype")
public class PostDAO implements DAOSkeleton {
    @Override
    public void update(Object updatedObject) {

    }

    @Override
    public void save(Object savedObject) {
        Session session=HibernateSessionFactory.getSession().openSession();
        session.beginTransaction();
        session.save(savedObject);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Post get(int id) {
        return HibernateSessionFactory.getSession().openSession().get(Post.class,id);

    }

    @Override
    public void delete(Object updatedObject) {

    }

    @Override
    public List<Post> getAll() {
        List<Post> posts= (List<Post>) HibernateSessionFactory.getSession().openSession().createQuery("From Post").list();
        //HibernateSessionFactory.getSession().openSession().flush();
        return posts;
    }
}
