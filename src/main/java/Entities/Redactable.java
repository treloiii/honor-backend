package Entities;

import java.util.Date;
import java.util.List;

public interface Redactable {
    void setTime(Date time);
    void setDescription(String str);
    void setTitle(String title);
    void setAuthor(String author);
    void setTitle_image_name(String title_image_name);
    void setTitle_image(String title_image);
    String getTitle_image_name();
    String getTitle();
    int getId();
    String getDescription();
    String getTitle_image();
    Date getTime();
    String getAuthor();
    String getCoords();
    void setCoords(String coords);
    String getTitle_image_mini();
    void setTitle_image_mini(String title_image_mini);
    String getDescription_short();
    void setDescription_short(String description_short);
    List<? extends Comments> getComments();
    void setComments(List<? extends Comments> comments);
}
