package services;

import Entities.Comments;
import Entities.News;
import Entities.Post;
import Entities.Redactable;
import utils.Utils;
import dao.PostDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component("postService")
public class PostService {
    @Autowired
    private PostDAO dao;
    @Autowired
    Utils utils;

    public PostService() {
    }

    public void updatePost(Post post){
        dao.update(post);
    }
    public Post getPostById(int id){
        return dao.get(id);
    }
    public List<Redactable> getAllPosts(int page, Integer count){
        List<Redactable> res;
        if(count!=null&&!count.equals(0)){
            res=dao.getAll(0, count);
        }else {
            res=dao.getAll((page - 1) * utils.RESULT_PER_PAGE, utils.RESULT_PER_PAGE);
        }
        return res;
    }
    public void addComment(Post post, Comments comment){
        comment.setTime(new Date());
        comment.setRedactable(post);
        dao.save(comment);
    }
    public void addComment(News news, Comments comments){
        comments.setRedactable(news);
        comments.setTime(new Date());
        dao.save(comments);
    }
    public void redactComment(int id,boolean active,int newsId){
        Post post=dao.get(newsId);
        post.getComments().forEach(comment->{
            if(comment.getId()==id) {
                comment.setActive(active);
                dao.update(comment);
                dao.clearCache();
            }
        });
    }
    public void deleteComment(int commentId,int newsId){
        Post post=dao.get(newsId);
        Comments comment=post.getComments().stream().filter(c->c.getId()==commentId).collect(Collectors.toList()).get(0);
        if(comment!=null) {
            dao.delete(comment);
            dao.update(post);
            dao.clearCache();
        }
    }
    public void savePost(Post post){
        dao.save(post);
    }
    public Post getLast(){
        return dao.getLast();
    }
    public Double getCount(){
        if(dao.getCount()% utils.RESULT_PER_PAGE==0)
            return Math.floor(dao.getCount()/ utils.RESULT_PER_PAGE);
        else
            return Math.floor(dao.getCount()/ utils.RESULT_PER_PAGE)+1;
    }
    public void delete(Object deletedObject){
        dao.delete(deletedObject);
    }
    public void clearCache(){
        dao.clearCache();
    }
}
