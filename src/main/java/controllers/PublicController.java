package controllers;
import Entities.*;
import Utils.Utils;
import com.google.gson.Gson;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import services.*;
import sql.ResultedQuery;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

@CrossOrigin
@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private Utils utils;
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
    @Autowired
    private ResultedQuery query;



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

    @RequestMapping("/addComment/{photo_id}")
    public String addComment(@RequestBody GalleryComments comment, @PathVariable int photo_id){
        GalleryImage image=galleryService.getImageById(photo_id);
        comment.setTime(new Date());
        galleryService.addComment(image,comment);
        return "Success";
    }
}
