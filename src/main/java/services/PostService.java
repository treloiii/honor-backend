package services;

import Entities.Comments;
import Entities.Post;
import Entities.Redactable;
import utils.Utils;
import dao.PostDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

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
        if(count!=null&&!count.equals(0)){
            return dao.getAll(0, count);
        }else {
            return dao.getAll((page - 1) * utils.RESULT_PER_PAGE, utils.RESULT_PER_PAGE);
        }
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
