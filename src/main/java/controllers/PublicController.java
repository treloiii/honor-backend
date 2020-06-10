package controllers;

import Entities.*;
import Entities.Comments;
import Entities.deprecated.*;
import services.deprecated.ActionsService;
import services.deprecated.NewsService;
import utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import services.*;
import sql.ResultedQuery;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    private OrdensService ordensService;
    @Autowired
    private ResultedQuery query;

    @Autowired
    private MailService mailService;

    @RequestMapping("/test")
    public String testBug() {
        return utils.BACKEND_URL;
    }

    @RequestMapping("/subscribe")
    public void subscribe(@RequestParam("email") String email, @RequestParam("name") String name) {
        mailService.subscribe(email, name);
    }

    @RequestMapping("/unsubscribe")
    public void subscribe(@RequestParam("id") int id) {
        mailService.unsubscribe(id);
    }

    @RequestMapping("/get/count/{type}")
    public PaginationCountSize getCountEntity(@PathVariable("type") String type) {
        return new PaginationCountSize(postService.getCount(type), utils.RESULT_PER_PAGE, "none");
    }

    @RequestMapping("/get/page/size")
    public Integer getPagePerSize() {
        return utils.RESULT_PER_PAGE;
    }

    @RequestMapping("/get/all/posts/{page}")
    public List<Post> getMain(@RequestParam(value = "count", required = false) Integer count, @PathVariable(required = false) int page) {
        return postService.getAllPosts(page, count,"memo");
    }

    @RequestMapping("/get/post")
    public Post getPost(@RequestParam("id") int id) {
        return postService.getPostById(id);
    }

    @RequestMapping("/get/gallery")
    public List<GalleryImage> getImages() {
        // galleryDao=new GalleryImageDAO();
        return galleryService.getAllGallery();
    }

    @RequestMapping("/get/image")
    public GalleryImage getImage(@RequestParam("id") int id) {
        return galleryService.getImageById(id);
    }

    @RequestMapping("/get/all/albums/{page}")
    public List<GalleryAlbum> getAlbums(@PathVariable(required = false) int page) {
        return albumService.getAllAlbums(page, null);
    }

    @RequestMapping("/get/album")
    public GalleryAlbum getAlbumById(@RequestParam("id") int id) {
        return albumService.getAlbum(id);
    }


    @RequestMapping("/get/all/news/{page}")
    public List<Post> getAllNews(@RequestParam(value = "count", required = false) Integer count, @PathVariable(required = false) int page) {
        return postService.getAllPosts(page, count,"news");
    }

    @RequestMapping("/get/news")
    public Post getNews(@RequestParam("id") int id) {
        return postService.getPostById(id);
    }

    @RequestMapping("/get/last/news")
    public Post getLastNews() {
        return postService.getLast("news");
    }

    @RequestMapping("/get/last/post")
    public Post getLastPost() {
        return postService.getLast("post");
    }

    @RequestMapping("/get/last/rally")
    public Post getLastRally() {
        return postService.getLast("rally");
    }

    @RequestMapping("/get/last/event")
    public Post getLastEvent() {
        return postService.getLast("post");
    }

    @RequestMapping("/get/last/photos")
    public List<GalleryImage> getLastPhotos() {
        return galleryService.getLastFive();
    }

    @RequestMapping("/get/last/all")
    public List<GridObject> getLasts() {
        List<GridObject> grid =Stream.of("memo","news","rally","events")
                .map(s->{
                    Post post=postService.getLast(s);
                    String type=mapType(s);
                    s=s.equals("memo")?"/memories":"/"+s;
                    return new GridObject(post.getTitle_image(),post.getTitle(),post.getId(),s,type,post.getTitle_image_mini());
                })
                .collect(Collectors.toList());
        GalleryImage image = galleryService.getLast();
        grid.add(new GridObject(image.getUrl(), image.getAlbum().getName(), image.getId(), "/gallery", "Галерея", "not set"));
        return grid;
    }

    private String mapType(String s) {
        switch (s){
            case "rally":
                return "Автопробеги";
            case "events":
                return "Мероприятия";
            case "news":
                return "Новости";
            case "memo":
                return "Воспоминания";
            default:
                throw new IllegalArgumentException("cant map "+s);
        }
    }


    @RequestMapping("/get/actions/{action}/{page}")
    public List<Post> getRallies(@PathVariable String action, @PathVariable(required = false) int page) {
        if (action.equals("rallies"))
            return postService.getAllPosts(page, 0, "rally");
        else if (action.equals("events"))
            return postService.getAllPosts(page, 0, action);
        else
            return null;
    }

    @RequestMapping("/get/action")
    public Post getRally(@RequestParam("id") int id) {
        return postService.getPostById(id);
    }

    @RequestMapping("/get/all/ordens")
    public List<Ordens> getOrdens() {
        return ordensService.getAllOrdens();
    }

    @RequestMapping("/get/orden")
    public Ordens getOrden(@RequestParam("id") int id) {
        return ordensService.getOrden(id);
    }

    @RequestMapping("/add/comment/image/{id}")
    public String addComment(@RequestBody GalleryComments comment, @PathVariable int id) {
        GalleryImage image = galleryService.getImageById(id);
        galleryService.addComment(image, comment);
        return "Success";
    }

    @RequestMapping("/add/comment/{type}/{id}")
    public String addComment(@PathVariable int id,@PathVariable String type, @RequestBody Comments comment) {
        Post news = postService.getPostById(id);
        postService.addComment(news, comment);
        return "success";
    }




    @Deprecated
    @RequestMapping("/getTreeDir")
    public FolderFile getDir() {
        return utils.getAllFiles(new File("/home/ensler/honor-server/static"));
    }


    @RequestMapping("/databaseDump")
    public String createDbDump(String password) {
        byte[] decoded = Base64.getDecoder().decode(password);
        Runtime rt = Runtime.getRuntime();
        try {
            String decode = new String(decoded, StandardCharsets.UTF_8);
            System.out.println(decode);
//            String command="mysqldump -u trelloiii -p"+ decode+"  honor > ~/honor-server/static/dump.sql";
            String command = utils.BASE_SERVER_PATH + "./dump.sh";
            System.out.println(command);
            Process pr = rt.exec(command);

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(pr.getErrorStream()));
            String s = null;
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
            int processCode = pr.waitFor();
            if (processCode == 0) {
                return "dump complete, see it in root of application";
            } else {
                return "error, process code: " + processCode;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

}
