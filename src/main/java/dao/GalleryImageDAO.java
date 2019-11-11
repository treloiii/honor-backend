package dao;

import com.honor.back.honorwebapp.GalleryImage;
import com.honor.back.honorwebapp.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.context.annotation.Bean;
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
        return HibernateSessionFactory.getSession().openSession().get(GalleryImage.class,id);
    }

    @Override
    public void delete(Object updatedObject) {

    }

    @Override
    public List<GalleryImage> getAll() {
            List<GalleryImage> posts = (List<GalleryImage>) HibernateSessionFactory.getSession().openSession().createQuery("From GalleryImage").list();
            return posts;
    }
}
