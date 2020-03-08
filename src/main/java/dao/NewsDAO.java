package dao;
import Entities.Redactable;
import com.honor.back.honorwebapp.HibernateSessionFactory;
import Entities.News;
import org.hibernate.Session;
import org.hibernate.cache.internal.EnabledCaching;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sql.ResultedQuery;

import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Component("newsDao")
public class NewsDAO implements DAOSkeleton {
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
    public News get(int id) {
        Session session= HibernateSessionFactory.getSession().openSession();
        session.beginTransaction();
        News news=session.get(News.class,id);
        session.getTransaction().commit();
        session.close();
        return news;
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
        Query query=session.createQuery("From News n",News.class).setFirstResult(from).setMaxResults(to);
        query.setCacheable(true);
        query.setCacheRegion("NEWS_LIST");
        List<Redactable> allNews=query.list();
        session.getTransaction().commit();
        session.close();
        return allNews;
    }

    @Override
    public Long getCount() {
        Session session =HibernateSessionFactory.getSession().openSession();
        session.beginTransaction();
        Query query=session.createQuery("SELECT COUNT(*) FROM News");
        query.setCacheable(true);
        query.setCacheRegion("COUNT_DATA_NEWS");
        System.out.println(query.getSingleResult());
        Long retVal= (Long) query.getSingleResult();
        session.getTransaction().commit();
        session.close();
        return retVal;
    }

    public News getLast(){
        Session session= HibernateSessionFactory.getSession().openSession();
        session.beginTransaction();
        News news;
        Query<News> query=session.createQuery("select new News(id,title,title_image,coords) From News order by id desc",News.class).setMaxResults(1);
        query.setCacheable(true);
        query.setCacheRegion("NEWS_LAST");
        news=query.getSingleResult();
        session.getTransaction().commit();
        session.close();
//        News news=new News();
//        try {
//            ResultSet rs = rq.getResultSet("select id,title,title_image from honor_news order by id desc");
//            rs.next();
//            news.setTitle_image(rs.getString("title_image"));
//            news.setTitle(rs.getString("title"));
//            news.setId(rs.getInt("id"));
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
        return news;
    }

    @Override
    public void clearCache() {
        try {
            EnabledCaching cache = (EnabledCaching) HibernateSessionFactory.getSession().getCache();
            cache.evictCollectionData();
            cache.evict(News.class);
            cache.evictRegion("COUNT_DATA_NEWS");
            cache.evictRegion("NEWS_LIST");
            cache.evictRegion("NEWS_LAST");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
