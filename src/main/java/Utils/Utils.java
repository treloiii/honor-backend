package Utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;


@Component("utils")
public class Utils {
    public Utils() {
    }

    public String fileUpload(String serverPath,String fileName, MultipartFile file){
        String res;
        if (!file.isEmpty()&&(file.getContentType().contains("jpeg")||file.getContentType().contains("png"))) {
            try {
                File file1=new File(serverPath + fileName + "." + file.getContentType().substring("image/".length()));
                if(!file1.exists()) {
                    byte[] bytes = file.getBytes();
                    System.out.println(file.getContentType());
                    BufferedOutputStream stream =
                            new BufferedOutputStream(new FileOutputStream(file1));
                    stream.write(bytes);
                    stream.close();
                    res="http://honor-webapp-server.std-763.ist.mospolytech.ru/"+serverPath.substring("/home/std/honor-backend/".length())+fileName + "." + file.getContentType().substring("image/".length());
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
}
