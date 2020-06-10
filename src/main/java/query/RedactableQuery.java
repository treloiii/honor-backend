package query;

import Entities.Post;
import Entities.deprecated.Redactable;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import services.deprecated.ActionsService;
import services.AlbumService;
import services.deprecated.NewsService;
import services.PostService;
import utils.PaginationCountSize;
import utils.Utils;

import java.util.List;

@Component
public class RedactableQuery implements GraphQLQueryResolver {
    @Autowired
    private Utils utils;
    @Autowired
    private PostService postService;
    @Autowired
    private NewsService newsService;
    @Autowired
    private ActionsService actionsService;
    @Autowired
    private AlbumService albumService;

    public RedactableQuery() {
    }

    public String mapType(int type) {
        switch (type) {
            case 1:
                return "rally";
            case 2:
                return "events";
            case 3:
                return "memo";
            case 4:
                return "news";
            default:
                throw new IllegalArgumentException("bad type");
        }
    }

    public List<Post> getAll(int page, int count, int type) {//type:[1-Rally,2-Events,3...-Post,4-News
        String mType = mapType(type);
        return postService.getAllPosts(page, count, mType);
    }

    public PaginationCountSize getCount(int type) {//type:5 - albums
        return new PaginationCountSize(postService.getCount(mapType(type)), utils.RESULT_PER_PAGE, "none");
    }

    public Post getLast(int type) {
        return postService.getLast(mapType(type));
    }

    public Post getById(int id, int type) {
        return postService.getPostById(id);
    }
}
