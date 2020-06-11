package services;

import Entities.Comments;
import Entities.Post;
import utils.Utils;
import dao.PostDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component("postService")
public class PostService {
    private final PostDAO dao;
    private final Utils utils;

    public PostService(PostDAO dao, Utils utils) {
        this.dao = dao;
        this.utils = utils;
    }

    public void updatePost(Post post){
        dao.update(post);
    }
    public Post getPostById(int id){
        return dao.get(id);
    }
    public List<Post> getAllPosts(int page, Integer count,String type){
        List<Post> res;
        if(count!=null&&!count.equals(0)){
            res=dao.getAll(0, count,type);
        }else {
            res=dao.getAll((page - 1) * utils.RESULT_PER_PAGE, utils.RESULT_PER_PAGE,type);
        }
        return res;
    }
    public List<Post> getAll(){
        return dao.getAll();
    }
    public void addComment(Post post, Comments comment){
        comment.setTime(new Date());
        comment.setPost(post);
        dao.save(post);
    }
    public void redactComment(int id,boolean active,int postId){
        Post post=dao.get(postId);
        post.getComments().forEach(comment->{
            if(comment.getId()==id) {
                comment.setActive(active);
                dao.updateComment(comment);
                dao.clearCache();
            }
        });
    }
    public void deleteComment(int commentId,int newsId){
        Post post=dao.get(newsId);
        Comments comment=post.getComments().stream().filter(c->c.getId()==commentId).collect(Collectors.toList()).get(0);
        if(comment!=null) {
            dao.deleteComment(comment);
            dao.update(post);
            dao.clearCache();
        }
    }
    public void savePost(Post post){
        dao.save(post);
    }
    public Post getLast(String type){
        return dao.getLast(type);
    }
    public void delete(Post post){
        dao.delete(post);
    }
    public Double getCount(String type){
        if(dao.getCount(type)% utils.RESULT_PER_PAGE==0)
            return Math.floor(dao.getCount(type)/ utils.RESULT_PER_PAGE);
        else
            return Math.floor(dao.getCount(type)/ utils.RESULT_PER_PAGE)+1;
    }

    public void clearCache(){
        dao.clearCache();
    }
}
