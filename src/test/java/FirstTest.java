import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import utils.Directory;
import utils.FolderFile;
import utils.Utils;

import java.io.File;
import java.io.IOException;

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
        String path="/home/ensler/honor-server/static/";
        Utils utils=new Utils();
        Directory folderFile=utils.getDirContent(new File(path));
        System.out.println(folderFile);
    }
}
