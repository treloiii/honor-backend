package dao;

import Entities.GalleryAlbum;
import com.honor.back.honorwebapp.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;

@Component("albumDao")
public class AlbumDAO implements DAOSkeleton {

    @Override
    public void update(Object updatedObject) {
        Session session= HibernateSessionFactory.getSession().openSession();
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
    public GalleryAlbum get(int id) {
        Session session=HibernateSessionFactory.getSession().openSession();
        session.beginTransaction();
        Query query=session.createQuery("From GalleryAlbum WHERE id="+id,GalleryAlbum.class);
        GalleryAlbum album=(GalleryAlbum) query.getSingleResult();
        session.getTransaction().commit();
        session.close();
        return album;
    }

    @Override
    public void delete(Object updatedObject) {
        Session session=HibernateSessionFactory.getSession().openSession();
        session.beginTransaction();
        session.delete(updatedObject);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<GalleryAlbum> getAll(int from,int to) {
        Session session=HibernateSessionFactory.getSession().openSession();
        session.beginTransaction();
        Query query=session.createQuery("From GalleryAlbum",GalleryAlbum.class).setFirstResult(from).setMaxResults(to);
        query.setCacheable(true);
        List<GalleryAlbum> albums=query.list();
        session.getTransaction().commit();
        session.close();
        return albums;
    }

    @Override
    public Long getCount() {
        Session session =HibernateSessionFactory.getSession().openSession();
        session.beginTransaction();
        Query query=session.createQuery("SELECT COUNT(*) FROM Albums");
        query.setCacheable(true);
        System.out.println(query.getSingleResult());
        Long retVal= (Long) query.getSingleResult();
        session.getTransaction().commit();
        session.close();
        return retVal;
    }
}
