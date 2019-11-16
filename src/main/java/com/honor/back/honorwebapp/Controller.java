package com.honor.back.honorwebapp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import services.AlbumService;
import services.GalleryService;
import services.NewsService;
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
    private GalleryService galleryService;

    @Autowired
    private PostService postService;

    @Autowired
    private AlbumService albumService;

    @Autowired
    private NewsService newsService;

    @RequestMapping("/test")
    public String getTest(){
        return "Hello world!";
    }
    @RequestMapping("/getMain")
    public List<Post> getMain() throws SQLException {
        return postService.getAllPosts();
    }
    @RequestMapping("/getPost")
    public Post getPost(@RequestParam("id") int id){
        return postService.getPostById(id);
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
    @RequestMapping("/getImage")
    public GalleryImage getImage(@RequestParam("id") int id){
        return galleryService.getImageById(id);
    }

    @RequestMapping("/addComment/{photo_id}")
    public String addComment(@RequestBody GalleryComments comment,@PathVariable int photo_id){
        GalleryImage image=galleryService.getImageById(photo_id);
        galleryService.addComment(image,comment);
        return "Success";
    }

    @RequestMapping("/getAlbums")
    public List<GalleryAlbum> getAlbums(){
        return albumService.getAllAlbums();
    }

    @RequestMapping("/getAlbum")
    public GalleryAlbum getAlbumById(@RequestParam("id") int id){
        return albumService.getAlbum(id);
    }


    @RequestMapping("/getAllNews")
    public List<News> getAllNews(){
        return newsService.getAllnews();
    }
    @RequestMapping("getNews")
    public News getNews(@RequestParam("id") int id){
        return newsService.getNewsById(id);
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
