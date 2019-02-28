package com.voshodnerd.dynamiclist.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class ImageModel {

    @SerializedName("albumId")
    @Expose
    Integer albumId;
    @SerializedName("id")
    @Expose
    Integer id;
    @SerializedName("title")
    @Expose
    String title;
    @SerializedName("url")
    @Expose
    String url;
    @SerializedName("thumbnailUrl")
    @Expose
    String thumbnailUrl;

}
