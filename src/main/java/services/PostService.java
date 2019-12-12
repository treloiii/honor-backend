package services;

import Entities.Post;
import dao.PostDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("postService")
public class PostService {
    @Autowired
    private PostDAO dao;

    public PostService() {
    }

    public Post getPostById(int id){
        return dao.get(id);
    }
    public List<Post> getAllPosts(){
        return dao.getAll(0);
    }
    public void savePost(Post post){
        dao.save(post);
    }
    public Post getLast(){
        return dao.getLast();
    }
}
