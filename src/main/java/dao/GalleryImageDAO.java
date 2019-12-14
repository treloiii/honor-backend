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
        GalleryImage image=session.createQuery("From GalleryImage WHERE id="+id,GalleryImage.class).getSingleResult();
        session.getTransaction().commit();
        session.close();
        return image;
    }

    @Override
    public void delete(Object deletedObject) {
        Session session=HibernateSessionFactory.getSession().openSession();
        session.beginTransaction();
        session.delete(deletedObject);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<GalleryImage> getAll(int from,int to) {
            Session session=HibernateSessionFactory.getSession().openSession();
            session.beginTransaction();
            List<GalleryImage> posts = session.createQuery("From GalleryImage c",GalleryImage.class).list();
            session.getTransaction().commit();
            session.close();
            return posts;
    }
    public List<GalleryImage> getLast(){
        Session session=HibernateSessionFactory.getSession().openSession();
        session.beginTransaction();
        List<GalleryImage> posts = session.createQuery("From GalleryImage c order by id desc",GalleryImage.class).setMaxResults(5).list();
        session.getTransaction().commit();
        session.close();
        return posts;
    }
}
