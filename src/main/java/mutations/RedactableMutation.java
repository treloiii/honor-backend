package mutations;


import Entities.Post;
import dao.PostDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RedactableMutation {
    @Autowired
    private PostDAO postDAO;

    public RedactableMutation() {
    }

    public Post addPost(){
       // postDAO.save();
        return null;
    }
}
