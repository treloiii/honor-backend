package dao;
import com.honor.back.honorwebapp.HibernateSessionFactory;
import Entities.News;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sql.ResultedQuery;

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
    public List<News> getAll(int id) {
        Session session=HibernateSessionFactory.getSession().openSession();
        session.beginTransaction();
        List<News> allNews = session.createQuery("From News",News.class).list();
        session.getTransaction().commit();
        session.close();
        return allNews;
    }

    public News getLast(){
        Session session= HibernateSessionFactory.getSession().openSession();
        session.beginTransaction();
        News news=session.createQuery("From News order by id desc",News.class).setMaxResults(1).getSingleResult();
        session.getTransaction().commit();
        session.close();
        return news;
    }
}
