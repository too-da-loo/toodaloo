package com.example.toodaloo;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("User")
public class User extends ParseObject {
    public static final String KEY_PROFILE_IMAGE = "profilePicture";
    public static final  String KEY_USERNAME = "user";

    public ParseFile getProfileImage() {return getParseFile(KEY_PROFILE_IMAGE);}
    public void setImage(ParseFile parseFile){ put(KEY_PROFILE_IMAGE,parseFile); }
    public ParseUser getUser(){
        return getParseUser(KEY_USERNAME);
    }
    public void setUser(ParseUser user){
        put(KEY_USERNAME, user);
    }


}
