package query;

import Entities.News;
import Entities.Post;
import Entities.Redactable;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import services.ActionsService;
import services.NewsService;
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

    public RedactableQuery() {
    }

    public List<Redactable> getAll(int page, int count,int type){//type:[1-Rally,2-Events,3...-Post,4-News
        if(type==1||type==2)
            return actionsService.getAllRallies(page,count,type);
        else if(type==4)
            return newsService.getAllnews(page,count);
        else
            return postService.getAllPosts(page,count);
    }
    public PaginationCountSize getCount(int type){
        if(type==1||type==2)
            return new PaginationCountSize(actionsService.getCount(type),utils.RESULT_PER_PAGE,"none");
        else if(type==4)
            return new PaginationCountSize(newsService.getCount(),utils.RESULT_PER_PAGE,"none");
        else
            return new PaginationCountSize(postService.getCount(),utils.RESULT_PER_PAGE,"none");
    }
    public Redactable getLast(int type){
        if(type==1||type==2)
            return actionsService.getLast(type);
        else if(type==4)
            return newsService.getLast();
        else
            return postService.getLast();
    }
    public Redactable getById(int id,int type){
        if(type==1||type==2)
            return actionsService.getRallyById(id);
        else if(type==4)
            return newsService.getNewsById(id);
        else
            return postService.getPostById(id);
    }
}
