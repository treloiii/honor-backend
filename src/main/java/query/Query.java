package query;

import Entities.deprecated.Actions;
import Entities.GalleryImage;
import Entities.deprecated.News;
import Entities.Post;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import services.*;
import services.deprecated.ActionsService;
import services.deprecated.NewsService;
import utils.GridObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class Query implements GraphQLQueryResolver {
    @Autowired
    private PostService postService;
    @Autowired
    private NewsService newsService;
    @Autowired
    private ActionsService actionsService;
    @Autowired
    private AlbumService albumService;
    @Autowired
    private GalleryService galleryService;
    public Query() {
    }

    public List<GridObject> getGrid(){
        List<GridObject> grid = Stream.of("memo","news","rally","events")
                .map(s->{
                    Post post=postService.getLast(s);
                    String type=mapType(s);
                    s=s.equals("memo")?"/memories":"/"+s;
                    return new GridObject(post.getTitle_image(),post.getTitle(),post.getId(),s,type,post.getTitle_image_mini());
                })
                .collect(Collectors.toList());
        GalleryImage image = galleryService.getLast();
        grid.add(new GridObject(image.getUrl(), image.getAlbum().getName(), image.getId(), "/gallery", "Галерея", "not set"));
        return grid;
    }
    private String mapType(String s) {
        switch (s){
            case "rally":
                return "Автопробеги";
            case "events":
                return "Мероприятия";
            case "news":
                return "Новости";
            case "memo":
                return "Воспоминания";
            default:
                throw new IllegalArgumentException("cant map "+s);
        }
    }
}
