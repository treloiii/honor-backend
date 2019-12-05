package services;

import Entities.GalleryAlbum;
import Entities.GalleryImage;
import Utils.Utils;
import dao.AlbumDAO;
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

    public List<GalleryAlbum> getAllAlbums(){
        List<GalleryAlbum> albums=dao.getAll(0);
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
        new File("/home/std/honor-backend/static/gallery/"+album.getId()).mkdir();
    }
}
