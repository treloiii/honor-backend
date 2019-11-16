package dao;

import com.honor.back.honorwebapp.GalleryAlbum;
import com.honor.back.honorwebapp.GalleryImage;
import com.honor.back.honorwebapp.HibernateSessionFactory;
import org.hibernate.Session;
import org.springframework.context.annotation.Scope;
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
        GalleryAlbum album=session.get(GalleryAlbum.class,id);
        session.getTransaction().commit();
        session.close();
        return album;
    }

    @Override
    public void delete(Object updatedObject) {

    }

    @Override
    public List<GalleryAlbum> getAll() {
        Session session=HibernateSessionFactory.getSession().openSession();
        session.beginTransaction();
        List<GalleryAlbum> albums = session.createQuery("From GalleryAlbum",GalleryAlbum.class).list();
        session.getTransaction().commit();
        session.close();
        return albums;
    }
}
