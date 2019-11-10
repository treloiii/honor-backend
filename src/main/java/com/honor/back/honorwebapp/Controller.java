package com.honor.back.honorwebapp;
import dao.PostDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sql.ResultedQuery;

import java.sql.SQLException;
import java.util.List;

@CrossOrigin
@RestController
public class Controller{
    @Autowired
    ResultedQuery rsq;
    @RequestMapping("/getMain")
    public List<Post> getMain() throws SQLException {
//        ResultSet rs=rsq.getResultSet("SELECT * FROM honor_main_posts");
//        while (rs.next()){
//            for(int i=1;i<rs.getMetaData().getColumnCount()+1;i++)
//                System.out.println(rs.getString(i));
//        }
        PostDAO dao=new PostDAO();
        return dao.getAll();
    }
    @RequestMapping("/newPost")
    public void addPost(@RequestBody Post post){
        PostDAO dao=new PostDAO();
        dao.save(post);
    }
}
