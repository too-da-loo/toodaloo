package com.example.toodaloo;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("User")
public class User extends ParseObject {
    public static final String KEY_PROFILE_IMAGE = "profilePicture";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_CREATED_KEY = "createdAt";

    public ParseFile getProfileImage() {
        return getParseFile(KEY_PROFILE_IMAGE);
    }

    public void setImage(ParseFile parseFile){
        put(KEY_PROFILE_IMAGE,parseFile);
    }

    public ParseUser getUser(){
        return getParseUser(KEY_USERNAME);
    }

    //Not sure in what scenario we'd use a setUser method but we'll keep it here
    public void setUser(ParseUser user){ put(KEY_USERNAME, user); }


}
