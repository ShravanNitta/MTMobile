package com.matching.tech.bio.services.impl;

import com.matching.tech.bio.beans.PersonInfo;
import com.matching.tech.bio.common.TaskListener;
import com.matching.tech.bio.services.interfaces.DummyService;
import com.matching.tech.bio.services.interfaces.ServiceApi;
import com.matching.tech.bio.services.interfaces.ServiceConstants;
import com.matching.tech.bio.services.interfaces.ServiceResponse;
import com.matching.tech.bio.utilities.Constants;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class VerificationServiceImpl {

    private ServiceResponse serviceResponse;
    private TaskListener taskListener;
    private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(ServiceConstants.CONNECTION_TIME_OUT, TimeUnit.SECONDS).readTimeout(ServiceConstants.READ_TIME_OUT, TimeUnit.SECONDS).build();

    public VerificationServiceImpl(TaskListener listener){
        this.taskListener = listener;
    }

    public void fingerprintVerification(String token,String idNumber,String fingerImage,int fingerPosition){

        if(Constants.isDemo){
            serviceResponse = new ServiceResponse();
            serviceResponse.setResponse(new DummyService().login());
            serviceResponse.setReturnCode(200);
            taskListener.onTaskCompleted(ServiceConstants.FINGERPRINT_VERIFICATION_TASK,serviceResponse);
        }else{
            try {
                serviceResponse = new ServiceResponse();
                Retrofit login = new Retrofit.Builder().client(okHttpClient).baseUrl("").addConverterFactory(ScalarsConverterFactory.create()).build();
                ServiceApi serviceApi = login.create(ServiceApi.class);
                Call<PersonInfo> call = serviceApi.fingerprintVerification(token,idNumber,fingerImage,fingerPosition);
                call.enqueue(new Callback<PersonInfo>() {
                    @Override
                    public void onResponse(Call<PersonInfo> call, Response<PersonInfo> response) {
                        if(response.isSuccessful() && response.code() == ServiceConstants.SUCCESS_CODE){
                            if(null  !=  response.body()){
                                serviceResponse.setResponse(response.body());
                            }
                            serviceResponse.setReturnCode(response.code());
                        }else{
                            try {
                                serviceResponse.setErrorCode(response.errorBody().string());
                            } catch (IOException e) {
                                serviceResponse.setErrorCode(e.getMessage());
                            }
                            serviceResponse.setReturnCode(response.code());
                        }
                        taskListener.onTaskCompleted(ServiceConstants.FINGERPRINT_VERIFICATION_TASK,serviceResponse);
                    }

                    @Override
                    public void onFailure(Call<PersonInfo> call, Throwable t) {
                        serviceResponse.setErrorCode(t.getMessage());
                        serviceResponse.setReturnCode(-1);
                        taskListener.onTaskCompleted(ServiceConstants.FINGERPRINT_VERIFICATION_TASK,serviceResponse);
                    }
                });
            } catch (Exception e) {
                serviceResponse.setErrorCode(e.getMessage());
                serviceResponse.setReturnCode(-2);
                taskListener.onTaskCompleted(ServiceConstants.FINGERPRINT_VERIFICATION_TASK,serviceResponse);
            }
        }
    }

    public void faceVerification(String token,String idNumber,String faceImage){

        if(Constants.isDemo){
            serviceResponse = new ServiceResponse();
            serviceResponse.setResponse(new DummyService().login());
            serviceResponse.setReturnCode(200);
            taskListener.onTaskCompleted(ServiceConstants.FACE_VERIFICATION_TASK,serviceResponse);
        }else{
            try {
                serviceResponse = new ServiceResponse();
                Retrofit biometricLogin = new Retrofit.Builder().client(okHttpClient).baseUrl("").addConverterFactory(ScalarsConverterFactory.create()).build();
                ServiceApi serviceApi = biometricLogin.create(ServiceApi.class);
                Call<PersonInfo> call = serviceApi.faceVerification(token,idNumber,faceImage);
                call.enqueue(new Callback<PersonInfo>() {
                    @Override
                    public void onResponse(Call<PersonInfo> call, Response<PersonInfo> response) {
                        if(response.isSuccessful() && response.code() == ServiceConstants.SUCCESS_CODE){
                            if(null  !=  response.body()){
                                serviceResponse.setResponse(response.body());
                            }
                            serviceResponse.setReturnCode(response.code());
                        }else{
                            try {
                                serviceResponse.setErrorCode(response.errorBody().string());
                            } catch (IOException e) {
                                serviceResponse.setErrorCode(e.getMessage());
                            }
                            serviceResponse.setReturnCode(response.code());
                        }
                        taskListener.onTaskCompleted(ServiceConstants.FACE_VERIFICATION_TASK,serviceResponse);
                    }

                    @Override
                    public void onFailure(Call<PersonInfo> call, Throwable t) {
                        serviceResponse.setErrorCode(t.getMessage());
                        serviceResponse.setReturnCode(-1);
                        taskListener.onTaskCompleted(ServiceConstants.FACE_VERIFICATION_TASK,serviceResponse);
                    }
                });
            } catch (Exception e) {
                serviceResponse.setErrorCode(e.getMessage());
                serviceResponse.setReturnCode(-2);
                taskListener.onTaskCompleted(ServiceConstants.FACE_VERIFICATION_TASK,serviceResponse);
            }
        }
    }

}
