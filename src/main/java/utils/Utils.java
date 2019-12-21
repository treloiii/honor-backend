package utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import sql.ResultedQuery;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;


@Component("utils")
public class Utils {
    public int RESULT_PER_PAGE;
    @Autowired
    private ResultedQuery rq;

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
                    res="http://honor-webapp-server.std-763.ist.mospolytech.ru/"+serverPath.substring("/home/ensler/honor-server/".length())+fileName + ".jpg";
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
}
