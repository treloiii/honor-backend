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
    @Autowired
    GalleryImageDAO galleryDao;
    @Autowired
    PostDAO postDao;

    @RequestMapping("/getMain")
    public List<Post> getMain() throws SQLException {
//        ResultSet rs=rsq.getResultSet("SELECT * FROM honor_main_posts");
//        while (rs.next()){
//            for(int i=1;i<rs.getMetaData().getColumnCount()+1;i++)
//                System.out.println(rs.getString(i));
//        }
        return postDao.getAll();
    }
    @RequestMapping("/newPost")
    public void addPost(@RequestBody Post post){
        postDao.save(post);
    }
    @RequestMapping("/getGallery")
    public List<GalleryImage> getImages(){
       // galleryDao=new GalleryImageDAO();
        return galleryDao.getAll();
    }



    @RequestMapping(value="/upload", method= RequestMethod.POST)
    public @ResponseBody String handleFileUpload(@RequestParam("name") String name,
                                                 @RequestParam("file") MultipartFile file){
        if (!file.isEmpty()&&(file.getContentType().contains("jpeg")||file.getContentType().contains("png"))) {
            try {
                    String serverPath = "/home/std/honor-backend/static/";
                    byte[] bytes = file.getBytes();
                    System.out.println(file.getContentType());
                    BufferedOutputStream stream =
                            new BufferedOutputStream(new FileOutputStream(new File(serverPath + name + "." + file.getContentType().substring("image/".length()))));
                    stream.write(bytes);
                    stream.close();
                    GalleryImage gi = new GalleryImage();
                    gi.setName(name);
                    gi.setServer_path(serverPath);
                    gi.setDescription("ss");
                    gi.setUrl("http://honor-webapp-server.std-763.ist.mospolytech.ru/static/" + name + "." + file.getContentType().substring("image/".length()));
                    galleryDao.save(gi);
                    return "Вы удачно загрузили " + name + " в " + name + "Галерею!";
            } catch (Exception e) {
                return "Вам не удалось загрузить " + name + " => " + e.getMessage();
            }
        } else {
            return "Вам не удалось загрузить " + name + " потому что файл пустой.";
        }
    }
}
