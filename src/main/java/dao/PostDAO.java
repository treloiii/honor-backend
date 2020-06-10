package dao;

import Entities.Comments;
import Entities.deprecated.Redactable;
import com.honor.back.honorwebapp.HibernateSessionFactory;
import Entities.Post;
import org.hibernate.Session;
import org.hibernate.cache.internal.EnabledCaching;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sql.ResultedQuery;

import java.util.List;
@Component("postDao")
@Scope("prototype")
public class PostDAO implements DAOSkeleton<Post> {
    @Override
    public void update(Post updatedObject) {
        Session session=HibernateSessionFactory.getSession().openSession();
        session.beginTransaction();
        session.update(updatedObject);
        session.getTransaction().commit();
        session.close();
        this.clearCache();
    }
    public void updateComment(Comments updatedObject) {
        Session session=HibernateSessionFactory.getSession().openSession();
        session.beginTransaction();
        session.update(updatedObject);
        session.getTransaction().commit();
        session.close();
        this.clearCache();
    }

    @Override
    public void save(Post savedObject) {
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
    public void delete(Post updatedObject) {
        Session session= HibernateSessionFactory.getSession().openSession();
        session.beginTransaction();
        session.delete(updatedObject);
        session.getTransaction().commit();
        session.close();
    }

    public void deleteComment(Comments updatedObject) {
        Session session= HibernateSessionFactory.getSession().openSession();
        session.beginTransaction();
        session.delete(updatedObject);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Post> getAll(int from, int to,String type) {
        Session session=HibernateSessionFactory.getSession().openSession();
        session.beginTransaction();
        Query<Post> query=session.createQuery("From Post p where p.type=:type", Post.class)
                .setParameter("type",type)
                .setFirstResult(from)
                .setMaxResults(to);
        query.setCacheable(true);
        query.setCacheRegion("POST_LIST");
        List<Post> posts=query.list();
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

    public Post getLast(String type){
        Session session= HibernateSessionFactory.getSession().openSession();
        session.beginTransaction();
        Post post;
        Query<Post> query=session
                .createQuery("select new Post(id,title,title_image,title_image_mini) from Post p where p.type=:type order by id desc",Post.class)
                .setParameter("type",type)
                .setMaxResults(1);
        query.setCacheable(true);
        query.setCacheRegion("LAST_POST");
        post=query.getSingleResult();
        session.getTransaction().commit();
        session.close();
        return post;
    }

    @Override
    public void clearCache() {
        EnabledCaching cache = (EnabledCaching) HibernateSessionFactory.getSession().getCache();
        cache.evictCollectionData();
        cache.evict(Post.class);
        try {
            cache.evictRegion("COUNT_DATA_POSTS");
        } catch (Exception e) {
        }
        try {
            cache.evictRegion("POST_LIST");
        } catch (Exception e) {
        }
        try {
            cache.evictRegion("LAST_POST");
        } catch (Exception e) {
        }
    }
}
