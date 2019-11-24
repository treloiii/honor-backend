package Utils;

import com.googlecode.pngtastic.core.PngImage;
import com.googlecode.pngtastic.core.PngOptimizer;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;


@Component("utils")
public class Utils {
    public Utils() {
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
                if(fileSizeKb>350)
                    file1= new File(serverPath + fileName + postfix + "." + contentType);
                else
                    file1= new File(serverPath + fileName + "." + contentType);

                if(!file1.exists()) {
                    byte[] bytes = file.getBytes();
                    System.out.println(file.getContentType());
                    BufferedOutputStream stream =
                            new BufferedOutputStream(new FileOutputStream(file1));
                    stream.write(bytes);
                    stream.close();
                    res="http://honor-webapp-server.std-763.ist.mospolytech.ru/"+serverPath.substring("/home/std/honor-backend/".length())+fileName + ".jpg";
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
}
