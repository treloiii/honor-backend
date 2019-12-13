package dao;

import com.honor.back.honorwebapp.HibernateSessionFactory;
import Entities.Post;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sql.ResultedQuery;

import java.sql.ResultSet;
import java.util.List;
@Component("postDao")
@Scope("prototype")
public class PostDAO implements DAOSkeleton {
    @Autowired
    private ResultedQuery rq;
    @Override
    public void update(Object updatedObject) {
        Session session=HibernateSessionFactory.getSession().openSession();
        session.beginTransaction();
        session.save(updatedObject);
        session.getTransaction().commit();
        session.close();
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
        Session session=HibernateSessionFactory.getSession().openSession();
        session.beginTransaction();
        Post post=session.get(Post.class,id);
        session.getTransaction().commit();
        session.close();
        return post;
    }

    @Override
    public void delete(Object updatedObject) {

    }

    @Override
    public List<Post> getAll(int id) {
        Session session=HibernateSessionFactory.getSession().openSession();
        session.beginTransaction();
        List<Post> posts= session.createQuery("From Post", Post.class).list();
        session.getTransaction().commit();
        session.close();
        return posts;
    }
    public Post getLast(){
//        Session session= HibernateSessionFactory.getSession().openSession();
//        session.beginTransaction();
//        Post post=session.createQuery("From Post order by id desc",Post.class).setMaxResults(1).getSingleResult();
//        session.getTransaction().commit();
//        session.close();
        Post post=new Post();
        try {
            ResultSet rs = rq.getResultSet("select id,title, title_image from honor_main_posts order by id desc");
            rs.next();
            post.setTitle_image(rs.getString("title_image"));
            post.setTitle(rs.getString("title"));
            post.setId(rs.getInt("id"));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return post;
    }
}
