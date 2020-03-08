package dao;

import Entities.Redactable;
import com.honor.back.honorwebapp.HibernateSessionFactory;
import Entities.Post;
import org.hibernate.Session;
import org.hibernate.cache.internal.EnabledCaching;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sql.ResultedQuery;

import java.math.BigInteger;
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
        session.update(updatedObject);
        session.getTransaction().commit();
        session.close();
        this.clearCache();
    }

    @Override
    public void save(Object savedObject) {
        Session session=HibernateSessionFactory.getSession().openSession();
        session.beginTransaction();
        session.save(savedObject);
        session.getTransaction().commit();
        session.close();
        this.clearCache();
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
        Session session= HibernateSessionFactory.getSession().openSession();
        session.beginTransaction();
        session.delete(updatedObject);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Redactable> getAll(int from, int to) {
        Session session=HibernateSessionFactory.getSession().openSession();
        session.beginTransaction();
        Query query=session.createQuery("From Post p", Post.class).setFirstResult(from).setMaxResults(to);
        query.setCacheable(true);
        query.setCacheRegion("POST_LIST");
        List<Redactable> posts=query.list();
        session.getTransaction().commit();
        session.close();
        return posts;
    }

    @Override
    public Long getCount() {
        Session session =HibernateSessionFactory.getSession().openSession();
        session.beginTransaction();
        Query query=session.createQuery("SELECT COUNT(*) FROM Post");
        query.setCacheable(true);
        query.setCacheRegion("COUNT_DATA_POSTS");
        System.out.println(query.getSingleResult());
        Long retVal= (Long) query.getSingleResult();
        session.getTransaction().commit();
        session.close();
        return retVal;
    }

    public Post getLast(){
        Session session= HibernateSessionFactory.getSession().openSession();
        session.beginTransaction();
        Post post;
        Query<Post> query=session.createQuery("select new Post(id,title,title_image,coords) from Post order by id desc",Post.class).setMaxResults(1);
        query.setCacheable(true);
        query.setCacheRegion("LAST_POST");
        post=query.getSingleResult();
        session.getTransaction().commit();
        session.close();
//        Post post=new Post();
//        try {
//            ResultSet rs = rq.getResultSet("select id,title, title_image from honor_main_posts order by id desc");
//            rs.next();
//            post.setTitle_image(rs.getString("title_image"));
//            post.setTitle(rs.getString("title"));
//            post.setId(rs.getInt("id"));
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
        return post;
    }

    @Override
    public void clearCache() {
        try {
            EnabledCaching cache = (EnabledCaching) HibernateSessionFactory.getSession().getCache();
            cache.evictCollectionData();
            cache.evict(Post.class);
            cache.evictRegion("COUNT_DATA_POSTS");
            cache.evictRegion("POST_LIST");
            cache.evictRegion("LAST_POST");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
