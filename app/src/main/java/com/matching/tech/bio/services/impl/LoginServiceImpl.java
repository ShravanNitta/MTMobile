package com.matching.tech.bio.services.impl;

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


public class LoginServiceImpl{

    private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(ServiceConstants.CONNECTION_TIME_OUT, TimeUnit.SECONDS).readTimeout(ServiceConstants.READ_TIME_OUT, TimeUnit.SECONDS).build();
    private ServiceResponse serviceResponse;
    private TaskListener listener;

    public LoginServiceImpl(TaskListener listener){
        this.listener = listener;
    }

    public void login(String userName,String password){

        if(Constants.isDemo){
            serviceResponse = new ServiceResponse();
            serviceResponse.setResponse(new DummyService().login());
            serviceResponse.setReturnCode(200);
            listener.onTaskCompleted(ServiceConstants.LOGIN_BY_PASSWORD_TASK,serviceResponse);
        }else{
            try {
                serviceResponse = new ServiceResponse();
                Retrofit login = new Retrofit.Builder().client(okHttpClient).baseUrl("").addConverterFactory(ScalarsConverterFactory.create()).build();
                ServiceApi serviceApi = login.create(ServiceApi.class);
                Call<String> call = serviceApi.login(userName,password);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
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
                        listener.onTaskCompleted(ServiceConstants.LOGIN_BY_PASSWORD_TASK,serviceResponse);
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        serviceResponse.setErrorCode(t.getMessage());
                        serviceResponse.setReturnCode(-1);
                        listener.onTaskCompleted(ServiceConstants.LOGIN_BY_PASSWORD_TASK,serviceResponse);
                    }
                });
            } catch (Exception e) {
                serviceResponse.setErrorCode(e.getMessage());
                serviceResponse.setReturnCode(-2);
                listener.onTaskCompleted(ServiceConstants.LOGIN_BY_PASSWORD_TASK,serviceResponse);
            }
        }
    }
    public void biometricLogin(String userName,String fingerImage,int fingerPosition){

        if(Constants.isDemo){
            serviceResponse = new ServiceResponse();
            serviceResponse.setResponse(new DummyService().login());
            serviceResponse.setReturnCode(200);
            listener.onTaskCompleted(ServiceConstants.LOGIN_BY_FINGERPRINT_TASK,serviceResponse);
        }else{
            try {
                serviceResponse = new ServiceResponse();
                Retrofit biometricLogin = new Retrofit.Builder().client(okHttpClient).baseUrl("").addConverterFactory(ScalarsConverterFactory.create()).build();
                ServiceApi serviceApi = biometricLogin.create(ServiceApi.class);
                Call<String> call = serviceApi.biometricLogin(userName,fingerImage,fingerPosition);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
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
                        listener.onTaskCompleted(ServiceConstants.LOGIN_BY_FINGERPRINT_TASK,serviceResponse);
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        serviceResponse.setErrorCode(t.getMessage());
                        serviceResponse.setReturnCode(-1);
                        listener.onTaskCompleted(ServiceConstants.LOGIN_BY_FINGERPRINT_TASK,serviceResponse);
                    }
                });
            } catch (Exception e) {
                serviceResponse.setErrorCode(e.getMessage());
                serviceResponse.setReturnCode(-2);
                listener.onTaskCompleted(ServiceConstants.LOGIN_BY_FINGERPRINT_TASK,serviceResponse);
            }
        }
    }

}
