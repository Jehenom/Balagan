package com.bala.gan.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by asisayag on 11/11/2017.
 */

public class KidModel {
    @SerializedName("name")
    public String kidName;

    @SerializedName("gender")
    public String kidGender;

    @SerializedName("image")
    public String kidImageURL;

    @SerializedName("relatives")
    public List<RelativeModel> relatives;
}
