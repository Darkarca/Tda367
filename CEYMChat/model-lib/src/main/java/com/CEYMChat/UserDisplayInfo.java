package com.CEYMChat;

import javafx.scene.image.ImageView;
import java.io.Serializable;

public class UserDisplayInfo implements Serializable {

    private String username;
    private ImageView img;
    private String status;

    public String getUsername() {
        return username;
    }
    public ImageView getImg() {
        return img;
    }
    public String getStatus() {
        return status;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setImg(ImageView img) {
        this.img = img;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
