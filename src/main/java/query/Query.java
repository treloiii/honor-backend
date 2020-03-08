package query;

import Entities.Actions;
import Entities.GalleryImage;
import Entities.News;
import Entities.Post;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import services.*;
import utils.GridObject;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;
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
        List<GridObject> grid=new ArrayList<>();
        Actions rally=actionsService.getLast(1);
        String coords=rally.getCoords();
        if(coords==null)
            grid.add(new GridObject(rally.getTitle_image(),rally.getTitle(),rally.getId(),"/rally","Автопробеги","null"));
        else
            grid.add(new GridObject(rally.getTitle_image(),rally.getTitle(),rally.getId(),"/rally","Автопробеги",coords));
        News news=newsService.getLast();
        coords=news.getCoords();
        if(coords==null)
            grid.add(new GridObject(news.getTitle_image(),news.getTitle(),news.getId(),"/news","Новости","null"));
        else
            grid.add(new GridObject(news.getTitle_image(),news.getTitle(),news.getId(),"/news","Новости",coords));
        Post post=postService.getLast();
        coords=news.getCoords();
        if(coords==null)
            grid.add(new GridObject(post.getTitle_image(),post.getTitle(),post.getId(),"/memories","Воспоминания","null"));
        else
            grid.add(new GridObject(post.getTitle_image(),post.getTitle(),post.getId(),"/memories","Воспоминания",coords));
        Actions event=actionsService.getLast(2);
        coords=event.getCoords();
        if(coords==null)
            grid.add(new GridObject(event.getTitle_image(),event.getTitle(),event.getId(),"/events","Мероприятия","null"));
        else
            grid.add(new GridObject(event.getTitle_image(),event.getTitle(),event.getId(),"/events","Мероприятия",coords));
        GalleryImage image=galleryService.getLast();
        grid.add(new GridObject(image.getUrl(),image.getAlbum().getName(),image.getId(),"/gallery","Галерея","null"));
        return grid;
    }
}
