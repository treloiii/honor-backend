package Entities;

import java.util.Date;

public interface Redactable {
    public void setTime(Date time);
    public void setDescription(String str);
    public void setTitle(String title);
    public void setAuthor(String author);
    public void setTitle_image_name(String title_image_name);
    public void setTitle_image(String title_image);
    public String getTitle_image_name();
    public String getTitle();
}
