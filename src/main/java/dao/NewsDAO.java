package dao;
import com.honor.back.honorwebapp.HibernateSessionFactory;
import Entities.News;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sql.ResultedQuery;

import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.sql.ResultSet;
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

    }

    @Override
    public List<News> getAll(int from,int to) {
        Session session=HibernateSessionFactory.getSession().openSession();
        session.beginTransaction();
        Query query=session.createQuery("From News n",News.class).setFirstResult(from).setMaxResults(to);
        query.setCacheable(true);
        List<News> allNews=query.list();
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
        System.out.println(query.getSingleResult());
        return (Long) query.getSingleResult();
    }

    public News getLast(){
//        Session session= HibernateSessionFactory.getSession().openSession();
//        session.beginTransaction();
//        News news=session.createQuery("select new News(id,title,title_image) From News order by id desc",News.class).setMaxResults(1).getSingleResult();
//        session.getTransaction().commit();
//        session.close();
        News news=new News();
        try {
            ResultSet rs = rq.getResultSet("select id,title,title_image from honor_news order by id desc");
            rs.next();
            news.setTitle_image(rs.getString("title_image"));
            news.setTitle(rs.getString("title"));
            news.setId(rs.getInt("id"));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return news;
    }
}
