package services;

import Entities.GalleryAlbum;
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

    public AlbumService() {
    }

    public List<GalleryAlbum> getAllAlbums(){
        return dao.getAll(0);
    }
    public GalleryAlbum getAlbum(int id){return dao.get(id);}
    public void addAlbum(GalleryAlbum album){
        album.setCreation_date(new Date());
        new File("/home/std/honor-backend/static/gallery/"+album.getId()).mkdirs();
        dao.save(album);
    }
}
