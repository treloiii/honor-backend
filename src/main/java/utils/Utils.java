package utils;

import Entities.Redactable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import services.ActionsService;
import services.NewsService;
import services.PostService;
import sql.ResultedQuery;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


@Component("utils")
public class Utils {
    public int RESULT_PER_PAGE;
    @Autowired
    private ResultedQuery rq;
    @Autowired
    private PostService postService;
    @Autowired
    private NewsService newsService;
    @Autowired
    private ActionsService actionsService;

    public Integer setResultPerPage(int count) throws SQLException {
        rq.VoidQuery("UPDATE honor_pagination_settings set count="+count+" where id=1");
        int result=reloadResultPerPage();
        if(result!=-1) {
            RESULT_PER_PAGE=result;
        }
        return result;
    }
    private Integer reloadResultPerPage(){
       // rq=new ResultedQuery();
        System.out.println("reload pagination");
        try {
            ResultSet rs = rq.getResultSet("SELECT * FROM honor_pagination_settings where id=1");
            rs.next();
            return rs.getInt("count");
        }
        catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }
    public void initResultPerPage(){
        int load=reloadResultPerPage();
        if(load!=-1)
            RESULT_PER_PAGE=load;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        initResultPerPage();
    }

    public Utils() {
        //reloadResultPerPage();
    }

    public static boolean deleteDirectory(File dir) {
        if (dir.isDirectory()) {
            File[] children = dir.listFiles();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDirectory(children[i]);
                if (!success) {
                    return false;
                }
            }
        }

        System.out.println("removing file or directory : " + dir.getName());
        return dir.delete();
    }

    public static void copy(File src, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(src);
            os = new FileOutputStream(dest);

            // buffer size 1K
            byte[] buf = new byte[1024];

            int bytesRead;
            while ((bytesRead = is.read(buf)) > 0) {
                os.write(buf, 0, bytesRead);
            }
        } finally {
            is.close();
            os.close();
        }
    }

    public String fileUpload(String serverPath,String fileName, MultipartFile file){
        String res;
        if (!file.isEmpty()&&(file.getContentType().contains("jpeg")||file.getContentType().contains("png"))) {
            try {
                String contentType="";
                if(file.getContentType().contains("jpeg"))
                    contentType="jpg";
                else
                    contentType="png";
                double fileSizeKb=file.getSize()/1024;
                File file1;
                String postfix = "_uncompressed";
                if(contentType.equals("jpg")) {
                    if (fileSizeKb > 350) {
                        file1 = new File(serverPath + fileName + postfix + "." + contentType);
                    }
                    else{
                        file1 = new File(serverPath + fileName + "." + contentType);
                    }
                }
                else {
                    file1 = new File(serverPath + fileName + "." + contentType);
                }

                if(!file1.exists()) {
                    byte[] bytes = file.getBytes();
                    System.out.println(file.getContentType());
                    BufferedOutputStream stream =
                            new BufferedOutputStream(new FileOutputStream(file1));
                    stream.write(bytes);
                    stream.close();
                    res="http://database.ensler.ru/"+serverPath.substring("/home/ensler/honor-server/".length())+fileName + ".jpg";
                    if(contentType.equals("jpg")) {
                        if(fileSizeKb>350)
                            compressJPEG(serverPath + fileName + postfix + "." + contentType, postfix);
                    }
                    else {
                        compressPNG(serverPath + fileName + "." + contentType, file1);
                    }
                }
                else {
                    res="file exists";
                }
            } catch (Exception e) {
                res=e.getMessage();
                e.printStackTrace();
            }
        } else {
            res="file empty";
        }
        return res;
    }


    private void compressPNG(String pathToPng,File fileToDelete){
        toJpeg(pathToPng,fileToDelete);
        //compressJPEG(pathToPng.substring(0,pathToPng.length()-".png".length())+".jpg");
    }

    private void compressJPEG(String file_path,String postfix){
        try {
            File imageFile = new File(file_path);
            File compressedImageFile = new File(file_path.substring(0,file_path.length()-4-postfix.length())+".jpg");

            InputStream is = new FileInputStream(imageFile);
            OutputStream os = new FileOutputStream(compressedImageFile);

            float quality = 0.4f;

            // create a BufferedImage as the result of decoding the supplied InputStream
            BufferedImage image = ImageIO.read(is);

            // get all image writers for JPG format
            Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");

            if (!writers.hasNext())
                throw new IllegalStateException("No writers found");

            ImageWriter writer = (ImageWriter) writers.next();
            ImageOutputStream ios = ImageIO.createImageOutputStream(os);
            writer.setOutput(ios);

            ImageWriteParam param = writer.getDefaultWriteParam();

            // compress to a given quality
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(quality);

            // appends a complete image stream containing a single image and
            //associated stream and image metadata and thumbnails to the output
            writer.write(null, new IIOImage(image, null, null), param);

            // close all streams
            is.close();
            os.close();
            ios.close();
            writer.dispose();
            imageFile.delete();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void toJpeg(String path,File fileToDelete){
        BufferedImage bufferedImage;
        try {
            //Считываем изображение в буфер
            bufferedImage = ImageIO.read(new File(path));

            // создаем пустое изображение RGB, с тай же шириной высотой и белым фоном
            BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(),
                    bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);

            // записываем новое изображение в формате jpg
            ImageIO.write(newBufferedImage, "jpg", new File(path.substring(0,path.length()-".png".length())+".jpg"));

            fileToDelete.delete();
            System.out.println("Готово!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String transliterate(String message){
//        char[] abcCyr =   {' ','а','б','в','г','д','е','ё', 'ж','з','и','й','к','л','м','н','о','п','р','с','т','у','ф','х', 'ц','ч', 'ш','щ','ъ','ы','ь','э', 'ю','я','А','Б','В','Г','Д','Е','Ё', 'Ж','З','И','Й','К','Л','М','Н','О','П','Р','С','Т','У','Ф','Х', 'Ц', 'Ч','Ш', 'Щ','Ъ','Ы','Ь','Э','Ю','Я','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
//        String[] abcLat = {" ","a","b","v","g","d","e","yo","zh","z","i","j","k","l","m","n","o","p","r","s","t","u","f","h","ts","ch","sh","sch", "`","$i$", "'","e","ju","ja","A","B","V","G","D","E","E","Zh","Z","I","Y","K","L","M","N","O","P","R","S","T","U","F","H","Ts","Ch","Sh","Sch", "","I", "","E","Ju","Ja","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
//        Map<String,String> translitMap=Translit.makeTranslitMap();
//
//        StringBuilder builder = new StringBuilder();
//        for (int i = 0; i < message.length(); i++) {
//            for (int x = 0; x < translitMap.size(); x++ ) {
//               // if (String.valueOf(message.charAt(i)).equals(abcCyr[x]\) {
//                    builder.append(translitMap.get(String.valueOf(message.charAt(i))));
//               // }
//            }
//        }
//        return builder.toString();
        Translit translit=new Translit(true);
        return translit.translit(message);
    }
    public String reverseTransliterate(String latMessage){
        Translit translit=new Translit(false);
        return translit.translit(latMessage);
    }
    public String createZipFromFiles(String[] fileRefs) throws IOException{
        File[] files=new File[fileRefs.length];
        for(int i=0;i< files.length;i++){
            files[i]=new File(fileRefs[i]);
        }
        String zipName=new Timestamp(System.currentTimeMillis()).getTime() +".zip";
        ZipOutputStream out=new ZipOutputStream(new FileOutputStream("/home/ensler/honor-server/static/temp/"+zipName));
        for(File f:files){
            FileInputStream in=new FileInputStream(f);
            out.putNextEntry(new ZipEntry(f.getName()));
            byte[] b=new byte[1024];
            int count;
            while((count=in.read(b))>0){
                out.write(b,0,count);
            }
            in.close();
        }
        out.close();
        return zipName;
    }
    public String createZipFromDirs(String[] fileRefs) {
        String baseDir="/home/ensler/honor-server/static/temp/";
        String zipName=new Timestamp(System.currentTimeMillis()).getTime()+".zip";
        String zipFileName = baseDir+zipName;
        try {
            final ZipOutputStream outputStream = new ZipOutputStream(new FileOutputStream(zipFileName));
            for (String f:fileRefs) {
                final Path sourceDir= Paths.get(f);
                Files.walkFileTree(sourceDir, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) {
                        try {
                            Path targetFile = sourceDir.relativize(file);
                            outputStream.putNextEntry(new ZipEntry(targetFile.toString()));
                            byte[] bytes = Files.readAllBytes(file);
                            outputStream.write(bytes, 0, bytes.length);
                            outputStream.closeEntry();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return FileVisitResult.CONTINUE;
                    }
                });
            }
            outputStream.close();
        } catch (IOException e) {
           e.printStackTrace();
        }
        return zipName;
    }

    private void makeZip(ZipOutputStream out){

    }
    public FolderFile getAllFiles(File folder){
        FolderFile folderFile=new FolderFile();
        folderFile.setName(folder.getName());
        folderFile.setPath(folder.getAbsolutePath());
        List<FolderFile> folderFiles=new ArrayList<>();
        File[] files=folder.listFiles();

//        Map<String,List<File>> notFolder=new TreeMap<>();
        List<File> allFiles=new ArrayList<>();
        for(File file:files){
            if(file.isDirectory()){
                folderFiles.add(getAllFiles(file));
            }
            else {
                allFiles.add(file);
            }
        }
        folderFile.setFiles(allFiles);
        folderFile.setFolderFiles(folderFiles);
        return folderFile;
//        notFolder.put(folder.getName(),allFiles);
    }

    public List<File> scanRedactables(){
        File news=new File("/home/ensler/honor-server/static/news");
        File post=new File("/home/ensler/honor-server/static/memo");
        File events=new File("/home/ensler/honor-server/static/events");
        File rally=new File("/home/ensler/honor-server/static/rally");
        List<File> result=new ArrayList<>();
        File[] arr={news,post,events,rally};
        for(File f:arr){
            result.addAll(scanFiles(f));
        }
        return result;
    }
    public List<File> scanGallery(){
        File gallery=new File("/home/ensler/honor-server/static/gallery");
        List<File> result=new ArrayList<>();
        result.addAll(scanFiles(gallery));
        return result;
    }
    private List<File> scanFiles(File folder){
        List<File> returnFiles=new ArrayList<>();
        File[] files=folder.listFiles();
        for(File file:files){
            if(file.isDirectory()){
                returnFiles.add(file);
                returnFiles.addAll((scanFiles(file)));
            }
        }
        return returnFiles;
    }
    public List<Redactable> getAllRedactables(){
        List<Redactable> redactables=new ArrayList<>();
        redactables.addAll(postService.getAllPosts(0,1000));
        redactables.addAll(newsService.getAllnews(0,1000));
        redactables.addAll(actionsService.getAllRallies(0,1000,1));
        redactables.addAll(actionsService.getAllRallies(0,1000,2));
        return redactables;
    }
    public Directory getDirContent(File file){
        Directory folder=new Directory();
        folder.setPath(file.getAbsolutePath());
        folder.setRelativePath(file.getPath());
        folder.setName(file.getName());
        List<String> files=new ArrayList<>();
        List<String> folders=new ArrayList<>();
        if(file.isDirectory()) {
            folder.setEmpty(false);
            File[] files1 = file.listFiles();
            for(File file1:files1){
                if(file1.isDirectory())
                    folders.add(file1.getName());
                else
                    files.add(file1.getName());
            }
        }
        else {
            throw new IllegalArgumentException("Not a directory");
        }
        folder.setEmpty(false);
        if(files.isEmpty()&&folders.isEmpty())
            folder.setEmpty(true);
        folder.setFiles(files);
        folder.setFolders(folders);
        return folder;
    }
}
