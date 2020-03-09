import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

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
}
