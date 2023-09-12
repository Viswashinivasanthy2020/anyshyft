package com.demo.anyshyft.api;

import com.demo.anyshyft.model.LicenceFormdata;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {
    @FormUrlEncoded
    @POST("get-licenseTypes")
    Call<LicenceFormdata> submitLicenceFormData(
            @Field("123") String api_key
       //     @Field("field2_name") String field2
    );
}
