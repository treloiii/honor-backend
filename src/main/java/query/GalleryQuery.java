package query;

import Entities.GalleryAlbum;
import Entities.GalleryImage;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import services.AlbumService;
import services.GalleryService;

import java.util.List;

@Component
public class GalleryQuery implements GraphQLQueryResolver {
    @Autowired
    private AlbumService albumService;
    @Autowired
    private GalleryService galleryService;

    public GalleryQuery(){

    }

    public List<GalleryAlbum> getAllAlbums(int page){
        return albumService.getAllAlbums(page);
    }
    public GalleryAlbum getAlbumById(int id){
        return albumService.getAlbum(id);
    }
    public GalleryImage getLastImage(){
        return galleryService.getLast();
    }
    public List<GalleryImage> getLastFiveImgs(){
        return galleryService.getLastFive();
    }
    public GalleryImage getImageById(int id){
        return galleryService.getImageById(id);
    }
}
