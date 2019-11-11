package services;

import com.honor.back.honorwebapp.GalleryComments;
import com.honor.back.honorwebapp.GalleryImage;
import dao.GalleryImageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("galleryService")
public class GalleryService {
    @Autowired
    GalleryImageDAO dao;

    public GalleryService() {
    }

    public List<GalleryImage> getAllGallery(){
        return dao.getAll();
    }

    public GalleryImage getImageById(int id){
        return dao.get(id);
    }

    public void addGalleryPhoto(GalleryImage image){
        dao.save(image);
    }
    public void addComment(GalleryImage image, GalleryComments comment){
        comment.setImage(image);
        //image.addComment(comment);
        dao.save(comment);
    }
}
