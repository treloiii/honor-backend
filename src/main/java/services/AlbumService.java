package services;

import com.honor.back.honorwebapp.GalleryAlbum;
import dao.AlbumDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("albumService")
public class AlbumService {
    @Autowired
    AlbumDAO dao;

    public AlbumService() {
    }

    public List<GalleryAlbum> getAllAlbums(){
        return dao.getAll();
    }
}
