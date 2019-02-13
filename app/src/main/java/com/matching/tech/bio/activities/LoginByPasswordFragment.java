package com.matching.tech.bio.activities;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.matching.tech.bio.R;
import com.matching.tech.bio.common.TaskListener;
import com.matching.tech.bio.common.UserSession;
import com.matching.tech.bio.services.interfaces.ServiceResponse;
import com.matching.tech.bio.services.impl.LoginServiceImpl;
import com.matching.tech.bio.utilities.Constants;
import com.matching.tech.bio.utilities.Utilities;

public class LoginByPasswordFragment extends Fragment implements TaskListener {

    private String userName,password;
    private EditText userName_et,password_et;
    private Button login;
    private Utilities utilities;
    public LoginByPasswordFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login, container, false);
        utilities = new Utilities(getActivity());

        userName_et = view.findViewById(R.id.userName);
        password_et = view.findViewById(R.id.password);
        login = view.findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processLoginRequest();
            }
        });
        return view;
    }
    private void processLoginRequest(){
        System.out.println("Processing.............");
        try{
            userName = userName_et.getText().toString();
            password = password_et.getText().toString();
            System.out.println(userName+":::"+password);
            if(userName.length() == 0){
                utilities.displaySnackBar(getResources().getString(R.string.username_required_en));
                userName_et.requestFocus();
            }else if(password.length() == 0){
                utilities.displaySnackBar(getResources().getString(R.string.password_required_en));
                password_et.requestFocus();
            }
            else {
                userName = userName.trim();
                password = password.trim();
                if(utilities.isNetworkAvailable()){
                    LoginServiceImpl loginService = new LoginServiceImpl(LoginByPasswordFragment.this);
                    loginService.login(userName,password);
                }else{
                    utilities.showProgressDialog(false);
                    View view = utilities.buildConfirmationErrorDialog(getResources().getString(R.string.connectionProblem_en),getResources().getString(R.string.turnon_data_en));
                    TextView ok_tv = view.findViewById(R.id.ok);
                    ok_tv.setTypeface(utilities.getTypeFace("regular"));

                    android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(getContext());
                    dialogBuilder.setView(view);
                    final android.app.AlertDialog alertDialog = dialogBuilder.create();
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    alertDialog.setCancelable(false);
                    alertDialog.show();
                    ok_tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                            alertDialog.dismiss();

                        }
                    });
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void onTaskCompleted(String taskName, Object result) {
        ServiceResponse serviceResponse = (ServiceResponse)result;
        utilities.showProgressDialog(false);
        if(null != serviceResponse && serviceResponse.getReturnCode() == Constants.SUCCESS_CODE){
            if(null != serviceResponse && null != serviceResponse.getResponse() && !"".equals(serviceResponse.getResponse())){
                UserSession userSession = new UserSession(getContext());
                userSession.set(Constants.token,(String)serviceResponse.getResponse());
                Intent intent=new Intent(getActivity(), Home.class);
                utilities.showProgressDialog(false);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        }else {
            View view = utilities.buildConfirmationErrorDialog(getResources().getString(R.string.authentication_failed_en),null != serviceResponse.getErrorCode()?null != utilities.getErrorMessage(serviceResponse.getErrorCode())? utilities.getErrorMessage(serviceResponse.getErrorCode()):serviceResponse.getErrorCode():"Return Code - "+serviceResponse.getReturnCode());
            TextView ok_tv = view.findViewById(R.id.ok);
            ok_tv.setTypeface(utilities.getTypeFace("bold"));

            android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(getActivity());
            dialogBuilder.setView(view);
            final android.app.AlertDialog alertDialog = dialogBuilder.create();
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            alertDialog.setCancelable(false);
            alertDialog.show();
            ok_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getActivity(), LoginActivity.class);
                    intent.putExtra(Constants.OPERATOR_ID, userName);
                    startActivity(intent);
                    alertDialog.dismiss();

                }
            });

        }
    }
 
}