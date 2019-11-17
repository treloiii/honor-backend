package dao;

import Entities.GalleryImage;
import com.honor.back.honorwebapp.HibernateSessionFactory;
import org.hibernate.Session;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("galleryDao")
@Scope("prototype")
public class GalleryImageDAO implements DAOSkeleton {
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
    public GalleryImage get(int id) {
        Session session=HibernateSessionFactory.getSession().openSession();
        session.beginTransaction();
        GalleryImage image=session.get(GalleryImage.class,id);
        session.getTransaction().commit();
        return image;
    }

    @Override
    public void delete(Object updatedObject) {

    }

    @Override
    public List<GalleryImage> getAll() {
            Session session=HibernateSessionFactory.getSession().openSession();
            session.beginTransaction();
            List<GalleryImage> posts = session.createQuery("From GalleryImage c",GalleryImage.class).list();
            session.getTransaction().commit();
            session.close();
            return posts;
    }
}
