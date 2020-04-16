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
        Post redactable= dao.get(id);
        List<? extends Comments> comments=redactable.getComments();
        comments=comments.stream().filter(Comments::isActive).collect(Collectors.toList());
        redactable.setComments(comments);
        return redactable;
    }
    public List<Redactable> getAllPosts(int page, Integer count){
        List<Redactable> res;
        if(count!=null&&!count.equals(0)){
            res=dao.getAll(0, count);
        }else {
            res=dao.getAll((page - 1) * utils.RESULT_PER_PAGE, utils.RESULT_PER_PAGE);
        }
        return res.stream().map(redactable -> {
            List<? extends Comments> comments=redactable.getComments();
            comments=comments.stream().filter(Comments::isActive).collect(Collectors.toList());
            redactable.setComments(comments);
            return redactable;
        }).collect(Collectors.toList());
    }
    public void addComment(Post post, Comments comment){
        comment.setTime(new Date());
        comment.setRedactable(post);
        dao.save(comment);
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
