package com.matching.tech.bio.services.interfaces;

import com.matching.tech.bio.beans.PersonInfo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ServiceApi {

    @FormUrlEncoded
    @POST("")
    Call<String> login(
            @Field(ServiceConstants.USERNAME) String userName,
            @Field(ServiceConstants.PASSWORD) String password);

    @FormUrlEncoded
    @POST("")
    Call<String> biometricLogin(
            @Field(ServiceConstants.USERNAME) String userName,
            @Field(ServiceConstants.FINGER_IMAGE) String fingerImage,
            @Field(ServiceConstants.FINGER_POSITION) int fingerPosition);

    @FormUrlEncoded
    @POST("")
    Call<PersonInfo> fingerprintVerification(
            @Header(ServiceConstants.AUTHORIZATION) String userToken,
            @Field(ServiceConstants.ID_NUMBER) String idNumber,
            @Field(ServiceConstants.FINGER_IMAGE) String fingerImage,
            @Field(ServiceConstants.FINGER_POSITION) int fingerPosition);

    @FormUrlEncoded
    @POST("")
    Call<PersonInfo> faceVerification(
            @Header(ServiceConstants.AUTHORIZATION) String userToken,
            @Field(ServiceConstants.ID_NUMBER) String idNumber,
            @Field(ServiceConstants.FACE_IMAGE) String fingerImage);

    @FormUrlEncoded
    @POST("")
    Call<Integer> enrollment(
            @Header(ServiceConstants.AUTHORIZATION) String userToken,
            @Field(ServiceConstants.PERSON_ID) String personId,
            @Field(ServiceConstants.FINGERS) String fingers,
            @Field(ServiceConstants.FACE_IMAGE) String fingerImage,
            @Field(ServiceConstants.BIRTH_DATE) String birthDate);
}
