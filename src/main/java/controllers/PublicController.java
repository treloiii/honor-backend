package controllers;
import Entities.*;
import utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import services.*;
import sql.ResultedQuery;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.*;

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
    private NewsService newsService;
    @Autowired
    private ActionsService actionsService;
    @Autowired
    private AlbumService albumService;
    @Autowired
    private OrdensService ordensService;
    @Autowired
    private ResultedQuery query;

    @RequestMapping("/test")
    public String testBug(){
        return "Works";
    }
    @RequestMapping("/get/count/{type}")
    public PaginationCountSize getCountEntity(@PathVariable("type") String type){
        switch (type) {
            case "events":
                return new PaginationCountSize(actionsService.getCount(2),utils.RESULT_PER_PAGE,"none");
            case "rally":
                return new PaginationCountSize(actionsService.getCount(1),utils.RESULT_PER_PAGE,"none");
            case "news":
                return new PaginationCountSize(newsService.getCount(),utils.RESULT_PER_PAGE,"none");
            case "memo":
                return new PaginationCountSize(postService.getCount(),utils.RESULT_PER_PAGE,"none");
            default:
                return new PaginationCountSize(-1,-1,"invalid type");
        }
    }
    @RequestMapping("/get/page/size")
    public Integer getPagePerSize(){
        return utils.RESULT_PER_PAGE;
    }

    @RequestMapping("/get/all/posts/{page}")
    public List<Redactable> getMain(@RequestParam(value = "count",required = false) Integer count,@PathVariable(required = false) int page) throws SQLException {
        return postService.getAllPosts(page,count);
    }
    @RequestMapping("/get/post")
    public Post getPost(@RequestParam("id") int id){
        return postService.getPostById(id);
    }

    @RequestMapping("/get/gallery")
    public List<GalleryImage> getImages(){
       // galleryDao=new GalleryImageDAO();
        return galleryService.getAllGallery();
    }
    @RequestMapping("/get/image")
    public GalleryImage getImage(@RequestParam("id") int id){
        return galleryService.getImageById(id);
    }

    @RequestMapping("/get/all/albums/{page}")
    public List<GalleryAlbum> getAlbums(@PathVariable(required = false) int page){
        return albumService.getAllAlbums(page);
    }

    @RequestMapping("/get/album")
    public GalleryAlbum getAlbumById(@RequestParam("id") int id){
        return albumService.getAlbum(id);
    }


    @RequestMapping("/get/all/news/{page}")
    public List<Redactable> getAllNews(@RequestParam(value = "count",required = false) Integer count,@PathVariable(required = false) int page){
        return newsService.getAllnews(page,count);
    }
    @RequestMapping("/get/news")
    public News getNews(@RequestParam("id") int id){
        return newsService.getNewsById(id);
    }

    @RequestMapping("/get/last/news")
    public News getLastNews(){
        return newsService.getLast();
    }
    @RequestMapping("/get/last/post")
    public Post getLastPost(){
        return postService.getLast();
    }
    @RequestMapping("/get/last/rally")
    public Actions getLastRally(){
        return actionsService.getLast(1);
    }
    @RequestMapping("/get/last/event")
    public Actions getLastEvent(){
        return actionsService.getLast(2);
    }
    @RequestMapping("/get/last/photos")
    public List<GalleryImage> getLastPhotos(){
        return galleryService.getLastFive();
    }
    @RequestMapping("/get/last/all")
    public List<GridObject> getLasts(){
        List<GridObject> grid=new ArrayList<>();
        Actions rally=actionsService.getLast(1);
        String coords=rally.getCoords();
        if(coords==null)
            grid.add(new GridObject(rally.getTitle_image(),rally.getTitle(),rally.getId(),"/rally","Автопробеги","null",rally.getTitle_image_mini()));
        else
            grid.add(new GridObject(rally.getTitle_image(),rally.getTitle(),rally.getId(),"/rally","Автопробеги",coords,rally.getTitle_image_mini()));
        News news=newsService.getLast();
        coords=news.getCoords();
        if(coords==null)
            grid.add(new GridObject(news.getTitle_image(),news.getTitle(),news.getId(),"/news","Новости","null",news.getTitle_image_mini()));
        else
            grid.add(new GridObject(news.getTitle_image(),news.getTitle(),news.getId(),"/news","Новости",coords,news.getTitle_image_mini()));
        Post post=postService.getLast();
        coords=post.getCoords();
        if(coords==null)
            grid.add(new GridObject(post.getTitle_image(),post.getTitle(),post.getId(),"/memories","Воспоминания","null",post.getTitle_image_mini()));
        else
            grid.add(new GridObject(post.getTitle_image(),post.getTitle(),post.getId(),"/memories","Воспоминания",coords,post.getTitle_image_mini()));
        Actions event=actionsService.getLast(2);
        coords=event.getCoords();
        if(coords==null)
            grid.add(new GridObject(event.getTitle_image(),event.getTitle(),event.getId(),"/events","Мероприятия","null",event.getTitle_image_mini()));
        else
            grid.add(new GridObject(event.getTitle_image(),event.getTitle(),event.getId(),"/events","Мероприятия",coords,event.getTitle_image_mini()));
        GalleryImage image=galleryService.getLast();
        grid.add(new GridObject(image.getUrl(),image.getAlbum().getName(),image.getId(),"/gallery","Галерея","null","not set"));
        return grid;
    }


    @RequestMapping("/get/actions/{action}/{page}")
    public List<Redactable> getRallies(@PathVariable String action,@PathVariable(required = false) int page){
        if(action.equals("rallies"))
            return actionsService.getAllRallies(page,0,1);
        else if(action.equals("events"))
            return actionsService.getAllRallies(page,0,2);
        else
            return null;
    }

    @RequestMapping("/get/action")
    public Actions getRally(@RequestParam("id") int id){
        return actionsService.getRallyById(id);
    }

    @RequestMapping("/get/all/ordens")
    public List<Ordens> getOrdens(){
        return ordensService.getAllOrdens();
    }

    @RequestMapping("/get/orden")
    public Ordens getOrden(@RequestParam("id")int id){
        return ordensService.getOrden(id);
    }

    @RequestMapping("/add/comment/{photo_id}")
    public String addComment(@RequestBody GalleryComments comment, @PathVariable int photo_id){
        GalleryImage image=galleryService.getImageById(photo_id);
        comment.setTime(new Date());
        comment.setActive(false);
        galleryService.addComment(image,comment);
        return "Success";
    }

    @RequestMapping("/getTreeDir")
    public FolderFile getDir(){
        return utils.getAllFiles(new File("/home/ensler/honor-server/static"));

    }
    @RequestMapping("/getDirContent")
    public Directory getDirContent(@RequestParam("path") String path){
        return utils.getDirContent(new File(path));
    }


    @RequestMapping("/checkUses")
    public Set<String> checkUses(){
        Set<String> unused=new HashSet<>();
        List<File> files=utils.scanRedactables();
        List<Redactable> redactables=utils.getAllRedactables();
        for(File f:files) {
            int i =redactables.size();
            for (Redactable r : redactables) {
                String s=utils.transliterate(r.getTitle());
                System.out.println(s);
                if (s.contains(f.getName())){
                    break;
                }
                i--;
            }
            if(i==0)
                unused.add(f.getName());
        }
        return unused;
    }

}
