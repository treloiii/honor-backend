package controllers;

import Entities.*;
import Entities.deprecated.Actions;
import Entities.deprecated.News;
import Entities.deprecated.Redactable;
import org.apache.commons.io.FileUtils;
import services.deprecated.ActionsService;
import services.deprecated.NewsService;
import utils.Directory;
import utils.Utils;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import services.*;
import sql.ResultedQuery;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;
import java.util.*;

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
    private OrdensService ordensService;
    @Autowired
    private ResultedQuery query;

    @RequestMapping("/add/post")
    public void addPost(@RequestBody Post post) {
        postService.savePost(post);
    }

    @RequestMapping("/delete/{type}")
    public String deleteNews(@RequestBody int id, @PathVariable("type") String type) throws SQLException {//type={news,memo,events}
        if (type.equals("news") || type.equals("memo") || type.equals("events") || type.equals("rally")) {
            try {
                Post post = postService.getPostById(id);

                //rs = this.query.getResultSet("SELECT title FROM honor_news WHERE id=" + id);
//                assert rs != null;
//                rs.next();
//                String title = rs.getString("title");
                String title = post.getTitle();
                String delPath = utils.BASE_SERVER_PATH + "static/" + type + "/" + utils.transliterate(title) + "/";
                System.out.println(title);
//                FileUtils.deleteDirectory(new File(delPath));
//                Utils.deleteDirectory(new File(delPath));
                FileUtils.deleteDirectory(new File(delPath));
                postService.delete(post);
                return "success";
            } catch (Exception e) {
                e.printStackTrace();
                return e + "error";
            }
        } else {
            return "invalid type";
        }
    }


    @RequestMapping("/delete/post")
    public String deletePosts(@RequestBody int id) throws SQLException {
        this.query.VoidQuery("DELETE FROM honor_main_posts WHERE id=" + id);
        return "success";
    }

    @RequestMapping("/delete/img")
    public String deleteGalleryImage(@RequestBody int id) {
        if (this.galleryService.deletePhoto(id)) {
            return "success";
        } else {
            return "error";
        }
    }

    @RequestMapping("/update/album")
    public String updateAlbum(@RequestParam("id") int id, @RequestParam("name") String name) {
        albumService.updateAlbum(id, name);
        return "success";
    }

    @RequestMapping("/delete/album")
    public String deleteAlbum(@RequestBody int id) {
        return albumService.deleteAlbum(id);
    }

    @RequestMapping(value = "/upload/story", method = RequestMethod.POST)
    public @ResponseBody
    String handleFileUpload(@RequestParam("post") String posted,
                            @RequestParam("file") MultipartFile file) {
        String serverPath = utils.BASE_SERVER_PATH + "static/memo/";
        Gson gson = new Gson();
        Post post = gson.fromJson(posted, Post.class);
        String fileUploadResult = utils.fileUpload(serverPath, post.getTitle(), file);
        if (!fileUploadResult.equals("file exists") && !fileUploadResult.equals("file empty")) {
            post.setTitle_image(fileUploadResult);
            postService.savePost(post);
            return "success";
        } else {
            return fileUploadResult;
        }
    }

    @RequestMapping("/set/pagination/{count}")
    public Integer setPagination(@PathVariable int count) {
        try {
            return this.utils.setResultPerPage(count);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @RequestMapping("/clear/cache/{type}")
    public String clearCache(@PathVariable("type") String type) {
        System.out.println(type);
        postService.clearCache();
        return "success";
    }


    @RequestMapping(value = "/upload/{type}/{updatable}", method = RequestMethod.POST)
    public String uploadNews(@RequestParam("pic") MultipartFile[] images, @RequestParam(value = "title_pic", required = false) MultipartFile titleImage,
                             @RequestParam(value = "title_pic_mini", required = false) MultipartFile titleImage_mini,
                             @RequestParam("title") String title,
                             @RequestParam("description") String description,
                             @RequestParam("description_short") String descriptionShort,
                             @RequestParam("picname") String titleImageName, @RequestParam(value = "news_id", required = false) Integer id,
                             @RequestParam(value = "time", required = false)
                             @DateTimeFormat(pattern = "yyyy-mm-dd") Date time,
                             @RequestParam("coords") String coords,
                             @PathVariable("updatable") String updatable,
                             @RequestParam(value = "newType", required = false) String newType,
                             @PathVariable(value = "type") String type) {//type:{news,memo,events,rally}
        if (type.equals("news") || type.equals("memo") || type.equals("events") || type.equals("rally")) {
            System.out.println(type);
            Post section;
            try {
                section=postService.getPostById(id);
            } catch (Exception e) {
                section = new Post();
            }
            File currentTitleImage;
            File currentTitleImageMini;
            File tempFile = null;
            File tempFile1 = null;
            if (updatable.equals("update")) {
                System.err.println("UPDATE");
                if (!type.equals(newType)) {
                    if (newType != null)
                        section.setType(newType);
                }
                try {
                    String tempDir = utils.BASE_SERVER_PATH + "static/temp/" + section.getTitle_image_name() + ".jpg";
                    String tempDir1 = utils.BASE_SERVER_PATH + "static/temp/" + section.getTitle_image_name() + "_cropped.jpg";
                    tempFile = new File(tempDir);
                    tempFile1 = new File(tempDir1);
                    currentTitleImage = new File(utils.BASE_SERVER_PATH + "static/" + type + "/" + utils.transliterate(section.getTitle()) + "/" + section.getTitle_image_name() + ".jpg");
                    currentTitleImageMini = new File(utils.BASE_SERVER_PATH + "static/" + type + "/" + utils.transliterate(section.getTitle()) + "/" + section.getTitle_image_name() + "_cropped.jpg");
                    utils.copy(currentTitleImage, tempFile);
                    utils.copy(currentTitleImageMini, tempFile1);
                    System.err.println("DELETING DIR");
                    FileUtils.deleteDirectory(new File(utils.BASE_SERVER_PATH + "static/" + type + "/" + utils.transliterate(section.getTitle()) + "/"));
                } catch (Exception e) {
                    System.out.println("cannot find dir");
                }
            }

            titleImageName = utils.transliterate(titleImageName);
            String uploadPath = utils.BASE_SERVER_PATH + "static/" + type + "/" + utils.transliterate(title) + "/";
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
            System.out.println("title" + titleImage);
            String titleRes = "";
            String titleMini = "";
            if (titleImage != null) {
                titleRes = utils.fileUpload(uploadPath, titleImageName, titleImage);
                titleMini = utils.fileUpload(uploadPath, titleImageName + "_cropped", titleImage_mini);
                if (!titleRes.equals("file exists")) {
                    System.out.println("SET TITLE IMAGE");
                    section.setTitle_image_name(titleImageName);
                    section.setTitle_image(titleRes);
                }
                if (!titleMini.equals("file exists")) {
                    section.setTitle_image_mini(titleMini);
                }
            } else {
                File uploadOld = new File(uploadPath + section.getTitle_image_name() + ".jpg");
                File uploadOld1 = new File(uploadPath + section.getTitle_image_name() + "_cropped.jpg");
                try {
                    utils.copy(tempFile, uploadOld);
                    utils.copy(tempFile1, uploadOld1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                section.setTitle_image(utils.BACKEND_URL+"static/"+type+"/"+utils.transliterate(title)+"/"+section.getTitle_image_name()+".jpg");
                section.setTitle_image_mini(utils.BACKEND_URL+"static/"+type+"/"+utils.transliterate(title)+"/"+section.getTitle_image_name()+"_cropped.jpg");
            }
            // if (!titleRes.equals("file exists") && !titleRes.equals("file empty")) {

            section.setTitle(title);
            section.setAuthor("Admin");
            section.setDescription(finalStr);
            section.setDescription_short(descriptionShort);
            if (updatable.equals("new")) {
                section.setTime(new Date());
                section.setType(type);
                postService.savePost(section);
            } else {
                section.setTime(time);
                postService.updatePost(section);
            }
            //  }
            System.out.println(finalStr);
            return "success";
        } else {
            return "invalid type";
        }
    }


    @RequestMapping("/add/album")
    public String addAlbum(@RequestBody GalleryAlbum album) {
        albumService.addAlbum(album);
        return "Album with id:" + album.getId();
    }

    @RequestMapping("/add/album/images/{album_id}")
    public String addImages(@RequestParam("images") String images, @RequestParam("files") MultipartFile[] files,
                            @PathVariable int album_id) {
        Gson gson = new Gson();
        List<GalleryImage> imagesList = null;
        imagesList = Arrays.asList(gson.fromJson(images, GalleryImage[].class));
        GalleryAlbum album = albumService.getAlbum(album_id);
        String response = "";
        String serverPath = utils.BASE_SERVER_PATH + "static/gallery/" + album.getId() + "/";
        int index = 0;
        for (GalleryImage image : imagesList) {
            image.setName(utils.transliterate(image.getName()));
            String fileUploadResult = utils.fileUpload(serverPath, image.getName(), files[index]);
            if (!fileUploadResult.equals("file exists") && !fileUploadResult.equals("file empty")) {
                image.setServer_path(serverPath);
                image.setUrl(utils.BACKEND_URL + "static/gallery/" + album.getId() + "/" + image.getName() + ".jpg");
                image.setAlbum(album);
                galleryService.addGalleryPhoto(image);
            } else {
                response += fileUploadResult;
            }
            index++;
        }
        if (response.equals(""))
            response += "success";
        return response;
    }

    @RequestMapping("/redact/comment/{type}")
    public String redactComment(@PathVariable(name = "type") String type,
                                @RequestParam(name = "active") boolean active,
                                @RequestParam(name = "id") int id,
                                @RequestParam(name = "red") int redId) {
        postService.redactComment(id, active, redId);
        return "success";
    }

    @RequestMapping("/delete/comment/{type}")
    public String deleteComment(@PathVariable(name = "type") String type,
                                @RequestParam(name = "id") int id,
                                @RequestParam(name = "red") int redId) {

        postService.deleteComment(id, redId);
        return "success";
    }

    @RequestMapping("/getDirContent")
    public Directory getDirContent(@RequestParam("path") String path) {
        return utils.getDirContent(new File(path));
    }

    @RequestMapping("/lastIn")
    public String saveLastInToLog(String address) {
        try {
            File log = new File(utils.BASE_SERVER_PATH + "lastIn.log");
            PrintWriter writer = new PrintWriter(new FileWriter(log, true));
            writer.println("last in by: " + address + " at " + new Date().toString());
            writer.close();
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @RequestMapping("/clearTemp")
    public String clearTemp() {
        File temp = new File(utils.BASE_SERVER_PATH + "static/temp");
        try {
            FileUtils.deleteDirectory(temp);
            temp.mkdirs();
        } catch (IOException e) {
            return e.getMessage();
        }
        return "success";
    }

    @RequestMapping("/removeUnusedFiles")
    public String checkUses() {
        Set<File> unused = new HashSet<>();
        List<File> files = utils.scanRedactables();
        List<File> albumFiles = utils.scanGallery();
        List<GalleryAlbum> albums = albumService.getAllAlbums(0, 1000);
        List<Post> redactables = postService.getAll();
        for (File f : files) {
            int i = redactables.size();
            for (Post r : redactables) {
                String s = utils.transliterate(r.getTitle());
                System.out.println(s);
                if (s.contains(f.getName())) {
                    break;
                }
                i--;
            }
            if (i == 0)
                unused.add(f);
        }
        for (File f : albumFiles) {
            int i = albums.size();
            for (GalleryAlbum album : albums) {
                if (f.getName().equals(String.valueOf(album.getId()))) {
                    break;
                }
                i--;
            }
            if (i == 0)
                unused.add(f);
        }
        for (File deleted : unused) {
            try {
                FileUtils.deleteDirectory(deleted);
            } catch (IOException e) {
                return e.getMessage();
            }
        }
        return "success";
    }

    @RequestMapping("/download/{type}")
    public String downloadZip(@RequestBody String[] files, @PathVariable String type,
                              HttpServletRequest request, HttpServletResponse response) {
        if (type.equals("folder")) {
            try {
                return utils.createZipFromDirs(files);
            } catch (Exception e) {
                return e.getMessage();
            }
        } else if (type.equals("files")) {
            try {
                return utils.createZipFromFiles(files);
            } catch (IOException e) {
                return e.getMessage();
            }
        } else {
            response.setStatus(400);
            return "Wrong type";
        }
    }


}
