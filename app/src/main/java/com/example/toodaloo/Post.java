package com.example.toodaloo;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Post")
public class Post extends ParseObject {
   public static final  String KEY_DESCRIPTION = "description";
   public static final  String KEY_IMAGE = "image";
   public static final  String KEY_USER = "user";
   public static final  String KEY_CREATED_KEY = "createdAt";
   public static final  String KEY_RESTNAME = "restaurantName";
   public static final String KEY_PROFILE_IMAGE = "profilePicture";

   public String getDescription(){
      return getString(KEY_DESCRIPTION);
   }

   public void setDescription (String description){ put(KEY_DESCRIPTION, description); }

   public ParseFile getImage(){
      return getParseFile(KEY_IMAGE);
   }

   public void setImage(ParseFile parseFile){
      put(KEY_IMAGE,parseFile);
   }

   public ParseUser getUser(){ return getParseUser(KEY_USER); }

   public void setUser(ParseUser user){
      put(KEY_USER, user);
   }

   public ParseObject getRestaurant() {return getParseObject(KEY_RESTNAME);}

   public void setRestaurant(ParseObject restaurant) {put (KEY_RESTNAME, restaurant);}


   public ParseFile getProfileImage() {return getParseFile(KEY_PROFILE_IMAGE);}


}
