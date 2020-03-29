package Entities;


import java.util.Date;
public interface Comments {
    int getId();
    String getNickname();
    String getDescription();
    Date getTime();
    void setNickname(String nickname);
    void setDescription(String description);
    void setTime(Date time);
    void setRedactable(Redactable redactable);
}
