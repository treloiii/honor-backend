package Utils;

import Entities.Actions;
import Entities.News;
import Entities.Post;

import java.util.List;

public class GridObject {
    private String image;
    private String title;
    private int id;
    private String url;
    private String type;
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public GridObject(String image, String title, int id, String url,String type) {
        this.image = image;
        this.title = title;
        this.id = id;
        this.url = url;
        this.type=type;
    }
}
