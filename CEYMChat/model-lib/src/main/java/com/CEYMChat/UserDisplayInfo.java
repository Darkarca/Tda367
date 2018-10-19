package com.CEYMChat;

import javafx.scene.image.ImageView;
import java.io.Serializable;
// A class containing information about a single user
public class UserDisplayInfo implements Serializable {

    private String username;
    private ImageView img;
    private String status;
    private Boolean isFriend = false;

    public String getUsername() {
        return username;
    }
    public ImageView getImg() {
        return img;
    }
    public String getStatus() {
        return status;
    }
    public Boolean getIsFriend(){return isFriend;}
    public void setUsername(String username) {
        this.username = username;
    }
    public void setImg(ImageView img) {
        this.img = img;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setIsFriend(Boolean isFriend){this.isFriend=isFriend;}
}
