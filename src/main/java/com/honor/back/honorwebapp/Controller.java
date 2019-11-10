package com.honor.back.honorwebapp;
import dao.GalleryImageDAO;
import dao.PostDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sql.ResultedQuery;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@CrossOrigin
@RestController
public class Controller{
//    @Autowired
//    ResultedQuery rsq;
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
    @RequestMapping("/getGallery")
    public List<GalleryImage> getImages(){
        GalleryImageDAO dao=new GalleryImageDAO();
        return dao.getAll();
    }



    @RequestMapping(value="/upload", method= RequestMethod.POST)
    public @ResponseBody String handleFileUpload(@RequestParam("name") String name,
                                                 @RequestParam("file") MultipartFile file){
        if (!file.isEmpty()&&(file.getContentType().contains("jpeg")||file.getContentType().contains("png"))) {
            try {
                byte[] bytes = file.getBytes();
                System.out.println(file.getContentType());
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File("C://cso//"+name+"."+file.getContentType().substring("image/".length()))));
                stream.write(bytes);
                stream.close();
                return "Вы удачно загрузили " + name + " в " + name + "-uploaded !";
            } catch (Exception e) {
                return "Вам не удалось загрузить " + name + " => " + e.getMessage();
            }
        } else {
            return "Вам не удалось загрузить " + name + " потому что файл пустой.";
        }
    }
}
