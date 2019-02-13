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


public class EnrollmentServiceImpl {

    private ServiceResponse serviceResponse;
    private TaskListener taskListener;
    private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(ServiceConstants.CONNECTION_TIME_OUT, TimeUnit.SECONDS).readTimeout(ServiceConstants.READ_TIME_OUT, TimeUnit.SECONDS).build();

    public EnrollmentServiceImpl(TaskListener listener){
        this.taskListener = listener;
    }

    public void enrollment(String token,String idNumber,String fingers,String faceImage,String birthDate){

        if(Constants.isDemo){
            serviceResponse = new ServiceResponse();
            serviceResponse.setResponse(new DummyService().login());
            serviceResponse.setReturnCode(200);
            taskListener.onTaskCompleted(ServiceConstants.ENROLLMENT_TASK,serviceResponse);
        }else{
            try {
                serviceResponse = new ServiceResponse();
                Retrofit login = new Retrofit.Builder().client(okHttpClient).baseUrl("").addConverterFactory(ScalarsConverterFactory.create()).build();
                ServiceApi serviceApi = login.create(ServiceApi.class);
                Call<Integer> call = serviceApi.enrollment(token,idNumber,fingers,faceImage,birthDate);
                call.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
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
                        taskListener.onTaskCompleted(ServiceConstants.ENROLLMENT_TASK,serviceResponse);
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        serviceResponse.setErrorCode(t.getMessage());
                        serviceResponse.setReturnCode(-1);
                        taskListener.onTaskCompleted(ServiceConstants.ENROLLMENT_TASK,serviceResponse);
                    }
                });
            } catch (Exception e) {
                serviceResponse.setErrorCode(e.getMessage());
                serviceResponse.setReturnCode(-2);
                taskListener.onTaskCompleted(ServiceConstants.ENROLLMENT_TASK,serviceResponse);
            }
        }
    }

}
