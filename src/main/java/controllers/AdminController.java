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

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {
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
    @RequestMapping("/add/post")
    public void addPost(@RequestBody Post post){
        postService.savePost(post);
    }

    @RequestMapping("/delete/{type}")
    public String deleteNews(@RequestBody int id,@PathVariable("type") String type) throws SQLException {//type={news,memo,events}
        if(type.equals("news")||type.equals("memo")||type.equals("events")) {
            try {
                ResultSet rs = null;
                switch (type) {
                    case "news":
                        rs = this.query.getResultSet("SELECT title FROM honor_news WHERE id=" + id);
                        break;
                    case "memo":
                        rs = this.query.getResultSet("SELECT title FROM honor_main_posts WHERE id=" + id);
                        break;
                    case "events":
                        rs = this.query.getResultSet("SELECT title FROM honor_actions WHERE id=" + id);
                        break;
                }
                //rs = this.query.getResultSet("SELECT title FROM honor_news WHERE id=" + id);
                assert rs != null;
                rs.next();
                String title = rs.getString("title");
                FileUtils.deleteDirectory(new File("/home/std/honor-backend/static/" + type + "/" + title + "/"));
                switch (type) {
                    case "news":
                        this.query.VoidQuery("DELETE FROM honor_news WHERE id=" + id);
                        break;
                    case "memo":
                        this.query.VoidQuery("DELETE FROM honor_main_posts WHERE id=" + id);
                        break;
                    case "events":
                        this.query.VoidQuery("DELETE FROM honor_actions WHERE id=" + id);
                        break;
                }
                return "success";
            } catch (Exception e) {
                e.printStackTrace();
                return e + "error";
            }
        }
        else {
            return "invalid type";
        }
    }


    @RequestMapping("/delete/post")
    public String deletePosts(@RequestBody int id) throws SQLException{
        this.query.VoidQuery("DELETE FROM honor_main_posts WHERE id="+id);
        return "success";
    }

    @RequestMapping("/delete/img")
    public String deleteGalleryImage(@RequestBody int id){
        if(this.galleryService.deletePhoto(id)){
            return "success";
        }
        else{
            return "error";
        }
    }
    @RequestMapping("/update/album")
    public String updateAlbum(@RequestParam("id") int id,@RequestParam("name") String name){
        albumService.updateAlbum(id,name);
        return "success";
    }
    @RequestMapping("/delete/album")
    public String deleteAlbum(@RequestBody int id){
       return albumService.deleteAlbum(id);
    }
    @RequestMapping(value="/upload/story", method= RequestMethod.POST)
    public @ResponseBody String handleFileUpload(@RequestParam("post") String posted,
                                                 @RequestParam("file") MultipartFile file){
        String serverPath = "/home/std/honor-backend/static/stories/";
        Gson gson =new Gson();
        Post post=gson.fromJson(posted,Post.class);
        String fileUploadResult=utils.fileUpload(serverPath,post.getTitle(),file);
        if(!fileUploadResult.equals("file exists")&&!fileUploadResult.equals("file empty")){
            post.setTitle_image(fileUploadResult);
            postService.savePost(post);
            return "success";
        }
        else{
            return fileUploadResult;
        }
    }


    @RequestMapping(value = "/upload/{type}/{updatable}",method = RequestMethod.POST)
    public String uploadNews(@RequestParam("pic") MultipartFile[] images,@RequestParam(value = "title_pic",required = false) MultipartFile titleImage,
                             @RequestParam("title") String title,@RequestParam("description") String description,
                             @RequestParam("picname") String titleImageName,@RequestParam(value = "news_id",required = false) Integer id,
                             @RequestParam(value = "time",required = false)
                             @DateTimeFormat(pattern = "yyyy-mm-dd") Date time,
                             @PathVariable("updatable") String updatable,
                             @PathVariable(value = "type") String type){//type:{news,memo,events}
        if(type.equals("news")||type.equals("memo")||type.equals("events")) {
            System.out.println(type);
            Redactable section = null;
            try {
                switch (type) {
                    case "news":
                        section = newsService.getNewsById(id);
                        break;
                    case "memo":
                        section = postService.getPostById(id);
                        break;
                    case "events":
                        section = actionsService.getRallyById(id);
                        break;
                }
            } catch (Exception e) {
                switch (type) {
                    case "news":
                        section = new News();
                        break;
                    case "memo":
                        section = new Post();
                        break;
                    case "events":
                        section = new Actions();
                        break;
                }

            }
            if (!updatable.equals("new")) {
                try {
                    FileUtils.deleteDirectory(new File("/home/std/honor-backend/static/" + type + "/" + utils.transliterate(section.getTitle()) + "/"));
                } catch (Exception e) {
                    System.out.println("cannot find dir");
                }
            }

            titleImageName = utils.transliterate(titleImageName);
            String uploadPath = "/home/std/honor-backend/static/" + type + "/" + utils.transliterate(title) + "/";
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
            String titleRes = "";
            if (titleImage != null) {
                titleRes = utils.fileUpload(uploadPath, titleImageName, titleImage);
            }
            if (!titleRes.equals("file exists") && !titleRes.equals("file empty")) {
                if (!titleRes.equals("")) {
                    section.setTitle_image_name(titleImageName);
                    section.setTitle_image(titleRes);
                }
                section.setTitle(title);
                section.setAuthor("Admin");
                section.setDescription(finalStr);
                if (updatable.equals("new")) {
                    section.setTime(new Date());
                    if (section instanceof News) {
                        newsService.addNews((News) section);
                    } else if (section instanceof Post) {
                        postService.savePost((Post) section);
                    } else if (section instanceof Actions) {
                        ((Actions) section).setType(actionsService.getType(2));
                        actionsService.saveAction((Actions) section);
                    }
                } else {
                    section.setTime(time);
                    if (section instanceof News) {
                        newsService.updateNews((News) section);
                    } else if (section instanceof Post) {
                        postService.updatePost((Post) section);
                    } else if (section instanceof Actions) {
                        actionsService.updateAction((Actions) section);
                    }
                }
            }
            System.out.println(finalStr);
            return "success";
        }
        else {
            return "invalid type";
        }
    }


    @RequestMapping("/add/album")
    public String addAlbum(@RequestBody GalleryAlbum album){
        albumService.addAlbum(album);
        return "Album with id:"+album.getId();
    }
    @RequestMapping("/add/album/images/{album_id}")
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
}
