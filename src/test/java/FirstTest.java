import Entities.deprecated.Redactable;
import dao.PostDAO;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FirstTest {

    @Test
    public void testDeleteDir(){
        String path="/home/ensler/honor-server/static/news/AAA/";
        try {
            FileUtils.deleteDirectory(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testGetDirContent(){
//        String path="/home/ensler/honor-server/static/news/ee/jhopa";
//        Utils utils=new Utils();
//        Directory folderFile=utils.getDirContent(new File(path));
//        System.out.println(folderFile);
    }
    @Test
    public void checkUsesTest(){
        PostDAO dao=new PostDAO();
        List<Redactable> all=new ArrayList<>();
        all.addAll(dao.getAll(0,1000));
        System.out.println(all);
    }
}
