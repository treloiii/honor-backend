package dao;

import Entities.GalleryAlbum;
import Entities.GalleryComments;
import Entities.GalleryImage;
import com.honor.back.honorwebapp.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.cache.internal.EnabledCaching;
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
        this.clearCache();
    }

    @Override
    public List<GalleryAlbum> getAll(int from,int to) {
        Session session=HibernateSessionFactory.getSession().openSession();
        session.beginTransaction();
        Query query=session.createQuery("From GalleryAlbum",GalleryAlbum.class).setFirstResult(from).setMaxResults(to);
        query.setCacheable(true);
        query.setCacheRegion("ALBUM_LIST");
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
        query.setCacheRegion("COUNT_DATA_ALBUM");
        System.out.println(query.getSingleResult());
        Long retVal= (Long) query.getSingleResult();
        session.getTransaction().commit();
        session.close();
        return retVal;
    }

    @Override
    public void clearCache() {
        EnabledCaching cache = (EnabledCaching) HibernateSessionFactory.getSession().getCache();
        cache.evictCollectionData();
        cache.evict(GalleryAlbum.class);
        cache.evict(GalleryImage.class);
        cache.evict(GalleryComments.class);
        try {
            cache.evictRegion("COUNT_DATA_ALBUM");
        } catch (Exception e) {
        }
        try {
            cache.evictRegion("ALBUM_LIST");
        } catch (Exception e) {
        }
    }
}
