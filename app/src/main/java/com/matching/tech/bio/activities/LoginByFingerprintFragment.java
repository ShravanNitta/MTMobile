package com.matching.tech.bio.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.matching.tech.bio.R;
import com.matching.tech.bio.common.TaskListener;
import com.matching.tech.bio.common.UserSession;
import com.matching.tech.bio.devices.colombo.CaptureData;
import com.matching.tech.bio.devices.colombo.ColomboFingerService;
import com.matching.tech.bio.devices.fingerprintUtils.DMFingerData;
import com.matching.tech.bio.devices.fingerprintUtils.DMSegmentationResult;
import com.matching.tech.bio.devices.fingerprintUtils.SegmentationUtil;
import com.matching.tech.bio.services.interfaces.ServiceConstants;
import com.matching.tech.bio.services.interfaces.ServiceResponse;
import com.matching.tech.bio.services.impl.LoginServiceImpl;
import com.matching.tech.bio.utilities.Constants;
import com.matching.tech.bio.utilities.LogUtils;
import com.matching.tech.bio.utilities.Utilities;

import java.util.ArrayList;

public class LoginByFingerprintFragment extends Fragment implements View.OnClickListener,TaskListener{

    private String userName,fingerImage;
    private String selectedFinger;
    private int fingerPosition;
    private EditText userName_et;
    private Button scan_finger;
    private Utilities utilities;
    private ColomboFingerService fingerService;
    private TextView liveView;
    private ImageView capturedImage;
    LoginActivity loginActivity;
    private ImageView rightHandImage,leftHandImage;
    private ImageView imgLeftThumb,imgLeftIndex,imgLeftMiddle,imgLeftLittle,imgLeftRing;
    private ImageView imgRightThumb,imgRightIndex,imgRightMiddle,imgRightLittle,imgRightRing;
    private android.app.AlertDialog a;
    private LinearLayout hand_selection,dialog_right_hand_layout_right_hand,dialog_left_hand_layout_left_hand;
    public static final String TAG = LoginByFingerprintFragment.class.getSimpleName();
    private UserSession userSession;
    public String[] perms ;
    public LoginByFingerprintFragment() {
    }
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.biometric_login, container, false);
        utilities = new Utilities(getActivity());
        loginActivity = (LoginActivity)getActivity();
        userName_et = view.findViewById(R.id.userName);
        scan_finger = view.findViewById(R.id.scan);
        liveView = view.findViewById(R.id.liveView);
        capturedImage = view.findViewById(R.id.capturedImage);
        scan_finger.setTypeface(utilities.getTypeFace("semi-bold"));
        liveView.setTypeface(utilities.getTypeFace("semi-bold"));
        userName_et.setTypeface(utilities.getTypeFace("semi-bold"));
        perms = getResources().getStringArray(R.array.write_external_storage_permission);

        if(null != getArguments() && null != getArguments().getString(Constants.USER_NAME)){
            userName = getArguments().getString(Constants.USER_NAME);
            userName_et.setText(userName);
        }
        if(null != fingerService){
            fingerService.closeDevice();
        }
        fingerService = new ColomboFingerService(loginActivity,liveView,capturedImage,scan_finger,this);
        leftHandImage =  view.findViewById(R.id.left_hand_selection);
        rightHandImage =  view.findViewById(R.id.right_hand_selection);
        leftHandImage.setOnClickListener(this);
        rightHandImage.setOnClickListener(this);
        scan_finger.setOnClickListener(this);
        hand_selection = view.findViewById(R.id.hand_selection);
        hand_selection.setVisibility(View.VISIBLE);
        capturedImage.setVisibility(View.GONE);

        userSession = new UserSession(getContext());
        userSession.clearSession();
        setDefaultFinger(1);
        return view;
    }
    private void setDefaultFinger(int fingerIndex){
        selectedFinger =  getResources().getString(R.string.right_thumb);
        fingerPosition = fingerIndex;
        rightHandImage.setImageDrawable(ContextCompat.getDrawable(loginActivity.getApplicationContext(), R.drawable.rt_icon_big));
        leftHandImage.setImageDrawable(ContextCompat.getDrawable(loginActivity.getApplicationContext(), R.drawable.lh_icon_big));
    }
    private void showLeftHandDialog() {
        android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(loginActivity);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_left_hand, null);
        dialogBuilder.setView(dialogView);

        imgLeftThumb =  dialogView.findViewById(R.id.dialog_left_hand_image_left_thumb);
        imgLeftIndex =  dialogView.findViewById(R.id.dialog_left_hand_image_left_index);
        imgLeftMiddle =  dialogView.findViewById(R.id.dialog_left_hand_image_left_middle);
        imgLeftRing =  dialogView.findViewById(R.id.dialog_left_hand_image_left_ring);
        imgLeftLittle =  dialogView.findViewById(R.id.dialog_left_hand_image_left_little);
        imgLeftThumb.setOnClickListener(this);
        imgLeftIndex.setOnClickListener(this);
        imgLeftMiddle.setOnClickListener(this);
        imgLeftRing.setOnClickListener(this);
        imgLeftLittle.setOnClickListener(this);

        dialog_left_hand_layout_left_hand = dialogView.findViewById(R.id.dialog_left_hand_layout_left_hand);
        dialog_left_hand_layout_left_hand.setBackground(ContextCompat.getDrawable(loginActivity.getApplicationContext(), R.drawable.lh_icon_big));
        setLeftHand(fingerPosition);
        dialogBuilder.setNegativeButton(getResources().getString(R.string.ok_en), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        a = dialogBuilder.create();
        a.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        a.setOnShowListener( new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                a.getButton(AlertDialog.BUTTON_NEGATIVE).setGravity(Gravity.CENTER);
                a.getButton(AlertDialog.BUTTON_NEGATIVE).setTextSize(14);
                a.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.white));
                a.getButton(AlertDialog.BUTTON_NEGATIVE).setBackgroundColor(getResources().getColor(R.color.gold_color));
            }
        });
        a.show();
        a.getButton(AlertDialog.BUTTON_NEGATIVE).setWidth(240);
        a.getButton(AlertDialog.BUTTON_NEGATIVE).setScaleY(0.7f);

    }

    private void showRightHandDialog() {

        android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(loginActivity);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_right_hand, null);
        dialogBuilder.setView(dialogView);

        imgRightThumb =  dialogView.findViewById(R.id.dialog_right_hand_image_right_thumb);
        imgRightIndex =  dialogView.findViewById(R.id.dialog_right_hand_image_right_index);
        imgRightMiddle =  dialogView.findViewById(R.id.dialog_right_hand_image_right_middle);
        imgRightRing =  dialogView.findViewById(R.id.dialog_right_hand_image_right_ring);
        imgRightLittle =  dialogView.findViewById(R.id.dialog_right_hand_image_right_little);
        imgRightThumb.setOnClickListener(this);
        imgRightIndex.setOnClickListener(this);
        imgRightMiddle.setOnClickListener(this);
        imgRightRing.setOnClickListener(this);
        imgRightLittle.setOnClickListener(this);

        dialog_right_hand_layout_right_hand = dialogView.findViewById(R.id.dialog_right_hand_layout_right_hand);
        dialog_right_hand_layout_right_hand.setBackground(ContextCompat.getDrawable(loginActivity.getApplicationContext(), R.drawable.rh_icon_big));
        setRightHand(fingerPosition);
        dialogBuilder.setNegativeButton(getResources().getString(R.string.ok_en), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        a = dialogBuilder.create();
        a.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        a.setOnShowListener( new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                a.getButton(AlertDialog.BUTTON_NEGATIVE).setGravity(Gravity.CENTER);
                a.getButton(AlertDialog.BUTTON_NEGATIVE).setTextSize(14);
                a.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.white));
                a.getButton(AlertDialog.BUTTON_NEGATIVE).setBackgroundColor(getResources().getColor(R.color.gold_color));
            }
        });
        a.show();
        a.getButton(AlertDialog.BUTTON_NEGATIVE).setWidth(240);
        a.getButton(AlertDialog.BUTTON_NEGATIVE).setScaleY(0.7f);
    }
    private void setLeftHand(int selectedFinger) {
        switch (selectedFinger){
            case 6:
                dialog_left_hand_layout_left_hand.setBackground(ContextCompat.getDrawable(loginActivity.getApplicationContext(), R.drawable.lt_icon_big));
                break;
            case 7:
                dialog_left_hand_layout_left_hand.setBackground(ContextCompat.getDrawable(loginActivity.getApplicationContext(), R.drawable.li_icon_big));
                break;
            case 8:
                dialog_left_hand_layout_left_hand.setBackground(ContextCompat.getDrawable(loginActivity.getApplicationContext(), R.drawable.lm_icon_big));
                break;
            case 9:
                dialog_left_hand_layout_left_hand.setBackground(ContextCompat.getDrawable(loginActivity.getApplicationContext(), R.drawable.lr_icon_big));
                break;
            case 10:
                dialog_left_hand_layout_left_hand.setBackground(ContextCompat.getDrawable(loginActivity.getApplicationContext(), R.drawable.ll_icon_big));
                break;
        }
    }
    private void setRightHand(int selectedFinger) {
        switch (selectedFinger){
            case 1:
                dialog_right_hand_layout_right_hand.setBackground(ContextCompat.getDrawable(loginActivity.getApplicationContext(), R.drawable.rt_icon_big));
                break;
            case 2:
                dialog_right_hand_layout_right_hand.setBackground(ContextCompat.getDrawable(loginActivity.getApplicationContext(), R.drawable.ri_icon_big));
                break;
            case 3:
                dialog_right_hand_layout_right_hand.setBackground(ContextCompat.getDrawable(loginActivity.getApplicationContext(), R.drawable.rm_icon_big));
                break;
            case 4:
                dialog_right_hand_layout_right_hand.setBackground(ContextCompat.getDrawable(loginActivity.getApplicationContext(), R.drawable.rr_icon_big));
                break;
            case 5:
                dialog_right_hand_layout_right_hand.setBackground(ContextCompat.getDrawable(loginActivity.getApplicationContext(), R.drawable.rl_icon_big));
                break;
        }
    }

    @Override
    public void onTaskCompleted(String taskName, Object result) {
        if(ServiceConstants.LOGIN_BY_FINGERPRINT_TASK.equals(taskName)){
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
        }else if(Constants.FINGERPRINT_CAPTURE_TASK.equals(taskName)){
            try {
                utilities.showProgressDialog(true);
                scan_finger.setEnabled(true);
                scan_finger.setVisibility(View.VISIBLE);
                hand_selection.setVisibility(View.VISIBLE);
                capturedImage.setVisibility(View.GONE);
                userName_et.setEnabled(true);
                liveView.setText("");
                CaptureData captureData = (CaptureData)result;
                if(null != captureData && Constants.SUCCESS.equals(captureData.getCaptureStatus())){
                    final int capturedFinger  = captureData.getCapturedFinger();
                    liveView.setText(getResources().getString(R.string.segmentation_in_progress_en));
                    DMSegmentationResult dmSegmentationResult = SegmentationUtil.getInstance().segmentFingers(captureData.getCapturedBytes(),captureData.getCapturedBitmap(),capturedFinger,captureData.getWidth(),captureData.getHeight(),captureData.getNfiqScore(),true);
                    liveView.setText("");
                    if(null != dmSegmentationResult ){
                        ArrayList<DMFingerData> segmentedFingers = dmSegmentationResult.getDmSegmentedFingers();
                        if(null != segmentedFingers && segmentedFingers.size()>0){
                            DMFingerData dmFingerData = segmentedFingers.get(0);

                            if(utilities.check_NFIQ_Quality(captureData.getNfiqScore(),fingerPosition)){
                                if(utilities.check_minutiae(dmFingerData.getMinutiaeCount(),fingerPosition)){
                                    final String fingerImage = dmFingerData.getWsqImage();
                                    fingerService.closeDevice();
                                    if(utilities.isNetworkAvailable()){
                                        LoginServiceImpl loginService = new LoginServiceImpl(LoginByFingerprintFragment.this);
                                        loginService.biometricLogin(userName,fingerImage,fingerPosition);
                                    }else{
                                        utilities.showProgressDialog(false);
                                        View view = utilities.buildConfirmationErrorDialog(getResources().getString(R.string.connectionProblem_en),getResources().getString(R.string.turnon_data_en));
                                        TextView ok_tv = view.findViewById(R.id.ok);
                                        ok_tv.setTypeface(utilities.getTypeFace("bold"));

                                        android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(loginActivity);
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

                                }else{
                                    utilities.showProgressDialog(false);
                                    utilities.displaySnackBar(getResources().getString(R.string.minutia_failed_en)+" "+(null != dmFingerData?dmFingerData.getMinutiaeCount():" "));
                                    liveView.setText(getResources().getString(R.string.minutia_failed_en)+" "+(null != dmFingerData?dmFingerData.getMinutiaeCount():" "));
                                }
                            }else{
                                utilities.showProgressDialog(false);
                                utilities.displaySnackBar(getResources().getString(R.string.nfiq_failed_en)+" "+(captureData.getNfiqScore()));
                                liveView.setText(getResources().getString(R.string.nfiq_failed_en)+" "+(captureData.getNfiqScore()));
                            }
                        }else{
                            utilities.showProgressDialog(false);
                            utilities.displaySnackBar(dmSegmentationResult.getReturnMessage());
                            liveView.setText(dmSegmentationResult.getReturnMessage());
                        }
                    }else{
                        utilities.showProgressDialog(false);
                        utilities.displaySnackBar(getResources().getString(R.string.segmentation_failed_en));
                        liveView.setText(getResources().getString(R.string.segmentation_failed_en));
                    }
                }else{
                    utilities.showProgressDialog(false);
                    utilities.displaySnackBar(getResources().getString(R.string.capture_failed_en));
                    liveView.setText(getResources().getString(R.string.capture_failed_en));
                }
            } catch (Exception e) {
                utilities.showProgressDialog(false);
                liveView.setText("Exception :1: "+e.getMessage());
            }
        }
    }

    @Override
    public void onClick(View view) {
        rightHandImage = view.findViewById(R.id.right_hand_selection);
        leftHandImage =  view.findViewById(R.id.left_hand_selection);
        switch (view.getId()) {
            case R.id.dialog_left_hand_image_left_thumb:
                fingerPosition = 6;
                setLeftHand(fingerPosition);
                leftHandImage.setImageDrawable(ContextCompat.getDrawable(loginActivity.getApplicationContext(),R.drawable.lt_icon_big));
                rightHandImage.setImageDrawable(ContextCompat.getDrawable(loginActivity.getApplicationContext(),R.drawable.rh_icon_big));
                a.dismiss();
                break;
            case R.id.dialog_left_hand_image_left_index:
                fingerPosition = 7;
                setLeftHand(fingerPosition);
                leftHandImage.setImageDrawable(ContextCompat.getDrawable(loginActivity.getApplicationContext(), R.drawable.li_icon_big));
                rightHandImage.setImageDrawable(ContextCompat.getDrawable(loginActivity.getApplicationContext(),R.drawable.rh_icon_big));
                a.dismiss();
                break;
            case R.id.dialog_left_hand_image_left_middle:
                fingerPosition = 8;
                setLeftHand(fingerPosition);
                leftHandImage.setImageDrawable(ContextCompat.getDrawable(loginActivity.getApplicationContext(),R.drawable.lm_icon_big));
                rightHandImage.setImageDrawable(ContextCompat.getDrawable(loginActivity.getApplicationContext(),R.drawable.rh_icon_big));
                a.dismiss();
                break;
            case R.id.dialog_left_hand_image_left_ring:
                fingerPosition = 9;
                setLeftHand(fingerPosition);
                leftHandImage.setImageDrawable(ContextCompat.getDrawable(loginActivity.getApplicationContext(),R.drawable.lr_icon_big));
                rightHandImage.setImageDrawable(ContextCompat.getDrawable(loginActivity.getApplicationContext(),R.drawable.rh_icon_big));
                a.dismiss();
                break;
            case R.id.dialog_left_hand_image_left_little:
                fingerPosition = 10;
                setLeftHand(fingerPosition);
                leftHandImage.setImageDrawable(ContextCompat.getDrawable(loginActivity.getApplicationContext(),R.drawable.ll_icon_big));
                rightHandImage.setImageDrawable(ContextCompat.getDrawable(loginActivity.getApplicationContext(),R.drawable.rh_icon_big));
                a.dismiss();
                break;
            case R.id.dialog_right_hand_image_right_thumb:
                fingerPosition = 1;
                setRightHand(fingerPosition);
                rightHandImage.setImageDrawable(ContextCompat.getDrawable(loginActivity.getApplicationContext(),R.drawable.rt_icon_big));
                leftHandImage.setImageDrawable(ContextCompat.getDrawable(loginActivity.getApplicationContext(),R.drawable.lh_icon_big));
                a.dismiss();
                break;
            case R.id.dialog_right_hand_image_right_index:
                fingerPosition = 2;
                setRightHand(fingerPosition);
                rightHandImage.setImageDrawable(ContextCompat.getDrawable(loginActivity.getApplicationContext(),R.drawable.ri_icon_big));
                leftHandImage.setImageDrawable(ContextCompat.getDrawable(loginActivity.getApplicationContext(),R.drawable.lh_icon_big));
                a.dismiss();
                break;
            case R.id.dialog_right_hand_image_right_middle:
                fingerPosition = 3;
                setRightHand(fingerPosition);
                rightHandImage.setImageDrawable(ContextCompat.getDrawable(loginActivity.getApplicationContext(),R.drawable.rm_icon_big));
                leftHandImage.setImageDrawable(ContextCompat.getDrawable(loginActivity.getApplicationContext(),R.drawable.lh_icon_big));
                a.dismiss();
                break;
            case R.id.dialog_right_hand_image_right_ring:
                fingerPosition = 4;
                rightHandImage.setImageDrawable(ContextCompat.getDrawable(loginActivity.getApplicationContext(),R.drawable.rr_icon_big));
                leftHandImage.setImageDrawable(ContextCompat.getDrawable(loginActivity.getApplicationContext(),R.drawable.lh_icon_big));
                setRightHand(fingerPosition);
                a.dismiss();
                break;
            case R.id.dialog_right_hand_image_right_little:
                fingerPosition = 5;
                setRightHand(fingerPosition);
                rightHandImage.setImageDrawable(ContextCompat.getDrawable(loginActivity.getApplicationContext(),R.drawable.rl_icon_big));
                leftHandImage.setImageDrawable(ContextCompat.getDrawable(loginActivity.getApplicationContext(),R.drawable.lh_icon_big));
                a.dismiss();
                break;

            case R.id.left_hand_selection:
                showLeftHandDialog();
                break;
            case R.id.right_hand_selection:
                showRightHandDialog();
                break;
            case R.id.scan:
                userName = userName_et.getText().toString();
                selectedFinger = utilities.getFingerMsg(fingerPosition);
                if(userName.length()==0){
                    utilities.displaySnackBar(getResources().getString(R.string.username_required_en));
                    userName_et.requestFocus();
                }else {
                    userName = userName.trim();
                    boolean isPermissionGranted  = isStoragePermissionGranted();
                    if(isPermissionGranted){
                        proceedCapture();
                    }
                }
                break;
        }
    }
    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (loginActivity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                requestPermissions(perms,Constants.SUCCESS_CODE);
                return false;
            }
        }else {
            //permission is automatically granted on sdk<23 upon installation
            LogUtils.debug(TAG,"Permission is granted");
            return true;
        }
    }
    @NonNull
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch(requestCode){
            case Constants.SUCCESS_CODE:
                boolean isPermissionGranted = (grantResults[0] == PackageManager.PERMISSION_GRANTED);
                LogUtils.debug(TAG,"isPermissionGranted :: "+isPermissionGranted);
                if(isPermissionGranted){
                    proceedCapture();
                }else{
                    liveView.setText("WRITE_EXTERNAL_STORAGE Permission DENIED");
                }
                break;

        }
    }
    private void proceedCapture(){
        scan_finger.setEnabled(false);
        userName_et.setEnabled(false);
        scan_finger.setVisibility(View.GONE);

        hand_selection.setVisibility(View.VISIBLE);
        capturedImage.setVisibility(View.VISIBLE);

        fingerService.capture(selectedFinger,fingerPosition);
    }
}