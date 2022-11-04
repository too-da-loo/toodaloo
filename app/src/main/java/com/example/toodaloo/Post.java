package com.example.toodaloo;

import com.google.android.gms.common.server.converter.StringToIntConverter;
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Post")
public class Post extends ParseObject {
   public static final String KEY_DESCRIPTION = "description";
   public static final String KEY_IMAGE = "image";
   public static final String KEY_USER = "user";
   public static final String KEY_CREATED_KEY = "createdAt";
   public static final String KEY_PLACE_NAME = "placeName";
   public static final String KEY_PLACE_ID = "placeID";
   public static final String KEY_PROFILE_IMAGE = "profilePicture";
   public static final String KEY_OBJECT_ID = "objectId";
   public static final String KEY_RATING = "rating";

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

   public String getPlaceName() {return getString(KEY_PLACE_NAME);}

   public void setPlaceName(String placeName) {put (KEY_PLACE_NAME, placeName);}

   public String getPlaceID() {return getString(KEY_PLACE_ID);}

   public void setPlaceID(String placeID) {put (KEY_PLACE_ID, placeID);}

   public ParseFile getProfileImage() {return getParseFile(KEY_PROFILE_IMAGE);}

   public int getRating() {return getInt(KEY_RATING);}

   public void setRating(int ratingNumber) {put (KEY_RATING, ratingNumber);}

}
