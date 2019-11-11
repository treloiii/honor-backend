package dao;

import com.honor.back.honorwebapp.GalleryAlbum;
import com.honor.back.honorwebapp.GalleryImage;
import com.honor.back.honorwebapp.HibernateSessionFactory;
import org.hibernate.Session;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("albumDao")
@Scope("prototype")
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
    public Object get(int id) {
        return HibernateSessionFactory.getSession().openSession().get(GalleryAlbum.class,id);
    }

    @Override
    public void delete(Object updatedObject) {

    }

    @Override
    public List<GalleryAlbum> getAll() {
        List<GalleryAlbum> albums = (List<GalleryAlbum>) HibernateSessionFactory.getSession().openSession().createQuery("From GalleryAlbum").list();
        return albums;
    }
}
