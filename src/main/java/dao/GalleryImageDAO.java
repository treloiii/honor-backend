package dao;

import Entities.GalleryAlbum;
import Entities.GalleryComments;
import Entities.GalleryImage;
import com.honor.back.honorwebapp.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.cache.internal.EnabledCaching;
import org.hibernate.query.Query;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
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
        this.clearCache();
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

    @Override
    public Long getCount() {
        return null;
    }

    public List<GalleryImage> getLastFive(){
        Session session=HibernateSessionFactory.getSession().openSession();
        session.beginTransaction();
        List<GalleryImage> posts = session.createQuery("From GalleryImage c order by id desc",GalleryImage.class).setMaxResults(5).list();
        session.getTransaction().commit();
        session.close();
        return posts;
    }
    public GalleryImage getLast(){
        Session session=HibernateSessionFactory.getSession().openSession();
        session.beginTransaction();
        Query<GalleryImage> query = session.createQuery("select new GalleryImage(id,name,description,url) from GalleryImage order by id desc",GalleryImage.class).setMaxResults(1);
        query.setCacheable(true);
        query.setCacheRegion("LAST_IMAGE");
        GalleryImage image=query.getSingleResult();
        session.getTransaction().commit();
        session.close();
        return image;
    }

    @Override
    public void clearCache() {
        try {
            EnabledCaching cache = (EnabledCaching) HibernateSessionFactory.getSession().getCache();
            cache.evictCollectionData();
            cache.evict(GalleryImage.class);
            cache.evict(GalleryComments.class);
            cache.evict(GalleryAlbum.class);
           // cache.evictRegion("COUNT_DATA_ALBUM");
            cache.evictRegion("ALBUM_LIST");
            cache.evictRegion("LAST_IMAGE");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
