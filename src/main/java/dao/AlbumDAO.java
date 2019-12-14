package dao;

import Entities.GalleryAlbum;
import com.honor.back.honorwebapp.HibernateSessionFactory;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

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
        GalleryAlbum album=session.createQuery("From GalleryAlbum WHERE id="+id,GalleryAlbum.class).getSingleResult();
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
        List<GalleryAlbum> albums = session.createQuery("From GalleryAlbum",GalleryAlbum.class).setFirstResult(from).setMaxResults(to).list();
        session.getTransaction().commit();
        session.close();
        return albums;
    }
}
