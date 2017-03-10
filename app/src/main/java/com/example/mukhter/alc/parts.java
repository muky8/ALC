package com.example.mukhter.alc;

/**
 * Created by MUKHTER on 09/03/2017.
 */

public class parts {

    private String  login , id ,image;
    public parts(String login,String id,String image){
        this.setLogin(login);
        this.setId(id);
        this.setImage(image);

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
