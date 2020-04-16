package services;

import Entities.GalleryAlbum;
import Entities.GalleryImage;
import utils.Utils;
import dao.AlbumDAO;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Date;
import java.util.List;

@Component("albumService")
public class AlbumService {
    @Autowired
    AlbumDAO dao;
    @Autowired
    Utils utils;

    public AlbumService() {
    }

    public List<GalleryAlbum> getAllAlbums(int page,Integer count){
        List<GalleryAlbum> albums=null;
        if(count!=null&&!count.equals(0)){
            albums =dao.getAll(0, count);
        }
        else {
            albums =dao.getAll((page - 1) * utils.RESULT_PER_PAGE, utils.RESULT_PER_PAGE);
        }
        for (GalleryAlbum album:albums){
            List<GalleryImage> imgs=album.getImages();
            for (GalleryImage img:imgs) {
                img.setName(utils.reverseTransliterate(img.getName()));
            }
            album.setImages(imgs);
        }
        return albums;
    }
    public GalleryAlbum getAlbum(int id){
        GalleryAlbum album= dao.get(id);
        List<GalleryImage> imgs=album.getImages();
        for (GalleryImage img:imgs) {
            img.setName(utils.reverseTransliterate(img.getName()));
        }
        album.setImages(imgs);
        return album;
    }
    public void addAlbum(GalleryAlbum album){
        album.setCreation_date(new Date());
        dao.save(album);
        new File(Utils.BASE_SERVER_PATH+"static/gallery/"+album.getId()).mkdirs();
    }

    public void updateAlbum(int id,String name){
        GalleryAlbum album=dao.get(id);
        album.setName(name);
        dao.update(album);
    }

    public String deleteAlbum(int id){
        try {
            FileUtils.deleteDirectory(new File(Utils.BASE_SERVER_PATH+"static/gallery/" + id + "/"));
            dao.delete(dao.get(id));
            return "success";
        }
        catch (Exception e){
            return "error "+e;
        }
    }

    public Double getCount(){
        if(dao.getCount()% utils.RESULT_PER_PAGE==0)
            return Math.floor(dao.getCount()/ utils.RESULT_PER_PAGE);
        else
            return Math.floor(dao.getCount()/ utils.RESULT_PER_PAGE)+1;
    }
    public void clearCache(){
        dao.clearCache();
    }
}
