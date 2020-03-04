package services;

import Entities.GalleryComments;
import Entities.GalleryImage;
import utils.Utils;
import dao.GalleryImageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

@Component("galleryService")
public class GalleryService {
    @Autowired
    GalleryImageDAO dao;
    @Autowired
    Utils utils;
    public GalleryService() {
    }

    public List<GalleryImage> getAllGallery(){
        return dao.getAll(0,0);
    }

    public GalleryImage getImageById(int id){
        return dao.get(id);
    }

    public void addGalleryPhoto(GalleryImage image){
        dao.save(image);
    }

    public boolean deletePhoto(int id){
        GalleryImage image=this.getImageById(id);
        try{
            File file=new File(image.getServer_path()+image.getName()+".jpg");
      //      boolean result= file.delete();
            Files.delete(file.toPath());
            dao.delete(this.getImageById(id));
            return !file.exists();
//            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public void addComment(GalleryImage image, GalleryComments comment){
        comment.setImage(image);
        //image.addComment(comment);
        dao.save(comment);
    }
    public List<GalleryImage> getLastFive(){
        List<GalleryImage> transList=dao.getLastFive();

        for (GalleryImage image:transList){
            image.setName(utils.reverseTransliterate(image.getName()));
        }
        return transList;
    }
    public GalleryImage getLast(){
        //        image.setName(utils.reverseTransliterate(image.getName()));
        return dao.getLast();
    }
    public void clearCache(){
        dao.clearCache();
    }
}
