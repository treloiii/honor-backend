package com.honor.back.honorwebapp;
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
public class Controller{
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

    @RequestMapping("/deleteNews")
    public String deleteNews(@RequestParam("id") int id) throws SQLException{
        try {
            ResultSet rs = this.query.getResultSet("SELECT title FROM honor_news WHERE id=" + id);
            String title = rs.getString("title");
            FileUtils.deleteDirectory(new File("/home/std/honor-backend/static/news/" + title + "/"));
            this.query.VoidQuery("DELETE FROM honor_news WHERE id=" + id);
            return "success";
        }
        catch (Exception e){
            e.printStackTrace();
            return e+"error";
        }
    }

    @RequestMapping("/deletePost")
    public String deletePosts(@RequestParam("id") int id) throws SQLException{
        this.query.VoidQuery("DELETE FROM honor_main_posts WHERE id="+id);
        return "success";
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
        albumService.addAlbum(album);
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
        String serverPath = "/home/std/honor-backend/static/gallery/" + album.getId()+"/";
        int index=0;
        for (GalleryImage image:imagesList) {
            image.setName(utils.transliterate(image.getName()));
            String fileUploadResult=utils.fileUpload(serverPath,image.getName(),files[index]);
            if(!fileUploadResult.equals("file exists")&&!fileUploadResult.equals("file empty")){
                image.setServer_path(serverPath);
                image.setUrl("http://honor-webapp-server.std-763.ist.mospolytech.ru/static/gallery/" + album.getId() + "/" + image.getName() + ".jpg");
                image.setAlbum(album);
                galleryService.addGalleryPhoto(image);
            }
            else{
                response+=fileUploadResult;
            }
            index++;
        }
        if(response.equals(""))
            response+="success";
        return response;
    }

    @RequestMapping("/addComment/{photo_id}")
    public String addComment(@RequestBody GalleryComments comment, @PathVariable int photo_id){
        GalleryImage image=galleryService.getImageById(photo_id);
        comment.setTime(new Date());
        galleryService.addComment(image,comment);
        return "Success";
    }


    @RequestMapping(value="/uploadStory", method= RequestMethod.POST)
    public @ResponseBody String handleFileUpload(@RequestParam("post") String posted,
                                                 @RequestParam("file") MultipartFile file){
        String serverPath = "/home/std/honor-backend/static/stories/";
        Gson gson =new Gson();
        Post post=gson.fromJson(posted,Post.class);
        String fileUploadResult=utils.fileUpload(serverPath,post.getTitle(),file);
        if(!fileUploadResult.equals("file exists")&&!fileUploadResult.equals("file empty")){
            post.setImage(fileUploadResult);
            postService.savePost(post);
            return "success";
        }
        else{
            return fileUploadResult;
        }
    }


    @RequestMapping(value = "/uploadNews/{updatable}",method = RequestMethod.POST)
    public String uploadNews(@RequestParam("pic") MultipartFile[] images,@RequestParam(value = "title_pic",required = false) MultipartFile titleImage,
                             @RequestParam("title") String title,@RequestParam("description") String description,
                             @RequestParam("picname") String titleImageName,@RequestParam(value = "news_id",required = false) Integer id,
                             @RequestParam(value = "time",required = false)
                                 @DateTimeFormat(pattern = "yyyy-mm-dd") Date time,
                             @PathVariable("updatable") String updatable){
        News news;
        try {
            news = newsService.getNewsById(id);
        }
        catch (Exception e){
            news = new News();
        }
        if(!updatable.equals("new")) {
            try {
                FileUtils.deleteDirectory(new File("/home/std/honor-backend/static/news/" + utils.transliterate(news.getTitle()) + "/"));
            }
            catch (Exception e){
                System.out.println("cannot find dir");
            }
        }

            titleImageName = utils.transliterate(titleImageName);
            String uploadPath = "/home/std/honor-backend/static/news/" + utils.transliterate(title) + "/";
            new File(uploadPath.substring(0, uploadPath.length() - 1)).mkdirs();
            String[] buf = description.split("_paste_");
            String finalStr = "";
            System.out.println(Arrays.toString(buf));
            int i = 0;
            for (String a : buf) {
                finalStr += a;
                if (i < images.length) {
                    String img = utils.fileUpload(uploadPath, images[i].getOriginalFilename(), images[i]);
                    if (!img.equals("file exists") && !img.equals("file empty")) {
                        finalStr += "<img src=\"" + img + "\">";
                    }
                }
                i++;
            }
            String titleRes="";
            if(titleImage!=null) {
                titleRes = utils.fileUpload(uploadPath, titleImageName, titleImage);
            }
            if (!titleRes.equals("file exists") && !titleRes.equals("file empty")) {
                if(!titleRes.equals("")) {
                    news.setTitle_image_name(titleImageName);
                    news.setTitle_image(titleRes);
                }
                news.setTitle(title);
                news.setAuthor("Admin");
                news.setDescription(finalStr);
                if(updatable.equals("new")) {
                    news.setTime(new Date());
                    newsService.addNews(news);
                }
                else{
                    news.setTime(time);
                    newsService.updateNews(news);
                }
            }
            System.out.println(finalStr);
            return "success";
    }
}
