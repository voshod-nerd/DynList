package com.voshodnerd.dynamiclist.rest;



import com.voshodnerd.dynamiclist.model.ImageModel;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SOService {
    @GET("/photos")
    Call<List<ImageModel>> getImageModelList();
}
