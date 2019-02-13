package com.matching.tech.bio.utilities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.matching.tech.bio.R;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utilities {

    private Activity parentActivity;
    private ProgressDialog progressDialog;
    private String language="en";
    private android.app.AlertDialog alertDialog;
    public static final String TAG = Utilities.class.getSimpleName();
    /**
     * getLanguage() method is used to get the selected language.
     * @return it returns the language selected by the user.*/
    public String getLanguage() {
        return language;
    }
    /**
     * setLanguage() method is used to set the selected language.
     * @param language is the user selected language.*/
    public void setLanguage(String language) {
        this.language = language;
    }

    public Utilities(Activity parentActivity){
        this.parentActivity=parentActivity;
        progressDialog = new ProgressDialog(parentActivity);
        progressDialog.setMessage(parentActivity.getResources().getString(R.string.please_wait));
        progressDialog.setCancelable(false);
    }
    /**
     * isNetworkAvailable() method will check the whether the device has network connection or not.
     * @return it returns boolean value indicating network availability.*/
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) parentActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }
    /**
     * displaySnackBar(String message)is used to show the red color snack bar for errors.
     * @param message is the message to shown in the snack bar.*/
    public void displaySnackBar(String message) {
        Snackbar snackbar = Snackbar.make(parentActivity.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        int snackbarTextId = android.support.design.R.id.snackbar_text;
        TextView textView = snackbarView.findViewById(snackbarTextId);
        textView.setTypeface(getTypeFace("regular"));
        textView.setTextColor(parentActivity.getResources().getColor(R.color.white));
        snackbarView.setBackgroundColor(Color.RED);
        snackbar.show();
    }
    /**
     * displaySnackBarNormal(String message)is used to show the black color snack bar for errors.
     * @param message is the message to shown in the snack bar.*/
    public void displaySnackBarNormal(String message) {
        Snackbar snackbar = Snackbar.make(parentActivity.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        int snackbarTextId = android.support.design.R.id.snackbar_text;
        TextView textView = snackbarView.findViewById(snackbarTextId);
        textView.setTypeface(getTypeFace("regular"));
        textView.setTextColor(parentActivity.getResources().getColor(R.color.white));
        snackbarView.setBackgroundColor(parentActivity.getResources().getColor(R.color.gold_color));
        snackbar.show();
    }
    /**
     * closeKeyboard() method will close the opened keyboard.
     * */
    public void closeKeyboard(){
        try {
            View view = parentActivity.getCurrentFocus();
            InputMethodManager imm = (InputMethodManager) parentActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        } catch (Exception e) {

        }
    }
    /**
     * getFingerIndex(String value) will get the finger position based on finger selected from dropdown.
     * @param value is finger text.
     * @return  it will return finger index .*/
    public int getFingerIndex(String value) {
        int fingerIndex=1;
        if(value.equals(parentActivity.getResources().getString(R.string.right_thumb))){
            fingerIndex = 1;
        }
        if(value.equals(parentActivity.getResources().getString(R.string.right_index))){
            fingerIndex = 2;
        }
        if(value.equals(parentActivity.getResources().getString(R.string.right_middle))){
            fingerIndex = 3;
        }
        if(value.equals(parentActivity.getResources().getString(R.string.right_ring))){
            fingerIndex = 4;
        }
        if(value.equals(parentActivity.getResources().getString(R.string.right_little))){
            fingerIndex = 5;
        }
        if(value.equals(parentActivity.getResources().getString(R.string.left_thumb))){
            fingerIndex = 6;
        }
        if(value.equals(parentActivity.getResources().getString(R.string.left_index))){
            fingerIndex = 7;
        }
        if(value.equals(parentActivity.getResources().getString(R.string.left_middle))){
            fingerIndex = 8;
        }
        if(value.equals(parentActivity.getResources().getString(R.string.left_ring))){
            fingerIndex = 9;
        }
        if(value.equals(parentActivity.getResources().getString(R.string.left_little))){
            fingerIndex = 10;
        }
        return fingerIndex ;
    }
    /**
     * getFingerIndex(String value) will get Finger text to show in dropdown based on the finger index..
     * @param value is finger index.
     * @return  it will return finger message .*/
    public String getFingerMsg(int value) {
        String fingerMsg=null;
        if(value == 1){
            fingerMsg = parentActivity.getResources().getString(R.string.right_thumb);
        }
        else if(value == 2){
            fingerMsg = parentActivity.getResources().getString(R.string.right_index);
        }
        else if(value == 3){
            fingerMsg = parentActivity.getResources().getString(R.string.right_middle);
        }
        else if(value == 4){
            fingerMsg = parentActivity.getResources().getString(R.string.right_ring);
        }
        else if(value == 5){
            fingerMsg = parentActivity.getResources().getString(R.string.right_little);
        }
        else if(value == 6){
            fingerMsg = parentActivity.getResources().getString(R.string.left_thumb);
        }
        else if(value == 7){
            fingerMsg = parentActivity.getResources().getString(R.string.left_index);
        }
        else if(value == 8){
            fingerMsg = parentActivity.getResources().getString(R.string.left_middle);
        }
        else if(value == 9){
            fingerMsg = parentActivity.getResources().getString(R.string.left_ring);
        }
        else if(value == 10){
            fingerMsg = parentActivity.getResources().getString(R.string.left_little);
        }
        else if(value == 23){
            fingerMsg = parentActivity.getResources().getString(R.string.right_index_middle);
        }
        else if(value == 78){
            fingerMsg = parentActivity.getResources().getString(R.string.left_index_middle);
        }
        else if(value == 0){
            fingerMsg = parentActivity.getResources().getString(R.string.chooseFinger);
        }
        return fingerMsg ;
    }

    public Typeface getTypeFace(String type){
        Typeface tf = Typeface.createFromAsset(parentActivity.getAssets(), "fonts/Cairo/Cairo-Regular.ttf");
        switch (type){
            case "bold":
                tf = Typeface.createFromAsset(parentActivity.getAssets(), "fonts/Cairo/Cairo-Bold.ttf");
                break;
            case "regular":
                tf = Typeface.createFromAsset(parentActivity.getAssets(), "fonts/Cairo/Cairo-Regular.ttf");
                break;
            case "semi-bold":
                tf = Typeface.createFromAsset(parentActivity.getAssets(),"fonts/Cairo/Cairo-SemiBold.ttf");
        }
        return  tf;
    }
    /**
     * checkImageIntensity(int intensity, int fingerPosition) method will check intensity based on the finger position and return result.
     * @param intensity is the intensity value.
     * @param fingerPosition is position of the finger.
     * @return  it will return String value saying that the intensity is acceptable or not.*/
    public String checkImageIntensity(int intensity, int fingerPosition){
        String result = Constants.SUCCESS;
        switch(fingerPosition){
            case 1:
                result = (intensity > parentActivity.getResources().getInteger(R.integer.right_thumb_minimumAcceptableValue_imageIntensity) && intensity < parentActivity.getResources().getInteger(R.integer.right_thumb_maximumAcceptableValue_imageIntensity)?Constants.SUCCESS:(intensity < parentActivity.getResources().getInteger(R.integer.right_thumb_minimumAcceptableValue_imageIntensity)?Constants.INTENSITY_TOO_LOW:Constants.INTENSITY_TOO_HIGH));
                break;
            case 2:
                result = (intensity > parentActivity.getResources().getInteger(R.integer.right_index_minimumAcceptableValue_imageIntensity) && intensity < parentActivity.getResources().getInteger(R.integer.right_index_maximumAcceptableValue_imageIntensity)?Constants.SUCCESS:(intensity < parentActivity.getResources().getInteger(R.integer.right_index_minimumAcceptableValue_imageIntensity)?Constants.INTENSITY_TOO_LOW:Constants.INTENSITY_TOO_HIGH));
                break;
            case 3:
                result = (intensity > parentActivity.getResources().getInteger(R.integer.right_middle_minimumAcceptableValue_imageIntensity) && intensity < parentActivity.getResources().getInteger(R.integer.right_middle_maximumAcceptableValue_imageIntensity)?Constants.SUCCESS:(intensity < parentActivity.getResources().getInteger(R.integer.right_middle_minimumAcceptableValue_imageIntensity)?Constants.INTENSITY_TOO_LOW:Constants.INTENSITY_TOO_HIGH));
                break;
            case 4:
                result = (intensity > parentActivity.getResources().getInteger(R.integer.right_ring_minimumAcceptableValue_imageIntensity) && intensity < parentActivity.getResources().getInteger(R.integer.right_ring_maximumAcceptableValue_imageIntensity)?Constants.SUCCESS:(intensity < parentActivity.getResources().getInteger(R.integer.right_ring_minimumAcceptableValue_imageIntensity)?Constants.INTENSITY_TOO_LOW:Constants.INTENSITY_TOO_HIGH));
                break;
            case 5:
                result = (intensity > parentActivity.getResources().getInteger(R.integer.right_little_minimumAcceptableValue_imageIntensity) && intensity < parentActivity.getResources().getInteger(R.integer.right_little_maximumAcceptableValue_imageIntensity)?Constants.SUCCESS:(intensity < parentActivity.getResources().getInteger(R.integer.right_little_minimumAcceptableValue_imageIntensity)?Constants.INTENSITY_TOO_LOW:Constants.INTENSITY_TOO_HIGH));
                break;
            case 6:
                result = (intensity > parentActivity.getResources().getInteger(R.integer.left_thumb_minimumAcceptableValue_imageIntensity) && intensity < parentActivity.getResources().getInteger(R.integer.left_thumb_maximumAcceptableValue_imageIntensity)?Constants.SUCCESS:(intensity < parentActivity.getResources().getInteger(R.integer.left_thumb_minimumAcceptableValue_imageIntensity)?Constants.INTENSITY_TOO_LOW:Constants.INTENSITY_TOO_HIGH));
                break;
            case 7:
                result = (intensity > parentActivity.getResources().getInteger(R.integer.left_index_minimumAcceptableValue_imageIntensity) && intensity < parentActivity.getResources().getInteger(R.integer.left_index_maximumAcceptableValue_imageIntensity)?Constants.SUCCESS:(intensity < parentActivity.getResources().getInteger(R.integer.left_index_minimumAcceptableValue_imageIntensity)?Constants.INTENSITY_TOO_LOW:Constants.INTENSITY_TOO_HIGH));
                break;
            case 8:
                result = (intensity > parentActivity.getResources().getInteger(R.integer.left_middle_minimumAcceptableValue_imageIntensity) && intensity < parentActivity.getResources().getInteger(R.integer.left_middle_maximumAcceptableValue_imageIntensity)?Constants.SUCCESS:(intensity < parentActivity.getResources().getInteger(R.integer.left_middle_minimumAcceptableValue_imageIntensity)?Constants.INTENSITY_TOO_LOW:Constants.INTENSITY_TOO_HIGH));
                break;
            case 9:
                result = (intensity > parentActivity.getResources().getInteger(R.integer.left_ring_minimumAcceptableValue_imageIntensity) && intensity < parentActivity.getResources().getInteger(R.integer.left_ring_maximumAcceptableValue_imageIntensity)?Constants.SUCCESS:(intensity < parentActivity.getResources().getInteger(R.integer.left_ring_minimumAcceptableValue_imageIntensity)?Constants.INTENSITY_TOO_LOW:Constants.INTENSITY_TOO_HIGH));
                break;
            case 10:
                result = (intensity > parentActivity.getResources().getInteger(R.integer.left_little_minimumAcceptableValue_imageIntensity) && intensity < parentActivity.getResources().getInteger(R.integer.left_little_maximumAcceptableValue_imageIntensity)?Constants.SUCCESS:(intensity < parentActivity.getResources().getInteger(R.integer.left_little_minimumAcceptableValue_imageIntensity)?Constants.INTENSITY_TOO_LOW:Constants.INTENSITY_TOO_HIGH));
                break;
            case 23:
                result = (intensity > parentActivity.getResources().getInteger(R.integer.ri_rm_minimumAcceptableValue_imageIntensity) && intensity < parentActivity.getResources().getInteger(R.integer.ri_rm_maximumAcceptableValue_imageIntensity)?Constants.SUCCESS:(intensity < parentActivity.getResources().getInteger(R.integer.ri_rm_minimumAcceptableValue_imageIntensity)?Constants.INTENSITY_TOO_LOW:Constants.INTENSITY_TOO_HIGH));
                break;
            case 78:
                result = (intensity > parentActivity.getResources().getInteger(R.integer.li_lm_minimumAcceptableValue_imageIntensity) && intensity < parentActivity.getResources().getInteger(R.integer.li_lm_maximumAcceptableValue_imageIntensity)?Constants.SUCCESS:(intensity < parentActivity.getResources().getInteger(R.integer.li_lm_minimumAcceptableValue_imageIntensity)?Constants.INTENSITY_TOO_LOW:Constants.INTENSITY_TOO_HIGH));
                break;
        }
        return result;
    }

    /**
     * check_NFIQ_Quality(int NFIQ, int fingerPosition) method will check NFIQ quality based on the finger position and NFIQ score and return true or false.
     * @param NFIQ is the NFIQ value.
     * @param fingerPosition is position of the finger.
     * @return  it will return boolean value saying that the NFIQ is acceptable or not.*/
    public boolean check_NFIQ_Quality(int NFIQ, int fingerPosition){
        boolean isNFIQ_acceptable = false;
        switch(fingerPosition){
            case 1:
                isNFIQ_acceptable = (NFIQ <= parentActivity.getResources().getInteger(R.integer.right_thumb_nfiq_acceptable_value_for_verify));
                break;
            case 2:
                isNFIQ_acceptable = (NFIQ <= parentActivity.getResources().getInteger(R.integer.right_index_nfiq_acceptable_value_for_verify));
                break;
            case 3:
                isNFIQ_acceptable = (NFIQ <= parentActivity.getResources().getInteger(R.integer.right_middle_nfiq_acceptable_value_for_verify));
                break;
            case 4:
                isNFIQ_acceptable = (NFIQ <= parentActivity.getResources().getInteger(R.integer.right_ring_nfiq_acceptable_value_for_verify));
                break;
            case 5:
                isNFIQ_acceptable = (NFIQ <= parentActivity.getResources().getInteger(R.integer.right_little_nfiq_acceptable_value_for_verify));
                break;
            case 6:
                isNFIQ_acceptable = (NFIQ <= parentActivity.getResources().getInteger(R.integer.left_thumb_nfiq_acceptable_value_for_verify));
                break;
            case 7:
                isNFIQ_acceptable = (NFIQ <= parentActivity.getResources().getInteger(R.integer.left_index_nfiq_acceptable_value_for_verify));
                break;
            case 8:
                isNFIQ_acceptable = (NFIQ <= parentActivity.getResources().getInteger(R.integer.left_middle_nfiq_acceptable_value_for_verify));
                break;
            case 9:
                isNFIQ_acceptable = (NFIQ <= parentActivity.getResources().getInteger(R.integer.left_ring_nfiq_acceptable_value_for_verify));
                break;
            case 10:
                isNFIQ_acceptable = (NFIQ <= parentActivity.getResources().getInteger(R.integer.left_little_nfiq_acceptable_value_for_verify));
                break;
            case 23:
                isNFIQ_acceptable = (NFIQ <= parentActivity.getResources().getInteger(R.integer.ri_rm_nfiq_acceptable_value_for_verify));
                break;
            case 78:
                isNFIQ_acceptable = (NFIQ <= parentActivity.getResources().getInteger(R.integer.li_lm_nfiq_acceptable_value_for_verify));
                break;
        }
        return isNFIQ_acceptable;
    }
    /**
     * check_NFIQ_Quality_For_Enrollment(int NFIQ, int fingerPosition) method will check NFIQ quality based on the finger position and NFIQ score and return true or false.
     * @param NFIQ is the NFIQ value.
     * @param fingerPosition is position of the finger.
     * @return  it will return boolean value saying that the NFIQ is acceptable or not.*/
    public boolean check_NFIQ_Quality_For_Enrollment(int NFIQ, int fingerPosition){
        boolean isNFIQ_acceptable = false;
        switch(fingerPosition){
            case 1:
                isNFIQ_acceptable = (NFIQ <= parentActivity.getResources().getInteger(R.integer.right_thumb_nfiq_acceptable_value_for_enrollment));
                break;
            case 2:
                isNFIQ_acceptable = (NFIQ <= parentActivity.getResources().getInteger(R.integer.right_index_nfiq_acceptable_value_for_enrollment));
                break;
            case 3:
                isNFIQ_acceptable = (NFIQ <= parentActivity.getResources().getInteger(R.integer.right_middle_nfiq_acceptable_value_for_enrollment));
                break;
            case 4:
                isNFIQ_acceptable = (NFIQ <= parentActivity.getResources().getInteger(R.integer.right_ring_nfiq_acceptable_value_for_enrollment));
                break;
            case 5:
                isNFIQ_acceptable = (NFIQ <= parentActivity.getResources().getInteger(R.integer.right_little_nfiq_acceptable_value_for_enrollment));
                break;
            case 6:
                isNFIQ_acceptable = (NFIQ <= parentActivity.getResources().getInteger(R.integer.left_thumb_nfiq_acceptable_value_for_enrollment));
                break;
            case 7:
                isNFIQ_acceptable = (NFIQ <= parentActivity.getResources().getInteger(R.integer.left_index_nfiq_acceptable_value_for_enrollment));
                break;
            case 8:
                isNFIQ_acceptable = (NFIQ <= parentActivity.getResources().getInteger(R.integer.left_middle_nfiq_acceptable_value_for_enrollment));
                break;
            case 9:
                isNFIQ_acceptable = (NFIQ <= parentActivity.getResources().getInteger(R.integer.left_ring_nfiq_acceptable_value_for_enrollment));
                break;
            case 10:
                isNFIQ_acceptable = (NFIQ <= parentActivity.getResources().getInteger(R.integer.left_little_nfiq_acceptable_value_for_enrollment));
                break;
            case 23:
                isNFIQ_acceptable = (NFIQ <= parentActivity.getResources().getInteger(R.integer.ri_rm_nfiq_acceptable_value_for_enrollment));
                break;
            case 78:
                isNFIQ_acceptable = (NFIQ <= parentActivity.getResources().getInteger(R.integer.li_lm_nfiq_acceptable_value_for_enrollment));
                break;
        }
        return isNFIQ_acceptable;
    }
    /**
     * check_minutiae(int minutiaeCount,int fingerPosition) method will check Minutiae quality based on the finger position and Minutiae count and return true or false..
     * @param minutiaeCount is the minutiae Count value.
     * @param fingerPosition is position of the finger.
     * @return  it will return boolean value saying that the MINUTIAE is acceptable or not.*/
    public boolean check_minutiae(int minutiaeCount,int fingerPosition){
        boolean isMinutiae_acceptable = false;
        switch(fingerPosition){
            case 1:
                isMinutiae_acceptable = (minutiaeCount >= parentActivity.getResources().getInteger(R.integer.right_thumb_minutiae_acceptable_value_for_verify));
                break;
            case 2:
                isMinutiae_acceptable = (minutiaeCount >= parentActivity.getResources().getInteger(R.integer.right_index_minutiae_acceptable_value_for_verify));
                break;
            case 3:
                isMinutiae_acceptable = (minutiaeCount >= parentActivity.getResources().getInteger(R.integer.right_middle_minutiae_acceptable_value_for_verify));
                break;
            case 4:
                isMinutiae_acceptable = (minutiaeCount >= parentActivity.getResources().getInteger(R.integer.right_ring_minutiae_acceptable_value_for_verify));
                break;
            case 5:
                isMinutiae_acceptable = (minutiaeCount >= parentActivity.getResources().getInteger(R.integer.right_little_minutiae_acceptable_value_for_verify));
                break;
            case 6:
                isMinutiae_acceptable = (minutiaeCount >= parentActivity.getResources().getInteger(R.integer.left_thumb_minutiae_acceptable_value_for_verify));
                break;
            case 7:
                isMinutiae_acceptable = (minutiaeCount >= parentActivity.getResources().getInteger(R.integer.left_index_minutiae_acceptable_value_for_verify));
                break;
            case 8:
                isMinutiae_acceptable = (minutiaeCount >= parentActivity.getResources().getInteger(R.integer.left_middle_minutiae_acceptable_value_for_verify));
                break;
            case 9:
                isMinutiae_acceptable = (minutiaeCount >= parentActivity.getResources().getInteger(R.integer.left_ring_minutiae_acceptable_value_for_verify));
                break;
            case 10:
                isMinutiae_acceptable = (minutiaeCount >= parentActivity.getResources().getInteger(R.integer.left_little_minutiae_acceptable_value_for_verify));
                break;
            case 23:
                isMinutiae_acceptable = (minutiaeCount >= parentActivity.getResources().getInteger(R.integer.ri_rm_minutiae_acceptance_for_verify));
                break;
            case 78:
                isMinutiae_acceptable = (minutiaeCount >= parentActivity.getResources().getInteger(R.integer.li_lm_minutiae_acceptance_for_verify));
                break;
        }
        return isMinutiae_acceptable;
    }

    /**
     * check_minutiae(int minutiaeCount,int fingerPosition) method will check Minutiae quality based on the finger position and Minutiae count and return true or false..
     * @param minutiaeCount is the minutiae Count value.
     * @param fingerPosition is position of the finger.
     * @return  it will return boolean value saying that the MINUTIAE is acceptable or not.*/
    public boolean check_minutiae_For_Enrollment(int minutiaeCount,int fingerPosition){
        boolean isMinutiae_acceptable = false;
        switch(fingerPosition){
            case 1:
                isMinutiae_acceptable = (minutiaeCount >= parentActivity.getResources().getInteger(R.integer.right_thumb_minutiae_acceptable_value_for_enrollment));
                break;
            case 2:
                isMinutiae_acceptable = (minutiaeCount >= parentActivity.getResources().getInteger(R.integer.right_index_minutiae_acceptable_value_for_enrollment));
                break;
            case 3:
                isMinutiae_acceptable = (minutiaeCount >= parentActivity.getResources().getInteger(R.integer.right_middle_minutiae_acceptable_value_for_enrollment));
                break;
            case 4:
                isMinutiae_acceptable = (minutiaeCount >= parentActivity.getResources().getInteger(R.integer.right_ring_minutiae_acceptable_value_for_enrollment));
                break;
            case 5:
                isMinutiae_acceptable = (minutiaeCount >= parentActivity.getResources().getInteger(R.integer.right_little_minutiae_acceptable_value_for_enrollment));
                break;
            case 6:
                isMinutiae_acceptable = (minutiaeCount >= parentActivity.getResources().getInteger(R.integer.left_thumb_minutiae_acceptable_value_for_enrollment));
                break;
            case 7:
                isMinutiae_acceptable = (minutiaeCount >= parentActivity.getResources().getInteger(R.integer.left_index_minutiae_acceptable_value_for_enrollment));
                break;
            case 8:
                isMinutiae_acceptable = (minutiaeCount >= parentActivity.getResources().getInteger(R.integer.left_middle_minutiae_acceptable_value_for_enrollment));
                break;
            case 9:
                isMinutiae_acceptable = (minutiaeCount >= parentActivity.getResources().getInteger(R.integer.left_ring_minutiae_acceptable_value_for_enrollment));
                break;
            case 10:
                isMinutiae_acceptable = (minutiaeCount >= parentActivity.getResources().getInteger(R.integer.left_little_minutiae_acceptable_value_for_enrollment));
                break;
            case 23:
                isMinutiae_acceptable = (minutiaeCount >= parentActivity.getResources().getInteger(R.integer.ri_rm_minutiae_acceptance_for_enrollment));
                break;
            case 78:
                isMinutiae_acceptable = (minutiaeCount >= parentActivity.getResources().getInteger(R.integer.li_lm_minutiae_acceptance_for_enrollment));
                break;
        }
        return isMinutiae_acceptable;
    }
    /**
     * showProgressDialog(boolean flag) will show loading dialog.
     * @param flag is the boolean value indicates to show dialog or not.*/
    public void showProgressDialog(boolean flag){
        if(flag){
            progressDialog.show();
        }else{
            progressDialog.dismiss();
        }

    }
    /**
     * extractExpirationTimeFromToken(String token) method is used to extract Token expiration time from user token.
     * @param token is the user token.
     * @return  it will return Date object */
    public Date extractExpirationTimeFromToken(String token){
        String[] tokenParts = token.split("\\.");
        if(tokenParts.length != 3){
            LogUtils.debug(TAG,"userToken is not JWT! Failed to extract the expiration time!");
        }
        else
        {
            String payload = null;
            try{
                payload = new String(android.util.Base64.decode(tokenParts[1], android.util.Base64.NO_WRAP));
            }catch(IllegalArgumentException e){
                LogUtils.debug(TAG,"tokenParts[1] is not in valid Base64 scheme! tokenParts[1] = " + tokenParts[1]);
            }
            catch(Exception e){
                // thrown if UTF-8 is not supported, should never happen!
                LogUtils.debug(TAG,"UTF-8 is not supported!!");
            }

            if(payload == null) LogUtils.debug(TAG,"payload is null!");
            else{
                try{
                    JsonObject jsonObject = new JsonParser().parse(payload).getAsJsonObject();
                    String exp = jsonObject.get("exp").getAsString();
                    LogUtils.debug(TAG,"Expiration Time String from TOKEN::   "+exp);

                    if(exp == null) LogUtils.debug(TAG,"The payload has no \"exp\"!");
                    else{
                        Date d = new Date(jsonObject.get("exp").getAsLong() * 1000L);
                        LogUtils.debug(TAG,"Calculated Expiration Time from TOKEN:::: "+d);
                        return d;
                    }
                }
                catch(Exception e)
                {
                    LogUtils.debug(TAG,"Failed to extract \"exp\" from payload!");
                }
            }
        }
        return null;
    }
    /**
     * extractIssueTimeFromToken(String token) method is used to extract Token expiration time from user token.
     * @param token is the user token.
     * @return  it will return Date object */
    public Date extractIssueTimeFromToken(String token){
        String[] tokenParts = token.split("\\.");
        if(tokenParts.length != 3){
            LogUtils.debug(TAG,"userToken is not JWT! Failed to extract the expiration time!");
        }
        else
        {
            String payload = null;
            try{
                payload = new String(android.util.Base64.decode(tokenParts[1], android.util.Base64.NO_WRAP));
            }catch(IllegalArgumentException e){
                LogUtils.debug(TAG,"tokenParts[1] is not in valid Base64 scheme! tokenParts[1] = " + tokenParts[1]);
            }
            catch(Exception e){
                // thrown if UTF-8 is not supported, should never happen!
                LogUtils.debug(TAG,"UTF-8 is not supported!!");
            }

            if(payload == null) LogUtils.debug(TAG,"payload is null!");
            else{
                try{
                    JsonObject jsonObject = new JsonParser().parse(payload).getAsJsonObject();
                    String exp = jsonObject.get("iat").getAsString();
                    LogUtils.debug(TAG,"Expiration Time String from TOKEN::   "+exp);

                    if(exp == null) LogUtils.debug(TAG,"The payload has no \"iat\"!");
                    else{
                        Date d = new Date(jsonObject.get("iat").getAsLong() * 1000L);
                        LogUtils.debug(TAG,"Calculated Expiration Time from TOKEN:::: "+d);
                        return d;
                    }
                }
                catch(Exception e)
                {
                    LogUtils.debug(TAG,"Failed to extract \"iat\" from payload!");
                }
            }
        }
        return null;
    }

    /**
     * timeDifference(Date expirationDateTime) method is used get the difference in 2 times.
     * @param expirationDateTime is the expiration time.
     * @return  it will return long value indicating difference in milli seconds.
     * */
    public long timeDifference(Date expirationDateTime,Date issueDateTime){
        long difference = 0;
        try {
            difference = 0;
            LogUtils.debug(TAG,"issueDateTime    :: "+issueDateTime);
            LogUtils.debug(TAG,"expirationDateTime :: "+expirationDateTime);
            difference = expirationDateTime.getTime() - issueDateTime.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  difference;
    }

    public View buildConfirmationDialog(String title,String message,String language){
        LayoutInflater inflater = parentActivity.getLayoutInflater();
        View dialogView = inflater.inflate(Constants.AR.equalsIgnoreCase(language)?R.layout.confirmation_dialog:R.layout.confirmation_dialog_en, null);
        TextView title_tv = dialogView.findViewById(R.id.title);
        TextView message_tv = dialogView.findViewById(R.id.message);

        title_tv.setTypeface(getTypeFace("bold"));
        message_tv.setTypeface(getTypeFace("regular"));

        title_tv.setText(title);
        message_tv.setText(message);

        return dialogView;
    }

    public View buildConfirmationErrorDialog(String title,String message){
        LayoutInflater inflater = parentActivity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.confirmation_error_dialog_en, null);
        TextView title_tv = dialogView.findViewById(R.id.title);
        TextView message_tv = dialogView.findViewById(R.id.message);

        title_tv.setTypeface(getTypeFace("bold"));
        message_tv.setTypeface(getTypeFace("regular"));

        title_tv.setText(title);
        message_tv.setText(message);

        return dialogView;
    }
    /**
     * removeWhiteSpaces(String text) will remove white spaces from arabic text.
     * @param text is arabic text to remove white spaces.
     * @return it will return arabic text after removing white spaces.*/
    public String removeWhiteSpaces(String text){
        return Normalizer.normalize(text, Normalizer.Form.NFKC).trim().replaceAll("\\s+", " ");
    }
    public void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {}
    }
    public boolean deleteDir(File dir) {
        try {
            if (dir != null && dir.isDirectory()) {
                String[] children = dir.list();
                for (int i = 0; i < children.length; i++) {
                    boolean success = deleteDir(new File(dir, children[i]));
                    if (!success) {
                        return false;
                    }
                }
                return dir.delete();
            } else if(dir!= null && dir.isFile()) {
                return dir.delete();
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public void showLargeImage(Bitmap largeBitmapImage){
        android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(parentActivity);
        LayoutInflater inflater = parentActivity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.face_image_viewer, null);
        ImageView largeImage = dialogView.findViewById(R.id.imageView);
        largeImage.setImageBitmap(largeBitmapImage);
        largeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }

    public String getDeviceName(){
        return Build.MANUFACTURER;
    }
    public void log(){
        try{
            File fileName = new File(Environment.getExternalStorageDirectory()+"/logfile_banan.log");
            fileName.createNewFile();
            String cmd = "logcat -d -f "+fileName.getAbsolutePath();
            Runtime.getRuntime().exec(cmd);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public String covertDate(Date dob){
        String convertedDate;
        try{
            DateFormat outDate = new SimpleDateFormat("d MMM yyyy");
            convertedDate =outDate.format(dob);
        }catch (Exception e){
            convertedDate = dob.toString();
        }
        return convertedDate;
    }

    public void writeToFile(byte[] capturedBytes, String fileName) {
        try{
            FileOutputStream fos = new FileOutputStream(Environment.getExternalStorageDirectory()+"/"+fileName);
            fos.write(capturedBytes);
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public String getArabicNumber(String number){
        try {
            if(null != number){
                char[] numberInChars = number.toCharArray();
                StringBuilder sb = new StringBuilder();
                for(int i = 0 ; i < numberInChars.length ; i++ ){
                    sb.append(convertNumberToArabic(numberInChars[i]));
                }
                return sb.toString();
            }else{
                return number;
            }
        } catch (Exception e) {
            return number;
        }
    }
    public char convertNumberToArabic(char inputNumber){
        try {
            int number = 0;
            if(inputNumber >= '\u0030' && inputNumber <= '\u0039')
                number = inputNumber + '\u0630';
            return number == 0 ? inputNumber : (char)number;
        } catch (Exception e) {
            return inputNumber;
        }
    }
}
