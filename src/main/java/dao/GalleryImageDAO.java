package dao;

import com.honor.back.honorwebapp.GalleryImage;
import com.honor.back.honorwebapp.HibernateSessionFactory;
import java.util.List;

public class GalleryImageDAO implements DAOSkeleton {
    @Override
    public void update(Object updatedObject) {

    }

    @Override
    public void save(Object savedObject) {

    }

    @Override
    public Object get(int id) {
        return HibernateSessionFactory.getSession().get(GalleryImage.class,id);
    }

    @Override
    public void delete(Object updatedObject) {

    }

    @Override
    public List getAll() {
        List<GalleryImage> posts= (List<GalleryImage>) HibernateSessionFactory.getSession().createQuery("From GalleryImage").list();
        return posts;
    }
}
