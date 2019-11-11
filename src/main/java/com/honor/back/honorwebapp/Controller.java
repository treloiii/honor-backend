package com.honor.back.honorwebapp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import services.GalleryService;
import services.PostService;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.List;

@CrossOrigin
@RestController
public class Controller{
    @Autowired
    GalleryService galleryService;
    @Autowired
    PostService postService;

    @RequestMapping("/getMain")
    public List<Post> getMain() throws SQLException {
//        ResultSet rs=rsq.getResultSet("SELECT * FROM honor_main_posts");
//        while (rs.next()){
//            for(int i=1;i<rs.getMetaData().getColumnCount()+1;i++)
//                System.out.println(rs.getString(i));
//        }
        return postService.getAllPosts();
    }
    @RequestMapping("/newPost")
    public void addPost(@RequestBody Post post){
        postService.savePost(post);
    }
    @RequestMapping("/getGallery")
    public List<GalleryImage> getImages(){
       // galleryDao=new GalleryImageDAO();
        return galleryService.getAllGallery();
    }


    @RequestMapping("/addComment/{photo_id}")
    public String addComment(@RequestBody GalleryComments comment,@PathVariable int photo_id){
        GalleryImage image=galleryService.getImageById(photo_id);
        galleryService.addComment(image,comment);
        return "Success";
    }



    @RequestMapping(value="/upload/{content}", method= RequestMethod.POST)
    public @ResponseBody String handleFileUpload(@RequestParam("name") String name,
                                                 @RequestParam("file") MultipartFile file,
                                                 @RequestParam("title") String title,
                                                 @RequestParam("description") String desc,
                                                 @PathVariable String content){
        if (!file.isEmpty()&&(file.getContentType().contains("jpeg")||file.getContentType().contains("png"))) {
            try {
                    if(content.equals("gallery")) {
                        String serverPath = "/home/std/honor-backend/static/gallery/";
                        //String serverPath="C://cso//";
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
                        gi.setUrl("http://honor-webapp-server.std-763.ist.mospolytech.ru/static/gallery/" + name + "." + file.getContentType().substring("image/".length()));
                        galleryService.addGalleryPhoto(gi);
                        return "Вы удачно загрузили " + name + " в " + name + "Галерею!";
                    }
                    else{
                        String serverPath = "/home/std/honor-backend/static/stories/";
                        //String serverPath = "C://cso//";
                        byte[] bytes = file.getBytes();
                        System.out.println(file.getContentType());
                        BufferedOutputStream stream =
                                new BufferedOutputStream(new FileOutputStream(new File(serverPath + name + "." + file.getContentType().substring("image/".length()))));
                        stream.write(bytes);
                        stream.close();
                        Post post = new Post();
                        post.setTitle(title);
                        post.setImage("http://honor-webapp-server.std-763.ist.mospolytech.ru/static/stories/" + name + "." + file.getContentType().substring("image/".length()));
                        post.setDescription(desc);
                        postService.savePost(post);
                        return "Вы удачно загрузили " + name + " в " + name + "Историю!";
                    }
            } catch (Exception e) {
                return "Вам не удалось загрузить " + name + " => " + e.getMessage();
            }
        } else {
            return "Вам не удалось загрузить " + name + " потому что файл пустой.";
        }
    }
}
