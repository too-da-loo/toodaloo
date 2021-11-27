package com.example.toodaloo;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Review.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("otj34oIMvAkxKx5WPdimCRsLgWLeuWKxtMcB2Wlh")
                .clientKey("zS672M3D8wZMJlQfqofkkmfbhY8UcTfmn11SwzA1")
                .server("https://parseapi.back4app.com")
                .build()
        );


    }
}
