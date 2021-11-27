package com.example.toodaloo;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Review")
public class Review extends ParseObject {
   public static final String KEY_REVIEW = "description";
   public static final String KEY_USER = "user";

   public String getReview(){
      return getString(KEY_REVIEW);
   }

   public void setReview(String review){
      put(KEY_REVIEW, review);
   }

   public ParseUser getUser(){
      return getParseUser(KEY_USER);
   }

   public void setUser(ParseUser user){
      put(KEY_USER, user);
   }
}
