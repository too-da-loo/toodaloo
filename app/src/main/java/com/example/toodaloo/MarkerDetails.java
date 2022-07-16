package com.example.toodaloo;

import android.os.Parcel;
import android.os.Parcelable;

public class MarkerDetails implements Parcelable {
    private String placeName;
    private String placeRating;
    private String placeAddress;
    private String placeID;

    public MarkerDetails(){
    }

    protected MarkerDetails(Parcel in) {
        placeName = in.readString();
        placeRating = in.readString();
        placeAddress = in.readString();
        placeID = in.readString();
    }

    public static final Creator<MarkerDetails> CREATOR = new Creator<MarkerDetails>() {
        @Override
        public MarkerDetails createFromParcel(Parcel in) {
            return new MarkerDetails(in);
        }

        @Override
        public MarkerDetails[] newArray(int size) {
            return new MarkerDetails[size];
        }
    };

    public String getPlaceName(){
        return placeName;
    }

    public void setPlaceName(String placeName){
        this.placeName=placeName;
    }

    public String getPlaceRating(){
        return placeRating;
    }

    public void setPlaceRating(String placeRating){
        this.placeRating=placeRating;
    }

    public String getPlaceAddress(){
        return placeAddress;
    }

    public void setPlaceAddress(String placeAddress){
        this.placeAddress=placeAddress;
    }

    public String getPlaceID() {return placeID; }

    public void setPlaceID(String placeID) { this.placeID=placeID; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(placeName);
        dest.writeString(placeRating);
        dest.writeString(placeAddress);
        dest.writeString(placeID);
    }
}
