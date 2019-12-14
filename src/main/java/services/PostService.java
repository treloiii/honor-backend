package services;

import Entities.Post;
import Utils.Utils;
import dao.PostDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("postService")
public class PostService {
    private final int RESULT_PER_PAGE= Utils.RESULT_PER_PAGE;
    @Autowired
    private PostDAO dao;

    public PostService() {
    }

    public void updatePost(Post post){
        dao.update(post);
    }
    public Post getPostById(int id){
        return dao.get(id);
    }
    public List<Post> getAllPosts(int page){
        return dao.getAll((page-1)*RESULT_PER_PAGE,page*RESULT_PER_PAGE);
    }
    public void savePost(Post post){
        dao.save(post);
    }
    public Post getLast(){
        return dao.getLast();
    }
}
