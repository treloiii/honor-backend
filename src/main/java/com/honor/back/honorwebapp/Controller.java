package com.honor.back.honorwebapp;
import Entities.*;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import services.*;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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

    @Autowired
    private ActionsService actionsService;

    @Autowired
    private OrdensService ordensService;

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
    public String addComment(@RequestBody GalleryComments comment, @PathVariable int photo_id){
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
    @RequestMapping("/getNews")
    public News getNews(@RequestParam("id") int id){
        return newsService.getNewsById(id);
    }


    @RequestMapping("/getActions/{action}")
    public List<Actions> getRallies(@PathVariable String action){
        if(action.equals("Rallies"))
            return actionsService.getAllRallies(1);
        else if(action.equals("Events"))
            return actionsService.getAllRallies(2);
        else
            return null;
    }

    @RequestMapping("/getAction")
    public Actions getRally(@RequestParam("id") int id){
        return actionsService.getRallyById(id);
    }

    @RequestMapping("/getOrdens")
    public List<Ordens> getOrdens(){
        return ordensService.getAllOrdens();
    }

    @RequestMapping("/getOrden")
    public Ordens getOrden(@RequestParam("id")int id){
        return ordensService.getOrden(id);
    }



    @RequestMapping("/addAlbum")
    public String addAlbum(@RequestBody GalleryAlbum album){
        album.setCreation_date(new Date());
        albumService.addAlbum(album);
        new File("/home/std/honor-backend/static/gallery/"+album.getId()).mkdirs();
        return "Album with id:"+album.getId();
    }
    @RequestMapping("/addAlbumImages/{album_id}")
    public String addImages(@RequestParam("images") String images,@RequestParam("files") MultipartFile[] files,
                                        @PathVariable int album_id){
        Gson gson=new Gson();
        List<GalleryImage> imagesList = null;
        imagesList= Arrays.asList(gson.fromJson(images,GalleryImage[].class));
        GalleryAlbum album=albumService.getAlbum(album_id);
        String response="";
        int index=0;
        for (GalleryImage image:imagesList) {
            try {
                if (!files[index].isEmpty() && (files[index].getContentType().contains("jpeg") || files[index].getContentType().contains("png"))) {
                    String serverPath = "/home/std/honor-backend/static/gallery/" + album.getId()+"/";
                    byte[] bytes = files[index].getBytes();
                    System.out.println(files[index].getContentType());
                    File file=new File(serverPath + image.getName() + "." + files[index].getContentType().substring("image/".length()));
                    if(!file.exists()) {
                        BufferedOutputStream stream =
                                new BufferedOutputStream(new FileOutputStream(file));
                        stream.write(bytes);
                        stream.close();
                        image.setServer_path(serverPath);
                        image.setUrl("http://honor-webapp-server.std-763.ist.mospolytech.ru/static/gallery/" + album.getId() + "/" + image.getName() + "." + files[index].getContentType().substring("image/".length()));
                        image.setAlbum(album);
                        galleryService.addGalleryPhoto(image);
                    }
                    else{
                        response+="Cannot upload file with name "+image.getName()+", because file alread exists;\n";
                    }
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
            index++;
        }
        if(response.equals(""))
            response+="success";
        return response;
    }


    @RequestMapping(value="/uploadStory", method= RequestMethod.POST)
    public @ResponseBody String handleFileUpload(@RequestParam("post") String posted,
                                                 @RequestParam("file") MultipartFile file){
        Gson gson =new Gson();
        Post post=gson.fromJson(posted,Post.class);
        if (!file.isEmpty()&&(file.getContentType().contains("jpeg")||file.getContentType().contains("png"))) {
            try {
                String serverPath = "/home/std/honor-backend/static/stories/";
                byte[] bytes = file.getBytes();
                System.out.println(file.getContentType());
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File(serverPath + post.getTitle() + "." + file.getContentType().substring("image/".length()))));
                stream.write(bytes);
                stream.close();
                post.setImage("http://honor-webapp-server.std-763.ist.mospolytech.ru/static/stories/" + post.getTitle() + "." + file.getContentType().substring("image/".length()));
                postService.savePost(post);
                return "Вы удачно загрузили " + post.getTitle();

            } catch (Exception e) {
                return "Вам не удалось загрузить " + post.getTitle() + " => " + e.getMessage();
            }
        } else {
            return "Вам не удалось загрузить " + post.getTitle() + " потому что файл пустой.";
        }
    }
}
