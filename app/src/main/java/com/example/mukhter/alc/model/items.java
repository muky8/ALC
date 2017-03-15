package com.example.mukhter.alc.model;

/**
 * Created by MUKHTER on 14/03/2017.
 */

public class items {
    private  String login;
    private  String Imageavatar;
    private String profileurl;

    public String getProfileurl() {
        return profileurl;
    }

    public void setProfileurl(String profileurl) {
        this.profileurl = profileurl;
    }

    public items(){

    }

    public String getImageavatar() {
        return Imageavatar;
    }

    public void setImageavatar(String imageavatar) {
        Imageavatar = imageavatar;
    }



    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
